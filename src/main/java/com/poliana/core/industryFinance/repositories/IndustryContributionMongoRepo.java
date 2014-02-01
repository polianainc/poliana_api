package com.poliana.core.industryFinance.repositories;

import com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap;
import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author David Gilmore
 * @date 1/13/14
 */
@Repository
public class IndustryContributionMongoRepo {

    private Datastore mongoStore;

    private static final Logger logger = Logger.getLogger(IndustryContributionMongoRepo.class);


    /**
     * Save totals for an industry's contributions to all legislators in a certain chamber during a given cycle
     * @param industryContributionTotalsMap
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap
     */
    public Key<IndustryContributionTotalsMap> saveIndustryContributionTotalsMap(IndustryContributionTotalsMap industryContributionTotalsMap) {

        return mongoStore.save(industryContributionTotalsMap);
    }

    /**
     * Get totals for an industry's contributions to all legislators during a given congressional cycle.
     * @param congress
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap
     */
    public IndustryContributionTotalsMap getIndustryContributionTotalsMap(String industryId, int congress) {

        Query<IndustryContributionTotalsMap> query = mongoStore.createQuery(IndustryContributionTotalsMap.class);

        query.and(
                query.criteria("industryId").equal(industryId),
                query.criteria("congress").equal(congress));

        return query.get();
    }

    /**
     * Get totals for an industry category contributions to all legislators during a given congressional cycle.
     * @param congress
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap
     */
    public IndustryContributionTotalsMap getIndustryCategoryContributionTotalsMap(String categoryId, int congress) {

        Query<IndustryContributionTotalsMap> query = mongoStore.createQuery(IndustryContributionTotalsMap.class);

        query.and(
                query.criteria("categoryId").equal(categoryId),
                query.criteria("congress").equal(congress));

        return query.get();
    }

    /**
     * Get totals for an industry's contributions to all legislators in a certain chamber during a given cycle.
     * @param chamber
     * @param congress
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap
     */
    public IndustryContributionTotalsMap getIndustryContributionTotalsMapByChamber(String industryId, String chamber, int congress) {

        Query<IndustryContributionTotalsMap> query = mongoStore.createQuery(IndustryContributionTotalsMap.class);

        query.and(
                query.criteria("industryId").equal(industryId),
                query.criteria("chamber").equal(chamber),
                query.criteria("congress").equal(congress));

        return query.get();
    }

    /**
     * Get totals for an industry category contributions to all legislators in a certain chamber during a given cycle.
     * @param chamber
     * @param congress
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap
     */
    public IndustryContributionTotalsMap getIndustryCategoryContributionTotalsMapByChamber(String categoryId, String chamber, int congress) {

        Query<IndustryContributionTotalsMap> query = mongoStore.createQuery(IndustryContributionTotalsMap.class);

        query.and(
                query.criteria("categoryId").equal(categoryId),
                query.criteria("chamber").equal(chamber),
                query.criteria("congress").equal(congress));

        return query.get();
    }

    /**
     * Get totals for an industry's contributions to all legislators in a certain chamber during a given time range.
     * @param industryId
     * @param chamber
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap
     */
    public IndustryContributionTotalsMap getIndustryContributionTotalsMapByChamber(String industryId, String chamber, long beginTimestamp, long endTimestamp) {

        Query<IndustryContributionTotalsMap> query = mongoStore.createQuery(IndustryContributionTotalsMap.class);

        query.and(
                query.criteria("industryId").equal(industryId),
                query.criteria("categoryId").doesNotExist(),
                query.criteria("chamber").equal(chamber),
                query.criteria("beginTimestamp").equal(beginTimestamp),
                query.criteria("endTimestamp").equal(endTimestamp));

        return query.get();
    }

    /**
     * Get totals for an industry category contributions to all legislators in a certain chamber during a given time range.
     * @param categoryId
     * @param chamber
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap
     */
    public IndustryContributionTotalsMap getIndustryCategoryContributionTotalsMapByChamber(String categoryId, String chamber, long beginTimestamp, long endTimestamp) {

        Query<IndustryContributionTotalsMap> query = mongoStore.createQuery(IndustryContributionTotalsMap.class);

        query.and(
                query.criteria("categoryId").equal(categoryId),
                query.criteria("industryId").doesNotExist(),
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
