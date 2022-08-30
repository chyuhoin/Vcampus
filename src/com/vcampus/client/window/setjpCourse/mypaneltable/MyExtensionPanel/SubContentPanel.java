/** ===================================================
 * Title: PanelStudentManage_T.java
 * Created: [2022-8-30  00:53:12] by  张星喆
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 教务系统-学生-选课-基于选课的小面板，存放每位老师的信息
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-30,创建此文件
 *2. 2022-8-30,完善设置
 *3.2022-8-30,前后端连接 修改人：张星喆
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window.setjpCourse.mypaneltable.MyExtensionPanel;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.client.window.setjpCourse.mypaneltable.RoundBorder;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Lesson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SubContentPanel extends JPanel{
    private JPanel superPanel=null;
    private JPanel thisPanel;

    //数据
    private String lessonID=null;//课程ID
    private Lesson mylesson=null;//当前课程
    private String studentID=null;//学生ID
    private Object[] Info=new Object[4];//教师-时间-最大人数-教师ID
    private int nowNum=0;//当前已选人数
    private int MaxNum=0;
    public String statusLesson="Optional";//判断课程状态，初始化为可选

    //控件
    private JLabel[] lblList=new JLabel[3];//信息
    private JTextField[] textList=new JTextField[3];//信息
    private JButton btn=new JButton("选择");//选课按钮
    private JButton btn2=new JButton("退选");//退课按钮
    private JLabel lblFlag=new JLabel();//状态标签1：已选、可选、已满
    private JLabel lblFlag2=new JLabel();//状态标签2：是否冲突

    MessagePasser passer = ClientMessagePasser.getInstance();

    /**
     * 构造函数
     * @param lesson
     * @param sID
     * @param info
     * @param supPanel
     */
    public SubContentPanel(Lesson lesson, String sID, Object[] info, JPanel supPanel){
        super();
        thisPanel=this;
        lessonID=lesson.getLessonID();
        mylesson=lesson;
        studentID=sID;
        Info=info;
        setSuperPanel(supPanel);
        //状态获取
        setJudgeLesson(lessonID,Info[3].toString(),sID);
        //panel设置
        createSubContent();
        this.setPreferredSize(new Dimension(superPanel.getPreferredSize().width/3,200));
    }
    public SubContentPanel(){
        super();
        thisPanel=this;
    }
    /**
     * 设置sub-panel
     * @anthor 张星喆
     * @since 20220828
     * @version 1.0
     */
    public void createSubContent(){
        SpringLayout layS=new SpringLayout();
        this.setLayout(layS);
        this.setBorder(new RoundBorder(Color.GRAY));

        //内容设置
        String[] strLbl=new String[]{"教师：","时间：","已选："};
        Object[] strText=new Object[]{"","",50};
        if(Info.length==4){
            System.arraycopy(Info, 0, strText, 0, Info.length-1);
            MaxNum=Integer.parseInt(Info[2].toString());//最大容量
        }

        //设置标签和文本框内容
        for(int i=0;i<lblList.length;i++){
            //标签
            lblList[i]=new JLabel(strLbl[i]);
            add(lblList[i]);
            if(i==0){
                layS.putConstraint(layS.NORTH,lblList[i],20,layS.NORTH,this);
                layS.putConstraint(layS.WEST,lblList[i],20,layS.WEST,this);
            }else {
                layS.putConstraint(layS.NORTH,lblList[i],10,layS.SOUTH,lblList[i-1]);
                layS.putConstraint(layS.WEST,lblList[i],0,layS.WEST,lblList[i-1]);
            }

            //文本框
            if(i==lblList.length-1)
                textList[i]=new JTextField(nowNum+"/"+strText[i]);
            else textList[i]=new JTextField(strText[i].toString());
            add(textList[i]);
            textList[i].setEditable(false);//设置不可编辑
            layS.putConstraint(layS.NORTH,textList[i], 0,layS.NORTH,lblList[i]);
            layS.putConstraint(layS.WEST,textList[i],10,layS.EAST,lblList[i]);
            layS.putConstraint(layS.EAST,textList[i],-20,layS.EAST,this);
        }

        //首先更新课程状态
        getDataFromSQL(lessonID,Info[3].toString());


        //状态标签1
        setLabel1();
        add(lblFlag);
        layS.putConstraint(layS.NORTH,lblFlag, 20,layS.SOUTH,lblList[2]);
        layS.putConstraint(layS.WEST,lblFlag,20,layS.WEST,this);
        //状态标签2
        setLabel2();
        add(lblFlag2);
        layS.putConstraint(layS.NORTH,lblFlag2, 0,layS.NORTH,lblFlag);
        layS.putConstraint(layS.WEST,lblFlag2,10,layS.EAST,lblFlag);


        //退课按钮
        add(btn2);
        layS.putConstraint(layS.NORTH,btn2, 10,layS.SOUTH,lblFlag);
        layS.putConstraint(layS.EAST,btn2,-20,layS.EAST,this);
        setBtn2Eable();//设置btn2按钮
        //选课按钮
        add(btn);
        layS.putConstraint(layS.NORTH,btn, 10,layS.SOUTH,lblFlag);
        layS.putConstraint(layS.EAST,btn,-10,layS.WEST,btn2);

        //设置文本大小
        setBoundsOfC();

        //监听事件
        //选课按钮
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //首先更新课程状态
                getDataFromSQL(lessonID,Info[3].toString());

                //一些必要信息：教师ID,便于缩短程序长度
                String tID=Info[3].toString();//老师ID

                //课程状态为“可选”
                if(Objects.equals(statusLesson, "Optional")){
                    //数据库操作--------------------------------------------------------------------------------
                    //"selectlesson"执行选课
                    Lesson lesson = new Lesson();
                    lesson.setTeacherID(studentID);//设置学生ID
                    lesson.setInnerID(lessonID+tID);//设置内部ID
                    Gson gson = new Gson();
                    String s = gson.toJson(lesson);
                    passer.send(new Message("admin", s, "lesson", "selectlesson"));

                    Message msg = passer.receive();
                    Map<String, String> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    String res = map.get("res");
                    //数据库操作---------------------------------------------------------------------
                    //选课成功-系统执行顺利
                    if(Objects.equals(res, "OK")) {
                        JOptionPane.showMessageDialog(superPanel == null ? thisPanel : superPanel,//避免superPanel为空
                                "选课成功", " ", JOptionPane.INFORMATION_MESSAGE);
                        //更新课程状态:查看数据库，并转为已选，同时需要更新界面
                        getDataFromSQL(lessonID,Info[3].toString());

                        System.out.println("选择课程成功：" + lessonID + " 老师：" + Info[3].toString());
                    }else JOptionPane.showMessageDialog(superPanel == null ? thisPanel : superPanel,//避免superPanel为空
                            "选课失败", "数据库操作警告", JOptionPane.WARNING_MESSAGE);

                }else {//课程状态“不可选”或“已选”
                    if(Objects.equals(statusLesson, "full"))
                        JOptionPane.showMessageDialog(
                                superPanel==null?thisPanel:superPanel,//避免superPanel为空
                                "该课程人数已满", "提示", JOptionPane.WARNING_MESSAGE);
                    else if(Objects.equals(statusLesson, "conflict"))
                        JOptionPane.showMessageDialog(
                                superPanel==null?thisPanel:superPanel,//避免superPanel为空
                                "该课程有冲突", "提示", JOptionPane.WARNING_MESSAGE);
                    else if(Objects.equals(statusLesson, "Selected"))
                        JOptionPane.showMessageDialog(
                                superPanel==null?thisPanel:superPanel,//避免superPanel为空
                                "您已选择该课程", "提示", JOptionPane.INFORMATION_MESSAGE);
                    else if (Objects.equals(statusLesson, "SameSelected"))
                        JOptionPane.showMessageDialog(
                                superPanel==null?thisPanel:superPanel,//避免superPanel为空
                                "您已选择该课程", "提示", JOptionPane.INFORMATION_MESSAGE);
                    else JOptionPane.showMessageDialog(
                                superPanel==null?thisPanel:superPanel,//避免superPanel为空
                                "课程状态有错(超过了5种)", "警告", JOptionPane.WARNING_MESSAGE);

                }
            }
        });

        //退选
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //首先更新课程状态
                getDataFromSQL(lessonID,Info[3].toString());

                //一些必要信息：教师ID,便于缩短程序长度
                String tID=Info[3].toString();//老师ID

                //退选确认
                int result = JOptionPane.showConfirmDialog(
                        superPanel == null ? thisPanel : superPanel,//避免superPanel为空,
                        "确认退选该课程？", "提示", JOptionPane.YES_NO_OPTION);
                //对话框结果判断
                if (result == JOptionPane.YES_OPTION) {//确认退选
                    //数据库操作------------------------------------------------------------------------------------------------
                    //"returnlesson"执行退课
                    Lesson lesson = new Lesson();
                    lesson.setTeacherID(studentID);//设置学生ID
                    lesson.setInnerID(lessonID+tID);//设置内部ID
                    Gson gson = new Gson();
                    String s = gson.toJson(lesson);
                    passer.send(new Message("admin", s, "lesson", "returnlesson"));

                    Message msg = passer.receive();
                    Map<String, String> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    String res = map.get("res");
                    //数据库操作---------------------------------------------------------------------
                    //退课成功-系统执行顺利
                    if(Objects.equals(res, "OK")) {
                        //弹出消息对话框
                        JOptionPane.showMessageDialog(superPanel == null ? thisPanel : superPanel,//避免superPanel为空
                                "退课成功", " ", JOptionPane.INFORMATION_MESSAGE);

                        //更新课程状态:查看数据库，并更新课程状态，同时需要更新界面
                        getDataFromSQL(lessonID,Info[3].toString());

                        System.out.println("退选该课程：" + lessonID + " 老师：" + Info[3].toString());
                    }else JOptionPane.showMessageDialog(superPanel == null ? thisPanel : superPanel,//避免superPanel为空
                            "退课失败", "数据库操作警告", JOptionPane.WARNING_MESSAGE);

                }else { //取消退选
                    //什么也不做
                }

            }
        });

    }

    /**
     * 进行选课/退课操作
     * @param lessonID
     * @param teacherID
     */
    private void getDataFromSQL(String lessonID,String teacherID){
        //数据库操作------
        //传数据 "getone" 查看当前课程状态
        //需要返回：总人数 and 剩余可选人数
        Lesson temp=new Lesson();
        temp.setInnerID(lessonID+Info[3].toString());//内部编号InnerID=lessonID+teacherID
        Gson gson=new Gson();
        String ss = gson.toJson(temp);
        passer.send(new Message("admin", ss, "lesson", "getone"));



        Message msg2 = passer.receive();
        Map<String, java.util.List<Lesson>> map2 = new Gson().fromJson(msg2.getData(), new TypeToken<HashMap<String, java.util.List<Lesson>>>() {
        }.getType());
        List<Lesson> res2 = map2.get("res");

        if(res2.size()>0){
            //计算已选人数
            nowNum=res2.get(0).getMaxSize()-res2.get(0).getLeftSize();
            //更新当前状态
            setJudgeLesson(lessonID,teacherID,studentID);
            //更新界面
            upDateData(nowNum);
        }else {
            System.out.println("函数getDataFromSQL(String lessonID,String teacherID)查询数据库失败");
        }

    }


    //状态更新
    /**
     * 设置状态标签1 & 2
     */
    public void setLabel1(){
        lblFlag.setOpaque(true);
        lblFlag.setForeground(Color.white);//前景色白色
        switch (statusLesson){
            case "Selected":
                lblFlag.setText(" 已选 ");
                lblFlag.setBackground(new Color(76,153,0));break;
            case "SameSelected":
                lblFlag.setText(" 已选同类课程 ");
                lblFlag.setBackground(new Color(0,139,139));break;
            case "full":
                lblFlag.setText(" 已满 ");
                lblFlag.setBackground(new Color(220,20,60));break;
            case "Optional":
                lblFlag.setText(" 可选 ");
                lblFlag.setBackground(new Color(255,140,0));break;
            case "conflict":
                lblFlag.setText(" 不可选 ");
                lblFlag.setBackground(new Color(128,128,128));break;
            default:
                lblFlag.setText(" --"+statusLesson+"-- ");
                lblFlag.setBackground(Color.BLACK);break;
        }
        System.out.println("学生选课：设置标签1为"+statusLesson);
    }
    public void setLabel2(){
        if(Objects.equals(statusLesson, "conflict")){
            lblFlag2.setText(" 课程冲突 ");
            lblFlag2.setOpaque(true);
            lblFlag2.setForeground(Color.white);//前景色白色
            lblFlag2.setBackground(new Color(255,69,0));//底色红色

            System.out.println("学生选课：设置标签2显示");
        }else {
            lblFlag2.setVisible(false);//隐藏标签
            System.out.println("学生选课：设置标签2隐藏");
        }
    }

    public void setSuperPanel(JPanel Panel){
        superPanel=Panel;
    }
    /**
     * 重新设置当前已选人数
     * @param num
     */
    public void setNowNum(int num){
        nowNum=num;
        String maxNum=Info[2].toString();
        textList[2].setText(nowNum+"/"+maxNum);
    }
    public int getNowNum(){
        return nowNum;
    }

    /**
     * 更新退课按钮状态
     */
    public void setBtn2Eable(){
        btn2.setEnabled(Objects.equals(statusLesson, "Selected"));//设置按钮是否可点击
    }

    /**
     * 汇总：更新页面状态
     */
    private void upDateData(int cNum){
        setNowNum(cNum);//更新已选人数文本框
        setLabel1();//更新label1
        setLabel2();//更新label2
        setBtn2Eable();//更新“退选”按钮是否可选

        thisPanel.updateUI();
        thisPanel.repaint();
    }


    //数据获取
    /**
     * 获取该课程状态
     * @param lessonID
     * @param teacherID
     * @param studentID
     */
    public void setJudgeLesson(String lessonID,String teacherID,String studentID){
        //"showlesson"
        Lesson lesson = new Lesson();
        lesson.setTeacherID(studentID);//设置学生ID
        lesson.setInnerID(lessonID+teacherID);//设置内部ID
        Gson gson = new Gson();
        String s = gson.toJson(lesson);
        passer.send(new Message("admin", s, "lesson", "showlesson"));

        Message msg = passer.receive();
        Map<String, String> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, String>>() {
        }.getType());
        String res = map.get("res");

        if(res!=null && !Objects.equals(res, ""))
            statusLesson=res;
        else System.out.println("String返回出错！！！！！！！");

    }

    //美化
    private void setBoundsOfC(){
        int size=18;
        for(int i=0;i<lblList.length;i++)
            lblList[i].setFont(new Font("黑体",Font.PLAIN,size));
        for(int i=0;i< textList.length;i++)
            textList[i].setFont(new Font("黑体",Font.PLAIN,size-2));
        lblFlag.setFont(new Font("黑体",Font.PLAIN,size));
        lblFlag2.setFont(new Font("黑体",Font.PLAIN,size));
        btn.setFont(new Font("黑体",Font.PLAIN,size));
        btn2.setFont(new Font("黑体",Font.PLAIN,size));
    }

    public void upDateSubPanel(ContentPanel panel_super){
        panel_super.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                getDataFromSQL(lessonID,Info[3].toString());
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
}
