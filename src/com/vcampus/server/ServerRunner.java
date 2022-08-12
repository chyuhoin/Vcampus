package com.vcampus.server;

import com.vcampus.net.Message;

import java.io.*;
import java.net.Socket;

public class ServerRunner implements Runnable{
    private Socket sock;

    public ServerRunner(Socket sock) {
        this.sock = sock;
    }

    @Override
    public void run() {
        try (InputStream input = sock.getInputStream()) {
            try (OutputStream output = sock.getOutputStream()) {
                ObjectOutputStream writer = new ObjectOutputStream(output);
                ObjectInputStream reader = new ObjectInputStream(input);
                writer.writeObject(new Message("200", "hello"));
                writer.flush();
                for (;;) {
                    Message s = (Message) reader.readObject();
                    if (s.getData().equals("bye")) {
                        writer.writeObject(new Message("200", "hello"));
                        writer.flush();
                        break;
                    }
                    writer.writeObject(new Message("200",s.getData()));
                    writer.flush();
                }
            }
        } catch (Exception e) {
            try {
                sock.close();
            } catch (IOException ignored) {
            }
            System.out.println("client disconnected.");
        }
    }
}
