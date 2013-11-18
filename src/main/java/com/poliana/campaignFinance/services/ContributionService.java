package com.poliana.campaignFinance.services;

import com.poliana.campaignFinance.entities.IndTimeRangeTotals;
import com.poliana.campaignFinance.repositories.ContributionHadoopRepo;
import com.poliana.entities.repositories.EntitiesRepo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author David Gilmore
 * @date 11/15/13
 */
public class ContributionService {

    @Autowired
    private EntitiesRepo entitiesRepo;
    @Autowired
    private ContributionHadoopRepo contributionRepo;

    public IndTimeRangeTotals industryTimeRangeTotalsByIndustryCongress(
            String industry, int congress) {


        return null;
    }


}
