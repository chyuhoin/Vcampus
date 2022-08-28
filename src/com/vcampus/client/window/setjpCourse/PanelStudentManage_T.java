/** ===================================================
 * Title: PanelStudentManage_T.java
 * Created: [2022-8-28  21:47:20] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 学生管理-教师界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-28,创建此文件
 *2. 2022-8-28，完善设置
 *3.2022-8-28,前后端连接 修改人：韩宇
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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelStudentManage_T extends JPanel {
    JButton btnInquire = new JButton("查询");
    JLabel lblHintLesson = new JLabel("课程编号 ：");
    //JLabel lblHintTeacher = new JLabel("教师一卡通号 ：");
    JTextField txtLessonID = new JTextField();
    //JTextField txtTeacherID = new JTextField();
    JButton btnMark = new JButton("添加成绩");

    MyTable table=null;
    DefaultTableModel tableModel =null;//表格模型
    JScrollPane scrollPane = null;
    JPanel panel = new JPanel();

    Object[] columnNames={"学生姓名", "一卡通号","学号","学院","专业","成绩"};
    Object[][] tableData=new Object[][]{};//保存所有用户信息
    MessagePasser passer = ClientMessagePasser.getInstance();


    public PanelStudentManage_T(String ID)
    {
        this.setLayout(null);
        int x=160,y=30;//起始坐标
        int txtWidth=110, txtHeight=30;

        lblHintLesson.setFont(new Font("宋体", Font.BOLD, 24));
        lblHintLesson.setBounds(x,y,200,40);
        txtLessonID.setBounds(x+140,y+5,txtWidth*2,txtHeight);
        txtLessonID.setFont(new Font("楷体", Font.BOLD, 20));
        /*
        lblHintTeacher.setFont(new Font("宋体", Font.BOLD, 24));
        lblHintTeacher.setBounds(x+420,y,200,40);
        txtTeacherID.setFont(new Font("楷体", Font.BOLD, 20));
        txtTeacherID.setBounds(x+610,y+5,txtWidth*2,txtHeight);

         */

        btnInquire.setFont(new Font("宋体",Font.BOLD, 20));
        btnInquire.setBounds(x+865,y+4,80,30);
        btnMark.setFont(new Font("宋体",Font.BOLD, 20));
        btnMark.setBounds(x+785,520,80*2,30);
        panel.setLayout(null);
        panel.setBounds(130,100,1000,400);

        this.add(btnInquire);
        this.add(txtLessonID);
        //this.add(txtTeacherID);
        this.add(lblHintLesson);
        //this.add(lblHintTeacher);
        this.add(panel);

        btnInquire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!txtLessonID.getText().equals(""))
                {
                    Lesson lesson=new Lesson();
                    Gson gson = new Gson();
                    //String s = gson.toJson(lesson);
                    lesson.setInnerID(txtLessonID.getText()+ID);
                    String s = gson.toJson(lesson);
                    passer.send(new Message("teacher", s, "lesson", "getspecificteacher"));
                    receiveMessage(s);
                }
                else
                {informFrame("请输入课程号！",true); }
            }
        });

        btnMark.addActionListener(new ActionListener() {//添加成绩
            @Override
            public void actionPerformed(ActionEvent e) {
                //List<String> grades = new LinkedList<>();
                StringBuilder grades= new StringBuilder("");
                int rowNum=tableModel.getRowCount();
                //System.out.println(rowNum);
                for(int i=0;i<rowNum;i++)
                {
                    if(i!=rowNum-1)
                    { grades.append(txtLessonID.getText()).append("/").append((String) tableModel.getValueAt(i, 1)).append("/").append((String) tableModel.getValueAt(i, 5)).append(","); }
                    else
                    { grades.append(txtLessonID.getText()).append("/").append((String) tableModel.getValueAt(i, 1)).append("/").append((String) tableModel.getValueAt(i, 5)); }
                }

                //System.out.println(grades);
                //MessagePasser passer = ClientMessagePasser.getInstance();
                Gson gson = new Gson();
                String s = gson.toJson(grades.toString());
                passer.send(new Message("teacher", s, "lesson", "addgradeall"));

                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                Message msg = passer.receive();
                System.out.println(msg);
                Map<String,Object> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String,Object>>(){}.getType());
                if(map.get("res").equals("OK"))
                { informFrame("成绩添加成功",false);}
                else
                { informFrame("成绩添加失败",true); }
            }
        });
    }

    public void receiveMessage(String s)
    {
        try {
            Thread.sleep(100);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        Message msg1 = passer.receive();
        Map<String, java.util.List<Student>> map = new Gson().fromJson(msg1.getData(), new TypeToken<HashMap<String, java.util.List<Student>>>(){}.getType());
        java.util.List<Student> res1 = map.get("res");
        if(res1.size()!=0)
        {
            tableData = new Object[res1.size()][6];//设置表格内容
            for (int i = 0; i < res1.size(); i++) {
                tableData[i][0] = res1.get(i).getName();
                tableData[i][1] = res1.get(i).getStudentID();
                tableData[i][2] = res1.get(i).getStudentNumber();
                tableData[i][3] = res1.get(i).getSchool();
                tableData[i][4] = res1.get(i).getMajor();
            }
            passer.send(new Message("admin", s, "lesson", "getgradeall"));//获取成绩

            Message msg2 = passer.receive();
            Map<String, java.util.List<String>> map2 = new Gson().fromJson(msg2.getData(), new TypeToken<HashMap<String, java.util.List<String>>>(){}.getType());
            List<String> res2 = map2.get("res");
            for(int i=0;i<res1.size();i++)
            { tableData[i][5] = res2.get(i).split("/")[1]; }
            setTable();
        }
        else
        { informFrame("未查询到学生名单！",true);}
    }

    public void setTable()
    {
        panel.removeAll();
        tableModel =new DefaultTableModel(tableData,columnNames);
        table=new MyTable(tableModel);
        table.setRowSelectionAllowed(true);
        table.setRowHeight(30);// 设置行高
        table.setFont(new Font("黑体",Font.PLAIN,18));//表格字体
        table.getTableHeader().setFont(new Font("黑体",Font.BOLD,20));//表头字体

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0,0,1000,400);
        panel.add(scrollPane);
        this.add(btnMark);

        updateUI();
        repaint();
    }

    public void informFrame(String title,Boolean flag)
    {
        if(flag) { JOptionPane.showMessageDialog(this, title, "警告", JOptionPane.ERROR_MESSAGE);}
        else { JOptionPane.showMessageDialog(this, title, "提示", JOptionPane.INFORMATION_MESSAGE);}
    }
}
