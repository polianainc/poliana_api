package com.poliana.core.industryFinance;

import com.poliana.core.ideology.IdeologyMatrix;
import com.poliana.core.ideology.LegislatorIdeology;
import com.poliana.core.industryFinance.entities.*;
import com.poliana.core.politicianFinance.entities.IndustryPoliticianContributions;
import com.poliana.core.time.CongressTimestamps;
import com.poliana.core.time.CongressYears;
import com.poliana.core.time.TimeService;
import com.poliana.core.industries.IndustryRepo;
import com.poliana.core.industries.Industry;
import com.poliana.core.legislators.Legislator;
import com.poliana.core.legislators.LegislatorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author David Gilmore
 * @date 11/15/13
 */
@Service
public class IndustryContributionService {

    private IndustryRepo industryRepo;
    private LegislatorService legislatorService;
    private IndustryContributionMongoRepo industryContributionMongoRepo;
    private IndustryContributionHadoopRepo industryContributionHadoopRepo;
    private TimeService timeService;

    private static final Logger logger = Logger.getLogger(IndustryContributionService.class);


    /**
     * Get a list of industry contributions compared to legislator ideology
     * @param ideologyMatrix
     * @param industryContributionTotalsHashMap
     * @return
     */
    public List<IndustryContributionCompare> getIdeologyVsContributions(
            IdeologyMatrix ideologyMatrix, IndustryContributionTotalsHashMap industryContributionTotalsHashMap) {

        List<IndustryContributionCompare> compareList = new LinkedList<>();

        if (ideologyMatrix == null || industryContributionTotalsHashMap == null)
            return new LinkedList<>();

        for (LegislatorIdeology legislatorIdeology: ideologyMatrix.getIdeologies()) {

            //Inititialize a new comparison
            IndustryContributionCompare compare = new IndustryContributionCompare();

            //If the sums were made for a category, only the categoryId will be set, and vice verse
            String industry;
            if (industryContributionTotalsHashMap.getIndustryId() != null)
                industry = industryContributionTotalsHashMap.getIndustryName();
            else
                industry = industryContributionTotalsHashMap.getCategoryName();

            //Set values
            compare.setIndustry(industry);
            compare.setLegislator(legislatorIdeology.getName());
            compare.setParty(legislatorIdeology.getParty());
            compare.setReligion(legislatorIdeology.getReligion());

            try { //Try to get an amount from the contribution totals' sum map
                compare.setAmount(industryContributionTotalsHashMap.getSums().get(legislatorIdeology.getBioguideId()));
            }
            catch (Exception e) { //If there is no entry for this legislator, set the value to 0
                compare.setAmount(0);
            }

            compare.setCompare1(legislatorIdeology.getIdeology());
            compare.setCompare1Metric("ideology");

            //Append the new compare object
            compareList.add(compare);
        }

        return compareList;
    }

    /**
     * Get a map of BioguideId->Total sum of industry contributions to all legislators during the given
     * congressional cycle
     * @param congress
     * @return
     */
    public IndustryContributionTotalsHashMap getIndustryTotals(String industryId, int congress) {

        //Check MongoDB for a cached industry chamber total document
        IndustryContributionTotalsHashMap chamberTotals =
                industryContributionMongoRepo.getIndustryTotals(industryId, congress);

        //If it exists, return it
        if (chamberTotals != null)
            return chamberTotals;

        //If not we'll use Impala to get it
        chamberTotals = industryContributionHadoopRepo.getIndustryContributionTotals(industryId, congress);

        if (chamberTotals != null)
            industryContributionMongoRepo.saveIndustryContributionTotals(chamberTotals);

        return chamberTotals;
    }

    /**
     * Get a map of BioguideId->Total sum of industry contributions to all legislators during the given
     * congressional cycles
     * @param congress
     * @return
     */
    public IndustryContributionTotalsHashMap getIndustryCategoryTotals(String categoryId, int congress) {

        //Check MongoDB for a cached industry chamber total document
        IndustryContributionTotalsHashMap chamberTotals =
                industryContributionMongoRepo.getIndustryCategoryTotals(categoryId, congress);

        //If it exists, return it
        if (chamberTotals != null)
            return chamberTotals;

        chamberTotals = industryContributionHadoopRepo.getIndustryCategoryContributionTotals(categoryId, congress);

        if (chamberTotals != null) {
            chamberTotals.setIndustryId(null);
            chamberTotals.setCategoryId(categoryId);
            industryContributionMongoRepo.saveIndustryContributionTotals(chamberTotals);
        }

        return chamberTotals;
    }

    /**
     * Get a map of BioguideId->Total sum of industry contributions to all legislators in a given chamber during the given
     * congressional cycles
     * @param chamber
     * @param congress
     * @return
     */
    public IndustryContributionTotalsHashMap getIndustryTotalsByChamber(String industryId, String chamber, int congress) {

        //Check MongoDB for a cached industry chamber total document
        IndustryContributionTotalsHashMap chamberTotals =
                industryContributionMongoRepo.getIndustryChamberTotals(industryId, chamber, congress);

        //If it exists, return it
        if (chamberTotals != null)
            return chamberTotals;

        //If not we'll use Impala to get it
        chamberTotals = industryContributionHadoopRepo.getIndustryChamberContributionTotals(industryId, chamber, congress);

        if (chamberTotals != null)
            industryContributionMongoRepo.saveIndustryContributionTotals(chamberTotals);

        return chamberTotals;
    }

    /**
     * Get a map of BioguideId->Total sum of industry contributions to all legislators in a given chamber during the given
     * congressional cycles
     * @param chamber
     * @param congress
     * @return
     */
    public IndustryContributionTotalsHashMap getIndustryCategoryTotalsByChamber(String categoryId, String chamber, int congress) {

        //Check MongoDB for a cached industry chamber total document
        IndustryContributionTotalsHashMap chamberTotals =
                industryContributionMongoRepo.getIndustryCategoryChamberTotals(categoryId, chamber, congress);

        //If it exists, return it
        if (chamberTotals != null)
            return chamberTotals;

        chamberTotals = industryContributionHadoopRepo.getIndustryCategoryChamberContributionTotals(categoryId, chamber, congress);

        if (chamberTotals != null) {
            chamberTotals.setIndustryId(null);
            chamberTotals.setCategoryId(categoryId);
            industryContributionMongoRepo.saveIndustryContributionTotals(chamberTotals);
        }

        return chamberTotals;
    }

    /**
     *
     * @param bioguideId
     * @param congress
     * @return
     */
    public List<IndustryPoliticianContribution> legislatorReceivedIndustryTotals(String bioguideId, int congress) {

        CongressTimestamps yearTimestamps = timeService.congressTimestamps(congress);
        return legislatorReceivedIndustryTotals(bioguideId, yearTimestamps.getBegin(), yearTimestamps.getEnd(), 0);
    }

    /**
     *
     * @param bioguideId
     * @param beginTimestamp
     * @param endTimestamp
     * @param limit
     * @return
     */
    public List<IndustryPoliticianContribution> legislatorReceivedIndustryTotals(String bioguideId,
                                                                                  long beginTimestamp, long endTimestamp, int limit) {

        return industryContributionHadoopRepo.legislatorReceivedIndustryTotals(bioguideId, beginTimestamp, endTimestamp, limit);
    }

    /**
     * Get an IndustryTimeRangeTotals object
     * @param industryId
     * @param congress
     * @param numSeries
     * @return
     */
    public IndustryTimeRangeTotals getIndustryTimeRangeTotals(String industryId, int congress, int numSeries) {

        CongressYears years = timeService.congressToYears(congress);

        List<IndustryPoliticianContributions> contributionTotals =
                industryContributionHadoopRepo.industryContrTotals(industryId, years.getYearOne(), years.getYearTwo());


        IndustryTimeRangeTotals timeRangeTotals = new IndustryTimeRangeTotals();

        timeRangeTotals.setCongress(congress);
        Industry industry = industryRepo.industry(industryId);
        timeRangeTotals.setIndustry(industry.getId());

        HashMap<String,Recipient> recipients = new HashMap<>(500);
        HashMap<String,Recipient> stateAverages = new HashMap<>(60);

        for (IndustryPoliticianContributions contributions : contributionTotals) {
            updatePartyCounts(contributions, timeRangeTotals);
            updateStateCounts(contributions, stateAverages, numSeries);
            updateRecipientMap(contributions, recipients, numSeries);
        }


        timeRangeTotals.setStates(stateAverages);
        timeRangeTotals.setTopRecipients(getTopRecipients(recipients, 5));
        timeRangeTotals.setBottomRecipients(getBottomRecipients(recipients, 5));

        return timeRangeTotals;
    }

    /**
     * Functional helper method that updates a map of Bioguide->Recipients from a list of industry contributions
     * @param contribution
     * @param recipients
     * @param numSeries
     */
    private void updateRecipientMap(IndustryPoliticianContributions contribution, HashMap<String, Recipient> recipients, int numSeries) {

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
    private void updatePartyCounts(IndustryPoliticianContributions contribution, IndustryTimeRangeTotals totals) {

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
    private void updateStateCounts(IndustryPoliticianContributions contribution, HashMap<String, Recipient> stateAverages, int numSeries) {

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

    /**
     * Get a legislator object from a contribution object.
     * @param contribution
     * @return
     */
    private Legislator getLegislator(IndustryPoliticianContributions contribution) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(contribution.getYear(),contribution.getMonth(),2);

        long timestamp = calendar.getTimeInMillis()/1000;

        return legislatorService.getLegislatorByIdTimestamp(contribution.getBioguideId(), (int) timestamp);
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

    @Autowired
    public void setIndustryRepo(IndustryRepo industryRepo) {
        this.industryRepo = industryRepo;
    }

    @Autowired
    public void setLegislatorService(LegislatorService legislatorService) {
        this.legislatorService = legislatorService;
    }

    @Autowired
    public void setIndustryContributionMongoRepo(IndustryContributionMongoRepo industryContributionMongoRepo) {
        this.industryContributionMongoRepo = industryContributionMongoRepo;
    }

    @Autowired
    public void setIndustryContributionHadoopRepo(IndustryContributionHadoopRepo industryContributionHadoopRepo) {
        this.industryContributionHadoopRepo = industryContributionHadoopRepo;
    }

    @Autowired
    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }
}