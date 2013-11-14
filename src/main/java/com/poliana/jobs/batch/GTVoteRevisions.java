package com.poliana.jobs.batch;

import com.poliana.bills.entities.Vote;
import com.poliana.bills.entities.VoteGT.VoteGT;
import com.poliana.bills.entities.VoteGT.Voter;
import com.poliana.bills.entities.VoteGT.Voters;
import com.poliana.entities.entities.Legislator;

import java.util.LinkedList;
import java.util.List;

/**
 * @author David Gilmore
 * @date 11/13/13
 */
public class GTVoteRevisions {

   public void processVotes(List<VoteGT> votesGt) {
       for (VoteGT voteGt: votesGt) {
           Vote vote = new Vote();

           vote.setVoteId(voteGt.getVoteId());
           vote.setCategory(voteGt.getCategory());
           vote.setCongress(voteGt.getCongress());
//           vote.setDate(voteGt.getDate()); TODO: unix timestamp
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

           Voters votersGt = voteGt.getVoters();
           List<Legislator> yeas = gtVoterToLegislator(votersGt.getYea());
           List<Legislator> nays = gtVoterToLegislator(votersGt.getNay());
           List<Legislator> notVoting = gtVoterToLegislator(votersGt.getNotVoting());
           List<Legislator> present = gtVoterToLegislator(votersGt.getPresent());

           vote.setYeaTotal(yeas.size());
           vote.setNayTotal(nays.size());
           vote.setNotVotingTotal(notVoting.size());
           vote.setPresentTotal(present.size());


       }
   }

   public List<Legislator> gtVoterToLegislator(List<Voter> votersGt) {
       List<Legislator> legislators = new LinkedList<>();
//     TODO find legislator by time and id
       return legislators;
   }
}
