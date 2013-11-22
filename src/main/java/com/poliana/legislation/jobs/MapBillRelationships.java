package com.poliana.legislation.jobs;

import com.poliana.legislation.entities.deprecated.BillPojo;
import com.poliana.legislation.entities.bills.Bill;
import com.poliana.legislation.entities.votes.BillRef;
import com.poliana.legislation.entities.govtrack.votes.VoteGt;
import com.poliana.legislation.entities.votes.Vote;
import com.poliana.legislation.entities.govtrack.votes.Voter;
import com.poliana.legislation.entities.govtrack.votes.Voters;
import com.poliana.legislation.repositories.BillMongoRepo;
import com.poliana.entities.entities.Legislator;
import com.poliana.entities.services.LegislatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author David Gilmore
 * @date 11/13/13
 */
@Component
public class MapBillRelationships {

    @Autowired
    private LegislatorService legislatorService;
    @Autowired
    private BillMongoRepo billMongoRepo;


    public List<Vote> processGovtrackVotesByCongress(int congress) {

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
                votes.add(vote);
            }

            return votes;
        } finally {
            System.out.println("AHHH");
        }

    }

    public Vote processVote(VoteGt voteGt) {

        Vote vote = new Vote();

        System.out.println("Processing: " + voteGt.getVoteId());
        vote.setVoteId(voteGt.getVoteId());
        vote.setCategory(voteGt.getCategory());
        vote.setCongress(voteGt.getCongress());

        int timeStamp = getTimestamp(voteGt.getDate());
        vote.setDate(timeStamp);
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
        cal.setTimeInMillis((long) timeStamp * 1000L);

        vote.setYear(cal.get(Calendar.YEAR));
        vote.setMonth(cal.get(Calendar.MONTH+1));

        Voters votersGt = voteGt.getVotes();
        List<Legislator> yeas = gtVoterToLegislator(votersGt.getYea(), timeStamp);
        List<Legislator> nays = gtVoterToLegislator(votersGt.getNay(), timeStamp);
        List<Legislator> notVoting = gtVoterToLegislator(votersGt.getNotVoting(), timeStamp);
        List<Legislator> present = gtVoterToLegislator(votersGt.getPresent(), timeStamp);

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

    public List<Bill> processBills(List<BillPojo> bills) {
        List<Bill> mappedBills = new LinkedList<>();
        int billIndex = 0;
        for (BillPojo bill : bills) {
            Bill newBill = new Bill();
            System.out.println("Processing bill: " +  ++billIndex + " " + bill.getBillId());

            newBill.setBillId(bill.getBillId());

            newBill.setOfficialTitle(bill.getOfficialTitle());
            newBill.setPopularTitle(bill.getPopularTitle());
            newBill.setShortTitle(bill.getShortTitle());

            Legislator sponsor =
                    legislatorService.legislatorByIdTimestamp(bill.getSponsorId(), bill.getIntroducedAt());
            newBill.setSponsor(sponsor);

            List<Legislator> legislators = new LinkedList<>();
            for (String cosponsor: bill.getCosponsorIds()) {
                Legislator legislator =
                         legislatorService.legislatorByIdTimestamp(cosponsor, bill.getIntroducedAt());
                 legislators.add(legislator);
            }
            newBill.setCosponsors(legislators);

            newBill.setTopSubject(bill.getTopSubject());
            newBill.setSubjects(bill.getSubjects());
            newBill.setIntroducedAt(bill.getIntroducedAt());
            newBill.setStatus(bill.getStatus());
            newBill.setStatusAt(bill.getStatusAt());
            newBill.setCongress(bill.getCongress());
            newBill.setBillType(bill.getBillType());
            newBill.setYear(bill.getYear());
            newBill.setMonth(bill.getMonth());

            mappedBills.add(newBill);
        }
        return mappedBills;
    }

    public List<Legislator> bioguideToLegislator(List<String> bioguideIds, int timeStamp) {

        List<Legislator> legislators = new LinkedList<>();

        try{
            for (String bioguideId: bioguideIds) {
                Legislator legislator =
                        legislatorService.legislatorByIdTimestamp(bioguideId, timeStamp);
                legislators.add(legislator);
            }
        }
        catch (NullPointerException e) {}

        return legislators;
    }

    public List<Legislator> gtVoterToLegislator(List<Voter> votersGt, int timeStamp) {
        List<Legislator> legislators = new LinkedList<>();

        try {
            for (Voter voter : votersGt) {
                Legislator legislator =
                        legislatorService.legislatorByIdTimestamp(voter.getPoliticianId(), timeStamp);
                legislators.add(legislator);
            }
        }
        catch (NullPointerException e) {}

        return legislators;
    }

    public int getTimestamp(String dateString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            Date date = formatter.parse(dateString);
            long time = date.getTime();
            return (int) (time/1000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
