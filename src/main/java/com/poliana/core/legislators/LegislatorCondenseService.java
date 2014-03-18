package com.poliana.core.legislators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The Legislator Condense service takes the flattened term objects for all legislators and condenses them into
 * a more application friendly (as opposed to data science friendly) collection.
 *
 * @author Grayson Carroll
 * @date 3/17/14
 */
@Service
public class LegislatorCondenseService {

    private LegislatorMongoRepo legislatorMongoRepo;
    private LegislatorRedisRepo legislatorRedisRepo;

    /**
     * Return all legislators flattened by bioguideId
     * @return
     */
    public List<LegislatorCondensed> getCondensedLegislators() {

        List<LegislatorCondensed> legislatorsCondensed;

        if(!legislatorRedisRepo.getCondensedLegislatorsCached()) {
            // if there aren't any condensed legislators in redis, grab the flat
            // legislators from mongo, iterate over them, create the condensed legislators,
            // store them in redis

            for(Iterator<Legislator> legislator = legislatorMongoRepo.getAllLegislators(); legislator.hasNext();) {
                Legislator leg = legislator.next();
                legislatorRedisRepo.storeOrUpdateCondensedLegislator(leg);
            }

            legislatorRedisRepo.setCondensedLegislatorsCached(true);
        }

        legislatorsCondensed = legislatorRedisRepo.getCondensedLegislators();

        // regardless of how they're created, send 'em out
        return legislatorsCondensed;
    }

    /**
     * Return all legislators in the given time range flattened by bioguideId
     * @return
     */
    public List<LegislatorCondensed> getCondensedLegislators(long beginTimestamp, long endTimestamp) {

        legislatorMongoRepo.getLegislators(beginTimestamp, endTimestamp);

        //TODO: Implement Logic
        LegislatorCondensed legislatorCondensed = new LegislatorCondensed();
        List<LegislatorCondensed> legislatorList = new LinkedList<>();

        return legislatorList;
    }

    @Autowired
    public void setLegislatorMongoRepo(LegislatorMongoRepo legislatorMongoRepo) {
        this.legislatorMongoRepo = legislatorMongoRepo;
    }

    @Autowired
    public void setLegislatorRedisRepo(LegislatorRedisRepo legislatorRedisRepo) {
        this.legislatorRedisRepo = legislatorRedisRepo;
    }
}
