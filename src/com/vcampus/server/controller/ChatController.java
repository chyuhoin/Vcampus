package com.vcampus.server.controller;

import com.google.gson.Gson;
import com.vcampus.net.Message;
import com.vcampus.pojo.InnerMessage;
import com.vcampus.pojo.User;
import com.vcampus.server.service.ChatService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatController implements Controller {

    private final ChatService service;
    private final Gson gson;
    private final Map<String, Object> map;

    public ChatController() {
        service = new ChatService();
        gson = new Gson();
        map = new HashMap<>();
    }

    private List<InnerMessage> getMsg(Message msg) {
        User user = gson.fromJson(msg.getData(), User.class);
        return service.getMessage(user);
    }

    @Override
    public Message check(Message msg) {
        if(msg.getOperation().equals("get")) {
            map.put("res", getMsg(msg));
        }
        else return new Message("404", "{res: 'Wrong Request!'}");
        return new Message("200", gson.toJson(map));
    }
}
