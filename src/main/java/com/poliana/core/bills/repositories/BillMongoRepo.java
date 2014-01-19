package com.poliana.core.bills.repositories;

import com.poliana.core.bills.entities.Bill;
import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author David Gilmore
 * @date 11/17/13
 */
@Repository
public class BillMongoRepo {

    private Datastore mongoStore;

    private static final Logger logger = Logger.getLogger(BillMongoRepo.class);


    public Bill getBill(String billId) {

        Query<Bill> query = mongoStore.find(Bill.class);

        query.criteria("billId").equal(billId);

        return query.get();
    }

    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
