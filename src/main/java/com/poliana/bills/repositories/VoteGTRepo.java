package com.poliana.bills.repositories;

import com.poliana.bills.entities.VoteGT.VoteGT;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author David Gilmore
 * @date 11/13/13
 */
public interface VoteGTRepo extends MongoRepository<VoteGT, String> {
    List<VoteGT> findAllByCongress(int congress);
}
