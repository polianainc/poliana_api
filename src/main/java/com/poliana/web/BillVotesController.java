package com.poliana.web;

import com.poliana.core.votes.VoteService;
import com.poliana.core.votes.entities.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.poliana.core.time.TimeService.CURRENT_CONGRESS;
import static com.poliana.core.time.TimeService.CURRENT_YEAR;

/**
 * @author David Gilmore
 * @date 1/16/14
 */
@Controller
@RequestMapping("/bills/votes/")
public class BillVotesController {

    private VoteService voteService;


    /**
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody String getVotesIndex() {

        return "Vote index page";
    }

    /**
     * Get a vote by its voteId. To get a vote from outside of the current year, the correct congressional cycle
     * and year must be supplied.
     * @param voteId
     * @param congress
     * @param year
     * @return
     */
    @RequestMapping(value = "{vote_id}", method = RequestMethod.GET)
    public @ResponseBody Vote getVoteById(
            @PathVariable("vote_id") String voteId,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "year", required = false, defaultValue = CURRENT_YEAR) Integer year) {

        Vote vote = voteService.getVote(voteId, congress, year);

        return vote;
    }

    @Autowired
    public void setVoteService(VoteService voteService) {
        this.voteService = voteService;
    }
}
