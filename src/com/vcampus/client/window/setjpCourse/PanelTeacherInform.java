/** ===================================================
 * Title: PanelTeacherInform.java
 * Created: [2022-8-26 10:49:27] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 教务管理——教师信息界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-26,创建此文件
 *2. 2022-8-26,完善设置
 *3.
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window.setjpCourse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.client.window.showMessageFrame;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Student;
import com.vcampus.pojo.Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教师个人身份信息界面
 * @author 韩宇
 * @date 2022/08/26
 */
public class PanelTeacherInform extends JPanel{
    int x=250,y=160;//起始坐标
    int lblWidth=300,lblHeight=40,txtWidth=500, txtHeight=40;
    int heightDiffer=60;//上下两行高度差
    int ltDiffer1=180;//1-左起两列标签文本框间隔 2-第三列标签文本框间隔
    int btnWidth = 80, btnHeight = 30;

    JLabel lblHint = new JLabel("教师身份信息");
    JLabel lblName = new JLabel("姓名       ");
    JTextField txtName = new JTextField();
    JLabel lblIdNum = new JLabel("一卡通号   ");
    JTextField txtIdNum = new JTextField();
    JLabel lblDep = new JLabel("学院       ");
    JTextField txtDep = new JTextField();
    JLabel lblMajor = new JLabel("专业      ");
    JTextField txtMajor = new JTextField();
    JLabel lblTime = new JLabel("非偏好时间  ");
    JTextField txtTime = new JTextField();
    JLabel lblHintTip = new JLabel("提示：输入格式 “周数/开始节数/结束节数”,示例：周三1-2节 - “3/1/2”");

    JButton btnEdit = new JButton("编辑");
    JButton btnOk = new JButton("确定");
    JButton btnCancel = new JButton("取消");

    JLabel labels[] = {lblName,lblIdNum,lblDep,lblMajor,lblTime};
    JTextField texts[] = {txtName,txtIdNum,txtDep,txtMajor,txtTime};

    CardLayout cardLayout=new CardLayout();
    MessagePasser passer = ClientMessagePasser.getInstance();
    Teacher teacher = new Teacher();//承接结果
    /**
     * 构造函数，控件设置及按钮响应函数、信息传递
     * @param ID 教师一卡通号
     */
    public PanelTeacherInform(String ID)//以老师对象为参数
    {
        this.setLayout(cardLayout);
        JPanel P1 = new JPanel();
        JPanel P2 = new JPanel();
        P1.setLayout(null);
        P2.setLayout(null);

        lblHint.setBounds(x+260,80,lblWidth,lblHeight);
        lblHint.setFont(new Font("宋体",Font.BOLD, 28));
        lblHintTip.setBounds(x+30,445,lblWidth*3,lblHeight);
        lblHintTip.setFont(new Font("宋体",Font.BOLD, 18));
        setButtonFont(btnOk);
        btnOk.setBounds(520-50,510,btnWidth,btnHeight);
        setButtonFont(btnCancel);
        btnCancel.setBounds(760-50,510,btnWidth,btnHeight);
        setButtonFont(btnEdit);
        btnEdit.setBounds(1000-50,y,btnWidth,btnHeight);
        P1.add(btnEdit);P1.add(lblHint);
        P2.add(btnOk);P2.add(btnCancel);P2.add(lblHint);
        P2.add(lblHintTip);

        this.add(P1,"P1");
        this.add(P2,"P2");

        teacher.setTeacherID(ID);
        Gson gson = new Gson();
        String s = gson.toJson(teacher);
        passer.send(new Message("teacher", s, "lesson", "showstatussteacher"));
        try { Thread.sleep(100); } catch (InterruptedException interruptedException) { interruptedException.printStackTrace(); }
        Message msg = passer.receive();
        Map<String, java.util.List<Teacher>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Teacher>>>(){}.getType());
        List<Teacher> res = map.get("res");
        System.out.println(res);
        if(res.size()!=0) {
            teacher= res.get(0);
            setCard("P1");
            setPanel(P1,false);
        }
        else {
            showMessageFrame test=new showMessageFrame("未查询到身份信息!",900,320,460, 80,1);

//            JOptionPane.showMessageDialog(this, "未查询到身份信息", "警告", JOptionPane.ERROR_MESSAGE);
            }

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCard("P2");
                setPanel(P2,true); }});
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                teacher.setTeacherName(txtName.getText());
                teacher.setSchool(txtDep.getText());
                teacher.setAbledMajor(txtMajor.getText());
                teacher.setTime(txtTime.getText());

                Gson gson = new Gson();
                String s = gson.toJson(teacher);
                passer.send(new Message("teacher", s, "lesson", "setteacher"));
                try { Thread.sleep(100); } catch (InterruptedException interruptedException) { interruptedException.printStackTrace(); }
                Message msg = passer.receive();
                System.out.println(msg);
                Map<String,Object> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String,Object>>(){}.getType());
                if(map.get("res").equals("OK"))
                {
//                    informFrame("修改成功",false);
                    new showMessageFrame("修改成功!",900,320,460, 80,2);

                    setCard("P1");
                    setPanel(P1,false);
                } else {
                    new showMessageFrame("修改失败!",900,320,460, 80,1);

//                    informFrame("修改失败",true);
                } }});
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCard("P1");
                setPanel(P1,false); }});
    }
    /**
     * 设置卡片翻页
     * @param card 需要展示的卡片对应名称
     */
    public void setCard(String card)
    {
        cardLayout.show(this,card);
    }
    /**
     *提示窗口
     * @param title 标题
     * @param flag  true-警告窗口 false-提示窗口
     */
    public void informFrame(String title,Boolean flag)
    {
        if(flag) {
            JOptionPane.showMessageDialog(this, title, "警告", JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(this, title, "提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /**
     * 设置标签和文本框字体
     * @param label 标签
     * @param text  文本框
     */
    public void setLabelFont(JLabel label,JTextField text)
    {   label.setFont(new Font("宋体", Font.BOLD, 24));
        text.setFont(new Font("宋体", Font.BOLD, 20)); }
    /**
     * 设置教师个人身份信息界面
     * @param p    需要设置的面板
     * @param flag 文本框是否可编辑
     */
    public void setPanel(JPanel p,Boolean flag)//传老师的对象
    {
        txtName.setText(teacher.getTeacherName());//姓名
        txtIdNum.setText(teacher.getTeacherID());//一卡通
        txtDep.setText(teacher.getSchool());//学院
        txtMajor.setText(teacher.getAbledMajor());//专业
        txtTime.setText(teacher.getTime());//时间（可能要转格式）
        for(int i=0;i<5;i++)
        {   labels[i].setBounds(x,y+heightDiffer*i,lblWidth,lblHeight);
            texts[i].setBounds(x+ltDiffer1,y+heightDiffer*i,txtWidth,txtHeight);
            setLabelFont(labels[i],texts[i]);
            p.add(labels[i]); p.add(texts[i]);
            texts[i].setEditable(flag); }
        txtIdNum.setEditable(false);
        p.add(lblHint);
        p.updateUI();p.repaint();
    }
    /**
     * 设置按钮字体
     * @param button 按钮
     */
    public void setButtonFont(JButton button)
    {   button.setFont(new Font("宋体",Font.BOLD, 18));
        button.setContentAreaFilled(false); }
}
