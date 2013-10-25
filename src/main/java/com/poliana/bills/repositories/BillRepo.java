package com.poliana.bills.repositories;

import com.poliana.bills.entities.BillVotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BillRepo {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveBillVotes(BillVotes billVotes) {
        mongoTemplate.save(billVotes, "bill_votes");
    }
}
