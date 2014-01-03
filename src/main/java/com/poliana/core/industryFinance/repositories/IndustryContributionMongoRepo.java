package com.poliana.core.industryFinance.repositories;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.poliana.core.industryFinance.entities.IndTimeRangeTotals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author David Gilmore
 * @date 11/23/13
 */
@Repository
public class IndustryContributionMongoRepo {

    private Datastore mongoStore;

    public Key<IndTimeRangeTotals> saveIndTimeRangeTotal(IndTimeRangeTotals indTimeRangeTotals) {
        return mongoStore.save(indTimeRangeTotals);
    }

    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
