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

        if (vote.getVotes() != null) {

            if (vote.getVotes().getYea() != null) {

                List<VoteVsContribution> yeaContributions = new LinkedList<>();

                for (Voter voter : vote.getVotes().getYea()) {

                    VoteVsContribution compare = new VoteVsContribution();

                    compare.setBioguideId(voter.getBioguideId());
                    compare.setParty(voter.getParty());
                    compare.setState(voter.getState());

                    compare.setSum(totalsMap.getSums().get(voter.getBioguideId()));

                    yeaContributions.add(compare);
                }

                voteVsContribution.setYeas(yeaContributions);
            }

            if (vote.getVotes().getNay() != null) {

                List<VoteVsContribution> nayContributions = new LinkedList<>();

                for (Voter voter : vote.getVotes().getNay()) {

                    VoteVsContribution compare = new VoteVsContribution();

                    compare.setBioguideId(voter.getBioguideId());
                    compare.setParty(voter.getParty());
                    compare.setState(voter.getState());

                    compare.setSum(totalsMap.getSums().get(voter.getBioguideId()));

                    nayContributions.add(compare);
                }

                voteVsContribution.setNays(nayContributions);
            }

            if (vote.getVotes().getNotVoting() != null) {

                List<VoteVsContribution> notVotingContributions = new LinkedList<>();

                for (Voter voter : vote.getVotes().getNotVoting()) {

                    VoteVsContribution compare = new VoteVsContribution();

                    compare.setBioguideId(voter.getBioguideId());
                    compare.setParty(voter.getParty());
                    compare.setState(voter.getState());

                    compare.setSum(totalsMap.getSums().get(voter.getBioguideId()));

                    notVotingContributions.add(compare);
                }

                voteVsContribution.setNotVoting(notVotingContributions);
            }

            if (vote.getVotes().getPresent() != null) {

                List<VoteVsContribution> absentContributions = new LinkedList<>();

                for (Voter voter : vote.getVotes().getPresent()) {

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
}
