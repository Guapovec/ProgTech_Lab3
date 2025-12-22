package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private static final String ILLEGITIMATE_CHILDREN_STR = "внебрачные дети";
    private static final String ANOTHER_VAR_STR = "другой вариант";

    private static final int WINDOW_HEIGHT = 700;
    private static final int WINDOW_WIDTH = 1100;

    private JFrame mainFrame;

    private ChartPanel graphPanel;
    TimeSeriesCollection dataset;

    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPaneTable;

    private JComboBox comboBox;
    private final Object[] comboBoxData = {ILLEGITIMATE_CHILDREN_STR, ANOTHER_VAR_STR};

    private JTextArea textArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().startUp());
    }

    private void startUp() {
        // инициализация главного окна
        mainFrame = new JFrame("Статистика");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBounds(150,150,WINDOW_WIDTH,WINDOW_HEIGHT);
        // инициализация компонентов
        setGraphPanel();
        setTable();
        setComboBox();
        setTextArea();
        // контейнер для выбора данных
        JPanel chooseDataPanel = new JPanel();
        JLabel chooseDataLabel = new JLabel("Выберите данные:");
        chooseDataPanel.add(chooseDataLabel);
        chooseDataPanel.add(comboBox);
        // контейнер для таблицы и выбора данных
        JPanel leftGridBag = new JPanel(new GridBagLayout());
        GridBagConstraints cL = new GridBagConstraints();
        cL.weighty = 1;
        cL.anchor = GridBagConstraints.SOUTH;
        cL.insets = new Insets(0,20,0,0);
        cL.gridx = 0;
        cL.gridy = 0;
        leftGridBag.add(chooseDataPanel, cL);
        cL.insets = new Insets(0,20,20,0);
        cL.gridy = 1;
        leftGridBag.add(scrollPaneTable, cL);
        // контейнер для графика и поля статистики
        JPanel rightGridBag = new JPanel(new GridBagLayout());
        GridBagConstraints cR = new GridBagConstraints();
        cR.weighty = 1;
        cR.insets = new Insets(20,0,20,20);
        cR.gridx = 0;
        cR.gridy = 0;
        rightGridBag.add(graphPanel, cR);
        cR.insets = new Insets(0,0,20,20);
        cR.gridy = 1;
        rightGridBag.add(textArea, cR);
        // добаление компонентов на главное окно
        mainFrame.add(rightGridBag, BorderLayout.EAST);
        mainFrame.add(leftGridBag, BorderLayout.WEST);

        mainFrame.setVisible(true);
    }

    public void setGraphPanel() {
        dataset = new TimeSeriesCollection();
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "",                            // title
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

        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        graphPanel = new ChartPanel(chart);
        graphPanel.setFillZoomRectangle(true);
        graphPanel.setMouseWheelEnabled(true);
        graphPanel.setPreferredSize(new Dimension(600, 400));
    }

    public void setTable() {
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        scrollPaneTable = new JScrollPane(table);
        scrollPaneTable.setPreferredSize(new Dimension(400,500));
    }

    public void setComboBox() {
        comboBox = new JComboBox<>(comboBoxData);
        comboBox.setSelectedIndex(-1);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String item = cb.getSelectedItem().toString();
                System.out.println("Выбранные данные: " + item);

                switch (item) {
                    case ILLEGITIMATE_CHILDREN_STR:
                        new IllegitimateChildren(tableModel, dataset, textArea);
                    case ANOTHER_VAR_STR:

                }
            }
        });
    }

    public void setTextArea() {
        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(400, 200));
        textArea.setEditable(false);
    }
}