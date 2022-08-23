/** ===================================================
 * Title: myMainTest.java
 * Created: [2022-8-15 20:42:30] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 创建系统主界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-15,创建此文件
 *2. 对这个文件的修改历史进行详细描述，一般包括时间，修改者，
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window;

import javax.swing.*;

public class myMainTest {
    public static void main(String[] args)
    {
        //创建登录自己的主窗口对象
        JFrame frame = new myMainFrame("VCampus校园管理系统",3,"000");
        //functions p = new functions(frame);
       /* // 当关闭窗口时，退出整个程序 (在Swing高级篇教程中会介绍)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 窗口居中
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);

        // 显示窗口
        frame.setVisible(true);

        */

    }
}

