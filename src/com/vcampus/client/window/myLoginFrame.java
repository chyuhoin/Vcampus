/** ===================================================
 * Title: myLoginFrame.java
 * Created: [2022-8-14 19:56:42] by  韩宇 张星喆
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 登录界面设置
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-14,创建此文件
 *2. 对这个文件的修改历史进行详细描述，一般包括时间，修改者，
 *    修改的内容描述，修改的原因
 */


package com.vcampus.client.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.User;

public class myLoginFrame extends JFrame  {

    JTextField txtUserName = new JTextField(20);
    JPasswordField txtPassWord = new JPasswordField(20);
    JButton btnLogin = new JButton("登录");
    //JFrame loginFrame;
    int flag=0;
    String userName="";

    public myLoginFrame(String title)
    {
        //loginFrame = new JFrame(title);
        //调用父类构造函数，设置窗口名称
        //super(title);
        //添加面板
        JPanel panel = new JPanel();
        this.add(panel);
        //设置布局
        panel.setLayout(null);

        // 当关闭窗口时，退出整个程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置窗口的其他参数，如窗口大小
        this.setSize(350, 270);
        this.setResizable(false);//窗口大小不可改

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - this.getWidth()) / 2, (screenSize.height - this.getHeight()) / 2);

        // 显示窗口
        this.setVisible(true);

        //设置标题
        JLabel lblTitleLabel=new JLabel("用户登录");
        lblTitleLabel.setBounds(120,20,100,45);
        lblTitleLabel.setFont(new Font("宋体",Font.BOLD, 20));
        panel.add(lblTitleLabel);

        // 创建用户名和密码输入提示 JLabel
        JLabel lblUserName = new JLabel("用户名:");
        lblUserName.setBounds(10,70,80,25);
        panel.add(lblUserName);
        JLabel lblPassWord = new JLabel("密码:");
        lblPassWord.setBounds(10,100,80,25);
        panel.add(lblPassWord);

        //创建文本框用于用户名和密码输入
        //JTextField userText = new JTextField(20);
        txtUserName.setBounds(100,70,165,25);
        panel.add(txtUserName);
        //JPasswordField passWordText = new JPasswordField(20);
        txtPassWord.setBounds(100,100,165,25);
        panel.add(txtPassWord);

        //创建单选按钮并设置其位置
        JRadioButton radioBtn01 = new JRadioButton("学生");
        JRadioButton radioBtn02 = new JRadioButton("教师");
        JRadioButton radioBtn03 = new JRadioButton("管理员");
        radioBtn01.setBounds(40,135,80,25);
        radioBtn02.setBounds(120,135,80,25);
        radioBtn03.setBounds(200,135,80,25);
        panel.add(radioBtn01);
        panel.add(radioBtn02);
        panel.add(radioBtn03);

        // 创建按钮组，把两个单选按钮添加到该组
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(radioBtn01);
        btnGroup.add(radioBtn02);
        btnGroup.add(radioBtn03);

        // 创建登录按钮
        //JButton loginButton = new JButton("登录");
        btnLogin.setBounds(40, 170, 260, 25);
        panel.add(btnLogin);
        btnLogin.setEnabled(false);

        //监听三个单选按钮选中事件，选中其中一个登录按钮才可使用
        responseSelected(radioBtn01,1);
        responseSelected(radioBtn02,2);
        responseSelected(radioBtn03,3);

       // JFrame tempjf=this;
        //设置登录按钮的监听事件
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(readTextContent())
                {
                    switch(flag)//学生——1，教师——2，管理员——3，用以区分权限
                    {
                        case(1):
                            System.out.println("学生申请登录");
                            break;
                        case(2):
                            System.out.println("教师申请登录");
                            break;
                        case(3):
                            System.out.println("管理员申请登录");
                            break;
                    }
                    //loginFrame.setVisible(false);
                    dispose();
                    myMainFrame tempMF=new myMainFrame("VCampus虚拟校园系统",flag,userName);

                }
                else//未输入用户名或密码，无法登陆，弹出窗口提示
                {
                    JOptionPane.showMessageDialog(panel, "用户名或密码错误！", "警告", JOptionPane.ERROR_MESSAGE);
                    clearText();
                }
            }
        });
    }

    public Boolean readTextContent()
    {
        //读取输入的用户名和密码
        /*String userName = txtUserName.getText();
        String passWord = new String(txtPassWord.getPassword());

        if(userName.length()!=0 && passWord.length()!=0)//如果用户名和密码已输入
        {
            System.out.println(userName+'\n'+passWord);//输出登录名和密码
            return true;
        }
        else
            return false;

         */

        //读取输入的用户名和密码
        String userName = txtUserName.getText();
        String passWord = new String(txtPassWord.getPassword());

        MessagePasser passer = ClientMessagePasser.getInstance();
        User user = new User(userName, passWord, flag);
        Gson gson = new Gson();
        passer.send(new Message("no", gson.toJson(user), "login", "get"));
        Message msg = passer.receive();
        Map<String,Object> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String,Object>>(){}.getType());
        return map.get("res").equals("OK");

//        if(userName.length()!=0 && passWord.length()!=0)//如果用户名和密码已输入
//        {
//            System.out.println(userName+'\n'+passWord);//输出登录名和密码
//            return true;
//        }
//        else
//            return false;
    }

    //选中学生/教师/管理员后的响应
    public void responseSelected(JRadioButton button, int num)
    {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(button.isSelected())//如果单选按钮选中，登录按钮才可使用，否则登录按钮无效
                {
                    btnLogin.setEnabled(true);//
                    flag=num;//学生——1，教师——2，管理员——3，用以区分权限
                }
            }
        });
    }

    public void clearText()
    {
        txtUserName.setText("");
        txtPassWord.setText("");
    }
}
