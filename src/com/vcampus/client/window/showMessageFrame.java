package com.vcampus.client.window;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;
import javax.swing.border.Border;

public class showMessageFrame extends javax.swing.JFrame {
    JLabel text=new JLabel();
    JButton close=new JButton();
    Toolkit tk = Toolkit.getDefaultToolkit();
    int height;
    int width;
    private String str = null;

    int x;
    int y;
    int mx;
    int my;
    int left;
    int top;

    String url0="test/img/错误.png";
    String url1="test/img/关闭1.png";
    ImageIcon Error_icon=new ImageIcon(url0);
    ImageIcon Close_icon=new ImageIcon(url1);
    public showMessageFrame(String str,int x,int y,int width,int height) {
        this.str = str;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        new Thread(new Runnable() {
            @Override
            public void run() {
                initGUI();
            }
        }).start();
    }


    private void initGUI() {
        try {
            addWindowFocusListener(new WindowFocusListener() {
                @Override
                public void windowGainedFocus(WindowEvent e) {
//                System.out.println("windowGainedFocus: 窗口得到焦点");
                }

                @Override
                public void windowLostFocus(WindowEvent e) {
//                System.out.println("windowLostFocus: 窗口失去焦点");
                    dispose();
                }
            });
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e){
//				System.out.println("鼠标按下");
                    //当鼠标点击时获取距离屏幕的坐标
                    mx=e.getX();
                    my=e.getY();
                }
            });
            //动态鼠标触发器
            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e){
//				System.out.println("鼠标拖动");
                    //获取当前位置的横坐标和纵坐标 left and top

                    //横向移动距离=横向动态坐标-鼠标点击时的横向静态坐标
                    //纵向移动距离=纵向动态坐标-鼠标点击时的纵向静态坐标
                    //设置可变化的位置 加上原来的位置即可
                    left=getLocation().x;
                    top=getLocation().y;
                    setLocation(left+e.getX()-mx, top+e.getY()-my);
                }
            });
            Error_icon.setImage(Error_icon.getImage().getScaledInstance((int)(0.05*width),(int)(0.4*height),Image.SCALE_DEFAULT));
            text.setIcon(Error_icon);
            text.setHorizontalTextPosition(SwingConstants.RIGHT);
            Close_icon.setImage(Close_icon.getImage().getScaledInstance((int)(0.04*width),(int)(0.32*height),Image.SCALE_DEFAULT));
            close.setIcon(Close_icon);

            setUndecorated(true);
            setLocationRelativeTo(null);
            setVisible(true);
            JPanel jPanel=new JPanel();
            //取代顶层容器
            setContentPane(jPanel);
            jPanel.setLayout(null);
            //设置背景
            jPanel.setBackground(new Color(0xDAFCEEEF, true));
            jPanel.setOpaque(true);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            {
                text.setText(str);
                text.setHorizontalAlignment(SwingConstants.LEFT);
                text.setForeground(new Color(0xF56B6C));
                text.setBounds((int)(0.1*width),(int)(0.3*height),(int)(0.75*width),(int)(0.4*height));
                text.setFont(new Font("宋体", 1, 20));
                jPanel.add(text);
            }
            {
                Border border=BorderFactory.createEmptyBorder(1, 1, 1, 1);
                close.setFocusPainted(false);
                close.setBackground(new Color(0xFFFFF0F0, true));//加了才能不显示
                close.setOpaque(false);
                close.setBorder(border);
                close.setBounds((int)(0.91*width),(int)(0.25*height),(int)(0.07*width),(int)(0.5*height));
                close.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e){
                        close.setOpaque(true);
                        close.setBackground(new Color(0xFFEE1B1B, true));
                    }
                    public void mouseExited(MouseEvent e) {
//                        close.setOpaque(false);
                        close.setBackground(new Color(0xFFFAF3F3, true));

                    }
                });
                close.addActionListener((e)->{
                    this.dispose();
                });
                jPanel.add(close);
            }
            setBounds(x, y, width, height);
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}