package com.vcampus.net;

import java.io.*;
import java.net.Socket;

public class ClientMessagePasser {

    private ObjectInputStream reader;
    private ObjectOutputStream writer;

    public ClientMessagePasser(ObjectInputStream reader, ObjectOutputStream writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public ClientMessagePasser(InputStream input, OutputStream output) {
        try {
            writer = new ObjectOutputStream(output);
            reader = new ObjectInputStream(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean send(Message message) {
        try {
            writer.writeObject(message);
            writer.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    public Message receive() {
        Message message = null;
        try {
            message = (Message) reader.readObject();
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        if(message == null) {
            message = new Message("404", "{}");
        }
        return message;
    }
}
