/** ===================================================
 * Title: SetJPUser2.java
 * Created: [2022-8-21 15:14:42] by  张星喆
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 用户管理-【管理员】用户信息管理界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-21,创建此文件
 *2. 对这个文件的修改历史进行详细描述，一般包括时间，修改者，
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpUser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.User;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetJPUser2 {
    public String[][] STRList;//保存所有用户信息
    public String[] strList=new String[3];//暂存当前某个用户信息（User）
    public JPanel jp1=new JPanel();//信息展示页面
    public JPanel jp2=new JPanel();//个人信息
    public JPanel jp3=new JPanel();//个人信息编辑
    public JPanel jp4=new JPanel();//注册
    public String ID;//身份
    public int type;
    MessagePasser passer = ClientMessagePasser.getInstance();
    public SetJPUser2(int t,String id,JPanel jp, CardLayout layout_Card){
        SpringLayout layout_Spring = new SpringLayout();
        jp.add("jp1",jp1);
        jp.add("jp2",jp2);
        jp.add("jp3",jp3);
        jp.add("jp4",jp4);
        jp1.setLayout(layout_Spring);
        jp2.setLayout(layout_Spring);
        jp3.setLayout(layout_Spring);
        jp4.setLayout(layout_Spring);
        layout_Card.show(jp, "jp1");//先显示jp1

        ID=id;//身份
        type=t;
        //setSTRList();
        setjp1(jp,layout_Spring,layout_Card);//加载jp1
    }
    /**
     * 获取所有用户的信息
     */
    public boolean setSTRList(){
        User user = new User();
        Gson gson = new Gson();
        String s = gson.toJson(user);
        passer.send(new Message("admin", s, "login", "getAll"));

        Message msg = passer.receive();
        Map<String, java.util.List<User>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<User>>>(){}.getType());
        List<User> res = map.get("res");
        if(res.size()!=0){
            STRList=new String[res.size()][3];
            for(int i=0;i<res.size();i++) {
                STRList[i][0] = res.get(i).getStudentID();
                STRList[i][1] = res.get(i).getPassword();
                switch (res.get(i).getType()) {
                    case (1): STRList[i][2] = "学生";break;
                    case (2): STRList[i][2] = "教师";break;
                    case (3): STRList[i][2] = "管理员";break;
                }
            }
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 根据 一卡通号，得到特定人的User
     * @param tempID
     * @return
     */
    public boolean setstrList(String tempID){
        User user = new User();
        user.setStudentID(tempID);
        Gson gson = new Gson();
        String s = gson.toJson(user);
        passer.send(new Message("admin", s, "login", "getone"));

        Message msg = passer.receive();
        Map<String, java.util.List<User>> map = new Gson().fromJson(msg.getData(),
                new TypeToken<HashMap<String, java.util.List<User>>>(){}.getType());
        List<User> res = map.get("res");
        User tempU=res.get(0);
        if(res.size()!=0){
            strList[0]=tempU.getStudentID();
            strList[1]=tempU.getPassword();
            switch (tempU.getType()) {
                case (1): strList[2] = "学生";break;
                case (2): strList[2] = "教师";break;
                case (3): strList[2] = "管理员";break;
            }
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * 用户管理-包括表格
     * @param jp
     * @param layout_Spring
     * @param layout_Card
     */
    public void setjp1(JPanel jp,SpringLayout layout_Spring,CardLayout layout_Card){
        setSTRList();//获取所有用户信息
        //jp1内容
        //标题
        JLabel lbl = new JLabel("用户信息管理");
        jp1.add(lbl);
        lbl.setFont(new Font("宋体", Font.BOLD, 33));
        layout_Spring.putConstraint(layout_Spring.NORTH, lbl, 30, layout_Spring.NORTH, jp1);  //标签1北侧——>容器北侧
        layout_Spring.putConstraint(layout_Spring.WEST, lbl, 20+20, layout_Spring.WEST, jp1);    //标签1西侧——>容器西侧
        // 创建注册按钮
        JButton btn1=new JButton("添加用户");
        jp1.add(btn1);
        btn1.setFont(new Font("宋体", Font.BOLD, 20));
        layout_Spring.putConstraint(layout_Spring.NORTH, btn1, 35, layout_Spring.NORTH, jp1);
        layout_Spring.putConstraint(layout_Spring.EAST, btn1, -30, layout_Spring.EAST, jp1);
        //创建搜索按钮
        JButton btn2=new JButton("搜索");
        jp1.add(btn2);
        btn2.setFont(new Font("宋体", Font.BOLD, 20));
        layout_Spring.putConstraint(layout_Spring.NORTH, btn2, 35, layout_Spring.NORTH, jp1);
        layout_Spring.putConstraint(layout_Spring.EAST, btn2, -10, layout_Spring.WEST, btn1);
        //创建文本框
        JTextField text=new JTextField();
        jp1.add(text);
        text.setEditable(true);//可以编辑
        text.setFont(new Font("宋体", Font.PLAIN, 20));//文本框字体
        text.setPreferredSize(new Dimension (300,30));
        layout_Spring.putConstraint(layout_Spring.NORTH, text, 35, layout_Spring.NORTH, jp1);
        layout_Spring.putConstraint(layout_Spring.EAST, text, -10, layout_Spring.WEST, btn2);

        //创建信息表格
//        String[] columnNames = {"一卡通号", "密码","权限","操作"}; // 定义表格列名数组
        String[] columnNames = {"一卡通号", "密码","权限"}; // 定义表格列名数组
        // 定义表格数据数组
        String[][] tableValues = new String[STRList.length][4];
        for(int i=0;i<STRList.length;i++){
            System.arraycopy(STRList[i], 0, tableValues[i], 0, 3);
            //tableValues[i][3]=i+"";
        }
        // 创建指定列名和数据的表格
        JTable table = new JTable(tableValues, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {  // 表格不可编辑---
                return false;
            }
        };
        //表格样式设置
        table.setRowHeight(30);// 设置行高
        table.setFont(new Font("黑体",Font.PLAIN,18));//表格字体
        table.getTableHeader().setFont(new Font("宋体",Font.BOLD,20));//表头字体
        //table.getColumnModel().getColumn(3).setCellRenderer(new MyButtonRender(jp,table));//添加按钮
        table.getTableHeader().setReorderingAllowed(false);//不允许拖动列头，以重新排序各列
        table.getTableHeader().setResizingAllowed(false);//不允许手动拖动来调整各列的大小
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class,tcr);

        // 创建显示表格的滚动面板
        JScrollPane scrollPane = new JScrollPane(table);
        jp1.add(scrollPane);
        layout_Spring.putConstraint(layout_Spring.NORTH, scrollPane, 30, layout_Spring.SOUTH, lbl);
        layout_Spring.putConstraint(layout_Spring.WEST, scrollPane, 40, layout_Spring.WEST, jp1);
        layout_Spring.putConstraint(layout_Spring.SOUTH, scrollPane, -20, layout_Spring.SOUTH, jp1);
        layout_Spring.putConstraint(layout_Spring.EAST, scrollPane, -30, layout_Spring.EAST, jp1);

        //监听事件
        //搜索按钮
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String info = text.getText().trim();
                if (!"".equals(text.getText().trim())) {//info为纯数字：info.chars().allMatch(Character::isDigit)
                    if (setstrList(info)) {//设置srtList
                        text.setText("");//清空搜索框
                        layout_Card.next(jp);//跳转到jp2
                        setjp2(jp, layout_Spring, layout_Card);//加载jp2
                    }
                    else
                        JOptionPane.showMessageDialog(
                                jp1,
                                "查无此人！",
                                "警告",
                                JOptionPane.ERROR_MESSAGE
                        );
                    System.out.println("用户管理系统-用户管理-个人信息搜索");
                }
                else{
                    JOptionPane.showMessageDialog(
                            jp1,
                            "输入框不能为空",
                            "警告",
                            JOptionPane.ERROR_MESSAGE
                    );
                    text.setText("");
                }
            }
        });
        //注册按钮
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout_Card.last(jp);//转入jp4
                setjp4(jp,layout_Spring,layout_Card);//加载setjp4
                System.out.println("用户管理系统-用户管理-注册");
            }
        });

    }
    public void setjp2(JPanel jp,SpringLayout layout_Spring,CardLayout layout_Card){
        //jp2内容
        //标题
        JLabel lbl2 = new JLabel("信息查询");
        jp2.add(lbl2);
        lbl2.setFont(new Font("宋体", Font.BOLD, 30));
        layout_Spring.putConstraint(layout_Spring.NORTH, lbl2, 20+100+20, layout_Spring.NORTH, jp2);  //标签1北侧——>容器北侧
        layout_Spring.putConstraint(layout_Spring.WEST, lbl2, 20+100+160, layout_Spring.WEST, jp2);    //标签1西侧——>容器西侧
        //按钮
        JButton btn1=new JButton("编辑");
        JButton btn2=new JButton("返回");
        if(!strList[2].equals("管理员"))
            jp2.add(btn1);//管理员没有编辑按钮
        jp2.add(btn2);
        btn1.setFont(new Font("宋体", Font.BOLD, 20));
        btn2.setFont(new Font("宋体", Font.BOLD, 20));
        layout_Spring.putConstraint(layout_Spring.NORTH, btn2, 400, layout_Spring.NORTH, jp2);//返回按钮
        layout_Spring.putConstraint(layout_Spring.EAST, btn2, -357, layout_Spring.EAST, jp2);
        layout_Spring.putConstraint(layout_Spring.NORTH, btn1, 400, layout_Spring.NORTH, jp2);//编辑按钮
        layout_Spring.putConstraint(layout_Spring.EAST, btn1, -30, layout_Spring.WEST, btn2);
        //信息列表：
        //标签
        JLabel[] lblList={
                new JLabel("一卡通号"),
                new JLabel("密码"),
                new JLabel("权限")
        };
        for(int i=0;i<lblList.length;i++){
            JLabel lbli=lblList[i];
            jp2.add(lbli);
            lbli.setFont(new Font("宋体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, lbli, 60+50*i, layout_Spring.SOUTH, lbl2);  //标签1北侧——>容器北侧
            layout_Spring.putConstraint(layout_Spring.EAST, lbli, 50, layout_Spring.EAST, lbl2);    //标签1西侧——>容器西侧
        }
        //文本框
        //JTextField[] textList=new JTextField[5];
        JTextField[] textList={
                new JTextField(),new JTextField(),
                new JTextField()};
        for(int i=0;i< textList.length;i++){
            textList[i].setText(strList[i]);
        }
        for(int i=0;i<textList.length;i++){
            JTextField texti=textList[i];
            jp2.add(texti);
            texti.setEditable(false);
            texti.setFont(new Font("宋体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, texti, 0, layout_Spring.NORTH, lblList[i]);
            layout_Spring.putConstraint(layout_Spring.WEST, texti, 20, layout_Spring.EAST, lblList[i]);
            texti.setColumns(30);
        }

        //编辑按钮监听
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout_Card.next(jp);//转入jp3
                setjp3(jp,layout_Spring,layout_Card);
                System.out.println("用户管理系统-个人信息-修改编辑确认");
            }
        });
        //返回按钮监听
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout_Card.first(jp);//转到jp1
                jp1.removeAll();
                setjp1(jp,layout_Spring,layout_Card);//重新加载jp1
                jp1.repaint();
                System.out.println("用户管理系统-个人信息-修改编辑取消");
            }
        });
    }
    public void setjp3(JPanel jp,SpringLayout layout_Spring,CardLayout layout_Card){
        //jp3内容 信息编辑，注销
        //标题
        JLabel lbl3 = new JLabel("信息编辑");
        jp3.add(lbl3);
        lbl3.setFont(new Font("宋体", Font.BOLD, 30));
        layout_Spring.putConstraint(layout_Spring.NORTH, lbl3, 20+100+20, layout_Spring.NORTH, jp3);  //标签1北侧——>容器北侧
        layout_Spring.putConstraint(layout_Spring.WEST, lbl3, 20+100+160, layout_Spring.WEST, jp3);    //标签1西侧——>容器西侧
        //按钮
        JButton btn3=new JButton("确认");
        JButton btn4=new JButton("取消");
        JButton btn5=new JButton("注销该用户");
        jp3.add(btn3);jp3.add(btn4);jp3.add(btn5);
        btn3.setFont(new Font("宋体", Font.BOLD, 20));
        btn4.setFont(new Font("宋体", Font.BOLD, 20));
        btn5.setFont(new Font("宋体", Font.BOLD, 20));
        layout_Spring.putConstraint(layout_Spring.NORTH, btn4, 400, layout_Spring.NORTH, jp3);//取消按钮
        layout_Spring.putConstraint(layout_Spring.EAST, btn4, -355, layout_Spring.EAST, jp3);
        layout_Spring.putConstraint(layout_Spring.NORTH, btn3, 400, layout_Spring.NORTH, jp3);//确认按钮
        layout_Spring.putConstraint(layout_Spring.EAST, btn3, -30, layout_Spring.WEST, btn4);
        layout_Spring.putConstraint(layout_Spring.SOUTH, btn5, 400, layout_Spring.SOUTH, jp3);//注销
        layout_Spring.putConstraint(layout_Spring.EAST, btn5, -20, layout_Spring.EAST, jp3);

        //信息列表：
        //标签
        JLabel[] lblList={
                new JLabel("一卡通号"),
                new JLabel("密码"),
                new JLabel("权限")
        };
        for(int i=0;i<lblList.length;i++){
            JLabel lbli=lblList[i];
            jp3.add(lbli);
            lbli.setFont(new Font("宋体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, lbli, 60+50*i, layout_Spring.SOUTH, lbl3);  //标签1北侧——>容器北侧
            layout_Spring.putConstraint(layout_Spring.EAST, lbli, 50, layout_Spring.EAST, lbl3);    //标签1西侧——>容器西侧
        }
        //文本框
        JTextField[] textList={
                new JTextField(),new JTextField(),
                new JTextField()};
        for(int i=0;i< textList.length;i++){
            textList[i].setText(strList[i]);
        }

        for(int i=0;i<textList.length;i++){
            JTextField texti=textList[i];
            jp3.add(texti);
            texti.setEditable(true);
            texti.setFont(new Font("宋体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, texti, 0, layout_Spring.NORTH, lblList[i]);
            layout_Spring.putConstraint(layout_Spring.WEST, texti, 20, layout_Spring.EAST, lblList[i]);
            texti.setColumns(30);
            strList[i]=texti.getText().trim();//获取文本框内信息
        }
        textList[1].setEditable(true);

        //确认按钮监听
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!textList[1].getText().trim().contains(" ")
                        && !textList[1].getText().trim().equals("")
                        && textList[1].getText().trim()!=null){//如果密码中不包含空格,不为“”,不为空
                    int t=0;
                    String textPower=textList[2].getText().trim();
                    switch (textPower) {
                        case ("学生"): t = 1;break;
                        case ("教师"): t = 2;break;
                        case ("管理员"): t = 3;break;
                    }
                    SendTnfo_Pass(textList[0].getText().trim(),textList[1].getText().trim(),t,"Change Password");//修改密码
                    jp2.removeAll();
                    setstrList(textList[0].getText().trim());//刷新数组
                    setjp2(jp,layout_Spring,layout_Card);//重新加载jp2
                    jp2.repaint();//重新绘制jp2
                    layout_Card.previous(jp);//返回前一页，jp2
                    System.out.println("用户管理系统-用户管理-修改编辑确认");
                }
                else {
                    JOptionPane.showConfirmDialog(
                            jp3,
                            "密码中不能包含空格",
                            "提示",
                            JOptionPane.YES_NO_CANCEL_OPTION
                    );
                }
            }
        });
        //取消按钮监听
        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout_Card.previous(jp);
                setjp2(jp,layout_Spring,layout_Card);//重新加载jp2
                System.out.println("用户管理系统-用户管理-修改编辑取消");
            }
        });
        //注销按钮监听
        btn5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        jp3,
                        "确认注销该用户？",
                        "提示",
                        JOptionPane.YES_NO_CANCEL_OPTION
                );
                System.out.println("选择结果: " + result);
                if(result==JOptionPane.YES_OPTION){
                    int t=0;
                    switch (textList[2].getText().trim()){
                        case("学生"):t=1;break;
                        case("教师"):t=2;break;
                        case("管理员"):t=3;break;
                    }
                    SendTnfo_Pass(textList[0].getText().trim(),textList[1].getText().trim(),t,"delete");//注销用户
                    layout_Card.first(jp);//返回第一页
                    jp1.removeAll();
                    setjp1(jp,layout_Spring,layout_Card);//重新加载第一页
                    jp1.repaint();
                    System.out.println("用户管理系统-用户管理-注销用户");
                }
            }
        });
    }
    public void setjp4(JPanel jp,SpringLayout layout_Spring,CardLayout layout_Card){
        //jp4内容-注册
        //标题
        JLabel lbl4 = new JLabel("注册新用户");
        jp4.add(lbl4);
        lbl4.setFont(new Font("宋体", Font.BOLD, 30));
        layout_Spring.putConstraint(layout_Spring.NORTH, lbl4, 20+100, layout_Spring.NORTH, jp4);  //标签1北侧——>容器北侧
        layout_Spring.putConstraint(layout_Spring.WEST, lbl4, 20+100+180, layout_Spring.WEST, jp4);    //标签1西侧——>容器西侧
        //语句
        JLabel lbltext = new JLabel("·请输入新用户的信息：");
        jp4.add(lbltext);
        lbltext.setFont(new Font("宋体", Font.BOLD, 25));
        layout_Spring.putConstraint(layout_Spring.NORTH, lbltext, 30, layout_Spring.SOUTH, lbl4);  //标签1北侧——>容器北侧
        layout_Spring.putConstraint(layout_Spring.WEST, lbltext, 60, layout_Spring.WEST, lbl4);
        //按钮
        JButton btn1=new JButton("确认");
        JButton btn2=new JButton("取消");
        jp4.add(btn1);jp4.add(btn2);
        btn1.setFont(new Font("宋体", Font.BOLD, 20));
        btn2.setFont(new Font("宋体", Font.BOLD, 20));
        layout_Spring.putConstraint(layout_Spring.NORTH, btn2, 430, layout_Spring.NORTH, jp4);//取消按钮
        layout_Spring.putConstraint(layout_Spring.EAST, btn2, -450, layout_Spring.EAST, jp4);
        layout_Spring.putConstraint(layout_Spring.NORTH, btn1, 430, layout_Spring.NORTH, jp4);//确认按钮
        layout_Spring.putConstraint(layout_Spring.EAST, btn1, -30, layout_Spring.WEST, btn2);
        //信息列表：
        //标签
        JLabel[] lblList={
                new JLabel("一卡通号"),
                new JLabel("密码"),/*默认密码123456*/
                new JLabel("身份")
        };
        for(int i=0;i<lblList.length;i++){
            JLabel lbli=lblList[i];
            jp4.add(lbli);
            lbli.setFont(new Font("宋体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, lbli, 40+50*i, layout_Spring.SOUTH, lbltext);  //标签1北侧——>容器北侧
            layout_Spring.putConstraint(layout_Spring.EAST, lbli, 50, layout_Spring.EAST, lbl4);
        }
        //文本框
        //JTextField[] textList=new JTextField[5];
        JTextField[] textList={new JTextField(),new JTextField(), new JTextField()};
        for(int i=0;i<textList.length;i++){
            JTextField texti=textList[i];
            jp4.add(texti);
            texti.setEditable(true);
            texti.setFont(new Font("黑体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, texti, 0, layout_Spring.NORTH, lblList[i]);
            layout_Spring.putConstraint(layout_Spring.WEST, texti, 20, layout_Spring.EAST, lblList[i]);
            texti.setColumns(20);
        }
        textList[1].setText("123456");//默认密码
        textList[1].setEditable(false);
        JLabel lbl_pass=new JLabel("*默认密码");//默认密码标签
        jp4.add(lbl_pass);
        lbl_pass.setFont(new Font("宋体", Font.BOLD, 20));
        layout_Spring.putConstraint(layout_Spring.NORTH, lbl_pass, 0, layout_Spring.NORTH, textList[1]);  //标签1北侧——>容器北侧
        layout_Spring.putConstraint(layout_Spring.WEST, lbl_pass, 20, layout_Spring.EAST, textList[1]);

        //确认按钮监听
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textID=textList[0].getText().trim();
                String textPower=textList[2].getText().trim();
                if(!Objects.equals(textID, "") && textID.chars().allMatch(Character::isDigit)) {
                    if (textPower.equals("学生") || textPower.equals("教师") || textPower.equals("管理员")) {
                        int result = JOptionPane.showConfirmDialog(
                                jp4,
                                "确认注册该用户？",
                                "提示",
                                JOptionPane.YES_NO_OPTION
                        );
                        System.out.println("选择结果: " + result);
                        if (result == JOptionPane.YES_OPTION) {
                            int t =0;
                            switch (textList[2].getText().trim()) {
                                case ("学生"): t=1;break;
                                case ("教师") : t=2;break;
                                case ("管理员") :t=3;break;
                            };
                            SendInfo_register(textList[0].getText().trim(), textList[1].getText().trim(), t);
                            layout_Card.first(jp);//返回jp1
                            jp1.removeAll();
                            setjp1(jp,layout_Spring,layout_Card);//重新加载jp1
                            jp1.repaint();//重新绘制
                            System.out.println("用户管理系统-用户管理-注册确认");
                        }
                    }
                    else {
                        if(textPower.equals("")||textPower.contains(" "))
                            JOptionPane.showConfirmDialog(jp3, "权限不能为空", "警告",
                                    JOptionPane.WARNING_MESSAGE);
                        else JOptionPane.showConfirmDialog(jp3, "权限只能为：“学生”or“教师”or“管理员”", "警告",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
                else {
                    if(Objects.equals(textID, ""))
                        JOptionPane.showConfirmDialog(jp3, "一卡通号不能为空", "警告",
                                JOptionPane.WARNING_MESSAGE);
                    else JOptionPane.showConfirmDialog(jp3, "一卡通号必须为纯数字", "警告",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        //取消按钮监听
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout_Card.first(jp);//返回jp1
                //jp1.removeAll();
                //setjp1(jp,layout_Spring,layout_Card);//重新加载jp1
                //jp1.repaint();
                System.out.println("用户管理系统-用户管理-注册取消");
            }
        });
    }

    public boolean SendTnfo_Pass(String id,String pass,int t,String operation){
        User user=new User();
        user.setStudentID(id);
        user.setPassword(pass);
        user.setType(t);
        Gson gson = new Gson();
        String s = gson.toJson(user);
        passer.send(new Message("admin", s, "login", operation));

        //接收信息是否传递成功
        Message msg = passer.receive();
        Map<String, Object> map = new Gson().fromJson(msg.getData(),
                new TypeToken<HashMap<String, Object>>(){}.getType());

        JPanel temjp=(operation.equals("Change Password"))?jp2:jp3;
        if(map.get("res").equals("OK")) {
            String strText=(operation.equals("Change Password"))?"编辑成功":"注销成功";
            JOptionPane.showMessageDialog(
                    temjp,
                    strText,
                    " ",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return true;
        }
        else{
            String strText=operation.equals("Change Password")?"编辑失败":"注销失败";
            JOptionPane.showMessageDialog(
                    temjp,
                    strText,
                    "ERROR",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
    }
    public boolean SendInfo_register(String id,String pass,int t){
        User user=new User();
        user.setStudentID(id);
        user.setPassword(pass);
        user.setType(t);
        Gson gson = new Gson();
        String s = gson.toJson(user);
        passer.send(new Message("admin", s, "login", "register"));

        //接收信息是否传递成功
        Message msg = passer.receive();
        Map<String, Object> map = new Gson().fromJson(msg.getData(),
                new TypeToken<HashMap<String, Object>>(){}.getType());
        if(map.get("res").equals("OK")) {
            JOptionPane.showMessageDialog(
                    jp4,
                    "注册成功",
                    " ",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return true;
        }
        else{
            JOptionPane.showMessageDialog(
                    jp4,
                    "注册失败：该用户已存在",
                    "ERROR",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
    }


    public class MyButtonRender implements TableCellRenderer {
        public JPanel jp;
        public JTable table;
        private JPanel jPanel;
        private JButton btn1,btn2;

        public MyButtonRender(JPanel tempjp,JTable tempTbl) {
            jp=tempjp;
            table=tempTbl;
            initJPanel();
            initButton();
            jPanel.add(btn1);
            jPanel.add(btn2);
        }

        private void initButton() {
            SpringLayout layout_Spring = new SpringLayout();
            CardLayout layout_Card=new CardLayout();
            btn1 = new JButton();
            btn2 = new JButton();
            btn1.setBounds(10, 1, 100, 30-4);
            btn2.setBounds(20+100, 1, 100, 30-4);
            btn1.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            layout_Card.show(jp,"jp2");
                            //获取选中的行
                            int index1 = table.getSelectedRow();//获取选中的行
                            String value= (String) table.getModel().getValueAt(index1, 0);//一卡通号
                            //数据库数据获取
                            //重新加载jp2
                            setjp2(jp,layout_Spring,layout_Card);
                            System.out.println("" + e.getActionCommand());
                            System.out.println(btn1.getText());
                        }
                    });
            btn2.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            layout_Card.show(jp,"jp3");
                            //获取选中的行
                            int index1 = table.getSelectedRow();//获取选中的行
                            String value= (String) table.getModel().getValueAt(index1, 0);//一卡通号
                            //数据库数据获取
                            //重新加载jp3
                            setjp3(jp,layout_Spring,layout_Card);
                            System.out.println("" + e.getActionCommand());
                            System.out.println(btn2.getText());
                        }
                    });
        }

        private void initJPanel() {
            jPanel = new JPanel();
            jPanel.setLayout(null);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            btn1.setText("编辑");
            btn2.setText("查看");
            return jPanel;
        }
    }
}
