/** ===================================================
 * Title: TabbedPanelLibrary_A.java
 * Created: [2022-8-17 17:07:20] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 图书馆管理——管理员界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-17,创建此文件
 *2.
 *3.
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window;

import javax.swing.*;
import java.awt.*;

public class TabbedPanelLibrary_A extends JTabbedPane{


    public TabbedPanelLibrary_A()
    {
        //JTabbedPane jtbp=new JTabbedPane();	//创建选项卡
        this.setTabPlacement(2);
        this.setBounds(0,0,1400,650);//注意！！！！！！！！！！！！！！！！！！！！！！！

        JPanel jp11 = new JPanel();
        JPanel jp12 = new JPanel();
        JPanel jp13 = new JPanel();
        JPanel jp14 = new JPanel();
        JPanel jp15 = new JPanel();
        //JPanel jp13 = new JPanel();
        //选项卡1的内容
        //设置标题
        JLabel lblTitleLabel = new JLabel("图书馆");
        lblTitleLabel.setFont(new Font("宋体", Font.BOLD, 50));
        //按钮
        JButton btnRegister = new JButton("AAAAA");
        btnRegister.setFont(new Font("宋体", Font.BOLD, 50));

        jp11.add(lblTitleLabel);
        jp12.add(btnRegister);

        this.addTab("书籍信息总览", null, jp11,"书籍信息总览");//
        this.addTab("查询书籍信息", null, jp12,"查询书籍信息");//书籍号 书名 一个或多个
        this.addTab("书籍信息管理", null, jp13,"书籍信息管理");//增加 删除 修改
        this.addTab("书籍借阅/退还", null, jp14,"书籍借阅/退还");
        this.addTab("查询借阅情况", null, jp15,"查询借阅情况");//某一个人的借阅
        this.setFont(new Font("宋体", Font.BOLD, 24));



        //jp.add(jtbp);
    }



}
