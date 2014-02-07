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
     * Using MongoDB, get a list of all industry to politician contributions for a given bioguide ID.
     * @param bioguideId
     * @return
     */
    public List<PoliticianIndustryContributionsTotals> getIndustryToPoliticianContributions(String bioguideId, int congress) {

        Query<PoliticianIndustryContributionsTotals> query = mongoStore.find(PoliticianIndustryContributionsTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("congress").equal(congress));

        return query.asList();
    }

    /**
     * Using MongoDB, get a list of all industry category to politician contributions for a given bioguide ID and congress
     * @param bioguideId
     * @return
     */
    public List<PoliticianIndustryContributionsTotals> getIndustryCategoryToPoliticianContributions(String bioguideId, int congress) {

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
     *
     * @param bioguideId
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public List<PoliticianIndustryContributionsTotals> getIndustryToPoliticianContributions(
            String bioguideId, long beginTimestamp, long endTimestamp) {

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
    public List<PoliticianIndustryContributionsTotals> getIndustryCateogryToPoliticianContributions(
            String bioguideId, long beginTimestamp, long endTimestamp) {

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
     * Using MongoDB, get a list of all industry to politician contributions for a given bioguide ID.
     * @param bioguideId
     * @return
     */
    public Iterator<PoliticianIndustryContributionsTotals> getIndustryToPoliticianContributionsIterator(String bioguideId) {

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
     * Using MongoDB, get a list of all industry to politician contributions for a given bioguide ID.
     * @param bioguideId
     * @return
     */
    public List<PoliticianIndustryContributionsTotals> getIndustryToPoliticianContributions(String bioguideId) {

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
     * Using MongoDB, get a list of all industry to politician contributions for a given bioguide ID.
     * @param bioguideId
     * @return
     */
    public List<PoliticianIndustryContributionsTotals> getIndustryCategoryToPoliticianContributions(String bioguideId) {

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
     * Get an iterator of a list of industry to politician contributions for a given time range
     * @param bioguideId
     * @param cycles
     * @return
     */
    public Iterator<PoliticianIndustryContributionsTotals> getIndustryToPoliticianContributionsIterator(String bioguideId, Integer... cycles) {

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
    public Iterator<PoliticianIndustryContributionsTotals> getIndustryCategoryToPoliticianContributionsIterator(String bioguideId, Integer... cycles) {

        Query<PoliticianIndustryContributionsTotals> query = mongoStore.find(PoliticianIndustryContributionsTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("industryId").doesNotExist(),
                query.criteria("congress").in(Arrays.asList(cycles))
        );

        return query.iterator();
    }

    /**
     * Count the industry contribution sums cached for a given politician and congressional cycle
     * @param bioguideId
     * @param congress
     * @return
     */
    public long countIndustryToPoliticianContributions(String bioguideId, int congress) {

        Query<PoliticianIndustryContributionsTotals> query = mongoStore.find(PoliticianIndustryContributionsTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("categoryId").doesNotExist(),
                query.criteria("congress").equal(congress));

        return mongoStore.getCount(query);
    }

    /**
     * Count the industry category contribution sums cached for a given politician and congressional cycle
     * @param bioguideId
     * @param congress
     * @return
     */
    public long countIndustryCategoryToPoliticianContributions(String bioguideId, int congress) {

        Query<PoliticianIndustryContributionsTotals> query = mongoStore.find(PoliticianIndustryContributionsTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("industryId").doesNotExist(),
                query.criteria("congress").equal(congress));

        return mongoStore.getCount(query);
    }


    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
