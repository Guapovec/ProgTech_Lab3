package org.example;

import org.jfree.chart.ChartPanel;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class IllegitimateChildren {
    private static final String childrenDataPath = "src\\main\\resources\\illegitimate_children.txt";

    private DefaultTableModel tableModel;
    private TimeSeriesCollection dataset;
    private JTextArea textArea;

    private List<Object[]> data = new ArrayList<>();

    public IllegitimateChildren(DefaultTableModel tableModel, TimeSeriesCollection dataset, JTextArea textArea) {
        this.tableModel = tableModel;
        this.dataset = dataset;
        this.textArea = textArea;
        loadData();
    }

    public void loadData() {
        loadDataFromFile();
        setGraphData();
        setTableData();
        setTextAreaData();
    }

    public void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(childrenDataPath))) {

            String line;
            String[] dataLine;
            while ((line = reader.readLine()) != null) {
                dataLine = line.trim().split(",");
                data.add(new Object[]{dataLine[0].trim(), dataLine[1].trim()});
            }

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла!");
        } catch (Exception e) {
            System.out.println("Неизвестная ошибка!");
            e.printStackTrace();
        }
    }

    public void setGraphData() {
        dataset.removeAllSeries();

        TimeSeries s1 = new TimeSeries("Реальные данные");
        for (Object[] row : data) {
            s1.add(new Year(Integer.parseInt(row[0].toString())), Double.parseDouble(row[1].toString()));
        }

        TimeSeries s2 = new TimeSeries("Прогнозируемые данные");
        int period = 5; // ПЕРИОД ДАННЫХ ДЛЯ СКОЛЬЗЯЩЕГО ОКНА
        int predict = 2; // ПРОГНОЗ ДАННЫХ ДЛЯ СКОЛЬЗЯЩЕГО ОКНА
        int lastYear = Integer.parseInt(data.getLast()[0].toString());
        double lastValue = Double.parseDouble(data.getLast()[1].toString());
        s2.add(new Year(lastYear), lastValue);

        for (int i = 0; i < predict; i++) {
            lastYear++;
            s2.add(new Year(lastYear), calcValue(lastYear, period));
        }

        dataset.addSeries(s1);
        dataset.addSeries(s2);
    }

    public double calcValue(int year, int period) {
        double sum = 0;
        for (int i = data.size(); i > data.size() - period; i--) {
            sum += Double.parseDouble(data.get(i-1)[1].toString());
        }
        double predictedValue = Math.round((sum / period) * 10.0) / 10.0;
        data.add(new Object[]{year, predictedValue});

        return predictedValue;
    }

    public void setTableData() {
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);

        tableModel.addColumn("Год");
        tableModel.addColumn("Процент");

        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    public void setTextAreaData() {
        double max = 0;
        double min = 100;
        for (int i = 1; i < data.size(); i++) {
            double diff = Math.abs(
                            Double.parseDouble(data.get(i)[1].toString()) -
                            Double.parseDouble(data.get(i-1)[1].toString())
                          );
            if (diff > max) max = diff;
            if (diff < min) min = diff;
        }

        String text = String.format("Максимальное изменение: %.1f%%\n" +
                                     "Минимальное изменение: %.1f%%", max, min);

        textArea.setText(text);
    }
}
