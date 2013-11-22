package com.poliana.legislation.jobs;

import com.poliana.entities.entities.Legislator;
import com.poliana.legislation.entities.bills.Bill;
import com.poliana.legislation.entities.govtrack.votes.VoteGt;
import com.poliana.legislation.entities.govtrack.votes.Voters;
import com.poliana.legislation.entities.votes.BillRef;
import com.poliana.legislation.entities.votes.Vote;
import com.poliana.legislation.repositories.BillMongoRepo;
import com.poliana.legislation.services.LegislationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
@Component
public class IngestGtVotes {

    @Autowired
    private BillMongoRepo billMongoRepo;

    @Autowired
    private LegislationService legislationService;

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

        int timeStamp = legislationService.getTimestamp(voteGt.getDate());
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
        List<Legislator> yeas = legislationService.gtVoterToLegislator(votersGt.getYea(), timeStamp);
        List<Legislator> nays = legislationService.gtVoterToLegislator(votersGt.getNay(), timeStamp);
        List<Legislator> notVoting = legislationService.gtVoterToLegislator(votersGt.getNotVoting(), timeStamp);
        List<Legislator> present = legislationService.gtVoterToLegislator(votersGt.getPresent(), timeStamp);

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

}
