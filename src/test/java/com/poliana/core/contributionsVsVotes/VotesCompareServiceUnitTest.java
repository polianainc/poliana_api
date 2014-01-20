package com.poliana.core.contributionsVsVotes;

import com.poliana.core.shared.AbstractVotesTest;
import com.poliana.core.votes.entities.Vote;
import org.junit.Test;

import java.util.Date;

/**
 * @author: graysoncarroll
 * @date: 1/20/14
 */
public class VotesCompareServiceUnitTest extends AbstractVotesTest {

    @Test
    public void testGetVoteVsIndustryContributions_FullVote() throws Exception {

        Vote vote = getVoteFixture("h768-111.2009", new Date(), 111);

        vote.setVoters(getVotersFixture(250, 150, 20, 20));


        //TODO: Add industry contributions and finish assertions
    }
}
