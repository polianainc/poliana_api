package com.poliana.core.industryFinance.services;

import com.poliana.core.industries.Industry;
import com.poliana.core.industries.IndustryRepo;
import com.poliana.core.industryFinance.IndustryContributionHadoopRepo;
import com.poliana.core.industryFinance.entities.IndustryPartyAndGeoTotals;
import com.poliana.core.industryFinance.entities.Recipient;
import com.poliana.core.legislators.Legislator;
import com.poliana.core.legislators.LegislatorService;
import com.poliana.core.politicianFinance.entities.IndustryPoliticianContributionTotals;
import com.poliana.core.time.CongressYears;
import com.poliana.core.time.TimeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author David Gilmore
 * @date 1/13/14
 */
@Service
public class IndustryPartyAndGeoService {

    private IndustryRepo industryRepo;
    private LegislatorService legislatorService;
    private IndustryContributionHadoopRepo industryContributionHadoopRepo;

    private TimeService timeService;

    private static final Logger logger = Logger.getLogger(IndustryPartyAndGeoService.class);

    public IndustryPartyAndGeoService() {
        this.timeService = new TimeService();
    }

    /**
     * Get an IndustryPartyAndGeoTotals object
     * @param industryId
     * @param congress
     * @param numSeries
     * @return
     */
    public IndustryPartyAndGeoTotals getIndustryPartyAndGeoTotals(String industryId, int congress, int numSeries) {

        CongressYears years = timeService.congressToYears(congress);

        List<IndustryPoliticianContributionTotals> contributionTotals =
                industryContributionHadoopRepo.getIndustryContributionTotals(industryId, years.getYearOne(), years.getYearTwo());


        IndustryPartyAndGeoTotals timeRangeTotals = new IndustryPartyAndGeoTotals();

        timeRangeTotals.setCongress(congress);
        Industry industry = industryRepo.industry(industryId);
        timeRangeTotals.setIndustry(industry.getId());

        HashMap<String,Recipient> recipients = new HashMap<>(500);
        HashMap<String,Recipient> stateAverages = new HashMap<>(60);

        for (IndustryPoliticianContributionTotals contributions : contributionTotals) {
            setPartyCounts(contributions, timeRangeTotals);
            setStateCounts(contributions, stateAverages, numSeries);
            setRecipientMap(contributions, recipients, numSeries);
        }


        timeRangeTotals.setStates(stateAverages);
        timeRangeTotals.setTopRecipients(getTopRecipients(recipients, 5));
        timeRangeTotals.setBottomRecipients(getBottomRecipients(recipients, 5));

        return timeRangeTotals;
    }


    /**
     * Get a legislator object from a contribution object.
     * @param contribution
     * @return
     */
    private Legislator getLegislator(IndustryPoliticianContributionTotals contribution) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(contribution.getYear(),contribution.getMonth(),2);

        long timestamp = calendar.getTimeInMillis()/1000;

        return legislatorService.getLegislatorByIdTimestamp(contribution.getBioguideId(), timestamp);
    }

    /**
     * Get a list of the top recipients given a map of recipient objects.
     * @param recipients
     * @param limit
     * @return
     */
    private List<Recipient> getTopRecipients(HashMap<String, Recipient> recipients, int limit) {
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

    /**
     * Get a list of the least recipients given a map of recipient objects.
     * @param recipients
     * @param limit
     * @return
     */
    private List<Recipient> getBottomRecipients(HashMap<String, Recipient> recipients, int limit) {

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

    /**
     * Functional helper method that updates a map of Bioguide->Recipients from a list of industry contributions
     * @param contribution
     * @param recipients
     * @param numSeries
     */
    private void setRecipientMap(IndustryPoliticianContributionTotals contribution, HashMap<String, Recipient> recipients, int numSeries) {

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

    /**
     * Functional helper method that increments sums and counts according to party
     * @param contribution
     * @param totals
     */
    private void setPartyCounts(IndustryPoliticianContributionTotals contribution, IndustryPartyAndGeoTotals totals) {

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

    /**
     * Functional helper method that increments sums and counts according to legislator state
     * @param contribution
     * @param stateAverages
     * @param numSeries
     */
    private void setStateCounts(IndustryPoliticianContributionTotals contribution, HashMap<String, Recipient> stateAverages, int numSeries) {

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

    @Autowired
    public void setIndustryRepo(IndustryRepo industryRepo) {
        this.industryRepo = industryRepo;
    }

    @Autowired
    public void setLegislatorService(LegislatorService legislatorService) {
        this.legislatorService = legislatorService;
    }

    @Autowired
    public void setIndustryContributionHadoopRepo(IndustryContributionHadoopRepo industryContributionHadoopRepo) {
        this.industryContributionHadoopRepo = industryContributionHadoopRepo;
    }
}
