/** ===================================================
 * Title: TabbedPanelLibrary_T.java
 * Created: [2022-8-17 17:09:05] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 图书馆管理——教师界面
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
import com.vcampus.client.window.setjpLibrary.mytablepanel.MyTablePanel;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Book;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 教师身份图书馆管理界面
 * @author 韩宇 张星喆
 * @date 2022/08/17
 */
public class TabbedPanelLibrary_T extends JTabbedPane {
    MessagePasser passer = ClientMessagePasser.getInstance();
    public TabbedPanelLibrary_T(int flag,String myID)
    {
        this.setBackground(new Color(0x0000001, true));
        this.setOpaque(false);
        if(flag==2) {
            //JTabbedPane jtbp=new JTabbedPane();	//创建选项卡
            this.setTabPlacement(1);
            this.setBounds(0, 0, 1200, 700);//注意！！！！！！！！！！！！！！！！！！！！！！！


            JPanel jp11 = new JPanel();
            JPanel jp12 = new JPanel();
            JPanel jp13 = new JPanel();
            //选项卡1的内容
            //查询数据库
            Book book = new Book();
            Gson gson = new Gson();
            String s = gson.toJson(book);
            passer.send(new Message("admin", s, "library", "get"));

            Message msg = passer.receive();
            Map<String, java.util.List<Book>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Book>>>() {
            }.getType());
            List<Book> res = map.get("res");
            try {
                Object[] columnNames = new Object[]{"书籍号", "书名", "作者", "类型", "剩余册数"};
                Object[][] rowData = new Object[res.size()][5];
                for (int i = 0; i < res.size(); i++) {
                    rowData[i][0] = res.get(i).getBookID();
                    rowData[i][1] = res.get(i).getBookName();
                    rowData[i][2] = res.get(i).getAuthor();
                    rowData[i][3] = res.get(i).getType();
                    rowData[i][4] = res.get(i).getLeftSize();
                }
                jp11.setLayout(new CardLayout(10, 10));
                jp11.add(new MyTablePanel(rowData, columnNames));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            //选项卡2的内容
            JPanel enquireBook = new PanelEnquireBook("teacher");

            //选项卡3的内容
            jp13.setLayout(new CardLayout(10, 10));
            jp13.add(new PanelMyBook(myID));

            this.addTab("书籍信息总览", new ImageIcon("Vcampus//resource//tab_library.png"), jp11, "书籍信息总览");
            this.addTab("查询书籍信息", new ImageIcon("Vcampus//resource//search.png"), enquireBook, "查询书籍信息");
            this.addTab("我的借阅", new ImageIcon("Vcampus//resource//search.png"), jp13, "查询借阅情况");
            this.setFont(new Font("宋体", Font.BOLD, 24));

        }

        //jp.add(jtbp);
    }
}
