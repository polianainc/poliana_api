package com.poliana.campaignFinance.services;

import com.poliana.campaignFinance.entities.IndTimeRangeTotals;
import com.poliana.campaignFinance.entities.IndToPolContrTotals;
import com.poliana.campaignFinance.entities.Recipient;
import com.poliana.campaignFinance.repositories.ContributionHadoopRepo;
import com.poliana.entities.entities.Legislator;
import com.poliana.entities.repositories.EntitiesMongoRepo;
import com.poliana.entities.services.LegislatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author David Gilmore
 * @date 11/15/13
 */
@Service
public class ContributionService {

    @Autowired
    private EntitiesMongoRepo entitiesMongoRepo;
    @Autowired
    private LegislatorService legislatorService;
    @Autowired
    private ContributionHadoopRepo contributionRepo;

    public IndTimeRangeTotals industryTimeRangeTotals(
            String industryId, int congress, int numSeries) {

        List<IndToPolContrTotals> contributionTotals =
                contributionRepo.industryContrTotals(industryId, congressToYears(congress));


        IndTimeRangeTotals timeRangeTotals = new IndTimeRangeTotals();

        timeRangeTotals.setCongress(congress);
        timeRangeTotals.setIndustry(entitiesMongoRepo.industry(industryId));

        HashMap<String,Recipient> recipients = new HashMap<>(500);
        HashMap<String,Recipient> stateAverages = new HashMap<>(60);

        for (IndToPolContrTotals contributions : contributionTotals) {
            partyCount(contributions, timeRangeTotals);
            stateCount(contributions, stateAverages, numSeries);
            recipientMap(contributions, recipients, numSeries);
        }


        timeRangeTotals.setStateAverages(stateAverages);
        timeRangeTotals.setTopRecipients(topRecipients(recipients));

        return timeRangeTotals;
    }

    public int[] congressToYears(int congress) {
        int[] years = new int[2];
        years[0] = (congress*2) + 1787;
        years[1] = years[0] + 1;
        return years;
    }

/*********************************************************************************************************/

/*********************************************************************************************************/

    private void recipientMap(IndToPolContrTotals contribution,
                              HashMap<String,Recipient> recipients, int numSeries) {

        String bioguideId = contribution.getBioguideId();
        Legislator legislator = getLegislator(contribution);
        if (recipients.containsKey(bioguideId)) {
            Recipient recipient = recipients.get(bioguideId);
            recipient.setCount(recipient.getCount() + contribution.getContributionsCount());
            recipient.setSum(recipient.getSum() + contribution.getContributionSum());
        }
        else {
            Recipient recipient = new Recipient();
            int contributionCount = contribution.getContributionsCount();
            int contributionSum = contribution.getContributionSum();

            recipient.setState(legislator.getTermState());
            recipient.setCount(contributionCount);
            recipient.setSum(contributionSum);
            recipient.setBioguideId(bioguideId);
            recipient.setSeriesAverage(contributionSum/numSeries);
            recipient.setParty(legislator.getParty());
            recipients.put(bioguideId, recipient);
        }
    }

    private void partyCount(IndToPolContrTotals contribution, IndTimeRangeTotals totals) {
        switch(contribution.getParty()) {
            case "Republican":
                totals.setRepublicanCount(totals.getRepublicanCount() + contribution.getContributionSum());
                totals.setRepublicanSum(totals.getRepublicanCount() + contribution.getContributionSum());
                break;
            case "Democrat":
                totals.setDemocratCount(totals.getDemocratCount() + contribution.getContributionsCount());
                totals.setDemocratSum(totals.getDemocratSum() + contribution.getContributionSum());
                break;
            case "Independent":
                totals.setIndependentCount(totals.getIndependentCount() + contribution.getContributionSum());
                totals.setIndependentSum(totals.getIndependentSum() + contribution.getContributionSum());
                break;
        }
    }

    private void stateCount(IndToPolContrTotals contribution,
                            HashMap<String,Recipient> stateAverages, int numSeries) {
        Legislator legislator = getLegislator(contribution);
        String state = legislator.getTermState();

        if (stateAverages.containsKey(state)) {
            Recipient recipient = stateAverages.get(state);
            recipient.setCount(recipient.getCount() + contribution.getContributionsCount());
            recipient.setSum(recipient.getSum() + contribution.getContributionSum());
        }
        else {
            Recipient recipient = new Recipient();
            recipient.setState(legislator.getTermState());

            int contributionsCount = contribution.getContributionsCount();
            int contributionsSum = contribution.getContributionSum();
            recipient.setCount(contributionsCount);
            recipient.setSum(contributionsSum);
            recipient.setSeriesAverage(contributionsSum/numSeries);
            stateAverages.put(legislator.getTermState(), recipient);
        }
    }

    private Legislator getLegislator(IndToPolContrTotals contribution) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(contribution.getYear(),contribution.getMonth(),2);
        long timeStamp = calendar.getTimeInMillis()/1000;
        return legislatorService.legislatorByIdTimestamp(contribution.getBioguideId(),(int)timeStamp);
    }

    private List<Recipient> topRecipients(HashMap<String,Recipient> recipients) {
        List<Recipient> recipientList = new ArrayList<>(recipients.values());

        Collections.sort(recipientList, new Comparator<Recipient>() {

            public int compare(Recipient r1, Recipient r2) {
                return r2.getSum() - r1.getSum();
            }
        });

        return recipientList.subList(0,10);
    }

}