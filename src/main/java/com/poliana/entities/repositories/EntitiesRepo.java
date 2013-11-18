package com.poliana.entities.repositories;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.query.Query;
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
public class EntitiesRepo {
    @Autowired
    private Datastore mongoStore;

    public Iterator<Legislator> allLegislatorTerms() {
        Query<Legislator> query =
                mongoStore.createQuery(Legislator.class);
        return query.iterator();
    }

    public Iterable<Key<Legislator>> saveLegislators(List<Legislator> legislators) {
        return mongoStore.save(legislators);
    }

    public Iterator<Industry> allIndustries() {
        Query<Industry> query = mongoStore.createQuery(Industry.class);
        return query.iterator();
    }
}
