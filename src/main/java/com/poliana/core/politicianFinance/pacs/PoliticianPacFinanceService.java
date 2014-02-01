package com.poliana.core.politicianFinance.pacs;

import com.poliana.core.time.TimeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author David Gilmore
 * @date 1/26/14
 */
@Service
public class PoliticianPacFinanceService {

    private TimeService timeService;

    private PoliticianPacMongoRepo politicianPacMongoRepo;
    private PoliticianPacHadoopRepo politicianPacHadoopRepo;

    private static final Logger logger = Logger.getLogger(PoliticianPacFinanceService.class);


    public PoliticianPacFinanceService() {

        this.timeService = new TimeService();
    }

    /**
     * Get a list of contributing PAC totals to a given legislator
     * @param bioguideId
     * @return
     */
    public List<PoliticianPacContributionsTotals> getPacToPoliticianTotals(String bioguideId) {

        List<PoliticianPacContributionsTotals> totalsList =
                politicianPacMongoRepo.getPacToPoliticianContributions(bioguideId);

        if (totalsList != null && totalsList.size() > 0)
            return totalsList;

        //If MongoDB didn't return, fall back to Impala.
        totalsList = politicianPacHadoopRepo.getPacToPoliticianContributions(bioguideId);

        //If Impala had something to return, save it to MongoDB
        if (totalsList != null && totalsList.size() > 0)
            politicianPacMongoRepo.savePacToPoliticianContributions(totalsList);

        return totalsList;
    }

    /**
     * Get a list of contribution totals from all contributing PACS summed over the given time range
     * @param bioguideId
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public List<PoliticianPacContributionsTotals> getPacToPoliticianTotals(String bioguideId, long beginTimestamp, long endTimestamp) {

        List<PoliticianPacContributionsTotals> totalsList =
                politicianPacMongoRepo.getPacToPoliticianContributions(bioguideId, beginTimestamp, endTimestamp);

        if (totalsList != null && totalsList.size() > 0)
            return totalsList;

        //If MongoDB didn't return, fall back to Impala.
        totalsList = politicianPacHadoopRepo.getPacToPoliticianContributions(bioguideId, beginTimestamp, endTimestamp);

        //If Impala had something to return, save it to MongoDB
        if (totalsList != null && totalsList.size() > 0)
            politicianPacMongoRepo.savePacToPoliticianContributions(totalsList);

        return totalsList;
    }

    /**
     * Get a HashMap of Cycle->PAC contribution lists for all congressional cycles in the given time range
     * @param bioguideId
     * @return
     */
    public HashMap<Integer, List<PoliticianPacContributionsTotals>> getPacToPoliticianTotalsPerCongress(String bioguideId) {

        HashMap<Integer, List<PoliticianPacContributionsTotals>> totalsHashMap = politicianPacHadoopRepo.getPacToPoliticianTotalsPerCongress(bioguideId);

        //Cache sums to MongoDB

        //Get an iterator for the values in the hash map
        Iterator it = totalsHashMap.entrySet().iterator();
        Map.Entry pairs;

        //Iterate through all entry pairs in the map and update the TermTotalsMap with the values.
        while (it.hasNext()) {
            pairs = (Map.Entry) it.next();

            if (politicianPacMongoRepo.countPacToPoliticianContributions(bioguideId, (Integer) pairs.getKey()) <= 0)
                politicianPacMongoRepo.savePacToPoliticianContributions((List<PoliticianPacContributionsTotals>) pairs.getValue());
        }

        return totalsHashMap;
    }

    /**
     * Get a HashMap of Cycle->PAC contribution lists for all congressional cycles in the given time range
     * @param bioguideId
     * @return
     */
    public HashMap<Integer, List<PoliticianPacContributionsTotals>> getPacToPoliticianTotalsPerCongress(String bioguideId, long beginTimestamp, long endTimestamp) {

        Integer[] cycles = timeService.getCongressionalCyclesByTimeRange(beginTimestamp, endTimestamp);

        //Query MongoDB for PAC to politician objects
        Iterator<PoliticianPacContributionsTotals> totalsIterator = politicianPacMongoRepo.getPacToPoliticianContributionsIterator(bioguideId, cycles);

        HashMap<Integer, List<PoliticianPacContributionsTotals>> totalsHashMap = new HashMap<>(30);

        //Add industry totals to the HashMap. Check the size, if it's zero, fall back to Impala.
        while (totalsIterator != null && totalsIterator.hasNext()) {
            PoliticianPacContributionsTotals pacTotals = totalsIterator.next();

            //If the hashmap already has a list of industry totals for the object's cycle
            if (totalsHashMap.containsKey(pacTotals.getCongress()))
                totalsHashMap.get(pacTotals.getCongress()).add(pacTotals);
                //If the hashmap doesn't contain a list of industry totals, make it
            else {
                List<PoliticianPacContributionsTotals> totalsList = new LinkedList<>();
                totalsList.add(pacTotals);
                totalsHashMap.put(pacTotals.getCongress(), totalsList);
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
        totalsHashMap = politicianPacHadoopRepo.getPacToPoliticianTotalsPerCongress(bioguideId, beginTimestamp, endTimestamp);

        //Cache sums to MongoDB

        //Get an iterator for the values in the hash map
        Iterator it = totalsHashMap.entrySet().iterator();
        Map.Entry pairs;

        //Iterate through all entry pairs in the map and update the TermTotalsMap with the values.
        while (it.hasNext()) {
            pairs = (Map.Entry) it.next();

            if (politicianPacMongoRepo.countPacToPoliticianContributions(bioguideId, (Integer) pairs.getKey()) <= 0)
                politicianPacMongoRepo.savePacToPoliticianContributions((List<PoliticianPacContributionsTotals>) pairs.getValue());
        }

        return totalsHashMap;
    }

    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }

    @Autowired
    public void setPoliticianPacMongoRepo(PoliticianPacMongoRepo politicianPacMongoRepo) {
        this.politicianPacMongoRepo = politicianPacMongoRepo;
    }

    @Autowired
    public void setPoliticianPacHadoopRepo(PoliticianPacHadoopRepo politicianPacHadoopRepo) {
        this.politicianPacHadoopRepo = politicianPacHadoopRepo;
    }
}
