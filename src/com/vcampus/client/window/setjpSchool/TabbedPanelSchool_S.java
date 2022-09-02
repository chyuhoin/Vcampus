/** ===================================================
 * Title: TabbedPanelSchool_S.java
 * Created: [2022-8-17 17:16:06] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 学籍管理——学生界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-17,创建此文件
 *2.
 *3.
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpSchool;

import com.vcampus.client.window.setjpSchool.StudentStatusEnquire_S;

import javax.swing.*;
import java.awt.*;
/**
 * 学生身份学籍管理界面
 * @author 韩宇 张星喆
 * @date 2022/08/17
 */
public class TabbedPanelSchool_S extends JTabbedPane {
    public TabbedPanelSchool_S(int flag,String ID)
    {
        this.setBackground(new Color(0x0000001, true));
        this.setOpaque(false);
        if(flag==1) {
            JLabel background1 =new JLabel();
            ImageIcon back = new ImageIcon("resource//User_background.png");
            background1.setIcon(back);
            background1.setBounds(0,0,1200,700);
            background1.setOpaque(false);

            this.setTabPlacement(1);
            this.setBounds(0, 0, 1400, 650);//注意！！！！！！！！！！！！！！！！！！！！！！！

            JPanel panelEnquire = new StudentStatusEnquire_S(ID);
          //  panelEnquire.add(background1);
            this.addTab("查看学籍信息", new ImageIcon("resource//tab_student.png"), panelEnquire, "查看个人学籍信息");
            this.setFont(new Font("宋体", Font.BOLD, 24));
        }


    }
}

