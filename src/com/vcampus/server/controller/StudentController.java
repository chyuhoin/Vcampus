package com.vcampus.server.controller;

import com.vcampus.net.Message;
import com.vcampus.pojo.Student;
import com.vcampus.server.service.Service;
import com.vcampus.server.service.StudentService;
import com.google.gson.*;

import java.util.HashMap;

public class StudentController implements Controller {

    @Override
    public Message check(Message msg){
        StudentService service = new StudentService();
        Gson gson = new Gson();
        switch (msg.getOperation()) {
            case "post"://添加学生信息 先删除对应studentID的学生，再添加新的信息
                Student user = gson.fromJson(msg.getData(), Student.class);
                if(!service.deleteStudent(user.getStudentID())) return new Message("200", "{res: 'NO'}");
                if(service.addStudent(user)) return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");
            case "get"://显示所有学生信息
                HashMap<String, Object> map2 = new HashMap<>();
                map2.put("res", service.viewAllStudents());
                return new Message("200", gson.toJson(map2));
            case "getone"://显示某一指标的学生 如：“姓名:于济源” “指标”+“:”+“数据”
                String data = msg.getData();
                String[] datanew = data.split(":",2);//根据:切分字符串;切两份
                HashMap<String, Object> map3 = new HashMap<>();
                map3.put("res", service.viewSpecificStudents(datanew[0],datanew[1]));
                return new Message("200", gson.toJson(map3));
            case "delete"://输入学生ID，删除对应学生
                if(service.deleteStudent(msg.getData())) return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");
            default:
                return new Message("404", "{res: 'Wrong Request!'}");
        }
    }
}