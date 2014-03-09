package com.poliana.core.politicianFinance.pacs;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @author David Gilmore
 * @date 2/28/14
 */
@Repository
public class PoliticianPacRedisRepo {

    Environment env;

    private JedisPool jedisPool;

    private String PAC_TO_POLITICIAN_CONTRIBUTIONS_PER_CONGRESS;

    private static final Logger logger = Logger.getLogger(PoliticianPacRedisRepo.class);


    /**
     * Check for the existence of the supplied congressional cycles in the cache
     * @param bioguideId
     * @param cycles
     * @return
     */
    public boolean getPacContributionsExistInCache(String bioguideId, Integer... cycles) {

        Jedis jedis = jedisPool.getResource();

        String key = PAC_TO_POLITICIAN_CONTRIBUTIONS_PER_CONGRESS + bioguideId;

        boolean exists = true;

        try {

            for (int i = 0; i < cycles.length; i++)
                exists = jedis.sismember(key, "" + cycles[i]);

        } catch (JedisConnectionException e) {
            if (null != jedis) {
                jedisPool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis)
                jedisPool.returnResource(jedis);
        }

        return exists;
    }

    /**
     * Record the existence of the supplied congressional cycles in the cache
     * @param bioguideId
     * @param cycles
     */
    public void setPacContributionsExistsInCache(String bioguideId, Integer... cycles) {

        if (cycles != null) {

            Jedis jedis = jedisPool.getResource();

            String key = PAC_TO_POLITICIAN_CONTRIBUTIONS_PER_CONGRESS + bioguideId;
            String[] values = new String[cycles.length];

            for (int i = 0; i < values.length; i++)
                values[i] = "" + cycles[i];

            try {
                jedis.sadd(key, values);

            } catch (JedisConnectionException e) {
                if (null != jedis) {
                    jedisPool.returnBrokenResource(jedis);
                    jedis = null;
                }
            } finally {
                if (null != jedis)
                    jedisPool.returnResource(jedis);
            }
        }
    }

    @Autowired
    public void setEnvAndRedisNameSpaces(Environment env) {

        this.env = env;

        this.PAC_TO_POLITICIAN_CONTRIBUTIONS_PER_CONGRESS = env.getProperty("pac.to.politician.contributions.per.congress");
    }

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
