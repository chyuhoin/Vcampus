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
import com.vcampus.client.window.setjpUSER.TabbedPanelUser_A;
import com.vcampus.client.window.setjpUSER.TabbedPanelUser_S;
import com.vcampus.client.window.setjpUSER.TabbedPanelUser_T;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class myMainFrame extends JFrame {
    private JPanel panelMain = new JPanel();
    public JPanel panelTop = new JPanel();
    public  JPanel panelBottom = new JPanel();

    public myMainFrame(String title, int flag, String ID)
    {
        super(title);//调用父类构造函数，设置窗口名称
        // FlatDarkLaf.setup();
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

        panelMain.setLayout(null);
        panelTop.setLayout(null);
        panelBottom.setLayout(null);

        panelTop.setBounds(0, 0, 1400, 200);
        panelBottom.setBounds(0,200,1400,650);

        panelMain.add(panelTop);
        panelMain.add(panelBottom);

        //panelTop内容设置
        //Logo
        JLabel lblLogoLabel=new JLabel("VCampus");
        lblLogoLabel.setBounds(20,10,500,80);
        lblLogoLabel.setFont(new Font("Jokerman",Font.BOLD, 60));
        panelTop.add(lblLogoLabel);

        //菜单按钮设置
        JButton btnModule1 = new JButton("用户管理");
        JButton btnModule2 = new JButton("学籍管理");
        JButton btnModule3 = new JButton("教务管理");
        JButton btnModule4 = new JButton("图书馆管理");
        JButton btnModule5 = new JButton("校园商店管理");
        JButton btnModule6 = new JButton("站内消息管理");

        //退出按钮
        JButton btnExit = new JButton("退出");
        btnExit.setFont(new Font("宋体",Font.BOLD, 16));
        btnExit.setBounds(1200,40,70,30);

        btnModule1.setFont(new Font("宋体",Font.BOLD, 24));
        btnModule2.setFont(new Font("宋体",Font.BOLD, 24));
        btnModule3.setFont(new Font("宋体",Font.BOLD, 24));
        btnModule4.setFont(new Font("宋体",Font.BOLD, 24));
        btnModule5.setFont(new Font("宋体",Font.BOLD, 24));
        btnModule6.setFont(new Font("宋体",Font.BOLD, 24));

        btnModule1.setBounds(40,110,200,60);
        btnModule2.setBounds(40+200+20,110,200,60);
        btnModule3.setBounds(40+200+200+40,110,200,60);
        btnModule4.setBounds(40+200+200+200+60,110,200,60);
        btnModule5.setBounds(40+200+200+200+200+80,110,200,60);
        btnModule6.setBounds(40+200+200+200+200+200+100,110,200,60);

        btnModule1.setContentAreaFilled(false);
        btnModule2.setContentAreaFilled(false);
        btnModule3.setContentAreaFilled(false);
        btnModule4.setContentAreaFilled(false);
        btnModule5.setContentAreaFilled(false);
        btnModule6.setContentAreaFilled(false);

        panelTop.add(btnModule1);
        panelTop.add(btnModule2);
        panelTop.add(btnModule3);
        panelTop.add(btnModule4);
        panelTop.add(btnModule5);
        panelTop.add(btnModule6);

        panelTop.add(btnExit);

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
                new TabbedPanelCourse_T(),
                new TabbedPanelCourse_A()};
        //图书馆
        JTabbedPane[] LibraryModule = {
                new JTabbedPane(),
                new TabbedPanelLibrary_S(),
                new TabbedPanelLibrary_T(),
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
                new TabbedPanelMessage_A()};

        //菜单按钮响应函数
        btnModule1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPanelSwitch(userModule[flag]);
                System.out.println("用户管理系统");
            }
        });

        btnModule2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPanelSwitch(schoolModule[flag]);
                System.out.println("学籍管理系统");
            }
        });

        btnModule3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPanelSwitch(courseModule[flag]);
                System.out.println("教务管理系统");
            }
        });

        btnModule4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPanelSwitch(LibraryModule[flag]);
                System.out.println("图书馆管理系统");
            }
        });

        btnModule5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPanelSwitch(storeModule[flag]);
                System.out.println("校园商城管理系统");
            }
        });

        btnModule6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPanelSwitch(messageModule[flag]);
                System.out.println("站内消息管理系统");
            }
        });

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

