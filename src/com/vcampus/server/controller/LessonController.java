package com.vcampus.server.controller;

import com.vcampus.net.Message;
import com.vcampus.pojo.Lesson;
import com.vcampus.pojo.Student;
import com.vcampus.server.service.Service;
import com.vcampus.server.service.LessonService;
import com.google.gson.*;
import com.vcampus.server.service.StudentService;

import java.util.HashMap;

public class LessonController implements Controller{
    @Override
    public Message check(Message msg){
        LessonService service = new LessonService();
        Gson gson = new Gson();
        switch (msg.getOperation()) {
            case "post"://添加课程信息
            case "postone"://添加对应课程的老师
            case "delete"://删除课程，输入课程ID
            case "deleteone"://删除对应老师教授的特定课程
            case "get"://显示所有课程
            case "getone"://显示某一指标的课程 如“专业:计算机” “指标”+“:”+“数据”
            case "set"://修改课程信息
            case"getstudent"://显示对应学生选的课 输入学生ID
            case "getteacher"://显示选择对应课程的学生 输入课程ID
            case "show"://输入ID，显示内容：学生：姓名、身份（1--学生 2--老师）、ID、专业
                        //               老师：姓名、身份、ID、可选专业、偏好时间
            case "arrange"://自动排课，输入课程ID
            default:
                return new Message("404", "{res: 'Wrong Request!'}");
        }
    }
}

