package com.vcampus.client.window.setjpLibrary;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.client.window.setjpLibrary.mytablepanel.MyTablePanel;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Book;
import com.vcampus.pojo.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PanelEnquireBandR extends JPanel {
    MessagePasser passer = ClientMessagePasser.getInstance();
    SpringLayout layS = new SpringLayout();//总布局-上下两个panel
    String studentID = null;
    Object[] columnNames = new Object[]{"书籍号", "书名", "作者", "类型", "剩余册数"};
    Object[][] rowData = new Object[0][5];
    JPanel PANEL=new JPanel();
    JPanel panel1=new JPanel();
    JPanel panel2;

    public PanelEnquireBandR() {
        PANEL=this;
        //设置总布局
        this.setLayout(layS);//弹性布局
        this.add(panel1);
        //上下两张panel排版
        layS.putConstraint(layS.NORTH, panel1, 0, layS.NORTH, this);
        layS.putConstraint(layS.WEST, panel1, 0, layS.WEST, this);
        layS.putConstraint(layS.EAST, panel1, 0, layS.EAST, this);
        layS.putConstraint(layS.SOUTH, panel1, 70, layS.NORTH, this);
        /**
         * 上半张panel
         */
        //查询&显示组件
        CardLayout layC = new CardLayout(10,10);
        panel1.setLayout(layC);

        SpringLayout layS1=new SpringLayout();
        SpringLayout layS2=new SpringLayout();
        JPanel jp1 = new JPanel(layS1);
        JPanel jp2 = new JPanel(layS2);
        panel1.add(jp1);//查询组件
        panel1.add(jp2);//显示组件
        //jp1
        JButton btn = new JButton("搜索");
        jp1.add(btn);
        btn.setFont(new Font("宋体", Font.BOLD, 20));
        layS1.putConstraint(layS1.NORTH, btn, 20, layS1.NORTH, jp1);
        layS1.putConstraint(layS1.EAST, btn, -20, layS1.EAST, jp1);

        JTextField text = new JTextField();
        jp1.add(text);
        text.setPreferredSize(new Dimension(240,30));
        text.setFont(new Font("宋体", Font.BOLD, 20));
        layS1.putConstraint(layS1.NORTH, text, 0, layS1.NORTH, btn);
        layS1.putConstraint(layS1.EAST, text, -20, layS1.WEST, btn);

        JLabel lbl = new JLabel("请输入借阅人的一卡通号：");
        jp1.add(lbl);
        lbl.setFont(new Font("宋体", Font.BOLD, 20));
        layS1.putConstraint(layS1.NORTH, lbl, 3, layS1.NORTH, btn);
        layS1.putConstraint(layS1.EAST, lbl, 0, layS1.WEST, text);
        //jp2
        JLabel lbl2 = new JLabel("当前查询用户< " + studentID + " >");
        jp2.add(lbl2);
        lbl2.setFont(new Font("宋体", Font.BOLD, 22));
        layS2.putConstraint(layS2.NORTH, lbl2, 20, layS2.NORTH, jp2);
        layS2.putConstraint(layS2.WEST, lbl2, 30, layS2.WEST, jp2);

        JButton btn2 = new JButton("完成");
        jp2.add(btn2);
        //btn2.setPreferredSize(new Dimension(100,30));
        btn2.setFont(new Font("宋体", Font.BOLD, 20));
        layS2.putConstraint(layS2.NORTH, btn2, 20, layS2.NORTH, jp2);
        layS2.putConstraint(layS2.EAST, btn2, -20, layS2.EAST, jp2);
        /**
         * 下半张panel
         */
        //表格显示界面
        panel2 = new MyTablePanel(rowData, columnNames);
        PANEL.add(panel2);
        layS.putConstraint(layS.NORTH, panel2, 10, layS.SOUTH, panel1);
        layS.putConstraint(layS.WEST, panel2, 0, layS.WEST, this);
        layS.putConstraint(layS.EAST, panel2, 0, layS.EAST, this);
        layS.putConstraint(layS.SOUTH, panel2, -10, layS.SOUTH, this);
        /**
         * 监听事件
         */
        //搜索按钮监听
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studentID = text.getText().trim();
                if(!Objects.equals(studentID, "")) {
                    layC.next(panel1);
                    lbl2.setText("当前查询用户< " + studentID + " >");
                    setPanel2_ID(studentID);
                }
                else{ JOptionPane.showMessageDialog(PANEL, "请输入用户一卡通号", "提示", JOptionPane.INFORMATION_MESSAGE); }
            }
        });
        //完成按钮监听
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layC.first(panel1);
                text.setText("");
                System.out.println("返回搜索栏");
                setPanel2(new Object[0][], columnNames);//空表格
            }
        });
    }

    public void setPanel2_ID(String studentID) {
        //查询数据库
        User user = new User();
        user.setStudentID(studentID);
        Gson gson = new Gson();
        String s = gson.toJson(user);
        passer.send(new Message("admin", s, "library", "getB"));
        Message msg = passer.receive();
        Map<String, java.util.List<Book>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Book>>>() {
        }.getType());
        List<Book> res = map.get("res");
        try {
            Object[][] rowData1 = new Object[res.size()][5];
            for (int i = 0; i < res.size(); i++) {
                rowData1[i][0] = res.get(i).getBookID();
                rowData1[i][1] = res.get(i).getBookName();
                rowData1[i][2] = res.get(i).getAuthor();
                rowData1[i][3] = res.get(i).getType();
                rowData1[i][4] = res.get(i).getLeftSize(); }
            setPanel2(rowData1, columnNames);
            if (res.size() == 0)
                JOptionPane.showMessageDialog(PANEL, "该用户暂未有借阅记录", " ", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) { throw new RuntimeException(e); }
    }
    public void setPanel2(Object[][] rowData, Object[] columnNames) {
        PANEL.remove(panel2);
        panel2 = new MyTablePanel(rowData, columnNames);
        PANEL.add(panel2);
        layS.putConstraint(layS.NORTH, panel2, 10, layS.SOUTH, panel1);
        layS.putConstraint(layS.WEST, panel2, 0, layS.WEST, this);
        layS.putConstraint(layS.EAST, panel2, 0, layS.EAST, this);
        layS.putConstraint(layS.SOUTH, panel2, -10, layS.SOUTH, this);
        updateUI();
        repaint();
    }
}
