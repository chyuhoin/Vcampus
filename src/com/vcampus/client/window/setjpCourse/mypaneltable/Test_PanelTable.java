package com.vcampus.client.window.setjpCourse.mypaneltable;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class Test_PanelTable {
    public static void main(String[] args) throws Exception {
        //皮肤包
        FlatLightLaf.setup();

        //创建登录自己的窗口对象
        JFrame frame = new JFrame("按钮表格");
        // 当关闭窗口时，退出整个程序 (在Swing高级篇教程中会介绍)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口的其他参数，如窗口大小
        frame.setSize(600, 400);
        frame.setResizable(false);//窗口大小不可改

        frame.setLayout(null);

        //内容面板
        JPanel jp=new JPanel();
        JLabel lbl=new JLabel("内容面板");
        jp.add(lbl);

        //JShrinkablePanel jSPanel=new JShrinkablePanel("标题",jp);

       // frame.add(jSPanel);
        //jSPanel.setBounds(100,100,200,100);

        // 窗口居中
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);
        // 显示窗口
        frame.setVisible(true);
    }
}
