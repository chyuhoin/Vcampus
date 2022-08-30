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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelHomePage_ST extends JPanel {

    JButton btnInquire = new JButton("查询");
    String[] listData = new String[]{"输入搜索条件","全部","商品名称", "商品编号", "商品类型", "商品价格"};
    // 创建一个下拉列表框
    JComboBox<String> comboBox = new JComboBox<String>(listData);
    JTextField txtEnquire = new JTextField();

    JLabel lblHint = new JLabel("校园商城");

    ///List<JPanel> informPanels = new List<JPanel>();
    PanelGoodsSimpleInform[] informPanel ={new PanelGoodsSimpleInform(this),new PanelGoodsSimpleInform(this),
            new PanelGoodsSimpleInform(this), new PanelGoodsSimpleInform(this),
            new PanelGoodsSimpleInform(this),new PanelGoodsSimpleInform(this)};
    //根据接受的所有消息，一个一个传入，用一个共有的res接受结果？
    PanelGoodsInform panelInform = new PanelGoodsInform();
    //JButton btnReturn = new JButton("返回");
    CardLayout cardLayout=new CardLayout();

    JPanel panelAll = new JPanel();
    //PanelGoodsInform panelBuy = new PanelGoodsInform();
    JPanel panelBuy = new JPanel();

    JButton btnOk = new JButton("确认购买");
    JButton btnCancel = new JButton("返回主页");


    public PanelHomePage_ST()//需要实时更新
    {
        this.setLayout(cardLayout);
        int x=470,y=50;//起始坐标
        int txtWidth=110, txtHeight=42;
        int btnWidth = 160, btnHeight = 30;

        panelAll.setLayout(null);//详情页
        panelBuy.setLayout(null);//购买页

        //lblHint.setBounds(120,20,300,40);
        //lblHint.setFont(new Font("宋体", Font.BOLD, 30));
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

        //发消息 set1里面接收消息
        setPanel1();//一打开就显示所有
        setCard("panelAll");

        btnInquire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = txtEnquire.getText();
                System.out.println(str);//传送，接收结果bool型

                int temp = comboBox.getSelectedIndex();
                switch(temp)//发出消息
                {
                    case 0:
                        informFrame("请选择搜索条件类型！",true);
                        break;
                    case 1:
                        //打包空商品，查所有
                        break;
                    case 2:
                        System.out.println("名称"+str);
                        break;
                    case 3:
                        System.out.println("编号"+str);//book。set
                        break;
                    case 4:
                        System.out.println("类型"+str);//book。set
                        break;
                    case 5:
                        System.out.println("价格"+str);//book。set
                        break;
                    default:
                        break;
                }
                //传消息出去
                setPanel1();
                setCard("panelAll");

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
                String str = panelInform.txtGoodsID.getText();
                System.out.println(str);
                //这里需要各人一卡通！
                //发消息 收消息，购买
                //如果可以
                if(true)
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

    public void setPanel1()
    {
        panelAll.removeAll();
        panelAll.add(comboBox);
        panelAll.add(txtEnquire);
        panelAll.add(btnInquire);
        //panelAll.add(lblHint);
        //接收消息，根据返回res的大小，设置数组长度，输入每一个商品为参数，并设置位置
        //如果不空，显示详情，空，提示
        if(true)
        {
            int x=80,y=120;
            int panelWidth=320,panelHeight=170;
            int heightDiffer=190,widthDiffer=350;
            for(int i=0;i<6;i++)
            //goodsSimpleInform.setBounds(50,80,320,170);
            {
                if(i>0)
                {
                    if(i%3==0)
                    {
                        y+=heightDiffer;
                        x=80;
                    }
                    else
                    {
                        x+=widthDiffer;
                    }
                }


                informPanel[i].setBounds(x,y,panelWidth,panelHeight);
                panelAll.add(informPanel[i]);
            }
        }
        else
            {
                informFrame("暂无商品上架！",false);
        }
        panelAll.updateUI();
        panelAll.repaint();
    }

    public void setPanel2()//显示商品详情，要传入一个商品//从小panel的详情按钮调用这个函数
    {
        panelBuy.removeAll();
        panelBuy.add(btnCancel);
        panelBuy.add(btnOk);

        panelInform = new PanelGoodsInform();//传入商品对象为参数
        panelInform.setBounds(150,50,800,500);
        panelBuy.add(panelInform);

        panelBuy.updateUI();
        panelBuy.repaint();
    }


}
