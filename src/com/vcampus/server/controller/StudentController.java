package com.vcampus.server.controller;

import com.vcampus.net.Message;
import com.vcampus.pojo.Student;
import com.vcampus.pojo.User;
import com.vcampus.server.service.Service;
import com.vcampus.server.service.StudentService;
import com.google.gson.*;
import com.vcampus.server.service.UserService;

import java.util.HashMap;

public class StudentController implements Controller {

    @Override
    public Message check(Message msg){
        switch (msg.getOperation()) {
            case "post"://添加学生信息
                StudentService service1 = new StudentService();
                Gson gson1 = new Gson();
                Student user = gson1.fromJson(msg.getData(), Student.class);
                if(service1.addstudent(user)) return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");
            case "get"://显示所有学生信息
                StudentService service2 = new StudentService();
                Gson gson2 = new Gson();
                HashMap<String, Object> map2 = new HashMap<>();
                map2.put("res", service2.viewAllStudents());
                return new Message("200", gson2.toJson(map2));
            case "getone"://显示某一指标的学生----还没写TAT
                StudentService service3 = new StudentService();
                Gson gson3 = new Gson();
                HashMap<String, Object> map3 = new HashMap<>();
                map3.put("res", service3.viewAllStudents());
                return new Message("200", gson3.toJson(map3));
            default:
                return new Message("404", "{res: 'Wrong Request!'}");
        }
    }
}