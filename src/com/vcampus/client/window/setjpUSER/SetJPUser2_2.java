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

package com.vcampus.client.window.setjpUSER;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Student;
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
    public  String ID;
    MessagePasser passer = ClientMessagePasser.getInstance();
    public SetJPUser2_2(int type,String id,JPanel jp, CardLayout layout_Card){
        ID=id;
        SpringLayout layout_Spring=new SpringLayout();
        jp.setLayout(layout_Spring);
        //标题
        JLabel lbl = new JLabel("密码修改");
        jp.add(lbl);
        lbl.setFont(new Font("黑体", Font.BOLD, 50));
        layout_Spring.putConstraint(layout_Spring.NORTH, lbl, 20, layout_Spring.NORTH, jp);  //标签1北侧——>容器北侧
        layout_Spring.putConstraint(layout_Spring.WEST, lbl, 20, layout_Spring.WEST, jp);    //标签1西侧——>容器西侧
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
            lbli.setFont(new Font("黑体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, lbli, 60+50*i, layout_Spring.SOUTH, lbl);
            layout_Spring.putConstraint(layout_Spring.EAST, lbli, 50, layout_Spring.EAST, lbl);
        }
        //文本框
        JTextField[] textList={new JTextField(),new JTextField(), new JTextField()};
        for(int i=0;i<textList.length;i++){
            JTextField texti=textList[i];
            jp.add(texti);
            texti.setEditable(true);
            texti.setFont(new Font("黑体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, texti, 0, layout_Spring.NORTH, lblList[i]);
            layout_Spring.putConstraint(layout_Spring.WEST, texti, 20, layout_Spring.EAST, lblList[i]);
            texti.setColumns(50);
        }

        // 创建确认按钮
        JButton btn=new JButton("确认");
        jp.add(btn);
        btn.setFont(new Font("黑体", Font.BOLD, 20));
        btn.setPreferredSize(new Dimension(150, 40));
        layout_Spring.putConstraint(layout_Spring.NORTH, btn, 50, layout_Spring.NORTH, textList[2]);
        layout_Spring.putConstraint(layout_Spring.EAST, btn, -500, layout_Spring.EAST, jp);

        //确定按钮监听
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newP1=textList[1].getText();
                String newP2=textList[2].getText();
                System.out.println("新密码："+newP1+" & "+newP2+" 是否相等："+(newP1.equals(newP2)));
                if(!newP1.equals(newP2)){
                    JOptionPane.showMessageDialog(
                            jp,
                            "两次输入的新密码不相同",
                            " ",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
                else{
                    String truePass="truePass";
                    if(!textList[0].getText().equals(truePass)){
                        JOptionPane.showMessageDialog(
                                jp,
                                "输入密码错误",
                                " ",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                    else{
                        String newPass=textList[1].getText();
                        SendTnfo_S_T(jp,newPass,type);
                        System.out.println("新密码："+newPass);
                    }
                }
                System.out.println("用户管理系统-密码-修改");
            }
        });
    }
    public boolean SendTnfo_S_T(JPanel jp,String newPass,int type){
        User user=new User();
        user.setStudentID(ID);
        user.setPassword(newPass);
        user.setType(type);

        Gson gson = new Gson();
        String s = gson.toJson(user);
        passer.send(new Message("login", s, "user", "Change Password"));

        //接收信息是否传递成功
        Message msg = passer.receive();
        Map<String, List<Student>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, List<Student>>>(){}.getType());
        if(map.get("res").equals("OK")) {
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
