package com.poliana.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * @author David Gilmore
 * @date 12/29/13
 */
@ComponentScan(basePackages = "com.poliana.core")
@PropertySource(value={"classpath:redis.properties"})
public class RedisConfig {

    @Autowired
    Environment env;

    @Bean
    public JedisPool jedisPool() {

        return new JedisPool(
                new JedisPoolConfig()
                , env.getProperty("redis.host")
                , Integer.parseInt(env.getProperty("redis.port"))
//                , 10000000
        );
    }

}
