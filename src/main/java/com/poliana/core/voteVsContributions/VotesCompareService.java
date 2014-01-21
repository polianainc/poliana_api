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

    public VoteVsIndustryContributions getVoteVsIndustryContributions(Vote vote, IndustryContributionTotalsMap totalsMap) {

        VoteVsIndustryContributions voteVsContribution = new VoteVsIndustryContributions();

        voteVsContribution.setVoteId(vote.getVoteId());
        voteVsContribution.setCongress(vote.getCongress());
        voteVsContribution.setVoteDate(vote.getDate());
        voteVsContribution.setIndustryName(totalsMap.getIndustryName());
        voteVsContribution.setBeginDate(new Date(totalsMap.getBeginTimestamp() * 1000));
        voteVsContribution.setEndDate(new Date(totalsMap.getEndTimestamp() * 1000));

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

    public VoteVsContributionTotals getVoteVsContributionTotals(Vote vote, IndustryContributionTotalsMap totalsMap) {

        VoteVsContributionTotals contributionTotals = new VoteVsContributionTotals();

        contributionTotals.setVoteId(vote.getVoteId());
        contributionTotals.setIndustryId(totalsMap.getIndustryId());
        contributionTotals.setIndustryName(totalsMap.getIndustryName());

        contributionTotals.setVoteDate(vote.getDate());
        contributionTotals.setBeginDate(new Date(totalsMap.getBeginTimestamp() * 1000));
        contributionTotals.setEndDate(new Date(totalsMap.getEndTimestamp() * 1000));

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

                contributionTotals.setYeaContributionSums(sum);
                contributionTotals.setYeaContributionCount(count);
                contributionTotals.setYeaContributionAvg(avg);
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

                contributionTotals.setNayContributionSums(sum);
                contributionTotals.setNayContributionCount(count);
                contributionTotals.setNayContributionAvg(avg);
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

                contributionTotals.setNotVotingContributionSums(sum);
                contributionTotals.setNotVotingContributionCount(count);
                contributionTotals.setNotVotingContributionAvg(avg);
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

                contributionTotals.setPresentContributionSums(sum);
                contributionTotals.setPresentContributionCount(count);
                contributionTotals.setPresentContributionAvg(avg);
            }
        }

        return contributionTotals;
    }
}
