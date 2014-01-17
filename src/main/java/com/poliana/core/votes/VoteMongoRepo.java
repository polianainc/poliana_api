package com.poliana.core.votes;

import com.poliana.core.votes.entities.Vote;
import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author David Gilmore
 * @date 12/26/13
 */
@Repository
public class VoteMongoRepo {

    private Datastore mongoStore;

    private static final Logger logger = Logger.getLogger(VoteMongoRepo.class);


    /**
     * Get a vote by its vote id and congressional cycle
     * @param voteId
     * @return
     */
    public Vote getVote(String voteId) {

        Query<Vote> query = mongoStore.find(Vote.class);

        query.and(
                query.criteria("voteId").equal(voteId));

        return query.get();
    }


    @Autowired
    public void setMongoStore(Datastore mongoStore) {
        this.mongoStore = mongoStore;
    }
}
