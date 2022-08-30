/** ===================================================
 * Title: TabbedPanelStore_A.java
 * Created: [2022-8-17 17:19:27] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 校园商城管理——管理员界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-17,创建此文件
 *2.
 *3.
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabbedPanelStore_A extends JTabbedPane {

    //回头改到教师和学生那里
    //MessagePasser passer = ClientMessagePasser.getInstance();

    Object[] columnNames = new Object[]{"已购商品编号","购买日期","商品状态","收货","退货"};
    //////////////



    public TabbedPanelStore_A()
    {
        this.setTabPlacement(1);
        this.setBounds(0,0,1400,650);//注意！！！！！！！！！！！！！！！！！！！！！！！

        JPanel jp11 = new JPanel();
        JPanel jp12 = new JPanel();
        JPanel jp13 = new JPanel();
        //选项卡1的内容
        //设置标题
        JLabel lblTitleLabel = new JLabel("校园商城");
        lblTitleLabel.setFont(new Font("宋体", Font.BOLD, 50));
        //按钮
        JButton btnRegister = new JButton("SSSSS");
        btnRegister.setFont(new Font("宋体", Font.BOLD, 50));

        jp11.add(lblTitleLabel);
        jp12.add(btnRegister);

        /*
        this.add("选项一",jp11);	//创建三个面板
        this.add("选项二",jp12);
        this.add("选项三",jp13);

         */
        JPanel goodsInform = new PanelGoodsInform();
        JPanel panelTest = new JPanel();
        panelTest.setLayout(null);
        //JPanel goodsSimpleInform = new PanelGoodsSimpleInform();
        //goodsSimpleInform.setBounds(50,80,320,170);
        //goodsSimpleInform.setBorder(BorderFactory.createTitledBorder("分组框")); //设置面板边框，实现分组框的效果，此句代码为关键代码
        //goodsSimpleInform.setBorder(BorderFactory.createLineBorder(Color.red));//设置面板边框颜色
        //panelTest.add(goodsSimpleInform);

        JPanel dealManage = new PanelDealManage_A();
        JPanel myStore = new PanelMyStore_S();
        PanelHomePage_ST homePage = new PanelHomePage_ST();
        PanelMyPurchaseOrder_ST purchaseOrder = new PanelMyPurchaseOrder_ST();
        purchaseOrder.setTable(columnNames,getAllOrder());

        PanelGoodsManage_A goodsManage = new PanelGoodsManage_A();


        //////////////改到老师和学生，修改选项卡
        //选项卡刷新??????????好像不更新
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                if (e.getClickCount() == 1) {
                    purchaseOrder.removeAll();
                    purchaseOrder.setTable(columnNames,getAllOrder());
                   // purchaseOrder.updateUI();
                   // purchaseOrder.repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });


        this.addTab("商品管理", null, jp12,"商品管理");
        this.addTab("交易记录", null, dealManage,"交易信息");
        this.addTab("商品详情测试", null, goodsInform,"点击查看选项三");
        this.addTab("商品详情测试2", null, panelTest,"点击查看选项三");
        this.addTab("商店", null, myStore,"点击查看选项三");
        this.addTab("我的订单",null,purchaseOrder,"点击查看选项三");
        this.addTab("商城首页",null,homePage,"....");
        this.addTab("商品管理",null,goodsManage,"....");
        this.setFont(new Font("宋体", Font.BOLD, 24));

    }

    public Object[][] getAllOrder(){
        //查询数据库
        //发消息，收消息
        if(true)
        {
            /*
        Object[][] rowData = new Object[res.size()][5];
        for (int i = 0; i < res.size(); i++) {
            rowData[i][0] = res.get(i).getBookID();
            rowData[i][1] = res.get(i).getBookName();
            rowData[i][2] = res.get(i).getAuthor();
            rowData[i][3] = res.get(i).getType();
            rowData[i][4] = res.get(i).getLeftSize();
        }

         */
            Object[][] rowData=new Object[][]{{"张三","2233","计算机"},
                    {"张三","2233","计算机"}, {"张三","2233","计算机"}, {"张三","2233","计算机"},
                    {"张三","2233","计算机"}, {"张三","2233","计算机"}, {"张三","2233","计算机"},
                    {"张三","2233","计算机"}, {"张三","2233","计算机"}, {"张三","2233","计算机"},
                    {"张三","2233","计算机"}, {"张三","2233","计算机"}, {"张三","2233","计算机"},
                    {"张三","2233","计算机"}, {"张三","2233","计算机"}};
            return rowData;
        }
        else
        {
            JOptionPane.showMessageDialog(this, "未查询到购买界面", "警告", JOptionPane.ERROR_MESSAGE);
            return null;
        }


    }

}
