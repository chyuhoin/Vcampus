/** ===================================================
 * Title: PanelBorrowAndReturn_A.java
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vcampus.client.window.showMessageFrame;
import com.vcampus.pojo.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
/**
 * 管理员 图书借阅归还界面
 * @author 韩宇
 * @date 2022/08/22
 */
public class PanelBorrowAndReturn_A extends JPanel {
    JTextField txtBookIdEnquire = new JTextField();
    JLabel lblHint = new JLabel("输入书籍号 ：");
    JButton btnInquire = new JButton("查询");
    JButton btnBorrow = new JButton("借阅");
    JButton btnReturn = new JButton("归还");
    int btnWidth = 90, btnHeight = 30;
    PanelBookInform panelInform = new PanelBookInform(new Book("","","","",0,""),false);
    MessagePasser passer = ClientMessagePasser.getInstance();
    public PanelBorrowAndReturn_A()
    {
        this.setLayout(null);
        lblHint.setFont(new Font("宋体", Font.BOLD, 24));
        lblHint.setBounds(280+30,43,200,40);
        txtBookIdEnquire.setBounds(200+250+30,50,300,30);
        txtBookIdEnquire.setFont(new Font("楷体",Font.BOLD,20));
        btnInquire.setFont(new Font("宋体",Font.BOLD, 20));
        btnInquire.setBounds(520+250+30,49,btnWidth,btnHeight);
        btnBorrow.setFont(new Font("宋体",Font.BOLD, 20));
        btnBorrow.setBounds(420,480,btnWidth,btnHeight);
        btnReturn.setFont(new Font("宋体",Font.BOLD, 20));
        btnReturn.setBounds(660,480,btnWidth,btnHeight);
        this.add(lblHint);this.add(txtBookIdEnquire);this.add(btnInquire);

        btnInquire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(panelInform);
                String str = txtBookIdEnquire.getText();
                Book book = new Book();book.setBookID(str);
                Gson gson = new Gson();String s = gson.toJson(book);
                passer.send(new Message("admin", s, "library", "get"));
                setPanel();
            }
        });
        btnBorrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { setFrame("借阅窗口");}
        });
        btnReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { setFrame("归还窗口");}});
    }
    /**
     * 显示要借阅的图书详情
     */
    public void setPanel()//参数是返回的结果，list一个或多个书
    {
        Message msg = passer.receive();
        Map<String, java.util.List<Book>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Book>>>(){}.getType());
        List<Book> res = map.get("res");
        if(res.size()!=0)
        {
            panelInform = new PanelBookInform(res.get(0),false);//传入书本的对象作为参数
            panelInform.setBounds(110,110,1400,350);
            this.add(btnBorrow);this.add(btnReturn);this.add(panelInform);
        } else { informFrame("未查询到相关书籍！",true); }
        updateUI();repaint();
    }
    /**
     * 借阅或归还图书时弹出窗口，输入借阅人的一卡通号
     * @param title 标题
     */
    public void setFrame(String title)
    {
        JFrame frame = new JFrame(title);
        frame.setSize(500, 200);// 设置窗口的其他参数，如窗口大小
        frame.setResizable(false);//窗口大小不可改
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// 窗口居中
        frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);
        frame.setVisible(true);
        frame.setLayout(null);// 显示窗口

        JLabel lblID = new JLabel("输入借阅人一卡通号 ：");JTextField txtID = new JTextField();
        lblID.setFont(new Font("宋体", Font.BOLD, 20));lblID.setBounds(20,35,300,40);
        txtID.setFont(new Font("楷体",Font.BOLD,20));txtID.setBounds(245,40,200,30);
        JButton btnOk = new JButton("确认");JButton btnCancel = new JButton("取消");
        btnOk.setFont(new Font("宋体",Font.BOLD, 18));btnOk.setBounds(135,110,btnWidth,btnHeight);
        btnCancel.setFont(new Font("宋体",Font.BOLD, 18));btnCancel.setBounds(255,110,btnWidth,btnHeight);

        frame.add(lblID);frame.add(txtID);frame.add(btnOk);frame.add(btnCancel);
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               HashMap<String,String> hashMap = new HashMap<>();
               hashMap.put("studentId",txtID.getText());
               hashMap.put("bookId",txtBookIdEnquire.getText());
               Gson gson = new Gson();String s = gson.toJson(hashMap);
               if(title=="借阅窗口") { passer.send(new Message("admin", s, "library", "borrow")); }
               else{ passer.send(new Message("admin", s, "library", "return")); }

               try { Thread.sleep(100); } catch (InterruptedException interruptedException) { interruptedException.printStackTrace(); }

               Message msg = (passer.receive());
               Map<String,java.util.List<Book>> map = gson.fromJson(msg.getData(), new TypeToken<HashMap<String,java.util.List<Book>>>(){}.getType());
               List<Book> res = map.get("res");
               frame.dispose();
               if(res.size()!=0)//map.get("res").equals("OK")
               {   informFrame("操作成功！",false);
                   if(title=="借阅窗口") { changeLeftSize(false); }
                   else { changeLeftSize(true); } }
               else { informFrame("操作失败!",true); } }});
        btnCancel.addActionListener(new ActionListener() {//取消 不操作
            @Override
            public void actionPerformed(ActionEvent e) { frame.dispose(); }});
    }
    /**
     * 提示窗口
     * @param title 标题
     * @param flag  true-警告 false-提示
     */
    public void informFrame(String title,Boolean flag)
    {   if(flag) {
        new showMessageFrame(title,900,240,460, 80,1);

//        JOptionPane.showMessageDialog(this, title, "警告", JOptionPane.ERROR_MESSAGE);
    }
    else {
        new showMessageFrame(title,900,240,460, 80,2);

//        JOptionPane.showMessageDialog(this, title, "提示", JOptionPane.INFORMATION_MESSAGE);
    } }
    /**
     * 借阅、归还图书后更新册数
     * @param flag true-归还 false-借阅
     *///借阅 归还成功后修改册数
    public void changeLeftSize(Boolean flag)
    {
        Integer tmp=Integer.valueOf(panelInform.txtLeftSize.getText());
        if(flag) { tmp+=1; }//归还
        else { tmp-=1; }//借阅
        panelInform.txtLeftSize.setText(tmp.toString());
    }
}
