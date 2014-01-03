package com.poliana.core.bills.jobs;

import com.google.code.morphia.Key;
import com.google.code.morphia.mapping.MappingException;
import com.poliana.core.time.TimeService;
import com.poliana.core.bills.govtrack.votes.Voter;
import com.poliana.core.legislators.Legislator;
import com.poliana.core.legislators.LegislatorService;
import com.poliana.core.bills.entities.Action;
import com.poliana.core.bills.entities.Bill;
import com.poliana.core.bills.entities.BillHistory;
import com.poliana.core.bills.entities.BillSummary;
import com.poliana.core.bills.govtrack.bills.*;
import com.poliana.core.bills.govtrack.votes.VoteGt;
import com.poliana.core.bills.govtrack.votes.Voters;
import com.poliana.core.votes.entities.BillRef;
import com.poliana.core.votes.entities.Vote;
import com.poliana.core.bills.repositories.BillMongoRepo;
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

    public void processBills(int congress) {

        Iterator<BillGt> billGtIterator = billMongoRepo.govtrackBillsByCongress(congress);

        int index = 0;
        while (billGtIterator.hasNext()) {
            BillGt billGt = billGtIterator.next();

            Bill bill = new Bill();

            bill.setBillId(billGt.getBillId());
            if (billGt.getBillId().equals("hr13-112"))
                System.out.println(++index + ": " + bill.getBillId());

            System.out.println(++index + ": " + bill.getBillId());

            bill.setOfficialTitle(billGt.getOfficialTitle());
            bill.setPopularTitle(billGt.getPopularTitle());
            bill.setShortTitle(billGt.getShortTitle());
            bill.setTitles(billGt.getTitles());
            bill.setTopSubject(billGt.getSubjectsTopTerm());
            bill.setSubjects(billGt.getSubjects());
            bill.setSummary(gtSummaryConvert(billGt.getSummary()));


            long introducedAt = TimeService.getTimestamp(billGt.getIntroducedAt());
            Legislator sponsor =
                    legislatorService.getLegislatorByIdTimestamp(billGt.getSponsor().getThomasId(), introducedAt);
            bill.setSponsor(sponsor.getId());

            List<String> legislators = new LinkedList<>();
            if (billGt.getCosponsors() != null) {
                for (Sponsor cosponsor: billGt.getCosponsors()) {
                    Legislator legislator =
                            legislatorService.getLegislatorByIdTimestamp(cosponsor.getThomasId(), introducedAt);
                    if (legislator != null) {
                        legislators.add(legislator.getId());
                    }
                }
                bill.setCosponsors(legislators);
            }

            bill.setIntroducedAt(billGt.getIntroducedAt());
            bill.setIntroducedTs(introducedAt);
            bill.setStatus(billGt.getStatus());
            if (billGt.getStatusAt() != null) {
                bill.setStatusTs(TimeService.getTimestamp(billGt.getStatusAt()));
                bill.setStatusAt(billGt.getStatusAt());
            }

            bill.setCongress(Integer.parseInt(billGt.getCongress()));
            bill.setBillType(billGt.getBillType());

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis( ((long)introducedAt*1000L) );

            bill.setYear(cal.get(Calendar.YEAR));
            bill.setMonth(cal.get(Calendar.MONTH+1));

            bill.setActions(gtActionsConvert(billGt.getActionGts()));
            bill.setAmendments(billGt.getAmendments());
            bill.setHistory(gtHistoryConvert(billGt.getHistory()));
            bill.setUpdatedAt(billGt.getUpdatedAt());
            bill.setUpdatedTs(TimeService.getTimestamp(billGt.getUpdatedAt()));

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

        long timestamp = TimeService.getTimestamp(voteGt.getDate());
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
        cal.setTimeInMillis(((long)timestamp*1000L));

        vote.setYear(cal.get(Calendar.YEAR));
        vote.setMonth(cal.get(Calendar.MONTH+1));

        Voters votersGt = voteGt.getVotes();
        List<String> yeas = gtVotersToLegislators(votersGt.getYea(), timestamp);
        List<String> nays = gtVotersToLegislators(votersGt.getNay(), timestamp);
        List<String> notVoting = gtVotersToLegislators(votersGt.getNotVoting(), timestamp);
        List<String> present = gtVotersToLegislators(votersGt.getPresent(), timestamp);

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

    protected BillHistory gtHistoryConvert(BillHistoryGt billHistoryGt) {
        BillHistory billHistory = new BillHistory();

        billHistory.setActive(billHistoryGt.isActive());
        billHistory.setActiveTs(TimeService.getTimestamp(billHistoryGt.getActiveAt()));
        billHistory.setActiveAt(billHistoryGt.getActiveAt());
        billHistory.setAwaitingSignature(billHistoryGt.isAwaitingSignature());
        billHistory.setAwaitingSignatureSince(TimeService.getTimestamp(billHistoryGt.getAwaitingSignatureSince()));
        billHistory.setEnacted(billHistoryGt.isEnacted());
        billHistory.setEnactedTs(TimeService.getTimestamp(billHistoryGt.getEnactedAt()));
        billHistory.setEnactedAt(billHistoryGt.getEnactedAt());
        billHistory.setHousePassageResult(billHistoryGt.getHousePassageResult());
        billHistory.setHousePassageResultTs(TimeService.getTimestamp(billHistoryGt.getHousePassageResultAt()));
        billHistory.setHousePassageResultAt(billHistoryGt.getHousePassageResultAt());
        billHistory.setSenatePassageResult(billHistoryGt.getSenatePassageResult());
        billHistory.setSenatePassageResultTs(TimeService.getTimestamp(billHistoryGt.getSenatePassageResultAt()));
        billHistory.setSenatePassageResultAt(billHistoryGt.getSenatePassageResultAt());
        billHistory.setHouseOverrideResult(billHistoryGt.getHouseOverrideResult());
        billHistory.setSenateOverrideResult(billHistoryGt.getSenateOverrideResult());
        billHistory.setVetoed(billHistory.isVetoed());
        billHistory.setVetoedTs(TimeService.getTimestamp(billHistoryGt.getVetoedAt()));
        billHistory.setVetoedAt(billHistoryGt.getVetoedAt());

        return billHistory;
    }

    protected BillSummary gtSummaryConvert(BillSummaryGt billSummaryGt) {
        BillSummary billSummary = new BillSummary();

        if (billSummaryGt != null) {
            if (billSummaryGt.getAs() != null)
                billSummary.setAs(billSummaryGt.getAs());
            if (billSummaryGt.getDate() != null)
                billSummary.setDateString(billSummaryGt.getDate());
                billSummary.setDateTs(TimeService.getTimestamp(billSummaryGt.getDate()));
            billSummary.setText(billSummaryGt.getText());
        }

        return billSummary;

    }

    protected List<Action> gtActionsConvert(List<ActionGt> actionGts) {
        List<Action> actions = new LinkedList<>();
        if (actionGts != null) {

            for (ActionGt actionGt: actionGts) {

                Action action = new Action();

                if (actionGt.getActedAt() != null)
                    action.setActedTs(TimeService.getTimestamp(actionGt.getActedAt()));
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

    protected List<String> gtVotersToLegislators(List<Voter> votersGt, long timestamp) {
        List<String> legislators = new LinkedList<>();

        try {
            for (Voter voter : votersGt) {
                Legislator legislator =
                        legislatorService.getLegislatorByIdTimestamp(voter.getPoliticianId(), timestamp);
                legislators.add(legislator.getId());
            }
        }
        catch (NullPointerException e) {}

        return legislators;
    }
}
