package com.vcampus.client.window.setjpCourse.mypaneltable.MyExtensionPanel;


import com.vcampus.client.window.setjpCourse.mypaneltable.RoundBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class SubContentPanel extends JPanel{
    private JPanel superPanel=null;
    private JPanel thisPanel;

    //数据
    private String lessonID=null;//课程ID
    private String studentID=null;//学生ID
    private Object[] Info=new Object[4];//教师-时间-最大人数-教师ID
    private int nowNum=0;//当前已选人数
    private int MaxNum=0;
    public String statusLesson=null;//判断课程状态

    //控件
    private JLabel[] lblList=new JLabel[3];//信息
    private JTextField[] textList=new JTextField[3];//信息
    private JButton btn=new JButton("选择");//选课按钮
    private JButton btn2=new JButton("退选");//退课按钮
    private JLabel lblFlag=new JLabel();//状态标签1：已选、可选、已满
    private JLabel lblFlag2=new JLabel();//状态标签2：是否冲突



    /**
     * 构造函数
     * @param lID
     * @param info
     */
    public SubContentPanel(String lID,String sID,Object[] info){
        super();
        thisPanel=this;
        lessonID=lID;
        studentID=sID;
        Info=info;
        //状态获取
        setJudgeLesson(lID,Info[3].toString(),sID);
        createSubContent();
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
        //内容
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
                layS.putConstraint(layS.NORTH,lblList[i],10,layS.NORTH,this);
                layS.putConstraint(layS.WEST,lblList[i],10,layS.WEST,this);
            }else {
                layS.putConstraint(layS.NORTH,lblList[i],10,layS.SOUTH,lblList[i-1]);
                layS.putConstraint(layS.WEST,lblList[i],0,layS.WEST,lblList[i-1]);
            }

            //文本框
            if(i==lblList.length-1)
                textList[i]=new JTextField(nowNum+"/"+strText[i]);
            else textList[i]=new JTextField(strText[i].toString());
            add(textList[i]);
            layS.putConstraint(layS.NORTH,textList[i], 0,layS.NORTH,lblList[i]);
            layS.putConstraint(layS.WEST,textList[i],10,layS.EAST,lblList[i]);
            layS.putConstraint(layS.EAST,textList[i],-10,layS.EAST,this);
        }

        //状态标签1
        setLabel1();
        add(lblFlag);
        layS.putConstraint(layS.NORTH,lblFlag, 10,layS.SOUTH,lblList[2]);
        layS.putConstraint(layS.WEST,lblFlag,10,layS.WEST,this);
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

        //监听事件
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //数据库操作--------------------------------------------------------------------------------
                //数据库操作-------------------------------------------------------------
                String tID=Info[3].toString();//老师ID
                setJudgeLesson(lessonID,tID,studentID);//更新课程状态
                if(Objects.equals(statusLesson, "Optional")){//课程可选
                    JOptionPane.showMessageDialog(
                            superPanel==null?thisPanel:superPanel,//避免superPanel为空
                            "选课成功",
                            " ",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    //数据库操作--------------------------------------------------------------------------------
                    //数据库操作------------------------------------------------------------------------------------------------
                    //利用lessonID+teacherID
                    int cNum=30;//获取当前已选人数=总人数-剩余名额
                    upDateData(cNum);
                    System.out.println("选择课程：" + lessonID + " 老师：" + Info[3].toString());
                }else {
                    if(Objects.equals(statusLesson, "full"))
                        JOptionPane.showMessageDialog(
                            superPanel==null?thisPanel:superPanel,//避免superPanel为空
                            "该课程人数已满", "提示", JOptionPane.WARNING_MESSAGE);
                    else if(Objects.equals(statusLesson, "conflict"))
                        JOptionPane.showMessageDialog(
                                superPanel==null?thisPanel:superPanel,//避免superPanel为空
                                "该课程有冲突", "提示", JOptionPane.WARNING_MESSAGE);
                    else JOptionPane.showMessageDialog(
                                superPanel==null?thisPanel:superPanel,//避免superPanel为空
                                "您已选择该课程", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //退选
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        superPanel == null ? thisPanel : superPanel,//避免superPanel为空,
                        "确认退选该课程？", "提示", JOptionPane.YES_NO_OPTION
                );
                if (result == 1) {//确认退选
                    JOptionPane.showMessageDialog(
                            superPanel == null ? thisPanel : superPanel,//避免superPanel为空
                            "退课成功", " ", JOptionPane.INFORMATION_MESSAGE
                    );

                    //数据库操作------------------------------------------------------------------------------------------------
                    //利用lessonID+teacherID
                    int cNum=30;//获取当前已选人数=总人数-剩余名额
                    upDateData(cNum);
                    System.out.println("退选该课程：" + lessonID + " 老师：" + Info[4].toString());
                }

            }
        });



    }

    //状态更新
    /**
     * 设置状态标签1
     */
    public void setLabel1(){
        lblFlag.setOpaque(true);
        lblFlag.setForeground(Color.white);//前景色白色
        switch (statusLesson){
            case "Selected":
                lblFlag.setText(" 已选 ");
                lblFlag.setBackground(new Color(76,153,0));break;
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
                break;
        }
        lblFlag.repaint();
    }
    public void setLabel2(){
        if(Objects.equals(statusLesson, "conflict")){
            lblFlag2.setText(" 课程冲突 ");
            lblFlag2.setOpaque(true);
            lblFlag2.setForeground(Color.white);//前景色白色
            lblFlag2.setBackground(new Color(255,69,0));//底色红色
        }else {
            lblFlag2.setVisible(false);//隐藏标签
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
        this.repaint();
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
        setNowNum(cNum);
        setLabel1();
        setBtn2Eable();

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
        //数据库

        //statusLesson=res;
        statusLesson="Selected";

    }

    /**
     * 进行选课/退课操作
     * @param Op
     * @param lessonID
     * @param teacherID
     */
    private void getDataFromSQL_and_UpDate(String Op,String lessonID,String teacherID){
        //数据库操作------------------------------------------------------------------------------------------------
        //传数据

        //需要返回：总人数 and 剩余可选人数 "getone"

        if(Objects.equals(Op, "selectlesson")) {
            //选课相关操作
            System.out.println("selectlesson");
        } else {
            //退课相关操作
            System.out.println("returnlesson");
        }

        if(true){
            //操作
        }

    }

}
