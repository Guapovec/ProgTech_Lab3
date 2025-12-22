package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class GraphChildren {

    private XYDataset createDataset()
    {
        TimeSeries s1 = new TimeSeries("График №1");
        s1.add(new Year(2009), 24.6);
        s1.add(new Year(2010), 24.9);
        s1.add(new Year(2011), 24.6);
        s1.add(new Year(2012), 24.4);
        s1.add(new Year(2013), 23.8);
        s1.add(new Year(2014), 23.0);
        s1.add(new Year(2015), 21.8);
        s1.add(new Year(2016), 21.2);
        s1.add(new Year(2017), 21.0);
        s1.add(new Year(2018), 22.2);
        s1.add(new Year(2019), 22.9);
        s1.add(new Year(2020), 23.6);
        s1.add(new Year(2021), 23.6);
        s1.add(new Year(2022), 24.2);
        s1.add(new Year(2023), 24.4);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s1);

        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset)
    {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Количество внебрачных детей", // title
                "",                                 // x-axis label
                "",                                 // y-axis label
                dataset,                            // data
                true,                               // create legend
                true,                               // generate tooltips
                false                               // generate URLs
        );

        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint    (Color.lightGray);
        plot.setDomainGridlinePaint(Color.white    );
        plot.setRangeGridlinePaint (Color.white    );
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible   (true);
            renderer.setBaseShapesFilled    (true);
            renderer.setDrawSeriesLineAsPath(true);
        }

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("yyyy"));

        // Настройка формата для оси Y
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

        // Создаем DecimalFormat с символом процента
        DecimalFormat percentFormat = new DecimalFormat("0.0'%'");
        rangeAxis.setNumberFormatOverride(percentFormat);
        rangeAxis.setTickUnit(new NumberTickUnit(0.5)); // шаг 0.5%

        return chart;
    }

    public ChartPanel createDemoPanel()
    {
        JFreeChart chart = createChart(createDataset());
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(600, 300));
        return panel;
    }

}
