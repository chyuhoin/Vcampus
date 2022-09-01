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

import java.io.IOException;

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
    JLabel lblName = new JLabel("姓名 : ");JTextField txtName = new JTextField();
    JLabel lblStudentNum = new JLabel("学号 : ");JTextField txtStudentNum = new JTextField();
    JLabel lblIdNum = new JLabel("一卡通号 : ");JTextField txtIdNum = new JTextField();
    JLabel lblNation = new JLabel("民族 : ");JTextField txtNation = new JTextField();
    JLabel lblGender = new JLabel("性别 : ");JTextField txtGender = new JTextField();
    JLabel lblPolitic = new JLabel("政治面貌 : ");JTextField txtPolitic = new JTextField();
    JLabel lblGrade = new JLabel("年级 : ");JTextField txtGrade = new JTextField();
    JLabel lblLengthOfSchooling = new JLabel("学制 : ");JTextField txtLengthOfSchooling = new JTextField();
    JLabel lblStatus = new JLabel("学籍状态 : ");JTextField txtStatus = new JTextField();
    JLabel lblDep = new JLabel("院系 : ");JTextField txtDep = new JTextField();
    JLabel lblMajor = new JLabel("专业 : ");JTextField txtMajor = new JTextField();
    JLabel lblGraduation = new JLabel("预计毕业时间 : ");JTextField txtGraduation = new JTextField();
    JLabel lblPhoneNum = new JLabel("联系电话 : ");JTextField txtPhoneNum = new JTextField();
    JButton btnInquire = new JButton("查询");
    JLabel lblClass = new JLabel("班级 ： ");JTextField txtClass = new JTextField();
    JLabel lblIdNumber = new JLabel("身份证号 ： ");JTextField txtIdNumber = new JTextField();
    JLabel lblImg = new JLabel();
    CardLayout cardLayout=new CardLayout();//布局 编辑器 表格设置
    MessagePasser passer = ClientMessagePasser.getInstance();
    JPanel panelEnquire = new JPanel();JPanel panelChange = new JPanel();
    JButton btnEdit = new JButton("编辑");JButton btnReturn = new JButton("返回");
    JButton btnOk = new JButton("确认修改");JButton btnCancel = new JButton("取消修改");
    Student t= new Student();
    int x=200,y=90;//起始坐标
    int lblWidth=200,lblHeight=40,txtWidth=110, txtHeight=40;
    int heightDiffer=60;//上下两行高度差
    int ltDiffer1=100,ltDiffer2=40;//1-左起两列标签文本框间隔 2-第三列标签文本框间隔
    int llDiffer=270;//两个标签之间的差距

    public StudentStatusEnquire_A(String ID)
    {
        this.setLayout(cardLayout);
        panelEnquire.setLayout(null);
        panelChange.setLayout(null);

        lblHint.setFont(new Font("宋体", Font.BOLD, 20));
        lblHint.setBounds(300,15,200,40);

        txtIdNumEnquire.setBounds(200+300,20,300,30);

        btnInquire.setFont(new Font("宋体",Font.BOLD, 16));
        btnInquire.setBounds(520+300,20,70,30);
        btnEdit.setFont(new Font("宋体",Font.BOLD, 16));
        btnEdit.setBounds(520+300,520,70,30);
        btnReturn.setFont(new Font("宋体",Font.BOLD, 16));
        btnReturn.setBounds(520+300,520,70,30);
        btnOk.setFont(new Font("宋体",Font.BOLD, 16));
        btnOk.setBounds(450,520,120,30);
        btnCancel.setFont(new Font("宋体",Font.BOLD, 16));
        btnCancel.setBounds(600,520,120,30);

        //setPanel1(t);
        panelEnquire.add(lblHint);
        panelEnquire.add(txtIdNumEnquire);
        panelEnquire.add(btnInquire);

        btnInquire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = txtIdNumEnquire.getText();
                Student student = new Student();
                student.setStudentID(str);
                Gson gson = new Gson();
                String s = gson.toJson(student);
                passer.send(new Message("student", s, "student", "getone"));
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
                setPanel1(t);
                setCard("panelEnquire");

            }
        });
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPanel2(t);
                setCard("panelChange");
            }
        });
        btnReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPanel1(t);
                setCard("panelEnquire");
            }
        });

        this.add(panelEnquire,"panelEnquire");
        this.add(panelChange,"panelChange");
    }
    /**
     * 设置当前需要现实的卡片
     * @param card 卡片对应名称
     */
    public void setCard(String card) { cardLayout.show(this,card); }

    public void enquireInform()
    {
        Message msg = passer.receive();
        Map<String,List<Student>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String,List<Student>>>(){}.getType());
        List<Student> res = map.get("res");
        System.out.println(res.get(0));

        if(res.size()!=0) {
            t= res.get(0);
            setPanel1(t);//如果查到这个人，set设置学生对象，传参数
            setPanel2(t);//同时构建修改页
            setCard("panelEnquire");
        }
        else {
            JOptionPane.showMessageDialog(this, "该生暂无学籍信息", "警告", JOptionPane.ERROR_MESSAGE);
            txtIdNumEnquire.setText("");
        }
    }

    public void changeInform(Student student) throws InterruptedException {
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
        student.setSex(tmpStr=="男"?0:1);//
        student.setStatus(Integer.valueOf(txtStatus.getText()));//数字

        Gson gson = new Gson();
        String s = gson.toJson(student);
        passer.send(new Message("student", s, "student", "post"));

        Thread.sleep(100);

        Message msg = passer.receive();
        System.out.println(msg);
        Map<String,Object> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String,Object>>(){}.getType());

        if(map.get("res").equals("OK"))//成功写入
        {
            JOptionPane.showMessageDialog(this, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            t=student;
            setPanel1(student);
            setCard("panelEnquire");
        }
        else
        {
            JOptionPane.showMessageDialog(this, "修改失败", "警告", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setPanel1(Student student)///展示详情的表格
    {
        panelEnquire.removeAll();

        panelEnquire.add(lblHint);
        panelEnquire.add(txtIdNumEnquire);
        panelEnquire.add(btnInquire);
        panelEnquire.add(btnEdit);

        //照片
        ImageIcon img = null;// 这是背景图片 .png .jpg .gif 等格式的图片都可以
        if(student.getImage()!=null) {
            try {
                img = new ImageIcon(StringAndImage.StringToImage(student.getImage()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //ImageIcon img = new ImageIcon(student.getImage());
            img.setImage(img.getImage().getScaledInstance(120, 150, Image.SCALE_DEFAULT));//这里设置图片大小，目前是20*20
            lblImg.setIcon(img);
            lblImg.setBounds(30, 30, 120, 150);
        }

            //第一行信息，姓名 学号 一卡通号
            lblName.setBounds(x, y, lblWidth, lblHeight);
            txtName.setBounds(x + ltDiffer1, y, txtWidth, txtHeight);
            txtName.setText(student.getName());
            setLabelFont(lblName, txtName,false);

            lblStudentNum.setBounds(x + llDiffer, y, lblWidth, lblHeight);
            txtStudentNum.setBounds(x + llDiffer + ltDiffer1, y, txtWidth, txtHeight);
            txtStudentNum.setText(student.getStudentNumber());
            setLabelFont(lblStudentNum, txtStudentNum,false);

            lblIdNum.setBounds(x + llDiffer * 2, y, lblWidth, lblHeight);
            txtIdNum.setBounds(x + llDiffer * 2 + ltDiffer1 + ltDiffer2, y, txtWidth, txtHeight);
            txtIdNum.setText(student.getStudentID());
            setLabelFont(lblIdNum, txtIdNum,false);

            //第二行信息 民族 性别 政治面貌
            lblNation.setBounds(x, y + heightDiffer, lblWidth, lblHeight);
            txtNation.setBounds(x + ltDiffer1, y + heightDiffer, txtWidth, txtHeight);
            txtNation.setText(student.getNation());
            setLabelFont(lblNation, txtNation,false);

            lblGender.setBounds(x + llDiffer, y + heightDiffer, lblWidth, lblHeight);
            txtGender.setBounds(x + llDiffer + ltDiffer1, y + heightDiffer, txtWidth, txtHeight);
            if (student.getSex() != null) {
                String gender = (student.getSex() == 0) ? "男" : "女";
                txtGender.setText(gender);
            }
            setLabelFont(lblGender, txtGender,false);

            lblPolitic.setBounds(x + llDiffer * 2, y + heightDiffer, lblWidth, lblHeight);
            txtPolitic.setBounds(x + llDiffer * 2 + ltDiffer1 + ltDiffer2, y + heightDiffer, txtWidth, txtHeight);
//            txtPolitic.setText(student.getPolitics());
            setLabelFont(lblPolitic, txtPolitic,false);

            //第三行信息  年级 学制 学籍状态
            lblGrade.setBounds(x, y + heightDiffer * 2, lblWidth, lblHeight);
            txtGrade.setBounds(x + ltDiffer1, y + heightDiffer * 2, txtWidth, txtHeight);
//            txtGrade.setText(student.getGrade().toString());
            setLabelFont(lblGrade, txtGrade,false);

            lblLengthOfSchooling.setBounds(x + llDiffer, y + heightDiffer * 2, lblWidth, lblHeight);
            txtLengthOfSchooling.setBounds(x + llDiffer + ltDiffer1, y + heightDiffer * 2, txtWidth, txtHeight);
//            txtLengthOfSchooling.setText(student.getEducationalSystem().toString());
            setLabelFont(lblLengthOfSchooling, txtLengthOfSchooling,false);

            lblStatus.setBounds(x + llDiffer * 2, y + heightDiffer * 2, lblWidth, lblHeight);
            txtStatus.setBounds(x + llDiffer * 2 + ltDiffer1 + ltDiffer2, y + heightDiffer * 2, txtWidth, txtHeight);
//            txtStatus.setText(student.getStatus().toString());
            setLabelFont(lblStatus, txtStatus,false);

            //第4行信息 院系 专业
            lblDep.setBounds(x, y + heightDiffer * 3, lblWidth, lblHeight);
            txtDep.setBounds(x + ltDiffer1, y + heightDiffer * 3, txtWidth * 3 - 30, txtHeight);
//            txtDep.setText(student.getSchool());
            setLabelFont(lblDep, txtDep,false);

            lblMajor.setBounds(x + llDiffer * 2 - 70, y + heightDiffer * 3, lblWidth, lblHeight);
            txtMajor.setBounds(x + llDiffer * 2 + ltDiffer1 - 70, y + heightDiffer * 3, txtWidth * 2, txtHeight);
            txtMajor.setText(student.getMajor());
            setLabelFont(lblMajor, txtMajor,false);

            //第5行信息 班级 预计毕业时间
            lblClass.setBounds(x, y + heightDiffer * 4, lblWidth, lblHeight);
            txtClass.setBounds(x + ltDiffer1, y + heightDiffer * 4, txtWidth, txtHeight);
            txtClass.setText(student.getClasss());
            setLabelFont(lblClass, txtClass,false);

            lblGraduation.setBounds(x + llDiffer, y + heightDiffer * 4, lblWidth, lblHeight);
            txtGraduation.setBounds(x + ltDiffer1 * 2 + llDiffer, y + heightDiffer * 4, txtWidth, txtHeight);
            txtGraduation.setText(student.getGraduateTime());///????
            setLabelFont(lblGraduation, txtGraduation,false);

            //第6行信息，身份证号
            lblIdNumber.setBounds(x, y + heightDiffer * 5, lblWidth, lblHeight);
            txtIdNumber.setBounds(x + ltDiffer1 * 2 - 60, y + heightDiffer * 5, txtWidth * 2, txtHeight);
            txtIdNumber.setText(student.getIDcard());
            setLabelFont(lblIdNumber, txtIdNumber,false);

            //第7行信息 联系电话
            lblPhoneNum.setBounds(x, y + heightDiffer * 6, lblWidth, lblHeight);
            txtPhoneNum.setBounds(x + ltDiffer1 * 2 - 60, y + heightDiffer * 6, txtWidth * 2, txtHeight);
            txtPhoneNum.setText(student.getPhoneNumber());
            setLabelFont(lblPhoneNum, txtPhoneNum,false);
        if(student.getGrade()!=null && student.getSchool()!=null && student.getStatus()!=null) {
            txtStudentNum.setText(student.getStudentNumber());
            txtPolitic.setText(student.getPolitics());
            txtGrade.setText(student.getGrade().toString());
            txtLengthOfSchooling.setText(student.getEducationalSystem().toString());
            txtStatus.setText(student.getStatus().toString());
            txtDep.setText(student.getSchool());
        }
        panelEnquire.add(lblName); panelEnquire.add(txtName); panelEnquire.add(lblStudentNum);
        panelEnquire.add(txtStudentNum); panelEnquire.add(lblIdNum); panelEnquire.add(txtIdNum);
        panelEnquire.add(lblNation); panelEnquire.add(txtNation); panelEnquire.add(lblGender);
        panelEnquire.add(txtGender); panelEnquire.add(lblPolitic); panelEnquire.add(txtPolitic);
        panelEnquire.add(lblGrade); panelEnquire.add(txtGrade); panelEnquire.add(lblLengthOfSchooling);
        panelEnquire.add(txtLengthOfSchooling); panelEnquire.add(lblStatus); panelEnquire.add(txtStatus);
        panelEnquire.add(lblDep); panelEnquire.add(txtDep); panelEnquire.add(lblMajor);
        panelEnquire.add(txtMajor); panelEnquire.add(lblGraduation); panelEnquire.add(txtGraduation);
        panelEnquire.add(lblPhoneNum); panelEnquire.add(txtPhoneNum);panelEnquire.add(lblClass);
        panelEnquire.add(txtClass);panelEnquire.add(lblIdNumber); panelEnquire.add(txtIdNumber);

        panelEnquire.updateUI();
        panelEnquire.repaint();
        updateUI();
        repaint();
    }

    public void setPanel2(Student student)//修改界面
    {
        panelChange.removeAll();
        panelChange.add(btnReturn);
        panelChange.add(btnOk);
        panelChange.add(btnCancel);

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
        setLabelFont(lblName,txtName,true);

        lblStudentNum.setBounds(x+llDiffer,y,lblWidth,lblHeight);
        txtStudentNum.setBounds(x+llDiffer+ltDiffer1,y,txtWidth,txtHeight);
        txtStudentNum.setText(student.getStudentNumber());
        setLabelFont(lblStudentNum,txtStudentNum,true);

        lblIdNum.setBounds(x+llDiffer*2,y,lblWidth,lblHeight);
        txtIdNum.setBounds(x+llDiffer*2+ltDiffer1+ltDiffer2 ,y,txtWidth,txtHeight);
        txtIdNum.setText(student.getStudentID());
        setLabelFont(lblIdNum,txtIdNum,true);

        //第二行信息 民族 性别 政治面貌
        lblNation.setBounds(x,y+heightDiffer,lblWidth,lblHeight);
        txtNation.setBounds(x+ltDiffer1,y+heightDiffer,txtWidth,txtHeight);
        txtNation.setText(student.getNation());
        setLabelFont(lblNation,txtNation,true);

        lblGender.setBounds(x+llDiffer,y+heightDiffer,lblWidth,lblHeight);
        txtGender.setBounds(x+llDiffer+ltDiffer1,y+heightDiffer,txtWidth,txtHeight);
        String gender=(student.getSex()==0)?"男":"女";
        txtGender.setText(gender);
        setLabelFont(lblGender,txtGender,true);

        lblPolitic.setBounds(x+llDiffer*2,y+heightDiffer,lblWidth,lblHeight);
        txtPolitic.setBounds(x+llDiffer*2+ltDiffer1+ltDiffer2,y+heightDiffer,txtWidth,txtHeight);
        txtPolitic.setText(student.getPolitics());
        setLabelFont(lblPolitic,txtPolitic,true);

        //第三行信息  年级 学制 学籍状态
        lblGrade.setBounds(x,y+heightDiffer*2,lblWidth,lblHeight);
        txtGrade.setBounds(x+ltDiffer1,y+heightDiffer*2,txtWidth,txtHeight);
        txtGrade.setText(student.getGrade().toString());
        setLabelFont(lblGrade,txtGrade,true);

        lblLengthOfSchooling.setBounds(x+llDiffer,y+heightDiffer*2,lblWidth,lblHeight);
        txtLengthOfSchooling.setBounds(x+llDiffer+ltDiffer1,y+heightDiffer*2,txtWidth,txtHeight);
        txtLengthOfSchooling.setText(student.getEducationalSystem().toString());
        setLabelFont(lblLengthOfSchooling,txtLengthOfSchooling,true);

        lblStatus.setBounds(x+llDiffer*2,y+heightDiffer*2,lblWidth,lblHeight);
        txtStatus.setBounds(x+llDiffer*2+ltDiffer1+ltDiffer2,y+heightDiffer*2,txtWidth,txtHeight);
        txtStatus.setText(student.getStatus().toString());
        setLabelFont(lblStatus,txtStatus,true);

        //第4行信息 院系 专业
        lblDep.setBounds(x,y+heightDiffer*3,lblWidth,lblHeight);
        txtDep.setBounds(x+ltDiffer1,y+heightDiffer*3,txtWidth*3-30,txtHeight);
        txtDep.setText(student.getSchool());
        setLabelFont(lblDep,txtDep,true);

        lblMajor.setBounds(x+llDiffer*2-70,y+heightDiffer*3,lblWidth,lblHeight);
        txtMajor.setBounds(x+llDiffer*2+ltDiffer1-70,y+heightDiffer*3,txtWidth*2,txtHeight);
        txtMajor.setText(student.getMajor());
        setLabelFont(lblMajor,txtMajor,true);

        //第5行信息 班级 预计毕业时间
        lblClass.setBounds(x,y+heightDiffer*4,lblWidth,lblHeight);
        txtClass.setBounds(x+ltDiffer1,y+heightDiffer*4,txtWidth,txtHeight);
        txtClass.setText(student.getClasss());
        setLabelFont(lblClass,txtClass,true);

        lblGraduation.setBounds(x+llDiffer,y+heightDiffer*4,lblWidth,lblHeight);
        txtGraduation.setBounds(x+ltDiffer1*2+llDiffer,y+heightDiffer*4,txtWidth,txtHeight);
        txtGraduation.setText(student.getGraduateTime());///????
        setLabelFont(lblGraduation, txtGraduation,true);

        //第6行信息，身份证号
        lblIdNumber.setBounds(x,y+heightDiffer*5,lblWidth,lblHeight);
        txtIdNumber.setBounds(x+ltDiffer1*2-60,y+heightDiffer*5,txtWidth*2,txtHeight);
        txtIdNumber.setText(student.getIDcard());
        setLabelFont(lblIdNumber,txtIdNumber,true);

        //第7行信息 联系电话
        lblPhoneNum.setBounds(x,y+heightDiffer*6,lblWidth,lblHeight);
        txtPhoneNum.setBounds(x+ltDiffer1*2-60,y+heightDiffer*6,txtWidth*2,txtHeight);
        txtPhoneNum.setText(student.getPhoneNumber());
        setLabelFont(lblPhoneNum,txtPhoneNum,true);

        panelChange.add(lblName); panelChange.add(txtName); panelChange.add(lblStudentNum);
        panelChange.add(txtStudentNum); panelChange.add(lblIdNum); panelChange.add(txtIdNum);
        panelChange.add(lblNation); panelChange.add(txtNation); panelChange.add(lblGender);
        panelChange.add(txtGender); panelChange.add(lblPolitic); panelChange.add(txtPolitic);
        panelChange.add(lblGrade); panelChange.add(txtGrade); panelChange.add(lblLengthOfSchooling);
        panelChange.add(txtLengthOfSchooling); panelChange.add(lblStatus); panelChange.add(txtStatus);
        panelChange.add(lblDep); panelChange.add(txtDep); panelChange.add(lblMajor); panelChange.add(txtMajor);
        panelChange.add(lblGraduation); panelChange.add(txtGraduation);panelChange.add(lblPhoneNum);
        panelChange.add(txtPhoneNum); panelChange.add(lblClass); panelChange.add(txtClass);
        panelChange.add(lblIdNumber); panelChange.add(txtIdNumber);panelChange.add(lblImg);
        panelChange.updateUI();
        panelChange.repaint();
        updateUI();
        repaint();
    }

    /**
     * 设置标签、文本框字体及文本框可编辑状态
     * @param label 标签
     * @param text  文本
     * @param flag  国旗
     */
    public void setLabelFont(JLabel label,JTextField text,Boolean flag)
    {
        label.setFont(new Font("楷体", Font.BOLD, 24));
        text.setFont(new Font("楷体", Font.BOLD, 20));
        text.setEditable(flag);
    }
}