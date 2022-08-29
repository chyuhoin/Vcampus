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

public class PanelCourseSelection extends JPanel {

    private String studentID;//登录学生ID
    private Student student=new Student();
    private List<ContentPanel> ContentPanelList=new ArrayList<ContentPanel>();//内容面板列表
    private Object[][] Data=null;
    MessagePasser passer = ClientMessagePasser.getInstance();

    public PanelCourseSelection(String ID) {
        //设置学生个人信息
        studentID=ID;//设置学生ID
        student=setMajor();
        if(student!=null) {
            System.out.println("当前登录选课系统：<"+student.getStudentID()+">"+student.getName());
            //设置布局
            this.setLayout(new CardLayout(10, 10));//卡片布局
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
                c.add(JEList[i]);
                if (i == 0) {
                    layS.putConstraint(layS.NORTH, JEList[i], 10, layS.NORTH, c);
                    layS.putConstraint(layS.WEST, JEList[i], 10, layS.WEST, c);
                    layS.putConstraint(layS.EAST, JEList[i], -10, layS.EAST, c);
                } else {
                    layS.putConstraint(layS.NORTH, JEList[i], 10, layS.SOUTH, JEList[i - 1]);
                    layS.putConstraint(layS.WEST, JEList[i], 10, layS.WEST, c);
                    layS.putConstraint(layS.EAST, JEList[i], -10, layS.EAST, c);
                }
            }

            jsp.setViewportView(c);
            jsp.getVerticalScrollBar().setUnitIncrement(10);//设置滚轮速度
            //有没有更好的方法
            c.setPreferredSize(new Dimension(jsp.getPreferredSize().width, 1200));

            this.add(jsp);

        }else {
            System.out.println("学生-教务管理-选课系统：未查询到用户<"+studentID+">");
        }

    }

    /**
     * 设置内容面板-List
     * @return
     */
    public void setContentPanel(){
        //数据库连接 获取可选课程、对应老师（姓名，ID）
        // 获取可选课程
        Lesson lesson = new Lesson();
        lesson.setMajor(student.getMajor());//设置院系
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

            for(String key:lessonGroup.keySet()) {
                //获取课程老师姓名
                setData(lessonGroup.get(key));
                ContentPanelList.add(new ContentPanel(key, studentID, Data));
            }

        } else {//未查到可选课程
            JOptionPane.showMessageDialog(this, "未查到可选课程", "警告",
                    JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(
                            this,
                            "数据库错误！！！",
                            "警告",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    System.out.println("数据库错误：src/com/vcampus/client/window/setjpCourse/PanelCourseSelection.java");
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

        Message msg = passer.receive();
        Map<String, java.util.List<Student>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Student>>>() {
        }.getType());
        List<Student> res = map.get("res");

        System.out.println("学生ID<"+studentID+">人数为"+res.size());
        if(res.size()>0){
            return res.get(0);
        }else{
            System.out.println("学生-教务管理-选课系统：未查询到用户/学生数据库查询出错！！！");
            return null;
        }
    }

}
