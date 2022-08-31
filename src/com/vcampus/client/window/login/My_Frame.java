package com.vcampus.client.window.login;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.client.window.myMainFrame;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.User;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.awt.Robot;

import java.util.Date;
import com.vcampus.client.window.showMessageFrame;

public class My_Frame extends JFrame{
    int x,y;
    int flag=1;//1--学生 2--老师 3--管理员

    //承接用户状态图标
//    JButton Status=new JButton("学生");
    JButton Status=new JButton("学生");
    //承接用户名图标
    JLabel Users=new JLabel();
    //承接密码图标
    JLabel codes=new JLabel();
    //承接关闭图标
    JButton close=new JButton();

    //承接最小化图标
    JButton min=new JButton();
    //承接背景图片
    JLabel bg=new JLabel();
    //承接头像图片
    JLabel tx=new JLabel();
    //写文字QQ
    JLabel qq=new Create_label("Welcome for Vcampus!",Color.black);
    JLabel qq1=new Create_label("用户名",Color.GRAY);
    JLabel qq2=new Create_label("密码",Color.GRAY);
    //用户名输入框
    JTextField userfield=new Create_textfield(10);
    //密码输入框
    JPasswordField codefield=new Create_codefield(10);
    //登录按钮
    JButton button=new Create_Login_Button("登录");
    String username = null;
    //资源文件路径
    String url0="Vcampus/img//用户.png";
    String url1="Vcampus/img//密码.png";
    String url2="Vcampus/img//关闭.png";
    String url3="Vcampus/img//背景.JPG";
    String url4="Vcampus/img//QQ.png";
    String url5="Vcampus/img//最小化.png";
    String url6="Vcampus/img//学生.png";
    String url7="Vcampus/img//教师.png";
    String url8="Vcampus/img//管理员.png";
    String url9="Vcampus/img//学校1.png";
    String url10="Vcampus/img//学校2.png";
    String url11="Vcampus/img//学校3.png";
    String url12="Vcampus/img//背景1.png";
    String url13="Vcampus/img//背景2.png";
    String url14="Vcampus/img//背景3.png";
    //声明图标
    ImageIcon Users_icon=new ImageIcon(url0);
    ImageIcon codes_icon=new ImageIcon(url1);
    ImageIcon close_icon=new ImageIcon(url2);

    ImageIcon min_icon=new ImageIcon(url5);
    Icon bg_icon=new ImageIcon(url3);
    ImageIcon tengxun=new ImageIcon(url4);

    ImageIcon student=new ImageIcon(url6);
    ImageIcon teacher=new ImageIcon(url7);
    ImageIcon administrator=new ImageIcon(url8);

    ImageIcon seu1=new ImageIcon(url9);
    ImageIcon seu2=new ImageIcon(url10);
    ImageIcon seu3=new ImageIcon(url11);

    ImageIcon seubg1=new ImageIcon(url12);
    ImageIcon seubg2=new ImageIcon(url13);
    ImageIcon seubg3=new ImageIcon(url14);
    int left,top;
    //构造方法
    public My_Frame() {

        //设置窗体大小
        setSize(1300, 800);
        //去除标题栏
        setUndecorated(true);
        //设置可见性
        setVisible(true);
        this.setResizable(false);//窗口大小不可改
        int width=this.getWidth();//获取窗口宽度
        int height=this.getHeight();//获取窗口高度 你也可以设置高度居中


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - this.getWidth()) / 2, (screenSize.height - this.getHeight()) / 2);


        // 设置窗体透明度
//        com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.9f);
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
        //创建容器JPanel
        JPanel jPanel=new JPanel();
        //取代顶层容器
        this.setContentPane(jPanel);
        //添加图标
        Users_icon.setImage(Users_icon.getImage().getScaledInstance((int)(0.05*width),(int)(0.06*height),Image.SCALE_DEFAULT));
        Users.setIcon(Users_icon);
        codes_icon.setImage(codes_icon.getImage().getScaledInstance((int)(0.05*width),(int)(0.06*height),Image.SCALE_DEFAULT));
        codes.setIcon(codes_icon);
//        bg.setIcon(bg_icon);
        close_icon.setImage(close_icon.getImage().getScaledInstance((int)(0.015*width),(int)(0.015*width),Image.SCALE_DEFAULT));
        close.setIcon(close_icon);
        min_icon.setImage(min_icon.getImage().getScaledInstance((int)(0.02*width),(int)(0.02*width),Image.SCALE_DEFAULT));
        min.setIcon(min_icon);
        tengxun.setImage(tengxun.getImage().getScaledInstance((int)(0.24*width),(int)(0.36*height),Image.SCALE_DEFAULT));
        tx.setIcon(tengxun);
//        ImageIcon student=new ImageIcon(url6);
//        ImageIcon teacher=new ImageIcon(url7);
//        ImageIcon administrator=new ImageIcon(url8);
        student.setImage(student.getImage().getScaledInstance((int)(0.08*width),(int)(0.08*height),Image.SCALE_DEFAULT));
        Status.setIcon(student);
        Status.setHorizontalTextPosition(SwingConstants.LEFT);
        //设置背景
        jPanel.setBackground(Color.white);
        jPanel.setOpaque(true);
        //设置布局
//        jPanel.setLayout(new SimpleLayout());
        jPanel.setLayout(null);
        //添加文本框
        MatteBorder matteBorder=new MatteBorder(0, 0, 1, 0, Color.gray);
        userfield.setBorder(matteBorder);
        userfield.setBounds((int)(0.32*width),(int)(0.38*height),(int)(0.34*width),(int)(0.06*height));
        jPanel.add(userfield);
        //添加用户图标
        Users.setBounds((int)(0.21*width),(int)(0.455*height),(int)(0.05*width),(int)(0.08*height));
//        jPanel.add(Users);
        codes.setBounds((int)(0.21*width),(int)(0.6*height),(int)(0.05*width),(int)(0.08*height));
//        jPanel.add(codes);
        //添加密码
        codefield.setBorder(matteBorder);
        codefield.setBounds((int)(0.32*width),(int)(0.50*height),(int)(0.34*width),(int)(0.06*height));
        jPanel.add(codefield);
        Border border=BorderFactory.createEmptyBorder(1, 1, 1, 1);
        close.setFocusPainted(false);
        close.setBackground(Color.red);//加了才能不显示
        close.setOpaque(false);
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
        });
        close.setBounds((int)(0.95*width),(int)(0*height),(int)(0.05*width),(int)(0.05*height));
        jPanel.add(close);
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
        jPanel.add(min);
        //添加状态
        Status.setFocusPainted(false);
        Status.setBackground(new Color(192, 192, 225, 131));;//加了才能不显示
        Status.setOpaque(false);
        Status.setBorder(border);
        Status.addActionListener((e)->{
            flag=(flag)%3+1;
            switch(flag){
                case 1:{
                    student.setImage(student.getImage().getScaledInstance((int)(0.03*width),(int)(0.025*width),Image.SCALE_DEFAULT));
                    Status.setIcon(student);
                    Status.setHorizontalTextPosition(SwingConstants.LEFT);
                    Status.setText("学生");
                    Status.setFont(new Font("黑体", 1, 20));
//                    seu1.setImage(seu1.getImage().getScaledInstance((int)(1*width),(int)(0.4*height),Image.SCALE_DEFAULT));
//                    bg.setIcon(seu1);
//                    bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(0.4*height));
                    seubg1.setImage(seubg1.getImage().getScaledInstance((int)(1*width),(int)(1*height),Image.SCALE_DEFAULT));
                    bg.setIcon(seubg1);
                    bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(1*height));
                    jPanel.add(bg);
                    break;
                }
                case 2:{
                    teacher.setImage(teacher.getImage().getScaledInstance((int)(0.03*width),(int)(0.025*width),Image.SCALE_DEFAULT));
                    Status.setIcon(teacher);
                    Status.setHorizontalTextPosition(SwingConstants.LEFT);
                    Status.setText("教师");
                    Status.setFont(new Font("黑体", 1, 20));
//                    seu2.setImage(seu2.getImage().getScaledInstance((int)(1*width),(int)(0.4*height),Image.SCALE_DEFAULT));
//                    bg.setIcon(seu2);
//                    bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(0.4*height));
                    seubg2.setImage(seubg2.getImage().getScaledInstance((int)(1*width),(int)(1*height),Image.SCALE_DEFAULT));
                    bg.setIcon(seubg2);
                    bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(1*height));
                    jPanel.add(bg);
                    break;
                }
                case 3:{
                    administrator.setImage(administrator.getImage().getScaledInstance((int)(0.03*width),(int)(0.025*width),Image.SCALE_DEFAULT));
                    Status.setIcon(administrator);
                    Status.setHorizontalTextPosition(SwingConstants.LEFT);
                    Status.setText("管理员");
                    Status.setFont(new Font("黑体", 1, 18));
//                    seu3.setImage(seu3.getImage().getScaledInstance((int)(1*width),(int)(0.4*height),Image.SCALE_DEFAULT));
//                    bg.setIcon(seu3);
//                    bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(0.4*height));
                    seubg3.setImage(seubg3.getImage().getScaledInstance((int)(1*width),(int)(1*height),Image.SCALE_DEFAULT));
                    bg.setIcon(seubg3);
                    bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(1*height));
                    jPanel.add(bg);
                    break;
                }
            }
//            this.setExtendedState(JFrame.ICONIFIED);
        });
//        Status.addActionListener((e)->{
//            flag=(flag)%3+1;
//            switch(flag){
//                case 1:{
//                    student.setImage(student.getImage().getScaledInstance((int)(0.03*width),(int)(0.025*width),Image.SCALE_DEFAULT));
//                    Status.setIcon(student);
//                    Status.setHorizontalTextPosition(SwingConstants.LEFT);
//                    Status.setText("学生");
//                    Status.setFont(new Font("黑体", 1, 20));
////                    seu1.setImage(seu1.getImage().getScaledInstance((int)(1*width),(int)(0.4*height),Image.SCALE_DEFAULT));
////                    bg.setIcon(seu1);
////                    bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(0.4*height));
//                    seubg1.setImage(seubg1.getImage().getScaledInstance((int)(1*width),(int)(1*height),Image.SCALE_DEFAULT));
//                    bg.setIcon(seubg1);
//                    bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(1*height));
//                    jPanel.add(bg);
//                    break;
//                }
//                case 2:{
//                    teacher.setImage(teacher.getImage().getScaledInstance((int)(0.03*width),(int)(0.025*width),Image.SCALE_DEFAULT));
//                    Status.setIcon(teacher);
//                    Status.setHorizontalTextPosition(SwingConstants.LEFT);
//                    Status.setText("教师");
//                    Status.setFont(new Font("黑体", 1, 20));
////                    seu2.setImage(seu2.getImage().getScaledInstance((int)(1*width),(int)(0.4*height),Image.SCALE_DEFAULT));
////                    bg.setIcon(seu2);
////                    bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(0.4*height));
//                    seubg2.setImage(seubg2.getImage().getScaledInstance((int)(1*width),(int)(1*height),Image.SCALE_DEFAULT));
//                    bg.setIcon(seubg2);
//                    bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(1*height));
//                    jPanel.add(bg);
//                    break;
//                }
//                case 3:{
//                    administrator.setImage(administrator.getImage().getScaledInstance((int)(0.03*width),(int)(0.025*width),Image.SCALE_DEFAULT));
//                    Status.setIcon(administrator);
//                    Status.setHorizontalTextPosition(SwingConstants.LEFT);
//                    Status.setText("管理员");
//                    Status.setFont(new Font("黑体", 1, 20));
////                    seu3.setImage(seu3.getImage().getScaledInstance((int)(1*width),(int)(0.4*height),Image.SCALE_DEFAULT));
////                    bg.setIcon(seu3);
////                    bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(0.4*height));
//                    seubg3.setImage(seubg3.getImage().getScaledInstance((int)(1*width),(int)(1*height),Image.SCALE_DEFAULT));
//                    bg.setIcon(seubg3);
//                    bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(1*height));
//                    jPanel.add(bg);
//                    break;
//                }
//            }
////            this.setExtendedState(JFrame.ICONIFIED);
//        });
        student.setImage(student.getImage().getScaledInstance((int)(0.03*width),(int)(0.025*width),Image.SCALE_DEFAULT));
        Status.setIcon(student);
        Status.setHorizontalTextPosition(SwingConstants.LEFT);
        Status.setText("学生");
        Status.setFont(new Font("黑体", 1, 20));
        Status.setBounds((int)(0.63*width),(int)(0.66*height),(int)(0.08*width),(int)(0.045*width));

        jPanel.add(Status);
        tx.setBounds((int)(0.24*width),(int)(0.04*height),(int)(0.24*width),(int)(0.36*height));
//        jPanel.add(tx);
        qq.setBounds((int)(0.32*width),(int)(0.12*height),(int)(0.40*width),(int)(0.36*height));
        qq.setFont(new Font("微软雅黑", 1, 26));
        qq.setHorizontalAlignment(SwingConstants.LEFT);
        jPanel.add(qq);
        qq1.setBounds((int)(0.32*width),(int)(0.18*height),(int)(0.40*width),(int)(0.36*height));
        qq1.setFont(new Font("微软雅黑", 1, 20));
        qq1.setHorizontalAlignment(SwingConstants.LEFT);
        jPanel.add(qq1);
        qq2.setBounds((int)(0.32*width),(int)(0.30*height),(int)(0.40*width),(int)(0.36*height));
        qq2.setFont(new Font("微软雅黑", 1, 20));
        qq2.setHorizontalAlignment(SwingConstants.LEFT);
        jPanel.add(qq2);
//        bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(0.4*height));
//        jPanel.add(bg);
//        seu1.setImage(seu1.getImage().getScaledInstance((int)(1*width),(int)(0.4*height),Image.SCALE_DEFAULT));
//        bg.setIcon(seu1);
//        bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(0.4*height));
        seubg1.setImage(seubg1.getImage().getScaledInstance((int)(1*width),(int)(1*height),Image.SCALE_DEFAULT));
        bg.setIcon(seubg1);
        bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(1*height));

        button.setBounds((int)(0.32*width),(int)(0.62*height),(int)(0.06*width),(int)(0.06*height));
        jPanel.add(button);
        jPanel.add(bg);

        button.addActionListener(new ActionListener() {
            @Override
                public void actionPerformed(ActionEvent e) {
                    if(readTextContent())
                    {
                        switch(flag)//学生——1，教师——2，管理员——3，用以区分权限
                        {
                            case(1):
                                System.out.println("学生申请登录");
                                break;
                            case(2):
                                System.out.println("教师申请登录");
                                break;
                            case(3):
                                System.out.println("管理员申请登录");
                                break;
                        }
                        //loginFrame.setVisible(false);
                        System.out.println("跳转完成");
                        new myMainFrame("VCampus虚拟校园系统",flag,username);
                        dispose();
                    }
                    else//未输入用户名或密码，无法登陆，弹出窗口提示
                    {
//                        screenSize.width
                        showMessageFrame test=new showMessageFrame("用户名或密码错误!",(int)(screenSize.width/2-0.19*width),(int)(screenSize.height/2-0.45*height),(int)(0.4*width),(int)(0.08*height));
//                        JOptionPane.showMessageDialog(jPanel, "用户名或密码错误！", "警告", JOptionPane.ERROR_MESSAGE);
                        clearText();
                    }
            }
        });
    }
    public Boolean readTextContent()
    {
        //读取输入的用户名和密码
        username = userfield.getText().trim();
        String passWord = new String(codefield.getPassword());
        System.out.println(username);
        System.out.println(passWord);
        System.out.println(flag);
        MessagePasser passer = ClientMessagePasser.getInstance();
        User user = new User(username, passWord, flag);
        Gson gson = new Gson();
        passer.send(new Message("no", gson.toJson(user), "login", "get"));

        Message msg = passer.receive();
        Map<String,Object> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String,Object>>(){}.getType());
        return map.get("res").equals("OK");
    }

    //选中学生/教师/管理员后的响应

    public void clearText()
    {
        userfield.setText("");
        codefield.setText("");
    }
    private class Create_textfield extends JTextField{
        public Create_textfield(int n) {
            super(n);
            setFont(new Font("宋体", 1, 30));
            setPreferredSize(new Dimension(215, 30));
        }
    }
    private class Create_codefield extends JPasswordField{
        public Create_codefield(int n) {
            super(n);
            setFont(new Font("宋体", 1, 30));
            setPreferredSize(new Dimension(215, 30));
        }
    }
    private class Create_label extends JLabel{
        public Create_label(String name,Color color) {
            super(name);
            setFont(new Font("微软雅黑", 1, 40));
            setForeground(color);
            setHorizontalAlignment(SwingConstants.CENTER);
        }
    }
    private class Create_Login_Button extends JButton{
        public Create_Login_Button(String text) {
            super(text);
            setBackground(new Color(0,191,255));
            setPreferredSize(new Dimension(215, 37));
            setForeground(Color.white);
            setFocusPainted(false);
            setFont(new Font("微软雅黑", 1, 20));
            setHorizontalAlignment(SwingConstants.CENTER);
        }
    }
}


