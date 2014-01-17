package com.poliana.core.bills.repositories;

import com.poliana.config.ApplicationConfig;
import com.poliana.core.bills.entities.Bill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.*;

/**
 * @author David Gilmore
 * @date 1/17/14
 */
@Profile("integration_test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ApplicationConfig.class, loader=AnnotationConfigContextLoader.class)
public class BillMongoRepoTest {

    private BillMongoRepo billMongoRepo;

    @Test
    public void testGetBill() throws Exception {

        Bill bill = billMongoRepo.getBill("s743-113");

        assertNotNull(bill);
    }

    @Autowired
    public void setBillMongoRepo(BillMongoRepo billMongoRepo) {
        this.billMongoRepo = billMongoRepo;
    }
}
