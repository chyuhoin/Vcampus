package com.vcampus.client.window.setjpCourse.mypaneltable.MyExtensionPanel;



import com.vcampus.client.window.setjpCourse.mypaneltable.RoundBorder;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

/**
 * 可以伸缩的面板
 * @anther 张星喆
 * @since 20220228
 * @version 1.0
 * */
public class JExtensionPanel extends JPanel implements MouseListener {
    private static final long serialVersionUID = 1L;
    private boolean isExpand;// 是否展开
    private String title;// 标题
    private JLabel label;// 标题面板
    private Component panel;// 内容面板
    private ImageIcon up_icon, down_icon;// 图标

    // ----各种构造函数---------------------------------------
    /**
     * 可以伸缩的面板,默认展开
     * */
    public JExtensionPanel() {
        this("", null, true);
    }

    /**
     * 可以伸缩的面板,默认展开
     * @param c 内容面板
     * */
    public JExtensionPanel(Component c) {
        this("", c, true);
    }

    /**
     * 可以伸缩的面板,默认展开
     * @param title 标题
     * @param c 内容面板
     * */
    public JExtensionPanel(String title, Component c) {
        this(title, c, true);
    }

    /**
     * 可伸缩面板-构造函数
     * @param title
     * @param c
     * @param isExpand
     */
    public JExtensionPanel(String title, Component c, boolean isExpand) {
        this.title = title;
        this.isExpand = isExpand;
        this.panel = c;
        init();
    }

    // ----各种方法---------------------------------------
    /**
     * 返回面板伸缩的状态
     * */
    public boolean isisExpand() {
        return isExpand;
    }

    /**
     * 设置面板的伸缩
     * @param expand 是否展开
     * */
    public void setExpand(boolean expand) {
        this.isExpand = expand;
        if (this.isExpand) {
            this.label.setIcon(down_icon);
            //this.label.setBorder(BorderFactory.createLoweredBevelBorder());//斜面边框(凹)
            this.label.setBorder(BorderFactory.createEtchedBorder());//蚀刻边框
            if (null != this.panel) {
                panel.setVisible(true);
            }
        } else {
            this.label.setIcon(up_icon);
            //this.label.setBorder(BorderFactory.createRaisedBevelBorder());//斜面边框(凸)
            this.label.setBorder(BorderFactory.createEtchedBorder());//蚀刻边框
            if (null != this.panel) {
                panel.setVisible(false);
            }
        }
        this.updateUI();
        //this.updateUI();
    }

    /**
     * 返回面板的内容面板
     * */
    public Component getPanel() {
        return panel;
    }

    /**
     * 设置内容面板
     *
     * @param panel 内容面板
     * */
    public void setPanel(Component panel) {
        if (null != panel) {//面板非空
            this.remove(this.panel);
            this.panel = panel;
            this.add(this.panel, BorderLayout.CENTER);
            panel.setVisible(this.isExpand);//设置内容面板是否可见
        } else {
            this.remove(this.panel);
            this.panel = null;
        }
        this.updateUI();//刷新布局
    }

    /**
     * 返回面板标题
     * */
    public String getTitle() {

        return title;
    }

    /**
     * 设置面板标题
     *
     * @param title 面板标题
     *
     * */
    public void setTitle(String title) {
        this.title = title;
        this.label.setText(title);
        this.updateUI();
    }

    /**
     * 创建标题指示图标
     * */
    private void createImages() {
        int w = this.getPreferredSize().width;
        int h = this.getPreferredSize().height;
        BufferedImage open = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        BufferedImage close = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        // 展开的图标
        Graphics2D g2 = open.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(this.getBackground());
        g2.fillRect(0, 0, w, h);
        int[] x = { 0, w / 2, w };
        int[] y = { h / 4, 3 * h / 4, h / 4 };
        Polygon p = new Polygon(x, y, 3);
        g2.setPaint(Color.BLACK);
        g2.fill(p);
        g2.setPaint(Color.BLACK);
        g2.draw(p);
        g2.dispose();
        down_icon = new ImageIcon(open);
        // 收缩的的图标
        g2 = close.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(getBackground());
        g2.fillRect(0, 0, w, h);
        x = new int[] { w / 4, 3 * w / 4, w / 4 };
        y = new int[] { 0, h / 2, h };
        p = new Polygon(x, y, 3);
        g2.setPaint(Color.GRAY);
        g2.fill(p);
        g2.setPaint(Color.GRAY);
        g2.draw(p);
        g2.dispose();
        up_icon = new ImageIcon(close);
    }

    /**
     * 初始化工作
     * */
    private void init() {
        this.createImages();// 获得图标
        this.setLayout(new BorderLayout());
        if (this.isExpand) {
            this.label = new JLabel(this.title, this.down_icon,
                    SwingConstants.LEADING);
            this.label.setBorder(new RoundBorder());
            if (null != this.panel) {
                this.add(this.panel, BorderLayout.CENTER);
            }
        } else {
            this.label = new JLabel(this.title, this.up_icon,
                    SwingConstants.LEADING);
            this.label.setBorder(new RoundBorder(Color.GRAY));
            if (null != this.panel) {
                this.add(this.panel, BorderLayout.CENTER);
                this.panel.setVisible(false);
            }
        }
        label.setBorder(new RoundBorder(Color.GRAY));
        label.setPreferredSize(new Dimension(this.getPreferredSize().width,60));
        this.add(this.label, BorderLayout.NORTH);

        this.label.addMouseListener(this);
    }

    // ----接口方法的实现---------------------------------------

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == label) {
            isExpand = !isExpand;
            if (this.isExpand) {
                this.label.setIcon(down_icon);
                this.label.setBorder(new RoundBorder());
                if (null != this.panel) {
                    panel.setVisible(true);
                }
            } else {
                this.label.setIcon(up_icon);
                this.label.setBorder(new RoundBorder(Color.GRAY));
                if (null != this.panel) {
                    panel.setVisible(false);
                }
            }
            this.updateUI();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * 用于测试
     * @param args
     */
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Container c = f.getContentPane();
        /*
        c.setLayout(new BorderLayout());

         */
        JPanel JP=new JPanel(new GridLayout(1,1));
        JScrollPane jsp=new JScrollPane(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );


        JPanel c=new JPanel();
        SpringLayout layS=new SpringLayout();
        c.setLayout(layS);
//内容面板
        Object[][] str1={
                {"张三","1-01-02",50,"ID1"},
                {"张三","1-01-02",50,"ID2"},
                {"张三","1-01-02",50,"ID3"},
                {"张三","1-01-02",50,"ID4"}
        };
        Object[][] str2={
                {"张三","1-01-02",50,"ID1"},
                {"张三","1-01-02",50,"ID2"}
        };
        Object[][] str3={
                {"张三","1-01-02",50,"ID1"},
                {"张三","1-01-02",50,"ID2"},
                {"张三","1-01-02",50,"ID1"},
                {"张三","1-01-02",50,"ID2"},
                {"张三","1-01-02",50,"ID3"},
                {"张三","1-01-02",50,"ID4"},
                {"张三","1-01-02",50,"ID1"},
        };


        ContentPanel panel1=new ContentPanel("0001","00000001",str1);
        ContentPanel panel2=new ContentPanel("0002","00000002",str2);
        ContentPanel panel3=new ContentPanel("0002","00000002",str3);
        ContentPanel panel4=new ContentPanel("0002","00000002",str2);
        ContentPanel panel5=new ContentPanel("0002","00000002",str2);
        ContentPanel panel6=new ContentPanel("0002","00000002",str1);
        ContentPanel panel7=new ContentPanel("0002","00000002",str1);
        ContentPanel panel8=new ContentPanel("0002","00000002",str1);
        //内容面板
        JExtensionPanel[] JEList={
                new JExtensionPanel("test1", panel1,false),
                new JExtensionPanel("test2", panel2,false),
                new JExtensionPanel("test3", panel3,false),
                new JExtensionPanel("test4", panel4,false),
                new JExtensionPanel("test5", panel5,false),
                new JExtensionPanel("test6", panel6,false),
                new JExtensionPanel("test7", panel7,false),
                new JExtensionPanel("test8", panel8,false)
        };

        for(int i=0;i<JEList.length;i++){
            c.add(JEList[i]);
            if(i==0){
                layS.putConstraint(layS.NORTH, JEList[i], 10, layS.NORTH, c);
                layS.putConstraint(layS.WEST, JEList[i], 10, layS.WEST, c);
                layS.putConstraint(layS.EAST, JEList[i], -10, layS.EAST, c);
            }
            else{
                layS.putConstraint(layS.NORTH, JEList[i], 10, layS.SOUTH, JEList[i-1]);
                layS.putConstraint(layS.WEST, JEList[i], 10, layS.WEST, c);
                layS.putConstraint(layS.EAST, JEList[i], -10, layS.EAST, c);
            }
        }


        jsp.setViewportView(c);
        jsp.getVerticalScrollBar().setUnitIncrement(10);//设置滚轮速度
        //有没有更好的方法
        c.setPreferredSize( new Dimension(jsp.getPreferredSize().width, 1200));

        JP.add(jsp);
        f.add(JP);

        f.setSize(800, 600);
        f.setLocation(200, 100);
        f.setVisible(true);
    }
}
