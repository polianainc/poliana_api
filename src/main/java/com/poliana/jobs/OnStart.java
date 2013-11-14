package com.poliana.jobs;

import com.poliana.bills.entities.VoteGT.VoteGT;
import com.poliana.bills.repositories.BillHadoopRepo;
import com.poliana.bills.repositories.VoteRepo;
import com.poliana.campaignFinance.entities.IndPartyTotals;
import com.poliana.campaignFinance.entities.IndToPolContrTotals;
import com.poliana.campaignFinance.repositories.ContributionRepo;
import com.poliana.entities.entities.Legislator;
import com.poliana.entities.repositories.EntitiesHadoopRepo;
import com.poliana.entities.repositories.LegislatorsCRUDRepo;
import com.poliana.jobs.batch.GenerateTestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@Profile("production")
public class OnStart implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private GenerateTestData testData;
    @Autowired
    private BillHadoopRepo billHadoopRepo;
    @Autowired
    private EntitiesHadoopRepo entitiesHadoopRepo;
    @Autowired
    private ContributionRepo contributionRepo;
    @Autowired
    private VoteRepo voteRepo;
    @Autowired
    private LegislatorsCRUDRepo legislatorsCRUDRepo;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        List<IndToPolContrTotals> industryContributions =
                contributionRepo.topIndToPolContrTotalsByYearMonth(2012, 1, 5, "total");
        List<IndToPolContrTotals> industryContributionsByIndustryId =
                contributionRepo.topIndToPolContrTotalsByIndustryYearMonth("X1200", 2012, 1, 5, "total");

        List<IndToPolContrTotals> indToPolContrTotalsByBioguideId =
                contributionRepo.topIndToPolContrTotalsByBioguideYearMonth("G000225", 2012, 1, 5, "total");
        List<IndPartyTotals> partyIndustryTotals =
                contributionRepo.indPartyContrTotalsByIndustryYearMonth("X1200", 2012, 1);
        List<VoteGT> votes = voteRepo.findAll();

//        List<Legislator> legislators = entitiesHadoopRepo.getAllLegistlators();
//        legislatorsCRUDRepo.save(legislators);

//        List<Legislator> legislator = legislatorsCRUDRepo.findByLisId("S307");

        System.out.println("yay!");
    }
}

