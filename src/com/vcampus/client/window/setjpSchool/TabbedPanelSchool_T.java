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

public class TabbedPanelSchool_T extends JTabbedPane {
    public TabbedPanelSchool_T(int flag,String ID)
    {
        if(flag==2) {
            this.setTabPlacement(2);
            this.setBounds(0, 0, 1400, 650);//注意！！！！！！！！！！！！！！！！！！！！！！！

            JPanel panel = new JPanel();
            JLabel lblHint = new JLabel("未查询到学籍信息！");
            lblHint.setFont(new Font("宋体", Font.BOLD, 28));
            lblHint.setBounds(40, 70, 200, 70);
            panel.add(lblHint);

            this.addTab("查看个人学籍信息", null, panel, "查看个人学籍信息");

            this.setFont(new Font("宋体", Font.BOLD, 24));
        }

        // jp.add(jtbp);
    }
}
