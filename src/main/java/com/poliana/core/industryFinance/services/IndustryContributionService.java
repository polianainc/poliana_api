package com.poliana.core.industryFinance.services;

import com.poliana.core.ideology.IdeologyMatrix;
import com.poliana.core.ideology.LegislatorIdeology;
import com.poliana.core.industryFinance.IndustryContributionHadoopRepo;
import com.poliana.core.industryFinance.IndustryContributionMongoRepo;
import com.poliana.core.industryFinance.entities.*;
import com.poliana.core.time.TimeService;
import com.poliana.core.industries.IndustryRepo;
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
     * @param industryContributionTotalsMap
     * @return
     */
    public List<IndustryContributionCompare> getIndustryContributionsVsIdeology(
            IdeologyMatrix ideologyMatrix, IndustryContributionTotalsMap industryContributionTotalsMap) {

        List<IndustryContributionCompare> compareList = new LinkedList<>();

        if (ideologyMatrix == null || industryContributionTotalsMap == null)
            return new LinkedList<>();

        for (LegislatorIdeology legislatorIdeology: ideologyMatrix.getIdeologies()) {

            //Initialize a new comparison
            IndustryContributionCompare compare = new IndustryContributionCompare();

            //If the sums were made for a category, only the categoryId will be set, and vice verse
            String industry;
            if (industryContributionTotalsMap.getIndustryId() != null)
                industry = industryContributionTotalsMap.getIndustryName();
            else
                industry = industryContributionTotalsMap.getCategoryName();

            //Set values
            compare.setIndustry(industry);
            compare.setLegislator(legislatorIdeology.getName());
            compare.setParty(legislatorIdeology.getParty());
            compare.setReligion(legislatorIdeology.getReligion());

            try { //Try to get an amount from the contribution totals' sum map
                compare.setAmount(industryContributionTotalsMap.getSums().get(legislatorIdeology.getBioguideId()));
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
                industryContributionMongoRepo.getIndustryCategoryContributionTotalsMap(categoryId, chamber, congress);

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