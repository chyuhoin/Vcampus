/** ===================================================
 * Title: TabbedPanelLibrary_A.java
 * Created: [2022-8-17 17:07:20] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 图书馆管理——管理员界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-17,创建此文件
 *2.
 *3.
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window.setjpLibrary;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.client.window.setjpLibrary.mytablepanel.*;
import com.vcampus.client.window.setjpUser.SetJPUser1;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 管理员身份图书馆管理界面
 * @author 韩宇 张星喆
 * @date 2022/08/17
 */
public class TabbedPanelLibrary_A extends JTabbedPane {

    MessagePasser passer = ClientMessagePasser.getInstance();
    JLabel background = new JLabel();
    Object[] columnNames = new Object[]{"书籍号", "书名", "作者", "类型", "剩余册数"};

    public TabbedPanelLibrary_A(int flag) {
        if(flag==3) {
            this.setBackground(new Color(0x0000001, true));
            this.setOpaque(false);

            ImageIcon icon = new ImageIcon("Vcampus\\resource\\library.jpg"); // 创建背景图片对象
            background.setIcon(icon);
            background.setBounds(0, 0, 1200, 700);
            JTabbedPane JTP = this;
            //JTabbedPane jtbp=new JTabbedPane();	//创建选项卡
            this.setTabPlacement(1);
            this.setBounds(0, 0, 1200, 700);//注意！！！！！！！！！！！！！！！！！！！！！！！

            JPanel jp11 = new JPanel();
            JPanel jp12 = new JPanel();
            JPanel jp13 = new JPanel();
            JPanel jp14 = new JPanel();
            JPanel jp15 = new JPanel();
            //选项卡1的内容
            jp11.setLayout(new CardLayout(10, 10));
            jp11.add(new MyTablePanel(getAllOfBook(), columnNames));

            //选项卡2的内容
            JPanel enquireBook = new PanelEnquireBook("admin");


            //jp.add(enquireBooK);
            //jp12.add(enquireBooK);
            // jp12.setOpaque(true);


            //选项卡3的内容
            JPanel manageBook = new PanelBookManage();

            //选项卡4的内容
            JPanel borrowReturnBook = new PanelBorrowAndReturn();

            //选项卡5的内容
            jp15.setLayout(new CardLayout(10, 10));
            jp15.setBackground(Color.ORANGE);
            jp15.add(new PanelEnquireBandR());

            //选项卡刷新
            this.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getClickCount() == 1) {
                        jp11.removeAll();
                        jp11.add(new MyTablePanel(getAllOfBook(), columnNames));
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
            enquireBook.add(background);
            this.addTab("书籍信息总览", new ImageIcon("resource//tab_library.png"), jp11, "书籍信息总览");//
            this.addTab("查询书籍信息", new ImageIcon("resource//search.png"), enquireBook, "查询书籍信息");//书籍号 书名 一个或多个
            this.addTab("书籍信息管理", new ImageIcon("resource//tab_manager.png"), manageBook, "书籍信息管理");//增加 删除 修改
            this.addTab("书籍借阅/退还", new ImageIcon("resource//tab_bookreturn.png"), borrowReturnBook, "书籍借阅/退还");
            this.addTab("查询借阅情况", new ImageIcon("resource//search.png"), jp15, "查询借阅情况");//某一个人的借阅
            this.setFont(new Font("宋体", Font.BOLD, 24));

        }

        //jp.add(jtbp);
    }

    public Object[][] getAllOfBook(){
        //查询数据库
        Book book = new Book();
        Gson gson = new Gson();
        String s = gson.toJson(book);
        passer.send(new Message("admin", s, "library", "get"));

        Message msg = passer.receive();
        Map<String, java.util.List<Book>> map =
                new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Book>>>() {}.getType());
        List<Book> res = map.get("res");
        Object[][] rowData = new Object[res.size()][5];
        for (int i = 0; i < res.size(); i++) {
            rowData[i][0] = res.get(i).getBookID();
            rowData[i][1] = res.get(i).getBookName();
            rowData[i][2] = res.get(i).getAuthor();
            rowData[i][3] = res.get(i).getType();
            rowData[i][4] = res.get(i).getLeftSize();
        }

        return rowData;
    }

}
