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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    //Teacher teacher = new Teacher();//承接结果

    public PanelTeacherInform()//以老师对象为参数
    {
        this.setLayout(cardLayout);
        //removeAll();
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
        setCard("P1");
        setPanel(P1,false);

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
                //teacher.getName=txtName.getText():
                //teacher.getName=txtName.getText():
                //teacher.getName=txtName.getText():
                //teacher.getName=txtName.getText():
                //teacher.getName=txtName.getText():

                //发消息，接收消息，中间停100ms

                if(true)//写入成功
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
            public void actionPerformed(ActionEvent e) {


            }
        });


    }

    public void setCard(String card)
    {
        cardLayout.show(this,card);
    }

    public void informFrame(String title,Boolean flag)
    {
        if(flag)
        { JOptionPane.showMessageDialog(this, title, "警告", JOptionPane.ERROR_MESSAGE);}
        else
        { JOptionPane.showMessageDialog(this, title, "提示", JOptionPane.INFORMATION_MESSAGE);}
    }

    public void setLabelFont(JLabel label,JTextField text)
    {
        label.setFont(new Font("楷体", Font.BOLD, 24));
        text.setFont(new Font("楷体", Font.BOLD, 20));
        //text.setEditable(f);//true 可编辑，false 不可编辑
    }

    public void setPanel(JPanel p,Boolean flag)//传老师的对象
    {
        //p.removeAll();

        //lblHint.setBounds(x+280,40,lblWidth,lblHeight);
        //lblHint.setFont(new Font("宋体",Font.BOLD, 28));
        txtName.setText("xxx");//姓名
        txtIdNum.setText("213201445");//一卡通
        txtDep.setText("计算机科学与工程学院");//学院
        txtMajor.setText("计算机科学与技术专业");//专业
        txtTime.setText("周一6-7");//时间（可能要转格式）

        for(int i=0;i<5;i++)
        {
            texts[i].setEditable(flag);
        }

        //设置其余坐标和字体
        for(int i=0;i<5;i++)
        {
            labels[i].setBounds(x,y+heightDiffer*i,lblWidth,lblHeight);
            texts[i].setBounds(x+ltDiffer1,y+heightDiffer*i,txtWidth,txtHeight);
            setLabelFont(labels[i],texts[i]);
            p.add(labels[i]); p.add(texts[i]);
        }


        p.add(lblHint);
        p.updateUI();
        p.repaint();

    }
    public void setButtonFont(JButton button)
    {
        button.setFont(new Font("宋体",Font.BOLD, 18));
        //button.setOpaque(true);
        button.setContentAreaFilled(false);
    }

    /*
    public class PanelTeacherInform1 extends JPanel{

        public PanelTeacherInform1()
        {
            this.setLayout(null);

            setButtonFont(btnEdit);
            btnEdit.setBounds(1000,y,btnWidth,btnHeight);
            add(btnEdit);
            //setPanel(this,false);
        }

    }
    public class PanelTeacherInform2 extends JPanel{
        public PanelTeacherInform2()
        {
            this.setLayout(null);

            setButtonFont(btnAdd);
            btnAdd.setBounds(1000,345,btnWidth,btnHeight);
            setButtonFont(btnOk);
            btnOk.setBounds(520,500,btnWidth,btnHeight);
            setButtonFont(btnCancel);
            btnCancel.setBounds(760,500,btnWidth,btnHeight);

            add(btnAdd);add(btnOk);add(btnCancel);
            //setPanel(this,true);

        }

    }

     */


}
