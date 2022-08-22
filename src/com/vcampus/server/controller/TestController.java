package com.vcampus.server.controller;

import com.google.gson.Gson;
import com.vcampus.net.Message;
import com.vcampus.pojo.Exam;
import com.vcampus.pojo.Lesson;
import com.vcampus.server.service.TestService;

import java.util.HashMap;

public class TestController implements Controller{

    @Override
    public Message check(Message msg){
        TestService service = new TestService();
        Gson gson = new Gson();
        switch (msg.getOperation()) {
            case "post":
                //添加考试信息,基于内部ID
                //传入exam的类
                Exam exam1 = gson.fromJson(msg.getData(), Exam.class);
                if(service.addExam(exam1)) return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");
            case "postall":
                //添加考试信息，基于课程ID
                //传入exam的类,此时innnerID传课程ID
                Exam exam2 = gson.fromJson(msg.getData(), Exam.class);
                if(service.addAllExam(exam2)) return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");
            case "delete":
                //删除考试
                //输入内部ID
                String innerID=msg.getData();
                if(service.deleteExam(innerID)) return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");
            case "deleteall":
                //删除考试
                //输入课程ID
                String lessonID=msg.getData();
                if(service.deleteAllExam(lessonID)) return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");

            case"addGrade"://添加成绩
            default:
                return new Message("404", "{res: 'Wrong Request!'}");
        }
    }
}
