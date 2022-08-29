package com.vcampus.client.window.Panel;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    Dimension d;
    Image image;

    public ImagePanel( Image image) {
        super();
        this.image = image;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        repaint();
    }
}
