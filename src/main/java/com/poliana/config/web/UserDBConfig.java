package com.poliana.config.web;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author David Gilmore
 * @date 3/27/14
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.poliana.users")
@PropertySource(value={"classpath:users.properties"})
public class UserDBConfig extends AbstractMongoConfiguration {

    @Autowired
    Environment env;

    @Override
    public String getDatabaseName() {
        return env.getProperty("userdb.name");
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        Mongo mongo = new MongoClient(
                env.getProperty("userdb.host")
                , Integer.parseInt(env.getProperty("userdb.port"))
        );
        mongo.setWriteConcern(WriteConcern.SAFE);
        return mongo;
    }

    @Bean
    public Datastore userStore() throws Exception {
        return new Morphia().createDatastore(
                mongo()
                , env.getProperty("userdb.name")
                , env.getProperty("userdb.username")
                , env.getProperty("userdb.password").toCharArray()
        );
    }

    @Override
    public String getMappingBasePackage() {
        return "com.poliana.users";
    }
}