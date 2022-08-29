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

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelCourseSelection extends JPanel {

    private Student student;//登录学生
    private Object[][] Data=null;
    MessagePasser passer = ClientMessagePasser.getInstance();

    public PanelCourseSelection(Student student) {
        this.setLayout(new BorderLayout());//边界布局

        //数据库连接 获取可选课程、对应老师（姓名，ID）
        // 获取可选课程
        Lesson lesson = new Lesson();
        lesson.setMajor(student.getMajor());//设置院系
        Gson gson = new Gson();
        String s = gson.toJson(lesson);
        passer.send(new Message("admin", s, "lesson", "getone"));
/*
                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
*/
        Message msg = passer.receive();
        Map<String, java.util.List<Lesson>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Lesson>>>() {
        }.getType());
        List<Lesson> res = map.get("res");

        //存放课程ID
        List<List<Lesson>> majorLesson=null;
        //内容面板
        ContentPanel[] ContentPanelList=null;
        if (res.size() != 0) {
            for(int i=0;i<res.size();i++){
                String currID=res.get(i).getLessonID();
                if(majorLesson!=null)
                for(int j=0;j<majorLesson.size();j++){

                }
            }

            ContentPanelList=new ContentPanel[res.size()];
            for(int i=0;i<res.size();i++) {
                //获取课程老师
                setData(res.get(i));
                ContentPanelList[i] = new ContentPanel("0001", student.getStudentID(), Data);
            }

        } else {//未查到可选课程
            JOptionPane.showMessageDialog(this, "未查到可选课程", "警告",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println("未查到可选课程：PanelCourseSelection.java");
        }


        //this.add(new JExtensionPanel("test1", panel1, false), BorderLayout.NORTH);

    }


    private void setData(Lesson lesson){



        int count=1;//查看有多少老师
        Data=new Object[count][4];
        for(int i=0;i<count;i++){
            Data[i][0]=lesson.getTeacherID();//老师姓名
            Data[i][1]=lesson.getTime();//上课时间
            Data[i][2]=lesson.getMaxSize();//课程容量
            Data[i][3]=lesson.getTeacherID();//老师ID
        }
    }



}
