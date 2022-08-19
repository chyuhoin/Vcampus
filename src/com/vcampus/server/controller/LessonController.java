package com.vcampus.server.controller;

import com.google.gson.reflect.TypeToken;
import com.vcampus.net.Message;
import com.vcampus.pojo.Lesson;
import com.vcampus.pojo.Student;
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
                Lesson lessonone = gson.fromJson(msg.getData(), Lesson.class);
                if(service.addOneLesson(lessonone)) return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");
            case "showteacher":
                //添加对应课程的老师时显示所有可选的老师
                //只要专业满足即可选择
            case"showtime":
                //输入老师ID，返回所有不可选的时间
                //不可选的时间有：非偏好时间、上课时间
            case "delete":
                //删除课程，输入课程ID
                //输入课程ID,删除对应课程ID的所有课程,删除对应的考试信息
            case "deleteone":
                //删除对应老师教授的特定课程
                //删除对应老师的考试信息
            case "get":
                //显示所有课程
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("res", service.viewAllLessons());
                return new Message("200", gson.toJson(map1));
            case "getone":
                //显示某一指标的课程 如“专业:计算机” “指标”+“:”+“数据”
                String data = msg.getData();
                Map<String, String> map = gson.fromJson(data, new TypeToken<HashMap<String, String>>() {}.getType());
                HashMap<String, Object> map2 = new HashMap<>();
                Set<Lesson> lessonSet = new HashSet<>();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    lessonSet.addAll(service.viewSpecificLessons(entry.getKey(), entry.getValue()));
                }
                map2.put("res", lessonSet);
                return new Message("200", gson.toJson(map2));
            case "set":
                //修改课程信息
            case"getstudent":
                //显示对应学生选的课 输入学生ID
            case "getteacher":
                //显示选择对应课程的学生 输入课程ID
            case "show":
                //输入ID，显示内容：学生：姓名、身份（1--学生 2--老师）、ID、专业
                //               老师：姓名、身份、ID、可选专业、偏好时间
            case "setteacher":
                //修改老师的可选专业与偏好时间
            case "arrange":
                //自动排课，输入课程ID
            case "addgrade":
                //添加成绩
                //输入学生ID，内部ID，成绩，用“,”隔开
                String oldStr = msg.getData();
                String[] strs = oldStr.split(",");//根据，切分字符串
                if(service.addGrade(strs[0],strs[1],Integer.parseInt(strs[2])))
                    return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");

            case "showlesson":
                //显示课程的状态：可选、已选、已满、时间冲突
            default:
                return new Message("404", "{res: 'Wrong Request!'}");
        }
    }
}

