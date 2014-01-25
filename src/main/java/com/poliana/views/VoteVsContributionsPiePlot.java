package com.poliana.views;

import com.poliana.core.voteVsContributions.VoteVsContributionTotals;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;

/**
 * @author David Gilmore
 * @date 1/21/14
 */
public class VoteVsContributionsPiePlot extends JFrame {

    private String title;
    private PieDataset dataset;

    public VoteVsContributionsPiePlot(VoteVsContributionTotals contributionTotals) {

        if (contributionTotals != null) {

            String chamber = contributionTotals.getVoteId().startsWith("h") ? "the House of Representatives" : "the Senate";

            this.title =
                    "Average contributions from " +
                    contributionTotals.getIndustryName() +
                    " from " +
                    contributionTotals.getBeginDate() +
                    " to " +
                    contributionTotals.getEndDate() +
                    " for the voting body of " +
                    chamber +
                    " on vote " +
                    contributionTotals.getVoteId();

            this.dataset = getDataset(contributionTotals);
        }
        else {
            this.title = "No data";
        }
    }

    /**
     * Creates a chart.
     *
     * @return A chart.
     */
    public JFreeChart generateChart() {

        JFreeChart chart = ChartFactory.createPieChart(
                this.title,  // chart title
                this.dataset,             // data
                true,               // include legend
                true,
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();

        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setNoDataMessage("No data available");
        plot.setCircular(false);
        plot.setLabelGap(0.02);

        return chart;
    }

    private PieDataset getDataset(VoteVsContributionTotals contributionTotals) {

        DefaultPieDataset dataset = new DefaultPieDataset();

        dataset.setValue("Yeas", contributionTotals.getYeaContributionAvg());
        dataset.setValue("Nays", contributionTotals.getNayContributionAvg());
        dataset.setValue("Not Voting", contributionTotals.getNotVotingContributionAvg());
        dataset.setValue("Absent", contributionTotals.getPresentContributionAvg());

        return dataset;
    }

}
