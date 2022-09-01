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

import java.io.*;
import java.net.Socket;
import java.util.Properties;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.FlatLightLaf;
import com.vcampus.net.ClientMessagePasser;

public class myLoginTest {
    public static void main(String[] args)
    {
        try {
            InputStream in= new BufferedInputStream(new FileInputStream(
                    new File("address.properties")));
            Properties prop = new Properties();
            prop.load(in);
            String host = prop.getProperty("host");
            Socket socket = null; // 连接指定服务器和端口
            socket = new Socket(host, 6666);
            ClientMessagePasser.build(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        myLoginFrame frame = new myLoginFrame("登录");

    }
}