package com.poliana.shell.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * @author David Gilmore
 * @date 12/3/13
 */
public class PatrisanshipView extends JFrame {

    public PatrisanshipView(String title) {
        super(title);
        final ChartPanel chartPanel = createPanel(title);
        chartPanel.setPreferredSize(new Dimension(345, 345));
        this.add(chartPanel, BorderLayout.CENTER);
        JPanel control = new JPanel();
    }

    private ChartPanel createPanel(String title) {
        JFreeChart jfreechart = ChartFactory.createScatterPlot(
                title, "X", "Y", createSampleData(),
                PlotOrientation.VERTICAL, true, true, false);
        XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
        xyPlot.setDomainCrosshairVisible(true);
        xyPlot.setRangeCrosshairVisible(true);
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesPaint(0, Color.blue);
        adjustAxis((NumberAxis) xyPlot.getDomainAxis(), true);
        adjustAxis((NumberAxis) xyPlot.getRangeAxis(), false);
        xyPlot.setBackgroundPaint(Color.white);
        return new ChartPanel(jfreechart);
    }

    private void adjustAxis(NumberAxis axis, boolean vertical) {
        axis.setRange(-3.0, 3.0);
        axis.setTickUnit(new NumberTickUnit(0.5));
        axis.setVerticalTickLabels(vertical);
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

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                PatrisanshipView demo = new PatrisanshipView("Scatter Add Demo");
                demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                demo.pack();
                demo.setLocationRelativeTo(null);
                demo.setVisible(true);
            }
        });
    }
}