/** ===================================================
 * Title: PanelStudentManage_T.java
 * Created: [2022-8-30  00:53:12] by  张星喆
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 教务系统-学生-查看已选课程
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
import com.vcampus.client.window.setjpLibrary.mytablepanel.MyTablePanel;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Lesson;
import com.vcampus.pojo.LessonGrade;
import com.vcampus.pojo.Student;
import com.vcampus.pojo.Teacher;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class PanelViewCourse_S extends JPanel {

    private Object[][] rowData=null;
    private Object[] colummName={"课程编号","课程名称","授课教师","开课院系","上课时间","上课教室","考核方式","成绩"};
    MessagePasser passer = ClientMessagePasser.getInstance();
    public PanelViewCourse_S(String studentID){
        this.setLayout(new CardLayout(20,10));

        setData(studentID);

        //设置表格
        MyTablePanel table=new MyTablePanel(rowData,colummName);
        this.add(table);
    }

    private void setData(String studentID){
        Student student=new Student();
        student.setStudentID(studentID);//设置学生ID
        Gson gson=new Gson();
        String s = gson.toJson(student);
        passer.send(new Message("admin", s, "lesson", "getstudent"));

        Message msg = passer.receive();
        Map<String, java.util.List<Lesson>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Lesson>>>() {
        }.getType());
        java.util.List<Lesson> res = map.get("res");

        if(res.size()>0){
            //"课程编号","课程名称","授课教师","开课院系","上课时间","上课教室","是否考试","成绩"
            rowData=new Object[res.size()][colummName.length];

            for(int i=0;i<res.size();i++){
                rowData[i][0]=res.get(i).getLessonID();
                rowData[i][1]=res.get(i).getName();
                rowData[i][2]=res.get(i).getTeacherID();//教师姓名-教师ID
                rowData[i][3]=res.get(i).getSchool();
                rowData[i][4]=res.get(i).getTime();
                rowData[i][5]=res.get(i).getClassroom();
                rowData[i][6]=res.get(i).getIsExam()==1?"考试":"其他";
                rowData[i][7]="暂无成绩";// 成绩
                //获取教师姓名
                Teacher teacher=new Teacher();
                teacher.setTeacherID(res.get(i).getTeacherID());
                String st = gson.toJson(teacher);
                passer.send(new Message("admin", st, "lesson", "showstatussteacher"));

                Message msg2 = passer.receive();
                Map<String, java.util.List<Teacher>> map2 = new Gson().fromJson(msg2.getData(), new TypeToken<HashMap<String, java.util.List<Teacher>>>() {
                }.getType());
                List<Teacher> res2 = map2.get("res");
                if(res.size()>0)
                    rowData[i][2]=res2.get(0).getTeacherName();

                //获取成绩 "showgradestudent"
                Map<String,LessonGrade> grades=setMyGrade(studentID);
                //设置成绩
                if(grades!=null)
                    rowData[i][6]=grades.get(res.get(i).getInnerID()).getGrade();

            }

        }else {
            //System.out.println("还未选课");
        }
    }

    private Map<String,LessonGrade> setMyGrade(String studentID){
        //获取成绩 "showgradestudent"
        String st = studentID;
        passer.send(new Message("admin", st, "lesson", "showgradestudent"));

        Message msg2 = passer.receive();
        Map<String, java.util.List<LessonGrade>> map2 = new Gson().fromJson(msg2.getData(), new TypeToken<HashMap<String, java.util.List<LessonGrade>>>() {
        }.getType());
        List<LessonGrade> res2 = map2.get("res");

        if(res2.size()>0){
            Map<String,LessonGrade> temp= new HashMap<>();
            for (LessonGrade lessonGrade : res2) {
                temp.put(lessonGrade.getInnerID(), lessonGrade);
            }
            return temp;
        }else {
            System.out.println("学生-教务系统-已选课程-未查到任何成绩");
            return null;
        }
    }
}
