/** ===================================================
 * Title: TabbedPanelUser_T.java
 * Created: [2022-8-17 17:25:01] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 用户管理——教师界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-17,创建此文件
 *2.
 *3.
 *    修改的内容描述，修改的原因
 */


package com.vcampus.client.window;

import com.vcampus.client.window.setjpUSER.*;

import javax.swing.*;
import java.awt.*;

public class TabbedPanelUser_T extends JTabbedPane{

    public TabbedPanelUser_T(String id) {
        this.setTabPlacement(2);
        this.setBounds(0,0,1400,650);//注意！！！！！！！！！！！！！！！！！！！！！！！

        // 创建卡片布局，卡片间水平和竖直间隔为 10
        final CardLayout layout_Card = new CardLayout(10, 10);
        JPanel jp11 = new JPanel(layout_Card);
        JPanel jp12 = new JPanel(layout_Card);
        //选项卡1的内容
        SetJPUser1 setjp1=new SetJPUser1(id,jp11,layout_Card);
        //选项卡2的内容
        SetJPUser2_2 setjp2_2=new SetJPUser2_2(id,jp12,layout_Card);

        this.addTab("个人信息",null,jp11,"查看个人信息");
        this.addTab("密码修改",null,jp12,"修改密码");
        this.setFont(new Font("宋体",Font.BOLD,24));

    }


}
