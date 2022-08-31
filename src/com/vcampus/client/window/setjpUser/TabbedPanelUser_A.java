/** ===================================================
 * Title: TabbedPanelUser_T.java
 * Created: [2022-8-17 17:22:31] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 用户管理——管理员界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-17,创建此文件
 *2.
 *3.
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpUser;

import javax.swing.*;
import java.awt.*;

public class TabbedPanelUser_A extends JTabbedPane{
    public TabbedPanelUser_A(int flag,String id)
    {
        if(flag==3) {
            this.setTabPlacement(1);
            this.setBounds(0, 0, 1200, 650);

            // 创建卡片布局，卡片间水平和竖直间隔为 10
            final CardLayout layout_Card = new CardLayout(10, 10);
            JPanel jp11 = new JPanel(layout_Card);
            JPanel jp12 = new JPanel(layout_Card);

            this.addTab("个人信息", new ImageIcon("resource//tab_personalInfo.png"), jp11, "查看个人信息");
            this.addTab("用户信息管理", new ImageIcon("resource//tab_password.png"), jp12, "查询和修改用户基本信息");
            this.setFont(new Font("宋体", Font.BOLD, 24));
        }
    }
}
