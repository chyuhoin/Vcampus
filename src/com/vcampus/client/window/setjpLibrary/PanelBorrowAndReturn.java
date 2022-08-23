/** ===================================================
 * Title: PanelBorrowAndReturn.java
 * Created: [2022-8-22  15:00:57] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 图书借还-管理员
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-22,创建此文件
 *2. 2022-8-22，完善借还界面 修改人：韩宇
 *3.
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window.setjpLibrary;

import com.vcampus.client.window.setjpLibrary.PanelBookInform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.vcampus.pojo.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;

public class PanelBorrowAndReturn extends JPanel {
    JTextField txtBookIdEnquire = new JTextField();
    JLabel lblHint = new JLabel("输入书籍号 ：");
    JButton btnInquire = new JButton("查询");
    JButton btnBorrow = new JButton("借阅");
    JButton btnReturn = new JButton("归还");
    int btnWidth = 90, btnHeight = 30;

    PanelBookInform panelInform = new PanelBookInform(new Book("","","","",0,""),false);
    //传入书本的对象作为参数

    public PanelBorrowAndReturn()
    {
        this.setLayout(null);

        lblHint.setFont(new Font("宋体", Font.BOLD, 24));
        lblHint.setBounds(280,43,200,40);

        txtBookIdEnquire.setBounds(200+250,50,300,30);

        btnInquire.setFont(new Font("宋体",Font.BOLD, 20));
        btnInquire.setBounds(520+250,49,btnWidth,btnHeight);

        btnBorrow.setFont(new Font("宋体",Font.BOLD, 20));
        btnBorrow.setBounds(420,480,btnWidth,btnHeight);

        btnReturn.setFont(new Font("宋体",Font.BOLD, 20));
        btnReturn.setBounds(660,480,btnWidth,btnHeight);

        this.add(lblHint);
        this.add(txtBookIdEnquire);
        this.add(btnInquire);

        btnInquire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = txtBookIdEnquire.getText();
                System.out.println(str);
                //传送消息

                setPanel();
            }
        });

        btnBorrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFrame("借阅窗口");
            }
        });

        btnReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFrame("归还窗口");
            }
        });

    }

    public void setPanel()//参数是返回的结果，list一个或多个书
    {
        updateUI();
        repaint();
        //先接收消息
        //根据消息判断，如果大小为1，放详情panel 借退按钮，否则弹窗警告
        Book book = new Book();//临时用来盛放构建informpanel的结果
        int num=1;
        if(num==1)
        {
            //JPanel panelInform = new PanelBookInform(book,false);//传入书本的对象作为参数
            panelInform = new PanelBookInform(book,false);//传入书本的对象作为参数
            panelInform.setBounds(0,110,1400,350);
            //panelInform.setBorder(BorderFactory.createTitledBorder("分组框")); //设置面板边框，实现分组框的效果，此句代码为关键代码
            //panelInform.setBorder(BorderFactory.createLineBorder(Color.red));//设置面板边框颜色
            this.add(btnBorrow);
            this.add(btnReturn);
            this.add(panelInform);
        }
        else
        { informFrame("未查询到相关书籍！",true); }
    }

    public void setFrame(String title)
    {
        JFrame frame = new JFrame(title);
        frame.setSize(500, 200);// 设置窗口的其他参数，如窗口大小
        frame.setResizable(false);//窗口大小不可改
        // 窗口居中
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);
        // 显示窗口
        frame.setVisible(true);
        frame.setLayout(null);

        JLabel lblID = new JLabel("输入借阅人一卡通号 ：");
        JTextField txtID = new JTextField();
        lblID.setFont(new Font("宋体", Font.BOLD, 20));
        lblID.setBounds(20,35,300,40);
        txtID.setBounds(245,40,200,30);

        JButton btnOk = new JButton("确认");
        JButton btnCancel = new JButton("取消");
        btnOk.setFont(new Font("宋体",Font.BOLD, 18));
        btnOk.setBounds(135,110,btnWidth,btnHeight);
        btnCancel.setFont(new Font("宋体",Font.BOLD, 18));
        btnCancel.setBounds(255,110,btnWidth,btnHeight);

        frame.add(lblID);frame.add(txtID);frame.add(btnOk);frame.add(btnCancel);

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String str = txtID.getText();
               System.out.println(str);
               //传map
                //接收消息//借或还成功
               if(true)
               {
                   frame.dispose();
                   informFrame("操作成功！",false);
                   if(title=="借阅窗口")
                   { changeLeftSize(false); }
                   else
                   { changeLeftSize(true); }
                   //setPanel();
               }
               else
               {
                   frame.dispose();
                   informFrame("操作失败!",true);
               }
            }
        });
        //取消 不操作
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { frame.dispose(); }
        });
    }

    //消息提示窗口
    public void informFrame(String title,Boolean flag)
    {
        if(flag)
        { JOptionPane.showMessageDialog(this, title, "警告", JOptionPane.ERROR_MESSAGE); }
        else
        { JOptionPane.showMessageDialog(this, title, "提示", JOptionPane.INFORMATION_MESSAGE); }
    }

    //借阅 归还成功后修改册数
    public void changeLeftSize(Boolean flag)
    {
        Integer tmp=Integer.valueOf(panelInform.txtLeftSize.getText());
        if(flag)//归还
        {
            tmp+=1;
            panelInform.txtLeftSize.setText(tmp.toString());
        }
        else//借阅
        {
            tmp-=1;
            panelInform.txtLeftSize.setText(tmp.toString());
        }
    }
}
