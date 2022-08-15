package com.vcampus.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.User;
import org.junit.Test;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class LibraryTest {

    @Test
    public void testViewAll() throws Exception {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        String s = gson.toJson(new User("22", "123456", 0));
        System.out.println(s);
        MessagePasser passer = ClientMessagePasser.getInstance();
        passer.send(new Message("no", s, "login", "get"));
        Message message = (passer.receive());
        Map<String,Object> map = new Gson().fromJson(message.getData(), new TypeToken<HashMap<String,Object>>(){}.getType());
        System.out.println(map.get("res"));
    }
}
