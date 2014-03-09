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
    private PoliticianIndustryRedisRepo politicianIndustryRedisRepo;

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
    public List<PoliticianIndustryContributionsTotals> getIndustryToPoliticianTotals(String bioguideId, int congress) {

        List<PoliticianIndustryContributionsTotals> totalsList = politicianIndustryMongoRepo.getIndustryToPoliticianContributions(bioguideId, congress);

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
    public List<PoliticianIndustryContributionsTotals> getIndustryCategoryToPoliticianTotals(String bioguideId, int congress) {

        List<PoliticianIndustryContributionsTotals> totalsList =
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
    public List<PoliticianIndustryContributionsTotals> getIndustryToPoliticianTotals(String bioguideId, long beginTimestamp, long endTimestamp) {

        List<PoliticianIndustryContributionsTotals> totalsList =
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
    public List<PoliticianIndustryContributionsTotals> getIndustryCategoryToPoliticianTotals(String bioguideId, long beginTimestamp, long endTimestamp) {

        List<PoliticianIndustryContributionsTotals> totalsList =
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
    public List<PoliticianIndustryContributionsTotals> getIndustryToPoliticianTotals(String bioguideId) {

        //Query MongoDB for industry to politician objects
        List<PoliticianIndustryContributionsTotals> totalsList =
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
    public List<PoliticianIndustryContributionsTotals> getIndustryCategoryToPoliticianTotals(String bioguideId) {

        //Query MongoDB for industry to politician objects
        List<PoliticianIndustryContributionsTotals> totalsList =
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
    @SuppressWarnings("unchecked")
    public HashMap<Integer, List<PoliticianIndustryContributionsTotals>> getIndustryToPoliticianTotalsPerCongress(String bioguideId) {

        HashMap<Integer, List<PoliticianIndustryContributionsTotals>> totalsHashMap = politicianIndustryHadoopRepo.getIndustryToPoliticianTotalsPerCongress(bioguideId);

        //Cache sums to MongoDB

        //Get an iterator for the values in the hash map
        Iterator it = totalsHashMap.entrySet().iterator();
        Map.Entry pairs;

        //Iterate through all entry pairs in the map and update the TermTotalsMap with the values.
        while (it.hasNext()) {
            pairs = (Map.Entry) it.next();

            politicianIndustryRedisRepo.setIndustryContributionsExistsInCache(bioguideId, (Integer) pairs.getKey());

            if (politicianIndustryMongoRepo.countIndustryToPoliticianContributions(bioguideId, (Integer) pairs.getKey()) <= 0)
                politicianIndustryMongoRepo.saveIndustryToPoliticianContributions((List<PoliticianIndustryContributionsTotals>)pairs.getValue());
        }

        return totalsHashMap;
    }

    /**
     * Get a HashMap of Cycle->Industry contribution lists for all congressional cycles in the given time range
     * @param bioguideId
     * @return
     */
    @SuppressWarnings("unchecked")
    public HashMap<Integer, List<PoliticianIndustryContributionsTotals>> getIndustryToPoliticianTotalsPerCongress(String bioguideId, long beginTimestamp, long endTimestamp) {

        Integer[] cycles = timeService.getCongressionalCyclesByTimeRange(beginTimestamp, endTimestamp);

        Iterator<PoliticianIndustryContributionsTotals> totalsIterator;
        HashMap<Integer, List<PoliticianIndustryContributionsTotals>> totalsHashMap = new HashMap<>(30);

        if (politicianIndustryRedisRepo.getIndustryContributionsExistInCache(bioguideId, cycles)) {

            //Query MongoDB for industry to politician objects
            totalsIterator = politicianIndustryMongoRepo.getIndustryToPoliticianContributionsIterator(bioguideId, cycles);

            //Add industry totals to the HashMap.
            while (totalsIterator != null && totalsIterator.hasNext()) {
                PoliticianIndustryContributionsTotals industryTotals = totalsIterator.next();

                //If the hashmap already has a list of industry totals for the object's cycle
                if (totalsHashMap.containsKey(industryTotals.getCongress()))
                    totalsHashMap.get(industryTotals.getCongress()).add(industryTotals);
                    //If the hashmap doesn't contain a list of industry totals, make it
                else {
                    List<PoliticianIndustryContributionsTotals> totalsList = new LinkedList<>();
                    totalsList.add(industryTotals);
                    totalsHashMap.put(industryTotals.getCongress(), totalsList);
                }
            }

            return totalsHashMap;

        }

        //Fall back to Impala if MongoDB did not have the sums cached
        totalsHashMap = politicianIndustryHadoopRepo.getIndustryToPoliticianTotalsPerCongress(bioguideId, beginTimestamp, endTimestamp);

        /*Cache sums to MongoDB
        Get an iterator for the values in the hash map */
        Iterator it = totalsHashMap.entrySet().iterator();
        Map.Entry pairs;

        //Iterate through all entry pairs in the map and update the TermTotalsMap with the values.
        while (it.hasNext()) {
            pairs = (Map.Entry) it.next();

            if (politicianIndustryMongoRepo.countIndustryToPoliticianContributions(bioguideId, (Integer) pairs.getKey()) <= 0)
                politicianIndustryMongoRepo.saveIndustryToPoliticianContributions((List<PoliticianIndustryContributionsTotals>)pairs.getValue());

        }

        //Update cache info in redis
        politicianIndustryRedisRepo.setIndustryContributionsExistsInCache(bioguideId, cycles);

        return totalsHashMap;
    }

    /**
     * Get a HashMap of Cycle->Industry-Category contribution lists for all congressional cycles a politician has been apart of
     * @param bioguideId
     * @return
     */
    @SuppressWarnings("unchecked")
    public HashMap<Integer, List<PoliticianIndustryContributionsTotals>> getIndustryCategoryToPoliticianTotalsPerCongress(String bioguideId) {

        HashMap<Integer, List<PoliticianIndustryContributionsTotals>> totalsHashMap = politicianIndustryHadoopRepo.getIndustryCategoryToPoliticianTotalsPerCongress(bioguideId);

        //Cache sums to MongoDB

        //Get an iterator for the values in the hash map
        Iterator it = totalsHashMap.entrySet().iterator();
        Map.Entry pairs;

        //Iterate through all entry pairs in the map and update the TermTotalsMap with the values.
        while (it.hasNext()) {
            pairs = (Map.Entry) it.next();

            politicianIndustryRedisRepo.setIndustryCategoryContributionsExistsInCache(bioguideId, (Integer) pairs.getKey());

            if (politicianIndustryMongoRepo.countIndustryCategoryToPoliticianContributions(bioguideId, (Integer) pairs.getKey()) <= 0)
                politicianIndustryMongoRepo.saveIndustryToPoliticianContributions((List<PoliticianIndustryContributionsTotals>)pairs.getValue());
        }

        return totalsHashMap;
    }

    /**
     * Get a HashMap of Cycle->Industry-Category contribution lists for all congressional cycles in the given time range
     * @param bioguideId
     * @return
     */
    public HashMap<Integer, List<PoliticianIndustryContributionsTotals>> getIndustryCategoryToPoliticianTotalsPerCongress(String bioguideId, long beginTimestamp, long endTimestamp) {

        Integer[] cycles = timeService.getCongressionalCyclesByTimeRange(beginTimestamp, endTimestamp);

        Iterator<PoliticianIndustryContributionsTotals> totalsIterator;
        HashMap<Integer, List<PoliticianIndustryContributionsTotals>> totalsHashMap = new HashMap<>(30);

        if (politicianIndustryRedisRepo.getIndustryCategoryContributionsExistsInCache(bioguideId, cycles)) {

            //Query MongoDB for industry to politician objects
             totalsIterator = politicianIndustryMongoRepo.getIndustryCategoryToPoliticianContributionsIterator(bioguideId, cycles);

            //Add industry totals to the HashMap.
            while (totalsIterator != null && totalsIterator.hasNext()) {
                PoliticianIndustryContributionsTotals industryTotals = totalsIterator.next();

                //If the hashmap already has a list of industry totals for the object's cycle
                if (totalsHashMap.containsKey(industryTotals.getCongress()))
                    totalsHashMap.get(industryTotals.getCongress()).add(industryTotals);
                    //If the hashmap doesn't contain a list of industry totals, make it
                else {
                    List<PoliticianIndustryContributionsTotals> totalsList = new LinkedList<>();
                    totalsList.add(industryTotals);
                    totalsHashMap.put(industryTotals.getCongress(), totalsList);
                }
            }

            return totalsHashMap;

        }

        //Fall back to Impala if MongoDB did not have the sums cached
        totalsHashMap = politicianIndustryHadoopRepo.getIndustryCategoryToPoliticianTotalsPerCongress(bioguideId, beginTimestamp, endTimestamp);

        /*Cache sums to MongoDB
        Get an iterator for the values in the hash map */
        Iterator it = totalsHashMap.entrySet().iterator();
        Map.Entry pairs;

        //Iterate through all entry pairs in the map and update the TermTotalsMap with the values.
        while (it.hasNext()) {
            pairs = (Map.Entry) it.next();

            if (politicianIndustryMongoRepo.countIndustryCategoryToPoliticianContributions(bioguideId, (Integer) pairs.getKey()) <= 0)
                politicianIndustryMongoRepo.saveIndustryToPoliticianContributions((List<PoliticianIndustryContributionsTotals>)pairs.getValue());

        }

        politicianIndustryRedisRepo.setIndustryCategoryContributionsExistsInCache(bioguideId, cycles);

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

    @Autowired
    public void setPoliticianIndustryRedisRepo(PoliticianIndustryRedisRepo politicianIndustryRedisRepo) {
        this.politicianIndustryRedisRepo = politicianIndustryRedisRepo;
    }
}
