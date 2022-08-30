package com.vcampus.client.window.setjpStore;

import javax.swing.*;

class ButtonEditor extends DefaultCellEditor {
    public JButton button;//represent the  cellEditorComponent
    private String cellValue;//保存cellEditorValue

    private int thisRow;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        //button.setOpaque(false);
        //button.setContentAreaFilled(false);


        /*button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

         */
    }

    public JComponent getTableCellEditorComponent(JTable table, Object value,
                                                  boolean isSelected, int row, int column) {
        //value 源于单元格数值
        cellValue = (value == null) ? "" : value.toString();

        thisRow=row;
        return button;
    }

    public Object getCellEditorValue() {
        return new String(cellValue);
    }

    //获取按钮
    public JButton getButton() {
        return button;
    }

    public int getThisRow()
    {
        return thisRow;
    }

    public void fireEditingStopped_1(){
        //刷新渲染器
        fireEditingStopped();
    }

}