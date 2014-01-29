package com.poliana.core.votes;

import com.poliana.core.votes.entities.Vote;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.createStrictControl;
import static org.easymock.EasyMock.expect;

/**
 * @author David Gilmore
 * @date 1/17/14
 */
public class VoteServiceUnitTest {

    private VoteService voteService;

    private VoteMongoRepo voteMongoRepoMock;

    private IMocksControl control;


    @Before
    public void setUp() throws Exception {

        this.control = createStrictControl();

        this.voteMongoRepoMock = this.control.createMock(VoteMongoRepo.class);

        this.voteService = new VoteService();

        this.voteService.setVoteMongoRepo(this.voteMongoRepoMock);
    }

    @Test
    public void testGetVote() throws Exception {

        expect(this.voteMongoRepoMock.getVote("h768-111.2009")).andReturn(new Vote());

        this.control.replay();

        voteService.getVote("h768", 111, 2009);

        this.control.verify();
    }
}
