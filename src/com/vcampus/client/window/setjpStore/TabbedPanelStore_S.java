/** ===================================================
 * Title: TabbedPanelStore_S.java
 * Created: [2022-8-17 17:20:31] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 校园商城管理——学生界面
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

public class TabbedPanelStore_S extends JTabbedPane {
    public TabbedPanelStore_S()
    {
        this.setTabPlacement(2);
        this.setBounds(0,0,1400,650);//注意！！！！！！！！！！！！！！！！！！！！！！！

        PanelHomePage_ST homePage = new PanelHomePage_ST();
        PanelMyStore_S myStore = new PanelMyStore_S();
        PanelMyPurchaseOrder_ST myPurchase = new PanelMyPurchaseOrder_ST();


        this.addTab("商城首页", null, homePage,"商城首页");
        this.addTab("我的店铺", null, myStore,"我的店铺");
        this.addTab("我的订单", null, myPurchase,"我的订单");
        this.setFont(new Font("宋体", Font.BOLD, 24));


        //////////////改到老师和学生，修改选项卡
        //选项卡刷新??????????好像不更新
        /*
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

         */

        //jp.add(jtbp);
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
