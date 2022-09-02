/** ===================================================
 * Title: TabbedPanelSchool_T.java
 * Created: [2022-8-17 17:17:23] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 学籍管理——教师界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-17,创建此文件
 *2. 2022-8-20, 完善界面设置 修改人：韩宇
 *3.
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpSchool;

import javax.swing.*;
import java.awt.*;
/**
 * 教师身份学籍管理界面
 * @author 韩宇 张星喆
 * @date 2022/08/17
 */
public class TabbedPanelSchool_T extends JTabbedPane {
    public TabbedPanelSchool_T(int flag,String ID)
    {
        ImageIcon img=new ImageIcon("Vcampus//resource//default.png");
        //img.setImage(img.getImage().getScaledInstance(1000,1000,Image.SCALE_DEFAULT));
        JLabel back = new JLabel(img);
        back.setText("教师无此功能");
        back.setFont(new Font("宋体",Font.BOLD,50));
        back.setHorizontalTextPosition(CENTER);
        back.setVerticalTextPosition(BOTTOM);
        this.setBackground(new Color(0x0FCFCFD, true));
        this.setOpaque(false);
        if(flag==2) {
            back.setBounds(0,0,500,500);
            back.setOpaque(false);
            this.setTabPlacement(1);
            this.setBounds(0, 0, 1200, 700);//注意！！！！！！！！！！！！！！！！！！！！！！！
            this.add(back);

            JPanel panel = new JPanel(new BorderLayout());
            JLabel lblHint = new JLabel("教师未开放此功能");
            lblHint.setFont(new Font("宋体", Font.BOLD, 28));
            //lblHint.setBounds(40, 70, 200, 70);
            panel.add(lblHint,BorderLayout.CENTER);

           // this.addTab("查看个人学籍信息", null, panel, "查看个人学籍信息");

            this.setFont(new Font("宋体", Font.BOLD, 24));
        }

        // jp.add(jtbp);
    }
}
