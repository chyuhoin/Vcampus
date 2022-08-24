/** ===================================================
 * Title: MyTablePanel.java
 * Created: [2022-8-22  12:35:13] by  张星喆
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 图书信息展示界面-通用
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-22,创建此文件
 *2. 2022-8-23, 完善设置 修改人：张星喆
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpLibrary.mytablepanel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.client.window.setjpLibrary.PanelBookInform;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Book;
import com.vcampus.pojo.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTablePanel extends JPanel{
    MessagePasser passer = ClientMessagePasser.getInstance();
    CardLayout layC=new CardLayout(10,10);//卡片布局
    SpringLayout layS=new SpringLayout();//弹性布局
    private int currentPage=1;//当前页面
    private  int pageSize=10;//每一页最多有多少行
    private int lastPage;//最末页
    MyTable table=null;//表格
    DefaultTableModel dtm=null;//表格模型
    JLabel lbl;//顶端标签
    Object[][] dList;//存储表格内容
    JComboBox<Integer> comboBox;//页码下拉框
    //卡片
    JPanel PANEL=new JPanel();
    JPanel JP1;
    JPanel JP2;

    public int getLastPage() {//获取末页页码
        return lastPage;
    }
    public void setLastPage(int lastPage) {//设置末页页码
        this.lastPage = lastPage;
    }
    public int getCurrentPage() {//获取当前页码
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {//设置当前页码
        this.currentPage = currentPage;
    }
    public int getPageSize() {//获取每页最大行数
        return pageSize;
    }
    public void setPageSize(int pageSize) {//设置每页最大行数
        this.pageSize = pageSize;
    }

    public MyTablePanel(Object[][] rowData, Object[] columnNames){
        //卡片
        PANEL=this;

        //数据获取
        if(rowData.length>0) {
            dList = new Object[rowData.length][rowData[0].length];
            for (int i = 0; i < rowData.length; i++)
                System.arraycopy(rowData[i], 0, dList[i], 0, rowData[i].length);
        } else{
            dList=new Object[0][];
        }

        //计算总页数
        int tblLength=dList.length;
        if(tblLength%pageSize==0){
            setLastPage(tblLength/getPageSize());
        }else{
            setLastPage(tblLength/getPageSize()+1);
        }
        if(getLastPage()==0) setCurrentPage(0);
        System.out.println("表格数据总长度为："+tblLength);
        System.out.println("总页数为："+getLastPage());

        //布局设置---
        setLayout(layC);//卡片布局
        JP1=new JPanel(layS);//弹性布局
        JP2=new JPanel(layS);
        add(JP1);
        add(JP2);

        setJP1(rowData, columnNames);
        layS.putConstraint(layS.SOUTH, lbl, -35, layS.SOUTH, JP1);
        layS.putConstraint(layS.WEST, lbl, 516, layS.WEST, JP1);
    }

    /**
     * 设置第一张卡片的界面
     * @param rowData
     * @param columnNames
     */
    public void setJP1(Object[][] rowData, Object[] columnNames){
        //标签设置
        lbl=new JLabel("当前是第"+getCurrentPage()+"页，共"+getLastPage()+"页");
        JP1.add(lbl);
        lbl.setFont(new Font("黑体",Font.PLAIN,15));
        //顶端标签位置
        layS.putConstraint(layS.SOUTH, lbl, -35, layS.SOUTH, JP1);
        layS.putConstraint(layS.WEST, lbl, 516, layS.WEST, JP1);

        //按钮设置---
        JButton btn1 = new JButton("首页");
        btn1.addActionListener(new MyTableListener());
        btn1.setActionCommand("首页");
        JButton btn2 = new JButton("上一页");
        btn2.addActionListener(new MyTableListener());
        JButton btn3 = new JButton("下一页");
        btn3.addActionListener(new MyTableListener());
        JButton btn4 = new JButton("末页");
        btn4.addActionListener(new MyTableListener());
        JP1.add(btn1);
        JP1.add(btn2);
        JP1.add(btn3);
        JP1.add(btn4);

        btn1.setFont(new Font("黑体",Font.PLAIN,18));
        btn2.setFont(new Font("黑体",Font.PLAIN,18));
        btn3.setFont(new Font("黑体",Font.PLAIN,18));
        btn4.setFont(new Font("黑体",Font.PLAIN,18));

        layS.putConstraint(layS.SOUTH, btn4, -30, layS.SOUTH, JP1);
        layS.putConstraint(layS.EAST, btn4, -10, layS.EAST, JP1);
        layS.putConstraint(layS.SOUTH, btn3, 0, layS.SOUTH, btn4);
        layS.putConstraint(layS.EAST, btn3, -10, layS.WEST, btn4);
        layS.putConstraint(layS.SOUTH, btn2, 0, layS.SOUTH, btn3);
        layS.putConstraint(layS.EAST, btn2, 0, layS.WEST, btn3);
        layS.putConstraint(layS.SOUTH, btn1, 0, layS.SOUTH, btn2);
        layS.putConstraint(layS.EAST, btn1, -10, layS.WEST, btn2);

        //表格设置---
        dtm=new DefaultTableModel(null,columnNames);
        table=new MyTable(dtm);

        table.setRowHeight(60);//设置行高
        table.setFont(new Font("黑体",Font.PLAIN,18));//设置表格字体
        table.getTableHeader().setFont(new Font("黑体",Font.PLAIN,20));//设置表头字体

        JScrollPane jsp = new JScrollPane();
        jsp.setViewportView(table);
        JP1.add(jsp);
        layS.putConstraint(layS.NORTH, jsp, 0, layS.NORTH, JP1);
        layS.putConstraint(layS.WEST, jsp, 10, layS.WEST, JP1);
        layS.putConstraint(layS.EAST, jsp, -10, layS.EAST, JP1);
        layS.putConstraint(layS.SOUTH, jsp, -10, layS.NORTH, btn1);

        showTable(currentPage);//显示第一页

        //设置鼠标双击事件
        table.addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                if (e.getClickCount() == 2) {
                    System.out.println("双击表格");
                    int row=((JTable)e.getSource()).rowAtPoint(e.getPoint());
                    String bookID=(String)dtm.getValueAt(row, 0);
                    String bookName=(String)dtm.getValueAt(row, 1);

                    //查询数据库
                    Book book=new Book();
                    book.setBookID(bookID);
                    book.setBookName(bookName);
                    Gson gson = new Gson();
                    String s = gson.toJson(book);
                    passer.send(new Message("admin", s, "library", "get"));

                    Message msg = passer.receive();
                    Map<String, java.util.List<Book>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Book>>>(){}.getType());
                    List<Book> res = map.get("res");

                    if(res.size()!=0) {
                        JP2.removeAll();
                        setJP2(res.get(0));//????? 仅根据书号返回的是Book List ????????
                        layC.next(PANEL);//切换到第二张卡
                        PANEL.updateUI();
                        PANEL.repaint();
                        System.out.println("查看书号为<" + bookID + ">的书的详情");
                    }else {
                        JOptionPane.showMessageDialog(null, "数据查询出错", "提示",
                                JOptionPane.ERROR_MESSAGE);
                        System.out.println("ERROR:查看书号为<" + bookID + ">的书的详情：数据查询出错");
                    }
                }
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        });

        //跳转页面
        int size=18;//字体大小
        JLabel lbl1=new JLabel("跳转到 第");
        JP1.add(lbl1);
        lbl1.setFont(new Font("黑体",Font.PLAIN,size));
        JLabel lbl2=new JLabel("页");
        JP1.add(lbl2);
        lbl2.setFont(new Font("黑体",Font.PLAIN,size));

        layS.putConstraint(layS.SOUTH, lbl1, 0, layS.SOUTH, btn1);
        layS.putConstraint(layS.WEST, lbl1, 10, layS.WEST, JP1);

        setcomboBox();
        comboBox.setFont(new Font("黑体",Font.BOLD,size));
        JP1.add(comboBox);
        layS.putConstraint(layS.SOUTH, comboBox, 5, layS.SOUTH, lbl1);
        layS.putConstraint(layS.WEST, comboBox, 10, layS.EAST, lbl1);

        layS.putConstraint(layS.SOUTH, lbl2, 0, layS.SOUTH, lbl1);
        layS.putConstraint(layS.WEST, lbl2, 10, layS.EAST, comboBox);
    }

    /**
     * 设置第二张卡片的界面
     */
    public void setJP2(Book book){
        //返回按钮
        JButton btnBack=new JButton("返回");
        JP2.add(btnBack);
        btnBack.setFont(new Font("黑体",Font.BOLD,22));
        layS.putConstraint(layS.NORTH, btnBack, 0, layS.NORTH, JP2);
        layS.putConstraint(layS.WEST, btnBack, 10, layS.WEST, JP2);
        //详情面板
        JPanel subJP2=new PanelBookInform(book,false);
        JP2.add(subJP2);
        layS.putConstraint(layS.NORTH, subJP2, 30, layS.NORTH, btnBack);
        layS.putConstraint(layS.WEST, subJP2, 10, layS.WEST, JP2);
        layS.putConstraint(layS.SOUTH, subJP2, -10, layS.SOUTH, JP2);
        layS.putConstraint(layS.EAST, subJP2, -30, layS.EAST, JP2);

        //返回按钮监听事件
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layC.first(PANEL);
            }
        });

    }
    /**
     * 设置下拉选项框
     */
    public void setcomboBox(){
        Integer[] data=new Integer[getLastPage()];
        for(int i=1;i<=getLastPage();i++)
            data[i-1]=i;

        if(getLastPage()>0) {
            comboBox = new JComboBox<Integer>(data);
            comboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        showTable((Integer) comboBox.getSelectedIndex() + 1);
                        System.out.println("选中: " + comboBox.getSelectedIndex() + " = " + comboBox.getSelectedItem());
                    }
                }
            });
            // 设置默认选中的条目
            comboBox.setSelectedIndex(getCurrentPage() - 1);
        }
        else{
            comboBox = new JComboBox<Integer>();
            comboBox.setSelectedIndex(-1);
        }

    }

    /**
     * 设置表格内容
     * @param currentPage
     */
    public void showTable(int currentPage) {
        dtm.setRowCount(0);// 清除原有行
        setCurrentPage(currentPage);//设置当前页
        System.out.println("showTable：当前页："+getCurrentPage());

        int tempSize= Math.min(pageSize, dList.length);
        System.out.println("showTable：tempSize="+tempSize);
        for (int row = 0; row < tempSize; row++)    //获得数据
        {
            if(row+(currentPage-1)*tempSize>=dList.length) break;
            Object[] rowV = new Object[dList[0].length];
            //System.out.println(row+(currentPage-1)*tempSize);
            System.arraycopy(dList[row+(currentPage-1)*tempSize], 0, rowV, 0, dList[0].length);
            dtm.addRow(rowV);
        }

        //顶端标签更新
        lbl.setText("当前是第"+getCurrentPage()+"页，共"+getLastPage()+"页");
        layS.putConstraint(layS.WEST, lbl, JP1.getWidth()/2-lbl.getWidth()/2, layS.WEST, JP1);
        //默认选中的条目更新
        System.out.println("showTable: 总页数为（if前）："+getLastPage());
        if(getLastPage()>0&&comboBox!=null) {
            comboBox.setSelectedIndex(getCurrentPage() - 1);
        }
        //System.out.println(JP1.getWidth()/2-lbl.getWidth()/2);
    }

    /**
     * 设置翻页按钮响应
     */
    class MyTableListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("首页")){
                if(getLastPage()>0)
                    showTable(1);
                else showTable(0);
            }

            if(e.getActionCommand().equals("上一页")){
                if(getLastPage()>0) {
                    if (getCurrentPage() == 1) {
                        setCurrentPage(2);
                    }
                    showTable(getCurrentPage() - 1);
                }
                else showTable(0);

            }

            if(e.getActionCommand().equals("下一页")){
                if(getCurrentPage()<getLastPage()){
                    showTable(getCurrentPage()+1);
                }else{
                    showTable(getLastPage());
                }
            }

            if(e.getActionCommand().equals("末页")){
                showTable(getLastPage());
            }
        }
    }

}
