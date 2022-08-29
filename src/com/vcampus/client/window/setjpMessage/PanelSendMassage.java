package com.vcampus.client.window.setjpMessage;

import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.MessagePasser;

import javax.swing.*;
import java.awt.*;

public class PanelSendMassage  extends JPanel {
    JButton btnInquire = new JButton("发送");
    JTextField txtEnquire = new JTextField();
    JTextArea area = new JTextArea();
    JLabel label = new JLabel("收信人");
    MessagePasser passer = ClientMessagePasser.getInstance();

    public PanelSendMassage() {
        this.setLayout(null);
        int x=50,y=50;//起始坐标
        int txtWidth=110, txtHeight=42;

        label.setBounds(x, y, txtWidth, txtHeight);
        label.setFont(new Font("楷体", Font.BOLD, 20));

        txtEnquire.setBounds(x + 100,y,txtWidth*3,txtHeight);
        txtEnquire.setFont(new Font("楷体", Font.BOLD, 20));

        btnInquire.setFont(new Font("宋体",Font.BOLD, 20));
        btnInquire.setBounds(x+680,y,80,40);

        area.setBounds(x, y + 100, 1100, 400);
        btnInquire.setFont(new Font("宋体",Font.BOLD, 20));

        // 添加到内容面板
        this.add(txtEnquire);
        this.add(btnInquire);
        this.add(area);
        this.add(label);
    }
}
