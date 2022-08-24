package com.vcampus.server.controller;

import com.google.gson.reflect.TypeToken;
import com.vcampus.net.Message;
import com.vcampus.pojo.Lesson;
import com.vcampus.pojo.Student;
import com.vcampus.pojo.Teacher;
import com.vcampus.server.service.Service;
import com.vcampus.server.service.LessonService;
import com.google.gson.*;
import com.vcampus.server.service.StudentService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LessonController implements Controller{
    @Override
    public Message check(Message msg){
        LessonService service = new LessonService();
        Gson gson = new Gson();
        switch (msg.getOperation()) {
            case "post":
                //添加课程信息
                //输入：课程ID、课程名称、学院、专业、是否有考试
                //此时创建一个其他数据为空的“空课程”
                //只有数据库中没有对应的课程ID才可以创建空课程
                Lesson lesson = gson.fromJson(msg.getData(), Lesson.class);
                if(service.addLesson(lesson)) return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");

            case "postone":
                //添加对应课程的老师
                //输入课程所需的所有数据，创建（内部编号为课程ID+教师ID）
                //如果没有对应课程ID，报错
                //如果有对应课程ID且为“空课程”,视为修改空课程
                //如果有对应课程ID且无空课程，则创建课程
                //如果有对应内部ID，则视为修改
                //在老师的课表里添加信息
                Lesson lessonone = gson.fromJson(msg.getData(), Lesson.class);
                if(service.addOneLesson(lessonone)) return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");
            case "showroom":
                //输入时间，显示可用教室
                String time = msg.getData();//课程ID
                HashMap<String, Object> map9 = new HashMap<>();
                map9.put("res", service.showRoom(time));
                return new Message("200", gson.toJson(map9));
            case "addroom":
                //给课程添加教室
                //输入教室ID，内部ID，给课程添加教室
                //用","隔开
                String oldStr5 = msg.getData();
                String[] strs5 = oldStr5.split(",");//根据，切分字符串
                if(service.addRoom(strs5[0],strs5[1]))
                    return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");
            case "showteacher":
                //添加对应课程的老师时显示所有可选的老师
                //只要专业满足即可选择
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("res", service.viewTeachers(msg.getData()));
                return new Message("200", gson.toJson(map1));
            case"showtime":
                //输入老师ID，返回所有不可选的时间
                //不可选的时间有：非偏好时间、上课时间
                String teacherID = msg.getData();//课程ID
                String res=service.showTecherTime(teacherID);
                return new Message("200", "{res: '"+res+"'}");
            case "delete":
                //删除课程
                //输入课程ID
                //删除对应课程ID的所有课程,删除对应的考试信息,删除对应学生的课表信息，删除老师的课表信息
                //具体实现：利用课程ID查到内部ID
                //转到内部ID的处理
                String deleteID = msg.getData();//课程ID
                if (service.delete(deleteID)) return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");
            case "deleteone":
                //删除对应老师教授的特定课程
                //删除对应老师的考试信息
                //输入内部ID
                //具体实现：利用内部ID查到老师ID与学生ID
                //执行老师退课函数（输入老师ID与内部ID，在老师课表中删除对应课的信息）
                //执行学生退课函数（输入学生ID与内部ID，在学生和课的表中删除信息，在学生课表中删除信息）
                //执行删除考试函数（输入内部ID，删除对应考试信息）
                //执行删除课程函数（输入内部ID，在课的表里删除信息）
                String deleteoneID = msg.getData();//内部编号
                if (service.deleteone(deleteoneID)) return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");

            case "get":
                //显示所有课程
                HashMap<String, Object> map2 = new HashMap<>();
                map2.put("res", service.viewAllLessons());
                return new Message("200", gson.toJson(map2));
            case "getone":
                //显示某一指标的课程 如“专业:计算机” “指标”+“:”+“数据”
                String data = msg.getData();
                Map<String, String> map = gson.fromJson(data, new TypeToken<HashMap<String, String>>() {}.getType());
                HashMap<String, Object> map3 = new HashMap<>();
                Set<Lesson> lessonSet = new HashSet<>();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    lessonSet.addAll(service.viewSpecificLessons(entry.getKey(), entry.getValue()));
                }
                map3.put("res", lessonSet);
                return new Message("200", gson.toJson(map3));
            case"getstudent":
                //显示对应学生选的课 输入学生ID
                String studentID = msg.getData();

                HashMap<String, Object> map4 = new HashMap<>();
                map4.put("res", service.searchMine(studentID));
                return new Message("200", gson.toJson(map4));
            case "getteacher":
                //显示选择对应课程的学生 输入课程ID
                String lessonID = msg.getData();
                HashMap<String, Object> map5 = new HashMap<>();
                map5.put("res", service.getTeacher(lessonID));
                return new Message("200", gson.toJson(map5));
            case "getspecificteacher":
                //显示选择对应课程的学生 输入内部ID
                String innerID = msg.getData();
                HashMap<String, Object> map6 = new HashMap<>();
                map6.put("res", service.getSpecificTeacher(innerID));
                return new Message("200", gson.toJson(map6));
            case "showstatusstudent":
                //输入ID，显示内容：*学生：姓名、身份（1--学生 2--老师）、ID、专业
                //               老师：姓名、身份、ID、可选专业、偏好时间
                //返回一个学生的类的list
                String studentID1 = msg.getData();
                HashMap<String, Object> map7 = new HashMap<>();
                map7.put("res", service.searchStudent(studentID1));
                return new Message("200", gson.toJson(map7));

            case "showstatussteacher":
                //输入ID，显示内容：学生：姓名、身份（1--学生 2--老师）、ID、专业
                //               *老师：姓名、身份、ID、可选专业、偏好时间
                //返回一个老师的类的list
                String teacherID1 = msg.getData();
                HashMap<String, Object> map8 = new HashMap<>();
                map8.put("res", service.searchTeacher(teacherID1));
                return new Message("200", gson.toJson(map8));
            case "setteacher":
                //修改老师的可选专业与偏好时间
                //输入一个老师的类
                //如果没有则视为添加
                //如果有则视为修改
                Teacher teacher = gson.fromJson(msg.getData(), Teacher.class);
                if (service.addTeacher(teacher)) return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");
            case "arrange":
                //自动排课，输入课程ID
            case "addgrade":
                //添加成绩
                //输入学生ID，内部ID，成绩，用“,”隔开
                String oldStr1 = msg.getData();
                String[] strs1 = oldStr1.split(",");//根据，切分字符串
                if(service.addGrade(strs1[0],strs1[1],Integer.parseInt(strs1[2])))
                    return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");

            case "showlesson":
                //显示课程的状态：可选、已选、已满、时间冲突
                //输入学生ID与课程号 用","隔开
                String oldStr2 = msg.getData();
                String[] strs2 = oldStr2.split(",");//根据，切分字符串
                String data1=service.judgeLesson(strs2[0],strs2[1]);
                return new Message("200", "{res: "+"'"+data1+"'}");
            case "selectlesson":
                //学生选课
                //输入学生ID与课程号 用","隔开
                String oldStr3 = msg.getData();
                String[] strs3 = oldStr3.split(",");//根据，切分字符串
                if(service.selectLesson(strs3[0],strs3[1]))
                    return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");
            case "returnlesson":
                //学生退课
                //输入学生ID与课程号 用","隔开
                String oldStr4 = msg.getData();
                String[] strs4 = oldStr4.split(",");//根据，切分字符串
                if(service.returnLesson(strs4[0],strs4[1]))
                    return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");

            default:
                return new Message("404", "{res: 'Wrong Request!'}");
        }
    }
}

