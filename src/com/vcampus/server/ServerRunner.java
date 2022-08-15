package com.vcampus.server;

import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.server.controller.Controller;
import com.vcampus.server.controller.LoginController;

import java.io.*;
import java.net.Socket;

public class ServerRunner implements Runnable{
    private InputStream input;
    private OutputStream output;
    private Controller loginController;

    public ServerRunner(Socket sock) {
        try {
            input = sock.getInputStream();
            output = sock.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loginController = new LoginController();
    }

    @Override
    public void run() {
        MessagePasser passer = null;
        passer = new MessagePasser(input, output);
        //passer.send(new Message("200", "hello"));
        for(;;) {
            Message message = passer.receive();
            if(message.getStatus().equals("disconnected")) break;
            switch (message.getModule()) {
                case "login":
                    passer.send(loginController.check(message));
                    break;
                case "student":
                    passer.send(new Message("200", "学籍管理"));
                    break;
                case "class":
                    passer.send(new Message("200", "教务"));
                    break;
                default:
                    passer.send(new Message("200", message.getData()));
                    break;
            }
        }

    }
}
