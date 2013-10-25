package com.poliana.bills.repositories;

import com.poliana.bills.entities.BillVotes;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BillVotesRepo extends MongoRepository<BillVotes, String> {}