/** ===================================================
 * Title: myLoginTest.java
 * Created: [2022-8-14 19:56:42] by  韩宇 张星喆
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 创建登录界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-14,创建此文件
 *2. 对这个文件的修改历史进行详细描述，一般包括时间，修改者，
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window;

import java.io.IOException;
import java.net.Socket;

//import com.formdev.flatlaf.*;
//import com.formdev.flatlaf.FlatLightLaf;
import com.vcampus.net.ClientMessagePasser;

public class myLoginTest {
    public static void main(String[] args)
    {

        Socket socket = null; // 连接指定服务器和端口
        try {
            socket = new Socket("localhost", 6666);
            ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //皮肤包
        //FlatLightLaf.setup();

        myLoginFrame frame = new myLoginFrame("登录");
        // 当关闭窗口时，退出整个程序
       /* frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置窗口的其他参数，如窗口大小
        frame.setSize(350, 270);
        frame.setResizable(false);//窗口大小不可改

        // 显示窗口
        frame.setVisible(true);

        */
    }
}