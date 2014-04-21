package com.poliana.core.politicianFinance.general;

import com.poliana.core.politicianFinance.financeProfile.SessionTotals;
import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author David Gilmore
 * @date 4/20/14
 */
@Repository
public class PoliticianMongoRepo {

    private Datastore mongoStore;

    private static final Logger logger = Logger.getLogger(PoliticianMongoRepo.class);


    /**
     * Save a list of industry to politician contribution objects
     * @param totalsList
     * @return
     */
    @SuppressWarnings("unchecked")
    public void saveIndustryAndPacContributions(List<Map<String, Object>> totalsList) {

        mongoStore.save(totalsList);
    }

    public List<SessionTotals> getSessionTotals(String bioguide) {

        Query<SessionTotals> query = mongoStore.find(SessionTotals.class);
        query.criteria("bioguideId").equal(bioguide);
        return query.asList();
    }


    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
