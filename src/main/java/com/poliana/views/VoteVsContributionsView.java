package com.poliana.views;

import com.poliana.core.voteVsContributions.VoteVsContribution;
import com.poliana.core.voteVsContributions.VoteVsIndustryContributions;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ExtendedCategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

/**
 * @author David Gilmore
 * @date 1/20/14
 */
public class VoteVsContributionsView  extends JFrame {

    private String title;
    private CategoryDataset dataset;

    /**
     * Plot industry to politician contribution totals for a given chamber in a given congressional cycle.
     */
    public VoteVsContributionsView(VoteVsIndustryContributions voteContributionCompare) {

        if (voteContributionCompare != null) {

            String voteId = voteContributionCompare.getVoteId();
            String chamber = voteId.startsWith("h") ? "The House of Representatives" : "The Senate";
            String industry = voteContributionCompare.getIndustryName();

            this.title =
                industry + " contributions to " + chamber + " grouped by votes on " + voteId;
        }
        else
            this.title = "No data";

        this.dataset = getContributionDataset(voteContributionCompare);
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

        plot.setDomainAxis(domainAxis);
        plot.setBackgroundPaint(Color.WHITE);
        plot.getDomainAxis(0).setVisible(false);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        renderer.setShadowVisible(false);

        return chart;
    }

    private CategoryDataset getContributionDataset(VoteVsIndustryContributions voteContributionCompare) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if (voteContributionCompare.getYeas() != null) {

            for (VoteVsContribution voteVsContribution : voteContributionCompare.getYeas()) {

                double sum;
                if (voteVsContribution.getSum() != null)
                    sum = voteVsContribution.getSum().doubleValue();
                else
                    sum = 0;

                dataset.addValue(sum, "Yea", voteVsContribution.getBioguideId());
            }
        }

        if (voteContributionCompare.getNays() != null) {

            for (VoteVsContribution voteVsContribution : voteContributionCompare.getNays()) {

                double sum;
                if (voteVsContribution.getSum() != null)
                    sum = voteVsContribution.getSum().doubleValue();
                else
                    sum = 0;

                dataset.addValue(sum, "Nay", voteVsContribution.getBioguideId());
            }
        }

        if (voteContributionCompare.getNotVoting() != null) {

            for (VoteVsContribution voteVsContribution : voteContributionCompare.getNotVoting()) {

                double sum;
                if (voteVsContribution.getSum() != null)
                    sum = voteVsContribution.getSum().doubleValue();
                else
                    sum = 0;

                dataset.addValue(sum, "Not Voting", voteVsContribution.getBioguideId());
            }
        }

        if (voteContributionCompare.getAbsent() != null) {

            for (VoteVsContribution voteVsContribution : voteContributionCompare.getAbsent()) {

                double sum;
                if (voteVsContribution.getSum() != null)
                    sum = voteVsContribution.getSum().doubleValue();
                else
                    sum = 0;

                dataset.addValue(sum, "Absent", voteVsContribution.getBioguideId());
            }
        }



        return dataset;
    }
}
