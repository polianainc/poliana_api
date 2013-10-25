package com.poliana.bills.repositories;

import com.poliana.bills.entities.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BillCRUDRepo extends MongoRepository<Bill, String> {}