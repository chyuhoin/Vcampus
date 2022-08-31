package com.vcampus.server.controller;

import com.google.gson.Gson;
import com.vcampus.net.Message;
import com.vcampus.pojo.InnerMessage;
import com.vcampus.pojo.PublicMessage;
import com.vcampus.pojo.User;
import com.vcampus.server.service.ChatService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Chat controller.
 *
 * @author ZhongHaoyuan
 */
public class ChatController implements Controller {

    private final ChatService service;
    private final Gson gson;
    private final Map<String, Object> map;

    /**
     * Instantiates a new Chat controller.
     */
    public ChatController() {
        service = new ChatService();
        gson = new Gson();
        map = new HashMap<>();
    }

    /**
     * Gets msg.
     *
     * @param msg the msg
     * @return the msg
     */
    private List<InnerMessage> getMsg(Message msg) {
        User user = gson.fromJson(msg.getData(), User.class);
        return service.getMessage(user);
    }

    /**
     * Send msg string.
     *
     * @param msg the msg
     * @return the string
     */
    private String sendMsg(Message msg) {
        InnerMessage iMsg = gson.fromJson(msg.getData(), InnerMessage.class);
        return service.sendMessage(iMsg) ? "OK": "Error";
    }

    /**
     * Gets pub msg.
     *
     * @return the pub msg
     */
    private List<PublicMessage> getPubMsg() {
        return service.getPublic();
    }

    /**
     * Send pub msg string.
     *
     * @param msg the msg
     * @return the string
     */
    private String sendPubMsg(Message msg) {
        PublicMessage pMsg = gson.fromJson(msg.getData(), PublicMessage.class);
        return service.sendPublic(pMsg) ? "OK": "Error";
    }

    /**
     * Delete msg string.
     *
     * @param msg the msg
     * @return the string
     */
    private String deleteMsg(Message msg) {
        InnerMessage iMsg = gson.fromJson(msg.getData(), InnerMessage.class);
        return service.deleteMessage(iMsg) ? "OK": "Error";
    }

    @Override
    public Message check(Message msg) {
        switch (msg.getOperation()) {
            case "get":
                map.put("res", getMsg(msg));
                break;
            case "post":
                map.put("res", sendMsg(msg));
                break;
            case "delete":
                map.put("res", deleteMsg(msg));
                break;
            case "getPub":
                map.put("res", getPubMsg());
                break;
            case "postPub":
                map.put("res", sendPubMsg(msg));
                break;
            default:
                return new Message("404", "{res: 'Wrong Request!'}");
        }
        return new Message("200", gson.toJson(map));
    }
}
