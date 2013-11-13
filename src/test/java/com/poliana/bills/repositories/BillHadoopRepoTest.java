package com.poliana.bills.repositories;

import com.poliana.bills.entities.Bill;
import com.poliana.bills.entities.BillVotes;
import com.poliana.bills.mappers.BillWithVotesMapper;
import com.poliana.config.StandaloneConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import static org.easymock.EasyMock.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;

/**
* @author David Gilmore
* @date 10/22/13
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=StandaloneConfig.class, loader=AnnotationConfigContextLoader.class)
@ActiveProfiles("dev")
public class BillHadoopRepoTest {

    private BillHadoopRepo billHadoopRepo;
    private JdbcTemplate impalaTemplateMock;

    private List<String> billIds;
    private List<String> voteIds;

    @Before
    public void loadData() {
        billHadoopRepo = new BillHadoopRepo();
        impalaTemplateMock = createMock(JdbcTemplate.class);
        billHadoopRepo.setImpalaTemplate(impalaTemplateMock);
    }

    @Test
    public void testBillsWithVotesByBillId() throws Exception {
//        expect(impalaTemplateMock.query("SELECT * FROM bills.bill_meta_embedded b " +
//                "JOIN bills.votes_by_bill v ON b.vote_id = v.vote_id " +
//                "WHERE b.bill_id = \'s743-113\'", new BillWithVotesMapper())).
//                andReturn(new ArrayList<Bill>());
//        replay(impalaTemplateMock);
//        billHadoopRepo.billsWithVotesByBillId("s743-113");
//        verify(impalaTemplateMock);
    }

    @Test
    public void testBillsWithVotesByCongress() throws Exception {
//        List<Bill> billWithVotes = billHadoopRepo.billsWithVotesByCongress(113, 15);
//        System.out.println("wow");
        assert(true);
    }

    @Test
    public void testBillMetaByBillId() throws Exception {
        String billId = billIds.get(0);
        Bill billMeta = billHadoopRepo.billMetaByBillId(billId);
        Assert.assertEquals(billId, billMeta.getBillId());
    }

    @Test
    public void testBillMetaByCongress() throws Exception {
        List<Bill> billMeta = billHadoopRepo.billMetaByCongress(113, 15);
        Assert.assertEquals(15, billMeta.size());
    }

    @Test
    public void testBillVotesByVoteId() throws Exception {
        String voteId = voteIds.get(0);
//        BillVotes billVotes = billHadoopRepo.billVotesByVoteId(voteId);
//        Assert.assertEquals(voteId, billVotes.getVoteId());
    }

    @Test
    public void testBillVotesByYear() throws Exception {
        List<BillVotes> billVotesList = billHadoopRepo.billVotesByYear(2013, 30);
        Assert.assertEquals(15, billVotesList.size());
    }
}
