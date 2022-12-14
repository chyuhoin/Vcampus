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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vcampus.client.window.setjpLibrary.mytablepanel.MyTablePanel;
import com.vcampus.client.window.showMessageFrame;
import com.vcampus.pojo.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Student;

/**
 * 书籍查询界面
 * @author 韩宇
 * @date 2022/08/22
 */
public class PanelEnquireBook extends JPanel{
    JButton btnInquire = new JButton("查询");
    String[] listData = new String[]{"选择搜索条件","书籍号", "书名", "作者", "类型"};
    JComboBox<String> comboBox = new JComboBox<String>(listData); // 创建一个下拉列表框
    JTextField txtEnquire = new JTextField();
    JLabel backGround = new JLabel();
    PanelBookInform panelInform = new PanelBookInform(new Book("","","","",0,""),true);
    MessagePasser passer = ClientMessagePasser.getInstance();

    public PanelEnquireBook(String status)
    {
        this.setLayout(null);
        int x=420,y=50;//起始坐标
        int txtWidth=110, txtHeight=42;

        backGround.setBounds(0,0,1200,650);
        backGround.setOpaque(false);
        comboBox.setBounds(x-180,y,220,40);
        comboBox.setFont(new Font("楷体", Font.BOLD, 24));
        comboBox.setOpaque(true);
        txtEnquire.setBounds(x+60,y,txtWidth*3,txtHeight);
        txtEnquire.setFont(new Font("楷体", Font.BOLD, 20));
        btnInquire.setFont(new Font("宋体",Font.BOLD, 20));
        btnInquire.setBounds(x+410,y,140,40);
        // 添加到内容面板
        this.add(comboBox);this.add(txtEnquire);this.add(btnInquire);
        //设置背景
        ImageIcon back = new ImageIcon("resource//library.png");
        backGround.setIcon(back);
        //设置搜索
        ImageIcon btnIcon = new ImageIcon("resource//search.png");
        btnInquire.setIcon(btnIcon);
        btnInquire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = txtEnquire.getText();
                Book book = new Book();
                int temp = comboBox.getSelectedIndex();
                switch(temp)//发出消息
                {
                    case 1:
                        book.setBookID(str);
                        break;
                    case 2:
                        book.setBookName(str);
                        break;
                    case 3:
                        book.setAuthor(str);
                        break;
                    case 4:
                        book.setType(str);
                        break;
                    case 0:
                        warningFrame("请选择搜索条件类型！");
                        break;
                    default:
                        break;
                }
                if(temp!=0)
                {   Gson gson = new Gson();String s = gson.toJson(book);
                    passer.send(new Message(status, s, "library", "get"));
                    setPanel(); }
            }
        });
    }
    /**
     *弹出警告窗口
     * @param tips 提示内容
     */
    public void warningFrame(String tips)
    {
        new showMessageFrame(tips,900,240,460, 80,1);

//        JOptionPane.showMessageDialog(this, tips, "警告", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * 设置书籍详情页
     */
    public void setPanel()
    {
        updateUI();repaint();
        Message msg = passer.receive();
        Map<String, java.util.List<Book>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Book>>>(){}.getType());
        List<Book> res = map.get("res");
        txtEnquire.setText("");
        if(res.size()==1)
        {   panelInform = new PanelBookInform(res.get(0),false);//传入书本的对象作为参数，参数传接收的消息
            panelInform.setBounds(115,150,1400,350);
            this.add(panelInform); }
        else
        {   if(res.size()==0) { warningFrame("未查询到相关结果！"); }
            else
            {   remove(panelInform);
                res.forEach(System.out::println);
                try{
                    Object[] columnNames = new Object[]{"书籍号","书名","作者","类型","剩余册数"};
                    Object[][] rowData = new Object[res.size()][5];
                    for(int i=0;i<res.size();i++){
                        rowData[i][0]=res.get(i).getBookID();
                        rowData[i][1]=res.get(i).getBookName();
                        rowData[i][2]=res.get(i).getAuthor();
                        rowData[i][3]=res.get(i).getType();
                        rowData[i][4]=res.get(i).getLeftSize();
                    }
                    JPanel panelInform = new MyTablePanel(rowData,columnNames);
                    panelInform.setBounds(0,120,1200,500);
                    this.add(panelInform);
                } catch (Exception e) { throw new RuntimeException(e); }
            }
        }
    }
}
