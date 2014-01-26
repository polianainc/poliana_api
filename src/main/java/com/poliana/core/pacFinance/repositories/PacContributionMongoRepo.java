package com.poliana.core.pacFinance.repositories;

import com.poliana.core.pacFinance.entities.PacPoliticianContributionTotals;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author David Gilmore
 * @date 12/19/13
 */
@Repository
public class PacContributionMongoRepo {

    private Datastore mongoStore;

    private static final Logger logger = Logger.getLogger(PacContributionMongoRepo.class);


    /**
     * Save a list of PacPoliticianContrTotals.
     * @param totalsList
     * @return
     * @see com.poliana.core.pacFinance.entities.PacPoliticianContributionTotals
     */
    public Iterable<Key<PacPoliticianContributionTotals>> savePacPoliticianContrTotals(List<PacPoliticianContributionTotals> totalsList) {

        return mongoStore.save(totalsList);
    }

    /**
     * Get an iterator for all PacPoliticianContrTotals in MongoDB for a given bioguide ID
     * @param bioguideId
     * @return
     */
    public Iterator<PacPoliticianContributionTotals> getAllPacPoliticianContrTotals(String bioguideId) {

        Query<PacPoliticianContributionTotals> query = mongoStore.find(PacPoliticianContributionTotals.class);

        query.criteria("bioguideId").equal(bioguideId);

        return query.iterator();
    }

    /**
     * Get a list of PAC politician contributions totals for a politician in a given congressional cycle.
     * @param bioguideId
     * @param congress
     * @return
     */
    public List<PacPoliticianContributionTotals> getPacPoliticianContrTotalsMongo(String bioguideId, int congress) {

        Query<PacPoliticianContributionTotals> query = mongoStore.find(PacPoliticianContributionTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("cycle").equal(congress));

        return query.asList();
    }

    /**
     *
     * @param bioguideId
     * @param congress
     * @return
     */
    public long countPacPoliticianContrTotals(String bioguideId, int congress) {

        Query<PacPoliticianContributionTotals> query = mongoStore.find(PacPoliticianContributionTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("cycle").equal(congress));

        return mongoStore.getCount(query);
    }


    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
