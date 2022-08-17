package com.vcampus.server.controller;

import com.vcampus.net.Message;
import com.vcampus.pojo.User;
import com.vcampus.server.service.UserService;
import com.google.gson.*;

public class LoginController implements Controller {

    @Override
    public Message check(Message msg) {
        UserService service = new UserService();
        Gson gson = new Gson();
        User user = gson.fromJson(msg.getData(), User.class);
        switch (msg.getOperation()){
            case "get": {//登录
                boolean res = service.login(user);
                String data = res ? "{res: 'OK'}" : "{res: 'Error'}";
                return new Message("200", data);
            }
            case "register": {//注册
                boolean res = service.register(user);
                String data = res ? "{res: 'OK'}" : "{res: 'Error'}";
                return new Message("200", data);
            }
            case "delete"://删除用户，输入ID
            case "Change Password"://修改密码
            case "Change Permissions"://输入ID，修改其权限 如：“123:1” “ID”+“:”+“1/2/3” 1--学生 2--老师 3--管理员

            default:
                return new Message("404", "{res: 'Wrong Request!'}");
        }
//        if(msg.getOperation().equals("get")) {
//            UserService service = new UserService();
//            Gson gson = new Gson();
//            User user = gson.fromJson(msg.getData(), User.class);
//            if(service.login(user)) return new Message("200", "{res: 'OK'}");
//            else return new Message("200", "{res: 'NO'}");
//        }
//        else if(msg.getOperation().equals("register")) {
//            UserService service = new UserService();
//            Gson gson = new Gson();
//            User user = gson.fromJson(msg.getData(), User.class);
//            if(service.register(user)) return new Message("200", "{res: 'OK'}");
//            else return new Message("200", "{res: 'NO'}");
//        }
//        else return new Message("404", "{res: 'Wrong Request!'}");
    }
}
