package com.poliana.web;

import com.poliana.core.ideology.IdeologyMatrix;
import com.poliana.core.ideology.IdeologyService;
import com.poliana.core.industryFinance.IndustryContributionService;
import com.poliana.core.industryFinance.entities.IndToPolContrTotals;
import com.poliana.core.industryFinance.entities.IndustryChamberTotals;
import com.poliana.core.time.TimeService;
import com.poliana.views.IndustryContributionView;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * @author David Gilmore
 * @date 1/4/14
 */
@Controller
@RequestMapping("/industry-finance")
public class IndustryContributionController extends AbstractBaseController {

    private IdeologyService ideologyService;
    private IndustryContributionService industryContributionService;

    private TimeService timeService;


    public IndustryContributionController() {
        this.timeService = new TimeService();
    }

    @RequestMapping("/{industryId}/{chamber}/{congress}/plot.png")
    public void plotIndustryContributionsAndIdeology(
            OutputStream stream,
            @PathVariable("industryId") String industryId,
            @PathVariable("chamber") String chamber,
            @PathVariable("congress") Integer congress) throws Exception {

        IdeologyMatrix ideologyMatrix = ideologyService.getIdeologyMatrix(chamber,congress);

        IndustryChamberTotals chamberTotals =
                industryContributionService.getIndustryTotalsByChamber(industryId, chamber, congress);

        IndustryContributionView industryContributionView = new IndustryContributionView(ideologyMatrix, chamberTotals);

        industryContributionView.sortIdeologyScores();

        CategoryDataset dataset = industryContributionView.getIndustryContributionData();

        String chamberName = (chamber.equals("h")) ? "House of Representatives" : "Senate";

        String title =
                chamberTotals.getName() +
                        " contributions to the " +
                        chamberName +
                        " during the " +
                        congress +
                        timeService.getNumberSuffix(congress) +
                        " congressional session";


        JFreeChart chart = industryContributionView.generateChart(title, dataset);

        ChartUtilities.writeChartAsPNG(stream, chart, 1200, 800);
    }

    @RequestMapping("/category/{categoryId}/{chamber}/{congress}/plot.png")
    public void plotIndustryCategoryContributionsAndIdeology(
            OutputStream stream,
            @PathVariable("categoryId") String categoryId,
            @PathVariable("chamber") String chamber,
            @PathVariable("congress") Integer congress) throws Exception {

        IdeologyMatrix ideologyMatrix = ideologyService.getIdeologyMatrix(chamber,congress);

        IndustryChamberTotals chamberTotals =
                industryContributionService.getCategoryTotalsByChamber(categoryId, chamber, congress);

        IndustryContributionView industryContributionView = new IndustryContributionView(ideologyMatrix, chamberTotals);

        industryContributionView.sortIdeologyScores();

        CategoryDataset dataset = industryContributionView.getIndustryContributionData();

        String chamberName = (chamber.equals("h")) ? "House of Representatives" : "Senate";

        String title =
                chamberTotals.getName() +
                        " contributions to the " +
                        chamberName +
                        " during the " +
                        congress +
                        timeService.getNumberSuffix(congress) +
                        " congressional session";


        JFreeChart chart = industryContributionView.generateChart(title, dataset);

        ChartUtilities.writeChartAsPNG(stream, chart, 1200, 800);
    }

    @Autowired
    public void setIdeologyService(IdeologyService ideologyService) {
        this.ideologyService = ideologyService;
    }

    @Autowired
    public void setIndustryContributionService(IndustryContributionService industryContributionService) {
        this.industryContributionService = industryContributionService;
    }
}
