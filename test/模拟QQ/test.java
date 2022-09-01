package 模拟QQ;

import com.vcampus.client.window.myMainFrame;
import com.vcampus.client.window.showMessageFrame;
import com.vcampus.net.ClientMessagePasser;

import java.io.IOException;
import java.net.Socket;

public class test {
    public static void main(String[] args) {
        Socket socket = null; // 连接指定服务器和端口
        try {
            socket = new Socket("localhost", 6666);
            ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        showMessageFrame test1=new showMessageFrame("用户名或密码错误！",600,400,360, 100,1);
//        myMainFrame test2= new myMainFrame("test",3,"1");
        new Main_Frame_Test("VCampus虚拟校园系统",3,"1");
    }
}
