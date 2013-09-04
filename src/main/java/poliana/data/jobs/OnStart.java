package poliana.data.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import poliana.data.jobs.batch.BillAnalysis;
import poliana.data.models.bill.ContributionTotals;
import poliana.data.models.bill.InfluenceGraph;
import poliana.data.repositories.bills.BillHiveJdbcRepo;
import poliana.data.services.bills.ContributionInfluence;

import java.util.List;

@Component
public class OnStart extends JobBase implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ContributionInfluence influence;
    @Autowired
    private BillHiveJdbcRepo billHiveJdbcRepo;
    @Autowired
    private BillAnalysis billAnalysis;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println(billHiveJdbcRepo.contributions("hr2397", "yeas").get(100).toString());
        System.out.println(billHiveJdbcRepo.contributions("hr2397", "nays").get(100).toString());
        System.out.println(billHiveJdbcRepo.contributions("hres198", "yeas").get(100).toString());
        System.out.println(billHiveJdbcRepo.contributions("hres198", "nays").get(100).toString());
        System.out.println(billHiveJdbcRepo.contributions("s743", "yeas").get(100).toString());
        System.out.println(billHiveJdbcRepo.contributions("s743", "nays").get(100).toString());
        System.out.println(billHiveJdbcRepo.contributions("s954", "yeas").get(100).toString());
        System.out.println(billHiveJdbcRepo.contributions("s954", "nays").get(100).toString());
        List<ContributionTotals> s743_totals = billHiveJdbcRepo.contributionTotals("s743");
        List<ContributionTotals> s954_totals = billHiveJdbcRepo.contributionTotals("s954");
        List<ContributionTotals> hr2387_totals = billHiveJdbcRepo.contributionTotals("hr2397");
        List<ContributionTotals> hres198_totals = billHiveJdbcRepo.contributionTotals("hres198");

        for(ContributionTotals industry : s743_totals)
            System.out.println(industry.toString());

        InfluenceGraph graph = billAnalysis.billTopList("hres198");
        System.out.println(graph.getData().getMainChildren().get(0));
    }
}
