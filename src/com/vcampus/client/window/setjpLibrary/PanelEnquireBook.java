/** ===================================================
 * Title: PanelEnquireBook.java
 * Created: [2022-8-22  11:43:57] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 查询图书信息-通用
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-22,创建此文件
 *2. 2022-8-22，完善面板 修改人：韩宇
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

public class PanelEnquireBook extends JPanel{

    JButton btnInquire = new JButton("查询");
    String[] listData = new String[]{"选择搜索条件","书籍号", "书名", "作者", "类型"};
    // 创建一个下拉列表框
    JComboBox<String> comboBox = new JComboBox<String>(listData);
    JTextField txtEnquire = new JTextField();
    //Book book = new Book();

    public PanelEnquireBook()
    {
        this.setLayout(null);
        int x=470,y=50;//起始坐标
        int txtWidth=110, txtHeight=42;
        // 设置默认选中的条目
        comboBox.setBounds(x-110,y,220,40);
        comboBox.setFont(new Font("楷体", Font.BOLD, 24));
        comboBox.setOpaque(true);

        txtEnquire.setBounds(x+130,y,txtWidth*3,txtHeight);
        txtEnquire.setFont(new Font("楷体", Font.BOLD, 20));

        btnInquire.setFont(new Font("宋体",Font.BOLD, 20));
        btnInquire.setBounds(x+480,y,80,40);

        // 添加到内容面板
        this.add(comboBox);
        this.add(txtEnquire);
        this.add(btnInquire);

        btnInquire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = txtEnquire.getText();
                System.out.println(str);//传送，接收结果bool型
                int temp = comboBox.getSelectedIndex();
                switch(temp)//发出消息
                {
                    case 1:
                        System.out.println("书籍号"+str);
                        break;
                    case 2:
                        System.out.println("书名"+str);//book。set
                        break;
                    case 3:
                        System.out.println("作者"+str);//book。set
                        break;
                    case 4:
                        System.out.println("类型"+str);//book。set
                        break;
                    case 0:
                        warningFrame("请选择搜索条件类型！");
                        break;
                    default:
                        break;
                }
                //传消息出去
                setPanel();
            }
        });

    }

    public void warningFrame(String tips)
    {
        JOptionPane.showMessageDialog(this, tips, "警告", JOptionPane.ERROR_MESSAGE);
    }

    Book book = new Book();//临时
    public void setPanel()
    {
        updateUI();
        repaint();
        //先接收消息//根据消息判断，如果大小为1，放详情panel//不为1，放表格//zxz//如果是空的，弹警告窗口
        int num=1;
        if(num==1)
        {
            JPanel panelInform = new PanelBookInform(book,false);//传入书本的对象作为参数，参数传接收的消息
            panelInform.setBounds(0,150,1400,350);
            //panelInform.setBorder(BorderFactory.createTitledBorder("分组框")); //设置面板边框，实现分组框的效果，此句代码为关键代码
            //panelInform.setBorder(BorderFactory.createLineBorder(Color.red));//设置面板边框颜色
            this.add(panelInform);
        }
        else
        {
            if(num==0)
            {
                warningFrame("未查询到相关结果！");
            }
            else
            {
                //构建表格
            }
        }
    }
}
