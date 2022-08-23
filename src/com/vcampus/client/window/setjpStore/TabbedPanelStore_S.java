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
        JLabel lblTitleLabel = new JLabel("学生校园商城");
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


        this.addTab("选项一", null, jp11,"点击查看选项一");
        this.addTab("选项二", null, jp12,"点击查看选项二");
        this.addTab("选项三", null, jp13,"点击查看选项三");
        this.setFont(new Font("宋体", Font.BOLD, 24));



        //jp.add(jtbp);
    }
}
