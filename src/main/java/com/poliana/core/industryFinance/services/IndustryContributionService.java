package com.poliana.core.industryFinance.services;

import com.poliana.core.industryFinance.repositories.IndustryContributionHadoopRepo;
import com.poliana.core.industryFinance.repositories.IndustryContributionMongoRepo;
import com.poliana.core.industryFinance.entities.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author David Gilmore
 * @date 11/15/13
 */
@Service
public class IndustryContributionService {

    private IndustryContributionMongoRepo industryContributionMongoRepo;
    private IndustryContributionHadoopRepo industryContributionHadoopRepo;


    private static final Logger logger = Logger.getLogger(IndustryContributionService.class);

    /**
     * Get a map of BioguideId->Total sum of industry contributions to all legislators during the given
     * congressional cycle
     * @param congress
     * @return
     */
    public IndustryContributionTotalsMap getIndustryContributionTotalsMap(String industryId, int congress) {

        //Check MongoDB for a cached industry chamber total document
        IndustryContributionTotalsMap chamberTotals =
                industryContributionMongoRepo.getIndustryContributionTotalsMap(industryId, congress);

        //If it exists, return it
        if (chamberTotals != null)
            return chamberTotals;

        //If not we'll use Impala to get it
        chamberTotals = industryContributionHadoopRepo.getIndustryContributionTotalsMap(industryId, congress);

        if (chamberTotals != null)
            industryContributionMongoRepo.saveIndustryContributionTotalsMap(chamberTotals);

        return chamberTotals;
    }

    /**
     * Get a map of BioguideId->Total sum of industry contributions to all legislators during the given
     * congressional cycles
     * @param congress
     * @return
     */
    public IndustryContributionTotalsMap getIndustryCategoryContributionTotalsMap(String categoryId, int congress) {

        //Check MongoDB for a cached industry chamber total document
        IndustryContributionTotalsMap chamberTotals =
                industryContributionMongoRepo.getIndustryCategoryContributionTotalsMap(categoryId, congress);

        //If it exists, return it
        if (chamberTotals != null)
            return chamberTotals;

        chamberTotals = industryContributionHadoopRepo.getIndustryCategoryContributionTotalsMap(categoryId, congress);

        if (chamberTotals != null) {
            chamberTotals.setIndustryId(null);
            chamberTotals.setCategoryId(categoryId);
            industryContributionMongoRepo.saveIndustryContributionTotalsMap(chamberTotals);
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
    public IndustryContributionTotalsMap getIndustryContributionTotalsMap(String industryId, String chamber, int congress) {

        //Check MongoDB for a cached industry chamber total document
        IndustryContributionTotalsMap chamberTotals =
                industryContributionMongoRepo.getIndustryContributionTotalsMapByChamber(industryId, chamber, congress);

        //If it exists, return it
        if (chamberTotals != null)
            return chamberTotals;

        //If not we'll use Impala to get it
        chamberTotals = industryContributionHadoopRepo.getIndustryContributionTotalsMapByChamber(industryId, chamber, congress);

        if (chamberTotals != null)
            industryContributionMongoRepo.saveIndustryContributionTotalsMap(chamberTotals);

        return chamberTotals;
    }

    /**
     * Get a map of BioguideId->Total sum of industry contributions to all legislators in a given chamber during the given
     * congressional cycles
     * @param chamber
     * @param congress
     * @return
     */
    public IndustryContributionTotalsMap getIndustryCategoryContributionTotalsMap(String categoryId, String chamber, int congress) {

        //Check MongoDB for a cached industry chamber total document
        IndustryContributionTotalsMap chamberTotals =
                industryContributionMongoRepo.getIndustryCategoryContributionTotalsMapByChamber(categoryId, chamber, congress);

        //If it exists, return it
        if (chamberTotals != null)
            return chamberTotals;

        chamberTotals = industryContributionHadoopRepo.getIndustryCategoryContributionTotalsMap(categoryId, chamber, congress);

        if (chamberTotals != null) {
            chamberTotals.setIndustryId(null);
            chamberTotals.setCategoryId(categoryId);
            industryContributionMongoRepo.saveIndustryContributionTotalsMap(chamberTotals);
        }

        return chamberTotals;
    }

    /**
     * Get a map of BioguideId->Total sum of industry contributions to all legislators in a given chamber during the given
     * congressional cycles
     * @param chamber
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public IndustryContributionTotalsMap getIndustryContributionTotalsMap(String industryId, String chamber, long beginTimestamp, long endTimestamp) {

        //Check MongoDB for a cached industry chamber total document
        IndustryContributionTotalsMap chamberTotals =
                industryContributionMongoRepo.getIndustryContributionTotalsMapByChamber(industryId, chamber, beginTimestamp, endTimestamp);

        //If it exists, return it
        if (chamberTotals != null)
            return chamberTotals;

        //If not we'll use Impala to get it
        chamberTotals = industryContributionHadoopRepo.getIndustryContributionTotalsMapByChamber(industryId, chamber, beginTimestamp, endTimestamp);

        if (chamberTotals != null)
            industryContributionMongoRepo.saveIndustryContributionTotalsMap(chamberTotals);

        return chamberTotals;
    }

    /**
     * Get a map of BioguideId->Total sum of industry contributions to all legislators in a given chamber during the given
     * congressional cycles
     * @param chamber
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public IndustryContributionTotalsMap getIndustryCategoryContributionTotalsMap(String categoryId, String chamber, long beginTimestamp, long endTimestamp) {

        //Check MongoDB for a cached industry chamber total document
        IndustryContributionTotalsMap chamberTotals =
                industryContributionMongoRepo.getIndustryCategoryContributionTotalsMapByChamber(categoryId, chamber, beginTimestamp, endTimestamp);

        //If it exists, return it
        if (chamberTotals != null)
            return chamberTotals;

        chamberTotals = industryContributionHadoopRepo.getIndustryCategoryContributionTotalsMapByChamber(categoryId, chamber, beginTimestamp, endTimestamp);

        if (chamberTotals != null)
            industryContributionMongoRepo.saveIndustryContributionTotalsMap(chamberTotals);


        return chamberTotals;
    }

    @Autowired
    public void setIndustryContributionMongoRepo(IndustryContributionMongoRepo industryContributionMongoRepo) {
        this.industryContributionMongoRepo = industryContributionMongoRepo;
    }

    @Autowired
    public void setIndustryContributionHadoopRepo(IndustryContributionHadoopRepo industryContributionHadoopRepo) {
        this.industryContributionHadoopRepo = industryContributionHadoopRepo;
    }
}