package com.poliana.views;

import javax.swing.JFrame;

import com.poliana.core.industryFinance.entities.IndustryContributionCompare;
import com.poliana.core.time.TimeService;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.util.List;

/**
 * @author David Gilmore
 * @date 12/3/13
 */
public class IndustryContributionBarPlot extends JFrame {

    private String title;
    private CategoryDataset dataset;
    private TimeService timeService;

    /**
     * Plot ideology vs. industry contribution totals for a given chamber in a given congressional cycle.
     * @param compareList
     * @param chamber
     * @param congress
     */
    public IndustryContributionBarPlot(List<IndustryContributionCompare> compareList, String chamber, int congress) {

        this.timeService = new TimeService();

        String chamberName = (chamber.toLowerCase().startsWith("s")) ? "Senate" : "House of Representatives";

        if (compareList.size() > 0) {
            this.title =
                    compareList.get(0).getIndustry() +
                            " contributions to the " +
                            chamberName +
                            " during the " +
                            congress +
                            timeService.getNumberSuffix(congress) +
                            " congressional session";
        }
        else
            this.title = "No data";

        this.dataset = getContributionsVsIdeology(compareList);
    }

    /**
     * Generate a certain type of chart.
     * @param type
     * @return
     */
    public JFreeChart generateChart(String type) {

        JFreeChart chart;

        switch(type) {
            default: chart = generateBarChart();
        }

        return chart;
    }

    private JFreeChart generateBarChart() {

        JFreeChart chart = ChartFactory.createBarChart(
                this.title,
                "Ideology Score", "Contribution Amount",
                this.dataset,
                PlotOrientation.VERTICAL,
                false,//Legend
                true, //Tooltips
                false //URLS
        );

        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        ExtendedCategoryAxis domainAxis = new ExtendedCategoryAxis(null);

        domainAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        domainAxis.setCategoryMargin(0.30);

        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        domainAxis.setLabel("* The position of legislators on the graph is determined through an analysis of their sponsorship patterns");

        plot.setDomainAxis(domainAxis);
        plot.setBackgroundPaint(Color.WHITE);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        if (((String)dataset.getRowKey(0)).toLowerCase().startsWith("r")) {
            renderer.setSeriesPaint(0, Color.red);
            renderer.setSeriesPaint(1, Color.blue);
        }
        else {
            renderer.setSeriesPaint(0, Color.blue);
            renderer.setSeriesPaint(1, Color.red);
        }

        renderer.setShadowVisible(false);

        return chart;
    }

    private CategoryDataset getContributionsVsIdeology(List<IndustryContributionCompare> compareList) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (IndustryContributionCompare compare: compareList) {
            dataset.addValue(compare.getAmount(), compare.getParty(), compare.getLegislator());
        }

        return dataset;
    }
}