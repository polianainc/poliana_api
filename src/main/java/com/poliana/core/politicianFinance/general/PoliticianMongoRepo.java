package com.poliana.core.politicianFinance.general;

import com.poliana.core.politicianFinance.financeProfile.SessionTotals;
import com.poliana.core.politicianFinance.industries.PoliticianIndustryContributionsTotals;
import com.rollup.olap.models.MorphiaDataNode;
import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author David Gilmore
 * @date 4/20/14
 */
public class PoliticianMongoRepo {
    private Datastore mongoStore;

    private static final Logger logger = Logger.getLogger(PoliticianMongoRepo.class);


    /**
     * Save a list of industry to politician contribution objects
     * @param totalsList
     * @return
     */
    @SuppressWarnings("unchecked")
    public Key<MorphiaDataNode> saveIndustryAndPacContributions(List<Map<String, Object>> totalsList) {

        MorphiaDataNode wrapper = new MorphiaDataNode();

        wrapper.setData(totalsList);
        wrapper.setResourceName("industry_and_pac_contributions");

        return mongoStore.save(wrapper);
    }

    /**
     * Save a collection of term totals to MongoDB
     * @param sessionTotalsCollection
     * @return
     */
    public Iterable<Key<SessionTotals>> saveSessionTotals(Collection<SessionTotals> sessionTotalsCollection) {

        return mongoStore.save(sessionTotalsCollection);
    }

    public List<SessionTotals> getSessionTotals(String bioguide) {

        Query<SessionTotals> query = mongoStore.find(SessionTotals.class);
        query.criteria("bioguideId").equal(bioguide);
        return query.asList();
    }

    /**
     * Get a list of industry to politician contribution sums for all time
     * @return
     */
    public List<PoliticianIndustryContributionsTotals> getAllIndustryToPoliticianTotalsAllTime() {

        Query<PoliticianIndustryContributionsTotals> query = mongoStore.find(PoliticianIndustryContributionsTotals.class);

        query.and();

        return query.asList();
    }

    /**
     * Get a list of industry category to politician contribution sums for all time
     * @return
     */
    public List<PoliticianIndustryContributionsTotals> getAllIndustryCategoryToPoliticianTotalsAllTime() {

        Query<PoliticianIndustryContributionsTotals> query = mongoStore.find(PoliticianIndustryContributionsTotals.class);

        query.and();

        return query.asList();
    }

    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
