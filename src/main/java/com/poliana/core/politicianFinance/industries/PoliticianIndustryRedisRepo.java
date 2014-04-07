package com.poliana.core.politicianFinance.industries;

import com.poliana.core.politicianFinance.pacs.PoliticianPacRedisRepo;
import com.poliana.core.time.TimeService;
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
public class PoliticianIndustryRedisRepo {

    Environment env;

    private TimeService timeService;
    private JedisPool jedisPool;

    private String INDUSTRY_TO_POLITICIAN_CONTRIBUTIONS_PER_CONGRESS;
    private String INDUSTRY_CATEGORY_TO_POLITICIAN_CONTRIBUTIONS_PER_CONGRESS;
    private String INDUSTRY_TO_POLITICIAN_CONTRIBUTIONS_ALL_TIME;
    private String INDUSTRY_CATEGORY_TO_POLITICIAN_CONTRIBUTIONS_ALL_TIME;

    private static final Logger logger = Logger.getLogger(PoliticianPacRedisRepo.class);


    public PoliticianIndustryRedisRepo() {
        this.timeService = new TimeService();
    }

    /**
     * Check for the existence of industry contributions over all time
     * @return
     */
    public boolean getAllIndustryContributionsAllTimeExistInCache() {

        Jedis jedis = jedisPool.getResource();

        String key = INDUSTRY_TO_POLITICIAN_CONTRIBUTIONS_ALL_TIME;

        boolean exists = true;

        try {
            exists = jedis.exists(key);

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
     * Record the existence of industry contributions over all time
     * @return
     */
    public void setIndustryContributionsAllTimeExistInCache() {

        Jedis jedis = jedisPool.getResource();

        String key = INDUSTRY_TO_POLITICIAN_CONTRIBUTIONS_ALL_TIME;

        try {
            jedis.set(key, this.timeService.getTimeNow());

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

    /**
     * Check for the existence of industry contributions over all time
     * @return
     */
    public boolean getAllIndustryCategoryContributionsAllTimeExistInCache() {

        Jedis jedis = jedisPool.getResource();

        String key = INDUSTRY_CATEGORY_TO_POLITICIAN_CONTRIBUTIONS_ALL_TIME;

        boolean exists = true;

        try {
            exists = jedis.exists(key);

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
     * Record the existence of industry contributions over all time
     * @return
     */
    public void setIndustryCategoryContributionsAllTimeExistInCache() {

        Jedis jedis = jedisPool.getResource();

        String key = INDUSTRY_CATEGORY_TO_POLITICIAN_CONTRIBUTIONS_ALL_TIME;

        try {
            jedis.set(key, this.timeService.getTimeNow());

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

    /**
     * Check for the existence of industry contributions over all time
     * @param bioguideId
     * @return
     */
    public boolean getIndustryContributionsAllTimeExistInCache(String bioguideId) {

        Jedis jedis = jedisPool.getResource();

        String key = INDUSTRY_TO_POLITICIAN_CONTRIBUTIONS_ALL_TIME + bioguideId;

        boolean exists = true;

        try {
            exists = jedis.exists(key);

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
     * Record the existence of industry contributions over all time
     * @param bioguideId
     * @return
     */
    public void setIndustryContributionsAllTimeExistInCache(String bioguideId) {

        Jedis jedis = jedisPool.getResource();

        String key = INDUSTRY_TO_POLITICIAN_CONTRIBUTIONS_ALL_TIME + bioguideId;

        try {
            jedis.set(key, this.timeService.getTimeNow());

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

    /**
     * Check for the existence of industry contributions over all time
     * @param bioguideId
     * @return
     */
    public boolean getIndustryCategoryContributionsAllTimeExistInCache(String bioguideId) {

        Jedis jedis = jedisPool.getResource();

        String key = INDUSTRY_CATEGORY_TO_POLITICIAN_CONTRIBUTIONS_ALL_TIME + bioguideId;

        boolean exists = true;

        try {
            exists = jedis.exists(key);

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
     * Record the existence of industry contributions over all time
     * @param bioguideId
     * @return
     */
    public void setIndustryCategoryContributionsAllTimeExistInCache(String bioguideId) {

        Jedis jedis = jedisPool.getResource();

        String key = INDUSTRY_CATEGORY_TO_POLITICIAN_CONTRIBUTIONS_ALL_TIME + bioguideId;

        try {
            jedis.set(key, this.timeService.getTimeNow());

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

    /**
     * Check for the existence of the supplied congressional cycles in the cache
     * @param bioguideId
     * @param cycles
     * @return
     */
    public boolean getIndustryContributionsExistInCache(String bioguideId, Integer... cycles) {

        Jedis jedis = jedisPool.getResource();

        String key = INDUSTRY_TO_POLITICIAN_CONTRIBUTIONS_PER_CONGRESS + bioguideId;

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
     * @return
     */
    public void setIndustryContributionsExistsInCache(String bioguideId, Integer... cycles) {

        if (cycles != null) {

            Jedis jedis = jedisPool.getResource();

            String key = INDUSTRY_TO_POLITICIAN_CONTRIBUTIONS_PER_CONGRESS + bioguideId;
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

    /**
     * Check for the existence of the supplied congressional cycles in the cache
     * @param bioguideId
     * @param cycles
     * @return
     */
    public boolean getIndustryCategoryContributionsExistsInCache(String bioguideId, Integer... cycles) {

        Jedis jedis = jedisPool.getResource();

        String key = INDUSTRY_CATEGORY_TO_POLITICIAN_CONTRIBUTIONS_PER_CONGRESS + bioguideId;

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
     * @return
     */
    public void setIndustryCategoryContributionsExistsInCache(String bioguideId, Integer... cycles) {

        if (cycles != null) {

            Jedis jedis = jedisPool.getResource();

            String key = INDUSTRY_CATEGORY_TO_POLITICIAN_CONTRIBUTIONS_PER_CONGRESS + bioguideId;
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

        this.INDUSTRY_TO_POLITICIAN_CONTRIBUTIONS_PER_CONGRESS = env.getProperty("industry.to.politician.contributions.per.congress");
        this.INDUSTRY_CATEGORY_TO_POLITICIAN_CONTRIBUTIONS_PER_CONGRESS = env.getProperty("industry.category.to.politician.contributions.per.congress");
        this.INDUSTRY_TO_POLITICIAN_CONTRIBUTIONS_ALL_TIME = env.getProperty("industry.to.politician.contributions.all.time");
        this.INDUSTRY_CATEGORY_TO_POLITICIAN_CONTRIBUTIONS_ALL_TIME = env.getProperty("industry.category.to.politician.contributions.all.time");
    }

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
