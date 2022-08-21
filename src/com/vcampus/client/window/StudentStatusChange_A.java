/** ===================================================
 * Title: StudentStatus_A.java
 * Created: [2022-8-19 16:48:13] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 学籍管理——管理员界面（修改学生学籍信息）
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-19,创建此文件
 *2. 2022-8-19,完成管理员修改学籍信息界面 修改人：韩宇
 *3.
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Student;
import java.lang.InterruptedException;

public class StudentStatusChange_A extends JPanel{
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
    JButton btnOk = new JButton("确认修改");
    JButton btnCancel = new JButton("取消修改");
    JLabel lblClass = new JLabel("班级 ： ");
    JTextField txtClass = new JTextField();
    JLabel lblIdNumber = new JLabel("身份证号 ： ");
    JTextField txtIdNumber = new JTextField();
    JLabel lblImg = new JLabel();
    MessagePasser passer = ClientMessagePasser.getInstance();
    Student t = new Student();//接收结果

    public StudentStatusChange_A(String ID)
    {
        this.setLayout(null);

        lblHint.setFont(new Font("宋体", Font.BOLD, 20));
        lblHint.setBounds(300,15,200,40);
        txtIdNumEnquire.setBounds(200+300,20,300,30);

        btnInquire.setFont(new Font("宋体",Font.BOLD, 16));
        btnInquire.setBounds(520+300,20,70,30);

        btnOk.setFont(new Font("宋体",Font.BOLD, 16));
        btnOk.setBounds(450,520,120,30);

        btnCancel.setFont(new Font("宋体",Font.BOLD, 16));
        btnCancel.setBounds(600,520,120,30);

        this.add(lblHint);
        this.add(txtIdNumEnquire);
        this.add(btnInquire); this.add(btnOk); this.add(btnCancel);

        btnInquire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = txtIdNumEnquire.getText();
                System.out.println(str);//传送，接收结果bool型以及学生对象
               // enquireInform(true);
                //Student student = new Student();
                //student.setStudentID(str);
                /*Gson gson = new Gson();
                //String s = gson.toJson(student);
                passer.send(new Message("student", "studentID:"+str, "student", "getone"));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                 */
                Student student = new Student();
                student.setStudentID(str);
                Gson gson = new Gson();
                String s = gson.toJson(student);
                passer.send(new Message("student", s, "student", "getone"));
                //enquireInform();
                enquireInform();
            }
        });

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    changeInform(t);//设置参数为学生的对象，修改所有信息
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                set(t);
            }
        });

    }

    public void enquireInform()
    {
        Message msg = passer.receive();
        Map<String, java.util.List<Student>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Student>>>(){}.getType());
        List<Student> res = map.get("res");

        if(res.size()!=0) {
            set(res.get(0));//如果查到这个人，set设置学生对象，传参数
            t= res.get(0);
        }
        else {
            JOptionPane.showMessageDialog(this, "查无此人！", "警告", JOptionPane.ERROR_MESSAGE);
            txtIdNumEnquire.setText("");
        }
    }

    public void changeInform(Student student) throws InterruptedException {
        String str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12,str13;
        str1=txtName.getText();
        str2=txtStudentNum.getText();
        str3=txtIdNum.getText();
        str4=txtNation.getText();
        str5=txtGender.getText();
        str6=txtPolitic.getText();
        str7=txtGrade.getText();
        str8=txtLengthOfSchooling.getText();
        str9=txtStatus.getText();
        str10=txtDep.getText();
        str11=txtMajor.getText();
        str12= txtGraduation.getText();
        str13=txtPhoneNum.getText();

        student.setName(txtName.getText());
        student.setStudentNumber(txtStudentNum.getText());
        student.setStudentID(txtIdNum.getText());
        student.setClasss(txtClass.getText());
        student.setEducationalSystem(Integer.valueOf(txtLengthOfSchooling.getText()));
        student.setGrade(Integer.valueOf(txtGrade.getText()));
        student.setImage(lblImg.getText());
        student.setIDcard(txtIdNumber.getText());
        student.setMajor(txtMajor.getText());
        student.setNation(txtNation.getText());
        student.setPhoneNumber(txtPhoneNum.getText());
        student.setPolitics(txtPolitic.getText());
        student.setSchool(txtDep.getText());
        String tmpStr=txtGender.getText();
        if(tmpStr=="男")
            student.setSex(0);
        else
            student.setSex(1);
        //student.setSex(Integer.valueOf(txtGender.getText()));//
        student.setStatus(Integer.valueOf(txtStatus.getText()));//数字


        System.out.println(str1+'\n'+str2+'\n'+str3+'\n'+str4+'\n'+str5+'\n'+str6+'\n'+str7+'\n'+str8+'\n'+str9+'\n'+str10+'\n'+str11+'\n'+str12+'\n'+str13+'\n');

        Gson gson = new Gson();
        String s = gson.toJson(student);
        passer.send(new Message("student", s, "student", "post"));

        Thread.sleep(100);

        Message msg = passer.receive();
        System.out.println(msg);
        Map<String,Object> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String,Object>>(){}.getType());
        //List<Student> res = map.get("res");
        //传输信息
        //接收bool结果
        if(map.get("res").equals("OK"))//成功写入
        {
            JOptionPane.showMessageDialog(this, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            set(student);
           // enquireInform(false);

            //this.remove(btnOk);
            //this.remove(btnCancel);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "修改失败", "警告", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void set(Student student)
    {
        updateUI();
        repaint();
        int x=200,y=90;//起始坐标
        int lblWidth=200,lblHeight=40,txtWidth=110, txtHeight=40;
        int heightDiffer=60;//上下两行高度差
        int ltDiffer1=100,ltDiffer2=40;//1-左起两列标签文本框间隔 2-第三列标签文本框间隔
        int llDiffer=270;//两个标签之间的差距

        lblImg.setIcon(new ImageIcon(student.getImage()));
        lblImg.setBounds(0,0,70,100);

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
        add(lblPhoneNum); add(txtPhoneNum); add(lblClass); add(txtClass);add(lblIdNumber); add(txtIdNumber);
        add(lblImg);
    }

    public void setLabelFont(JLabel label,JTextField text)
    {
        label.setFont(new Font("楷体", Font.BOLD, 24));
        text.setFont(new Font("楷体", Font.BOLD, 20));
        text.setEditable(true);
    }
}
