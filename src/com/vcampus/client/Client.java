package com.vcampus.client;

import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 6666); // 连接指定服务器和端口
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();
        Scanner scanner = new Scanner(System.in);
        for(;;) {
            String s = scanner.nextLine();
            MessagePasser passer = new MessagePasser(input, output);
            passer.send(new Message("200", s));
            Message message = (passer.receive());
            System.out.println(message);
        }
    }
}
