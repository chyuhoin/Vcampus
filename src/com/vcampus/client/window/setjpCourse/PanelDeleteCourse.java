/** ===================================================
 * Title: PanelDeleteCourse.java
 * Created: [2022-8-26 17:07:21] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 删除课程-管理员界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-26,创建此文件
 *2. 2022-8-26,完善设置
 *3.
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window.setjpCourse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelDeleteCourse extends JPanel{
    JButton btnInquire = new JButton("查询");//查询
    JButton btnOk = new JButton("确认");
    JButton btnCancel = new JButton("取消");
    JButton btnDeleteAll = new JButton("删除所有课程");

    MyTable table=null;
    DefaultTableModel tableModel =null;//表格模型
    JScrollPane scrollPane = null;
    JPanel panel = new JPanel();

    Object[] columnNames={"教师姓名", "一卡通号","学院","专业","操作"};
    Object[][] tableData=new Object[][]{
            {"张三","2233","计算机","计算机","删除"},
            {"张三","2233","计算机","计算机","删除"},
            {"张三","2233","计算机","计算机","删除"},
            {"张三","2233","计算机","计算机","删除"},
            {"张三","2233","计算机","计算机","删除"},
            {"张三","2233","计算机","计算机","删除"},
            {"张三","2233","计算机","计算机","删除"},
            {"张三","2233","计算机","计算机","删除"},
            {"张三","2233","计算机","计算机","删除"},
            {"张三","2233","计算机","计算机","删除"},
            {"张三","2233","计算机","计算机","删除"},
            {"张三","2233","计算机","计算机","删除"},
            {"张三","2233","计算机","计算机","删除"},
            {"张三","2233","计算机","计算机","删除"},
            {"张三","2233","计算机","计算机","删除"}};//保存所有用户信息

    JTextField txtEnquire = new JTextField();
    JLabel lblHint = new JLabel("输入课程编号 ：");
    int btnWidth = 120, btnHeight = 30;
    int txtWidth=110, txtHeight=32;



    public PanelDeleteCourse()
    {
        this.setLayout(null);
        int x=60,y=50,differ=160;

        setButtonFont(btnInquire);
        btnInquire.setBounds(970,y,btnWidth,btnHeight);

        setButtonFont(btnOk);
        btnOk.setBounds(420,500,btnWidth,btnHeight);

        setButtonFont(btnCancel);
        btnCancel.setBounds(660,500,btnWidth,btnHeight);

        setButtonFont(btnDeleteAll);
        btnDeleteAll.setBounds(850,550,btnWidth*2,btnHeight);

        lblHint.setFont(new Font("宋体", Font.BOLD, 24));
        lblHint.setBounds(535,y-5,200,40);

        txtEnquire.setBounds(730,y,txtWidth*2,txtHeight);
        txtEnquire.setFont(new Font("楷体", Font.BOLD, 20));

        panel.setLayout(null);
        panel.setBounds(10,120,1200,400);
        //panel.setBorder(BorderFactory.createTitledBorder("分组框")); //设置面板边框，实现分组框的效果，此句代码为关键代码
        //panel.setBorder(BorderFactory.createLineBorder(Color.red));//设置面板边框颜色

        this.add(btnInquire);
        this.add(lblHint);
        this.add(txtEnquire);
        this.add(panel);

        btnInquire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = txtEnquire.getText();
                System.out.println(str);

                //传消息出去 打包一个课程

                //传给详情界面
                setTable();
            }
        });

        btnDeleteAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = txtEnquire.getText();
                System.out.println(str);
                //发送消息，打包课程,删除所有
                tableData=null;

                deleteAll();
            }
        });




    }

    public void setButtonFont(JButton button)
    {
        button.setFont(new Font("宋体",Font.BOLD, 22));
        button.setContentAreaFilled(false);
    }

    public void informFrame(String title,Boolean flag)
    {
        if(flag)
        { JOptionPane.showMessageDialog(this, title, "警告", JOptionPane.ERROR_MESSAGE);}
        else
        { JOptionPane.showMessageDialog(this, title, "提示", JOptionPane.INFORMATION_MESSAGE);}
    }

    public void setTable()
    {
        //接收消息

        //如果不空，构建表格

        add(btnDeleteAll);
        add(panel);
        System.out.println("构件表格");
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

            tableModel =new DefaultTableModel(tableData,columnNames);
            table=new MyTable(tableModel);
            table.setRowSelectionAllowed(true);
            // 行高
            //table.setRowHeight(24);
            // 数据
            table.setRowHeight(30);// 设置行高
            table.setFont(new Font("黑体",Font.PLAIN,18));//表格字体
            table.getTableHeader().setFont(new Font("黑体",Font.BOLD,20));//表头字体

            //添加渲染器
            //table.getColumn("操作").setCellRenderer(new ButtonRenderer());
            //添加编辑器
            //table.getColumn("操作").setCellEditor( new ButtonEditor(new JCheckBox()));


            //添加渲染器
            ButtonRenderer btnR=new ButtonRenderer("删除");
            table.getColumn("操作").setCellRenderer(btnR);
            //添加编辑器
            ButtonEditor btnE=new ButtonEditor(new JCheckBox());
            table.getColumn("操作").setCellEditor(btnE);
            btnE.getButton().setText("删除");
            //GUI设置
            //JScrollPane scroll = new JScrollPane(table);

           /* btnE.getButton().addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    int rowName=((JTable)e.getSource()).rowAtPoint(e.getPoint());
                    String teacherID=(String)tableModel.getValueAt(rowName, 1);
                    String courseID=txtEnquire.getText();
                    //int rowName = rowAtPoint(e.getPoint());

                    //收发请求，删掉对应课程老师
                    //如果成功
                    if(true) {
                        informFrame("删除成功",false);
                        tableModel.removeRow(rowName);
                        updateUI();
                        repaint();
                    }else {
                        informFrame("删除失败",true);
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

            */



          /*  btnE.getButton().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //int rowName=((JTable)e.getSource()).rowAtPoint(e.getPoint());
                    //String teacherID=(String)tableModel.getValueAt(rowName, 1);
                    String courseID=txtEnquire.getText();
                    System.out.println(btnE.getButton().getX());

                    //收发请求，删掉对应课程老师
                    //如果成功
                    if(true) {
                        informFrame("删除成功",false);
                        //tableModel.removeRow(rowName);
                        //updateUI();
                        //repaint();
                    }else {
                        informFrame("删除失败",true);
                    }



                    //刷新渲染器
                    btnE.fireEditingStopped_1();
                }
            });

           */


            //panel.add(scroll);


            scrollPane = new JScrollPane(table);
            scrollPane.setBounds(0,0,1200,400);
            panel.add(scrollPane);

            btnE.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int rowName=btnE.getThisRow();
                    String teacherID=(String)tableModel.getValueAt(rowName, 1);
                    String courseID=txtEnquire.getText();
                    System.out.println(teacherID+"  "+courseID);

                    //收发请求，删掉对应课程老师
                    //如果成功
                    if(true) {
                        informFrame("删除成功",false);
                        btnE.fireEditingStopped_1();
                        tableModel.removeRow(rowName);//不能删最后一行
                        //setTable();//重新构建表格  拿回所有数据？
                        updateUI();
                        repaint();
                    }else {
                        informFrame("删除失败",true);
                    }


                }
            });

            /*
            table.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }
                @Override
                public void mousePressed(MouseEvent e) {

                    int rowName=((JTable)e.getSource()).rowAtPoint(e.getPoint());
                    int columnName=((JTable)e.getSource()).columnAtPoint(e.getPoint());
                    int temp=tableModel.getRowCount();
                    if(columnName==4)
                    {
                        String teacherID=(String)tableModel.getValueAt(rowName, 1);
                        String courseID=txtEnquire.getText();
                        System.out.println("当前列"+rowName+"00"+temp);

                            //收发请求，删掉对应课程老师
                            //如果成功
                            if(true) {
                                informFrame("删除成功",false);
                                tableModel.removeRow(rowName);//不能删最后一行
                                //setTable();//重新构建表格  拿回所有数据？
                                updateUI();
                                repaint();
                            }else {
                                informFrame("删除失败",true);
                            }
                    }
                    btnE.fireEditingStopped_1();

                }
                @Override
                public void mouseReleased(MouseEvent e) {}
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            });

             */
        }
        else
        {
            informFrame("未查询到相关课程",true);
        }
        updateUI();
        repaint();
    }

    public void deleteAll()
    {
        //接收消息
        //成功
        if(true)
        {
            informFrame("删除成功",false);
            remove(panel);
            remove(btnDeleteAll);
            updateUI();
            repaint();
        }
        else
        {
            informFrame("删除失败",true);
        }
    }

}
