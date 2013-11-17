package com.poliana.bills.repositories;

import com.poliana.bills.models.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author David Gilmore
 * @date 11/14/13
 */
public interface VotesCRUDRepo extends MongoRepository<Vote, String> {
    Vote findByVoteId(String voteId);
}
