package org.example;

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

    private JPanel graphPanel;

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
        graphPanel = new GraphChildren().createDemoPanel();
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