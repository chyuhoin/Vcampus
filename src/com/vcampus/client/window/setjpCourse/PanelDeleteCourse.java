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
 *3. 2022-8-28,前后端连接 修改人：韩宇
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window.setjpCourse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Lesson;
import com.vcampus.pojo.Teacher;

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

    Object[] columnNames={"课程名称", "授课教师一卡通号","开课学院","所属专业","上课时间","授课教室","课容量","操作"};
    Object[][] tableData=new Object[][]{};//保存所有用户信息

    JTextField txtEnquire = new JTextField();
    JLabel lblHint = new JLabel("输入课程编号 ：");
    int btnWidth = 120, btnHeight = 30;
    int txtWidth=110, txtHeight=32;
    MessagePasser passer = ClientMessagePasser.getInstance();

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

        this.add(btnInquire);
        this.add(lblHint);
        this.add(txtEnquire);
        this.add(panel);

        btnInquire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Lesson lesson=new Lesson();
                lesson.setLessonID(txtEnquire.getText());
                System.out.println(lesson);

                Gson gson = new Gson();
                String s = gson.toJson(lesson);
                passer.send(new Message("admin", s, "lesson", "getone"));

                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                setTable();
                updateUI();
                repaint();
            }
        });

        btnDeleteAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Lesson lesson=new Lesson();
                lesson.setLessonID(txtEnquire.getText());
                System.out.println(lesson);

                Gson gson = new Gson();
                String s = gson.toJson(lesson);
                passer.send(new Message("admin", s, "lesson", "delete"));

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
        panel.removeAll();
        //接收消息
        Message msg = passer.receive();
        Map<String, java.util.List<Lesson>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, List<Lesson>>>(){}.getType());
        List<Lesson> res = map.get("res");
        //如果不空，构建表格
        if(res.size()!=0)
        {
            add(btnDeleteAll);
            add(panel);
            //设置表格内容
            tableData = new Object[res.size()][8];
            for (int i = 0; i < res.size(); i++) {
                tableData[i][0] = res.get(i).getName();
                tableData[i][1] = res.get(i).getTeacherID();
                tableData[i][2] = res.get(i).getSchool();
                tableData[i][3] = res.get(i).getMajor();
                tableData[i][4] = res.get(i).getTime();
                tableData[i][5] = res.get(i).getClassroom();
                tableData[i][6] = res.get(i).getMaxSize();
            }

            tableModel =new DefaultTableModel(tableData,columnNames);
            table=new MyTable(tableModel);
            table.setRowSelectionAllowed(true);
            table.getColumnModel().getColumn(1).setPreferredWidth(120);

            // 数据
            table.setRowHeight(30);// 设置行高
            table.setFont(new Font("黑体",Font.PLAIN,18));//表格字体
            table.getTableHeader().setFont(new Font("黑体",Font.BOLD,20));//表头字体

            //添加渲染器
            ButtonRenderer btnR=new ButtonRenderer("删除");
            table.getColumn("操作").setCellRenderer(btnR);
            //添加编辑器
            ButtonEditor btnE=new ButtonEditor(new JCheckBox());
            table.getColumn("操作").setCellEditor(btnE);

            scrollPane = new JScrollPane(table);
            scrollPane.setBounds(0,0,1200,400);
            panel.add(scrollPane);

            btnE.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int rowName=btnE.getThisRow();
                    String teacherID=(String)tableModel.getValueAt(rowName, 1);
                    String courseID=txtEnquire.getText();
                    //System.out.println(teacherID+"  "+courseID);

                    Lesson lesson=new Lesson();
                    lesson.setInnerID(courseID+teacherID);
                    System.out.println(lesson);

                    Gson gson = new Gson();
                    String s = gson.toJson(lesson);
                    passer.send(new Message("admin", s, "lesson", "deleteone"));

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }

                    Message msg = passer.receive();
                    System.out.println(msg);
                    Map<String,Object> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String,Object>>(){}.getType());

                    if(map.get("res").equals("OK"))
                    {
                        informFrame("删除成功",false);
                        btnE.fireEditingStopped_1();
                        tableModel.removeRow(rowName);
                        updateUI();
                        repaint();
                    }
                    else { informFrame("删除失败",true); }
                }
            });
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
        Message msg = passer.receive();
        System.out.println(msg);
        Map<String,Object> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String,Object>>(){}.getType());

        if(map.get("res").equals("OK"))
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
