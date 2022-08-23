/** ===================================================
 * Title: StudentStatusEnquire_A.java
 * Created: [2022-8-18 17:12:13] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 学籍管理——管理员界面(查询学生学籍信息）
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-18,创建此文件
 *2. 2022-8-19,完成管理员查询学籍信息界面 修改人：韩宇
 *3. 2022-8-21,完善前后端连接   修改人：韩宇
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window.setjpSchool;

import com.vcampus.dao.utils.StringAndImage;
import com.vcampus.pojo.Student;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.User;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class StudentStatusEnquire_A extends JPanel {
    JTextField txtIdNumEnquire = new JTextField();
    JLabel lblHint = new JLabel("输入学生一卡通号 ：");
    JLabel lblName = new JLabel("姓名 : ");
    JTextField txtName = new JTextField();
    JLabel lblStudentNum = new JLabel("学号 : ");
    JTextField txtStudentNum = new JTextField();
    JLabel lblIdNum = new JLabel("一卡通号 : ");
    JTextField txtIdNum = new JTextField();
    JLabel lblNation = new JLabel("民族 : ");
    JTextField txtNation = new JTextField();
    JLabel lblGender = new JLabel("性别 : ");
    JTextField txtGender = new JTextField();
    JLabel lblPolitic = new JLabel("政治面貌 : ");
    JTextField txtPolitic = new JTextField();
    JLabel lblGrade = new JLabel("年级 : ");
    JTextField txtGrade = new JTextField();
    JLabel lblLengthOfSchooling = new JLabel("学制 : ");
    JTextField txtLengthOfSchooling = new JTextField();
    JLabel lblStatus = new JLabel("学籍状态 : ");
    JTextField txtStatus = new JTextField();
    JLabel lblDep = new JLabel("院系 : ");
    JTextField txtDep = new JTextField();
    JLabel lblMajor = new JLabel("专业 : ");
    JTextField txtMajor = new JTextField();
    JLabel lblGraduation = new JLabel("预计毕业时间 : ");
    JTextField txtGraduation = new JTextField();
    JLabel lblPhoneNum = new JLabel("联系电话 : ");
    JTextField txtPhoneNum = new JTextField();
    JButton btnInquire = new JButton("查询");
    JLabel lblClass = new JLabel("班级 ： ");
    JTextField txtClass = new JTextField();
    JLabel lblIdNumber = new JLabel("身份证号 ： ");
    JTextField txtIdNumber = new JTextField();
    JLabel lblImg = new JLabel();

    MessagePasser passer = ClientMessagePasser.getInstance();
    public StudentStatusEnquire_A(String ID)
    {
        this.setLayout(null);

        lblHint.setFont(new Font("宋体", Font.BOLD, 20));
        lblHint.setBounds(300,15,200,40);

        txtIdNumEnquire.setBounds(200+300,20,300,30);

        btnInquire.setFont(new Font("宋体",Font.BOLD, 16));
        btnInquire.setBounds(520+300,20,70,30);

        this.add(lblHint);
        this.add(txtIdNumEnquire);
        this.add(btnInquire);

        btnInquire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = txtIdNumEnquire.getText();
                System.out.println(str);//传送，接收结果bool型 和学生对象
                //enquireInform(true);\
                //MessagePasser passer = ClientMessagePasser.getInstance();
                Student student = new Student();
                student.setStudentID(str);
                Gson gson = new Gson();
                String s = gson.toJson(student);
                passer.send(new Message("student", s, "student", "getone"));
                enquireInform();


                //return map.get("res").equals("OK");

            }
        });

    }

    public void enquireInform()
    {
        Message msg = passer.receive();
        Map<String,List<Student>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String,List<Student>>>(){}.getType());
        List<Student> res = map.get("res");

        //enquireInform(res.size()!=0,res.get(0));
        if(res.size()!=0) {
            set(res.get(0));//如果查到这个人，set设置学生对象，传参数
        }
        else {
            JOptionPane.showMessageDialog(this, "查无此人！", "警告", JOptionPane.ERROR_MESSAGE);
            txtIdNumEnquire.setText("");
        }
    }

    public void set(Student student)
    {
        int x=200,y=90;//起始坐标
        int lblWidth=200,lblHeight=40,txtWidth=110, txtHeight=40;
        int heightDiffer=60;//上下两行高度差
        int ltDiffer1=100,ltDiffer2=40;//1-左起两列标签文本框间隔 2-第三列标签文本框间隔
        int llDiffer=270;//两个标签之间的差距

        //照片
        ImageIcon img = null;// 这是背景图片 .png .jpg .gif 等格式的图片都可以
        try {
            img = new ImageIcon(StringAndImage.StringToImage(student.getImage()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //ImageIcon img = new ImageIcon(student.getImage());
        img.setImage(img.getImage().getScaledInstance(120,150,Image.SCALE_DEFAULT));//这里设置图片大小，目前是20*20
        lblImg.setIcon(img);
        lblImg.setBounds(30,30,120,150);

        //第一行信息，姓名 学号 一卡通号
        lblName.setBounds(x,y,lblWidth,lblHeight);
        txtName.setBounds(x+ltDiffer1,y,txtWidth,txtHeight);
        txtName.setText(student.getName());
        setLabelFont(lblName,txtName);

        lblStudentNum.setBounds(x+llDiffer,y,lblWidth,lblHeight);
        txtStudentNum.setBounds(x+llDiffer+ltDiffer1,y,txtWidth,txtHeight);
        txtStudentNum.setText(student.getStudentNumber());
        setLabelFont(lblStudentNum,txtStudentNum);

        lblIdNum.setBounds(x+llDiffer*2,y,lblWidth,lblHeight);
        txtIdNum.setBounds(x+llDiffer*2+ltDiffer1+ltDiffer2 ,y,txtWidth,txtHeight);
        txtIdNum.setText(student.getStudentID());
        setLabelFont(lblIdNum,txtIdNum);

        //第二行信息 民族 性别 政治面貌
        lblNation.setBounds(x,y+heightDiffer,lblWidth,lblHeight);
        txtNation.setBounds(x+ltDiffer1,y+heightDiffer,txtWidth,txtHeight);
        txtNation.setText(student.getNation());
        setLabelFont(lblNation,txtNation);

        lblGender.setBounds(x+llDiffer,y+heightDiffer,lblWidth,lblHeight);
        txtGender.setBounds(x+llDiffer+ltDiffer1,y+heightDiffer,txtWidth,txtHeight);
        String gender=(student.getSex()==0)?"男":"女";
        txtGender.setText(gender);
        setLabelFont(lblGender,txtGender);

        lblPolitic.setBounds(x+llDiffer*2,y+heightDiffer,lblWidth,lblHeight);
        txtPolitic.setBounds(x+llDiffer*2+ltDiffer1+ltDiffer2,y+heightDiffer,txtWidth,txtHeight);
        txtPolitic.setText(student.getPolitics());
        setLabelFont(lblPolitic,txtPolitic);

        //第三行信息  年级 学制 学籍状态
        lblGrade.setBounds(x,y+heightDiffer*2,lblWidth,lblHeight);
        txtGrade.setBounds(x+ltDiffer1,y+heightDiffer*2,txtWidth,txtHeight);
        txtGrade.setText(student.getGrade().toString());
        setLabelFont(lblGrade,txtGrade);

        lblLengthOfSchooling.setBounds(x+llDiffer,y+heightDiffer*2,lblWidth,lblHeight);
        txtLengthOfSchooling.setBounds(x+llDiffer+ltDiffer1,y+heightDiffer*2,txtWidth,txtHeight);
        txtLengthOfSchooling.setText(student.getEducationalSystem().toString());
        setLabelFont(lblLengthOfSchooling,txtLengthOfSchooling);

        lblStatus.setBounds(x+llDiffer*2,y+heightDiffer*2,lblWidth,lblHeight);
        txtStatus.setBounds(x+llDiffer*2+ltDiffer1+ltDiffer2,y+heightDiffer*2,txtWidth,txtHeight);
        txtStatus.setText(student.getStatus().toString());
        setLabelFont(lblStatus,txtStatus);

        //第4行信息 院系 专业
        lblDep.setBounds(x,y+heightDiffer*3,lblWidth,lblHeight);
        txtDep.setBounds(x+ltDiffer1,y+heightDiffer*3,txtWidth*3-30,txtHeight);
        txtDep.setText(student.getSchool());
        setLabelFont(lblDep,txtDep);

        lblMajor.setBounds(x+llDiffer*2-70,y+heightDiffer*3,lblWidth,lblHeight);
        txtMajor.setBounds(x+llDiffer*2+ltDiffer1-70,y+heightDiffer*3,txtWidth*2,txtHeight);
        txtMajor.setText(student.getMajor());
        setLabelFont(lblMajor,txtMajor);

        //第5行信息 班级 预计毕业时间
        lblClass.setBounds(x,y+heightDiffer*4,lblWidth,lblHeight);
        txtClass.setBounds(x+ltDiffer1,y+heightDiffer*4,txtWidth,txtHeight);
        txtClass.setText(student.getClasss());
        setLabelFont(lblClass,txtClass);

        lblGraduation.setBounds(x+llDiffer,y+heightDiffer*4,lblWidth,lblHeight);
        txtGraduation.setBounds(x+ltDiffer1*2+llDiffer,y+heightDiffer*4,txtWidth,txtHeight);
        txtGraduation.setText(student.getGraduateTime());///????
        setLabelFont(lblGraduation, txtGraduation);

        //第6行信息，身份证号
        lblIdNumber.setBounds(x,y+heightDiffer*5,lblWidth,lblHeight);
        txtIdNumber.setBounds(x+ltDiffer1*2-60,y+heightDiffer*5,txtWidth*2,txtHeight);
        txtIdNumber.setText(student.getIDcard());
        setLabelFont(lblIdNumber,txtIdNumber);

        //第7行信息 联系电话
        lblPhoneNum.setBounds(x,y+heightDiffer*6,lblWidth,lblHeight);
        txtPhoneNum.setBounds(x+ltDiffer1*2-60,y+heightDiffer*6,txtWidth*2,txtHeight);
        txtPhoneNum.setText(student.getPhoneNumber());
        setLabelFont(lblPhoneNum,txtPhoneNum);

        add(lblName); add(txtName); add(lblStudentNum); add(txtStudentNum); add(lblIdNum); add(txtIdNum);
        add(lblNation); add(txtNation); add(lblGender); add(txtGender); add(lblPolitic); add(txtPolitic);
        add(lblGrade); add(txtGrade); add(lblLengthOfSchooling);
        add(txtLengthOfSchooling); add(lblStatus); add(txtStatus);
        add(lblDep); add(txtDep); add(lblMajor); add(txtMajor); add(lblGraduation); add(txtGraduation);
        add(lblPhoneNum); add(txtPhoneNum);add(lblClass); add(txtClass);add(lblIdNumber); add(txtIdNumber);


        updateUI();
        repaint();
    }

    public void setLabelFont(JLabel label,JTextField text)
    {
        label.setFont(new Font("楷体", Font.BOLD, 24));
        text.setFont(new Font("楷体", Font.BOLD, 20));
        text.setEditable(false);
    }
}