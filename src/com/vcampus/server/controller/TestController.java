package com.vcampus.server.controller;

import com.google.gson.Gson;
import com.vcampus.net.Message;
import com.vcampus.server.service.TestService;

import java.util.HashMap;

public class TestController implements Controller{

    @Override
    public Message check(Message msg){
        TestService service = new TestService();
        Gson gson = new Gson();
        switch (msg.getOperation()) {
            case "post"://添加考试信息,基于内部ID
            case "delete"://删除考试，输入课程ID
            case"set"://修改考试信息
            case"addGrade"://添加成绩
            default:
                return new Message("404", "{res: 'Wrong Request!'}");
        }
    }
}
