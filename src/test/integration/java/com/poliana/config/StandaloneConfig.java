package com.poliana.config;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import redis.clients.jedis.JedisPool;

import static org.mockito.Mockito.mock;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
* @author David Gilmore
* @date 10/23/13
*/
@Configuration
@ComponentScan("com.poliana.core")
public class StandaloneConfig {

    @Bean
    public DataSource dataSource() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, NamingException {
        JdbcDataSource dataSource = new JdbcDataSource();
        return dataSource;
    }

    @Bean
    public JdbcTemplate impalaTemplate() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, NamingException {
        JdbcTemplate impalaTemplate = new JdbcTemplate(dataSource());
        return impalaTemplate;
    }

    @Bean
    public JdbcTemplate hiveTemplate() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, NamingException {
        JdbcTemplate hiveTemplate = new JdbcTemplate(dataSource());
        return hiveTemplate;
    }

    @Bean
    public Datastore mongoStore() {
        return new Morphia().createDatastore("");
    }

    @Bean
    public JedisPool jedisPool() {
        return mock(JedisPool.class);
    }
}
