package com.vcampus.client.window.login;

import com.formdev.flatlaf.FlatLightLaf;
import com.vcampus.net.ClientMessagePasser;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class login {
    public static void Create_GUI(){
        JFrame frame=new My_Frame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {

        Socket socket = null; // 连接指定服务器和端口
        try {
            InputStream in= new BufferedInputStream(new FileInputStream(
                    new File("address.properties")));
            Properties prop = new Properties();
            prop.load(in);
            String host = prop.getProperty("host");
            String port = prop.getProperty("port");
            socket = new Socket(host, Integer.parseInt(port));
            ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(()->{
            Create_GUI();
        });
    }
}
