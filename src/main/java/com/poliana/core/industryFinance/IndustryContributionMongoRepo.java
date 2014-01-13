package com.poliana.core.industryFinance;

import com.poliana.core.industryFinance.entities.IndustryContributionTotalsHashMap;
import com.poliana.core.industryFinance.entities.IndustryTimeRangeTotals;
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
     * @param industryContributionTotalsHashMap
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotalsHashMap
     */
    public Key<IndustryContributionTotalsHashMap> saveIndustryContributionTotals(IndustryContributionTotalsHashMap industryContributionTotalsHashMap) {

        return mongoStore.save(industryContributionTotalsHashMap);
    }

    /**
     * Get totals for an industry's contributions to all legislators during a given congressional cycle.
     * @param congress
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotalsHashMap
     */
    public IndustryContributionTotalsHashMap getIndustryTotals(String industryId, int congress) {

        Query<IndustryContributionTotalsHashMap> query = mongoStore.createQuery(IndustryContributionTotalsHashMap.class);

        query.and(
                query.criteria("industryId").equal(industryId),
                query.criteria("congress").equal(congress));

        return query.get();
    }

    /**
     * Get totals for an industry category contributions to all legislators during a given congressional cycle.
     * @param congress
     * @return
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotalsHashMap
     */
    public IndustryContributionTotalsHashMap getIndustryCategoryTotals(String categoryId, int congress) {

        Query<IndustryContributionTotalsHashMap> query = mongoStore.createQuery(IndustryContributionTotalsHashMap.class);

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
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotalsHashMap
     */
    public IndustryContributionTotalsHashMap getIndustryChamberTotals(String industryId, String chamber, int congress) {

        Query<IndustryContributionTotalsHashMap> query = mongoStore.createQuery(IndustryContributionTotalsHashMap.class);

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
     * @see com.poliana.core.industryFinance.entities.IndustryContributionTotalsHashMap
     */
    public IndustryContributionTotalsHashMap getIndustryCategoryChamberTotals(String categoryId, String chamber, int congress) {

        Query<IndustryContributionTotalsHashMap> query = mongoStore.createQuery(IndustryContributionTotalsHashMap.class);

        query.and(
                query.criteria("categoryId").equal(categoryId),
                query.criteria("chamber").equal(chamber),
                query.criteria("congress").equal(congress));

        return query.get();
    }

    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
