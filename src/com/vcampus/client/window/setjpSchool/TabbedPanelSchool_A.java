/** ===================================================
 * Title: TabbedPanelSchool_A.java
 * Created: [2022-8-17 17:15:06] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 学籍管理——教师界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-17,创建此文件
 *2. 2022-8-18,修改选项卡设置，增加学籍信息显示 修改人：韩宇
 *3.
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpSchool;

import com.vcampus.client.window.setjpSchool.StudentStatusChange_A;
import com.vcampus.client.window.setjpSchool.StudentStatusEnquire_A;

import javax.swing.*;
import java.awt.*;

public class TabbedPanelSchool_A extends JTabbedPane {

    public TabbedPanelSchool_A(int flag,String ID)
    {
        if(flag==3) {
            this.setTabPlacement(1);
            this.setBounds(0, 0, 1200, 650);

            JPanel panelEnquire = new StudentStatusEnquire_A(ID);
            JPanel panelChange = new StudentStatusChange_A(ID);

            this.addTab("查看学籍信息", null, panelEnquire, "查看学籍信息");
            this.addTab("修改学籍信息", null, panelChange, "修改学籍信息");
            //this.addTab("选项三", null, jp13,"点击查看选项三");
            this.setFont(new Font("宋体", Font.BOLD, 24));
        }
    }


}
