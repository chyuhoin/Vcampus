/** ===================================================
 * Title: PanelHomePage_A.java
 * Created: [2022-8-30  10:42:57] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 商城首页-教师/学生
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-30,创建此文件
 *2. 2022-8-30， 完善设置 修改人：韩宇
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelHomePage_ST extends JPanel {

    JButton btnInquire = new JButton("查询");
    String[] listData = new String[]{"输入搜索条件","全部","商品名称", "商品编号", "商品价格"};
    // 创建一个下拉列表框
    JComboBox<String> comboBox = new JComboBox<String>(listData);
    JTextField txtEnquire = new JTextField();

    JLabel lblHint = new JLabel("校园商城");

    List<PanelGoodsSimpleInform> informPanels = new ArrayList<>();
    //根据接受的所有消息，一个一个传入，用一个共有的res接受结果？
    PanelGoodsInform panelInform = null;
    //JButton btnReturn = new JButton("返回");
    CardLayout cardLayout=new CardLayout();
    MessagePasser passer = ClientMessagePasser.getInstance();

    JPanel panelAll = new JPanel();
    //PanelGoodsInform panelBuy = new PanelGoodsInform();
    JPanel panelBuy = new JPanel();

    JButton btnOk = new JButton("确认购买");
    JButton btnCancel = new JButton("返回主页");
    Goods goods = new Goods();
    String status;
    JPanel p = new JPanel();
    JScrollPane scrollPane;


    public PanelHomePage_ST(String ID,int flag)//需要实时更新
    {
        this.setLayout(cardLayout);
        int x=470,y=50;//起始坐标
        int txtWidth=110, txtHeight=42;
        int btnWidth = 160, btnHeight = 30;

        if(flag==1){ status="student";}
        if(flag==2){ status="teacher";};

        panelAll.setLayout(null);//详情页
        panelBuy.setLayout(null);//购买页

        // 设置默认选中的条目
        comboBox.setBounds(x-110,y,220,40);
        comboBox.setFont(new Font("楷体", Font.BOLD, 24));
        comboBox.setOpaque(true);

        txtEnquire.setBounds(x+130,y,txtWidth*3,txtHeight);
        txtEnquire.setFont(new Font("楷体", Font.BOLD, 20));

        btnInquire.setFont(new Font("宋体",Font.BOLD, 20));
        btnInquire.setBounds(x+480,y,80,40);

        btnOk.setBounds(400,500,btnWidth,btnHeight);
        setButtonFont(btnOk);
        btnCancel.setBounds(640,500,btnWidth,btnHeight);
        setButtonFont(btnCancel);

        this.add(panelAll,"panelAll");
        this.add(panelBuy,"panelBuy");

        p.setLayout(null);
        p.setPreferredSize(new Dimension(1100,900));

        setPanel1(goods);//一打开就显示第一页

        btnInquire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int temp = comboBox.getSelectedIndex();
                switch(temp)//发出消息
                {
                    case 0:
                        informFrame("请选择搜索条件类型！",true);
                        break;
                    case 1:
                        goods = new Goods();
                        setPanel1(goods);
                        break;
                    case 2:
                        goods = new Goods();
                        goods.setGoodsName(txtEnquire.getText());
                        setPanel1(goods);
                        break;
                    case 3:
                        goods = new Goods();
                        goods.setGoodsID(txtEnquire.getText());
                        setPanel1(goods);
                        break;
                    case 4:
                        goods = new Goods();
                        goods.setPrice(txtEnquire.getText());
                        setPanel1(goods);
                        break;
                    default:
                        break;
                }
                setPanel1(goods);
                setCard("panelAll");
                repaint();
                updateUI();
            }
        });


        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCard("panelAll");
            }
        });

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("studentID",ID);
                hashMap.put("goodsID",panelInform.txtGoodsID.getText());
                Gson gson = new Gson();
                String s = gson.toJson(hashMap);
                passer.send(new Message(status, s, "shop", "buy"));

                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                Message msg = passer.receive();
                System.out.println(msg);
                Map<String,Object> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String,Object>>(){}.getType());

                if(map.get("res").equals("OK"))
                {
                    informFrame("购买成功！",false);
                    setCard("panelAll");
                }
                else
                {
                    informFrame("购买失败！",true);
                }
            }
        });


    }

    public void setCard(String card)
    {
        cardLayout.show(this,card);
    }

    public void setButtonFont(JButton button)
    {
        button.setFont(new Font("宋体",Font.BOLD, 18));
        button.setContentAreaFilled(false);
    }

    public void informFrame(String title,Boolean flag)
    {
        if(flag)
        { JOptionPane.showMessageDialog(this, title, "警告", JOptionPane.ERROR_MESSAGE);}
        else
        { JOptionPane.showMessageDialog(this, title, "提示", JOptionPane.INFORMATION_MESSAGE);}
    }

    public void setPanel1(Goods g)
    {
        panelAll.removeAll();
        panelAll.add(comboBox);
        panelAll.add(txtEnquire);
        panelAll.add(btnInquire);
        //panelAll.add(lblHint);
        //接收消息，根据返回res的大小，设置数组长度，输入每一个商品为参数，并设置位置
        //如果不空，显示详情，空，提示
        Gson gson = new Gson();
        String s = gson.toJson(g);
        passer.send(new Message(status, s, "shop", "get"));

        Message msg = passer.receive();
        Map<String, List<Goods>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, List<Goods>>>() {}.getType());
        List<Goods> res = map.get("res");
        if(res.size()!=0)
        {
            int x=50,y=0;
            int panelWidth=320,panelHeight=170;
            int heightDiffer=190,widthDiffer=350;
            for(int i=0;i<res.size();i++)
            {
                informPanels.add(new PanelGoodsSimpleInform(this,res.get(i)));
                if(i>0)
                {
                    if(i%3==0)
                    {
                        y+=heightDiffer;
                        x=50;
                    }
                    else
                    {
                        x+=widthDiffer;
                    }
                }
                informPanels.get(i).setBounds(x,y,panelWidth,panelHeight);
                p.add(informPanels.get(i));
            }
        }
        else
            {
                informFrame("暂无商品上架！",false);
            }

        scrollPane= new JScrollPane(p);
        scrollPane.setBounds(30,120,1150,400);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);

        //总是出现垂直滚动条
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panelAll.add(scrollPane);



        panelAll.updateUI();
        panelAll.repaint();
    }

    public void setPanel2(Goods g)//显示商品详情，要传入一个商品//从小panel的详情按钮调用这个函数
    {
        panelBuy.removeAll();
        panelBuy.add(btnCancel);
        panelBuy.add(btnOk);

        panelInform = new PanelGoodsInform(g,false);//传入商品对象为参数
        panelInform.setBounds(150,50,800,500);
        panelBuy.add(panelInform);

        panelBuy.updateUI();
        panelBuy.repaint();
    }


}
