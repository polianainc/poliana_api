package com.poliana.core.politicianFinance.general;

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
 * @date 4/20/14
 */
@Repository
public class PoliticianRedisRepo {

    Environment env;

    private TimeService timeService;
    private JedisPool jedisPool;

    private String INDUSTRY_AND_PAC_CONTRIBUTION_TOTALS;

    private static final Logger logger = Logger.getLogger(PoliticianPacRedisRepo.class);

    public PoliticianRedisRepo() {
        this.timeService = new TimeService();
    }


    /**
     * Check for the existence of industry contributions over all time
     * @return
     */
    public boolean getIndustryAndPacContributionsExistInCache() {

        Jedis jedis = jedisPool.getResource();

        String key = INDUSTRY_AND_PAC_CONTRIBUTION_TOTALS;

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
    public void setIndustryAndPacContributionsExistInCache() {

        Jedis jedis = jedisPool.getResource();

        String key = INDUSTRY_AND_PAC_CONTRIBUTION_TOTALS;

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


    @Autowired
    public void setEnvAndRedisNameSpaces(Environment env) {

        this.env = env;
        this.INDUSTRY_AND_PAC_CONTRIBUTION_TOTALS = env.getProperty("industry.and.pac.contribution.totals");
    }

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
