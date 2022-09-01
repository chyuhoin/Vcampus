/** ===================================================
 * Title: PanelDealManage_A.java
 * Created: [2022-8-29  16:32:45] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 买方卖方交易记录-管理员
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
import com.vcampus.pojo.Goods;
import com.vcampus.pojo.Record;
import com.vcampus.pojo.Student;
import com.vcampus.pojo.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 管理员交易记录管理
 * @author 韩宇
 * @date 2022/08/29
 */
public class PanelDealManage_A extends JPanel{
    JLabel lblHint = new JLabel("输入买方/卖方一卡通号：");
    JButton btnBuyInquire = new JButton("买方交易查询");
    JButton btnSellInquire = new JButton("卖方交易查询");
    JTextField txtEnquire = new JTextField();

    Object[] columnNames={};
    Object[][] tableData=new Object[][]{};//表格数据
    MessagePasser passer = ClientMessagePasser.getInstance();
    MyTablePanel_Shop tableP=new MyTablePanel_Shop(tableData,columnNames);
    public PanelDealManage_A ()
    {
        this.setLayout(null);
        int x=120+20,y=40;//起始坐标
        int txtWidth=220, txtHeight=30;

        lblHint.setBounds(x,y-5,300,40);
        lblHint.setFont(new Font("宋体", Font.BOLD, 22));
        txtEnquire.setBounds(x+300,y,txtWidth,txtHeight);
        txtEnquire.setFont(new Font("楷体", Font.BOLD, 20));
        btnBuyInquire.setFont(new Font("宋体",Font.BOLD, 20));
        btnBuyInquire.setBounds(x+550,y,160,30);
        btnSellInquire.setFont(new Font("宋体",Font.BOLD, 20));
        btnSellInquire.setBounds(x+760,y,160,30);
        // 添加到内容面板
        this.add(lblHint);
        this.add(txtEnquire);
        this.add(btnBuyInquire);
        this.add(btnSellInquire);
        setPanel();

        btnBuyInquire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                user.setStudentID(txtEnquire.getText());
                Gson gson = new Gson();
                String s = gson.toJson(user);
                passer.send(new Message("admin", s, "shop", "getBuy"));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                Message msg = passer.receive();
                Map<String, java.util.List<Record>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, List<Record>>>() {}.getType());
                List<Record> res = map.get("res");
                remove(tableP);

                if(res.size()!=0)
                {
                    tableData=new Object[res.size()][3];
                    for(int i=0;i<res.size();i++) {
                        tableData[i][0]=res.get(i).getGoodsID();
                        tableData[i][1]=res.get(i).getDate();
                        tableData[i][2]=res.get(i).getStatus().toString();
                    }
                    columnNames= new Object[]{"已购商品编号","购买日期","商品状态"};
                    setPanel();
                }
                else
                {
                    remove(tableP);
                    updateUI();
                    repaint();
                    informFrame("未查询到购买记录！",true);
                }
            }
        });
        btnSellInquire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                user.setStudentID(txtEnquire.getText());
                Gson gson = new Gson();
                String s = gson.toJson(user);
                passer.send(new Message("admin", s, "shop", "getSell"));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                Message msg = passer.receive();
                Map<String, java.util.List<Goods>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, List<Goods>>>() {}.getType());
                List<Goods> res = map.get("res");
                remove(tableP);

                if(res.size()!=0)
                {
                    tableData=new Object[res.size()][5];
                    for(int i=0;i<res.size();i++) {
                        tableData[i][0]=res.get(i).getGoodsID();
                        tableData[i][1]=res.get(i).getGoodsName();
                        tableData[i][2]=res.get(i).getPrice();
                        tableData[i][3]=res.get(i).getDealDate();
                        tableData[i][4]=res.get(i).getNum();
                    }
                    columnNames= new Object[]{"商品编号","商品名称","商品价格","上架时间","剩余数量"};
                    setPanel();
                }
                else
                {
                    remove(tableP);
                    updateUI();
                    repaint();
                    informFrame("未查询到售货记录！",true);
                }
            }
        });
    }
    /**
     * 提示窗口
     * @param title 标题
     * @param flag  true-警告 false-提示
     */
    public void informFrame(String title,Boolean flag)
    {   if(flag) { JOptionPane.showMessageDialog(this, title, "警告", JOptionPane.ERROR_MESSAGE);}
        else { JOptionPane.showMessageDialog(this, title, "提示", JOptionPane.INFORMATION_MESSAGE);} }
    /**
     * 设置表格
     */
    public void setPanel()
    {
        tableP=new MyTablePanel_Shop(tableData,columnNames);
        this.add(tableP);
        //tableP.getTable().getColumnModel().getColumn(1).setPreferredWidth(120);
        tableP.setBounds(25,100,1150,520);//设置位置和大小
        updateUI();
        repaint();
    }


}
