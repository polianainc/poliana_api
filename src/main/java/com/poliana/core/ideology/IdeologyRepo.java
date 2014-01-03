package com.poliana.core.ideology;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.query.Query;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author David Gilmore
 * @date 12/29/13
 */
@Repository
public class IdeologyRepo {

    private Datastore mongoStore;

    private static final Logger logger = Logger.getLogger(IdeologyRepo.class);


    /**
     *
     * @param ideologyMatrix
     * @return
     */
    public Key<IdeologyMatrix> saveIdeologyMatrix(IdeologyMatrix ideologyMatrix) {

        return mongoStore.save(ideologyMatrix);
    }

    /**
     * Save an getIdeologyMatrix matrix to the ideology_matrix collection
     * @param chamber
     * @param congress
     * @return
     * @see IdeologyMatrix
     */
    public IdeologyMatrix getIdeologyMatrix(String chamber, int congress) {

        Query<IdeologyMatrix> query = mongoStore.find(IdeologyMatrix.class);

        query.and(
                query.criteria("chamber").equal(chamber),
                query.criteria("congress").equal(congress));

        return query.get();
    }

    /**
     * Get a LegislatorIdeology object.
     * @param bioguideId
     * @param congress
     * @return
     * @see LegislatorIdeology
     */
    public LegislatorIdeology getLegislatorIdeology(String bioguideId, int congress) {

        Query<LegislatorIdeology> query = mongoStore.find(LegislatorIdeology.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("congress").equal(congress));

        return query.get();
    }

    /**
     * Get a LegislatorIdeology object.
     * @param bioguideId
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     * @see LegislatorIdeology
     */
    public LegislatorIdeology getLegislatorIdeology(String bioguideId, int beginTimestamp, int endTimestamp) {

        Query<LegislatorIdeology> query = mongoStore.find(LegislatorIdeology.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("beginTimestamp").equal(beginTimestamp),
                query.criteria("endTimestamp").equal(endTimestamp));

        return query.get();
    }

    /**
     * Save LegislatorIdeology objects to the legislator_ideology collection
     * @param ideologies
     * @return
     * @see LegislatorIdeology
     */
    public Iterable<Key<LegislatorIdeology>> saveLegislatorIdeologies(List<LegislatorIdeology> ideologies) {

        return mongoStore.save(ideologies);
    }

    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
