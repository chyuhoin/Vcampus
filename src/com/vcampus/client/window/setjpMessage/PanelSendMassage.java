package com.vcampus.client.window.setjpMessage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.InnerMessage;
import com.vcampus.pojo.PublicMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class PanelSendMassage extends JPanel {
    JButton btnInquire = new JButton("发送");
    JTextField txtEnquire = new JTextField();
    JTextArea a=new JTextArea();
    JScrollPane area=new JScrollPane(a);
    JLabel label = new JLabel("收信人ID");
    JLabel labell=new JLabel("正文");
    JLabel label2=new JLabel("(不填则为公共消息)");
    ImageIcon image2=new ImageIcon("Vcampus//resource//Massage//发送.gif");
    MessagePasser passer = ClientMessagePasser.getInstance();
    Gson gson = new Gson();

    public PanelSendMassage(String ID) {
        SpringLayout layS=new SpringLayout();
        this.setLayout(layS);

        //标题1
        label.setFont(new Font("楷体", Font.BOLD, 20));
        layS.putConstraint(layS.NORTH, label, 40, layS.NORTH, this);
        layS.putConstraint(layS.WEST, label, 40, layS.WEST, this);
        //文本框
        txtEnquire.setFont(new Font("楷体", Font.BOLD, 20));
        layS.putConstraint(layS.NORTH, txtEnquire, 0, layS.NORTH, label);
        layS.putConstraint(layS.WEST, txtEnquire, 10, layS.EAST, label);
        layS.putConstraint(layS.EAST, txtEnquire, -40, layS.EAST, this);
        //注释
        label2.setFont(new Font("楷体", Font.BOLD, 16));
        label2.setForeground(Color.gray);
        layS.putConstraint(layS.NORTH, label2, 10, layS.SOUTH, txtEnquire);
        layS.putConstraint(layS.WEST, label2, 0, layS.WEST, txtEnquire);
        //图片
        int size=320;
        image2.setImage(image2.getImage().getScaledInstance(size,size,Image.SCALE_DEFAULT));
        JLabel lblImg2=new JLabel(image2);
        lblImg2.setPreferredSize(new Dimension(size,size));
        layS.putConstraint(layS.EAST, lblImg2, 0, layS.EAST, txtEnquire);
        //文本域
        a.setFont(new Font("宋体",Font.BOLD, 20));
        a.setLineWrap(true);

        layS.putConstraint(layS.NORTH, area, 20, layS.SOUTH,label2);
        layS.putConstraint(layS.WEST, area, 0, layS.WEST, txtEnquire);
        layS.putConstraint(layS.EAST, area, -10, layS.WEST, lblImg2);

        layS.putConstraint(layS.NORTH, lblImg2, 0, layS.NORTH, area);
        layS.putConstraint(layS.SOUTH, area, -50, layS.SOUTH, lblImg2);
        //标签2
        labell.setFont(new Font("楷体", Font.BOLD, 20));
        layS.putConstraint(layS.NORTH, labell, 0, layS.NORTH, area);
        layS.putConstraint(layS.EAST, labell, -10, layS.WEST, area);
        //按钮
        btnInquire.setFont(new Font("宋体",Font.BOLD, 20));
        btnInquire.setPreferredSize(new Dimension(180,35));
        layS.putConstraint(layS.NORTH, btnInquire, 10, layS.SOUTH, area);
        layS.putConstraint(layS.EAST, btnInquire, 0, layS.EAST,area);
        layS.putConstraint(layS.SOUTH, btnInquire, 0, layS.SOUTH, lblImg2);

        // 添加到内容面板
        this.add(labell);
        this.add(lblImg2);
        this.add(label2);
        this.add(txtEnquire);
        this.add(btnInquire);
        this.add(area);
        this.add(label);

        btnInquire.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                String target = txtEnquire.getText();
                String content = a.getText();
                if(target.equals("")) {
                    PublicMessage pMsg = new PublicMessage(ID, content);
                    passer.send(new Message("student", gson.toJson(pMsg), "chat", "postPub"));
                    Message message = passer.receive();
                    Map<String, String> map = gson.fromJson(message.getData(),
                            new TypeToken<Map<String, String>>(){}.getType());
                    String res = map.get("res");
                    if(!res.equals("OK")) warningFrame("发送失败！");
                    else informFrame("发送成功");
                } else {
                    InnerMessage iMsg = new InnerMessage(target, content, ID, null);
                    passer.send(new Message("student", gson.toJson(iMsg), "chat", "post"));
                    Message message = passer.receive();
                    Map<String, String> map = gson.fromJson(message.getData(),
                            new TypeToken<Map<String, String>>(){}.getType());
                    String res = map.get("res");
                    if(!res.equals("OK")) warningFrame("发送失败！");
                    else informFrame("发送成功");
                }
                txtEnquire.setText("");
                a.setText("");
            }
        });
    }

    public void warningFrame(String tips) {
        JOptionPane.showMessageDialog(this, tips, "警告", JOptionPane.ERROR_MESSAGE);
    }

    public void informFrame(String title) {
        JOptionPane.showMessageDialog(this, title, "提示", JOptionPane.INFORMATION_MESSAGE);
    }
}
