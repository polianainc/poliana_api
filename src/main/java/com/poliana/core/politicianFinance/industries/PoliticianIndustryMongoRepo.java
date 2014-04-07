package com.poliana.core.politicianFinance.industries;

import com.poliana.core.politicianFinance.financeProfile.SessionTotals;
import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author David Gilmore
 * @date 1/3/14
 */
@Repository
public class PoliticianIndustryMongoRepo {

    private Datastore mongoStore;

    private static final Logger logger = Logger.getLogger(PoliticianIndustryMongoRepo.class);


    /**
     * Save a list of industry to politician contribution objects
     * @param totalsList
     * @return
     */
    @SuppressWarnings("unchecked")
    public Iterable<Key<PoliticianIndustryContributionsTotals>> saveIndustryToPoliticianContributions(List<PoliticianIndustryContributionsTotals> totalsList) {

        return mongoStore.save(totalsList);
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

    /**
     * Get industry to politician contribution sums for all time
     * @return
     */
    public PoliticianIndustryContributionsTotals getIndustryToPoliticianTotalsAllTime(String bioguideId) {

        Query<PoliticianIndustryContributionsTotals> query = mongoStore.find(PoliticianIndustryContributionsTotals.class);

        query.and();

        return query.get();
    }

    /**
     * Get industry category to politician contribution sums for all time
     * @return
     */
    public PoliticianIndustryContributionsTotals getIndustryCategoryToPoliticianTotalsAllTime(String bioguideId) {

        Query<PoliticianIndustryContributionsTotals> query = mongoStore.find(PoliticianIndustryContributionsTotals.class);

        query.and();

        return query.get();
    }

    /**
     * Get a list of all industry to politician contributions for a given bioguide ID.
     * @param bioguideId
     * @return
     */
    public List<PoliticianIndustryContributionsTotals> getIndustryToPoliticianTotals(String bioguideId) {

        Query<PoliticianIndustryContributionsTotals> query = mongoStore.find(PoliticianIndustryContributionsTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("industryId").exists(),
                query.criteria("congress").doesNotExist(),
                query.criteria("year").doesNotExist(),
                query.criteria("month").doesNotExist(),
                query.criteria("beginTimestamp").doesNotExist(),
                query.criteria("endTimestamp").doesNotExist());

        return query.asList();
    }

    /**
     * Get a list of all industry to politician contributions for a given bioguide ID.
     * @param bioguideId
     * @return
     */
    public List<PoliticianIndustryContributionsTotals> getIndustryCategoryToPoliticianTotals(String bioguideId) {

        Query<PoliticianIndustryContributionsTotals> query = mongoStore.find(PoliticianIndustryContributionsTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("categoryId").exists(),
                query.criteria("congress").doesNotExist(),
                query.criteria("year").doesNotExist(),
                query.criteria("month").doesNotExist(),
                query.criteria("beginTimestamp").doesNotExist(),
                query.criteria("endTimestamp").doesNotExist());

        return query.asList();
    }

    /**
     * Get a list of all industry to politician contributions for a given bioguide ID.
     * @param bioguideId
     * @return
     */
    public List<PoliticianIndustryContributionsTotals> getIndustryToPoliticianTotals(String bioguideId, int congress) {

        Query<PoliticianIndustryContributionsTotals> query = mongoStore.find(PoliticianIndustryContributionsTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("congress").equal(congress));

        return query.asList();
    }

    /**
     * Count the industry contribution sums cached for a given politician and congressional cycle
     * @param bioguideId
     * @param congress
     * @return
     */
    public long countIndustryToPoliticianTotals(String bioguideId, int congress) {

        Query<PoliticianIndustryContributionsTotals> query = mongoStore.find(PoliticianIndustryContributionsTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("categoryId").doesNotExist(),
                query.criteria("congress").equal(congress));

        return mongoStore.getCount(query);
    }

    /**
     * Get a list of all industry category to politician contributions for a given bioguide ID and congress
     * @param bioguideId
     * @return
     */
    public List<PoliticianIndustryContributionsTotals> getIndustryCategoryToPoliticianTotals(String bioguideId, int congress) {

        Query<PoliticianIndustryContributionsTotals> query = mongoStore.find(PoliticianIndustryContributionsTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("categoryId").exists(),
                query.criteria("congress").equal(congress),
                query.criteria("year").doesNotExist(),
                query.criteria("month").doesNotExist(),
                query.criteria("beginTimestamp").doesNotExist(),
                query.criteria("endTimestamp").doesNotExist());

        return query.asList();
    }

    /**
     * Count the industry category contribution sums cached for a given politician and congressional cycle
     * @param bioguideId
     * @param congress
     * @return
     */
    public long countIndustryCategoryToPoliticianTotals(String bioguideId, int congress) {

        Query<PoliticianIndustryContributionsTotals> query = mongoStore.find(PoliticianIndustryContributionsTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("industryId").doesNotExist(),
                query.criteria("congress").equal(congress));

        return mongoStore.getCount(query);
    }

    /**
     *
     * @param bioguideId
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public List<PoliticianIndustryContributionsTotals> getIndustryToPoliticianTotals(String bioguideId, long beginTimestamp, long endTimestamp) {

        Query<PoliticianIndustryContributionsTotals> query = mongoStore.find(PoliticianIndustryContributionsTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("industryId").exists(),
                query.criteria("beginTimestamp").equal(beginTimestamp),
                query.criteria("endTimestamp").equal(endTimestamp));

        return query.asList();
    }

    /**
     *
     * @param bioguideId
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public List<PoliticianIndustryContributionsTotals> getIndustryCategoryToPoliticianTotals(String bioguideId, long beginTimestamp, long endTimestamp) {

        Query<PoliticianIndustryContributionsTotals> query = mongoStore.find(PoliticianIndustryContributionsTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("categoryId").exists(),
                query.criteria("industryId").doesNotExist(),
                query.criteria("beginTimestamp").equal(beginTimestamp),
                query.criteria("endTimestamp").equal(endTimestamp));

        return query.asList();
    }

    /**
     * Get a list of all industry to politician contributions for a given bioguide ID.
     * @param bioguideId
     * @return
     */
    public Iterator<PoliticianIndustryContributionsTotals> getIndustryToPoliticianTotalsIterator(String bioguideId) {

        Query<PoliticianIndustryContributionsTotals> query = mongoStore.find(PoliticianIndustryContributionsTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("industryId").exists(),
                query.criteria("congress").doesNotExist(),
                query.criteria("year").doesNotExist(),
                query.criteria("month").doesNotExist(),
                query.criteria("beginTimestamp").doesNotExist(),
                query.criteria("endTimestamp").doesNotExist());

        return query.iterator();
    }

    /**
     * Get an iterator of a list of industry to politician contributions for a given time range
     * @param bioguideId
     * @param cycles
     * @return
     */
    public Iterator<PoliticianIndustryContributionsTotals> getIndustryToPoliticianTotalsIterator(String bioguideId, Integer... cycles) {

        Query<PoliticianIndustryContributionsTotals> query = mongoStore.find(PoliticianIndustryContributionsTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("categoryId").doesNotExist(),
                query.criteria("congress").in(Arrays.asList(cycles))
        );

        return query.iterator();
    }

    /**
     * Get an iterator of a list of industry category to politician contributions for a given time range
     * @param bioguideId
     * @param cycles
     * @return
     */
    public Iterator<PoliticianIndustryContributionsTotals> getIndustryCategoryToPoliticianTotalsIterator(String bioguideId, Integer... cycles) {

        Query<PoliticianIndustryContributionsTotals> query = mongoStore.find(PoliticianIndustryContributionsTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("industryId").doesNotExist(),
                query.criteria("congress").in(Arrays.asList(cycles))
        );

        return query.iterator();
    }


    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
