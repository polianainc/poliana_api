package com.poliana.bills.services;

import com.poliana.bills.entities.BillAction;
import com.poliana.bills.repositories.BillHadoopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {
    @Autowired
    private BillHadoopRepo billHadoopRepo;

    public List<BillAction> billActions(String billId) {
        return billHadoopRepo.billActions(billId);
    }

}
