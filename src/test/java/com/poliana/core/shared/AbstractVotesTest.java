package com.poliana.core.shared;

import com.poliana.core.time.TimeService;
import com.poliana.core.votes.entities.Vote;
import com.poliana.core.votes.entities.Voter;
import com.poliana.core.votes.entities.Voters;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author graysoncarroll
 * @date 1/20/14
 */
public abstract class AbstractVotesTest {

   protected TimeService timeService;

   public AbstractVotesTest() {
       this.timeService = new TimeService();
   }

   protected Vote getVoteFixture(String voteId, Date date, int congress) {

       Vote vote = new Vote();

       vote.setDate(date);
       vote.setCongress(congress);
       vote.setVoteId(voteId);

       return vote;
   }

    protected Voters getVotersFixture(int yeaCount, int nayCount, int notVotingCount, int presentCount) {

        Voters voters = new Voters();

        voters.setYea(getVoterListFixture(yeaCount));
        voters.setNay(getVoterListFixture(nayCount));
        voters.setPresent(getVoterListFixture(presentCount));
        voters.setNotVoting(getVoterListFixture(notVotingCount));

        return voters;
    }

    protected Voter getVoterFixture(String id, String party, String state) {

        Voter voter = new Voter();
        voter.setBioguideId(id);
        voter.setParty(party);
        voter.setState(state);

        return voter;
    }

    protected List<Voter> getVoterListFixture(int count) {

        if (count == 0)
            return null;

        int order = 0;
        String bioguideId;
        List<Voter> voters = new LinkedList<>();

        for (int i=0;i<count;i++) {

            order = (i/10 == order) ? order : order++;
            bioguideId = String.format("A%0" + (6-order) + "d", i);

            Voter voter = getVoterFixture(bioguideId, "D", "TN");

            voters.add(voter);
        }

        return voters;
    }
}
