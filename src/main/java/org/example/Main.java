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
    private static final String childrenDataPath = "src\\main\\resources\\illegitimate_children.txt";
    private static final String anotherDataPath = "src\\main\\resources\\another_var.txt";

    private static final String ILLEGITIMATE_CHILDREN_STR = "внебрачные дети";
    private static final String ANOTHER_VAR_STR = "другой вариант";

    private static final int WINDOW_HEIGHT = 700;
    private static final int WINDOW_WIDTH = 1100;

    private JFrame mainFrame;

    private ChartPanel graphPanel;

    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPaneTable;

    private JComboBox comboBox;
    private final Object[] comboBoxData = {ILLEGITIMATE_CHILDREN_STR, ANOTHER_VAR_STR};

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
        // контейнер для выбора данных
        JPanel chooseDataPanel = new JPanel();
        JLabel chooseDataLabel = new JLabel("Выберите данные:");
        chooseDataPanel.add(chooseDataLabel);
        chooseDataPanel.add(comboBox);
        // контейнер для таблицы и выбора данных
        JPanel gridBag = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1;
        c.anchor = GridBagConstraints.SOUTH;
        c.insets = new Insets(0,20,20,0);
        c.gridx = 0;
        c.gridy = 0;
        gridBag.add(chooseDataPanel, c);
        c.gridy = 1;
        gridBag.add(scrollPaneTable, c);
        // добаление компонентов на главное окно
        mainFrame.add(graphPanel, BorderLayout.EAST);
        mainFrame.add(gridBag, BorderLayout.WEST);

        mainFrame.setVisible(true);
    }

    public void setGraphPanel() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "", // title
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
        graphPanel.setPreferredSize(new Dimension(600, 300));
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
                        // загрузка данных вашего варианта
                    case ANOTHER_VAR_STR:
                        // загрузка данных вашего варианта
                }
            }
        });
    }
}