package com.poliana.core.legislators;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

/**
 * @author David Gilmore
 * @date 12/26/13
 */
@Repository
public class LegislatorMongoRepo {

    @Autowired
    Environment env;

    private Datastore mongoStore;

    private static final Logger logger = Logger.getLogger(LegislatorMongoRepo.class);


    /**
     * Fetch all legislator objects from Mongo for every term he or she has served.
     * @return
     */
    public Iterator<Legislator> getAllLegislators() {
        Query<Legislator> query =
                mongoStore.createQuery(Legislator.class);

        query.disableCursorTimeout();

        return query.iterator();
    }

    /**
     *
     * @param legislatorId
     * @return
     */
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

    /**
     *
     * @param legislators
     * @return
     */
    public Iterable<Key<Legislator>> saveLegislators(List<Legislator> legislators) {

        return mongoStore.save(legislators);
    }

    /**
     * Get all legislator term objects for a given chamber and time range.
     *
     * b. some legislator's term beginning timestamp
     * e. some legislator's term ending timestamp
     * B. the inputted beginning timestamp
     * E. the inputted ending timestamp
     *
     * KB: (b < e) ^ (B < E)
     *
     * P. b < B
     * Q. b < E
     * R. e < B
     * S. e < E
     *
     * We need PQ~RS + ~PQ~R~S + PQ~R~S:
     * PQ~RS + ~PQ~R~S + PQ~R~S
     * Q~R (P~S + ~P~S + P~S)
     * Q~R ~(~PS)
     * Q~R (P + ~S)
     *
     * @param chamber
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public Iterator<Legislator> getLegislators(String chamber, long beginTimestamp, long endTimestamp) {

        //If the timestamps are backward, swap 'em
        if (endTimestamp < beginTimestamp) {
            long tmp = endTimestamp;
            endTimestamp = beginTimestamp;
            beginTimestamp = tmp;
        }

        //Contruct a Legislator query
        Query<Legislator> query = mongoStore.find(Legislator.class);

        //Add our logic critieria
        query.and(
            query.criteria("termType").contains(chamber),
            query.and(
                query.and(
                    query.criteria("beginTimestamp").lessThan(endTimestamp),
                    query.criteria("endTimestamp").greaterThan(beginTimestamp)
                ),
                query.or(
                    query.criteria("beginTimestamp").lessThan(beginTimestamp),
                    query.criteria("endTimestamp").greaterThan(endTimestamp)
                )
            )
        );

        return query.iterator();
    }

    /**
     * Get all legislator term objects for a given time range. Note logic in chamber specific method comments
     *
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public Iterator<Legislator> getLegislators(long beginTimestamp, long endTimestamp) {

        //If the timestamps are backward, swap 'em
        if (endTimestamp < beginTimestamp) {
            long tmp = endTimestamp;
            endTimestamp = beginTimestamp;
            beginTimestamp = tmp;
        }

        //Contruct a Legislator query
        Query<Legislator> query = mongoStore.find(Legislator.class);

        //Add our logic critieria
        query.and(
                query.and(
                        query.criteria("beginTimestamp").lessThan(endTimestamp),
                        query.criteria("endTimestamp").greaterThan(beginTimestamp)
                ),
                query.or(
                        query.criteria("beginTimestamp").lessThan(beginTimestamp),
                        query.criteria("endTimestamp").greaterThan(endTimestamp)
                )

        );

        return query.iterator();
    }

    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
