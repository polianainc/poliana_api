package com.poliana.config;

import com.mongodb.*;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

/**
* @author David Gilmore
* @date 10/23/13
*/
@Configuration
@Profile("dev")
@ComponentScan("com.poliana")
public class StandaloneConfig {

    @Bean
    public DataSource dataSource() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, NamingException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:˜/test");
        dataSource.setUser("test");
        dataSource.setPassword("test");
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

    @Bean MongoTemplate mongoTemplate() throws IOException {
        int port = Network.getFreeServerPort();;
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(port, Network.localhostIsIPv6()))
                .build();

        MongodStarter runtime = MongodStarter.getDefaultInstance();

        MongodExecutable mongodExecutable = null;
        mongodExecutable = runtime.prepare(mongodConfig);
        MongodProcess mongod = mongodExecutable.start();

        MongoClient mongo = new MongoClient("localhost", port);

        return new MongoTemplate(mongo, "embedded");
    }
}
