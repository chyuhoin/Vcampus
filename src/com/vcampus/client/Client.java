package com.vcampus.client;

import com.vcampus.net.Message;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket sock = new Socket("localhost", 6666); // 连接指定服务器和端口
        try (InputStream input = sock.getInputStream()) {
            try (OutputStream output = sock.getOutputStream()) {
                ObjectOutputStream writer = new ObjectOutputStream(output);
                ObjectInputStream reader = new ObjectInputStream(input);
                Scanner scanner = new Scanner(System.in);
                System.out.println("[server] " + reader.readObject());
                for (;;) {
                    System.out.print(">>> "); // 打印提示
                    String s = scanner.nextLine(); // 读取一行输入
                    Message message = new Message("200", s);
                    writer.writeObject(message);
                    writer.flush();
                    Message resp = (Message) reader.readObject();
                    System.out.println("<<< " + resp);
                    if (resp.getData().equals("bye")) {
                        break;
                    }
                }
            }
        }
        sock.close();
        System.out.println("disconnected.");
    }
}
