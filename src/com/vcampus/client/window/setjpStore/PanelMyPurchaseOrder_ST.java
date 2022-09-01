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
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Book;
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
/**
 * 我的订单-学生和教师界面，可查看已购商品并进行收货退货操作
 *
 * @author 韩宇
 * @date 2022/08/29
 */
public class PanelMyPurchaseOrder_ST extends JPanel{
    JLabel lblHint = new JLabel("已购商品");
    MessagePasser passer = ClientMessagePasser.getInstance();
    MyTable_Shop table=null;//老师
    JScrollPane scrollPane = null;
    JPanel tablePanel = new JPanel();
    String userID;
    String status;
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
        tablePanel.setBounds(150,80,900,500);
        this.add(lblHint);
    }
    /**
     * 提示窗口
     * @param title 标题
     * @param flag  true-警告 false-提示
     */
    public void informFrame(String title,Boolean flag)
    {
        if(flag) { JOptionPane.showMessageDialog(this, title, "警告", JOptionPane.ERROR_MESSAGE);}
        else { JOptionPane.showMessageDialog(this, title, "提示", JOptionPane.INFORMATION_MESSAGE);}
    }
    /**
     * 设置表格及表格内按钮响应函数，可以进行确认收货及申请退货操作
     * @param columnNames 表头
     * @param tableData   表格数据
     */
    public void setTable(Object[] columnNames,Object[][] tableData)
    {
        tablePanel.removeAll();this.add(lblHint);
        DefaultTableModel model= new DefaultTableModel(tableData,columnNames);
        table=new MyTable_Shop(model);
        table.setRowSelectionAllowed(true);
        table.setRowHeight(30);// 设置行高
        table.setFont(new Font("黑体",Font.PLAIN,18));//表格字体
        table.getTableHeader().setFont(new Font("黑体",Font.BOLD,20));//表头字体
        ButtonRenderer btnR1=new ButtonRenderer("确认收货");//添加渲染器
        table.getColumn("收货").setCellRenderer(btnR1);
        ButtonEditor btnE1 =new ButtonEditor(new JCheckBox());
        btnE1.getButton().setText("确认收货");
        table.getColumn("收货").setCellEditor(btnE1);
        ButtonRenderer btnR2=new ButtonRenderer("申请退货");
        table.getColumn("退货").setCellRenderer(btnR2);
        ButtonEditor btnE2 =new ButtonEditor(new JCheckBox());//添加编辑器
        btnE2.getButton().setText("申请退货");
        table.getColumn("退货").setCellEditor(btnE2);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0,0,900,500);
        tablePanel.add(scrollPane);

        btnE1.getButton().addActionListener(new ActionListener() {//确认收货
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowName=btnE1.getThisRow();
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("studentID",userID);
                hashMap.put("goodsID",(String)model.getValueAt(rowName, 0));
                Gson gson = new Gson();String s = gson.toJson(hashMap);
                passer.send(new Message(status, s, "shop", "confirm"));
                try { Thread.sleep(100); } catch (InterruptedException interruptedException) { interruptedException.printStackTrace(); }
                Message msg = passer.receive();
                Map<String,Object> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String,Object>>(){}.getType());

                if(map.get("res").equals("OK"))
                {   btnE1.fireEditingStopped_1();
                    btnE1.getButton().setText("已收货");
                    informFrame("收货成功！", false);
                    model.removeRow(rowName);//表格里删掉这一行
                } else { informFrame("收货失败！", true); } }
        });

        btnE2.getButton().addActionListener(new ActionListener() {//申请退货
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowName = btnE2.getThisRow();
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("studentID",userID);
                hashMap.put("goodsID",(String)model.getValueAt(rowName, 0));
                Gson gson = new Gson();String s = gson.toJson(hashMap);
                passer.send(new Message(status, s, "shop", "return"));
                try { Thread.sleep(100); } catch (InterruptedException interruptedException) { interruptedException.printStackTrace(); }

                Message msg = passer.receive();
                Map<String,Object> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String,Object>>(){}.getType());
                if(map.get("res").equals("OK"))
                {
                    btnE2.fireEditingStopped_1();
                    btnE2.getButton().setText("已退货");
                    informFrame("退货成功！", false);
                    model.removeRow(rowName);//表格里删掉这一行
                } else { informFrame("退货失败！", true); }
            }
        });
        this.add(tablePanel);
        updateUI();repaint();
        tablePanel.updateUI();tablePanel.repaint();
    }
}






