package com.vcampus.client.window.setjpUser;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyButtonRender implements TableCellRenderer {
    public JPanel jp;
    private JPanel jPanel;
    private JButton btn1,btn2;

    public MyButtonRender() {
        initJPanel();
        initButton();
        jPanel.add(btn1);
        jPanel.add(btn2);
    }

    private void initButton() {
        SpringLayout layout_Spring = new SpringLayout();
        CardLayout layout_Card=new CardLayout();
        btn1 = new JButton();
        btn2 = new JButton();
        btn1.setBounds(10, 1, 100, 30-4);
        btn2.setBounds(20+100, 1, 100, 30-4);
        btn1.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        System.out.println("" + e.getActionCommand());
                        System.out.println(btn1.getText());
                    }
                });
        btn2.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("" + e.getActionCommand());
                        System.out.println(btn2.getText());
                    }
                });
    }

    private void initJPanel() {
        jPanel = new JPanel();
        jPanel.setLayout(null);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        btn1.setText("编辑");
        btn2.setText("查看");
        return jPanel;
    }
}

