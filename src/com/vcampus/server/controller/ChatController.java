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
 * 聊天模块的Controller
 *
 * @author ZhongHaoyuan
 */
public class ChatController implements Controller {

    private final ChatService service;
    private final Gson gson;
    private final Map<String, Object> map;

    /**
     * 初始化一个ChatController.
     */
    public ChatController() {
        service = new ChatService();
        gson = new Gson();
        map = new HashMap<>();
    }

    /**
     * 获得该用户所有接收到的私信
     *
     * @param msg 消息，包含一个User
     * @return 该User所接收到的所有私信的列表
     */
    private List<InnerMessage> getMsg(Message msg) {
        User user = gson.fromJson(msg.getData(), User.class);
        return service.getMessage(user);
    }

    /**
     * 向指定人发送一个私信
     *
     * @param msg 消息，包含一个私信的具体内容
     * @return 表示操作是否正常完成的字符串
     */
    private String sendMsg(Message msg) {
        InnerMessage iMsg = gson.fromJson(msg.getData(), InnerMessage.class);
        return service.sendMessage(iMsg) ? "OK": "Error";
    }

    /**
     * 获得全部的公共消息
     *
     * @return 公共消息，按照时间排序
     */
    private List<PublicMessage> getPubMsg() {
        return service.getPublic();
    }

    /**
     * 发送一条公共消息
     *
     * @param msg 消息，包含他要发的那个公共消息
     * @return 表示操作是否正常完成的字符串
     */
    private String sendPubMsg(Message msg) {
        PublicMessage pMsg = gson.fromJson(msg.getData(), PublicMessage.class);
        return service.sendPublic(pMsg) ? "OK": "Error";
    }

    /**
     * 删除一条私信
     *
     * @param msg 消息，包含要被删的那个私信
     * @return 表示操作是否正常完成的字符串
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
