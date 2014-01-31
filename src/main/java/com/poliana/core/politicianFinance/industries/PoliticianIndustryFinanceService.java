package com.poliana.core.politicianFinance.industries;

import com.poliana.core.time.TimeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @author David Gilmore
 * @date 12/26/13
 */
@Service
public class PoliticianIndustryFinanceService {

    private TimeService timeService;

    private PoliticianIndustryMongoRepo politicianIndustryMongoRepo;
    private PoliticianIndustryHadoopRepo politicianIndustryHadoopRepo;

    private static final Logger logger = Logger.getLogger(PoliticianIndustryFinanceService.class);


    public PoliticianIndustryFinanceService() {

        this.timeService = new TimeService();
    }

    /**
     * Get a list of industry to politician contributions summed over a given congressional cycle
     * @param bioguideId
     * @param congress
     * @return
     */
    public List<PoliticianIndustryContributionTotals> getIndustryToPoliticianTotals(String bioguideId, int congress) {

        List<PoliticianIndustryContributionTotals> totalsList = politicianIndustryMongoRepo.getIndustryToPoliticianContributions(bioguideId, congress);

        if (totalsList != null && totalsList.size() > 0)
            return totalsList;

        //If MongoDB didn't return, fall back to Impala.
        totalsList = politicianIndustryHadoopRepo.getIndustryToPoliticianContributions(bioguideId, congress);

        //If Impala had something to return, save it to MongoDB
        if (totalsList != null && totalsList.size() > 0)
            politicianIndustryMongoRepo.saveIndustryToPoliticianContributions(totalsList);

        return totalsList;
    }

    /**
     * Get a list of industry category to politician contributions summed over a given congressional cycle
     * @param bioguideId
     * @param congress
     * @return
     */
    public List<PoliticianIndustryContributionTotals> getIndustryCategoryToPoliticianTotals(String bioguideId, int congress) {

        List<PoliticianIndustryContributionTotals> totalsList =
                politicianIndustryMongoRepo.getIndustryCategoryToPoliticianContributions(bioguideId, congress);

        if (totalsList != null && totalsList.size() > 0)
            return totalsList;

        //If MongoDB didn't return, fall back to Impala.
        totalsList = politicianIndustryHadoopRepo.getIndustryCategoryToPoliticianContributions(bioguideId, congress);

        //If Impala had something to return, save it to MongoDB
        if (totalsList != null && totalsList.size() > 0)
            politicianIndustryMongoRepo.saveIndustryToPoliticianContributions(totalsList);

        return totalsList;
    }

    /**
     * Get a list of industry to politician contributions summed over a given congressional cycle
     * @param bioguideId
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public List<PoliticianIndustryContributionTotals> getIndustryToPoliticianTotals(String bioguideId, long beginTimestamp, long endTimestamp) {

        List<PoliticianIndustryContributionTotals> totalsList =
                politicianIndustryMongoRepo.getIndustryToPoliticianContributions(bioguideId, beginTimestamp, endTimestamp);

        if (totalsList != null && totalsList.size() > 0)
            return totalsList;

        //If MongoDB didn't return, fall back to Impala.
        totalsList = politicianIndustryHadoopRepo.getIndustryToPoliticianContributions(bioguideId, beginTimestamp, endTimestamp);

        //If Impala had something to return, save it to MongoDB
        if (totalsList.size() > 0)
            politicianIndustryMongoRepo.saveIndustryToPoliticianContributions(totalsList);

        return totalsList;
    }

    /**
     * Get a list of industry to politician contributions summed over a given congressional cycle
     * @param bioguideId
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public List<PoliticianIndustryContributionTotals> getIndustryCategoryToPoliticianTotals(String bioguideId, long beginTimestamp, long endTimestamp) {

        List<PoliticianIndustryContributionTotals> totalsList =
                politicianIndustryMongoRepo.getIndustryCateogryToPoliticianContributions(bioguideId, beginTimestamp, endTimestamp);

        if (totalsList != null && totalsList.size() > 0)
            return totalsList;

        //If MongoDB didn't return, fall back to Impala.
        totalsList = politicianIndustryHadoopRepo.getIndustryCategoryToPoliticianContributions(bioguideId, beginTimestamp, endTimestamp);

        //If Impala had something to return, save it to MongoDB
        if (totalsList.size() > 0)
            politicianIndustryMongoRepo.saveIndustryToPoliticianContributions(totalsList);

        return totalsList;
    }

    /**
     * Get a list of industry to politician contribution sums for all time
     * @param bioguideId
     * @return
     */
    public List<PoliticianIndustryContributionTotals> getIndustryToPoliticianTotals(String bioguideId) {

        //Query MongoDB for industry to politician objects
        List<PoliticianIndustryContributionTotals> totalsList =
                politicianIndustryMongoRepo.getIndustryToPoliticianContributions(bioguideId);

        if (totalsList != null && totalsList.size() > 0)
            return totalsList;

        totalsList = politicianIndustryHadoopRepo.getIndustryToPoliticianContributions(bioguideId);

        if (totalsList != null && totalsList.size() > 0)
            politicianIndustryMongoRepo.saveIndustryToPoliticianContributions(totalsList);

        return totalsList;
    }

    /**
     * Get a list of industry category to politician contribution sums for all time
     * @param bioguideId
     * @return
     */
    public List<PoliticianIndustryContributionTotals> getIndustryCategoryToPoliticianTotals(String bioguideId) {

        //Query MongoDB for industry to politician objects
        List<PoliticianIndustryContributionTotals> totalsList =
                politicianIndustryMongoRepo.getIndustryCategoryToPoliticianContributions(bioguideId);

        if (totalsList != null && totalsList.size() > 0)
            return totalsList;

        totalsList = politicianIndustryHadoopRepo.getIndustryCategoryToPoliticianContributions(bioguideId);

        if (totalsList != null && totalsList.size() > 0)
            politicianIndustryMongoRepo.saveIndustryToPoliticianContributions(totalsList);

        return totalsList;
    }

    /**
     * Get a HashMap of Cycle->Industry contribution lists for all congressional cycles a politician has been apart of
     * @param bioguideId
     * @return
     */
    public HashMap<Integer, List<PoliticianIndustryContributionTotals>> getIndustryToPoliticianTotalsPerCongress(String bioguideId) {

        HashMap<Integer, List<PoliticianIndustryContributionTotals>> totalsHashMap = politicianIndustryHadoopRepo.getIndustryToPoliticianTotalsPerCongress(bioguideId);

        //Cache sums to MongoDB

        //Get an iterator for the values in the hash map
        Iterator it = totalsHashMap.entrySet().iterator();
        Map.Entry pairs;

        //Iterate through all entry pairs in the map and update the TermTotalsMap with the values.
        while (it.hasNext()) {
            pairs = (Map.Entry) it.next();

            if (politicianIndustryMongoRepo.countIndustryToPoliticianContributions(bioguideId, (Integer) pairs.getKey()) <= 0)
                politicianIndustryMongoRepo.saveIndustryToPoliticianContributions((List<PoliticianIndustryContributionTotals>)pairs.getValue());
        }

        return totalsHashMap;
    }

    /**
     * Get a HashMap of Cycle->Industry-Category contribution lists for all congressional cycles a politician has been apart of
     * @param bioguideId
     * @return
     */
    public HashMap<Integer, List<PoliticianIndustryContributionTotals>> getIndustryToPoliticianTotalsPerCongress(String bioguideId, long beginTimestamp, long endTimestamp) {

        Integer[] cycles = timeService.getCongressionalCyclesByTimeRange(beginTimestamp, endTimestamp);

        //Query MongoDB for industry to politician objects
        Iterator<PoliticianIndustryContributionTotals> totalsIterator = politicianIndustryMongoRepo.getIndustryToPoliticianContributionsIterator(bioguideId, cycles);

        HashMap<Integer, List<PoliticianIndustryContributionTotals>> totalsHashMap = new HashMap<>(30);

        //Add industry totals to the HashMap. Check the size, if it's zero, fall back to Impala.
        while (totalsIterator != null && totalsIterator.hasNext()) {
            PoliticianIndustryContributionTotals industryTotals = totalsIterator.next();

            //If the hashmap already has a list of industry totals for the object's cycle
            if (totalsHashMap.containsKey(industryTotals.getCongress()))
                totalsHashMap.get(industryTotals.getCongress()).add(industryTotals);
                //If the hashmap doesn't contain a list of industry totals, make it
            else {
                List<PoliticianIndustryContributionTotals> totalsList = new LinkedList<>();
                totalsList.add(industryTotals);
                totalsHashMap.put(industryTotals.getCongress(), totalsList);
            }
        }

        //Ensure that all the necessary cycles were returned from MongoDB, if not, we'll use Impala
        if (totalsHashMap.size() > 0) {
            boolean totalsMapContainsAllCycles = true;

            for (int i = 0; i < cycles.length; i++)
                totalsMapContainsAllCycles = totalsHashMap.containsKey(cycles[i]) ? totalsMapContainsAllCycles : false;

            if (totalsMapContainsAllCycles)
                return totalsHashMap;
        }

        //Fall back to Impala if MongoDB did not have the sums cached
        totalsHashMap = politicianIndustryHadoopRepo.getIndustryToPoliticianTotalsPerCongress(bioguideId, beginTimestamp, endTimestamp);

        //Cache sums to MongoDB

        //Get an iterator for the values in the hash map
        Iterator it = totalsHashMap.entrySet().iterator();
        Map.Entry pairs;

        //Iterate through all entry pairs in the map and update the TermTotalsMap with the values.
        while (it.hasNext()) {
            pairs = (Map.Entry) it.next();

            if (politicianIndustryMongoRepo.countIndustryToPoliticianContributions(bioguideId, (Integer) pairs.getKey()) <= 0)
                politicianIndustryMongoRepo.saveIndustryToPoliticianContributions((List<PoliticianIndustryContributionTotals>)pairs.getValue());
        }

        return totalsHashMap;
    }

    /**
     * Get a HashMap of Cycle->Industry-Category contribution lists for all congressional cycles a politician has been apart of
     * @param bioguideId
     * @return
     */
    public HashMap<Integer, List<PoliticianIndustryContributionTotals>> getIndustryCategoryToPoliticianTotalsPerCongress(String bioguideId) {

        HashMap<Integer, List<PoliticianIndustryContributionTotals>> totalsHashMap = politicianIndustryHadoopRepo.getIndustryCategoryToPoliticianTotalsPerCongress(bioguideId);

        //Cache sums to MongoDB

        //Get an iterator for the values in the hash map
        Iterator it = totalsHashMap.entrySet().iterator();
        Map.Entry pairs;

        //Iterate through all entry pairs in the map and update the TermTotalsMap with the values.
        while (it.hasNext()) {
            pairs = (Map.Entry) it.next();

            if (politicianIndustryMongoRepo.countIndustryCategoryToPoliticianContributions(bioguideId, (Integer) pairs.getKey()) <= 0)
                politicianIndustryMongoRepo.saveIndustryToPoliticianContributions((List<PoliticianIndustryContributionTotals>)pairs.getValue());
        }

        return totalsHashMap;
    }

    /**
     * Get a HashMap of Cycle->Industry-Category contribution lists for all congressional cycles a politician has been apart of
     * @param bioguideId
     * @return
     */
    public HashMap<Integer, List<PoliticianIndustryContributionTotals>> getIndustryCategoryToPoliticianTotalsPerCongress(String bioguideId, long beginTimestamp, long endTimestamp) {

        Integer[] cycles = timeService.getCongressionalCyclesByTimeRange(beginTimestamp, endTimestamp);

        //Query MongoDB for industry to politician objects
        Iterator<PoliticianIndustryContributionTotals> totalsIterator = politicianIndustryMongoRepo.getIndustryCategoryToPoliticianContributionsIterator(bioguideId, cycles);

        HashMap<Integer, List<PoliticianIndustryContributionTotals>> totalsHashMap = new HashMap<>(30);

        //Add industry totals to the HashMap. Check the size, if it's zero, fall back to Impala.
        while (totalsIterator != null && totalsIterator.hasNext()) {
            PoliticianIndustryContributionTotals industryTotals = totalsIterator.next();

            //If the hashmap already has a list of industry totals for the object's cycle
            if (totalsHashMap.containsKey(industryTotals.getCongress()))
                totalsHashMap.get(industryTotals.getCongress()).add(industryTotals);
                //If the hashmap doesn't contain a list of industry totals, make it
            else {
                List<PoliticianIndustryContributionTotals> totalsList = new LinkedList<>();
                totalsList.add(industryTotals);
                totalsHashMap.put(industryTotals.getCongress(), totalsList);
            }
        }

        //Ensure that all the necessary cycles were returned from MongoDB, if not, we'll use Impala
        if (totalsHashMap.size() > 0) {
            boolean totalsMapContainsAllCycles = true;

            for (int i = 0; i < cycles.length; i++)
                totalsMapContainsAllCycles = totalsHashMap.containsKey(cycles[i]) ? totalsMapContainsAllCycles : false;

            if (totalsMapContainsAllCycles)
                return totalsHashMap;
        }

        //Fall back to Impala if MongoDB did not have the sums cached
        totalsHashMap = politicianIndustryHadoopRepo.getIndustryCategoryToPoliticianTotalsPerCongress(bioguideId, beginTimestamp, endTimestamp);

        //Cache sums to MongoDB

        //Get an iterator for the values in the hash map
        Iterator it = totalsHashMap.entrySet().iterator();
        Map.Entry pairs;

        //Iterate through all entry pairs in the map and update the TermTotalsMap with the values.
        while (it.hasNext()) {
            pairs = (Map.Entry) it.next();

            if (politicianIndustryMongoRepo.countIndustryCategoryToPoliticianContributions(bioguideId, (Integer) pairs.getKey()) <= 0)
                politicianIndustryMongoRepo.saveIndustryToPoliticianContributions((List<PoliticianIndustryContributionTotals>)pairs.getValue());
        }

        return totalsHashMap;
    }


    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }

    @Autowired
    public void setPoliticianIndustryMongoRepo(PoliticianIndustryMongoRepo politicianIndustryMongoRepo) {
        this.politicianIndustryMongoRepo = politicianIndustryMongoRepo;
    }

    @Autowired
    public void setPoliticianIndustryHadoopRepo(PoliticianIndustryHadoopRepo politicianIndustryHadoopRepo) {
        this.politicianIndustryHadoopRepo = politicianIndustryHadoopRepo;
    }
}
