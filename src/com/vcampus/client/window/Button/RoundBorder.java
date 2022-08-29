package com.vcampus.client.window.Button;

import javax.swing.border.Border;
import java.awt.*;

public class RoundBorder implements Border {
    public Insets getBorderInsets(Component c) {
        return new Insets(0,0,0,0);
    }
    public boolean isBorderOpaque() {
        return false;
    }
    public void paintBorder(Component c, Graphics g, int x, int y,
                            int width, int height) {
        //使用黑色在组件的外边缘绘制一个圆角矩形
        g.setColor(Color.BLACK);
        g.drawRoundRect(0, 0, c.getWidth()-1, c.getHeight()-1, 10, 10);
    }
}

