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
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
/**
 * 管理员身份教务管理界面
 * @author 韩宇 张星喆
 * @date 2022/08/17
 */
public class TabbedPanelCourse_A extends JTabbedPane {

    public TabbedPanelCourse_A(int flag)
    {
        this.setBackground(new Color(0x0000001, true));
        this.setOpaque(false);
        if(flag==3) {
            this.setTabPlacement(1);
            this.setBounds(0, 0, 1200, 700);

            JPanel deleteCourse = new PanelDeleteCourse();
            JPanel createCourse = new PanelCreateCourse();
            JPanel enquireCourse = new PanelEnquireCourse();
            JPanel manageStudent = new PanelStudentManage_A();


            this.addTab("创建课程", new ImageIcon("resource//tab_add.png"), createCourse, "创建课程");
            this.addTab("删除课程", new ImageIcon("resource//tab_delete.png"), deleteCourse, "删除课程");
            this.addTab("课程查询", new ImageIcon("resource//search.png"), enquireCourse, "课程查询");
            this.addTab("学生管理", new ImageIcon("resource//tab_manage.png"), manageStudent, "学生管理");
            this.setFont(new Font("宋体", Font.BOLD, 24));
        }

    }

}
