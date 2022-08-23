/** ===================================================
 * Title: SetJPUser3.java
 * Created: [2022-8-21 15:15:42] by  张星喆
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 用户管理-【管理员】权限设置界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-21,创建此文件
 *2. 对这个文件的修改历史进行详细描述，一般包括时间，修改者，
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpUser;

import javax.swing.*;
import java.awt.*;

public class SetJPUser3 {
    public JPanel jp1=new JPanel();//信息展示页面
    public JPanel jp2=new JPanel();//个人信息
    public String ID;
    public SetJPUser3(String id,JPanel jp, CardLayout layout_Card){
        SpringLayout layout_Spring = new SpringLayout();
        jp1.setLayout(layout_Spring);
        jp2.setLayout(layout_Spring);
        jp.add(jp1);
        jp.add(jp2);
        layout_Card.show(jp, "jp1");//先显示jp1

        ID=id;
        setjp1(jp,layout_Spring,layout_Card);
        setjp2(jp,layout_Spring,layout_Card);
    }

    public void setjp1(JPanel jp,SpringLayout layout_Spring,CardLayout layout_Card){
        //标题
        JLabel lbl = new JLabel("权限更改");
        jp1.add(lbl);
        lbl.setFont(new Font("黑体", Font.BOLD, 50));
        layout_Spring.putConstraint(layout_Spring.NORTH, lbl, 20, layout_Spring.NORTH, jp1);  //标签1北侧——>容器北侧
        layout_Spring.putConstraint(layout_Spring.WEST, lbl, 20, layout_Spring.WEST, jp1);
        //文本
        JLabel textlbl = new JLabel("查询要变更权限的用户：");
        jp1.add(textlbl);
        textlbl.setFont(new Font("黑体", Font.BOLD, 20));
        layout_Spring.putConstraint(layout_Spring.NORTH, textlbl, 20, layout_Spring.SOUTH, lbl);  //标签1北侧——>容器北侧
        layout_Spring.putConstraint(layout_Spring.WEST, textlbl, 60, layout_Spring.WEST, jp1);
    }

    public void setjp2(JPanel jp,SpringLayout layout_Spring,CardLayout layout_Card){

    }
}
