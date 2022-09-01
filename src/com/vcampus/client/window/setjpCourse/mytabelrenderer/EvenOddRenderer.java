package com.vcampus.client.window.setjpCourse.mytabelrenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

 public class EvenOddRenderer extends DefaultTableCellRenderer {

    //private static final long serialVersionUID = 1L;
    private Color[][] myColor=null;

    public EvenOddRenderer(Color[][] color){
        this.myColor=color;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int col) {
        this.setBackground(myColor[row][col]);
        if(value!=null)
            this.setText(value.toString());
        return this;
    }
}
