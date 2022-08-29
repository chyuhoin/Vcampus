package com.vcampus.server.service;

import com.vcampus.dao.MessageDao;
import com.vcampus.pojo.InnerMessage;
import com.vcampus.pojo.PublicMessage;
import com.vcampus.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class ChatService implements Service {
    public List<InnerMessage> getMessage(User user) {
        try {
            return MessageDao.readMessage(user.getStudentID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public boolean sendMessage(InnerMessage msg) {
        try {
            return MessageDao.sendMessage(msg.getSender(), msg.getStudentID(), msg.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteMessage(InnerMessage msg) {
        return MessageDao.deleteMessage(msg.getInnerID());
    }

    public boolean sendPublic(PublicMessage msg) {
        return MessageDao.sendToPublic(msg.getStudentID(), msg.getContent());
    }

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
