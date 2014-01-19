package com.poliana.core.bills.repositories;

import com.poliana.config.ApplicationConfig;
import com.poliana.core.bills.entities.*;
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
        // The indices of nested data were chosen by consulting the data for the most populated objects

        Bill bill = billMongoRepo.getBill("s743-113");

        assertNotNull(bill);

        assertNotNull(bill.getId());
        assertEquals("s743-113", bill.getBillId());
        assertEquals("s", bill.getBillType());
        assertEquals(new Integer(113), bill.getCongress());
        assertNotNull(bill.getIntroducedAt());
        assertEquals(new Integer(743), bill.getNumber());
        assertNotNull(bill.getOfficialTitle());
        assertNotNull(bill.getShortTitle());
        assertNotNull(bill.getStatus());
        assertNotNull(bill.getStatusAt());
        assertNotNull(bill.getSubjects());
        assertNotNull(bill.getSubjectsTopTerm());
        assertNotNull(bill.getUpdatedAt());

        assertNotNull(bill.getActions());

        Action action = bill.getActions().get(1);
        assertNotNull(action.getActedAt());
        assertNotNull(action.getCalendar());
        assertNotNull(action.getNumber());
        assertNotNull(action.getStatus());
        assertNotNull(action.getText());
        assertNotNull(action.getType());
        assertNotNull(action.getUnder());

        action = bill.getActions().get(2);
        Reference reference = action.getReferences().get(0);

        assertNotNull(reference.getReference());
        assertNotNull(reference.getType());

        BillAmendmentRef amendmentRef = bill.getAmendments().get(0);
        assertNotNull(amendmentRef.getAmendmentId());
        assertNotNull(amendmentRef.getAmendmentType());
        assertNotNull(amendmentRef.getChamber());
        assertNotNull(amendmentRef.getNumber());

        assertNotNull(bill.getCommittees());

        Committee committee = bill.getCommittees().get(1);
        assertNotNull(committee.getActivity());
        assertNotNull(committee.getCommittee());
        assertNotNull(committee.getCommitteeId());
        assertNotNull(committee.getSubcommittee());
        assertNotNull(committee.getSubcommitteeId());

        assertNotNull(bill.getCosponsors());

        Sponsor cosponsor = bill.getCosponsors().get(0);
        assertNotNull(cosponsor.getDistrict());
        assertNotNull(cosponsor.getName());
        assertNotNull(cosponsor.getSponsoredAt());
        assertNotNull(cosponsor.getState());
        assertNotNull(cosponsor.getThomasId());
        assertNotNull(cosponsor.getTitle());

        assertNotNull(bill.getHistory());

        BillHistory history = bill.getHistory();
        assertNotNull(history.getActive());
        assertNotNull(history.getActiveAt());
        assertNotNull(history.getAwaitingSignature());
        assertNotNull(history.getEnacted());
        assertNotNull(history.getSenatePassageResult());
        assertNotNull(history.getSenatePassageResultAt());
        assertNotNull(history.getVetoed());

        assertNotNull(bill.getBillsRelated());

        BillsRelated related = bill.getBillsRelated().get(0);
        assertNotNull(related.getBillId());
        assertNotNull(related.getReason());
        assertNotNull(related.getType());

        assertNotNull(bill.getSponsor());

        Sponsor sponsor = bill.getSponsor();
        assertNotNull(sponsor.getName());
        assertNotNull(sponsor.getState());
        assertNotNull(sponsor.getThomasId());
        assertNotNull(sponsor.getTitle());
        assertNotNull(sponsor.getType());

        assertNotNull(bill.getSummary());

        BillSummary summary = bill.getSummary();
        assertNotNull(summary.getAs());
        assertNotNull(summary.getDate());
        assertNotNull(summary.getText());

        assertNotNull(bill.getTitles());

        BillTitle title = bill.getTitles().get(0);
        assertNotNull(title.getAs());
        assertNotNull(title.getForPortion());
        assertNotNull(title.getTitle());
        assertNotNull(title.getType());
    }

    @Autowired
    public void setBillMongoRepo(BillMongoRepo billMongoRepo) {
        this.billMongoRepo = billMongoRepo;
    }
}
