/** ===================================================
 * Title: PanelStudentManage.java
 * Created: [2022-8-27  22:09:20] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 学生管理-管理员界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-27,创建此文件
 *2.
 *3.
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpCourse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PanelStudentManage extends JPanel {

    CardLayout cardLayout=new CardLayout();

    JButton btnInquire = new JButton("查询");
    JLabel lblHintLesson = new JLabel("课程编号 ：");
    JLabel lblHintTeacher = new JLabel("教师一卡通号 ：");
    JTextField txtLessonID = new JTextField();
    JTextField txtTeacherID = new JTextField();
    JButton btnMark = new JButton("添加成绩");


    MyTable table=null;
    DefaultTableModel tableModel =null;//表格模型
    JScrollPane scrollPane = null;
    JPanel panel = new JPanel();

    Object[] columnNames={"学生姓名", "一卡通号","学号","学院","专业","成绩"};
    Object[][] tableData=new Object[][]{
            {"张三","2233","09020207","计算机","计算机","100"},
            {"张三","2233","09020207","计算机","计算机","100"},
            {"张三","2233","09020207","计算机","计算机","100"},
            {"张三","2233","09020207","计算机","计算机","100"},
            {"张三","2233","09020207","计算机","计算机","100"},
            {"张三","2233","09020207","计算机","计算机","100"},
            {"张三","2233","09020207","计算机","计算机","100"},
            {"张三","2233","09020207","计算机","计算机","100"},
            {"张三","2233","09020207","计算机","计算机","100"},
            {"张三","2233","09020207","计算机","计算机","100"},
            {"张三","2233","09020207","计算机","计算机","100"},
            {"张三","2233","09020207","计算机","计算机","100"},
            {"张三","2233","09020207","计算机","计算机","100"},
            {"张三","2233","09020207","计算机","计算机","100"},
            {"张三","2233","09020207","计算机","计算机","100"}};//保存所有用户信息

    public PanelStudentManage()
    {
        this.setLayout(null);
        //removeAll();
        //JPanel P1 = new JPanel();
        //JPanel P2 = new JPanel();
       // P1.setLayout(null);
        //P2.setLayout(null);

        int x=160,y=30;//起始坐标
        int txtWidth=110, txtHeight=30;

        lblHintLesson.setFont(new Font("宋体", Font.BOLD, 24));
        lblHintLesson.setBounds(x,y,200,40);
        txtLessonID.setBounds(x+140,y+5,txtWidth*2,txtHeight);
        txtLessonID.setFont(new Font("楷体", Font.BOLD, 20));

        lblHintTeacher.setFont(new Font("宋体", Font.BOLD, 24));
        lblHintTeacher.setBounds(x+420,y,200,40);

        txtTeacherID.setFont(new Font("楷体", Font.BOLD, 20));
        txtTeacherID.setBounds(x+610,y+5,txtWidth*2,txtHeight);

        btnInquire.setFont(new Font("宋体",Font.BOLD, 20));
        btnInquire.setBounds(x+865,y+4,80,30);

        btnMark.setFont(new Font("宋体",Font.BOLD, 20));
        btnMark.setBounds(x+785,520,80*2,30);

        panel.setLayout(null);
        panel.setBounds(130,100,1000,400);
        // 添加到内容面板

        this.add(btnInquire);
        this.add(txtLessonID);
        this.add(txtTeacherID);
        this.add(lblHintLesson);
        this.add(lblHintTeacher);
        this.add(panel);

        btnInquire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str1 = txtLessonID.getText();
                String str2 = txtTeacherID.getText();

                //传消息，接收消息，如果查到结果，构建表格
                if(true)
                {
                    //设置表格内容
            /*tableData = new Object[res.size()][4];
            for (int i = 0; i < res.size(); i++) {
                tableData[i][0] = res.get(i).getName();
                tableData[i][1] = res.get(i).getID();
                tableData[i][2] = res.get(i).getMajor();
                tableData[i][3] = res.get(i).getSchool();
            }

             */
                    setTable();
                }
                else
                {
                    informFrame("未查询到相关课程！",true);
                }
            }
        });

        btnMark.addActionListener(new ActionListener() {//添加成绩
            @Override
            public void actionPerformed(ActionEvent e) {
                /*tableData = new Object[res.size()][4];
            for (int i = 0; i < res.size(); i++) {


                 String mark=(String)table.getValueAt(i, 5);
                 res.get(i).setMark()=tableData[i][3];
            }

             */
                //传消息
                //接收
                if(true)
                {
                    informFrame("成绩添加成功",false);
                }
                else
                {
                    informFrame("成绩添加失败",true);
                }
            }
        });

    }

    public void setTable()
    {
        tableModel =new DefaultTableModel(tableData,columnNames);
        table=new MyTable(tableModel);
        table.setRowSelectionAllowed(true);
        // 数据
        table.setRowHeight(30);// 设置行高
        table.setFont(new Font("黑体",Font.PLAIN,18));//表格字体
        table.getTableHeader().setFont(new Font("黑体",Font.BOLD,20));//表头字体

        //添加渲染器
        //ButtonRenderer btnR=new ButtonRenderer();
        //table.getColumn("操作").setCellRenderer(btnR);
        //添加编辑器
        //ButtonEditor btnE=new ButtonEditor(new JCheckBox());
        //table.getColumn("操作").setCellEditor(btnE);
        //btnE.getButton().setText("删除");

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0,0,1000,400);
        panel.add(scrollPane);
        this.add(btnMark);


        updateUI();
        repaint();
    }

    public void informFrame(String title,Boolean flag)
    {
        if(flag)
        { JOptionPane.showMessageDialog(this, title, "警告", JOptionPane.ERROR_MESSAGE);}
        else
        { JOptionPane.showMessageDialog(this, title, "提示", JOptionPane.INFORMATION_MESSAGE);}
    }




}
