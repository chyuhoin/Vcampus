package com.vcampus.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Student;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentTest {
    @Test
    public void testGetOne() throws IOException {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
        MessagePasser passer = ClientMessagePasser.getInstance();

        Student student = new Student();
        student.setStudentID("213203450");
        String s = gson.toJson(student);
        System.out.println(s);
        passer.send(new Message("student", s, "student", "getone"));

        Message message = (passer.receive());
        System.out.println(message);
        Map<String, List<Student>> map = gson.fromJson(message.getData(),
                new TypeToken<HashMap<String,List<Student>>>(){}.getType());
        List<Student> res = map.get("res");

        for(Student item: res) {
            System.out.println(item);
        }
    }
}
