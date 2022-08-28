/** ===================================================
 * Title: PanelTeacherInform.java
 * Created: [2022-8-26 10:49:27] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 教务管理——教师信息界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-26,创建此文件
 *2. 2022-8-26,完善设置
 *3.
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window.setjpCourse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Student;
import com.vcampus.pojo.Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelTeacherInform extends JPanel{
    int x=300,y=100;//起始坐标
    int lblWidth=300,lblHeight=40,txtWidth=500, txtHeight=40;
    int heightDiffer=60;//上下两行高度差
    int ltDiffer1=180;//1-左起两列标签文本框间隔 2-第三列标签文本框间隔
    int btnWidth = 80, btnHeight = 30;

    JLabel lblHint = new JLabel("教师身份信息");
    JLabel lblName = new JLabel("姓名       ");
    JTextField txtName = new JTextField();
    JLabel lblIdNum = new JLabel("一卡通号   ");
    JTextField txtIdNum = new JTextField();
    JLabel lblDep = new JLabel("学院       ");
    JTextField txtDep = new JTextField();
    JLabel lblMajor = new JLabel("专业      ");
    JTextField txtMajor = new JTextField();
    JLabel lblTime = new JLabel("非偏好时间  ");
    JTextField txtTime = new JTextField();

    //JComboBox
    String[] listTime = new String[]{"","1","2","3","4","5","6","7","8","9","10","11","12","13"};
    String[] listWeek = new String[]{"","周一","周二","周三","周四","周五"};

    JButton btnAdd = new JButton("添加");
    JButton btnEdit = new JButton("编辑");
    JButton btnOk = new JButton("确定");
    JButton btnCancel = new JButton("取消");

    JLabel labels[] = {lblName,lblIdNum,lblDep,lblMajor,lblTime};
    JTextField texts[] = {txtName,txtIdNum,txtDep,txtMajor,txtTime};

    // 创建一个下拉列表框
    JComboBox<String>[] comboBoxTime;
    JComboBox<String>[] comboBoxWeek;

    CardLayout cardLayout=new CardLayout();
    MessagePasser passer = ClientMessagePasser.getInstance();

    Teacher teacher = new Teacher();//承接结果

    public PanelTeacherInform(String ID)//以老师对象为参数
    {
        this.setLayout(cardLayout);
        JPanel P1 = new JPanel();
        JPanel P2 = new JPanel();
        P1.setLayout(null);
        P2.setLayout(null);

        lblHint.setBounds(x+280,40,lblWidth,lblHeight);
        lblHint.setFont(new Font("宋体",Font.BOLD, 28));
        setButtonFont(btnAdd);
        btnAdd.setBounds(1000,345,btnWidth,btnHeight);
        setButtonFont(btnOk);
        btnOk.setBounds(520,500,btnWidth,btnHeight);
        setButtonFont(btnCancel);
        btnCancel.setBounds(760,500,btnWidth,btnHeight);
        setButtonFont(btnEdit);
        btnEdit.setBounds(1000,y,btnWidth,btnHeight);

        P1.add(btnEdit);P1.add(lblHint);
        P2.add(btnAdd);P2.add(btnOk);P2.add(btnCancel);P2.add(lblHint);

        this.add(P1,"P1");
        this.add(P2,"P2");

       // Teacher t = new Teacher();
        teacher.setTeacherID(ID);
        Gson gson = new Gson();
        String s = gson.toJson(teacher);
        passer.send(new Message("teacher", s, "lesson", "showstatusteacher"));

        try {
            Thread.sleep(100);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        Message msg = passer.receive();
        Map<String, java.util.List<Teacher>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Teacher>>>(){}.getType());
        List<Teacher> res = map.get("res");
        System.out.println(res);

        if(res.size()!=0) {
            teacher= res.get(0);
            setCard("P1");
            setPanel(P1,false);
        }
        else {
            JOptionPane.showMessageDialog(this, "未查询到身份信息", "警告", JOptionPane.ERROR_MESSAGE);
        }

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCard("P2");
                setPanel(P2,true);
            }
        });

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                teacher.setTeacherName(txtName.getText());
                teacher.setTeacherID(txtIdNum.getText());
                teacher.setSchool(txtDep.getText());
                teacher.setAbledMajor(txtMajor.getText());
                teacher.setTime(txtTime.getText());

                Gson gson = new Gson();
                String s = gson.toJson(teacher);
                passer.send(new Message("teacher", s, "lesson", "setteacher"));

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
                    informFrame("修改成功",false);
                    setCard("P1");
                    setPanel(P1,false);
                }
                else
                {
                    informFrame("修改失败",true);
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCard("P1");
                setPanel(P1,false);
            }
        });

        btnAdd.addActionListener(new ActionListener() {//添加非偏好时间 下拉框
            @Override
            public void actionPerformed(ActionEvent e)
            {


            }
        });


    }

    public void setCard(String card)
    {
        cardLayout.show(this,card);
    }

    public void informFrame(String title,Boolean flag)
    {
        if(flag) { JOptionPane.showMessageDialog(this, title, "警告", JOptionPane.ERROR_MESSAGE);}
        else { JOptionPane.showMessageDialog(this, title, "提示", JOptionPane.INFORMATION_MESSAGE);}
    }

    public void setLabelFont(JLabel label,JTextField text)
    {
        label.setFont(new Font("楷体", Font.BOLD, 24));
        text.setFont(new Font("楷体", Font.BOLD, 20));
    }

    public void setPanel(JPanel p,Boolean flag)//传老师的对象
    {
        txtName.setText(teacher.getTeacherName());//姓名
        txtIdNum.setText(teacher.getTeacherID());//一卡通
        txtDep.setText(teacher.getSchool());//学院
        txtMajor.setText(teacher.getAbledMajor());//专业
        txtTime.setText(teacher.getTime());//时间（可能要转格式）

        for(int i=0;i<5;i++)
        {
            labels[i].setBounds(x,y+heightDiffer*i,lblWidth,lblHeight);
            texts[i].setBounds(x+ltDiffer1,y+heightDiffer*i,txtWidth,txtHeight);
            setLabelFont(labels[i],texts[i]);
            p.add(labels[i]); p.add(texts[i]);
            texts[i].setEditable(flag);
        }
        p.add(lblHint);
        p.updateUI();
        p.repaint();
    }
    public void setButtonFont(JButton button)
    {
        button.setFont(new Font("宋体",Font.BOLD, 18));
        button.setContentAreaFilled(false);
    }
}
