/** ===================================================
 * Title: SetJPUser1.java
 * Created: [2022-8-21 15:14:42] by  张星喆
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 用户管理-个人信息界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-14,创建此文件
 *2. 对这个文件的修改历史进行详细描述，一般包括时间，修改者，
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpUSER;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Student;
import com.vcampus.pojo.User;
import com.vcampus.pojo.User;//导入User类

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetJPUser1 extends JPanel {
    public String[] strList=new String[3];//暂存当前User的信息
    public JPanel jp1=new JPanel();
    public JPanel jp2=new JPanel();
    public String ID;
    public int type;
    MessagePasser passer = ClientMessagePasser.getInstance();
    public SetJPUser1(int t,String id,JPanel jp, CardLayout layout_Card){
        SpringLayout layout_Spring = new SpringLayout();
        jp1.setLayout(layout_Spring);
        jp2.setLayout(layout_Spring);
        jp.add(jp1);
        jp.add(jp2);
        layout_Card.show(jp, "jp1");//先显示jp1

        ID=id;
        type=t;//身份
        setjp1(jp,layout_Spring,layout_Card);
    }

    public void setStrList(String tempID){
        User user = new User();
        user.setStudentID(tempID);
        //user.setType(type);
        Gson gson = new Gson();
        String s = gson.toJson(user);
        passer.send(new Message("admin", s, "login", "getone"));

        Message msg = passer.receive();
        System.out.println(msg);
        Map<String, List<User>> map = new Gson().fromJson(msg.getData(),
                new TypeToken<HashMap<String, List<User>>>(){}.getType());
        List<User> res = map.get("res");
        User tempU= res.get(0);
        if(!res.isEmpty()){
            strList[0]=tempU.getStudentID();
            strList[1]=tempU.getPassword();
            switch (tempU.getType()) {
                case (1): strList[2] = "学生";break;
                case (2): strList[2] = "教师";break;
                case (3): strList[2] = "管理员";break;
            }
        }
        else{
            JOptionPane.showMessageDialog(
                    jp1,
                    "查无此人！",
                    "警告",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * 设置第一张卡片的内容-个人信息展示
     * @param jp
     * @param layout_Card
     */
    public void setjp1(JPanel jp,SpringLayout layout_Spring,CardLayout layout_Card){
        //设置具体内容
        setStrList(ID);//个人信息
        //jp1内容
        //标题
        JLabel lbl = new JLabel("个人信息");
        jp1.add(lbl);
        lbl.setFont(new Font("黑体", Font.BOLD, 50));
        layout_Spring.putConstraint(layout_Spring.NORTH, lbl, 20, layout_Spring.NORTH, jp1);  //标签1北侧——>容器北侧
        layout_Spring.putConstraint(layout_Spring.WEST, lbl, 20, layout_Spring.WEST, jp1);    //标签1西侧——>容器西侧
        // 创建修改按钮
        JButton btn=new JButton("修改密码");
        if(strList[2].equals("管理员"))
            jp1.add(btn);
        btn.setFont(new Font("黑体", Font.BOLD, 20));
        layout_Spring.putConstraint(layout_Spring.NORTH, btn, 20, layout_Spring.NORTH, jp1);
        layout_Spring.putConstraint(layout_Spring.EAST, btn, -20, layout_Spring.EAST, jp1);
        //信息列表：
        //标签
        JLabel[] lblList={
                new JLabel("一卡通号"),
                new JLabel("密码"),
                new JLabel("权限")
        };
        for(int i=0;i<lblList.length;i++){
            JLabel lbli=lblList[i];
            jp1.add(lbli);
            lbli.setFont(new Font("黑体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, lbli, 60+50*i, layout_Spring.SOUTH, lbl);  //标签1北侧——>容器北侧
            layout_Spring.putConstraint(layout_Spring.EAST, lbli, 50, layout_Spring.EAST, lbl);    //标签1西侧——>容器西侧
        }
        //文本框
        //JTextField[] textList=new JTextField[5];
        JTextField[] textList={new JTextField(),new JTextField(), new JTextField()};
        //设置文本框内容
        for(int i=0;i<textList.length;i++){
            textList[i].setText(strList[i]);
        }
        for(int i=0;i<textList.length;i++){
            JTextField texti=textList[i];
            jp1.add(texti);
            texti.setEditable(false);
            texti.setFont(new Font("黑体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, texti, 0, layout_Spring.NORTH, lblList[i]);
            layout_Spring.putConstraint(layout_Spring.WEST, texti, 20, layout_Spring.EAST, lblList[i]);
            texti.setColumns(60);
        }

        //修改按钮监听-管理员
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout_Card.next(jp);
                setjp2(jp,layout_Spring,layout_Card);
                System.out.println("用户管理系统-个人信息-修改");
            }
        });
    }

    /**
     * 设置第二张卡片：（管理员）修改密码
     * @param jp
     * @param layout_Spring
     * @param layout_Card
     */
    public void setjp2(JPanel jp,SpringLayout layout_Spring,CardLayout layout_Card){
        //标题
        JLabel lbl = new JLabel("密码修改");
        jp2.add(lbl);
        lbl.setFont(new Font("黑体", Font.BOLD, 50));
        layout_Spring.putConstraint(layout_Spring.NORTH, lbl, 20, layout_Spring.NORTH, jp2);  //标签1北侧——>容器北侧
        layout_Spring.putConstraint(layout_Spring.WEST, lbl, 20, layout_Spring.WEST, jp2);    //标签1西侧——>容器西侧
        //信息列表：
        //标签
        JLabel[] lblList={
                new JLabel("请输入当前密码："),
                new JLabel("请输入新密码："),
                new JLabel("请在此输入新密码：")
        };
        for(int i=0;i<lblList.length;i++){
            JLabel lbli=lblList[i];
            jp2.add(lbli);
            lbli.setFont(new Font("黑体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, lbli, 60+50*i, layout_Spring.SOUTH, lbl);
            layout_Spring.putConstraint(layout_Spring.EAST, lbli, 10, layout_Spring.EAST, lbl);
        }
        //文本框
        JTextField[] textList={new JTextField(),new JTextField(), new JTextField()};
        for(int i=0;i<textList.length;i++){
            JTextField texti=textList[i];
            jp2.add(texti);
            texti.setEditable(true);
            texti.setFont(new Font("黑体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, texti, 0, layout_Spring.NORTH, lblList[i]);
            layout_Spring.putConstraint(layout_Spring.WEST, texti, 20, layout_Spring.EAST, lblList[i]);
            texti.setColumns(50);
        }

        JButton btn=new JButton("确认");// 创建确认按钮
        jp2.add(btn);
        JButton btn2=new JButton("取消");//创建取消按钮
        jp2.add(btn2);
        btn.setFont(new Font("黑体", Font.BOLD, 20));
        btn.setPreferredSize(new Dimension(100, 40));
        layout_Spring.putConstraint(layout_Spring.NORTH, btn, 50, layout_Spring.NORTH, textList[2]);
        layout_Spring.putConstraint(layout_Spring.EAST, btn, -20, layout_Spring.WEST, btn2);

        btn2.setFont(new Font("黑体", Font.BOLD, 20));
        btn2.setPreferredSize(new Dimension(100, 40));
        layout_Spring.putConstraint(layout_Spring.NORTH, btn2, 0, layout_Spring.NORTH,btn);
        layout_Spring.putConstraint(layout_Spring.EAST, btn2, 0, layout_Spring.EAST, textList[2]);

        //确定按钮监听
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newP1=textList[1].getText();
                String newP2=textList[2].getText();
                System.out.println("新密码："+newP1+" & "+newP2+" 是否相等："+(newP1.equals(newP2)));
                if(!newP1.equals(newP2)){
                    JOptionPane.showMessageDialog(
                            jp2,
                            "两次输入的新密码不相同",
                            " ",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
                else{
                    String truePass=strList[1];
                    if(!textList[0].getText().equals(truePass)){
                        JOptionPane.showMessageDialog(
                                jp2,
                                "输入原密码错误",
                                " ",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                    else{
                        if(SendTnfo_A(newP1)) {//修改密码成功 newP1==newP2
                            layout_Card.first(jp);
                            jp1.removeAll();
                            setjp1(jp, layout_Spring, layout_Card);//设置jp1
                            jp1.repaint();//重新绘制jp1
                            for (JTextField jTextField : textList) jTextField.setText(""); //清空密码框
                        System.out.println("新密码："+newP1);
                        }
                    }
                }
                System.out.println("用户管理系统-密码-修改");
            }
        });
        btn2.addActionListener(new ActionListener() {//取消按钮
            @Override
            public void actionPerformed(ActionEvent e) {
                layout_Card.first(jp);
            }
        });

    }

    /**
     * 传送，修改密码
     * @return
     */
    public boolean SendTnfo_A(String newPass){
        User user=new User();
        user.setStudentID(strList[0]);
        user.setPassword(newPass);
        user.setType(3);
        Gson gson = new Gson();
        String s = gson.toJson(user);
        passer.send(new Message("admin", s, "login", "Change Password"));

        //接收信息是否传递成功
        Message msg = passer.receive();
        Map<String, Object> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, Object>>(){}.getType());
        if(map.get("res").equals("OK")) {
            JOptionPane.showMessageDialog(
                jp2,
                "密码修改成功",
                " ",
                JOptionPane.INFORMATION_MESSAGE
            );
            return true;
        }
        else{
            JOptionPane.showMessageDialog(
                    jp2,
                    "密码修改失败",
                    "ERROR",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
    }
}
