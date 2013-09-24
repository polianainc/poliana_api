package com.poliana.jobs;

import com.poliana.bills.entities.BillAction;
import com.poliana.bills.repositories.BillRepo;
import com.poliana.codes.repositories.CodesRepo;
import com.poliana.expenditures.repositories.ExpendituresRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import com.poliana.jobs.batch.BillAnalysis;
import com.poliana.jobs.batch.BillParse;
import com.poliana.contributions.repositories.ContributionRepo;
import com.poliana.contributions.services.ContributionService;

import java.util.List;

@Component
public class OnStart extends JobBase implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ContributionService influence;
    @Autowired
    private ContributionRepo contributioRepo;
    @Autowired
    private BillAnalysis billAnalysis;
    @Autowired
    private BillParse billParse;
    @Autowired
    private CodesRepo codesRepo;
    @Autowired
    private ExpendituresRepo expeditureRepo;
    @Autowired
    private BillRepo billRepo;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        List<FecContribution> fecContributions = contributioRepo.fecContributions();
//        List<IndividualContribution> individualContributions = contributioRepo.individualContributions();
//        List<PacToCandidateContribution> pacToCandidateContributions = contributioRepo.pacToCandidateContributions();
//        List<PacToPacContribution> pacToPacContributions = contributioRepo.pacToPacContributions();
//        List<Industry> industries = codesRepo.getIndustries();
//        List<CongCommittee> congCommittees = codesRepo.getCongCommitties();
//        List<CommitteeContributor> committeeContributors = codesRepo.getCommitteeContributors();
//        List<CandidateIds> candidateIds = codesRepo.getCandidateIds();
//        List<CandidateContributor> candidateContributors = codesRepo.getCandidateContributors();
//        billRepo.execute("SELECT * FROM bills_json LIMIT 10");
        List<BillAction> billActionList = billRepo.billActions("s743-113");
        System.out.print("YAY");
    }
}
