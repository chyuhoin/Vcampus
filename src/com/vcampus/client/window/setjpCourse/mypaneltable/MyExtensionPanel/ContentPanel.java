/** ===================================================
 * Title: PanelStudentManage_T.java
 * Created: [2022-8-30  00:53:12] by  张星喆
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 教务系统-学生-选课-基于选课的内容面板，存放数个小面板 SubContentPanel
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-30,创建此文件
 *2. 2022-8-30,完善设置
 *3.2022-8-30,前后端连接 修改人：张星喆
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window.setjpCourse.mypaneltable.MyExtensionPanel;

import com.vcampus.pojo.Lesson;

import javax.swing.*;
import java.awt.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class ContentPanel extends JPanel {

    // private static final long serialVersionUID = 1L;
    private ContentPanel thisPanel;
    private Object[][] Info=new Object[0][];
    int numPanel=0;
    private SubContentPanel[] subjp =null;//子panel
    private Lesson lesson=null;//课程ID
    private String studentID=null;//学生ID

    public ContentPanel(Lesson l,String sID,Object[][] info) {
        super();
        lesson=l;
        studentID=sID;
        Info=info;
        thisPanel=this;
        createContent();

        upDateContent();//设置响应事件
    }

    public void createContent() {
        int w = this.getPreferredSize().width;
        int h = this.getPreferredSize().height;

        int numTeacher=Info.length;
        numPanel=numTeacher;

        JPanel jp=new JPanel();//网格布局
        GridLayout layout = new GridLayout(3, 3);
        jp.setLayout(layout);
        // 设置 水平 和 竖直 间隙
        layout.setHgap(20);
        layout.setVgap(20);
        //设置网格
        if(numTeacher<=3) {
            layout.setRows(1);layout.setColumns(3);
            numPanel=3;
        } else if(numTeacher<=6) {
            layout.setRows(2);layout.setColumns(3);
            numPanel=6;
        } else {
            layout.setRows(3);layout.setColumns(3);
            numPanel=9;
        }


        //小panel
        subjp = new SubContentPanel[numPanel];
        for(int i=0;i<numPanel;i++){
            if(i<numTeacher) {
                subjp[i] = new SubContentPanel(lesson, studentID, Info[i],this);
                subjp[i].upDateSubPanel(this);
            }
            else subjp[i]=new SubContentPanel();//空panel

            jp.add(subjp[i]);
        }


        this.setLayout(new CardLayout(20,20));
        this.add(jp);

    }


    public void upDateContent(){
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                for(int i=0;i<numPanel;i++){
                    if(i<Info.length) {
                        subjp[i] = new SubContentPanel(lesson, studentID, Info[i],thisPanel);
                        subjp[i].upDateSubPanel(thisPanel);
                    }
                    else subjp[i]=new SubContentPanel();//空panel
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

}