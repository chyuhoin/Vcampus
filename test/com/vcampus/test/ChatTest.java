package com.vcampus.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.InnerMessage;
import com.vcampus.pojo.PublicMessage;
import com.vcampus.pojo.User;
import org.junit.Test;

import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatTest {
    @Test
    public void testGet() throws Exception {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        User user = new User();
        user.setStudentID("2");
        passer.send(new Message("student", gson.toJson(user), "chat", "get"));
        Message message = passer.receive();
        System.out.println(message);

        Map<String, List<InnerMessage>> map = gson.fromJson(message.getData(),
                new TypeToken<Map<String, List<InnerMessage>>>(){}.getType());
        System.out.println(map.get("res"));
    }

    @Test
    public void testSend() throws Exception {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        InnerMessage innerMessage = new InnerMessage();
        innerMessage.setSender("1");
        innerMessage.setContent("nya~");
        innerMessage.setStudentID("2");
        passer.send(new Message("student", gson.toJson(innerMessage), "chat", "post"));
        Message message = passer.receive();
        System.out.println(message);

        Map<String, String> map = gson.fromJson(message.getData(),
                new TypeToken<Map<String, String>>(){}.getType());
        System.out.println(map.get("res"));
    }

    @Test
    public void testSendP() throws Exception {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        PublicMessage publicMessage = new PublicMessage();
        publicMessage.setStudentID("1");
        publicMessage.setContent("皆おはよう～");
        passer.send(new Message("student", gson.toJson(publicMessage), "chat", "postPub"));
        Message message = passer.receive();
        System.out.println(message);

        Map<String, String> map = gson.fromJson(message.getData(),
                new TypeToken<Map<String, String>>(){}.getType());
        System.out.println(map.get("res"));
    }

    @Test
    public void testGetP() throws Exception {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        passer.send(new Message("student", "", "chat", "getPub"));
        Message message = passer.receive();
        System.out.println(message);

        Map<String, List<PublicMessage>> map = gson.fromJson(message.getData(),
                new TypeToken<Map<String, List<PublicMessage>>>(){}.getType());
        map.get("res").forEach(System.out::println);
    }
}
