package com.vcampus.net;

import java.io.*;
import java.net.Socket;

/**
 * 使用socket传递消息的传送器
 *
 * @author ZhongHaoyuan
 */
public class MessagePasser {
    private final InputStream input;
    private final OutputStream output;

    /**
     * 使用输入输出流来实例化一个传送器
     *
     * @param input  输入流
     * @param output 输出流
     */
    public MessagePasser(InputStream input, OutputStream output) {
        this.input = input;
        this.output = output;
    }

    /**
     * 传递一个消息，返回boolean表示传递是否出现异常
     *
     * @param message 消息本身
     * @return 是否正常完成发送
     */
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

    /**
     * 接收一个消息，如果对方一直没有消息传过来的话就会一直等
     *
     * @return 消息本身
     */
    public Message receive() {
        Message message = null;
        try {
            ObjectInputStream reader = new ObjectInputStream(input);
            message = (Message) reader.readObject();
        } catch (IOException exception) {
            message = new Message("disconnected", "{}");
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        if(message == null) {
            message = new Message("404", "{}");
        }
        return message;
    }
}
