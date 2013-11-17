package com.poliana.campaignFinance.repositories;

import com.poliana.bills.models.Bill;
import com.poliana.entities.models.Industry;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author David Gilmore
 * @date 11/15/13
 */
public interface IndustryCRUDRepo extends MongoRepository<Industry, String> {}
