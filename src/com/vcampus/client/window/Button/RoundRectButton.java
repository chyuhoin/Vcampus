package com.vcampus.client.window.Button;

import javax.swing.*;
import java.awt.*;

public class  RoundRectButton extends JButton
{
    public RoundRectButton(String s)
    {
        super(s);
        setMargin(new Insets(0,0,0,0));//去除文字与按钮的边沿
        setBorder(new RoundBorder());//圆角矩形边界
        setContentAreaFilled(false);//取消原先画矩形的设置
        //setBorderPainted(false);//会导致按钮没有明显边界
        setFocusPainted(false);//去除文字周围的虚线框
    }
    protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
        g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1,10,10);//填充圆角矩形边界
        // 这个调用会画一个标签和焦点矩形。
        super.paintComponent(g);
    }
}
