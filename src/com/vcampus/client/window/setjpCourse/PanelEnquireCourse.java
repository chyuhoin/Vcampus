/** ===================================================
 * Title: PanelEnquireCourse.java
 * Created: [2022-8-27 14:02:20] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 课程查询-通用界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-27,创建此文件
 *2.
 *3.
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpCourse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.*;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

public class PanelEnquireCourse extends JPanel {
    JButton btnInquire = new JButton("查询");
    String[] listData = new String[]{"选择搜索条件","课程名称", "课程编号", "授课教师", "开课学院","所属专业"};
    //表格数据
    Object[] columnNames={"课程名称", "授课教师一卡通号","开课学院","所属专业","上课时间","授课教室","课容量"};
    Object[][] tableData=new Object[][]{};//保存所有用户信息
    // 创建一个下拉列表框
    JComboBox<String> comboBox = new JComboBox<String>(listData);
    JTextField txtEnquire = new JTextField();
    MessagePasser passer = ClientMessagePasser.getInstance();

    public PanelEnquireCourse ()
    {
        this.setLayout(null);
        int x=470,y=50;//起始坐标
        int txtWidth=110, txtHeight=42;

        // 设置默认选中的条目
        comboBox.setBounds(x-110,y,220,40);
        comboBox.setFont(new Font("楷体", Font.BOLD, 24));
        comboBox.setOpaque(true);

        txtEnquire.setBounds(x+130,y,txtWidth*3,txtHeight);
        txtEnquire.setFont(new Font("楷体", Font.BOLD, 20));

        btnInquire.setFont(new Font("宋体",Font.BOLD, 20));
        btnInquire.setBounds(x+480,y,80,40);

        // 添加到内容面板
        this.add(comboBox);
        this.add(txtEnquire);
        this.add(btnInquire);


        btnInquire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = txtEnquire.getText();
                System.out.println(str);//传送，接收结果bool型
                Lesson lesson=new Lesson();
                int temp = comboBox.getSelectedIndex();
                switch(temp)//发出消息
                {//"课程名称", "课程编号", "授课教师", "开课学院","所属专业","上课时间"
                    case 1:
                        System.out.println("课程名称"+str);
                        lesson.setName(str);
                        break;
                    case 2:
                        System.out.println("课程编号"+str);//book。set
                        lesson.setLessonID(str);
                        break;
                    case 3:
                        System.out.println("授课教师"+str);//book。set
                        lesson.setTeacherID(str);
                        break;
                    case 4:
                        System.out.println("开课学院"+str);//book。set
                        lesson.setSchool(str);
                        break;
                    case 5:
                        System.out.println("所属专业"+str);//book。set
                        lesson.setMajor(str);
                        break;
                    case 0:
                        informFrame("请选择搜索条件类型！",true);
                        break;
                    default:
                        break;
                }
                //传消息出去
                System.out.println();
                Gson gson = new Gson();
                String s = gson.toJson(lesson);
                passer.send(new Message("admin", s, "lesson", "getone"));
/*
                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
*/
                Message msg = passer.receive();
                Map<String, java.util.List<Lesson>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, List<Lesson>>>(){}.getType());
                List<Lesson> res = map.get("res");

                //如果不空，构建表格
                if(res.size()!=0)
                {
                    tableData=new Object[res.size()][];
                    //"课程名称", "授课教师一卡通号","开课学院","所属专业","上课时间","授课教室","课容量"
                    for(int i=0;i<res.size();i++) {
                        tableData[i]=new Object[columnNames.length];
                        tableData[i][0]=res.get(i).getName();
                        tableData[i][1]=res.get(i).getTeacherID();
                        tableData[i][2]=res.get(i).getSchool();
                        tableData[i][3]=res.get(i).getMajor();
                        tableData[i][4]=res.get(i).getTime();
                        tableData[i][5]=res.get(i).getClassroom();
                        tableData[i][6]=res.get(i).getMaxSize();
                    }
                    setPanel();
                }
                else
                {
                    informFrame("未查询寻到相关课程！",true);
                }
            }
        });

    }

    public void informFrame(String title,Boolean flag)
    {
        if(flag)
        { JOptionPane.showMessageDialog(this, title, "警告", JOptionPane.ERROR_MESSAGE);}
        else
        { JOptionPane.showMessageDialog(this, title, "提示", JOptionPane.INFORMATION_MESSAGE);}
    }

    public void setPanel()
    {
        //绘制表格
       //System.out.println("chegg");
        MyTablePanel_Course tableP=new MyTablePanel_Course(tableData,columnNames);
        this.add(tableP);
        tableP.getTable().getColumnModel().getColumn(1).setPreferredWidth(120);
        tableP.setBounds(20,110,1210,510);//设置位置和大小

    }


}
