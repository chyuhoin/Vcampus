package com.vcampus.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Goods;
import com.vcampus.pojo.Record;
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
        goods.setGoodsName("434234");
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

        Goods goods = new Goods("037", "coffee", "1",
                "350", "state", "2022-8-21",
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
        user.setStudentID("333");
        passer.send(new Message("student", gson.toJson(user), "shop", "getSell"));
        Message message = passer.receive();
        System.out.println(message);

        Map<String, List<Goods>> map = gson.fromJson(message.getData(),
                new TypeToken<Map<String, List<Goods>>>(){}.getType());
        List<Goods> list = map.get("res");
        list.forEach(System.out::println);
    }

    @Test
    public void testGetBuy() throws Exception {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        User user = new User();
        user.setStudentID("22");
        passer.send(new Message("student", gson.toJson(user), "shop", "getBuy"));
        Message message = passer.receive();
        System.out.println(message);

        Map<String, List<Record>> map = gson.fromJson(message.getData(),
                new TypeToken<Map<String, List<Record>>>(){}.getType());
        List<Record> list = map.get("res");
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

    @Test
    public void testReturn() throws Exception {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        HashMap<String, String> args = new HashMap<>();
        args.put("studentID", "test");
        args.put("goodsID", "123");
        passer.send(new Message("student", gson.toJson(args), "shop", "return"));
        Message message = passer.receive();
        System.out.println(message);

        Map<String, String> map = gson.fromJson(message.getData(),
                new TypeToken<Map<String, String>>(){}.getType());
        System.out.println(map.get("res"));
    }

    @Test
    public void testConfirm() throws Exception {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        HashMap<String, String> args = new HashMap<>();
        args.put("studentID", "test");
        args.put("goodsID", "123");
        passer.send(new Message("student", gson.toJson(args), "shop", "confirm"));
        Message message = passer.receive();
        System.out.println(message);

        Map<String, String> map = gson.fromJson(message.getData(),
                new TypeToken<Map<String, String>>(){}.getType());
        System.out.println(map.get("res"));
    }

    @Test
    public void testDelete() throws Exception {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        Goods goods = new Goods();
        goods.setGoodsID("037");
        passer.send(new Message("student", gson.toJson(goods), "shop", "delete"));
        Message message = passer.receive();
        System.out.println(message);

        Map<String, String> map = gson.fromJson(message.getData(),
                new TypeToken<Map<String, String>>(){}.getType());
        System.out.println(map.get("res"));
    }

    @Test
    public void testChange() throws Exception {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        Goods goods = new Goods("11", "11", "1",
                "1", "1", "1",
                "null", 1, 1);
        passer.send(new Message("student", gson.toJson(goods), "shop", "put"));
        Message message = passer.receive();
        System.out.println(message);

        Map<String, String> map = gson.fromJson(message.getData(),
                new TypeToken<Map<String, String>>(){}.getType());
        System.out.println(map.get("res"));
    }
}
