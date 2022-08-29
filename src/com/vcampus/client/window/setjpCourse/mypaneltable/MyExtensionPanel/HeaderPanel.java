package com.vcampus.client.window.setjpCourse.mypaneltable.MyExtensionPanel;

import javax.swing.*;
import java.awt.*;


public class HeaderPanel extends JPanel {
    //private static final long serialVersionUID = 1L;
    private int height = 50;
    private boolean isShow;

    public HeaderPanel() {
        this.isShow = false;
        setPanel();
    }

    public void setPanel(){
        SpringLayout layS=new SpringLayout();
        this.setLayout(layS);

        //标签
        JLabel lblImg=new JLabel();
        lblImg.setPreferredSize(new Dimension(30,30));
        layS.putConstraint(layS.NORTH,lblImg,10,layS.NORTH,this);
        layS.putConstraint(layS.EAST,lblImg,-10,layS.EAST,this);
        layS.putConstraint(layS.SOUTH,lblImg,-10,layS.SOUTH,this);

        ImageIcon icon1=new ImageIcon("Fig/SunFig.png");//生成icon图片
        icon1.setImage(icon1.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));//设置图片大小
        ImageIcon icon2=new ImageIcon("Fig/MoonFig.png");//生成icon图片
        icon1.setImage(icon2.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));//设置图片大小
        if(isShow)
            lblImg.setIcon(icon1);
        else
            lblImg.setIcon(icon2);

        //按钮
        JButton btn = new JButton("Button");
        btn.setPreferredSize(new Dimension(120,30));
        layS.putConstraint(layS.NORTH,btn,10,layS.NORTH,this);
        layS.putConstraint(layS.WEST,btn,10,layS.WEST,this);
        layS.putConstraint(layS.SOUTH,btn,-10,layS.SOUTH,this);


        this.add(btn);
        this.add(lblImg);

        updateUI();
        repaint();
    }

    public void removePanel(){
        this.removeAll();
    }
    //设置是否可见标志
    public void setShow(boolean isShow) {
        this.isShow = isShow;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.getWidth(), height);
    }

    @Override
    public Dimension getSize() {
        return new Dimension(this.getWidth(), height);
    }

}