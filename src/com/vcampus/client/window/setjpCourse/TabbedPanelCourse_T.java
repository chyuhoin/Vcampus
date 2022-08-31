/** ===================================================
 * Title: TabbedPanelCourse_T.java
 * Created: [2022-8-17 17:06:23] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 教务管理——教师界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-17,创建此文件
 *2.
 *3.
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpCourse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TabbedPanelCourse_T extends JTabbedPane {
    public TabbedPanelCourse_T(int flag,String ID)
    {
        if(flag==2) {
            //JTabbedPane jtbp=new JTabbedPane();	//创建选项卡
            this.setTabPlacement(1);
            this.setBounds(0, 0, 1200, 650);//注意！！！！！！！！！！！！！！！！！！！！！！！


            JPanel teacherInform = new PanelTeacherInform(ID);
            JPanel manageStudent = new PanelStudentManage_T(ID);
            JPanel enquireCourse = new PanelEnquireCourse();
            PanelTimeTable_T teacherTimeTable=new PanelTimeTable_T(ID);

            //鼠标点击事件
            this.addMouseListener(new MouseListener(){
                @Override public void mouseClicked(MouseEvent e) {
                }
                @Override public void mousePressed(MouseEvent e) {
                        teacherTimeTable.upDateTable();

                }
                @Override public void mouseReleased(MouseEvent e) {}
                @Override public void mouseEntered(MouseEvent e) {

                }
                @Override public void mouseExited(MouseEvent e) {}
            });


            this.addTab("个人信息", null, teacherInform, "个人信息");
            this.addTab("学生管理", null, manageStudent, "学生管理");
            this.addTab("课程查询", null, enquireCourse, "课程查询");
            this.addTab("我的课表", null, teacherTimeTable, "我的课表");
            this.setFont(new Font("宋体", Font.BOLD, 24));
        }

        //jp.add(jtbp);
    }
}
