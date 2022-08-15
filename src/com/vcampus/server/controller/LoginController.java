package com.vcampus.server.controller;

import com.vcampus.net.Message;
import com.vcampus.pojo.User;
import com.vcampus.server.service.UserService;
import com.google.gson.*;

public class LoginController implements Controller {

    @Override
    public Message check(Message msg) {
        if(msg.getOperation().equals("get")) {
            UserService service = new UserService();
            Gson gson = new Gson();
            User user = gson.fromJson(msg.getData(), User.class);
            if(service.login(user)) return new Message("200", "{res: 'OK'}");
            else return new Message("200", "{res: 'NO'}");
        }
        else if(msg.getOperation().equals("register")) {
            UserService service = new UserService();
            Gson gson = new Gson();
            User user = gson.fromJson(msg.getData(), User.class);
            if(service.register(user)) return new Message("200", "{res: 'OK'}");
            else return new Message("200", "{res: 'NO'}");
        }
        else return new Message("404", "{res: 'Wrong Request!'}");
    }
}
