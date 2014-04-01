package com.poliana.users;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

/**
 * @author David Gilmore
 * @date 3/18/14
 */
@Repository
public class UserSecurityRepositoryImpl implements UserSecurityRepository {

    private Datastore mongoStore;

    private static final Logger logger = Logger.getLogger(UserSecurityRepository.class);

    @Override
    public UserDetails getUserByUsername(String username) {

        Query<RESTUser> query = mongoStore.find(RESTUser.class);

        query.criteria("username").equal(username);

        return query.get();
    }

    @Override
    public UserDetails getUserByApiKey(String apiKey) {

        Query<RESTUser> query = mongoStore.find(RESTUser.class);

        query.criteria("apiKey").equal(apiKey);

        return query.get();
    }

    @Override
    public UserDetails createUser(UserDetails user) {

        mongoStore.save(user);

        return user;
    }

    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
