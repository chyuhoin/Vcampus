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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Lesson;
import com.vcampus.pojo.Student;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabbedPanelCourse_S extends JTabbedPane {
    MessagePasser passer = ClientMessagePasser.getInstance();

    public TabbedPanelCourse_S(int flag,String ID) {
        if(flag==1) {
            this.setTabPlacement(2);
            this.setBounds(0, 0, 1400, 650);//注意！！！！！！！！！！！！！！！！！！！！！！！


            JPanel jp11 = new JPanel(new GridLayout(1,1));
            JPanel jp12 = new JPanel();
            JPanel jp13 = new JPanel();

            //选项卡1的内容
            jp11.add(new PanelCourseSelection(ID));


            this.addTab("选课系统", null, jp11, "选课系统");
            this.addTab("已选课程", null, jp12, "查看已选课程");
            this.addTab("我的课表", null, jp13, "查看我的课表");
            this.setFont(new Font("宋体", Font.BOLD, 24));
        }

        //jp.add(jtbp);
    }
}
