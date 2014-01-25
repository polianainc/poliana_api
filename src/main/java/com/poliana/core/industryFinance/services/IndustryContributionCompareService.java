package com.poliana.core.industryFinance.services;

import com.poliana.core.ideology.IdeologyMatrix;
import com.poliana.core.ideology.LegislatorIdeology;
import com.poliana.core.industryFinance.entities.IndustryContributionCompare;
import com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author David Gilmore
 * @date 1/19/14
 */
@Service
public class IndustryContributionCompareService {

    private static final Logger logger = Logger.getLogger(IndustryContributionCompareService.class);


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


}
