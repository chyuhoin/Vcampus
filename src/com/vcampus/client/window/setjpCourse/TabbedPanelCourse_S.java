/** ===================================================
 * Title: TabbedPanelCourse_S.java
 * Created: [2022-8-17 17:05:43] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 教务管理——学生界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-17,创建此文件
 *2.
 *3.
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpCourse;

import com.vcampus.client.window.setjpUser.SetJPUser1;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.MessagePasser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TabbedPanelCourse_S extends JTabbedPane {
    MessagePasser passer = ClientMessagePasser.getInstance();

    public TabbedPanelCourse_S(int flag,String ID) {
        JTabbedPane JTP=this;
        if(flag==1) {
            this.setTabPlacement(1);
            this.setBounds(0, 0, 1200, 650);//注意！！！！！！！！！！！！！！！！！！！！！！！

            JPanel jp11 = new PanelCourseSelection_S(ID);
            PanelViewCourse_S jp12 = new PanelViewCourse_S(ID);
            PanelTimeTable_S jp13 = new PanelTimeTable_S(ID);

            JLabel background1 =new JLabel();
            ImageIcon back = new ImageIcon("resource//User_background.png");
            background1.setIcon(back);
            background1.setBounds(0,0,1200,650);
            background1.setOpaque(false);
            jp11.setOpaque(false);
            jp11.add(background1);

            this.addTab("选课系统", null, jp11, "选课系统");
            this.addTab("已选课程", null, jp12, "查看已选课程");
            this.addTab("我的课表", null, jp13, "查看我的课表");
            this.setFont(new Font("宋体", Font.BOLD, 24));

            //鼠标点击事件
            this.addMouseListener(new MouseListener(){
                @Override public void mouseClicked(MouseEvent e) {
                }
                @Override public void mousePressed(MouseEvent e) {
                    if(JTP.getSelectedIndex()==1){
                        jp12.repaintPanel();
                    }
                    if(JTP.getSelectedIndex()==2){
                        jp13.upDateTable();
                    }
                }
                @Override public void mouseReleased(MouseEvent e) {}
                @Override public void mouseEntered(MouseEvent e) {

                }
                @Override public void mouseExited(MouseEvent e) {}
            });
        }

        //jp.add(jtbp);
    }
}
