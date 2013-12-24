package com.poliana.core.finance.industries;

import com.poliana.core.finance.industries.entities.IndTimeRangeTotals;
import com.poliana.core.finance.industries.entities.IndToPolContrTotals;
import com.poliana.core.finance.general.entities.Recipient;
import com.poliana.core.finance.industries.entities.IndustryPoliticianContributions;
import com.poliana.core.finance.industries.repositories.IndustryContributionHadoopRepo;
import com.poliana.core.common.util.TimeUtils;
import com.poliana.core.entities.entities.Industry;
import com.poliana.core.entities.entities.Legislator;
import com.poliana.core.entities.repositories.EntitiesMongoRepo;
import com.poliana.core.entities.services.LegislatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author David Gilmore
 * @date 11/15/13
 */
@Service
public class IndustryContributionService {

    @Autowired
    private EntitiesMongoRepo entitiesMongoRepo;
    @Autowired
    private LegislatorService legislatorService;
    @Autowired
    private IndustryContributionHadoopRepo contributionRepo;

    /**
     *
     * @param bioguideId
     * @param congress
     * @return
     */
    public List<IndustryPoliticianContributions> legislatorReceivedIndustryTotals(String bioguideId, int congress) {
        int[] yearTimestamps = TimeUtils.yearTimestamps(congress);
        return legislatorReceivedIndustryTotals(bioguideId, yearTimestamps[0], yearTimestamps[1], 0);
    }

    /**
     *
     * @param bioguideId
     * @param beginTimestamp
     * @param endTimestamp
     * @param limit
     * @return
     */
    public List<IndustryPoliticianContributions> legislatorReceivedIndustryTotals(String bioguideId,
                                                           int beginTimestamp, int endTimestamp, int limit) {
        return contributionRepo.legislatorReceivedIndustryTotals(bioguideId, beginTimestamp, endTimestamp, limit);
    }

    /**
     *
     * @param bioguideId
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public List<IndustryPoliticianContributions> legislatorReceivedIndustryTotals(String bioguideId,
                                                           int beginTimestamp, int endTimestamp) {
        return contributionRepo.legislatorReceivedIndustryTotals(bioguideId, beginTimestamp, endTimestamp);
    }

    /**
     * @param industryId
     * @param congress
     * @param numSeries
     * @return
     */
    public IndTimeRangeTotals industryTimeRangeTotals(
            String industryId, int congress, int numSeries) {

        List<IndToPolContrTotals> contributionTotals =
                contributionRepo.industryContrTotals(industryId, TimeUtils.congressToYears(congress));


        IndTimeRangeTotals timeRangeTotals = new IndTimeRangeTotals();

        timeRangeTotals.setCongress(congress);
        Industry industry = entitiesMongoRepo.industry(industryId);
        timeRangeTotals.setIndustry(industry.getId());

        HashMap<String,Recipient> recipients = new HashMap<>(500);
        HashMap<String,Recipient> stateAverages = new HashMap<>(60);

        for (IndToPolContrTotals contributions : contributionTotals) {
            partyCount(contributions, timeRangeTotals);
            stateCount(contributions, stateAverages, numSeries);
            recipientMap(contributions, recipients, numSeries);
        }


        timeRangeTotals.setStates(stateAverages);
        timeRangeTotals.setTopRecipients(topRecipients(recipients, 5));
        timeRangeTotals.setBottomRecipients(bottomRecipients(recipients, 5));

        return timeRangeTotals;
    }

/**********************************************************************************************************************/

/**********************************************************************************************************************/

    protected boolean assertBioguide(String bioguideId) {
        return (bioguideId.length() == 7);
    }

    protected void recipientMap(IndToPolContrTotals contribution,
                              HashMap<String,Recipient> recipients, int numSeries) {

        String bioguideId = contribution.getBioguideId();
        Legislator legislator = getLegislator(contribution);
        if (recipients.containsKey(bioguideId)) {
            Recipient recipient = recipients.get(bioguideId);
            recipient.setCount(recipient.getCount() + contribution.getContributionsCount());
            recipient.setSum(recipient.getSum() + contribution.getContributionSum());
            recipient.setSeriesAverage(recipient.getSum()/numSeries);
        }
        else {
            Recipient recipient = new Recipient();
            int contributionCount = contribution.getContributionsCount();
            int contributionSum = contribution.getContributionSum();

            recipient.setState(legislator.getTermState());
            recipient.setCount(contributionCount);
            recipient.setSum(contributionSum);
            recipient.setBioguideId(bioguideId);
            recipient.setSeriesAverage(contributionSum / numSeries);
            recipient.setParty(legislator.getParty());
            recipient.setFirstName(legislator.getFirstName());
            recipient.setLastName(legislator.getLastName());
            recipients.put(bioguideId, recipient);
        }
    }

    protected void partyCount(IndToPolContrTotals contribution, IndTimeRangeTotals totals) {
        switch(contribution.getParty()) {
            case "Republican":
                totals.setRepublicanCount(totals.getRepublicanCount() + contribution.getContributionsCount());
                totals.setRepublicanSum(totals.getRepublicanSum() + contribution.getContributionSum());
                break;
            case "Democrat":
                totals.setDemocratCount(totals.getDemocratCount() + contribution.getContributionsCount());
                totals.setDemocratSum(totals.getDemocratSum() + contribution.getContributionSum());
                break;
            case "Independent":
                totals.setIndependentCount(totals.getIndependentCount() + contribution.getContributionsCount());
                totals.setIndependentSum(totals.getIndependentSum() + contribution.getContributionSum());
                break;
        }
    }

    protected void stateCount(IndToPolContrTotals contribution,
                            HashMap<String,Recipient> stateAverages, int numSeries) {
        Legislator legislator = getLegislator(contribution);
        String state = legislator.getTermState();

        if (stateAverages.containsKey(state)) {
            Recipient recipient = stateAverages.get(state);
            recipient.setCount(recipient.getCount() + contribution.getContributionsCount());
            recipient.setSum(recipient.getSum() + contribution.getContributionSum());
            recipient.setSeriesAverage(recipient.getSum()/numSeries);
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

    protected Legislator getLegislator(IndToPolContrTotals contribution) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(contribution.getYear(),contribution.getMonth(),2);
        long timestamp = calendar.getTimeInMillis()/1000;
        return legislatorService.legislatorByIdTimestamp(contribution.getBioguideId(),(int)timestamp);
    }

    protected List<Recipient> topRecipients(HashMap<String,Recipient> recipients, int limit) {
        List<Recipient> recipientList = new ArrayList<>(recipients.values());

        Collections.sort(recipientList, new Comparator<Recipient>() {

            public int compare(Recipient r1, Recipient r2) {
                return r2.getSum() - r1.getSum();
            }
        });

        if (recipientList.size() > limit)
            return recipientList.subList(0,limit);
        else
            return recipientList;
    }

    protected List<Recipient> bottomRecipients(HashMap<String,Recipient> recipients, int limit) {
        List<Recipient> recipientList = new ArrayList<>(recipients.values());

        Collections.sort(recipientList, new Comparator<Recipient>() {

            public int compare(Recipient r1, Recipient r2) {
                return r1.getSum() - r2.getSum();
            }
        });

        int recipientsCount = recipientList.size();
        int index = 0;
        int firstPositive = 0;
        Iterator<Recipient> recipientIterator = recipientList.iterator();
        while (recipientIterator.hasNext() && firstPositive < recipientsCount - limit) {
            Recipient recipient = recipientIterator.next();
            if (recipient.getSum() > 0) {
                firstPositive = index;
                break;
            }
            index++;
        }

        if (recipientsCount > limit + firstPositive)
            return recipientList.subList(firstPositive,limit+firstPositive);
        else if (recipientsCount <= limit)
            return recipientList;
        else {
            int offset = (firstPositive - recipientsCount) + limit;
            return recipientList.subList(firstPositive-offset,firstPositive-offset);
        }
    }
}