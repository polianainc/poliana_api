package com.poliana.core.pacFinance;

import com.poliana.core.pacFinance.entities.PacPoliticianContributionTotals;
import com.poliana.core.pacFinance.repositories.PacContributionHadoopRepo;
import com.poliana.core.pacFinance.repositories.PacContributionMongoRepo;
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

    private PacContributionMongoRepo pacContributionMongoRepo;
    private PacContributionHadoopRepo pacContributionHadoopRepo;

    private static final Logger logger = Logger.getLogger(PacContributionService.class);


    /**
     * Get a list of PAC contribution totals in a given congressional cycle for a certain politician.
     * @param bioguideId
     * @return
     */
    public List<PacPoliticianContributionTotals> getPacTotals(String bioguideId, int congress) {

        //Check MongoDB for PAC contributions
        List<PacPoliticianContributionTotals> pacTotals = pacContributionMongoRepo.getPacPoliticianContrTotalsMongo(bioguideId, congress);

        //If MongoDB returned a list, use it
        if (pacTotals.size() > 0)
            return pacTotals;

        //If MongoDB doesn't have the sums cached, fall back to Impala
        pacTotals = pacContributionHadoopRepo.getPacPoliticianContributionTotals(bioguideId, congress);

        //If Impala had sums to return, save them
        if (pacTotals.size() > 0)
            pacContributionMongoRepo.savePacPoliticianContrTotals(pacTotals);

        return pacTotals;
    }

    /**
     * Get a HashMap of Cycle->List<PacPoliticianContrTotals for all congressional cycles a politician has been apart of
     * @param bioguideId
     * @return
     */
    public HashMap<Integer, List<PacPoliticianContributionTotals>> getPacTotalsAllTime(String bioguideId) {

        HashMap<Integer, List<PacPoliticianContributionTotals>> totalsHashMap = getPacTotalsAllTimeMongo(bioguideId);

        //A size greater than 0 means that MongoDB had the sums cached
        if (totalsHashMap.size() > 0)
            return totalsHashMap;

        //Fall back to Impala if MongoDB did not have the sums cached
        totalsHashMap = pacContributionHadoopRepo.getAllLegislatorReceivedPacTotals(bioguideId);

        //Get an iterator for the values in the hash map
        Iterator it = totalsHashMap.entrySet().iterator();
        Map.Entry pairs;

        //Iterate through all entry pairs in the map and update the TermTotalsMap with the values.
        while (it.hasNext()) {
            pairs = (Map.Entry) it.next();

            if (pacContributionMongoRepo.countPacPoliticianContrTotals(bioguideId, (Integer) pairs.getKey()) <= 0)
                pacContributionMongoRepo.savePacPoliticianContrTotals((List<PacPoliticianContributionTotals>)pairs.getValue());
        }

        return totalsHashMap;
    }

    /**
     * Get a map of Cycle->PAC contribution lists for a politician from the MongoDB cache
     * @param bioguideId
     * @return
     * @see PacPoliticianContributionTotals
     */
    private HashMap<Integer, List<PacPoliticianContributionTotals>> getPacTotalsAllTimeMongo(String bioguideId) {

        Iterator<PacPoliticianContributionTotals> totalsIterator = pacContributionMongoRepo.getAllPacPoliticianContrTotals(bioguideId);

        HashMap<Integer, List<PacPoliticianContributionTotals>> totalsHashMap = new HashMap<>(30);

        //Add PAC totals to the HashMap. Check the size, if it's zero, fall back to Impala.
        while (totalsIterator.hasNext()) {
            PacPoliticianContributionTotals pacTotals = totalsIterator.next();

            //If the hashmap already has a list of PAC totals for the object's cycle
            if (totalsHashMap.containsKey(pacTotals.getCycle()))
                totalsHashMap.get(pacTotals.getCycle()).add(pacTotals);
                //If the hashmap doesn't contain a list of PAC totals, make it
            else {
                List<PacPoliticianContributionTotals> totalsList = new LinkedList<>();
                totalsList.add(pacTotals);
                totalsHashMap.put(pacTotals.getCycle(), totalsList);
            }
        }

        return totalsHashMap;
    }

    @Autowired
    public void setPacContributionMongoRepo(PacContributionMongoRepo pacContributionMongoRepo) {
        this.pacContributionMongoRepo = pacContributionMongoRepo;
    }

    @Autowired
    public void setPacContributionHadoopRepo(PacContributionHadoopRepo pacContributionHadoopRepo) {
        this.pacContributionHadoopRepo = pacContributionHadoopRepo;
    }
}
