package com.vcampus.server;

import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;

import java.io.*;
import java.net.Socket;

public class ServerRunner implements Runnable{
    private InputStream input;
    private OutputStream output;

    public ServerRunner(Socket sock) {
        try {
            input = sock.getInputStream();
            output = sock.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        MessagePasser passer = null;
        passer = new MessagePasser(input, output);
        //passer.send(new Message("200", "hello"));
        for(;;) {
            Message message = passer.receive();
            if(message.getStatusCode().equals("300")) break;
            switch (message.getModule()) {
                case "login":
                    passer.send(new Message("200", "登录"));
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
