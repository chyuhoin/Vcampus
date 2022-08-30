package com.vcampus.client.window.login;

import com.vcampus.net.ClientMessagePasser;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class login {
    public static void Create_GUI(){
        JFrame frame=new My_Frame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {

        Socket socket = null; // 连接指定服务器和端口
        try {
            socket = new Socket("localhost", 6666);
            ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(()->{
            Create_GUI();
        });
    }
}
