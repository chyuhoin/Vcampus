/** ===================================================
 * Title: TabbedPanelCourse_A.java
 * Created: [2022-8-17 17:05:13] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 教务管理——管理员界面
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

public class TabbedPanelCourse_A extends JTabbedPane {

    public TabbedPanelCourse_A()
    {
        //JTabbedPane jtbp=new JTabbedPane();	//创建选项卡
        this.setTabPlacement(2);
        this.setBounds(0,0,1400,650);//注意！！！！！！！！！！！！！！！！！！！！！！！

        JPanel jp11 = new JPanel();
        JPanel jp12 = new JPanel();
        JPanel jp13 = new JPanel();
        JPanel jp14 = new JPanel();
        JPanel jp15 = new JPanel();
        //选项卡1的内容
        //设置标题
        JLabel lblTitleLabel = new JLabel("教务教务教务");
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
        JPanel teacherInform = new PanelTeacherInform();
        JPanel deleteCourse = new PanelDeleteCourse();
        JPanel createCourse = new PanelCreateCourse();
        JPanel enquireCourse = new PanelEnquireCourse();
        JPanel manageStudent = new PanelStudentManage();




        this.addTab("创建课程", null, createCourse,"创建课程");
        this.addTab("删除课程", null, deleteCourse,"删除课程");
        this.addTab("课程查询", null, enquireCourse,"课程查询");
        this.addTab("学生管理", null, manageStudent,"学生管理");
        this.addTab("课表查询", null, jp15,"课表查询");
        this.addTab("教师信息",null,teacherInform,"教师信息");
        this.setFont(new Font("宋体", Font.BOLD, 24));



        //jp.add(jtbp);
    }

}
