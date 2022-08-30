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
    JTextArea area = new JTextArea();
    JLabel label = new JLabel("收信人ID，或public");
    MessagePasser passer = ClientMessagePasser.getInstance();
    Gson gson = new Gson();

    public PanelSendMassage(String ID) {
        this.setLayout(null);
        int x=50,y=50;//起始坐标
        int txtWidth=110, txtHeight=42;

        label.setBounds(x, y, txtWidth * 3, txtHeight);
        label.setFont(new Font("楷体", Font.BOLD, 20));

        txtEnquire.setBounds(x + 210,y,txtWidth*3,txtHeight);
        txtEnquire.setFont(new Font("楷体", Font.BOLD, 20));

        btnInquire.setFont(new Font("宋体",Font.BOLD, 20));
        btnInquire.setBounds(x+780,y,80,40);

        area.setBounds(x, y + 100, 1100, 400);
        area.setFont(new Font("宋体",Font.BOLD, 20));

        // 添加到内容面板
        this.add(txtEnquire);
        this.add(btnInquire);
        this.add(area);
        this.add(label);

        btnInquire.addActionListener(new ActionListener() {
            @Override
            public synchronized void actionPerformed(ActionEvent e) {
                String target = txtEnquire.getText();
                String content = area.getText();
                if(target.equals("public")) {
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
                area.setText("");
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
