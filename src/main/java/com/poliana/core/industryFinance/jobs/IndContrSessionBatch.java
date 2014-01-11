package com.poliana.core.industryFinance.jobs;

import com.poliana.core.industries.IndustryRepo;
import com.poliana.core.industryFinance.IndustryContributionService;
import com.poliana.core.industries.Industry;
import com.poliana.core.industryFinance.IndustryContributionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * @author David Gilmore
 * @date 11/22/13
 */
@Component
public class IndContrSessionBatch {

    private IndustryContributionService industryContributionService;
    private IndustryRepo industryRepo;
    private IndustryContributionRepo industryContributionRepo;

    /**
     * Save all industry totals for a given congress
     * @param congress
     */
    public void processIndustryTotals(int congress) {

        Iterator<Industry> industries = industryRepo.getIndustriesFromMongo();

        while (industries.hasNext()) {
            String industry = industries.next().getIndustryId();
            industryContributionRepo.saveIndTimeRangeTotal(
                    industryContributionService.getIndustryTimeRangeTotals(industry, congress, 24));
        }
    }

    @Autowired
    public void setIndustryContributionService(IndustryContributionService industryContributionService) {
        this.industryContributionService = industryContributionService;
    }

    @Autowired
    public void setIndustryRepo(IndustryRepo industryRepo) {
        this.industryRepo = industryRepo;
    }

    @Autowired
    public void setIndustryContributionRepo(IndustryContributionRepo industryContributionRepo) {
        this.industryContributionRepo = industryContributionRepo;
    }
}
