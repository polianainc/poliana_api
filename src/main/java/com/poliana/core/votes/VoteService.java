package com.poliana.core.votes;

import com.poliana.core.time.TimeService;
import com.poliana.core.votes.entities.Vote;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author David Gilmore
 * @date 1/17/14
 */
@Service
public class VoteService {

    private VoteMongoRepo voteMongoRepo;

    private TimeService timeService;

    private static final Logger logger = Logger.getLogger(VoteService.class);

    public VoteService() {

        this.timeService = new TimeService();
    }

    /**
     * Get all vote data with a vote id and congressional cycle
     * @param voteId
     * @return
     */
    public Vote getVote(String voteId, int congress, int year) {

        voteId += "-" + congress + "." + year;
        //TODO: Update MS timestamps to seconds
        //TODO: present to absent

        Vote vote = voteMongoRepo.getVote(voteId);

        if (vote != null)
            logger.info("Vote " + voteId + " fetched from MongoDB");

        return vote;
    }


    @Autowired
    public void setVoteMongoRepo(VoteMongoRepo voteMongoRepo) {
        this.voteMongoRepo = voteMongoRepo;
    }
}
