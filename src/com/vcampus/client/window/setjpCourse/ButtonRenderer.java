package com.vcampus.client.window.setjpCourse;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer implements TableCellRenderer {
    private JButton jButton;

    public ButtonRenderer(String str) {
        initButton(str);
        //jPanel.add(jButton);
    }

    private void initButton(String str) {
        jButton = new JButton();
        jButton.setText(str);
        jButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("" + e.getActionCommand());
                        System.out.println(jButton.getText());
                    }
                });
    }

    public JComponent getTableCellRendererComponent(JTable table, Object value,
                                                    boolean isSelected, boolean hasFocus, int row, int column) {
       //jButton.setText("删除");
       //jButton.setToolTipText("从课程中删除该教师");
        return jButton;
    }

}

/*
class ButtonRenderer extends JButton implements TableCellRenderer {
    public JComponent getTableCellRendererComponent(JTable table, Object value,
                                                    boolean isSelected, boolean hasFocus, int row, int column) {
        //value 源于editor
        String text = (value == null) ? "" : value.toString();
        //按钮文字
        setText(text);
        //单元格提示
        setToolTipText(text);
        //背景色
        setBackground(null);
        //前景色
        setForeground(null);

        //setContentAreaFilled(false);

        return this;

    }
}

 */
