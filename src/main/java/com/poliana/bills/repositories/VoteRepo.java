package com.poliana.bills.repositories;

import com.poliana.bills.entities.VoteGT.VoteGT;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author David Gilmore
 * @date 11/13/13
 */
public interface VoteRepo extends MongoRepository<VoteGT, String> {}
