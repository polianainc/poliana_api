package com.poliana.legislation.jobs;

import com.poliana.entities.entities.Legislator;
import com.poliana.entities.services.LegislatorService;
import com.poliana.legislation.entities.bills.Bill;
import com.poliana.legislation.entities.bills.BillHistory;
import com.poliana.legislation.entities.govtrack.bills.BillGt;
import com.poliana.legislation.entities.govtrack.bills.BillHistoryGt;
import com.poliana.legislation.entities.govtrack.bills.Sponsor;
import com.poliana.legislation.services.LegislationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
@Component
public class IngestGtBills {

    @Autowired
    private LegislatorService legislatorService;

    @Autowired
    private LegislationService legislationService;

    public List<Bill> processBills(List<BillGt> bills) {
        List<Bill> mappedBills = new LinkedList<>();

        int billIndex = 0;
        for (BillGt billGt : bills) {
            Bill bill = new Bill();

            bill.setBillId(billGt.getBillId());

            bill.setOfficialTitle(billGt.getOfficialTitle());
            bill.setPopularTitle(billGt.getPopularTitle());
            bill.setShortTitle(billGt.getShortTitle());
            bill.setTitles(billGt.getTitles());
            bill.setTopSubject(billGt.getSubjectsTopTerm());
            bill.setSubjects(billGt.getSubjects());
            bill.setSummary(billGt.getSummary());

            int introducedAt = legislationService.getTimestamp(billGt.getIntroducedAt());
            Legislator sponsor =
                    legislatorService.legislatorByIdTimestamp(billGt.getSponsor().getId(), introducedAt);
            bill.setSponsor(sponsor);

            List<Legislator> legislators = new LinkedList<>();
            for (Sponsor cosponsor: billGt.getCosponsors()) {
                Legislator legislator =
                        legislatorService.legislatorByIdTimestamp(cosponsor.getId(), introducedAt);
                legislators.add(legislator);
            }
            bill.setCosponsors(legislators);

            bill.setIntroducedAt(introducedAt);
            bill.setStatus(billGt.getStatus());
            bill.setStatusAt(legislationService.getTimestamp(billGt.getStatusAt()));
            bill.setCongress(billGt.getCongress());
            bill.setBillType(billGt.getBillType());

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis((long) introducedAt * 1000L);

            bill.setYear(cal.get(Calendar.YEAR));
            bill.setMonth(cal.get(Calendar.MONTH+1));

            bill.setActions(billGt.getActions());
            bill.setAmendments(billGt.getAmendments());
            bill.setHistory(gtHistoryConvert(billGt.getHistory()));


            mappedBills.add(bill);
        }
        return mappedBills;
    }

    private BillHistory gtHistoryConvert(BillHistoryGt billHistoryGt) {
        BillHistory billHistory = new BillHistory();

        billHistory.setActive(billHistoryGt.isActive());
        billHistory.setActiveAt(legislationService.getTimestamp(billHistoryGt.getActiveAt()));
        billHistory.setAwaitingSignature(billHistoryGt.isAwaitingSignature());
        billHistory.setAwaitingSignatureSince(legislationService.getTimestamp(billHistoryGt.getAwaitingSignatureSince()));
        billHistory.setEnacted(billHistoryGt.isEnacted());
        billHistory.setEnactedAt(legislationService.getTimestamp(billHistoryGt.getEnactedAt()));
        billHistory.setHousePassageResult(billHistoryGt.getHousePassageResult());
        billHistory.setHousePassageResultAt(legislationService.getTimestamp(billHistoryGt.getHousePassageResultAt()));
        billHistory.setSenatePassageResult(billHistoryGt.getSenatePassageResult());
        billHistory.setSenatePassageResultAt(legislationService.getTimestamp(billHistoryGt.getSenatePassageResultAt()));
        billHistory.setHouseOverrideResult(billHistoryGt.getHouseOverrideResult());
        billHistory.setSenateOverrideResult(billHistoryGt.getSenateOverrideResult());
        billHistory.setVetoed(billHistory.isVetoed());
        billHistory.setVetoedAt(legislationService.getTimestamp(billHistoryGt.getVetoedAt()));

        return billHistory;
    }

}
