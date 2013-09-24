package com.poliana.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "poliana.data.repositories")
public class MongoConfig extends AbstractMongoConfiguration {

    @Override
    public String getDatabaseName() {
        return "PolianaTest";
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        Mongo mongo = new MongoClient("127.0.0.1");
        mongo.setWriteConcern(WriteConcern.SAFE);
        return mongo;
    }

    @Override
    public String getMappingBasePackage() {
        return "poliana.data.mongo";
    }
}