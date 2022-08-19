package com.vcampus.server.controller;

import com.google.gson.reflect.TypeToken;
import com.vcampus.net.Message;
import com.vcampus.pojo.Student;
import com.vcampus.server.service.StudentService;
import com.google.gson.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StudentController implements Controller {

    @Override
    public Message check(Message msg){
        StudentService service = new StudentService();
        Gson gson = new Gson();
        switch (msg.getOperation()) {
            case "post": {
                //添加学生信息
                //只有在用户管理中存在对应ID在可以添加
                //如果学籍信息中已存在对应ID，则视为修改
                Student student = gson.fromJson(msg.getData(), Student.class);
                if (service.addStudent(student)) return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");
            }
            case "get": {//显示所有学生信息
                HashMap<String, Object> map2 = new HashMap<>();
                map2.put("res", service.viewAllStudents());
                return new Message("200", gson.toJson(map2));
            }
            case "getone": {//显示某符合某些指标的学生，用或来连接 如：“name:于济源” “属性”+“:”+“数据”
                String data = msg.getData();
                Map<String, String> map = gson.fromJson(data, new TypeToken<HashMap<String, String>>() {}.getType());
                HashMap<String, Object> map3 = new HashMap<>();
                Set<Student> studentSet = new HashSet<>();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    studentSet.addAll(service.viewSpecificStudents(entry.getKey(), entry.getValue()));
                }
                map3.put("res", studentSet);
                return new Message("200", gson.toJson(map3));
            }
            case "delete"://输入学生ID，删除对应学生
                if(service.deleteStudent(msg.getData())) return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");
            default:
                return new Message("404", "{res: 'Wrong Request!'}");
        }
    }
}