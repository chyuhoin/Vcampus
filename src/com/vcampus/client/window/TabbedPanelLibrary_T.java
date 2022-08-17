/** ===================================================
 * Title: TabbedPanelLibrary_T.java
 * Created: [2022-8-17 17:09:05] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 图书馆管理——教师界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-17,创建此文件
 *2.
 *3.
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window;

import javax.swing.*;
import java.awt.*;

public class TabbedPanelLibrary_T extends JTabbedPane {
    public TabbedPanelLibrary_T()
    {
        //JTabbedPane jtbp=new JTabbedPane();	//创建选项卡
        this.setTabPlacement(2);
        this.setBounds(0,0,1400,650);//注意！！！！！！！！！！！！！！！！！！！！！！！

        JPanel jp11 = new JPanel();
        JPanel jp12 = new JPanel();
        JPanel jp13 = new JPanel();
        //选项卡1的内容
        //设置标题
        JLabel lblTitleLabel = new JLabel("教师图书馆");
        lblTitleLabel.setFont(new Font("宋体", Font.BOLD, 50));
        //按钮
        JButton btnRegister = new JButton("AAAAA");
        btnRegister.setFont(new Font("宋体", Font.BOLD, 50));

        jp11.add(lblTitleLabel);
        jp12.add(btnRegister);




        this.addTab("选项一", null, jp11,"点击查看选项一");
        this.addTab("选项二", null, jp12,"点击查看选项二");
        this.addTab("选项三", null, jp13,"点击查看选项三");
        this.setFont(new Font("宋体", Font.BOLD, 24));



        //jp.add(jtbp);
    }
}
