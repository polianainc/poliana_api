package com.poliana.bills.repositories;

import com.poliana.bills.entities.Bill;
import com.poliana.bills.entities.BillVotes;
import com.poliana.config.StandaloneConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;
import java.util.List;

/**
* @author David Gilmore
* @date 10/22/13
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=StandaloneConfig.class, loader=AnnotationConfigContextLoader.class)
@ActiveProfiles("dev")
public class BillHadoopRepoTest {

    @Resource
    private BillHadoopRepo billHadoopRepo;

    @Test
    public void testBillsWithVotesByBillId() throws Exception {
        Bill billWithVotes = billHadoopRepo.billsWithVotesByBillId("hjres108-100");
        Assert.assertEquals("hjres108-100", billWithVotes.getBillId());
    }

    @Test
    public void testBillsWithVotesByCongress() throws Exception {
        List<Bill> billWithVotes = billHadoopRepo.billsWithVotesByCongress(100, 100);
        Assert.assertEquals(100, billWithVotes.size());
    }

    @Test
    public void testBillMetaByBillId() throws Exception {
        Bill billMeta = billHadoopRepo.billMetaByBillId("hconres10-100");
        Assert.assertEquals("hconres10-100", billMeta.getBillId());
    }

    @Test
    public void testBillMetaByCongress() throws Exception {
        List<Bill> billMeta = billHadoopRepo.billMetaByCongress(110, 100);
        Assert.assertEquals(100, billMeta.size());
    }

    @Test
    public void testBillVotesByBillId() throws Exception {
        BillVotes billVotes = billHadoopRepo.billVotesByBillId("s743-113");
        Assert.assertEquals(billVotes.getBillId(), "s743-113");
    }

    @Test
    public void testBillVotesByCongress() throws Exception {
        List<BillVotes> billVotesList = billHadoopRepo.billVotesByCongress(113, 10);
        Assert.assertEquals(10, billVotesList.size());
    }

    @Test
    public void testBillVotesByYear() throws Exception {
        List<BillVotes> billVotesList = billHadoopRepo.billVotesByYear(2012, 30);
        Assert.assertEquals(30, billVotesList.size());
    }

    @Test
    public void testBillActions() throws Exception {

    }
}
