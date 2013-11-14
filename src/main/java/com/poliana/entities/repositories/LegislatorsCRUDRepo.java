package com.poliana.entities.repositories;

import com.poliana.entities.entities.Legislator;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author David Gilmore
 * @date 11/13/13
 */
public interface LegislatorsCRUDRepo extends MongoRepository<Legislator, String> {
    List<Legislator> findByLisId(String lisId);
}