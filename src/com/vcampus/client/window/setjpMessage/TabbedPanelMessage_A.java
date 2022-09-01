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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

/**
 * 管理员身份站内消息管理界面
 * @author 韩宇 张星喆 钟昊原
 * @date 2022/08/17
 */
public class TabbedPanelMessage_A extends JTabbedPane{

    public MyMassagePanel priMessage;
    public MyMassagePanel pubMessage;
    public ImageIcon image1=new ImageIcon("Vcampus//resource//Massage//回信.gif");
    public ImageIcon image2=new ImageIcon("Vcampus//resource//Massage//发送.gif");
    public ImageIcon image3=new ImageIcon("Vcampus//resource//Massage//信息.gif");
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

        SpringLayout layS0=new SpringLayout();
        jp12.setLayout(layS0);
        //按钮
        JLabel label = new JLabel("请输入要删除的消息的id号:");
        label.setFont(new Font("宋体", Font.BOLD, 16));
        label.setForeground(Color.darkGray);
        JButton btnRegister = new JButton("删除");
        btnRegister.setFont(new Font("宋体", Font.BOLD, 20));
        JTextField txtEnquire = new JTextField();
        txtEnquire.setFont(new Font("楷体", Font.BOLD, 20));
        //标签
        layS0.putConstraint(layS0.NORTH,label,10,layS0.NORTH,jp12);
        layS0.putConstraint(layS0.WEST,label,10,layS0.WEST,jp12);
        layS0.putConstraint(layS0.EAST,label,-10,layS0.EAST,jp12);
        //文本框
        layS0.putConstraint(layS0.NORTH,txtEnquire,10,layS0.SOUTH,label);
        layS0.putConstraint(layS0.WEST,txtEnquire,0,layS0.WEST,label);
        layS0.putConstraint(layS0.EAST,txtEnquire,0,layS0.EAST,label);
        //按钮
        layS0.putConstraint(layS0.NORTH,btnRegister,10,layS0.SOUTH,txtEnquire);
        layS0.putConstraint(layS0.EAST,btnRegister,0,layS0.EAST,txtEnquire);


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
                            new TypeToken<Map<String, String>>() {
                            }.getType());
                    String res = map.get("res");
                    if (!res.equals("OK")) warningFrame("删除失败！");
                    else informFrame("删除成功");
                }
            }
        });

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                priMessage.keepTabelPage(getAllMessage());
                pubMessage.keepTabelPage(getPubMessage());
                System.out.println("renew!");
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

        jp12.add(btnRegister);
        jp12.add(txtEnquire);
        jp12.add(label);
        JPanel panel = new PanelSendMassage(ID);

        priMessage = new MyMassagePanel(getAllMessage(), new Object[]{"id", "消息"});
        pubMessage = new MyMassagePanel(getPubMessage(), new Object[]{"消息"});

        //Jp11-查看私信-panel
        SpringLayout layS=new SpringLayout();
        jp11.setLayout(layS);
        //动图
        int size=300;
        image1.setImage(image1.getImage().getScaledInstance(size,size,Image.SCALE_DEFAULT));
        JLabel lblImg1=new JLabel(image1);
        lblImg1.setPreferredSize(new Dimension(size,size));
        jp11.add(lblImg1);
        layS.putConstraint(layS.NORTH, lblImg1, 20, layS.NORTH, jp11);
        layS.putConstraint(layS.EAST, lblImg1, -20, layS.EAST, jp11);
        //删除区域
        jp11.add(jp12);
        layS.putConstraint(layS.NORTH, jp12, 20, layS.SOUTH, lblImg1);
        layS.putConstraint(layS.EAST,jp12, 0, layS.EAST, lblImg1);
        layS.putConstraint(layS.WEST, jp12, 0, layS.WEST, lblImg1);
        layS.putConstraint(layS.SOUTH, jp12, 0, layS.SOUTH, jp11);
        //信件表格
        jp11.add(priMessage);
        //priMessage.setTableColumWidth(0,120);//设置id列宽度
        layS.putConstraint(layS.NORTH, priMessage, 0, layS.NORTH, lblImg1);
        layS.putConstraint(layS.WEST, priMessage, 20, layS.WEST, jp11);
        layS.putConstraint(layS.EAST, priMessage, -10, layS.WEST, lblImg1);
        layS.putConstraint(layS.SOUTH, priMessage, 0, layS.SOUTH, jp11);


        //jp13-查看公共消息
        SpringLayout laySSS=new SpringLayout();
        jp13.setLayout(laySSS);
        jp13.add(pubMessage);
        laySSS.putConstraint(layS.SOUTH, pubMessage, 0, layS.SOUTH, jp13);
        laySSS.putConstraint(layS.NORTH, pubMessage, 30, layS.NORTH, jp13);
        laySSS.putConstraint(layS.WEST, pubMessage, 30, layS.WEST, jp13);
        laySSS.putConstraint(layS.EAST, pubMessage, -30, layS.EAST, jp13);

        this.addTab("查看私信",  new ImageIcon("resource//tab_privatemessage.png"), jp11, "查看私信");
        this.addTab("公共频道", new ImageIcon("resource//tab_publicmessage.png"), jp13, "查看公共频道消息");
        this.addTab("发送消息", new ImageIcon("resource//tab_send.png"), panel, "发送消息");
//        this.addTab("删除消息",  new ImageIcon("resource//tab_delete.png"), jp12, "删除私有消息");
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
//            rowData[i][1] = "来自" + from + "的消息：" + content;
            rowData[i][1] = "来自" + ID + "的消息：" + content;

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