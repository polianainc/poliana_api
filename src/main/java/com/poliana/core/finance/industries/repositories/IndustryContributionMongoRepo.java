package com.poliana.core.finance.industries.repositories;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.poliana.core.finance.industries.entities.IndTimeRangeTotals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author David Gilmore
 * @date 11/23/13
 */
@Repository
public class IndustryContributionMongoRepo {

    @Autowired
    private Datastore mongoStore;

    public Key<IndTimeRangeTotals> saveIndTimeRangeTotal(IndTimeRangeTotals indTimeRangeTotals) {
        return mongoStore.save(indTimeRangeTotals);
    }
}
