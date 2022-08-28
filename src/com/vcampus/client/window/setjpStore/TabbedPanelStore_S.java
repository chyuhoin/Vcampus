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

        JPanel jp11 = new JPanel();
        JPanel jp12 = new JPanel();
        JPanel jp13 = new JPanel();
        //选项卡1的内容
        //设置标题

        //按钮
        JButton btnRegister = new JButton("卖出");
    //    btnRegister.setFont(new Font("宋体", Font.BOLD, 25));

        jp12.add(btnRegister);
        btnRegister.setLayout(null);
        btnRegister.setBounds(0,0,50,5);
        /*
        this.add("选项一",jp11);	//创建三个面板
        this.add("选项二",jp12);
        this.add("选项三",jp13);

         */


        this.addTab("学校商店", null, jp11,"点击查看学校商店");
        this.addTab("二手商店", null, jp12,"点击查看二手商店");
        this.addTab("我的购买记录", null, jp13,"点击查看我的记录");
        this.setFont(new Font("宋体", Font.BOLD, 24));




        //jp.add(jtbp);
    }
}
