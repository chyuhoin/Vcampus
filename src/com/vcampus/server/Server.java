package com.vcampus.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ExecutorService es = Executors.newFixedThreadPool(6); // 创建一个固定大小的线程池
        ServerSocket ss = new ServerSocket(6666); // 监听指定端口
        System.out.println("server is running...");
        while (true) {
            Socket sock = ss.accept();
            System.out.println("connected from " + sock.getRemoteSocketAddress());
            ServerRunner runner = new ServerRunner(sock);
            es.submit(runner);
        }
    }
}
