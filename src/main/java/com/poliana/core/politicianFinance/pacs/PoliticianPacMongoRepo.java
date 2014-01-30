package com.poliana.core.politicianFinance.pacs;

import org.apache.commons.collections.iterators.ArrayIterator;
import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author David Gilmore
 * @date 1/26/14
 */
@Repository
public class PoliticianPacMongoRepo {

    private Datastore mongoStore;

    private static final Logger logger = Logger.getLogger(PoliticianPacMongoRepo.class);


    public Iterable<Key<PoliticianPacContributionsTotals>> savePacToPoliticianContributions(List<PoliticianPacContributionsTotals> contributionsList) {

        return mongoStore.save(contributionsList);
    }

    public List<PoliticianPacContributionsTotals> getPacToPoliticianContributions(String bioguideId) {

        return null;
    }

    public Iterator<PoliticianPacContributionsTotals> getPacToPoliticianContributionsIterator(String bioguideId) {

        return null;
    }

    /**
     * Get an iterator of a list of politician to pac contributions for a given time range
     * @param bioguideId
     * @param cycles
     * @return
     */
    public Iterator<PoliticianPacContributionsTotals> getPacToPoliticianContributionsIterator(String bioguideId, Integer... cycles) {

        Query<PoliticianPacContributionsTotals> query = mongoStore.find(PoliticianPacContributionsTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("congress").in(Arrays.asList(cycles))
        );

        return query.iterator();
    }

    public List<PoliticianPacContributionsTotals> getPacToPoliticianContributions(String bioguideId, long beginTimestamp, long endTimestamp) {

        return null;
    }

    public List<PoliticianPacContributionsTotals> getPacToPoliticianTotalsPerCongress(String bioguideId, long beginTimestamp, long endTimestamp) {

        return null;
    }

    /**
     * Count the PAC contribution sums cached for a given politician and congressional cycle
     * @param bioguideId
     * @param congress
     * @return
     */
    public long countPacToPoliticianContributions(String bioguideId, int congress) {

        Query<PoliticianPacContributionsTotals> query = mongoStore.find(PoliticianPacContributionsTotals.class);

        query.and(
                query.criteria("bioguideId").equal(bioguideId),
                query.criteria("congress").equal(congress));

        return mongoStore.getCount(query);
    }

    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
