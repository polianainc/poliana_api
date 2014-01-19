package com.poliana.core.bills;

import com.poliana.core.bills.entities.Bill;
import com.poliana.core.bills.repositories.BillMongoRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author David Gilmore
 * @date 1/17/14
 */
@Service
public class BillService {

    private BillMongoRepo billMongoRepo;

    private static final Logger logger = Logger.getLogger(BillService.class);

    /**
     * Get a bill by its id and congressional cycle
     * @param billId
     * @param congress
     * @return
     */
    public Bill getBill(String billId, int congress) {

        billId += "-" + congress;

        return billMongoRepo.getBill(billId);
    }

    @Autowired
    public void setBillMongoRepo(BillMongoRepo billMongoRepo) {
        this.billMongoRepo = billMongoRepo;
    }
}
