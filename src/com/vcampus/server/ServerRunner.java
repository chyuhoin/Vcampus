package com.vcampus.server;

import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.server.controller.*;

import java.io.*;
import java.net.Socket;

/**
 * 服务器具体运行的一个线程，每个线程对一个客户端服务
 * 本ServerRunner做的是初步分发的服务
 *
 * @author ZhongHaoyuan
 */
public class ServerRunner implements Runnable{
    private InputStream input;
    private OutputStream output;
    private final Controller loginController, libraryController, studentController,
            lessonController, testController, shopController, chatController;

    /**
     * 使用和指定客户端通信的信道实例化一个ServerRunner
     * 该ServerRunner之后会专门和指定的客户端通信
     *
     * @param sock the sock
     */
    public ServerRunner(Socket sock) {
        try {
            input = sock.getInputStream();
            output = sock.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loginController   = new LoginController();
        libraryController = new LibraryController();
        studentController = new StudentController();
        lessonController  = new LessonController();
        testController    = new TestController();
        shopController    = new ShopController();
        chatController    = new ChatController();

    }

    @Override
    public void run() {
        MessagePasser passer = null;
        passer = new MessagePasser(input, output);
        //passer.send(new Message("200", "hello"));
        for(;;) {
            Message message = passer.receive();
            if(message.getStatus().equals("disconnected")) break;
            System.out.println(message);
            switch (message.getModule()) {
                case "login":
                    passer.send(loginController.check(message));
                    break;
                case "student":
                    passer.send(studentController.check(message));
                    break;
                case "lesson":
                    passer.send(lessonController.check(message));
                    break;
                case "test":
                    passer.send(testController.check(message));
                    break;
                case "library":
                    passer.send(libraryController.check(message));
                    break;
                case "shop":
                    passer.send(shopController.check(message));
                    break;
                case "chat":
                    passer.send(chatController.check(message));
                    break;
                default:
                    passer.send(new Message("200", message.getData()));
                    break;
            }
        }
    }
}
