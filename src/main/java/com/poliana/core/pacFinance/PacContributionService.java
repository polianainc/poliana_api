package com.poliana.core.pacFinance;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author David Gilmore
 * @date 1/4/14
 */
@Service
public class PacContributionService {

    private PacContributionRepo pacContributionRepo;

    private static final Logger logger = Logger.getLogger(PacContributionService.class);


    /**
     * Get a list of PAC contribution totals in a given congressional cycle for a certain politician.
     * @param bioguideId
     * @return
     */
    public List<PacPoliticianContrTotals> getPacTotals(String bioguideId, int congress) {

        //Check MongoDB for PAC contributions
        List<PacPoliticianContrTotals> pacTotals = pacContributionRepo.getPacPoliticianContrTotalsMongo(bioguideId, congress);

        //If MongoDB returned a list, use it
        if (pacTotals.size() > 0)
            return pacTotals;

        //If MongoDB doesn't have the sums cached, fall back to Impala
        pacTotals = pacContributionRepo.getPacPoliticianContrTotals(bioguideId, congress);

        //If Impala had sums to return, save them
        if (pacTotals.size() > 0)
            pacContributionRepo.savePacPoliticianContrTotals(pacTotals);

        return pacTotals;
    }

    /**
     * Get a HashMap of Cycle->List<PacPoliticianContrTotals for all congressional cycles a politician has been apart of
     * @param bioguideId
     * @return
     */
    public HashMap<Integer, List<PacPoliticianContrTotals>> getPacTotalsAllTime(String bioguideId) {

        HashMap<Integer, List<PacPoliticianContrTotals>> totalsHashMap = getPacTotalsAllTimeMongo(bioguideId);

        //A size greater than 0 means that MongoDB had the sums cached
        if (totalsHashMap.size() > 0)
            return totalsHashMap;

        //Fall back to Impala if MongoDB did not have the sums cached
        totalsHashMap = pacContributionRepo.getAllLegislatorReceivedPacTotals(bioguideId);

        //Get an iterator for the values in the hash map
        Iterator it = totalsHashMap.entrySet().iterator();
        Map.Entry pairs;

        //Iterate through all entry pairs in the map and update the TermTotalsMap with the values.
        while (it.hasNext()) {
            pairs = (Map.Entry) it.next();

            if (pacContributionRepo.countPacPoliticianContrTotals(bioguideId, (Integer) pairs.getKey()) <= 0)
                pacContributionRepo.savePacPoliticianContrTotals((List<PacPoliticianContrTotals>)pairs.getValue());
        }

        return totalsHashMap;
    }

    /**
     * Get a map of Cycle->PAC contribution lists for a politician from the MongoDB cache
     * @param bioguideId
     * @return
     * @see PacPoliticianContrTotals
     */
    private HashMap<Integer, List<PacPoliticianContrTotals>> getPacTotalsAllTimeMongo(String bioguideId) {

        Iterator<PacPoliticianContrTotals> totalsIterator = pacContributionRepo.getAllPacPoliticianContrTotals(bioguideId);

        HashMap<Integer, List<PacPoliticianContrTotals>> totalsHashMap = new HashMap<>(30);

        //Add PAC totals to the HashMap. Check the size, if it's zero, fall back to Impala.
        while (totalsIterator.hasNext()) {
            PacPoliticianContrTotals pacTotals = totalsIterator.next();

            //If the hashmap already has a list of PAC totals for the object's cycle
            if (totalsHashMap.containsKey(pacTotals.getCycle()))
                totalsHashMap.get(pacTotals.getCycle()).add(pacTotals);
                //If the hashmap doesn't contain a list of PAC totals, make it
            else {
                List<PacPoliticianContrTotals> totalsList = new LinkedList<>();
                totalsList.add(pacTotals);
                totalsHashMap.put(pacTotals.getCycle(), totalsList);
            }
        }

        return totalsHashMap;
    }

    @Autowired
    public void setPacContributionRepo(PacContributionRepo pacContributionRepo) {
        this.pacContributionRepo = pacContributionRepo;
    }
}
