/** ===================================================
 * Title: SetJPUser1.java
 * Created: [2022-8-21 15:14:42] by  张星喆
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 用户管理-个人信息界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-14,创建此文件
 *2. 对这个文件的修改历史进行详细描述，一般包括时间，修改者，
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpUSER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetJPUser1 extends JPanel {
    public String[] strList=new String[5];
    public JPanel jp1=new JPanel();
    public JPanel jp2=new JPanel();

    public String ID;
    public SetJPUser1(String id,JPanel jp, CardLayout layout_Card){
        SpringLayout layout_Spring = new SpringLayout();
        jp1.setLayout(layout_Spring);
        jp2.setLayout(layout_Spring);
        jp.add(jp1);
        jp.add(jp2);
        layout_Card.show(jp, "jp1");//先显示jp1

        ID=id;
        setjp1(jp,layout_Spring,layout_Card);
        setjp2(jp,layout_Spring,layout_Card);
    }

    /**
     * 设置文本框的内容
     */
    public void setStrList(){
        for(int i=0;i<strList.length;i++){
            strList[i]="1234567";
        }
    }
    public void setjp1(JPanel jp,SpringLayout layout_Spring,CardLayout layout_Card){
        //设置具体内容
        setStrList();
        //jp1内容
        //标题
        JLabel lbl = new JLabel("个人信息");
        jp1.add(lbl);
        lbl.setFont(new Font("黑体", Font.BOLD, 50));
        layout_Spring.putConstraint(layout_Spring.NORTH, lbl, 20, layout_Spring.NORTH, jp1);  //标签1北侧——>容器北侧
        layout_Spring.putConstraint(layout_Spring.WEST, lbl, 20, layout_Spring.WEST, jp1);    //标签1西侧——>容器西侧
        // 创建修改按钮
        JButton btn=new JButton("编辑");
        jp1.add(btn);
        btn.setFont(new Font("黑体", Font.BOLD, 20));
        layout_Spring.putConstraint(layout_Spring.NORTH, btn, 20, layout_Spring.NORTH, jp1);
        layout_Spring.putConstraint(layout_Spring.EAST, btn, -20, layout_Spring.EAST, jp1);
        //信息列表：
        //标签
        JLabel[] lblList={
                new JLabel("姓名"),
                new JLabel("一卡通号"),
                new JLabel("性别"),
                new JLabel("年龄"),
                new JLabel("联系电话")
        };
        for(int i=0;i<lblList.length;i++){
            JLabel lbli=lblList[i];
            jp1.add(lbli);
            lbli.setFont(new Font("黑体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, lbli, 60+50*i, layout_Spring.SOUTH, lbl);  //标签1北侧——>容器北侧
            layout_Spring.putConstraint(layout_Spring.EAST, lbli, 50, layout_Spring.EAST, lbl);    //标签1西侧——>容器西侧
        }
        //文本框
        //JTextField[] textList=new JTextField[5];
        JTextField[] textList={
                new JTextField(),new JTextField(),
                new JTextField(),new JTextField(),
                new JTextField()
        };
        for(int i=0;i<textList.length;i++){
            textList[i].setText(strList[i]);
        }
        for(int i=0;i<textList.length;i++){
            JTextField texti=textList[i];
            jp1.add(texti);
            texti.setEditable(false);
            texti.setFont(new Font("黑体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, texti, 0, layout_Spring.NORTH, lblList[i]);
            layout_Spring.putConstraint(layout_Spring.WEST, texti, 20, layout_Spring.EAST, lblList[i]);
            texti.setColumns(60);
        }

        //修改按钮监听
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout_Card.next(jp);
                System.out.println("用户管理系统-个人信息-修改");
            }
        });
    }

    public void setjp2(JPanel jp,SpringLayout layout_Spring,CardLayout layout_Card){
        //jp2内容
        //标题
        JLabel lbl2 = new JLabel("编辑信息");
        jp2.add(lbl2);
        lbl2.setFont(new Font("黑体", Font.BOLD, 50));
        layout_Spring.putConstraint(layout_Spring.NORTH, lbl2, 20, layout_Spring.NORTH, jp2);  //标签1北侧——>容器北侧
        layout_Spring.putConstraint(layout_Spring.WEST, lbl2, 20, layout_Spring.WEST, jp2);    //标签1西侧——>容器西侧
        //按钮
        JButton btn1=new JButton("确认");
        JButton btn2=new JButton("取消");
        jp2.add(btn1);jp2.add(btn2);
        btn1.setFont(new Font("黑体", Font.BOLD, 20));
        btn2.setFont(new Font("黑体", Font.BOLD, 20));
        layout_Spring.putConstraint(layout_Spring.NORTH, btn2, 20, layout_Spring.NORTH, jp2);//取消按钮
        layout_Spring.putConstraint(layout_Spring.EAST, btn2, -20, layout_Spring.EAST, jp2);
        layout_Spring.putConstraint(layout_Spring.NORTH, btn1, 20, layout_Spring.NORTH, jp2);//确认按钮
        layout_Spring.putConstraint(layout_Spring.EAST, btn1, -30, layout_Spring.WEST, btn2);
        //信息列表：
        //标签
        JLabel[] lblList={
                new JLabel("姓名"),
                new JLabel("一卡通号"),
                new JLabel("性别"),
                new JLabel("年龄"),
                new JLabel("联系电话")
        };
        for(int i=0;i<lblList.length;i++){
            JLabel lbli=lblList[i];
            jp2.add(lbli);
            lbli.setFont(new Font("黑体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, lbli, 60+50*i, layout_Spring.SOUTH, lbl2);  //标签1北侧——>容器北侧
            layout_Spring.putConstraint(layout_Spring.EAST, lbli, 50, layout_Spring.EAST, lbl2);    //标签1西侧——>容器西侧
        }
        //文本框
        //JTextField[] textList=new JTextField[5];
        JTextField[] textList={
                new JTextField(),new JTextField(),
                new JTextField(),new JTextField(),
                new JTextField()
        };
        for(int i=0;i< textList.length;i++){
            textList[i].setText(strList[i]);
        }
        for(int i=0;i<textList.length;i++){
            JTextField texti=textList[i];
            jp2.add(texti);
            texti.setEditable(false);
            texti.setFont(new Font("黑体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, texti, 0, layout_Spring.NORTH, lblList[i]);
            layout_Spring.putConstraint(layout_Spring.WEST, texti, 20, layout_Spring.EAST, lblList[i]);
            texti.setColumns(60);
        }
        textList[4].setEditable(true);

        //确认按钮监听
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textTel=textList[4].getText();//获取电话号码
                //补充数据传输==========================================================================================
                strList[4]=textTel;
                setjp1(jp,layout_Spring,layout_Card);
                layout_Card.first(jp);
                System.out.println("用户管理系统-个人信息-修改编辑确认");
            }
        });
        //取消按钮监听
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout_Card.first(jp);
                System.out.println("用户管理系统-个人信息-修改编辑取消");
            }
        });
    }
}
