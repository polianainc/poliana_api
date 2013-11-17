package com.poliana.jobs;

import com.poliana.bills.entities.Bill;
import com.poliana.bills.entities.VoteGT.VoteGT;
import com.poliana.bills.models.Vote;
import com.poliana.bills.repositories.BillCRUDRepo;
import com.poliana.bills.repositories.BillHadoopRepo;
import com.poliana.bills.repositories.VoteGTRepo;
import com.poliana.bills.repositories.VotesCRUDRepo;
import com.poliana.campaignFinance.repositories.ContributionHadoopRepo;
import com.poliana.campaignFinance.repositories.IndustryCRUDRepo;
import com.poliana.entities.models.Industry;
import com.poliana.entities.repositories.EntitiesHadoopRepo;
import com.poliana.entities.repositories.LegislatorsCRUDRepo;
import com.poliana.jobs.batch.MapBillRelationships;
import com.poliana.jobs.batch.GenerateTestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    private BillCRUDRepo billCRUDRepo;
    @Autowired
    private EntitiesHadoopRepo entitiesHadoopRepo;
    @Autowired
    private ContributionHadoopRepo contributionHadoopRepo;
    @Autowired
    private VoteGTRepo voteGTRepo;
    @Autowired
    private VotesCRUDRepo votesCRUDRepo;
    @Autowired
    private LegislatorsCRUDRepo legislatorsCRUDRepo;
    @Autowired
    private MapBillRelationships gtProcess;
    @Autowired
    private IndustryCRUDRepo industryCRUDRepo;
    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

//        System.out.println("Loading bills from impala");
//        List<Bill> bills = billHadoopRepo.billsHavingVotesByCongress(113, 0);
//        System.out.println("Processing bills");
//        billCRUDRepo.save(gtProcess.processBills(bills));
        gtProcess.mapVotesToBills();

//        List<Industry> industries = entitiesHadoopRepo.getIndustries();
//        industryCRUDRepo.save(industries);

//        List<VoteGT> voteGTs = voteGTRepo.findAllByCongress(113);
//        votesCRUDRepo.save(gtProcess.processVotes(voteGTs));

        System.out.println("yay!");
    }
}

