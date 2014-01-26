package com.poliana.core.voteVsContributions;

import com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap;
import com.poliana.core.votes.entities.Vote;
import com.poliana.core.votes.entities.Voter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author David Gilmore
 * @date 1/20/14
 */
@Service
public class VotesCompareService {

    private static final Logger logger = Logger.getLogger(VotesCompareService.class);


    /**
     * Get a breakdown of industry contributions to the entire voting body of a vote PER INDIVIDUAL
     * @param vote
     * @param totalsMap
     * @return
     */
    public VoteVsIndustryContributions getVoteVsIndustryContributions(Vote vote, IndustryContributionTotalsMap totalsMap) {

        if (vote == null || totalsMap == null)
            return null;

        VoteVsIndustryContributions voteVsContribution = new VoteVsIndustryContributions();

        voteVsContribution.setVoteId(vote.getVoteId());
        voteVsContribution.setCongress(vote.getCongress());
        voteVsContribution.setVoteDate(vote.getDate());

        String name = totalsMap.getIndustryId() != null ? totalsMap.getIndustryName() : totalsMap.getCategoryName();

        voteVsContribution.setIndustryName(name);

        try {
            voteVsContribution.setBeginDate(new Date(totalsMap.getBeginTimestamp() * 1000));
            voteVsContribution.setEndDate(new Date(totalsMap.getEndTimestamp() * 1000));
        }
        catch (NullPointerException e) {
            logger.error(e);
        }

        if (vote.getVoters() != null) {

            if (vote.getVoters().getYea() != null) {

                List<VoteVsContribution> yeaContributions = new LinkedList<>();

                for (Voter voter : vote.getVoters().getYea()) {

                    VoteVsContribution compare = new VoteVsContribution();

                    compare.setBioguideId(voter.getBioguideId());
                    compare.setParty(voter.getParty());
                    compare.setState(voter.getState());

                    compare.setSum(totalsMap.getSums().get(voter.getBioguideId()));

                    yeaContributions.add(compare);
                }

                voteVsContribution.setYeas(yeaContributions);
            }

            if (vote.getVoters().getNay() != null) {

                List<VoteVsContribution> nayContributions = new LinkedList<>();

                for (Voter voter : vote.getVoters().getNay()) {

                    VoteVsContribution compare = new VoteVsContribution();

                    compare.setBioguideId(voter.getBioguideId());
                    compare.setParty(voter.getParty());
                    compare.setState(voter.getState());

                    compare.setSum(totalsMap.getSums().get(voter.getBioguideId()));

                    nayContributions.add(compare);
                }

                voteVsContribution.setNays(nayContributions);
            }

            if (vote.getVoters().getNotVoting() != null) {

                List<VoteVsContribution> notVotingContributions = new LinkedList<>();

                for (Voter voter : vote.getVoters().getNotVoting()) {

                    VoteVsContribution compare = new VoteVsContribution();

                    compare.setBioguideId(voter.getBioguideId());
                    compare.setParty(voter.getParty());
                    compare.setState(voter.getState());

                    compare.setSum(totalsMap.getSums().get(voter.getBioguideId()));

                    notVotingContributions.add(compare);
                }

                voteVsContribution.setNotVoting(notVotingContributions);
            }

            if (vote.getVoters().getPresent() != null) {

                List<VoteVsContribution> absentContributions = new LinkedList<>();

                for (Voter voter : vote.getVoters().getPresent()) {

                    VoteVsContribution compare = new VoteVsContribution();

                    compare.setBioguideId(voter.getBioguideId());
                    compare.setParty(voter.getParty());
                    compare.setState(voter.getState());

                    compare.setSum(totalsMap.getSums().get(voter.getBioguideId()));

                    absentContributions.add(compare);
                }

                voteVsContribution.setAbsent(absentContributions);
            }
        }

        return voteVsContribution;
    }


    public VoteVsIndustryContributionTotals getVoteVsIndustryContributionTotals(Vote vote, IndustryContributionTotalsMap totalsMap) {

        if (vote == null || totalsMap == null)
            return null;

        VoteVsIndustryContributionTotals voteVsContributionTotals = new VoteVsIndustryContributionTotals();

        voteVsContributionTotals.setVoteId(vote.getVoteId());

        String name = totalsMap.getCategoryId() != null ? totalsMap.getCategoryName() : totalsMap.getIndustryName();
        String id = totalsMap.getCategoryId() != null ? totalsMap.getCategoryId() : totalsMap.getIndustryId();

        voteVsContributionTotals.setIndustryName(name);
        voteVsContributionTotals.setIndustryId(id);

        voteVsContributionTotals.setVoteId(vote.getVoteId());
        voteVsContributionTotals.setVoteDate(vote.getDate());

        voteVsContributionTotals.setVoteDate(vote.getDate());

        try {
            voteVsContributionTotals.setBeginDate(new Date(totalsMap.getBeginTimestamp() * 1000));
            voteVsContributionTotals.setEndDate(new Date(totalsMap.getEndTimestamp() * 1000));
        }
        catch (NullPointerException e) {
            logger.error(e);
        }

        if (vote.getVoters() != null) {

            if (vote.getVoters().getYea() != null) {

                int sum = 0;
                int count = 0;
                double avg;

                for (Voter voter : vote.getVoters().getYea()) {

                    count++;

                    try {
                        sum += totalsMap.getSums().get(voter.getBioguideId());
                    }
                    catch (NullPointerException e) {}
                }

                avg = sum / count;

                voteVsContributionTotals.setYeaContributionSums(sum);
                voteVsContributionTotals.setYeaContributionCount(count);
                voteVsContributionTotals.setYeaContributionAvg(avg);
            }

            if (vote.getVoters().getNay() != null) {

                int sum = 0;
                int count = 0;
                double avg;

                for (Voter voter : vote.getVoters().getNay()) {

                    count++;

                    try {
                        sum += totalsMap.getSums().get(voter.getBioguideId());
                    }
                    catch (NullPointerException e) {}
                }

                avg = sum / count;

                voteVsContributionTotals.setNayContributionSums(sum);
                voteVsContributionTotals.setNayContributionCount(count);
                voteVsContributionTotals.setNayContributionAvg(avg);
            }

            if (vote.getVoters().getNotVoting() != null) {

                int sum = 0;
                int count = 0;
                double avg;

                for (Voter voter : vote.getVoters().getNotVoting()) {

                    count++;

                    try {
                        sum += totalsMap.getSums().get(voter.getBioguideId());
                    }
                    catch (NullPointerException e) {}
                }

                avg = sum / count;

                voteVsContributionTotals.setNotVotingContributionSums(sum);
                voteVsContributionTotals.setNotVotingContributionCount(count);
                voteVsContributionTotals.setNotVotingContributionAvg(avg);
            }

            if (vote.getVoters().getPresent() != null) {

                int sum = 0;
                int count = 0;
                double avg;

                for (Voter voter : vote.getVoters().getPresent()) {

                    count++;

                    try {
                        sum += totalsMap.getSums().get(voter.getBioguideId());
                    }
                    catch (NullPointerException e) {}
                }

                avg = sum / count;

                voteVsContributionTotals.setPresentContributionSums(sum);
                voteVsContributionTotals.setPresentContributionCount(count);
                voteVsContributionTotals.setPresentContributionAvg(avg);
            }
        }

        return voteVsContributionTotals;
    }
}
