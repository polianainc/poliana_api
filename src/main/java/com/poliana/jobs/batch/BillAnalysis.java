package com.poliana.jobs.batch;

import com.poliana.entities.entities.Legislator;
import org.springframework.stereotype.Component;
import com.poliana.jobs.JobBase;
import com.poliana.contributions.models.IndustryContributor;
import com.poliana.contributions.models.IndustryInfl;
import com.poliana.contributions.models.InfluenceGraph;

import java.util.List;

@Component
public class BillAnalysis extends JobBase {

    private String winner;

    public InfluenceGraph billTopList(String billId) {

        contributionService.setIndustryNameMap();
        contributionService.setCatNameMap();
        contributionService.setPoliticianMap();

        List<Legislator> legislators = entitiesHiveRepo.getAllLegislators();
        InfluenceGraph graph = new InfluenceGraph();

        //Set bill id
        graph.setId(billId);

        //Set winner
        graph.setWinner(winner);

        //Set data...
        IndustryInfl industryInfl = new IndustryInfl();

        //Make data
        List<IndustryContributor> mainChildren = contributionService.getYeasByDistinctDiff(billId, 15);
        industryInfl.setMainChildren((mainChildren));
        industryInfl.setMainTotal(contributionService.getMainTotal(billId));
        industryInfl.setMainTouched(contributionService.getMainTouched(billId));


        List<IndustryContributor> offChildren = contributionService.getNaysByDistinctDiff(billId, 15);
        industryInfl.setOffChildren((offChildren));
        industryInfl.setOffTotal(contributionService.getOffTotal(billId));
        industryInfl.setOffTouched(contributionService.getOffTouched(billId));

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
