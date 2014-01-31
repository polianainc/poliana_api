package com.poliana.core.pacFinance.repositories;

import org.mongodb.morphia.Datastore;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author David Gilmore
 * @date 12/19/13
 */
@Repository
public class PacContributionMongoRepo {

    private Datastore mongoStore;

    private static final Logger logger = Logger.getLogger(PacContributionMongoRepo.class);

    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
