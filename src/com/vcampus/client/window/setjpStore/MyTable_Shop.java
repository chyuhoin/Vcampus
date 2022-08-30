/** ===================================================
 * Title: MyTable_Shop.java
 * Created: [2022-8-22  09:01:13] by  张星喆
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 图书信息展示界面-通用
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-22,创建此文件
 *2. 2022-8-22, 完善设置 修改人：张星喆
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpStore;

import javax.swing.*;
import javax.swing.table.*;
import java.util.Enumeration;

public class MyTable_Shop extends JTable {                       // 实现自己的表格类
    // 重写JTable类的构造方法
    int lastColumn;
    public MyTable_Shop(DefaultTableModel tblModel) {//Vector rowData, Vector columnNames
        super(tblModel);  // 调用父类的构造方法
        //fitTableColumns(this);//设置列宽随表格内容自动调整
        lastColumn=tblModel.getColumnCount();
    }
    // 重写JTable类的getTableHeader()方法
    public JTableHeader getTableHeader() {                  // 定义表格头
        JTableHeader tableHeader = super.getTableHeader();  // 获得表格头对象
        tableHeader.setReorderingAllowed(false);//不允许拖动列头，以重新排序各列
        tableHeader.setResizingAllowed(false);//不允许手动拖动来调整各列的大小
        DefaultTableCellRenderer hr = (DefaultTableCellRenderer) tableHeader
                .getDefaultRenderer();                      // 获得表格头的单元格对象
        //hr.setFont(new Font("黑体",Font.BOLD,30));//表头字体
        //hr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);     // 设置列名居中显示
        return tableHeader;
    }
    // 重写JTable类的getDefaultRenderer(Class<?> columnClass)方法
    public TableCellRenderer getDefaultRenderer(Class<?> columnClass) { // 定义单元格
        DefaultTableCellRenderer cr = (DefaultTableCellRenderer) super
                .getDefaultRenderer(columnClass);                       // 获得表格的单元格对象
        cr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);     // 设置单元格内容居中显示
        return cr;
    }
    // 重写JTable类的isCellEditable(int row, int column)方法
    public boolean isCellEditable(int row, int column)
    {  // 表格不可编辑---

        if(column==lastColumn-1||column==lastColumn-2)
            return true;
        else return false;
    }

    //设置列宽随表格内容自动调整
    public void fitTableColumns(JTable myTable) { // 設置table的列寬隨內容調整
        JTableHeader header = myTable.getTableHeader();
        int rowCount = myTable.getRowCount();
        Enumeration<TableColumn> columns = myTable.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            TableColumn column = (TableColumn) columns.nextElement();
            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());

            int width = (int) myTable.getTableHeader().getDefaultRenderer()
                    .getTableCellRendererComponent(myTable,column.getIdentifier(), false, false, -1, col)
                    .getPreferredSize().getWidth();
            for (int row = 0; row < rowCount; row++) {
                int preferredWidth = (int) myTable.getCellRenderer(row, col)
                        .getTableCellRendererComponent(myTable,myTable.getValueAt(row, col), false, false,row, col)
                        .getPreferredSize().getWidth();
                width = Math.max(width, preferredWidth);
            }
            header.setResizingColumn(column);
            column.setWidth(width + myTable.getIntercellSpacing().width);
        }

    }
}