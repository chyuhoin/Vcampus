/** ===================================================
 * Title: TabbedPanelSchool_A.java
 * Created: [2022-8-17 17:15:06] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 学籍管理——教师界面
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

public class TabbedPanelSchool_A extends JTabbedPane {

    public TabbedPanelSchool_A()
    {
        this.setTabPlacement(2);
        this.setBounds(0,0,1400,650);//注意！！！！！！！！！！！！！！！！！！！！！！！

        JPanel jp11 = new JPanel();
        JPanel jp12 = new JPanel();
        JPanel jp13 = new JPanel();
        //选项卡1的内容
        //设置标题
        JLabel lblTitleLabel = new JLabel("学籍123");
        lblTitleLabel.setFont(new Font("宋体",Font.BOLD, 50));
        //按钮
        JButton btnRegister = new JButton("学籍123");
        btnRegister.setFont(new Font("宋体", Font.BOLD, 24));

        jp11.add(lblTitleLabel);
        jp11.add(btnRegister);

        this.add("选项一",jp11);	//创建三个面板
        this.add("选项二",jp12);
        this.add("选项三",jp13);

    }


}
