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

package com.vcampus.client.window;

import javax.swing.*;
import java.awt.*;

public class TabbedPanelSchool_S extends JTabbedPane {
    public TabbedPanelSchool_S(String ID)
    {
        this.setTabPlacement(2);
        this.setBounds(0,0,1400,650);//注意！！！！！！！！！！！！！！！！！！！！！！！

        JPanel panelEnquire = new StudentStatusEnquire_S(ID);

        this.addTab("查看学籍信息", null, panelEnquire,"查看个人学籍信息");
        this.setFont(new Font("宋体", Font.BOLD, 24));


    }
}

