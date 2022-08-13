package com.vcampus.net;

import java.io.*;
import java.net.Socket;

public class MessagePasser {
    private final InputStream input;
    private final OutputStream output;

    public MessagePasser(InputStream input, OutputStream output) {
        this.input = input;
        this.output = output;
    }

    public boolean send(Message message) {
        try {
            ObjectOutputStream writer = new ObjectOutputStream(output);
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
            ObjectInputStream reader = new ObjectInputStream(input);
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
