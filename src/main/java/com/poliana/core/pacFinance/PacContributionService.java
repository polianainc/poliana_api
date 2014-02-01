package com.poliana.core.pacFinance;

import com.poliana.core.pacFinance.entities.PacContributionTotalsMap;
import com.poliana.core.pacFinance.repositories.PacContributionHadoopRepo;
import com.poliana.core.pacFinance.repositories.PacContributionMongoRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * Get a map of BioguideId->Total sum of PAC contributions to all legislators during the given
     * congressional cycle
     * @param congress
     * @return
     */
    public PacContributionTotalsMap getPacContributionTotalsMap(String pacId, int congress) {

        //Check MongoDB for a cached industry chamber total document
        PacContributionTotalsMap chamberTotals =
                pacContributionMongoRepo.getPacContributionTotalsMap(pacId, congress);

        //If it exists, return it
        if (chamberTotals != null)
            return chamberTotals;

        //If not we'll use Impala to get it
        chamberTotals = pacContributionHadoopRepo.getPacContributionTotalsMap(pacId, congress);

        if (chamberTotals != null)
            pacContributionMongoRepo.savePacContributionTotalsMap(chamberTotals);

        return chamberTotals;
    }

    /**
     * Get a map of BioguideId->Total sum of PAC contributions to all legislators in a given chamber during the given
     * congressional cycles
     * @param chamber
     * @param congress
     * @return
     */
    public PacContributionTotalsMap getPacContributionTotalsMap(String pacId, String chamber, int congress) {

        //Check MongoDB for a cached pac chamber total document
        PacContributionTotalsMap chamberTotals =
                pacContributionMongoRepo.getPacContributionTotalsMap(pacId, chamber, congress);

        //If it exists, return it
        if (chamberTotals != null)
            return chamberTotals;

        //If not we'll use Impala to get it
        chamberTotals = pacContributionHadoopRepo.getPacContributionTotalsMap(pacId, chamber, congress);

        if (chamberTotals != null)
            pacContributionMongoRepo.savePacContributionTotalsMap(chamberTotals);

        return chamberTotals;
    }

    /**
     * Get a map of BioguideId->Total sum of industry contributions to all legislators during the given
     * time period
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public PacContributionTotalsMap getPacContributionTotalsMap(String pacId, long beginTimestamp, long endTimestamp) {

        //Check MongoDB for a cached pac chamber total document
        PacContributionTotalsMap chamberTotals =
                pacContributionMongoRepo.getPacContributionTotalsMap(pacId, beginTimestamp, endTimestamp);

        //If it exists, return it
        if (chamberTotals != null)
            return chamberTotals;

        //If not we'll use Impala to get it
        chamberTotals = pacContributionHadoopRepo.getPacContributionTotalsMap(pacId, beginTimestamp, endTimestamp);

        if (chamberTotals != null)
            pacContributionMongoRepo.savePacContributionTotalsMap(chamberTotals);

        return chamberTotals;
    }

    /**
     * Get a map of BioguideId->Total sum of pac contributions to all legislators in a given chamber during the given
     * time period
     * @param chamber
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public PacContributionTotalsMap getPacContributionTotalsMap(String pacId, String chamber, long beginTimestamp, long endTimestamp) {

        //Check MongoDB for a cached pac chamber total document
        PacContributionTotalsMap chamberTotals =
                pacContributionMongoRepo.getPacContributionTotalsMap(pacId, chamber, beginTimestamp, endTimestamp);

        //If it exists, return it
        if (chamberTotals != null)
            return chamberTotals;

        //If not we'll use Impala to get it
        chamberTotals = pacContributionHadoopRepo.getPacContributionTotalsMap(pacId, chamber, beginTimestamp, endTimestamp);

        if (chamberTotals != null)
            pacContributionMongoRepo.savePacContributionTotalsMap(chamberTotals);

        return chamberTotals;
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
