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
    public MyMassagePanel priMessage;
    public MyMassagePanel pubMessage;
    final MessagePasser passer = ClientMessagePasser.getInstance();
    Thread thread;
    String ID;

    public TabbedPanelMessage_A(int flag,String ID) {
        this.ID = ID;

        this.setTabPlacement(1);
        this.setBounds(0, 0, 1200, 650);//注意！！！！！！！！！！！！！！！！！！！！！！！

        JPanel jp11 = new JPanel();
        JPanel jp12 = new JPanel();
        JPanel jp13 = new JPanel();
        //选项卡1的内容

        jp12.setLayout(null);
        //按钮
        JLabel label = new JLabel("说明：请输入要删除的消息的id号，只能删除别人发给自己的私有消息");
        label.setBounds(50, 50, 1000, 50);
        label.setFont(new Font("宋体", Font.BOLD, 20));
        JButton btnRegister = new JButton("删除");
        btnRegister.setBounds(600, 150, 100, 50);
        btnRegister.setFont(new Font("宋体", Font.BOLD, 20));
        JTextField txtEnquire = new JTextField();
        txtEnquire.setBounds(50, 150,500, 50);
        txtEnquire.setFont(new Font("楷体", Font.BOLD, 20));
        btn.setBounds(500, 300, 100, 50);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (passer) {
                    priMessage.keepTabelPage(getAllMessage());
                    pubMessage.keepTabelPage(getPubMessage());
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.out.println("renew!");
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message message;
                Gson gson = new Gson();
                InnerMessage iMsg = new InnerMessage();
                iMsg.setInnerID(Integer.valueOf(txtEnquire.getText()));

                synchronized (passer) {
                    passer.send(new Message("student", gson.toJson(iMsg),
                            "chat", "delete"));
                    message = passer.receive();
                    Map<String, String> map = gson.fromJson(message.getData(),
                            new TypeToken<Map<String, String>>(){}.getType());
                    String res = map.get("res");
                    if(!res.equals("OK")) warningFrame("删除失败！");
                    else informFrame("删除成功");
                }
            }
        });

        jp12.add(btnRegister);
        jp12.add(txtEnquire);
        jp12.add(label);
        jp12.add(btn);
        JPanel panel = new PanelSendMassage(ID);

        priMessage = new MyMassagePanel(getAllMessage(), new Object[]{"id", "消息"});
        pubMessage = new MyMassagePanel(getPubMessage(), new Object[]{"消息"});

        jp11.setLayout(new CardLayout(10, 10));
        jp11.add(priMessage);
        jp13.setLayout(new CardLayout(10, 10));
        jp13.add(pubMessage);

        this.addTab("查看私信", null, jp11, "查看私信");
        this.addTab("公共频道", null, jp13, "查看公共频道消息");
        this.addTab("发送消息", null, panel, "发送消息");
        this.addTab("操作消息", null, jp12, "删除私有消息");
        this.setFont(new Font("宋体", Font.BOLD, 24));

        //jp.add(jtbp);
    }

    public Object[][] getAllMessage() {
        Gson gson = new Gson();
        Message message;
        User user = new User();
        user.setStudentID(ID);
        synchronized (passer) {
            passer.send(new Message("admin", gson.toJson(user), "chat", "get"));
            message = passer.receive();
        }

        Map<String, java.util.List<InnerMessage>> map = gson.fromJson(message.getData(),
                new TypeToken<Map<String, List<InnerMessage>>>(){}.getType());
        List<InnerMessage> res = map.get("res");

        Object[][] rowData = new Object[res.size()][2];
        for (int i = 0; i < res.size(); i++) {
            InnerMessage iMsg = res.get(res.size() - i - 1);
            String from = iMsg.getSender();
            String content = iMsg.getContent();
            rowData[i][1] = "来自" + from + "的消息：" + content;

            rowData[i][0] = iMsg.getInnerID();

        }
        return rowData;
    }

    public void warningFrame(String tips) {
        JOptionPane.showMessageDialog(this, tips, "警告", JOptionPane.ERROR_MESSAGE);
    }

    public void informFrame(String title) {
        JOptionPane.showMessageDialog(this, title, "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    public Object[][] getPubMessage() {
        Gson gson = new Gson();
        Message message;

        synchronized (passer) {
            passer.send(new Message("admin", "", "chat", "getPub"));
            message = passer.receive();
        }

        Map<String, List<PublicMessage>> map = gson.fromJson(message.getData(),
                new TypeToken<Map<String, List<PublicMessage>>>(){}.getType());
        List<PublicMessage> res = map.get("res");

        Object[][] rowData = new Object[res.size()][1];
        for (int i = 0; i < res.size(); i++) {
            String from = res.get(res.size() - i - 1).getStudentID();
            String content = res.get(res.size() - i - 1).getContent();
            rowData[i][0] = "来自" + from + "的消息：" + content;
        }
        return rowData;
    }
}
