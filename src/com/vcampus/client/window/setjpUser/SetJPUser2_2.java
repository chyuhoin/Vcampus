/** ===================================================
 * Title: SetJPUser2_2.java
 * Created: [2022-8-21 15:14:42] by  张星喆
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 用户管理-【学生/教师】密码修改界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-21,创建此文件
 *2. 对这个文件的修改历史进行详细描述，一般包括时间，修改者，
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpUser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.client.window.showMessageFrame;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于老师/学生 修改密码
 */
public class SetJPUser2_2 {
    public String[] strList=new String[3];//暂存当前User的信息
    public String ID;
    public int type;
    public boolean changeJudge;
    MessagePasser passer = ClientMessagePasser.getInstance();

    public SetJPUser2_2(int t,String id,JPanel jp, CardLayout layout_Card){
        ID=id;
        type=t;
        changeJudge=false;
        setjp(jp,layout_Card);
    }
    public void setChangeJudge(boolean b){changeJudge=b;}
    public boolean getChangeJudge(){return changeJudge;}
    public void setjp(JPanel jp,CardLayout layout_Card){
        setStrList(ID);

        SpringLayout layout_Spring=new SpringLayout();
        jp.setLayout(layout_Spring);
        //标题
        JLabel lbl = new JLabel(" 密码修改");
        ImageIcon lblIcon = new ImageIcon("Vcampus//resource//User_passwordRevise.png");
        lbl.setIcon(lblIcon);
        lbl.setHorizontalTextPosition(SwingConstants.RIGHT);
        jp.add(lbl);
        lbl.setFont(new Font("宋体", Font.BOLD, 30));
        layout_Spring.putConstraint(layout_Spring.NORTH, lbl, 20+100+20, layout_Spring.NORTH, jp);
        layout_Spring.putConstraint(layout_Spring.WEST, lbl, 20+100+150, layout_Spring.WEST, jp);
        //信息列表：
        //标签
        JLabel[] lblList={
                new JLabel("请输入当前密码："),
                new JLabel("请输入新密码："),
                new JLabel("请在此输入新密码：")
        };
        for(int i=0;i<lblList.length;i++){
            JLabel lbli=lblList[i];
            jp.add(lbli);
            lbli.setFont(new Font("宋体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, lbli, 60+50*i, layout_Spring.SOUTH, lbl);
            layout_Spring.putConstraint(layout_Spring.EAST, lbli, 10+20+20+20, layout_Spring.EAST, lbl);
        }
        //文本框
        JTextField[] textList={new JTextField(),new JTextField(), new JTextField()};
        for(int i=0;i<textList.length;i++){
            JTextField texti=textList[i];
            jp.add(texti);
            texti.setEditable(true);
            texti.setFont(new Font("宋体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, texti, 0, layout_Spring.NORTH, lblList[i]);
            layout_Spring.putConstraint(layout_Spring.WEST, texti, 20, layout_Spring.EAST, lblList[i]);
            texti.setColumns(30);
        }

        // 创建确认按钮
        JButton btn=new JButton("确认");
        jp.add(btn);
        btn.setFont(new Font("宋体", Font.BOLD, 20));
        //btn.setPreferredSize(new Dimension(150, 40));
        layout_Spring.putConstraint(layout_Spring.NORTH, btn, 50, layout_Spring.NORTH, textList[2]);
        layout_Spring.putConstraint(layout_Spring.EAST, btn, 0, layout_Spring.EAST, textList[2]);

        //确定按钮监听
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newP1=textList[1].getText().trim();
                String newP2=textList[2].getText().trim();
                System.out.println("新密码："+newP1+" & "+newP2+" 是否相等："+(newP1.equals(newP2)));
                if(!newP1.equals(newP2)){
                    showMessageFrame test=new showMessageFrame("两次输入的新密码不相同!",900,320,460, 80,1);
//                    JOptionPane.showMessageDialog(
//                            jp,
//                            "两次输入的新密码不相同",
//                            " ",
//                            JOptionPane.WARNING_MESSAGE
//                    );
                }
                else{
                    setStrList(ID);
                    String truePass=strList[1];
                    if(!textList[0].getText().trim().equals(truePass)){
                        JOptionPane.showMessageDialog(
                                jp,
                                "输入密码错误",
                                "提示",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                    else{
                        String newPass=textList[1].getText().trim();
                        SendTnfo_S_T(jp,newPass,type);
                        for (JTextField jTextField : textList) jTextField.setText("");//设置文本框为空
                        //SetJPUser1 setjp1=new SetJPUser1(1,ID,jp11,layout_Card);
                        changeJudge=true;
                        System.out.println("新密码："+newPass);
                    }
                }
                System.out.println("用户管理系统-密码-修改");
            }
        });
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
        if(res.size()!=0){
            strList[0]=tempU.getStudentID();
            strList[1]=tempU.getPassword();
            switch (tempU.getType()) {
                case (1): strList[2] = "学生";break;
                case (2): strList[2] = "教师";break;
                case (3): strList[2] = "管理员";break;
            }
        }
        else{
            //null
        }
    }
    public boolean SendTnfo_S_T(JPanel jp,String newPass,int type){
        User user=new User();
        user.setStudentID(ID);
        user.setPassword(newPass);
        user.setType(type);

        Gson gson = new Gson();
        String s = gson.toJson(user);
        passer.send(new Message("admin", s, "login", "Change Password"));

        //接收信息是否传递成功
        Message msg = passer.receive();
        Map<String, Object> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, Object>>(){}.getType());
        //传输信息
        //接收bool结果
        if(map.get("res").equals("OK")) {//成功写入
            JOptionPane.showMessageDialog(
                    jp,
                    "密码修改成功",
                    " ",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return true;
        }
        else{
            JOptionPane.showMessageDialog(
                    jp,
                    "密码修改失败",
                    "ERROR",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
    }
}
