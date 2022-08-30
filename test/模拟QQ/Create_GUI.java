package 模拟QQ;

//import com.formdev.flatlaf.FlatDarkLaf;
//import com.formdev.flatlaf.FlatLightLaf;
import com.vcampus.net.ClientMessagePasser;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class Create_GUI {
    public static void Create_GUI(){

//        JFrame frame=new MyQQ_Frame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        javax.swing.SwingUtilities.invokeLater(()->{
            Create_GUI();
        });
    }
}
