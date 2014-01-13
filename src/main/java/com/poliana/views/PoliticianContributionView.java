package com.poliana.views;

import com.poliana.core.politicianFinance.entities.IndustryPoliticianContributions;
import com.poliana.core.legislators.Legislator;
import com.poliana.core.time.TimeService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ExtendedCategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author David Gilmore
 * @date 1/10/14
 */
public class PoliticianContributionView extends JFrame {

    private String title;
    private CategoryDataset dataset;
    private TimeService timeService;


    /**
     * Plot industry to politician contribution totals for a given chamber in a given congressional cycle.
     * @param contributions
     * @param congress
     */
    public PoliticianContributionView(List<IndustryPoliticianContributions> contributions, Legislator legislator, int congress) {

        this.timeService = new TimeService();

        if (contributions != null && contributions.size() > 0) {
            this.title =
                    "Industry contributions  to " +
                    legislator.getFirstName() + " " +
                    legislator.getLastName() +
                    " during the " +
                    congress +
                    timeService.getNumberSuffix(congress) +
                    " congress";
        }
        else
            this.title = "No data";

        this.dataset = getContributionDataset(contributions);
    }

    /**
     * Generate a plot of the given type.
     * @param type
     * @return
     */
    public JFreeChart generateChart(String type) {

        switch(type) {
            default: return generateBarChart();
        }
    }

    private JFreeChart generateBarChart() {

        JFreeChart chart = ChartFactory.createBarChart(
                this.title,
                "", "Contribution Amount",
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

        plot.setDomainAxis(domainAxis);
        plot.setBackgroundPaint(Color.WHITE);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        renderer.setShadowVisible(false);

        return chart;
    }

    private CategoryDataset getContributionDataset(List<IndustryPoliticianContributions> contributions) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (IndustryPoliticianContributions contribution: contributions) {
            dataset.addValue(contribution.getContributionSum(), "", contribution.getIndustryName());
        }

        return dataset;
    }
}
