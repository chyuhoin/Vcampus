/** ===================================================
 * Title: myMainFrame.java
 * Created: [2022-8-15 20:42:30] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 主界面设置
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-15,创建此文件
 *2. 2022-8-16,重新构建主界面布局,使用卡片布局，修改人：韩宇
 *3. 2022-8-17，由panel切换改为使用选项卡，修改人：韩宇 张星喆
 *4. 2022-8-17，根据不同身份显示不同界面，根据功能实现界面切换，修改人：韩宇
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window;

import com.vcampus.client.window.Button.RoundRectButton;
import com.vcampus.client.window.Panel.ImagePanel;
import com.vcampus.client.window.setjpStore.TabbedPanelStore_A;
import com.vcampus.client.window.setjpStore.TabbedPanelStore_S;
import com.vcampus.client.window.setjpStore.TabbedPanelStore_T;
import com.vcampus.client.window.setjpCourse.TabbedPanelCourse_A;
import com.vcampus.client.window.setjpCourse.TabbedPanelCourse_S;
import com.vcampus.client.window.setjpCourse.TabbedPanelCourse_T;
import com.vcampus.client.window.setjpLibrary.TabbedPanelLibrary_A;
import com.vcampus.client.window.setjpLibrary.TabbedPanelLibrary_S;
import com.vcampus.client.window.setjpLibrary.TabbedPanelLibrary_T;
import com.vcampus.client.window.setjpMessage.TabbedPanelMessage_A;
import com.vcampus.client.window.setjpMessage.TabbedPanelMessage_S;
import com.vcampus.client.window.setjpMessage.TabbedPanelMessage_T;
import com.vcampus.client.window.setjpSchool.TabbedPanelSchool_A;
import com.vcampus.client.window.setjpSchool.TabbedPanelSchool_S;
import com.vcampus.client.window.setjpSchool.TabbedPanelSchool_T;
import com.vcampus.client.window.setjpUser.TabbedPanelUser_A;
import com.vcampus.client.window.setjpUser.TabbedPanelUser_S;
import com.vcampus.client.window.setjpUser.TabbedPanelUser_T;
import org.junit.Test;

import javax.swing.*;
import javax.swing.plaf.metal.MetalTabbedPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class myMainFrame extends JFrame {
    private JPanel panelMain = new JPanel();
    public JPanel panelTop = new JPanel();
    public  JPanel panelBottom = new JPanel();
    public JPanel panelLeft = new JPanel();

    public myMainFrame(String title, int flag, String ID)
    {

        super(title);//调用父类构造函数，设置窗口名称
        //this.setDefaultLookAndFeelDecorated(true);
        // FlatDarkLaf.setup();
        final int[] current = new int[1];//当前在哪个界面
        current[0] =-1;
        this.setSize(1400, 850);// 设置窗口的其他参数，如窗口大小
        this.setResizable(false);//窗口大小不可改

        // 当关闭窗口时，退出整个程序 (在Swing高级篇教程中会介绍)
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 窗口居中
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - this.getWidth()) / 2, (screenSize.height - this.getHeight()) / 2);

        // 显示窗口
        this.setVisible(true);

        setContentPane(panelMain);//设置panelMain作为窗口容器
        ((JComponent) this.getContentPane()).setOpaque(false);

        panelMain.setOpaque(false);
        panelTop.setOpaque(false);
        panelBottom.setOpaque(false);
     //   panelLeft.setOpaque(false);

        panelMain.setLayout(null);
        panelTop.setLayout(null);
        panelBottom.setLayout(null);
        panelLeft.setLayout(null);

        panelTop.setBounds(0, 0, 1400, 100);
        panelBottom.setBounds(180,100,1200,650);
        panelLeft.setBounds(0,100,180,750);
        panelMain.add(panelLeft);
        panelMain.add(panelTop);
        panelMain.add(panelBottom);


        //Logo
        JLabel lblLogoLabel=new JLabel("VCampus");
        lblLogoLabel.setBounds(900,10,500,80);
        lblLogoLabel.setFont(new Font("Jokerman",Font.BOLD, 60));
        lblLogoLabel.setForeground(Color.white);
        panelTop.add(lblLogoLabel);
        JButton btnExit = new JButton("退出");
        btnExit.setFont(new Font("宋体",Font.BOLD, 16));
        btnExit.setBounds(1200,40,70,30);
        panelTop.add(btnExit);
        //加背景图片
        JLabel topBack = new JLabel();
        ImageIcon topImg = new ImageIcon("resource\\top.png");
        topBack.setIcon(topImg);
        topBack.setBounds(0,0,1400,100);
        panelTop.add(topBack);
        JLabel leftBack = new JLabel();
        leftBack.setOpaque(false);
       // ImageIcon leftImg = new ImageIcon("resource\\left.jpg");
       // leftBack.setIcon(leftImg);
       // leftBack.setBounds(0,100,180,750);
       // panelLeft.add(leftBack);

        //panelLeft按钮设置
        //设置背景
        Color color = new Color(252,199,64);
        panelLeft.setBackground(color);
        //菜单按钮设置
        JButton btnModule1 = new RoundRectButton("用户管理");
        JButton btnModule2 = new RoundRectButton("学籍管理");
        JButton btnModule3 = new RoundRectButton("教务管理");
        JButton btnModule4 = new RoundRectButton("校图书馆");
        JButton btnModule5 = new RoundRectButton("校园商店");
        JButton btnModule6 = new RoundRectButton("站内消息");
        JButton[] btn = {btnModule1,btnModule2,btnModule3,btnModule4,btnModule5,btnModule6};
        //退出按钮


        btnModule1.setFont(new Font("正楷",Font.BOLD, 24));
        btnModule1.setForeground(Color.WHITE);
        btnModule2.setFont(new Font("正楷",Font.BOLD, 24));
        btnModule2.setForeground(Color.WHITE);
        btnModule3.setFont(new Font("正楷",Font.BOLD, 24));
        btnModule3.setForeground(Color.WHITE);
        btnModule4.setFont(new Font("正楷",Font.BOLD, 24));
        btnModule4.setForeground(Color.WHITE);
        btnModule5.setFont(new Font("正楷",Font.BOLD, 24));
        btnModule5.setForeground(Color.WHITE);
        btnModule6.setFont(new Font("正楷",Font.BOLD, 24));
        btnModule6.setForeground(Color.WHITE);

        btnModule1.setBounds(20,10,150,60);
        btnModule2.setBounds(20,10+110,150,60);
        btnModule3.setBounds(20,10+110+110,150,60);
        btnModule4.setBounds(20,10+110*3,150,60);
        btnModule5.setBounds(20,10+110*4,150,60);
        btnModule6.setBounds(20,10+110*5,150,60);


//        btnModule1.setContentAreaFilled(false);
//        btnModule2.setContentAreaFilled(false);
//        btnModule3.setContentAreaFilled(false);
//        btnModule4.setContentAreaFilled(false);
//        btnModule5.setContentAreaFilled(false);
//        btnModule6.setContentAreaFilled(false);
        //去边框
        btnModule1.setBorder(null);
        btnModule2.setBorder(null);
        btnModule3.setBorder(null);
        btnModule4.setBorder(null);
        btnModule5.setBorder(null);
        btnModule6.setBorder(null);


        //添加按钮
        panelLeft.add(btnModule1);
        panelLeft.add(btnModule2);
        panelLeft.add(btnModule3);
        panelLeft.add(btnModule4);
        panelLeft.add(btnModule5);
        panelLeft.add(btnModule6);

        for(int i=0;i<6;i++){
            btn[i].setOpaque(false);
            btn[i].setBackground(color);
        }
       // 设置图标背景
        ImageIcon btnicon1 = new ImageIcon("resource//user.png");
        btnModule1.setIcon(btnicon1);
        btnModule1.setHorizontalTextPosition(SwingConstants.RIGHT);
        ImageIcon btnicon2 = new ImageIcon("resource//student.png");
        btnModule2.setIcon(btnicon2);
        btnModule2.setHorizontalTextPosition(SwingConstants.RIGHT);
        ImageIcon btnicon3 = new ImageIcon("resource//lesson.png");
        btnModule3.setIcon(btnicon3);
        btnModule3.setHorizontalTextPosition(SwingConstants.RIGHT);
        ImageIcon btnicon4 = new ImageIcon("resource//library.png");
        btnModule4.setIcon(btnicon4);
        btnModule4.setHorizontalTextPosition(SwingConstants.RIGHT);
        ImageIcon btnicon5 = new ImageIcon("resource//store.png");
        btnModule5.setIcon(btnicon5);
        btnModule5.setHorizontalTextPosition(SwingConstants.RIGHT);
        ImageIcon btnicon6 = new ImageIcon("resource//message.png");
        btnModule6.setIcon(btnicon6);
        btnModule6.setHorizontalTextPosition(SwingConstants.RIGHT);
        //创建各模块各权限对应面板
        //用户管理模块
        JTabbedPane[] userModule = {
                new JTabbedPane(),
                new TabbedPanelUser_S(ID),
                new TabbedPanelUser_T(ID),
                new TabbedPanelUser_A(ID)};
        //学籍管理模块
        JTabbedPane[] schoolModule = {
                new JTabbedPane(),
                new TabbedPanelSchool_S(ID),
                new TabbedPanelSchool_T(ID),
                new TabbedPanelSchool_A(ID)};
        //教务系统
        JTabbedPane[] courseModule = {
                new JTabbedPane(),
                new TabbedPanelCourse_S(),
                new TabbedPanelCourse_T(ID),
                new TabbedPanelCourse_A()};
        //图书馆
        JTabbedPane[] LibraryModule = {
                new JTabbedPane(),
                new TabbedPanelLibrary_S(ID),
                new TabbedPanelLibrary_T(ID),
                new TabbedPanelLibrary_A()};
        //商店
        JTabbedPane[] storeModule = {
                new JTabbedPane(),
                new TabbedPanelStore_S(),
                new TabbedPanelStore_T(),
                new TabbedPanelStore_A()};
        //站内消息
        JTabbedPane[] messageModule = {
                new JTabbedPane(),
                new TabbedPanelMessage_S(),
                new TabbedPanelMessage_T(),
                new TabbedPanelMessage_A(ID)};
       JTabbedPane [][] module ={userModule,schoolModule,courseModule,LibraryModule,storeModule,messageModule};
        //菜单按钮响应函数
//        btnModule1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                current[0] = 1;
//
//                for(int i=0;i<6;i++){
//                    btn[i].setBackground(color);
//                    btn[i].setForeground(Color.WHITE);
//                }
//                btnModule1.setBackground(Color.WHITE);
//                btnModule1.setForeground(Color.BLACK);
//                setPanelSwitch(userModule[flag]);
//                System.out.println("用户管理系统");
//            }
//        });

        for(int i=0;i<6;i++)
        {
            int finalI = i;
            int finalI1 = i;
            btn[i].addActionListener(new ActionListener() {
                int temp = finalI;
                @Override
                public void actionPerformed(ActionEvent e) {
                    current[0] = finalI;

                    for(int j=0;j<6;j++){
                        btn[j].setBackground(color);
                        btn[j].setForeground(Color.WHITE);
                    }
                    btn[finalI1].setBackground(Color.WHITE);
                    btn[finalI1].setForeground(Color.BLACK);
                    setPanelSwitch(module[finalI][flag]);
                    System.out.println("用户管理系统");
                }
            });
            int finalI2 = i;
            btn[i].addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    btn[finalI2].setBackground(Color.WHITE);
                    btn[finalI2].setForeground(Color.BLACK);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if(current[0]!=finalI2){
                        btn[finalI2].setBackground(color);
                        btn[finalI2].setForeground(Color.WHITE);
                    }
                }
            });
        }

//        btnModule2.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                current[0] = 2;
//                for(int i=0;i<6;i++){
//                    btn[i].setBackground(color);
//                    btn[i].setForeground(Color.WHITE);
//                }
//                btnModule2.setBackground(Color.WHITE);
//                btnModule2.setForeground(Color.BLACK);
//                setPanelSwitch(schoolModule[flag]);
//                System.out.println("学籍管理系统");
//            }
//        });

//        btnModule3.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                current[0] = 3;
//                for(int i=0;i<6;i++){
//                    btn[i].setBackground(color);
//                    btn[i].setForeground(Color.WHITE);
//                }
//                btnModule3.setBackground(Color.WHITE);
//                btnModule3.setForeground(Color.BLACK);
//                setPanelSwitch(courseModule[flag]);
//                System.out.println("教务管理系统");
//            }
//        });

//        btnModule4.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                current[0] = 4;
//                for(int i=0;i<6;i++){
//                btn[i].setBackground(color);
//                    btn[i].setForeground(Color.WHITE);
//            }
//                btnModule4.setBackground(Color.WHITE);
//                btnModule4.setForeground(Color.BLACK);
//                setPanelSwitch(LibraryModule[flag]);
//                System.out.println("图书馆管理系统");
//            }
//        });

//        btnModule5.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                current[0] = 5;
//                for(int i=0;i<6;i++){
//                    btn[i].setBackground(color);
//                    btn[i].setForeground(Color.WHITE);
//                }
//                btnModule5.setBackground(Color.WHITE);
//                btnModule5.setForeground(Color.BLACK);
//                setPanelSwitch(storeModule[flag]);
//                System.out.println("校园商城管理系统");
//            }
//        });
//
//        btnModule6.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                current[0] =6;
//                for(int i=0;i<6;i++){
//                    btn[i].setBackground(color);
//                    btn[i].setForeground(Color.WHITE);
//                }
//                btnModule6.setBackground(Color.WHITE);
//                btnModule6.setForeground(Color.BLACK);
//                setPanelSwitch(messageModule[flag]);
//                System.out.println("站内消息管理系统");
//                //current =6;
//            }
//        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    //实现下方面板切换
    public void setPanelSwitch(JTabbedPane jtp)
    {
        panelBottom.removeAll();
        panelBottom.add(jtp);
        panelBottom.updateUI();
        panelBottom.repaint();
    }

}

