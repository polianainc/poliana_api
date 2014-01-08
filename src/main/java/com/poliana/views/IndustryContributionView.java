package com.poliana.views;

import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import javax.swing.JFrame;

import com.poliana.core.ideology.IdeologyMatrix;
import com.poliana.core.ideology.LegislatorIdeology;
import com.poliana.core.industryFinance.entities.IndustryChamberTotals;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * @author David Gilmore
 * @date 12/3/13
 */
public class IndustryContributionView extends JFrame {

    private IdeologyMatrix ideologyMatrix;
    private IndustryChamberTotals industryChamberTotals;

    public IndustryContributionView(IdeologyMatrix ideologyMatrix, IndustryChamberTotals industryChamberTotals) {

        this.ideologyMatrix = ideologyMatrix;
        this.industryChamberTotals = industryChamberTotals;
    }

    public JFreeChart generateChart(String title, CategoryDataset dataset) {

        JFreeChart chart = ChartFactory.createBarChart(title,
                "Ideology Score", "Contribution Amount", dataset, PlotOrientation.VERTICAL,
                false, true, false);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        ExtendedCategoryAxis domainAxis = new ExtendedCategoryAxis(null);

        domainAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        domainAxis.setCategoryMargin(0.30);

        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        domainAxis.setLabel("* The position of legislators on the graph is determined through an analysis of their sponsorship patterns");

        plot.setDomainAxis(domainAxis);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        renderer.setSeriesPaint(0, Color.blue);
        renderer.setSeriesPaint(1, Color.red);

        return chart;
    }

    private void adjustAxis(NumberAxis axis, boolean vertical) {

        axis.setRange(-3.0, 3.0);
        axis.setTickUnit(new NumberTickUnit(0.5));
        axis.setVerticalTickLabels(vertical);
    }



    public CategoryDataset getIndustryContributionData() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (LegislatorIdeology legislatorIdeology: this.ideologyMatrix.getIdeologies()) {

            Integer total = industryChamberTotals.getSums().get(legislatorIdeology.getBioguideId());


            if (legislatorIdeology.getParty().equalsIgnoreCase("democrat")) {
                if (total != null)
                    dataset.setValue(total, "democrat", legislatorIdeology.getName());
                else
                    dataset.setValue(total, "democrat", "");
            }

            if (legislatorIdeology.getParty().equalsIgnoreCase("republican")) {
                if (total != null)
                    dataset.setValue(total, "republican", legislatorIdeology.getName());
                else
                    dataset.setValue(total, "republican", "");
            }
        }

        return dataset;
    }

    public void sortIdeologyScores() {

        class LegislatorIdeologyComparator implements Comparator<LegislatorIdeology> {
            public int compare(LegislatorIdeology leg1, LegislatorIdeology leg2) {
                return (int) (leg1.getIdeology() - leg2.getIdeology());
            }
        }

        Collections.sort(this.ideologyMatrix.getIdeologies(), new LegislatorIdeologyComparator());

    }

    private XYDataset createSampleData() {

        Random rand = new Random();

        XYSeries series = new XYSeries("Original");
        XYSeries added = new XYSeries("Moved");

        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        for (int i = 0; i < 8 * 8; i++) {
            series.add(rand.nextGaussian(), rand.nextGaussian());
        }
        xySeriesCollection.addSeries(series);
        xySeriesCollection.addSeries(added);
        return xySeriesCollection;
    }
}