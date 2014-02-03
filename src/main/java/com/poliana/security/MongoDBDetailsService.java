package com.poliana.security;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

/**
 * @author David Gilmore
 * @date 2/1/14
 */
@Component
public class MongoDBDetailsService implements ClientDetailsService {

    private Datastore mongoStore;


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        ClientDetails details;

        Query<ClientDetails> query = mongoStore.find(ClientDetails.class);

        query.criteria("clientId").equal(clientId);

        details = query.get();

        if (details == null)
            throw new ClientRegistrationException("No client with requested id: " + clientId);

        return details;
    }

    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
