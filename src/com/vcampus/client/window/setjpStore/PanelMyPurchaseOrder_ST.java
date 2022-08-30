/** ===================================================
 * Title: PanelMyPurchaseOrder_ST.java
 * Created: [2022-8-29  22:07:54] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 我的订单-学生/教师界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-29,创建此文件
 *2. 2022-8-29， 完善设置 修改人：韩宇
 *3.
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window.setjpStore;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.Message;
import com.vcampus.pojo.Record;
import com.vcampus.pojo.User;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class PanelMyPurchaseOrder_ST extends JPanel{
    JLabel lblHint = new JLabel("已购商品");

    //MessagePasser passer = ClientMessagePasser.getInstance();
    //MyTablePanel_Shop tableP=new MyTablePanel_Shop(tableData,columnNames);
    MyTable_Shop table=null;//老师
    JScrollPane scrollPane = null;
    JPanel tablePanel = new JPanel();
    String userID;
    String status;

    //List<goods>承接所以有回传的结果？？？
    //可能需要一个商品对象来回改
    //Object[] columnNames={"已购商品编号","购买日期","商品状态","收货","退货"};

   // Object[][] tableData=new Object[][]{{"张三","2233","计算机"},{"张三","2233","计算机"}, {"张三","2233","计算机"}, {"张三","2233","计算机"},{"张三","2233","计算机"}, {"张三","2233","计算机"}, {"张三","2233","计算机"},{"张三","2233","计算机"}, {"张三","2233","计算机"}, {"张三","2233","计算机"},{"张三","2233","计算机"}, {"张三","2233","计算机"}, {"张三","2233","计算机"},{"张三","2233","计算机"}, {"张三","2233","计算机"}};//保存所有用户信息

    public PanelMyPurchaseOrder_ST(String ID,int flag)//传入用户ID 改成选项卡更新就传表头和数据
    {
        this.setLayout(null);

        int x=120,y=20;//起始坐标

        userID=ID;
        if(flag==1){ status="student";}
        if(flag==2){ status="teacher";};

        lblHint.setBounds(x,y,300,40);
        lblHint.setFont(new Font("宋体", Font.BOLD, 30));

        tablePanel.setLayout(null);
        tablePanel.setBounds(20,80,1150,500);

        this.add(lblHint);
        //this.add(tablePanel);

        //发消息 收消息 getBuy //写成选项卡更新！
        //如果有

        //if(true)
        //{
            //设置表格数据
            //tableData= new
            /*for(int i=0;i< ;i++)
            {
                tableData[i][0]=
            }
             */
            //setTable();
        //}
        //else
        //{
            //informFrame("未查询到购买记录",true);
            //tableData=null;
            //setTable();
       // }

    }

    public void informFrame(String title,Boolean flag)
    {
        if(flag)
        { JOptionPane.showMessageDialog(this, title, "警告", JOptionPane.ERROR_MESSAGE);}
        else
        { JOptionPane.showMessageDialog(this, title, "提示", JOptionPane.INFORMATION_MESSAGE);}
    }

    public void setTable(Object[] columnNames,Object[][] tableData)
    {
        //remove(tablePanel);
        this.add(lblHint);
        System.out.println("构件表格");
        DefaultTableModel model= new DefaultTableModel(tableData,columnNames);
        table=new MyTable_Shop(model);
        table.setRowSelectionAllowed(true);
        // 行高
        table.setRowHeight(30);// 设置行高
        // 数据
        table.setFont(new Font("黑体",Font.PLAIN,18));//表格字体
        table.getTableHeader().setFont(new Font("黑体",Font.BOLD,20));//表头字体
        //添加渲染器

        ButtonRenderer btnR1=new ButtonRenderer("确认收货");
        table.getColumn("收货").setCellRenderer(btnR1);
        ButtonEditor btnE1 =new ButtonEditor(new JCheckBox());
        btnE1.getButton().setText("确认收货");
        table.getColumn("收货").setCellEditor(btnE1);

        ButtonRenderer btnR2=new ButtonRenderer("申请退货");
        table.getColumn("退货").setCellRenderer(btnR2);
        //添加编辑器
        ButtonEditor btnE2 =new ButtonEditor(new JCheckBox());
        btnE2.getButton().setText("申请退货");
        table.getColumn("退货").setCellEditor(btnE2);

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0,0,1150,500);
        tablePanel.add(scrollPane);

        btnE1.getButton().addActionListener(new ActionListener() {//确认收货
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowName=btnE1.getThisRow();
/*
                User user = new User();
                user.setStudentID(ID);
                Gson gson = new Gson();
                String s = gson.toJson(user);
                passer.send(new Message(status, s, "shop", "getBuy"));

                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                Message msg = passer.receive();
                Map<String, java.util.List<Record>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Record>>>() {}.getType());
                List<Record> res = map.get("res");

 */

                if (true)//操作成功
                {
                    btnE1.getButton().setText("已收货");
                    informFrame("收货成功！", false);
                    model.removeRow(rowName);//表格里删掉这一行
                } else {
                    informFrame("收货失败！", true);
                }
                btnE1.fireEditingStopped_1();
            }
        });

        btnE2.getButton().addActionListener(new ActionListener() {//申请退化
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowName = btnE2.getThisRow();
                //获取商品号，打包商品，退货
                //发送消息，接收消息
                if (true)//操作成功
                {
                    btnE2.getButton().setText("已退货");
                    informFrame("退货成功！", false);
                    model.removeRow(rowName);//表格里删掉这一行
                } else {
                    informFrame("退货失败！", true);
                }
                btnE2.fireEditingStopped_1();
            }
        });

        System.out.println("构件表格22222");

        this.add(tablePanel);
        updateUI();
        repaint();
    }
}






