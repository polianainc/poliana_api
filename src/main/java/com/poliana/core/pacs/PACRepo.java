package com.poliana.core.pacs;

import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * @author David Gilmore
 * @date 12/26/13
 */
@Repository
public class PacRepo {

    private Datastore mongoStore;

    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
