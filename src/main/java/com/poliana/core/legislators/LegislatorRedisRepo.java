package com.poliana.core.legislators;

import org.apache.log4j.Logger;
import org.msgpack.MessagePack;
import org.msgpack.type.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author David Gilmore
 * @date 2/28/14
 */
@Repository
public class LegislatorRedisRepo {

    Environment env;

    private JedisPool jedisPool;

    private String TERMS_BY_BIOGUIDE;
    private String TERMS_BY_LIS;
    private String TERMS_BY_THOMAS;
    private String TERMS_BY_MONGO;

    private String LEGISLATOR_CONDENSED;

    private static final Logger logger = Logger.getLogger(LegislatorRedisRepo.class);


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
     * Given a redis key, return a condensed legislator object. Assumes the id has been constructed properly
     * @param bioguideId
     * @return
     */
    public LegislatorCondensed getCondensedLegislator(String bioguideId) {

        Jedis jedis = jedisPool.getResource();
        MessagePack messagePack = new MessagePack();
        LegislatorCondensed legislator = new LegislatorCondensed();
        try {
            byte[] bioBytes = bioguideId.getBytes();
            byte[] legislatorCondensedBytes = jedis.get(bioBytes);
            Value legislatorCondensedValue = messagePack.read(legislatorCondensedBytes);
            legislator = (LegislatorCondensed) legislatorCondensedValue;
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
        return legislator;
    }

    /**
     * Using Redis, save legislator term lists with bioguide, lis, thomas, and mongo ids as keys.
     */
    public void saveLegislatorTerms(Iterator<Legislator> legislatorIterator) {

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
                if (!jedis.exists(messagePack.write(bioguideKey)))
                    jedis.lpush(messagePack.write(bioguideKey), messagePack.write(legislator));
                if (!jedis.exists(messagePack.write(thomasKey)))
                    jedis.lpush(messagePack.write(thomasKey), messagePack.write(legislator));
                if (!jedis.exists(messagePack.write(lisKey)))
                    jedis.lpush(messagePack.write(lisKey), messagePack.write(legislator));
                if (!jedis.exists(messagePack.write(mongoKey)))
                    jedis.lpush(messagePack.write(mongoKey), messagePack.write(legislator));
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * Adds or updates a normal legislator to the condensed legislator cache in redis
     * @param legislator the legislator to add
     */

    public void saveLegislatorCondensed(LegislatorCondensed legislator) {

        Jedis jedis = jedisPool.getResource();
        MessagePack messagePack = new MessagePack();

        String key = LEGISLATOR_CONDENSED + legislator.getBioguideId();
        try {
            if (!jedis.exists(messagePack.write(key)))
                jedis.lpush(messagePack.write(key), messagePack.write(legislator));
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if(null != jedis)
                jedisPool.returnResource(jedis);
        }

    }

    /**
     * Sets the flag for whether or not the condensed legislators are cached in redis
     * in a valid manner
     *
     * @param cached true if cached
     */

    public void setCondensedLegislatorsCached(boolean cached) {

        Jedis jedis = jedisPool.getResource();
        String key = LEGISLATOR_CONDENSED + "cached";

        try {

            if(cached)
                jedis.set(key, "true");
            else
                jedis.set(key, "false");

        } catch (JedisConnectionException e) {

            logger.error(e.getMessage());

            if (null != jedis) {
                jedisPool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis)
                jedisPool.returnResource(jedis);
        }
    }

    /**
     * Determines whether or not the condensed legislators have been cached in redis yet
     * @return true if they're cached, false if not
     */
    public boolean getCondensedLegislatorsCached() {
        Jedis jedis = jedisPool.getResource();

        String key = LEGISLATOR_CONDENSED + "cached";
        boolean cached = false;

        try {

            cached = jedis.get(key).equals("true");

        } catch (JedisConnectionException e) {

            logger.error(e.getMessage());

            if (null != jedis) {
                jedisPool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis)
                jedisPool.returnResource(jedis);
        }

        return cached;

    }

    /**
     * Grabs all condensed legislators from redis
     * @return all the condensed legislators
     */
    public List<LegislatorCondensed> getCondensedLegislators() {

        // TODO: Figure out how to pull all records from redis in one query using just the prefix of the key (LEGISLATOR_CONDENSED)
        List<LegislatorCondensed> legislators = new LinkedList<>();
        return legislators;
    }

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Autowired
    public void setRedisNameSpace(Environment env) {

        this.env = env;

        this.TERMS_BY_BIOGUIDE = env.getProperty("terms.by.bioguide");
        this.TERMS_BY_LIS = env.getProperty("terms.by.lis");
        this.TERMS_BY_THOMAS = env.getProperty("terms.by.thomas");
        this.TERMS_BY_MONGO = env.getProperty("terms.by.mongo");

        this.LEGISLATOR_CONDENSED = env.getProperty("legislator.condensed");
    }
}
