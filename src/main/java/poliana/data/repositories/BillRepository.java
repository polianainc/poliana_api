package poliana.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import poliana.data.models.bill.Bill;

import java.util.List;

public interface BillRepository extends MongoRepository<Bill, Long> {
    List<Bill> findAll();
    Bill save(Bill bill);
}
