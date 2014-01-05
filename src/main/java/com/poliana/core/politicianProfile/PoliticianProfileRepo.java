package com.poliana.core.politicianProfile;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import com.poliana.core.legislators.Legislator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author David Gilmore
 * @date 1/3/14
 */
@Repository
public class PoliticianProfileRepo {

    private Datastore mongoStore;

    private static final Logger logger = Logger.getLogger(PoliticianProfileRepo.class);


    /**
     * Save a collection of term totals to MongoDB
     * @param termTotalsCollection
     * @return
     */
    public Iterable<Key<TermTotals>> saveTermTotals(Collection<TermTotals> termTotalsCollection) {

        return mongoStore.save(termTotalsCollection);
    }

    public List<TermTotals> getTermTotals(String bioguide) {

        Query<TermTotals> query = mongoStore.find(TermTotals.class);
        query.criteria("bioguideId").equal(bioguide);
        return query.asList();
    }

    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
