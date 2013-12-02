package com.poliana.core.campaignFinance.jobs;

import com.poliana.core.campaignFinance.repositories.ContributionMongoRepo;
import com.poliana.core.campaignFinance.services.ContributionService;
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
    private ContributionService contributionService;

    @Autowired
    private EntitiesMongoRepo entitiesMongoRepo;

    @Autowired
    private ContributionMongoRepo contributionMongoRepo;

    public void processIndustryTotals(int congress) {
        Iterator<Industry> industries = entitiesMongoRepo.getIndustries();
        while (industries.hasNext()) {
            String industry = industries.next().getIndustryId();
            contributionMongoRepo.saveIndTimeRangeTotal(
                    contributionService.industryTimeRangeTotals(industry,congress,24));
        }
    }
}
