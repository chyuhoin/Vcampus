package com.vcampus.server.service;

import com.vcampus.dao.MessageDao;
import com.vcampus.pojo.InnerMessage;
import com.vcampus.pojo.PublicMessage;
import com.vcampus.pojo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 站内消息模块的服务层
 *
 * @author ZhongHaoyuan
 */
public class ChatService implements Service {
    /**
     * 获得发送给这个User的所有私信
     *
     * @param user 查询的用户
     * @return 所有这种私信的列表
     */
    public List<InnerMessage> getMessage(User user) {
        try {
            return MessageDao.readMessage(user.getStudentID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 发送一条私信
     *
     * @param msg 私信
     * @return 操作成功与否
     */
    public boolean sendMessage(InnerMessage msg) {
        try {
            return MessageDao.sendMessage(msg.getSender(), msg.getStudentID(), msg.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除一条私信
     *
     * @param msg 私信
     * @return 操作成功与否
     */
    public boolean deleteMessage(InnerMessage msg) {
        return MessageDao.deleteMessage(msg.getInnerID());
    }

    /**
     * 发送一条公共消息
     *
     * @param msg 公共消息
     * @return 操作成功与否
     */
    public boolean sendPublic(PublicMessage msg) {
        return MessageDao.sendToPublic(msg.getStudentID(), msg.getContent());
    }

    /**
     * 获得全部的公开消息
     *
     * @return 全部的公开消息的列表
     */
    public List<PublicMessage> getPublic() {
        List<PublicMessage> messages = new ArrayList<>();
        try {
            messages = MessageDao.getPublicMessage();
            //messages.sort((s1, s2) -> {s1.getSendTime().compareTo(s2.getSendTime())});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }
}
