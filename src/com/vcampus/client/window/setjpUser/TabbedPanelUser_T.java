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


package com.vcampus.client.window.setjpUser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.client.window.setjpLibrary.mytablepanel.MyTablePanel;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabbedPanelUser_T extends JTabbedPane{

    public TabbedPanelUser_T(int flag,String id) {
        if(flag==2) {
            JTabbedPane JTP = this;

            this.setTabPlacement(1);
            this.setBounds(0, 0, 1200, 650);

            // 创建卡片布局，卡片间水平和竖直间隔为 10
            final CardLayout layout_Card = new CardLayout(10, 10);
            JPanel jp11 = new JPanel(layout_Card);
            JPanel jp12 = new JPanel(layout_Card);

            this.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getClickCount() == 1 && JTP.getSelectedIndex() == 0) {
                        jp11.removeAll();
                        new SetJPUser1(1, id, jp11, layout_Card);
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

            this.addTab("个人信息", new ImageIcon("resource//tab_personalInfo.png"), jp11, "查看个人信息");
            this.addTab("密码修改", new ImageIcon("resource//tab_password.png"), jp12, "修改密码");
            this.setFont(new Font("宋体", Font.BOLD, 24));
        }
    }
}
