package com.poliana.web;

import com.poliana.core.contributionsVsVotes.VoteVsIndustryContributions;
import com.poliana.core.contributionsVsVotes.VotesCompareService;
import com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap;
import com.poliana.core.industryFinance.services.IndustryContributionCompareService;
import com.poliana.core.industryFinance.services.IndustryContributionService;
import com.poliana.core.time.TimeService;
import com.poliana.core.votes.VoteService;
import com.poliana.core.votes.entities.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.poliana.core.time.TimeService.CURRENT_CONGRESS;
import static com.poliana.core.time.TimeService.CURRENT_YEAR;

/**
 * @author David Gilmore
 * @date 1/19/14
 */
@Controller
@RequestMapping("/bills/votes/")
public class BillVotesIndustryContributionsController {

    private TimeService timeService;

    private VoteService voteService;
    private IndustryContributionService industryContributionService;
    private VotesCompareService votesCompareService;

    public BillVotesIndustryContributionsController() {

        this.timeService = new TimeService();
    }

    /**
     * Get contribution totals from an industry compared against the voting body of a given vote
     * @param voteId
     * @param congress
     * @param year
     * @return
     */
    @RequestMapping(value = "{vote_id}", params = {"industry_id", "start", "end"}, method = RequestMethod.GET)
    public @ResponseBody VoteVsIndustryContributions getVoteVsIndustryContributions(
            @PathVariable("vote_id") String voteId,
            @RequestParam(value = "congress", required = false, defaultValue = CURRENT_CONGRESS) Integer congress,
            @RequestParam(value = "year", required = false, defaultValue = CURRENT_YEAR) Integer year,
            @RequestParam(value = "industry_id", required = true) String industryId,
            @RequestParam(value = "start", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date start,
            @RequestParam(value = "end", required = true) @DateTimeFormat(pattern = "MM-dd-yyyy") Date end) {

        String chamber = voteId.substring(0, 1);

        if (!year.equals(CURRENT_YEAR) && congress.equals(CURRENT_CONGRESS))
            congress = timeService.getYearToCongress(year);

        Vote vote = voteService.getVote(voteId, congress, year);

        IndustryContributionTotalsMap totalsMap =
                industryContributionService.getIndustryContributionTotalsMap(industryId, chamber, start.getTime()/1000, end.getTime()/1000);

        VoteVsIndustryContributions voteVsIndustryContributions = new VoteVsIndustryContributions();

        if (vote != null && totalsMap != null)
            voteVsIndustryContributions = votesCompareService.getVoteVsIndustryContributions(vote, totalsMap);

        return voteVsIndustryContributions;
    }

    @Autowired
    public void setVoteService(VoteService voteService) {
        this.voteService = voteService;
    }

    @Autowired
    public void setIndustryContributionService(IndustryContributionService industryContributionService) {
        this.industryContributionService = industryContributionService;
    }

    @Autowired
    public void setVotesCompareService(VotesCompareService votesCompareService) {
        this.votesCompareService = votesCompareService;
    }
}
