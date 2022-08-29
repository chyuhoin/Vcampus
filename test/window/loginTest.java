package window;

import com.vcampus.client.window.myLoginFrame;
import com.vcampus.net.ClientMessagePasser;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class loginTest {
    public static void main(String[] args)
    {

        Socket socket = null; // 连接指定服务器和端口
        try {
            socket = new Socket("localhost", 6666);
            ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        myLoginFrame frame = new myLoginFrame("登录");
        // 当关闭窗口时，退出整个程序
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置窗口的其他参数，如窗口大小
        frame.setSize(500, 500);
        frame.setResizable(true);//窗口大小不可改

        // 显示窗口
        frame.setVisible(true);


    }
}
