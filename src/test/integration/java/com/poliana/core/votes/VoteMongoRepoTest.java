package com.poliana.core.votes;

import com.poliana.config.ApplicationConfig;
import com.poliana.core.votes.entities.BillReference;
import com.poliana.core.votes.entities.Vote;
import com.poliana.core.votes.entities.Voter;
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
public class VoteMongoRepoTest {

    private VoteMongoRepo voteMongoRepo;

    @Test
    public void testGetVote() throws Exception {

        Vote vote = voteMongoRepo.getVote("h768-111.2009");

        assertNotNull(vote);

        assertEquals("h768-111.2009", vote.getVoteId());
        assertEquals("h", vote.getChamber());
        assertEquals("passage-suspension", vote.getCategory());
        assertEquals(768, vote.getNumber().intValue());
        assertNotNull(vote.getId());
        assertNotNull(vote.getDate());
        assertNotNull(vote.getQuestion());
        assertNotNull(vote.getRequires());
        assertNotNull(vote.getResultText());
        assertNotNull(vote.getSession());
        assertNotNull(vote.getSourceUrl());
        assertNotNull(vote.getSubject());
        assertNotNull(vote.getType());
        assertNotNull(vote.getUpdatedAt());

        BillReference billReference = vote.getBill();

        assertNotNull(billReference.getCongress());
        assertNotNull(billReference.getNumber());
        assertNotNull(billReference.getType());

        Voter voter = vote.getVoters().getYea().get(0);

        assertNotNull(voter.getDisplayName());
        assertNotNull(voter.getBioguideId());
        assertNotNull(voter.getParty());
        assertNotNull(voter.getState());
    }

    @Autowired
    public void setVoteMongoRepo(VoteMongoRepo voteMongoRepo) {
        this.voteMongoRepo = voteMongoRepo;
    }
}


