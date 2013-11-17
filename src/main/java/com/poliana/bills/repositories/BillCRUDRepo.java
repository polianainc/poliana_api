package com.poliana.bills.repositories;

import com.poliana.bills.models.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BillCRUDRepo extends MongoRepository<Bill, String> {
//    Page<Bill> findAllByCongress(int congress, PageRequest page);
}