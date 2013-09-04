package poliana.data.jobs.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import poliana.data.jobs.JobBase;
import poliana.data.models.bill.Industry;
import poliana.data.models.bill.IndustryInfl;
import poliana.data.models.bill.InfluenceGraph;
import poliana.data.services.bills.ContributionInfluence;

import java.util.List;

@Component
public class BillAnalysis extends JobBase {

    @Autowired
    private ContributionInfluence influence;
    private String winner;

    public InfluenceGraph billTopList(String billId) {

        InfluenceGraph graph = new InfluenceGraph();

        //Set bill id
        graph.setId(billId);

        //Set winner
        graph.setWinner(winner);

        //Set data...
        IndustryInfl industryInfl = new IndustryInfl();

        //Make data
        List<Industry> mainChildren = influence.getMainIndustries(billId, 5);
        industryInfl.setMainChildren((mainChildren));
        industryInfl.setMainTotal(influence.getMainTotal(billId));
        industryInfl.setMainTouched(influence.getMainTouched(billId));


        List<Industry> offChildren = influence.getOffIndustries(billId, 5);
        industryInfl.setOffChildren((offChildren));
        industryInfl.setOffTotal(influence.getOffTotal(billId));
        industryInfl.setOffTouched(influence.getOffTouched(billId));

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
