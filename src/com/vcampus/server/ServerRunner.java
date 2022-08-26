package com.vcampus.server;

import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.server.controller.*;

import java.io.*;
import java.net.Socket;

public class ServerRunner implements Runnable{
    private InputStream input;
    private OutputStream output;
    private final Controller loginController, libraryController, studentController,
            lessonController, testController, shopController;

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
                default:
                    passer.send(new Message("200", message.getData()));
                    break;
            }
        }

    }
}
