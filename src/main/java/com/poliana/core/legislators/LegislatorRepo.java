package com.poliana.core.legislators;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.query.Query;
import org.apache.log4j.Logger;
import org.msgpack.MessagePack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.IOException;
import java.util.*;

/**
 * @author David Gilmore
 * @date 12/26/13
 */
@Repository
public class LegislatorRepo {

    @Autowired
    private JdbcTemplate impalaTemplate;

    @Autowired
    private Datastore mongoStore;

    @Autowired
    private JedisPool jedisPool;

    private final String TERMS_BY_BIOGUIDE = "termsByBioguide:";
    private final String TERMS_BY_LIS = "termsByLis:";
    private final String TERMS_BY_THOMAS = "termsByThomas:";
    private final String TERMS_BY_MONGO = "termsByMongo:";
    private final String TERMS_BY_TIMESTAMP = "termsByTimestamp:";

    private static final Logger logger = Logger.getLogger(LegislatorRepo.class);


    /**
     * Persist a list of legislator objects to MongoDB
     * @return
     */
    public Iterable<Key<Legislator>> saveLegislatorsToMongo() {
        List<Legislator> legislators = getAllLegistlatorTerms();
        return saveLegislators(legislators);
    }

    /**
     * Fetch all legislator objects from Impala for every term he or she has served.
     * @return
     */
    public List<Legislator> getAllLegistlatorTerms() {
        String query = "SELECT * FROM entities.legislators_flat_terms";
        return impalaTemplate.query(query, new LegislatorMapper());
    }

    /**
     * Fetch all legislator objects from Mongo for every term he or she has served.
     * @return
     */
    public Iterator<Legislator> getAllLegislators() {
        Query<Legislator> query =
                mongoStore.createQuery(Legislator.class);
        return query.iterator();
    }

    /**
     * Using Redis, save legislator term lists with bioguide, lis, thomas, and mongo ids as keys.
     */
    public void loadLegislatorTermsToRedis() {

        Iterator<Legislator> legislatorIterator = getAllLegislators();
        Jedis jedis = jedisPool.getResource();
        String bioguideKey;
        String thomasKey;
        String lisKey;
        String mongoKey;
        MessagePack messagePack = new MessagePack();
        while (legislatorIterator.hasNext()) {
            Legislator legislator = legislatorIterator.next();
            bioguideKey = TERMS_BY_BIOGUIDE + legislator.getBioguideId();
            thomasKey = TERMS_BY_THOMAS + legislator.getThomasId();
            lisKey = TERMS_BY_LIS + legislator.getLisId();
            mongoKey = TERMS_BY_MONGO + legislator.getId();
            try {
                jedis.lpush(messagePack.write(bioguideKey), messagePack.write(legislator));
                jedis.lpush(messagePack.write(thomasKey), messagePack.write(legislator));
                jedis.lpush(messagePack.write(lisKey), messagePack.write(legislator));
                jedis.lpush(messagePack.write(mongoKey), messagePack.write(legislator));
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * Return a list of legislator objects for every term a given legislator has served
     * @param bioguideId
     * @return
     */
    public List<Legislator> getLegislatorTermsByBioguide(String bioguideId) {

        String key = TERMS_BY_BIOGUIDE + bioguideId;
        return getLegislatorTermsByKey(key);
    }

    /**
     * Return a list of legislator objects for every term a given legislator has served
     * @param thomasId
     * @return
     */
    public List<Legislator> getLegislatorTermsByThomas(String thomasId) {

        String key = TERMS_BY_THOMAS + thomasId;
        return getLegislatorTermsByKey(key);
    }

    /**
     * Return a list of legislator objects for every term a given legislator has served
     * @param lisId
     * @return
     */
    public List<Legislator> getLegislatorTermsByLis(String lisId) {

        String key = TERMS_BY_LIS + lisId;
        return getLegislatorTermsByKey(key);
    }

    /**
     * Return a list of legislator objects for every term a given legislator has served
     * @param mongoId
     * @return
     */
    public List<Legislator> getLegislatorTermsByMongoId(String mongoId) {

        String key = TERMS_BY_MONGO + mongoId;
        return getLegislatorTermsByKey(key);
    }

    /**
     * Given a redis key, return a list lof legislator objects. Assumes the id has been constructed properly
     * @param key
     * @return
     */
    public List<Legislator> getLegislatorTermsByKey(String key) {

        Jedis jedis = jedisPool.getResource();
        MessagePack messagePack = new MessagePack();
        List<Legislator> legislators = new LinkedList<>();
        try {
            List<byte[]> legislatorBytes = jedis.lrange(messagePack.write(key), 0, -1);
            for (byte[] l : legislatorBytes) {
                Legislator legislator = messagePack.read(l, Legislator.class);
                legislators.add(legislator);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                jedisPool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis)
                jedisPool.returnResource(jedis);
        }
        return legislators;
    }

    /**
     *
     * @param legislatorId
     * @return
     */
    public Iterator<Legislator> getLegislator(String legislatorId) {
        Query<Legislator> query = mongoStore.find(Legislator.class);
        query.or(
                query.criteria("thomasId").equal(legislatorId),
                query.criteria("bioguideId").equal(legislatorId),
                query.criteria("opensecretsId").equal(legislatorId),
                query.criteria("fecId").equal(legislatorId),
                query.criteria("lisId").equal(legislatorId),
                query.criteria("govtrackId").equal(legislatorId));
        return query.iterator();
    }

    /**
     *
     * @param legislators
     * @return
     */
    public Iterable<Key<Legislator>> saveLegislators(List<Legislator> legislators) {
        return mongoStore.save(legislators);
    }

    /**
     *
     * @param chamber
     * @param timestampBegin
     * @param timestampEnd
     * @return
     */
    public Iterator<Legislator> getLegislators(String chamber, int timestampBegin, int timestampEnd) {
        Query<Legislator> query = mongoStore.find(Legislator.class);
        query.and(
                query.criteria("termType").contains(chamber),
                query.criteria("beginTimestamp").lessThan(timestampEnd),
                query.criteria("endTimestamp").greaterThan(timestampBegin)
        );
        return query.iterator();
    }

}
