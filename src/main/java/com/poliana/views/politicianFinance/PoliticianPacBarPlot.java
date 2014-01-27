package com.poliana.views.politicianFinance;

import com.poliana.core.politicianFinance.pacs.PoliticianPacContributionsTotals;
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
import java.util.Date;
import java.util.List;

/**
 * @author David Gilmore
 * @date 1/27/14
 */
public class PoliticianPacBarPlot extends JFrame {

    private String title;
    private CategoryDataset dataset;

    /**
     * Plot PAC to politician contribution totals for all time.
     */
    public PoliticianPacBarPlot(List<PoliticianPacContributionsTotals> contributions) {

        if (contributions != null && contributions.size() > 0) {

            PoliticianPacContributionsTotals totals = contributions.get(0);

            this.title =
                    "Industry contributions  to " +
                            totals.getFirstName() + " " +
                            totals.getLastName();
        }
        else
            this.title = "No data";

        this.dataset = getContributionDataset(contributions);
    }

    /**
     * Plot PAC to politician contribution totals for a given time range
     */
    public PoliticianPacBarPlot(List<PoliticianPacContributionsTotals> contributions, Date start, Date end) {

        if (contributions != null && contributions.size() > 0) {

            PoliticianPacContributionsTotals totals = contributions.get(0);

            this.title =
                    "Industry contributions  to " +
                            totals.getFirstName() + " " +
                            totals.getLastName() + " " +
                            "from " + start.toString() + " to " + end.toString();
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


    private CategoryDataset getContributionDataset(List<PoliticianPacContributionsTotals> contributions) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if (contributions == null)
            return dataset;

        for (PoliticianPacContributionsTotals contribution: contributions) {
            dataset.addValue(contribution.getContributionSum(), "", contribution.getPacName());
        }

        return dataset;
    }
}
