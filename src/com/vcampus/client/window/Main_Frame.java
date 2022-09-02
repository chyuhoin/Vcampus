package com.vcampus.client.window;

import com.formdev.flatlaf.FlatLightLaf;
import com.vcampus.client.window.Button.RoundRectButton;
import com.vcampus.client.window.setjpCourse.TabbedPanelCourse_A;
import com.vcampus.client.window.setjpCourse.TabbedPanelCourse_S;
import com.vcampus.client.window.setjpCourse.TabbedPanelCourse_T;
import com.vcampus.client.window.setjpLibrary.TabbedPanelLibrary_A;
import com.vcampus.client.window.setjpLibrary.TabbedPanelLibrary_S;
import com.vcampus.client.window.setjpLibrary.TabbedPanelLibrary_T;
import com.vcampus.client.window.setjpMessage.TabbedPanelMessage_A;
import com.vcampus.client.window.setjpMessage.TabbedPanelMessage_S;
import com.vcampus.client.window.setjpMessage.TabbedPanelMessage_T;
import com.vcampus.client.window.setjpSchool.TabbedPanelSchool_A;
import com.vcampus.client.window.setjpSchool.TabbedPanelSchool_S;
import com.vcampus.client.window.setjpSchool.TabbedPanelSchool_T;
import com.vcampus.client.window.setjpStore.TabbedPanelStore_A;
import com.vcampus.client.window.setjpStore.TabbedPanelStore_S;
import com.vcampus.client.window.setjpStore.TabbedPanelStore_T;
import com.vcampus.client.window.setjpUser.TabbedPanelUser_A;
import com.vcampus.client.window.setjpUser.TabbedPanelUser_S;
import com.vcampus.client.window.setjpUser.TabbedPanelUser_T;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.vcampus.client.window.login.login.Create_GUI;

public class Main_Frame extends JFrame {

    int x,y;
    int left,top;
    private JPanel panelMain = new JPanel();
    public JPanel panelTop = new JPanel();
    public  JPanel panelBottom = new JPanel();
    public JPanel panelLeft = new JPanel();

    public JLabel seuBack = new JLabel();
    //背景
    public JLabel bg = new JLabel();
    String url0="Vcampus//img//主界面背景.png";
    ImageIcon bg_icon=new ImageIcon(url0);
    public JButton close = new JButton();
    String url1="Vcampus//img//关闭.png";
    ImageIcon close_icon=new ImageIcon(url1);
    JButton min=new JButton();
    String url2="Vcampus//img//最小化.png";
    ImageIcon min_icon=new ImageIcon(url2);
    JButton back=new JButton();
    String url3="Vcampus//img//返回.png";
    ImageIcon back_icon=new ImageIcon(url3);
    //时间
    JLabel time = new JLabel();
    public Main_Frame(String title, int flag, String ID)
    {
        super(title);//调用父类构造函数，设置窗口名称
        //this.setDefaultLookAndFeelDecorated(true);
//        FlatDarculaLaf FlatLightLafLaf;
        FlatLightLaf.setup();//设置皮肤
        final int[] current = new int[1];//当前在哪个界面
        current[0] =-1;
        this.setSize(1400, 850);// 设置窗口的其他参数，如窗口大小
        this.setResizable(false);//窗口大小不可改
        int width=this.getWidth();//获取窗口宽度
        int height=this.getHeight();//获取窗口高度 你也可以设置高度居中

        // 当关闭窗口时，退出整个程序 (在Swing高级篇教程中会介绍)
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 窗口居中
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - this.getWidth()) / 2, (screenSize.height - this.getHeight()) / 2);

        //去除标题栏
        setUndecorated(true);
        //由于没有标题栏所以界面不能拖动改变位置
        //采取以下方法可以解决
        //静态鼠标触发器
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e){
//				System.out.println("鼠标按下");
                //当鼠标点击时获取距离屏幕的坐标
                x=e.getX();
                y=e.getY();
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
                setLocation(left+e.getX()-x, top+e.getY()-y);
            }
        });
        // 显示窗口
        this.setVisible(true);

        setContentPane(panelMain);//设置panelMain作为窗口容器
        ((JComponent) this.getContentPane()).setOpaque(false);


        panelMain.setOpaque(false);
        panelTop.setOpaque(false);
        panelBottom.setOpaque(false);
        //  panelBottom,setContentPane();
        //   panelLeft.setOpaque(false);

        panelMain.setLayout(null);
        panelTop.setLayout(null);
        panelBottom.setLayout(null);
        panelLeft.setLayout(null);


        panelTop.setBounds(0, 0, 1400, 100);
        panelBottom.setBounds(210,150,1300,2000);
        panelLeft.setBounds(0,100,180,750);
        panelMain.add(panelLeft);
        panelMain.add(panelBottom);
        //背景
        bg_icon.setImage(bg_icon.getImage().getScaledInstance((int)(1*width),(int)(1*height),Image.SCALE_DEFAULT));
        bg.setIcon(bg_icon);
        bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(1*height));
//        panelMain.add(bg);

        //添加关闭，返回，最小化
        {
            close_icon.setImage(close_icon.getImage().getScaledInstance((int)(0.015*width),(int)(0.015*width),Image.SCALE_DEFAULT));
            close.setIcon(close_icon);
            min_icon.setImage(min_icon.getImage().getScaledInstance((int)(0.02*width),(int)(0.02*width),Image.SCALE_DEFAULT));
            min.setIcon(min_icon);
            back_icon.setImage(back_icon.getImage().getScaledInstance((int)(0.02*width),(int)(0.02*width),Image.SCALE_DEFAULT));
            back.setIcon(back_icon);
            close.setFocusPainted(false);
            close.setBackground(Color.red);//加了才能不显示
            close.setOpaque(false);
            Border border=BorderFactory.createEmptyBorder(1, 1, 1, 1);
            close.setBorder(border);
            close.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e){
                    close.setOpaque(true);
                    close.setBackground(new Color(0xF13B3B));
                }
                public void mouseExited(MouseEvent e) {
                    close.setOpaque(false);
                }
            });
            close.addActionListener((e)->{
                this.dispose();
                SwingUtilities.invokeLater(()->{
                    Create_GUI();
                });

            });
            close.setBounds((int)(0.95*width),(int)(0*height),(int)(0.05*width),(int)(0.05*height));
            panelMain.add(close);
            min.setFocusPainted(false);
            min.setBackground(new Color(165, 200, 250, 255));;//加了才能不显示
            min.setOpaque(false);
            min.setBorder(border);
            min.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e){
                    min.setOpaque(true);
                    min.setBackground(new Color(165, 200, 250, 255));
                }
                public void mouseExited(MouseEvent e) {
                    min.setOpaque(false);
                }
            });
            min.addActionListener((e)->{
                this.setExtendedState(JFrame.ICONIFIED);
            });
            min.setBounds((int)(0.9*width),(int)(0*height),(int)(0.05*width),(int)(0.05*height));
            panelMain.add(min);
            back.setFocusPainted(false);
            back.setBackground(new Color(165, 200, 250, 255));;//加了才能不显示
            back.setOpaque(false);
            back.setBorder(border);
            back.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e){
                    back.setOpaque(true);
                    back.setBackground(new Color(165, 200, 250, 255));
                }
                public void mouseExited(MouseEvent e) {
                    back.setOpaque(false);
                }
            });
            back.addActionListener((e)->{
                panelBottom.removeAll();
                panelBottom.updateUI();
                panelBottom.repaint();
            });
            back.setBounds((int)(0.85*width),(int)(0*height),(int)(0.05*width),(int)(0.05*height));
            panelMain.add(back);
        }
        //时间
        {
            time.setForeground(new Color(0x98000000, true));
            time.setBounds(1000,30,1200,150);
            time.setFont(new Font("微软雅黑",Font.BOLD,28));
            this.add(time);
            this.setTimer(time);
        }
        //校徽背景图
//        ImageIcon seuIcon = new ImageIcon("resource//seu.jpg");
//        seuBack.setBounds(0,0,750,750);
//        seuBack.setIcon(seuIcon);
//        panelBottom.add(seuBack);
        //Logo
        JLabel lblLogoLabel=new JLabel("VCampus");
        lblLogoLabel.setBounds(900,10,500,80);
        lblLogoLabel.setFont(new Font("Jokerman",Font.BOLD, 60));
        lblLogoLabel.setForeground(Color.white);
        panelTop.add(lblLogoLabel);
        JButton btnExit = new JButton();
        ImageIcon exitIcon = new ImageIcon("Vcampus//resource//exit.png");
        btnExit.setIcon(exitIcon);
        btnExit.setOpaque(false);
        btnExit.setContentAreaFilled(false);
        btnExit.setBorderPainted(false);
        btnExit.setFont(new Font("宋体",Font.BOLD, 16));
        btnExit.setBounds(1200,40,120,50);
        panelTop.add(btnExit);
        //加背景图片
        JLabel topBack = new JLabel();
        ImageIcon topImg = new ImageIcon("Vcampus//resource//top.png");
        topBack.setIcon(topImg);
        topBack.setBounds(0,0,1400,120);
        panelTop.add(topBack);
        JLabel leftBack = new JLabel();
        leftBack.setOpaque(false);
        //底边栏设置
//        JLabel bottomBar = new JLabel();
//        bottomBar.setBounds(180,730,1200,120);
//        bottomBar.setIcon(new ImageIcon("resource//bottom.png"));
//        panelMain.add(bottomBar);
        //panelLeft按钮设置
        //设置背景
        Color color = new Color(252,199,64);
        panelLeft.setBackground(color);
        panelLeft.setOpaque(false);
        //菜单按钮设置
        JButton btnModule1 = new RoundRectButton("用户管理");
        JButton btnModule2 = new RoundRectButton("学籍管理");
        JButton btnModule3 = new RoundRectButton("教务管理");
        JButton btnModule4 = new RoundRectButton("校图书馆");
        JButton btnModule5 = new RoundRectButton("校园商店");
        JButton btnModule6 = new RoundRectButton("站内消息");
        JButton[] btn = {btnModule1,btnModule2,btnModule3,btnModule4,btnModule5,btnModule6};
        //退出按钮


        btnModule1.setFont(new Font("正楷",Font.BOLD, 20));
        btnModule1.setForeground(Color.WHITE);
        // btnModule1.setOpaque(false);
        btnModule2.setFont(new Font("正楷",Font.BOLD, 20));
        btnModule2.setForeground(Color.WHITE);
        btnModule3.setFont(new Font("正楷",Font.BOLD, 20));
        btnModule3.setForeground(Color.WHITE);
        btnModule4.setFont(new Font("正楷",Font.BOLD, 20));
        btnModule4.setForeground(Color.WHITE);
        btnModule5.setFont(new Font("正楷",Font.BOLD, 20));
        btnModule5.setForeground(Color.WHITE);
        btnModule6.setFont(new Font("正楷",Font.BOLD, 20));
        btnModule6.setForeground(Color.WHITE);

        btnModule1.setBounds(26,50,150,100);
        btnModule2.setBounds(26,50+110,150,100);
        btnModule3.setBounds(26,50+110+110,150,100);
        btnModule4.setBounds(26,50+110*3,150,100);
        btnModule5.setBounds(26,50+110*4,150,100);
        btnModule6.setBounds(26,50+110*5,150,100);


//        btnModule1.setContentAreaFilled(false);
//        btnModule2.setContentAreaFilled(false);
//        btnModule3.setContentAreaFilled(false);
//        btnModule4.setContentAreaFilled(false);
//        btnModule5.setContentAreaFilled(false);
//        btnModule6.setContentAreaFilled(false);
        //去边框
        btnModule1.setBorder(null);
        btnModule2.setBorder(null);
        btnModule3.setBorder(null);
        btnModule4.setBorder(null);
        btnModule5.setBorder(null);
        btnModule6.setBorder(null);




        for(int i=0;i<6;i++){
            btn[i].setBackground(new Color(0x0C13A97, true));
            btn[i].setOpaque(false);
            btn[i].setContentAreaFilled(false);
        }
        // 设置图标背景
        ImageIcon[]btnicon =new ImageIcon[6];
        btnicon[0] = new ImageIcon("Vcampus//resource//btn_user.png");
        btnModule1.setIcon(btnicon[0]);
        btnModule1.setHorizontalTextPosition(JButton.CENTER);
        btnModule1.setVerticalTextPosition(JButton.BOTTOM);
        btnicon[1] = new ImageIcon("Vcampus//resource//btn_student.png");
        btnModule2.setIcon(btnicon[1]);
        btnModule2.setHorizontalTextPosition(JButton.CENTER);
        btnModule2.setVerticalTextPosition(JButton.BOTTOM);
        btnicon[2] = new ImageIcon("Vcampus//resource//btn_lesson.png");
        btnModule3.setIcon(btnicon[2]);
        btnModule3.setHorizontalTextPosition(JButton.CENTER);
        btnModule3.setVerticalTextPosition(JButton.BOTTOM);
        btnicon[3] = new ImageIcon("Vcampus//resource//btn_library.png");
        btnModule4.setIcon(btnicon[3]);
        btnModule4.setHorizontalTextPosition(JButton.CENTER);
        btnModule4.setVerticalTextPosition(JButton.BOTTOM);
        btnicon[4] = new ImageIcon("Vcampus//resource//btn_store.png");
        btnModule5.setIcon(btnicon[4]);
        btnModule5.setHorizontalTextPosition(JButton.CENTER);
        btnModule5.setVerticalTextPosition(JButton.BOTTOM);
        btnicon[5] = new ImageIcon("Vcampus//resource//btn_message.png");
        btnModule6.setIcon(btnicon[5]);
        btnModule6.setHorizontalTextPosition(JButton.CENTER);
        btnModule6.setVerticalTextPosition(JButton.BOTTOM);
        ImageIcon[]btnicon_selected = new ImageIcon[6];
        btnicon_selected[0] = new ImageIcon("Vcampus//resource//btn_user_selected.png");
        btnicon_selected[1] = new ImageIcon("Vcampus//resource//btn_student_selected.png");
        btnicon_selected[2] = new ImageIcon("Vcampus//resource//btn_lesson_selected.png");
        btnicon_selected[3] = new ImageIcon("Vcampus//resource//btn_library_selected.png");
        btnicon_selected[4] = new ImageIcon("Vcampus//resource//btn_store_selected.png");
        btnicon_selected[5] = new ImageIcon("Vcampus//resource//btn_message_selected.png");
        //添加按钮
        panelLeft.add(btnModule1);
        panelLeft.add(btnModule2);
        panelLeft.add(btnModule3);
        panelLeft.add(btnModule4);
        panelLeft.add(btnModule5);
        panelLeft.add(btnModule6);
        //打印测试
        //创建各模块各权限对应面板
        //用户管理模块
        JTabbedPane[] userModule = {
                new JTabbedPane(),
                new TabbedPanelUser_S(ID),
                new TabbedPanelUser_T(flag,ID),
                new TabbedPanelUser_A(flag,ID)};
        //学籍管理模块
        JTabbedPane[] schoolModule = {
                new JTabbedPane(),
                new TabbedPanelSchool_S(flag,ID),
                new TabbedPanelSchool_T(flag,ID),
                new TabbedPanelSchool_A(flag,ID)};
        //教务系统
        JTabbedPane[] courseModule = {
                new JTabbedPane(),
                new TabbedPanelCourse_S(flag,ID),
                new TabbedPanelCourse_T(flag,ID),
                new TabbedPanelCourse_A(flag)};
        //图书馆
        JTabbedPane[] LibraryModule = {
                new JTabbedPane(),
                new TabbedPanelLibrary_S(flag,ID),
                new TabbedPanelLibrary_T(flag,ID),
                new TabbedPanelLibrary_A(flag)};
        //商店
        JTabbedPane[] storeModule = {
                new JTabbedPane(),
                new TabbedPanelStore_S(flag,ID),
                new TabbedPanelStore_T(flag,ID),
                new TabbedPanelStore_A(flag)};
        //站内消息
        JTabbedPane[] messageModule = {
                new JTabbedPane(),
                new TabbedPanelMessage_A(flag,ID),
                new TabbedPanelMessage_A(flag,ID),
                new TabbedPanelMessage_A(flag,ID)};
        JTabbedPane [][] module ={userModule,schoolModule,courseModule,LibraryModule,storeModule,messageModule};
        for(int i=0;i<6;i++)
        {
            int finalI = i;
            int finalI1 = i;
            btn[i].addActionListener(new ActionListener() {
                int temp = finalI;
                @Override
                public void actionPerformed(ActionEvent e) {
                    current[0] = finalI;

                    for(int j=0;j<6;j++){
                        btn[j].setBackground(new Color(0x000000B, true));
                        btn[j].setOpaque(false);
                        btn[j].setForeground(Color.WHITE);
                        btn[j].setIcon(btnicon[j]);
                    }
                    btn[finalI1].setBackground(Color.WHITE);
                    btn[finalI1].setForeground(Color.BLACK);
                    btn[finalI1].setIcon(btnicon_selected[finalI]);
                    setPanelSwitch(module[finalI][flag]);
//                    System.out.println("用户管理系统");
                }
            });
            int finalI2 = i;
            btn[i].addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    btn[finalI2].setBackground(Color.WHITE);
                    btn[finalI2].setOpaque(true);
                    btn[finalI2].setForeground(Color.BLACK);
                    btn[finalI2].setIcon(btnicon_selected[finalI2]);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if(current[0]!=finalI2){
                        btn[finalI2].setBackground(new Color(0x0000001, true));
                        btn[finalI2].setOpaque(false);
                        btn[finalI2].setForeground(Color.WHITE);
                        btn[finalI2].setIcon(btnicon[finalI2]);
                    }
                }
            });
        }
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        ImageIcon exitIcon2 = new ImageIcon("Vcampus//resource//exit2.png");
        btnExit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnExit.setIcon(exitIcon2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnExit.setIcon(exitIcon);
            }
        });
        panelMain.add(bg);
    }

    //实现下方面板切换
    public void setPanelSwitch(JTabbedPane jtp)
    {
        panelBottom.removeAll();
        panelBottom.add(jtp);
        panelBottom.updateUI();
        panelBottom.repaint();
    }
    private void setTimer(JLabel time)
    {
        final JLabel varTime=time;
        Timer timeAction = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long timemillis = System.currentTimeMillis();
                //转换日期显示格式
                SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                varTime.setText(df.format(new Date(timemillis)));
            }
        });
        timeAction.start();
    }

}


