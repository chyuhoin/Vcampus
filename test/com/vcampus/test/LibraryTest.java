package com.vcampus.test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Book;
import com.vcampus.pojo.User;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryTest {

    @Test
    public void testViewAll() throws Exception {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        Book book = new Book();
        //book.setAuthor("you");
        String s = gson.toJson(book);
        System.out.println(s);
        passer.send(new Message("student", s, "library", "get"));
        Message message = (passer.receive());
        Map<String,List<Book>> map = gson.fromJson(message.getData(), new TypeToken<HashMap<String,List<Book>>>(){}.getType());

        List<?> res = (List<?>) map.get("res");
        for(Object item: res) {
            //System.out.println(item.toString());
            //Book book1 = gson.fromJson(item.toString(), Book.class);
            System.out.println(item);
        }
    }

    @Test
    public void testAdd() throws IOException {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        Book book = new Book("00000001", "The Man Who Changed China",
                "Robert Lawrence Kuhn", "history", 20, null);
        String s = gson.toJson(book);
        System.out.println(s);
        passer.send(new Message("admin", s, "library", "post"));
        Message message = (passer.receive());
        Map<String,String> map = gson.fromJson(message.getData(), new TypeToken<HashMap<String,String>>(){}.getType());

        System.out.println(map.get("res"));
    }

    @Test
    public void borrow() throws IOException {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("studentId", "213203450");
        hashMap.put("bookId", "00000001");
        String s = gson.toJson(hashMap);
        System.out.println(s);
        passer.send(new Message("admin", s, "library", "borrow"));
        Message message = (passer.receive());
        Map<String,String> map = gson.fromJson(message.getData(), new TypeToken<HashMap<String,String>>(){}.getType());

        System.out.println(map.get("res"));
    }

    @Test
    public void returnBook() throws IOException {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("studentId", "213203450");
        hashMap.put("bookId", "00000001");
        String s = gson.toJson(hashMap);
        System.out.println(s);
        passer.send(new Message("admin", s, "library", "return"));
        Message message = (passer.receive());
        Map<String,String> map = gson.fromJson(message.getData(), new TypeToken<HashMap<String,String>>(){}.getType());

        System.out.println(map.get("res"));
    }

    @Test
    public void testViewBorrow() throws Exception {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        User user = new User("213203450", "111", 1);
        String s = gson.toJson(user);
        System.out.println(s);
        passer.send(new Message("student", s, "library", "getB"));
        Message message = (passer.receive());
        Map<String,List<Book>> map = gson.fromJson(message.getData(), new TypeToken<HashMap<String,List<Book>>>(){}.getType());

        List<?> res = (List<?>) map.get("res");
        for(Object item: res) {
            //System.out.println(item.toString());
            //Book book1 = gson.fromJson(item.toString(), Book.class);
            System.out.println(item);
        }
    }
}
