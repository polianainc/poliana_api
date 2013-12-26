package com.poliana.core.legislators;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

/**
 * @author David Gilmore
 * @date 12/26/13
 */
@Repository
public class LegislatorsRepo {

    @Autowired
    private JdbcTemplate impalaTemplate;

    @Autowired
    private Datastore mongoStore;

    /**
     *
     * @return
     */
    public Iterable<Key<Legislator>> loadLegislatorsToMongo() {
        List<Legislator> legislators = getAllLegistlators();
        return saveLegislators(legislators);
    }

    /**
     *
     * @return
     */
    public List<Legislator> getAllLegistlators() {
        String query = "SELECT * FROM entities.legislators_flat_terms";
        return impalaTemplate.query(query, new LegislatorMapper());
    }

    /**
     *
     * @param condition
     * @return
     */
    public List<Legislator> getAllLegistlators(String condition) {
        String where = " WHERE " + condition;
        String query = "SELECT * FROM entities.legislators_flat_terms" + where;
        return impalaTemplate.query(query, new LegislatorMapper());
    }

    /**
     *
     * @param lisId
     * @return
     */
    public List<Legislator> legislatorByLisId(String lisId) {
        String query = "SELECT * FROM entities.legislators_flat_terms " +
                "WHERE lis_id = \'" + lisId +"\'";

        return impalaTemplate.query(query, new LegislatorMapper());
    }

    /**
     *
     * @return
     */
    public Iterator<Legislator> allLegislatorTerms() {
        Query<Legislator> query =
                mongoStore.createQuery(Legislator.class);
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
     *
     * @param chamber
     * @param timestampBegin
     * @param timestampEnd
     * @return
     */
    public Iterator<Legislator> getLegislators(String chamber, int timestampBegin, int timestampEnd) {
        Query<Legislator> query = mongoStore.find(Legislator.class);
        query.and(
                query.criteria("termType").contains(chamber),
                query.criteria("beginTimestamp").lessThan(timestampEnd),
                query.criteria("endTimestamp").greaterThan(timestampBegin)
        );
        return query.iterator();
    }
}
