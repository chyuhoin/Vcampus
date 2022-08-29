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

public class TabbedPanelCourse_T extends JTabbedPane {
    public TabbedPanelCourse_T(String ID)
    {
        //JTabbedPane jtbp=new JTabbedPane();	//创建选项卡
        this.setTabPlacement(2);
        this.setBounds(0,0,1400,650);//注意！！！！！！！！！！！！！！！！！！！！！！！

        JPanel jp11 = new JPanel();
        JPanel jp12 = new JPanel();
        JPanel jp13 = new JPanel();
        //选项卡1的内容
        //设置标题
        JLabel lblTitleLabel = new JLabel("教师教务");
        lblTitleLabel.setFont(new Font("宋体", Font.BOLD, 50));
        //按钮
        JButton btnRegister = new JButton("wwww");
        btnRegister.setFont(new Font("宋体", Font.BOLD, 50));

        jp11.add(lblTitleLabel);
        jp12.add(btnRegister);

        /*
        this.add("选项一",jp11);	//创建三个面板
        this.add("选项二",jp12);
        this.add("选项三",jp13);

         */
       // JPanel teacherInform = new PanelTeacherInform(ID);
        JPanel manageStudent = new PanelStudentManage_T(ID);
        JPanel enquireCourse = new PanelEnquireCourse();



       // this.addTab("个人信息", null, teacherInform,"个人信息");
        this.addTab("学生管理", null, manageStudent,"学生管理");
        this.addTab("课程查询",null,enquireCourse,"课程查询");
        this.addTab("我的课表", null, jp13,"我的课表");
        this.setFont(new Font("宋体", Font.BOLD, 24));



        //jp.add(jtbp);
    }
}
