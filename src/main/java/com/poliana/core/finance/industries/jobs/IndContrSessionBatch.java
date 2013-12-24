package com.poliana.core.finance.industries.jobs;

import com.poliana.core.finance.industries.repositories.IndustryContributionMongoRepo;
import com.poliana.core.finance.industries.IndustryContributionService;
import com.poliana.core.entities.entities.Industry;
import com.poliana.core.entities.repositories.EntitiesMongoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
@Component
public class IndContrSessionBatch {

    @Autowired
    private IndustryContributionService industryContributionService;

    @Autowired
    private EntitiesMongoRepo entitiesMongoRepo;

    @Autowired
    private IndustryContributionMongoRepo industryContributionMongoRepo;

    public void processIndustryTotals(int congress) {
        Iterator<Industry> industries = entitiesMongoRepo.getIndustries();
        while (industries.hasNext()) {
            String industry = industries.next().getIndustryId();
            industryContributionMongoRepo.saveIndTimeRangeTotal(
                    industryContributionService.industryTimeRangeTotals(industry,congress,24));
        }
    }
}
