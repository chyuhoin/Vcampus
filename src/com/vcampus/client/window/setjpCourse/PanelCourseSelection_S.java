/** ===================================================
 * Title: PanelStudentManage_T.java
 * Created: [2022-8-30  00:53:12] by  张星喆
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 教务系统-学生-选课系统
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-30,创建此文件
 *2. 2022-8-30,完善设置
 *3.2022-8-30,前后端连接 修改人：张星喆
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window.setjpCourse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.client.window.setjpCourse.mypaneltable.MyExtensionPanel.ContentPanel;
import com.vcampus.client.window.setjpCourse.mypaneltable.MyExtensionPanel.JExtensionPanel;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Lesson;
import com.vcampus.pojo.Student;
import com.vcampus.pojo.Teacher;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PanelCourseSelection_S extends JPanel {

    private String studentID;//登录学生ID
    private Student student = new Student();
    private List<Lesson> lessonList=null;
    private List<ContentPanel> ContentPanelList = new ArrayList<ContentPanel>();//内容面板列表
    private Object[][] Data = null;
    MessagePasser passer = ClientMessagePasser.getInstance();

    public PanelCourseSelection_S(String ID) {
        //设置学生个人信息
        studentID = ID;//设置学生ID
        student = setMajor();
        System.out.println("当前登录选课系统：<" + student.getStudentID() + ">" + student.getName());

        //设置布局
        this.setLayout(new CardLayout(5, 10));//卡片布局
        JScrollPane jsp = new JScrollPane(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        JPanel c = new JPanel();
        SpringLayout layS = new SpringLayout();
        c.setLayout(layS);

        //设置内容面板
        setContentPanel();

        //设置伸缩面板
        JExtensionPanel[] JEList = new JExtensionPanel[ContentPanelList.size()];

        for (int i = 0; i < JEList.length; i++) {
            //初始化伸缩面板
            JEList[i]=new JExtensionPanel(lessonList.get(i),ContentPanelList.get(i),false);
            //添加伸缩面板
            c.add(JEList[i]);
            //设置布局
            int marginsLength=80;//页边距
            if (i == 0) {
                layS.putConstraint(layS.NORTH, JEList[i], 20, layS.NORTH, c);
                layS.putConstraint(layS.WEST, JEList[i], marginsLength, layS.WEST, c);
                layS.putConstraint(layS.EAST, JEList[i], -marginsLength, layS.EAST, c);
            } else {
                layS.putConstraint(layS.NORTH, JEList[i], 10, layS.SOUTH, JEList[i - 1]);
                layS.putConstraint(layS.WEST, JEList[i], marginsLength, layS.WEST, c);
                layS.putConstraint(layS.EAST, JEList[i], -marginsLength, layS.EAST, c);
            }
        }

        jsp.setViewportView(c);
        jsp.getVerticalScrollBar().setUnitIncrement(20);//设置滚轮速度
        //有没有更好的方法
        c.setPreferredSize(new Dimension(jsp.getPreferredSize().width, 1200));

        this.add(jsp);

    }

    /**
     * 设置内容面板-List
     * @return
     */
    public void setContentPanel(){
        //数据库连接 获取可选课程、对应老师（姓名，ID）
        // 获取可选课程
        Lesson lesson = new Lesson();
        //lesson.setMajor(student.getMajor());//设置院系
        lesson.setMajor("计算机");
        Gson gson = new Gson();
        String s = gson.toJson(lesson);
        passer.send(new Message("admin", s, "lesson", "getone"));

        Message msg = passer.receive();
        Map<String, java.util.List<Lesson>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Lesson>>>() {
        }.getType());
        List<Lesson> res = map.get("res");

        if (res.size() != 0) {
            //筛选课程
            Map<String, List<Lesson>> lessonGroup=setGrouping(res);
            //存储课程名称
            lessonList=new ArrayList<Lesson>();

            for(String key:lessonGroup.keySet()) {
                //存储课程
                Lesson tempLesson=lessonGroup.get(key).get(0);//我们只需课程ID及名称，故不必考虑老师
                lessonList.add(tempLesson);
                //获取课程老师姓名
                setData(lessonGroup.get(key));
                ContentPanelList.add(new ContentPanel(tempLesson, studentID, Data));
            }

        } else {//未查到可选课程
            System.out.println("未查到可选课程：PanelCourseSelection.java");
        }

    }
    /**
     * 数据分组，将课程ID相同的放在一起
     * @param lessonList
     * @return result
     */
    private Map<String, List<Lesson>> setGrouping(List<Lesson> lessonList){
        return lessonList.stream().collect(Collectors.groupingBy(Lesson::getLessonID));
    }

    /**
     * 获取老师姓名，设置数据
     * @param lessonG
     */
    private void setData(List<Lesson> lessonG){
        if(lessonG.size()>0) {
            int count = lessonG.size();//查看有多少老师
            Data = new Object[count][4];//老师信息
            for (int i = 0; i < count; i++) {
                //Data[i][0] = lessonG.get(i).getTeacherID();//老师姓名
                Data[i][1] = lessonG.get(i).getTime();//上课时间
                Data[i][2] = lessonG.get(i).getMaxSize();//课程容量
                Data[i][3] = lessonG.get(i).getTeacherID();//老师ID
                Teacher tempTea = new Teacher();
                tempTea.setTeacherID(lessonG.get(i).getTeacherID());
                Gson gson = new Gson();
                String s = gson.toJson(tempTea);
                passer.send(new Message("admin", s, "lesson", "showstatussteacher"));

                Message msg = passer.receive();
                Map<String, java.util.List<Teacher>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Teacher>>>() {
                }.getType());
                List<Teacher> res = map.get("res");

                if(res.size()>0)
                    Data[i][0]=res.get(0).getTeacherName();
                else {
                    System.out.println("数据库错误\"showstatussteacher\"：" +
                            "src/com/vcampus/client/window/setjpCourse/PanelCourseSelection.java");
                }

            }
        }
    }

    /**
     * 获取学生学院
     * @return
     */
    private Student setMajor(){
        //获取学生学院
        Student student=new Student();
        student.setStudentID(studentID);
        Gson gson = new Gson();
        String s = gson.toJson(student);
        passer.send(new Message("admin", s, "lesson", "showstatusstudent"));

        try {
            Thread.sleep(100);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        Message msg = passer.receive();
        Map<String, java.util.List<Student>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Student>>>() {
        }.getType());
        List<Student> res = map.get("res");
        System.out.println("学生：----");
        System.out.println(res);

        System.out.println("学生ID<"+studentID+">人数为"+res.size());
        if(res.size()>0){
            return res.get(0);
        }else{
            System.out.println("学生-教务管理-选课系统：未查询到用户/学生数据库查询出错！！！");
            return null;
        }
    }

}
