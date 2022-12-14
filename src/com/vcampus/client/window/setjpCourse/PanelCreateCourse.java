/** ===================================================
 * Title: TabbedCreatCourse.java
 * Created: [2022-8-26 17:06:11] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 课程创建-管理员界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-26,创建此文件
 *2. 2022-8-27,完善设置   修改人：韩宇
 *3. 2022-8-28,前后端连接 修改人：韩宇
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window.setjpCourse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.client.window.showMessageFrame;
import com.vcampus.dao.utils.StringAndImage;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Book;
import com.vcampus.pojo.Lesson;
import com.vcampus.pojo.Student;
import com.vcampus.pojo.Teacher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 创建课程面板
 * 创建课程面板采用卡片布局，共设三张卡片，第一张用于填充课程基本信息，第二张用表格显示可选老师，第三张用表格显示可选教室并完成课程创建
 * @author 韩宇
 * @date 2022/08/26
 */
public class PanelCreateCourse extends JPanel{
    int x=300,y=120;//起始坐标
    int lblWidth=300,lblHeight=40,txtWidth=500, txtHeight=40,btnWidth = 80, btnHeight = 30;
    int heightDiffer=60, llDiffer1=330,ltDiffer1=180;;//0-上下两行高度差 1-左起两列标签文本框间隔 2-第三列标签文本框间隔
    JLabel lblHint1 = new JLabel();//填写课程信息-绿
    JLabel lblHint2 = new JLabel();//选择任课老师-绿
    JLabel lblHint3 = new JLabel();//选择上课教室-绿
    //JLabel lblHint1 = new JLabel("填写课程信息");//填写课程信息-蓝
    //JLabel lblHint2 = new JLabel("选择任课老师");//选择任课老师-蓝
    //JLabel lblHint3 = new JLabel("选择上课教室");//选择上课教室-蓝
    ImageIcon img1 = new ImageIcon("Vcampus//img//1.png");// 这是背景图片 .png .jpg .gif 等格式的图片都可以
    ImageIcon img2 = new ImageIcon("Vcampus//img//2.png");
//    Vcampus/
    ImageIcon img3 = new ImageIcon("Vcampus//img//3.png");

    JLabel lblName = new JLabel("课程名称");JTextField txtName = new JTextField();
    JLabel lblIdNum = new JLabel("课程编号");JTextField txtIdNum = new JTextField();
    JLabel lblDep = new JLabel("开课学院");JTextField txtDep = new JTextField();
    JLabel lblMajor = new JLabel("所属专业");JTextField txtMajor = new JTextField();
    JLabel lblTime = new JLabel("授课时间");JTextField txtTime = new JTextField();
    JLabel lblSize = new JLabel("课程容量");JTextField txtSize = new JTextField();
    JLabel lblExam = new JLabel("是否安排考试");
    JRadioButton radioBtn01 = new JRadioButton("是");JRadioButton radioBtn02 = new JRadioButton("否");
    JPanel panelCourse = new JPanel(); JPanel panelTeacher = new JPanel(); JPanel panelRoom = new JPanel();//设置三张卡片Panel
    JButton btnNext1 = new JButton("下一步");JButton btnNext2 = new JButton("下一步");//设置所有按钮
    JButton btnReturn1 = new JButton("返回");JButton btnReturn2 = new JButton("返回");
    JButton btnFinish = new JButton("完成创建");
    ButtonGroup btnGroup = new ButtonGroup();
    JLabel labels[] = {lblName,lblIdNum,lblDep,lblMajor,lblTime,lblSize,lblExam};
    JTextField texts[] = {txtName,txtIdNum,txtDep,txtMajor,txtTime,txtSize};
    CardLayout cardLayout=new CardLayout();//布局 编辑器 表格设置
    ButtonEditor btnE = null;
    MyTable table1=null;MyTable table2=null;//教室
    JScrollPane scrollPane1 = null;JScrollPane scrollPane2 = null;
    JPanel tablePanel1 = new JPanel();JPanel tablePanel2 = new JPanel();
    Object[] columnNames1={"教师姓名", "一卡通号","学院","专业","操作"};Object[] columnNames2={"教室","操作"};
    Object[][] tableData1=new Object[][]{};Object[][] tableData2=new Object[][]{};
    Lesson L = new Lesson();MessagePasser passer = ClientMessagePasser.getInstance();
    /**
     * 构造函数，设置所有控件位置及按钮响应
     */
    public PanelCreateCourse()
    {
        this.setLayout(cardLayout);
        panelCourse.setLayout(null);panelTeacher.setLayout(null);panelRoom.setLayout(null);

        img1.setImage(img1.getImage().getScaledInstance(250,40,Image.SCALE_DEFAULT));//这里设置图片大小，目前是20*20
        lblHint1.setIcon(img1);
        img2.setImage(img2.getImage().getScaledInstance(250,40,Image.SCALE_DEFAULT));//这里设置图片大小，目前是20*20
        lblHint2.setIcon(img2);
        img3.setImage(img3.getImage().getScaledInstance(250,40,Image.SCALE_DEFAULT));//这里设置图片大小，目前是20*20
        lblHint3.setIcon(img3);

        lblHint1.setBounds(180,40,250,40);//lblHint1.setFont(new Font("宋体",Font.BOLD, 28));
        lblHint2.setBounds(180+llDiffer1,40,250,40);//lblHint2.setFont(new Font("宋体",Font.BOLD, 28));
        lblHint3.setBounds(180+llDiffer1*2,40,250,40);//lblHint3.setFont(new Font("宋体",Font.BOLD, 28));
        setButtonFont(btnNext1);btnNext1.setBounds(760,520,btnWidth*2,btnHeight);
        setButtonFont(btnNext2);btnNext2.setBounds(760+40,520,btnWidth*2,btnHeight);
        setButtonFont(btnFinish);btnFinish.setBounds(760,520,btnWidth*2,btnHeight);
        setButtonFont(btnReturn1);btnReturn1.setBounds(1000+40,520,btnWidth,btnHeight);
        setButtonFont(btnReturn2);btnReturn2.setBounds(1000,520,btnWidth,btnHeight);
        radioBtn01.setBounds(500,487,80,25);radioBtn01.setFont(new Font("宋体",Font.BOLD, 18));
        radioBtn02.setBounds(600,487,80,25);radioBtn02.setFont(new Font("宋体",Font.BOLD, 18));
        btnGroup.add(radioBtn01);btnGroup.add(radioBtn02);
        tablePanel1.setLayout(null);tablePanel1.setBounds(50,100,1200,400);
        tablePanel2.setLayout(null);tablePanel2.setBounds(330,100,600,400);
        for(int i=0;i<6;i++)//设置其余坐标和字体
        {   labels[i].setBounds(x,y+heightDiffer*i,lblWidth,lblHeight);
            texts[i].setBounds(x+ltDiffer1,y+heightDiffer*i,txtWidth,txtHeight);
            setLabelFont(labels[i],texts[i]);texts[i].setEditable(true); }
        labels[6].setFont(new Font("楷体", Font.BOLD, 24));labels[6].setBounds(x,y+heightDiffer*6,lblWidth,lblHeight);
        setPanel1();
        btnNext1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(txtName.getText().equals("")||txtIdNum.getText().equals("")||txtDep.getText().equals("")||txtMajor.getText().equals("")||txtTime.getText().equals("")))
                {   L.setName(txtName.getText());L.setLessonID(txtIdNum.getText());L.setSchool(txtDep.getText());
                    L.setMajor(txtMajor.getText());L.setTime(txtTime.getText());
                    L.setMaxSize(Integer.valueOf(txtSize.getText()));L.setLeftSize(Integer.valueOf(txtSize.getText()));
                    Lesson lesson=new Lesson();lesson.setMajor(txtMajor.getText());lesson.setTime(txtTime.getText());
                    Gson gson = new Gson();String s = gson.toJson(lesson);
                    passer.send(new Message("admin", s, "lesson", "showteachertime"));
                    try { Thread.sleep(100); } catch (InterruptedException interruptedException) { interruptedException.printStackTrace(); }
                    Message msg = passer.receive();
                    Map<String, java.util.List<Teacher>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Teacher>>>(){}.getType());
                    List<Teacher> res = map.get("res");
                    if(res.size()!=0)
                    {   tableData1 = new Object[res.size()][5]; //设置表格内容
                        for (int i = 0; i < res.size(); i++)
                        {   tableData1[i][0] = res.get(i).getTeacherName();
                            tableData1[i][1] = res.get(i).getTeacherID();
                            tableData1[i][2] = res.get(i).getSchool();
                            tableData1[i][3] = res.get(i).getAbledMajor(); }
                        setPanel2();setCard("panelTeacher"); }
                    else {informFrame("未查询到授课教师！",true); }
                } else {informFrame("请填写完整课程信息",true); } }});
        btnNext2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Lesson lesson=new Lesson();lesson.setTime(txtTime.getText());
                Gson gson = new Gson();String s = gson.toJson(lesson);
                passer.send(new Message("admin", s, "lesson", "showroom"));
                try { Thread.sleep(100); } catch (InterruptedException interruptedException) { interruptedException.printStackTrace(); }
                Message msg = passer.receive();
                Map<String, java.util.List<String>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<String>>>(){}.getType());
                List<String> res = map.get("res");
                if(res.size()!=0)//设置表格内容
                {   tableData2 = new Object[res.size()][2];
                    for (int i = 0; i < res.size(); i++)
                    {tableData2[i][0] = res.get(i); }
                    setPanel3();setCard("panelRoom"); }
                else {informFrame("未查询到空余教室！",true); }
            }
        });
        btnReturn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { setPanel1();setCard("panelCourse"); }});
        btnReturn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { setPanel2();setCard("panelTeacher"); }});
        btnFinish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if(!(L.getName().equals("")||L.getLessonID().equals("")||L.getMaxSize()==0||L.getTeacherID().equals("")||L.getSchool().equals("")|| L.getMajor().equals("")||L.getClassroom().equals("")||L.getTime().equals("")))
               {   L.setInnerID(L.getLessonID()+L.getTeacherID());L.setStatus(1);
                   Gson gson = new Gson();String s = gson.toJson(L);
                   passer.send(new Message("admin", s, "lesson", "addlesson"));
                   try { Thread.sleep(100); } catch (InterruptedException interruptedException) { interruptedException.printStackTrace(); }
                   Message msg = passer.receive();
                   Map<String,Object> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String,Object>>(){}.getType());
                   if(map.get("res").equals("OK"))
                   {   informFrame("创建成功",false);
                       setPanel1();setCard("panelCourse"); }
                   else {informFrame("创建失败",true); }
               } else {informFrame("课程信息不完善，无法创建！",true);} }});
        radioBtn01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { if(radioBtn01.isSelected()) { L.setIsExam(1); } }});
        radioBtn02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(radioBtn01.isSelected()) { L.setIsExam(0); } }});
        this.add(panelCourse,"panelCourse");this.add(panelTeacher,"panelTeacher");this.add(panelRoom,"panelRoom");
    }
    /**
     * 弹出提示窗口
     * @param title 提示内容
     * @param flag  true-警告窗口，false-提示窗口
     */
    public void informFrame(String title,Boolean flag)
    {
        if(flag) {
            new showMessageFrame(title,900,240,460, 80,1);

//            JOptionPane.showMessageDialog(this, title, "警告", JOptionPane.ERROR_MESSAGE);
        }
        else {
            new showMessageFrame(title,900,240,460, 80,1);

//            JOptionPane.showMessageDialog(this, title, "提示", JOptionPane.INFORMATION_MESSAGE);
        } }
    /**
     * 设置当前需要现实的卡片
     * @param card 卡片对应名称
     */
    public void setCard(String card) { cardLayout.show(this,card); }
    /**
     * 设置填写课程详情的卡片
     */
    public void setPanel1() {
        panelCourse.removeAll();panelCourse.add(lblHint1);panelCourse.add(radioBtn01);panelCourse.add(radioBtn02);
        for(int i=0;i<6;i++) { panelCourse.add(labels[i]); panelCourse.add(texts[i]); }
        panelCourse.add(lblExam);panelCourse.add(btnNext1);panelCourse.updateUI();panelCourse.repaint(); }
    /**
     * 设置选择授课老师的卡片
     */
    public void setPanel2() {
        panelTeacher.removeAll();panelTeacher.add(lblHint1);panelTeacher.add(lblHint2);panelTeacher.add(btnNext2);panelTeacher.add(btnReturn1);
        setTable(columnNames1,tableData1,tablePanel1,table1,scrollPane1,1100,400,1);
        panelTeacher.add(tablePanel1);panelTeacher.updateUI();panelTeacher.repaint(); }
    /**
     * 设置选择上课教室的卡片
     */
    public void setPanel3() {
        panelRoom.removeAll();panelRoom.add(lblHint1);panelRoom.add(lblHint2);panelRoom.add(lblHint3);panelRoom.add(btnReturn2);panelRoom.add(btnFinish);
        setTable(columnNames2,tableData2,tablePanel2,table2,scrollPane2,600,400,0);
        panelRoom.add(tablePanel2);panelRoom.updateUI();panelRoom.repaint(); }
    /**
     * 设置按钮字体及使按钮背景消失
     * @param button 需要设置的按钮
     */
    public void setButtonFont(JButton button) { button.setFont(new Font("宋体",Font.BOLD, 18));button.setContentAreaFilled(false); }
    /**
     * 设置标签和文本框字体
     * @param label 需要设置的标签
     * @param text  需要设置的文本
     */
    public void setLabelFont(JLabel label,JTextField text) { label.setFont(new Font("楷体", Font.BOLD, 24));text.setFont(new Font("楷体", Font.BOLD, 20)); }
    /**
     * 设置显示的表格
     * @param title      表格题头
     * @param data       表格数据
     * @param p          放置表格的面板
     * @param table      表格
     * @param scrollPane 滚动窗格
     * @param width      表格宽度
     * @param height     表格高度
     * @param flag       flag==1，从选择任课老师的表格里读取老师ID，否则从选择上课教室的表格里读取教室ID
     */
    public void setTable(Object[] title,Object[][] data,JPanel p,MyTable table, JScrollPane scrollPane,int width,int height,int flag)
    {
        DefaultTableModel model= new DefaultTableModel(data,title);//表格设置
        Integer[] tempInt=null;
        if(title.length==2) tempInt= new Integer[]{1};//教室
        else tempInt= new Integer[]{4};//老师
        table=new MyTable(model, tempInt);table.setRowSelectionAllowed(true);
        table.setRowHeight(30);table.setFont(new Font("黑体",Font.PLAIN,18));//设置行高 表格字体
        table.getTableHeader().setFont(new Font("黑体",Font.BOLD,20));//表头字体
        ButtonRenderer btnR=new ButtonRenderer("添加");table.getColumn("操作").setCellRenderer(btnR); //添加渲染器
        btnE =new ButtonEditor(new JCheckBox());btnE.getButton().setText("添加");table.getColumn("操作").setCellEditor(btnE);
        scrollPane = new JScrollPane(table);scrollPane.setBounds(0,0,width,height);p.add(scrollPane);
        btnE.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowName=btnE.getThisRow();
                if(flag==1) { L.setTeacherID((String)model.getValueAt(rowName, 1)); }
                else { L.setClassroom((String)model.getValueAt(rowName, 0)); }
                informFrame("添加成功",false);btnE.fireEditingStopped_1();model.removeRow(rowName); }});
        updateUI();repaint(); }
}
