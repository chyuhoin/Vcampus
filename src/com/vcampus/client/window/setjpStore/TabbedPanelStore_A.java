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

    //////////////



    public TabbedPanelStore_A()
    {
        this.setTabPlacement(1);
        this.setBounds(0,0,1400,650);//注意！！！！！！！！！！！！！！！！！！！！！！！

        //JPanel goodsSimpleInform = new PanelGoodsSimpleInform();
        //goodsSimpleInform.setBounds(50,80,320,170);
        //goodsSimpleInform.setBorder(BorderFactory.createTitledBorder("分组框")); //设置面板边框，实现分组框的效果，此句代码为关键代码
        //goodsSimpleInform.setBorder(BorderFactory.createLineBorder(Color.red));//设置面板边框颜色
        //panelTest.add(goodsSimpleInform);

        /*
        JPanel myStore = new PanelMyStore_S();
        PanelHomePage_ST homePage = new PanelHomePage_ST();
        PanelMyPurchaseOrder_ST purchaseOrder = new PanelMyPurchaseOrder_ST();
        purchaseOrder.setTable(columnNames,getAllOrder());
 */
        PanelGoodsManage_A goodsManage = new PanelGoodsManage_A();
        JPanel dealManage = new PanelDealManage_A();




        this.addTab("商品管理",null,goodsManage,"商品管理");
        this.addTab("交易记录", null, dealManage,"交易信息");
        this.setFont(new Font("宋体", Font.BOLD, 24));

    }



}
