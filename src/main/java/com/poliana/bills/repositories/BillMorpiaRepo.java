package com.poliana.bills.repositories;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateResults;
import com.poliana.bills.models.VoteMorphia;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author David Gilmore
 * @date 11/17/13
 */
public class BillMorpiaRepo {

    @Autowired
    private Datastore mongoStore;

    public VoteMorphia voteByVoteId(String voteId) {
        Query<VoteMorphia> voteQuery =
                mongoStore.find(VoteMorphia.class,"voteId",voteId);
        return voteQuery.get();
    }

//    public UpdateResults<VoteMorphia> update
}
