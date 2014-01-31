package com.poliana.core.pacFinance.repositories;

import com.poliana.core.pacFinance.entities.PacContributionTotalsMap;
import org.mongodb.morphia.Datastore;
import org.apache.log4j.Logger;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
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


    /**
     * Save totals for an pac's contributions to all legislators in a certain chamber during a given cycle
     * @param pacContributionTotalsMap
     * @return
     * @see com.poliana.core.pacFinance.entities.PacContributionTotalsMap
     */
    public Key<PacContributionTotalsMap> savePacContributionTotalsMap(PacContributionTotalsMap pacContributionTotalsMap) {

        return mongoStore.save(pacContributionTotalsMap);
    }

    /**
     * Get totals for an pac's contributions to all legislators during a given congressional cycle.
     * @param congress
     * @return
     * @see com.poliana.core.pacFinance.entities.PacContributionTotalsMap
     */
    public PacContributionTotalsMap getPacContributionTotalsMap(String pacId, int congress) {

        Query<PacContributionTotalsMap> query = mongoStore.createQuery(PacContributionTotalsMap.class);

        query.and(
                query.criteria("pacId").equal(pacId),
                query.criteria("congress").equal(congress));

        return query.get();
    }

    /**
     * Get totals for an pac's contributions to all legislators in a certain chamber during a given cycle.
     * @param chamber
     * @param congress
     * @return
     * @see com.poliana.core.pacFinance.entities.PacContributionTotalsMap
     */
    public PacContributionTotalsMap getPacContributionTotalsMap(String pacId, String chamber, int congress) {

        Query<PacContributionTotalsMap> query = mongoStore.createQuery(PacContributionTotalsMap.class);

        query.and(
                query.criteria("pacId").equal(pacId),
                query.criteria("chamber").equal(chamber),
                query.criteria("congress").equal(congress));

        return query.get();
    }

    /**
     * Get totals for an pac's contributions to all legislators during a given time range
     * @param pacId
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public PacContributionTotalsMap getPacContributionTotalsMap(String pacId, long beginTimestamp, long endTimestamp) {

        Query<PacContributionTotalsMap> query = mongoStore.createQuery(PacContributionTotalsMap.class);

        query.and(
                query.criteria("pacId").equal(pacId),
                query.criteria("beginTimestamp").equal(beginTimestamp),
                query.criteria("endTimestamp").equal(endTimestamp));

        return query.get();
    }

    /**
     * Get totals for an pac's contributions to all legislators in a certain chamber during a given time cycle
     * @param pacId
     * @param chamber
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public PacContributionTotalsMap getPacContributionTotalsMap(String pacId, String chamber, long beginTimestamp, long endTimestamp) {

        Query<PacContributionTotalsMap> query = mongoStore.createQuery(PacContributionTotalsMap.class);

        query.and(
                query.criteria("pacId").equal(pacId),
                query.criteria("chamber").equal(chamber),
                query.criteria("beginTimestamp").equal(beginTimestamp),
                query.criteria("endTimestamp").equal(endTimestamp));

        return query.get();
    }

    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
