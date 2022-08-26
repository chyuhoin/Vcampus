package com.vcampus.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Goods;
import com.vcampus.pojo.User;
import org.junit.Test;

import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopTest {
    @Test
    public void testGet() throws Exception {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        Goods goods = new Goods();
        passer.send(new Message("student", gson.toJson(goods), "shop", "get"));
        Message message = passer.receive();
        System.out.println(message);

        Map<String, List<Goods>> map = gson.fromJson(message.getData(),
                new TypeToken<Map<String, List<Goods>>>(){}.getType());
        List<Goods> list = map.get("res");
        list.forEach(System.out::println);
    }

    @Test
    public void testPost() throws Exception {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        Goods goods = new Goods("155", "chocolate", "2",
                "500", "state", "2022-8-2",
                null, 5, 1);
        passer.send(new Message("student", gson.toJson(goods), "shop", "post"));
        Message message = passer.receive();
        System.out.println(message);

        Map<String, String> map = gson.fromJson(message.getData(),
                new TypeToken<Map<String, String>>(){}.getType());
        System.out.println(map.get("res"));
    }

    @Test
    public void testGetSell() throws Exception {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        User user = new User();
        user.setStudentID("2");
        passer.send(new Message("student", gson.toJson(user), "shop", "getSell"));
        Message message = passer.receive();
        System.out.println(message);

        Map<String, List<Goods>> map = gson.fromJson(message.getData(),
                new TypeToken<Map<String, List<Goods>>>(){}.getType());
        List<Goods> list = map.get("res");
        list.forEach(System.out::println);
    }

    @Test
    public void testBuy() throws Exception {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        HashMap<String, String> args = new HashMap<>();
        args.put("studentID", "1");
        args.put("goodsID", "155");
        passer.send(new Message("student", gson.toJson(args), "shop", "buy"));
        Message message = passer.receive();
        System.out.println(message);

        Map<String, String> map = gson.fromJson(message.getData(),
                new TypeToken<Map<String, String>>(){}.getType());
        System.out.println(map.get("res"));
    }
}
