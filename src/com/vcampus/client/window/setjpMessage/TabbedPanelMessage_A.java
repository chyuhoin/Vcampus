/** ===================================================
 * Title: TabbedPanelMessage_A.java
 * Created: [2022-8-17 17:11:20] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 站内消息管理——管理员界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-17,创建此文件
 *2.
 *3.
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpMessage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.client.window.setjpLibrary.PanelEnquireBook;
import com.vcampus.client.window.setjpLibrary.mytablepanel.MyTablePanel;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.InnerMessage;
import com.vcampus.pojo.PublicMessage;
import com.vcampus.pojo.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class TabbedPanelMessage_A extends JTabbedPane{

    JButton btn = new JButton("刷新");
    public MyTablePanel priMessage;
    public MyTablePanel pubMessage;
    Thread thread;

    public TabbedPanelMessage_A(String ID) {
        this.setTabPlacement(1);
        this.setBounds(0,0,1400,650);//注意！！！！！！！！！！！！！！！！！！！！！！！

        JPanel jp11 = new JPanel();
        JPanel jp12 = new JPanel();
        JPanel jp13 = new JPanel();
        //选项卡1的内容
        //按钮
        JButton btnRegister = new JButton("啦啦啦啦啦");
        btnRegister.setFont(new Font("宋体", Font.BOLD, 50));

        jp12.add(btnRegister);
        JPanel panel = new PanelSendMassage(ID);

        priMessage = new MyTablePanel(getAllMessage(), new Object[]{"消息"});
        pubMessage = new MyTablePanel(getPubMessage(), new Object[]{"消息"});

        priMessage.add(btn);
        pubMessage.add(btn);

        jp11.setLayout(new CardLayout(10, 10));
        jp11.add(priMessage);
        jp13.setLayout(new CardLayout(10, 10));
        jp13.add(pubMessage);

        this.addTab("查看私信", null, jp11,"查看私信");
        this.addTab("公共频道", null, jp13,"查看公共频道消息");
        this.addTab("发送消息", null, panel,"发送消息");
        this.setFont(new Font("宋体", Font.BOLD, 24));

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    priMessage = new MyTablePanel(getAllMessage(), new Object[]{"消息"});
                    pubMessage = new MyTablePanel(getPubMessage(), new Object[]{"消息"});
                    jp11.removeAll(); jp13.removeAll();
                    jp11.add(priMessage); jp13.add(pubMessage);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("renew!");
                }

            }
        });
        thread.start();

        //jp.add(jtbp);
    }

    public synchronized Object[][] getAllMessage() {
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        User user = new User();
        user.setStudentID("2");
        passer.send(new Message("student", gson.toJson(user), "chat", "get"));
        Message message = passer.receive();

        Map<String, java.util.List<InnerMessage>> map = gson.fromJson(message.getData(),
                new TypeToken<Map<String, List<InnerMessage>>>(){}.getType());
        List<InnerMessage> res = map.get("res");

        Object[][] rowData = new Object[res.size()][1];
        for (int i = 0; i < res.size(); i++) {
            String from = res.get(i).getStudentID();
            String content = res.get(i).getContent();
            rowData[i][0] = "来自" + from + "的消息：" + content;
        }
        return rowData;
    }

    public synchronized Object[][] getPubMessage() {
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();


        passer.send(new Message("student", "", "chat", "getPub"));
        Message message = passer.receive();

        Map<String, List<PublicMessage>> map = gson.fromJson(message.getData(),
                new TypeToken<Map<String, List<PublicMessage>>>(){}.getType());
        List<PublicMessage> res = map.get("res");

        Object[][] rowData = new Object[res.size()][1];
        for (int i = 0; i < res.size(); i++) {
            String from = res.get(i).getStudentID();
            String content = res.get(i).getContent();
            rowData[i][0] = "来自" + from + "的消息：" + content;
        }
        return rowData;
    }
}
