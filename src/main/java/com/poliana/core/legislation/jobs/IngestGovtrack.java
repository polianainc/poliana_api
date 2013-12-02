package com.poliana.core.legislation.jobs;

import com.google.code.morphia.Key;
import com.google.code.morphia.mapping.MappingException;
import com.poliana.core.common.util.TimeUtils;
import com.poliana.core.entities.entities.Legislator;
import com.poliana.core.entities.services.LegislatorService;
import com.poliana.core.legislation.entities.bills.Action;
import com.poliana.core.legislation.entities.bills.Bill;
import com.poliana.core.legislation.entities.bills.BillHistory;
import com.poliana.core.legislation.entities.bills.BillSummary;
import com.poliana.core.legislation.entities.govtrack.bills.*;
import com.poliana.core.legislation.entities.govtrack.votes.VoteGt;
import com.poliana.core.legislation.entities.govtrack.votes.Voters;
import com.poliana.core.legislation.entities.votes.BillRef;
import com.poliana.core.legislation.entities.votes.Vote;
import com.poliana.core.legislation.repositories.BillMongoRepo;
import com.poliana.core.legislation.services.LegislationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
@Component
public class IngestGovtrack {
    @Autowired
    private BillMongoRepo billMongoRepo;

    @Autowired
    private LegislatorService legislatorService;

    @Autowired
    private LegislationService legislationService;

    public void processBills(int congress) {

        Iterator<BillGt> billGtIterator = billMongoRepo.govtrackBillsByCongress(congress);

        int index = 0;
        while (billGtIterator.hasNext()) {
            BillGt billGt = billGtIterator.next();

            Bill bill = new Bill();

            bill.setBillId(billGt.getBillId());

            System.out.println(++index + ": " + bill.getBillId());

            bill.setOfficialTitle(billGt.getOfficialTitle());
            bill.setPopularTitle(billGt.getPopularTitle());
            bill.setShortTitle(billGt.getShortTitle());
            bill.setTitles(billGt.getTitles());
            bill.setTopSubject(billGt.getSubjectsTopTerm());
            bill.setSubjects(billGt.getSubjects());
            bill.setSummary(gtSummaryConvert(billGt.getSummary()));

            int introducedAt = TimeUtils.getTimestamp(billGt.getIntroducedAt());
            Legislator sponsor =
                    legislatorService.legislatorByIdTimestamp(billGt.getSponsor().getThomasId(), introducedAt);
            bill.setSponsor(sponsor);

            List<Legislator> legislators = new LinkedList<>();
            if (billGt.getCosponsors() != null) {
                for (Sponsor cosponsor: billGt.getCosponsors()) {
                    Legislator legislator =
                            legislatorService.legislatorByIdTimestamp(cosponsor.getThomasId(), introducedAt);
                    if (legislator != null) {
                        legislators.add(legislator);
                    }
                }
                bill.setCosponsors(legislators);
            }


            bill.setIntroducedAt(introducedAt);
            bill.setStatus(billGt.getStatus());
            if (billGt.getStatusAt() != null) {
                bill.setStatusAt(TimeUtils.getTimestamp(billGt.getStatusAt()));
            }
            bill.setCongress(Integer.parseInt(billGt.getCongress()));
            bill.setBillType(billGt.getBillType());

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis((long) introducedAt * 1000L);

            bill.setYear(cal.get(Calendar.YEAR));
            bill.setMonth(cal.get(Calendar.MONTH+1));

            bill.setActions(gtActionsConvert(billGt.getActionGts()));
            bill.setAmendments(billGt.getAmendments());
            bill.setHistory(gtHistoryConvert(billGt.getHistory()));

            billMongoRepo.saveBill(bill);

        }

    }

    public void processGovtrackVotesByCongress(int congress) {

        try {

            Iterator<VoteGt> gtVotes = billMongoRepo.govtrackVotesByCongress(congress);

            List<Vote> votes = new LinkedList<>();

            while( gtVotes.hasNext()) {
                VoteGt gtVote = gtVotes.next();

                Vote vote = processVote(gtVote);

                if(vote.getBillInfo() != null) {

                    BillRef billRef = vote.getBillInfo();

                    String billId =
                            billRef.getType() + billRef.getNumber() + "-" + billRef.getCongress();

                    Bill bill = billMongoRepo.billByBillId(billId);

                    if(bill != null) {
                        vote.setBill(bill);
                        billMongoRepo.updateBill(bill, "vote_id", vote.getVoteId());
                    }
                }
                try {
                    Key<Vote> voteReturn = billMongoRepo.saveVote(vote);
                }
                catch (MappingException e) {
                    System.out.println(e);
                }
            }

        } finally {
        }

    }

    public Vote processVote(VoteGt voteGt) {

        Vote vote = new Vote();

        System.out.println("Processing: " + voteGt.getVoteId());
        if (voteGt.getVoteId().contains("h198")) {
            System.out.println("whoa");
        }

        vote.setVoteId(voteGt.getVoteId());
        vote.setCategory(voteGt.getCategory());
        vote.setCongress(voteGt.getCongress());

        int timestamp = TimeUtils.getTimestamp(voteGt.getDate());
        vote.setDate(timestamp);
        vote.setBillInfo(voteGt.getBill());
        vote.setAmendment(voteGt.getAmendment());
        vote.setNomination(voteGt.getNomination());
        vote.setNumber(voteGt.getNumber());
        vote.setQuestion(voteGt.getQuestion());
        vote.setRequires(voteGt.getRequires());
        vote.setResult(voteGt.getResult());
        vote.setResultText(voteGt.getResultText());
        vote.setSession(voteGt.getSession());
        vote.setSourceUrl(voteGt.getSourceUrl());
        vote.setSubject(voteGt.getSubject());
        vote.setType(voteGt.getType());
        vote.setUpdatedAt(voteGt.getUpdatedAt());
        vote.setChamber(voteGt.getChamber());

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis((long) timestamp * 1000L);

        vote.setYear(cal.get(Calendar.YEAR));
        vote.setMonth(cal.get(Calendar.MONTH+1));

        Voters votersGt = voteGt.getVotes();
        List<Legislator> yeas = legislationService.gtVotersToLegislators(votersGt.getYea(), timestamp);
        List<Legislator> nays = legislationService.gtVotersToLegislators(votersGt.getNay(), timestamp);
        List<Legislator> notVoting = legislationService.gtVotersToLegislators(votersGt.getNotVoting(), timestamp);
        List<Legislator> present = legislationService.gtVotersToLegislators(votersGt.getPresent(), timestamp);

        vote.setYeaTotal(yeas.size());
        vote.setNayTotal(nays.size());
        vote.setNotVotingTotal(notVoting.size());
        vote.setPresentTotal(present.size());

        vote.setYeas(yeas);
        vote.setNays(nays);
        vote.setNotVoting(notVoting);
        vote.setPresent(present);

        return vote;
    }

    private BillHistory gtHistoryConvert(BillHistoryGt billHistoryGt) {
        BillHistory billHistory = new BillHistory();

        billHistory.setActive(billHistoryGt.isActive());
        billHistory.setActiveAt(TimeUtils.getTimestamp(billHistoryGt.getActiveAt()));
        billHistory.setAwaitingSignature(billHistoryGt.isAwaitingSignature());
        billHistory.setAwaitingSignatureSince(TimeUtils.getTimestamp(billHistoryGt.getAwaitingSignatureSince()));
        billHistory.setEnacted(billHistoryGt.isEnacted());
        billHistory.setEnactedAt(TimeUtils.getTimestamp(billHistoryGt.getEnactedAt()));
        billHistory.setHousePassageResult(billHistoryGt.getHousePassageResult());
        billHistory.setHousePassageResultAt(TimeUtils.getTimestamp(billHistoryGt.getHousePassageResultAt()));
        billHistory.setSenatePassageResult(billHistoryGt.getSenatePassageResult());
        billHistory.setSenatePassageResultAt(TimeUtils.getTimestamp(billHistoryGt.getSenatePassageResultAt()));
        billHistory.setHouseOverrideResult(billHistoryGt.getHouseOverrideResult());
        billHistory.setSenateOverrideResult(billHistoryGt.getSenateOverrideResult());
        billHistory.setVetoed(billHistory.isVetoed());
        billHistory.setVetoedAt(TimeUtils.getTimestamp(billHistoryGt.getVetoedAt()));

        return billHistory;
    }

    private BillSummary gtSummaryConvert(BillSummaryGt billSummaryGt) {
        BillSummary billSummary = new BillSummary();

        if (billSummaryGt != null) {
            if (billSummaryGt.getAs() != null)
                billSummary.setAs(billSummaryGt.getAs());
            if (billSummaryGt.getDate() != null)
                billSummary.setDate(TimeUtils.getTimestamp(billSummaryGt.getDate()));
            billSummary.setText(billSummaryGt.getText());
        }

        return billSummary;

    }

    private List<Action> gtActionsConvert(List<ActionGt> actionGts) {
        List<Action> actions = new LinkedList<>();
        if (actionGts != null) {

            for (ActionGt actionGt: actionGts) {

                Action action = new Action();

                if (actionGt.getActedAt() != null)
                    action.setActedAt(TimeUtils.getTimestamp(actionGt.getActedAt()));
                action.setBillIds(actionGt.getBillIds());
                action.setReferences(actionGt.getReferences());
                action.setCalendar(actionGt.getCalendar());
                action.setText(actionGt.getText());
                action.setType(actionGt.getType());
                action.setRoll(actionGt.getRoll());
                action.setUnder(actionGt.getUnder());
                action.setNumber(Integer.parseInt(actionGt.getNumber()));
                action.setHow(actionGt.getHow());
                action.setResult(actionGt.getResult());
                action.setStatus(actionGt.getStatus());
                action.setSuspension(actionGt.getSuspension());
                action.setWhere(actionGt.getWhere());
                action.setVoteType(actionGt.getVoteType());

                actions.add(action);
            }
        }
        return actions;
    }
}
