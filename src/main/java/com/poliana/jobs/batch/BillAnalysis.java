package com.poliana.jobs.batch;

import com.poliana.entities.models.Legislator;
import org.springframework.stereotype.Component;
import com.poliana.jobs.JobBase;
import com.poliana.campaignFinance.models.demo.IndustryContributor;
import com.poliana.campaignFinance.models.demo.IndustryInfl;
import com.poliana.campaignFinance.models.demo.InfluenceGraph;

import java.util.List;

@Component
public class BillAnalysis extends JobBase {

    private String winner;

    public InfluenceGraph billTopList(String billId) {

        DEMOContributionService.setIndustryNameMap();
        DEMOContributionService.setCatNameMap();
        DEMOContributionService.setPoliticianMap();

        List<Legislator> legislators = null;
        InfluenceGraph graph = new InfluenceGraph();

        //Set bill id
        graph.setId(billId);

        //Set winner
        graph.setWinner(winner);

        //Set data...
        IndustryInfl industryInfl = new IndustryInfl();

        //Make data
        List<IndustryContributor> mainChildren = DEMOContributionService.getYeasByDistinctDiff(billId, 15);
        industryInfl.setMainChildren((mainChildren));
        industryInfl.setMainTotal(DEMOContributionService.getMainTotal(billId));
        industryInfl.setMainTouched(DEMOContributionService.getMainTouched(billId));


        List<IndustryContributor> offChildren = DEMOContributionService.getNaysByDistinctDiff(billId, 15);
        industryInfl.setOffChildren((offChildren));
        industryInfl.setOffTotal(DEMOContributionService.getOffTotal(billId));
        industryInfl.setOffTouched(DEMOContributionService.getOffTouched(billId));

        graph.setData(industryInfl);

        return graph;

    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

}
