package com.poliana.entities.repositories;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.query.Query;
import com.poliana.entities.entities.CongCommittee;
import com.poliana.entities.entities.Industry;
import com.poliana.entities.entities.Legislator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

/**
 * @author David Gilmore
 * @date 11/17/13
 */
@Repository
public class EntitiesMongoRepo {
    @Autowired
    private Datastore mongoStore;

    public Iterator<Legislator> allLegislatorTerms() {
        Query<Legislator> query =
                mongoStore.createQuery(Legislator.class);
        return query.iterator();
    }

    public Iterator<Legislator> getLegislator(String legislatorId) {
        Query<Legislator> query = mongoStore.find(Legislator.class);
        query.or(
                query.criteria("thomasId").equal(legislatorId),
                query.criteria("bioguideId").equal(legislatorId),
                query.criteria("opensecretsId").equal(legislatorId),
                query.criteria("fecId").equal(legislatorId),
                query.criteria("lisId").equal(legislatorId),
                query.criteria("govtrackId").equal(legislatorId));
        return query.iterator();
    }

    public Iterator<Legislator> getLegislators(String chamber, int timestampBegin, int timestampEnd) {
        Query<Legislator> query = mongoStore.find(Legislator.class);
        query.and(
                query.criteria("termStart").greaterThan(timestampBegin),
                query.criteria("termStart").lessThan(timestampEnd),
                query.criteria("termEnd").greaterThan(timestampEnd)
        );
        return query.iterator();
    }

    public Iterable<Key<Legislator>> saveLegislators(List<Legislator> legislators) {
        return mongoStore.save(legislators);
    }

    public Iterator<Industry> getIndustries() {
        Query<Industry> query = mongoStore.createQuery(Industry.class);
        return query.iterator();
    }

    public Iterable<Key<Industry>> saveIndustries(List<Industry> industries) {
        return mongoStore.save(industries);
    }

    public Industry industry(String industryId) {
        return mongoStore.find(Industry.class).filter("industryId", industryId).get();
    }

    public Iterator<CongCommittee> getCommittees() {
        Query<CongCommittee> query = mongoStore.createQuery(CongCommittee.class);
        return query.iterator();
    }

    public Iterable<Key<CongCommittee>> saveCommittees(List<CongCommittee> congCommittee) {
        return mongoStore.save(congCommittee);
    }

}
