package com.vcampus.test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Book;
import com.vcampus.pojo.Lesson;
import com.vcampus.pojo.Teacher;
import com.vcampus.pojo.User;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LessonTest {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        Gson gson = new Gson();
//      String s = gson.toJson(new Lesson("0003","计算机组成原理",null,null,null,null,"计算机学院","计算机",1,null,null,0));
//      String s = gson.toJson(new Teacher("33","李四","计算机学院","计算机","1/01/13",1));
//      String s = gson.toJson(new Lesson("0002","信号与系统2","123",50,50,"1/3/4,2/3/4","计算机学院","计算机",1,"J3-201",4,1));
//      String s = gson.toJson(new Lesson("0002","信号与系统2","135",30,29,"1/1/2,2/1/2","计算机学院","计算机",1,"J1-101",4,1));
      String s = gson.toJson(new Lesson("0003","计算机组成原理","33",10,10,"1/3/4,1/5/6","计算机学院","计算机",1,"J5-201",4,1));

//        String s="0002123";
        System.out.println(s);
        MessagePasser passer = ClientMessagePasser.getInstance();
//        passer.send(new Message("no", s, "lesson", "post"));
//        passer.send(new Message("no", s, "lesson", "addlesson"));
//        passer.send(new Message("no", s, "lesson", "setteacher"));
//        passer.send(new Message("no", s, "lesson", "postone"));
//        passer.send(new Message("no", s, "lesson", "setlesson"));
//        passer.send(new Message("no", s, "lesson", "showlesson"));
//        passer.send(new Message("no", s, "lesson", "selectlesson"));
//        passer.send(new Message("no", s, "lesson", "showgradestudent"));
//        passer.send(new Message("no", s, "lesson", "addgrade"));
//        passer.send(new Message("no", s, "lesson", "showstatussteacher"));
//        passer.send(new Message("no", s, "lesson", "showstatusstudent"));
//        passer.send(new Message("no", s, "lesson", "getspecificteacher"));
//        passer.send(new Message("no", s, "lesson", "showteachertime"));
//        passer.send(new Message("no", s, "lesson", "getteacher"));
//        passer.send(new Message("no", s, "lesson", "getstudent"));
//        passer.send(new Message("no", s, "lesson", "get"));
//        passer.send(new Message("no", s, "lesson", "showtime"));
//        passer.send(new Message("no", s, "lesson", "getone"));
//        passer.send(new Message("no", s, "lesson", "showallteacher"));
//        passer.send(new Message("no", s, "lesson", "showroom"));
//        passer.send(new Message("no", s, "lesson", "showtable"));
//        passer.send(new Message("no", s, "lesson", "showtablename"));
//        passer.send(new Message("no", s, "lesson", "deleteone"));
//        passer.send(new Message("no", s, "lesson", "delete"));
        passer.send(new Message("no", s, "lesson", "arrange"));
        Message message = (passer.receive());
        Map<String,Object> map = new Gson().fromJson(message.getData(), new TypeToken<HashMap<String,Object>>(){}.getType());
        System.out.println(map.get("res"));
    }
}
//    String s = gson.toJson(new Lesson("0002","信号与系统2",null,null,null,null,"计算机学院","计算机",1,null,null,0));
//        System.out.println(s);
//                MessagePasser passer = ClientMessagePasser.getInstance();
//                passer.send(new Message("no", s, "lesson", "setlesson"));

//    String s = gson.toJson(new Teacher("123","于济源","计算机学院","计算机","1/01/02,2/01/02,3/01/02,4/01/02,5/01/02",1));
//        System.out.println(s);
//                MessagePasser passer = ClientMessagePasser.getInstance();
//                passer.send(new Message("no", s, "lesson", "setteacher"));