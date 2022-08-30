package 模拟QQ;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.net.URL;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public class MyQQ_Frame extends JFrame{
    int x,y;
    int flag=1;//1--学生 2--老师 3--管理员

    //承接用户状态图标
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
    JLabel qq=new Create_label("2020",Color.white);
    //用户名输入框
    JTextField userfield=new Create_textfield(10);
    //密码输入框
    JPasswordField codefield=new Create_codefield(10);
    //登录按钮
    JButton button=new Create_Login_Button("登录");
    //资源文件路径
    URL url0=getClass().getResource("/img/用户.png");
    URL url1=getClass().getResource("/img/密码.png");
    URL url2=getClass().getResource("/img/关闭.png");
    URL url3=getClass().getResource("/img/背景.JPG");
    URL url4=getClass().getResource("/img/QQ.png");
    URL url5=getClass().getResource("/img/最小化.png");
    URL url6=getClass().getResource("/img/学生.png");
    URL url7=getClass().getResource("/img/教师.png");
    URL url8=getClass().getResource("/img/管理员.png");
    URL url9=getClass().getResource("/img/学校1.png");
    URL url10=getClass().getResource("/img/学校2.png");
    URL url11=getClass().getResource("/img/学校3.png");
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
    int left,top;
    //构造方法
    public MyQQ_Frame() {

        //设置窗体大小
        setSize(535,410);
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
        com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.9f);
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
        close_icon.setImage(close_icon.getImage().getScaledInstance((int)(0.05*width),(int)(0.05*width),Image.SCALE_DEFAULT));
        close.setIcon(close_icon);
        min_icon.setImage(min_icon.getImage().getScaledInstance((int)(0.06*width),(int)(0.06*width),Image.SCALE_DEFAULT));
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
        userfield.setBounds((int)(0.26*width),(int)(0.455*height),(int)(0.48*width),(int)(0.08*height));
        jPanel.add(userfield);
        //添加用户图标
        Users.setBounds((int)(0.21*width),(int)(0.455*height),(int)(0.05*width),(int)(0.08*height));
        jPanel.add(Users);
        codes.setBounds((int)(0.21*width),(int)(0.6*height),(int)(0.05*width),(int)(0.08*height));
        jPanel.add(codes);
        //添加密码
        codefield.setBorder(matteBorder);
        codefield.setBounds((int)(0.26*width),(int)(0.6*height),(int)(0.48*width),(int)(0.08*height));
        jPanel.add(codefield);
        Border border=BorderFactory.createEmptyBorder(1, 1, 1, 1);
        close.setFocusPainted(false);
        close.setBackground(Color.red);//加了才能不显示
        close.setOpaque(false);
        close.setBorder(border);
        close.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                close.setOpaque(true);
                close.setBackground(Color.red);
            }
            public void mouseExited(MouseEvent e) {
                close.setOpaque(false);
            }
        });
        close.addActionListener((e)->{
            this.dispose();
        });
        close.setBounds((int)(0.92*width),(int)(0*height),(int)(0.08*width),(int)(0.08*width));
        jPanel.add(close);
        min.setFocusPainted(false);
        min.setBackground(new Color(192, 192, 225, 131));;//加了才能不显示
        min.setOpaque(false);
        min.setBorder(border);
        min.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                min.setOpaque(true);
                min.setBackground(new Color(122, 122, 207, 255));
            }
            public void mouseExited(MouseEvent e) {
                min.setOpaque(false);
            }
        });
        min.addActionListener((e)->{
            this.setExtendedState(JFrame.ICONIFIED);
        });
        min.setBounds((int)(0.84*width),(int)(0*height),(int)(0.08*width),(int)(0.08*width));
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
                    student.setImage(student.getImage().getScaledInstance((int)(0.08*width),(int)(0.08*height),Image.SCALE_DEFAULT));
                    Status.setIcon(student);
                    Status.setHorizontalTextPosition(SwingConstants.LEFT);
                    Status.setText("学生");
                    setFont(new Font("宋体", 1, 20));
                    seu1.setImage(seu1.getImage().getScaledInstance((int)(1*width),(int)(0.4*height),Image.SCALE_DEFAULT));
                    bg.setIcon(seu1);
                    bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(0.4*height));
                    jPanel.add(bg);
                    break;
                }
                case 2:{
                    teacher.setImage(teacher.getImage().getScaledInstance((int)(0.08*width),(int)(0.08*height),Image.SCALE_DEFAULT));
                    Status.setIcon(teacher);
                    Status.setHorizontalTextPosition(SwingConstants.LEFT);
                    Status.setText("教师");
                    setFont(new Font("宋体", 1, 20));
                    seu2.setImage(seu2.getImage().getScaledInstance((int)(1*width),(int)(0.4*height),Image.SCALE_DEFAULT));
                    bg.setIcon(seu2);
                    bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(0.4*height));
                    jPanel.add(bg);
                    break;
                }
                case 3:{
                    administrator.setImage(administrator.getImage().getScaledInstance((int)(0.08*width),(int)(0.08*height),Image.SCALE_DEFAULT));
                    Status.setIcon(administrator);
                    Status.setHorizontalTextPosition(SwingConstants.LEFT);
                    Status.setText("管理员");
                    setFont(new Font("宋体", 1, 20));
                    seu3.setImage(seu3.getImage().getScaledInstance((int)(1*width),(int)(0.4*height),Image.SCALE_DEFAULT));
                    bg.setIcon(seu3);
                    bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(0.4*height));
                    jPanel.add(bg);
                    break;
                }
            }
//            this.setExtendedState(JFrame.ICONIFIED);
        });
        Status.setBounds((int)(0.80*width),(int)(1*height-0.1*width),(int)(0.20*width),(int)(0.1*width));
        jPanel.add(Status);
        tx.setBounds((int)(0.24*width),(int)(0.04*height),(int)(0.24*width),(int)(0.36*height));
//        jPanel.add(tx);
        qq.setBounds((int)(0.30*width),(int)(0.02*height),(int)(0.40*width),(int)(0.36*height));
        qq.setText("Vcampus");
        jPanel.add(qq);
//        bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(0.4*height));
//        jPanel.add(bg);
        seu1.setImage(seu1.getImage().getScaledInstance((int)(1*width),(int)(0.4*height),Image.SCALE_DEFAULT));
        bg.setIcon(seu1);
        bg.setBounds((int)(0*width),(int)(0*height),(int)(1*width),(int)(0.4*height));
        jPanel.add(bg);
        button.setBounds((int)(0.23*width),(int)(0.775*height),(int)(0.53*width),(int)(0.1*height));
        jPanel.add(button);
        button.addActionListener((e)->{
            String names=userfield.getText();
            @SuppressWarnings("deprecation")
            String mima=codefield.getText();
            Users []yonghu=new Users[5];
            for(int q=0;q<5;q++){
                yonghu[q]=new Users();
            }
            yonghu[0].Users="3320938770";
            yonghu[1].Users="1321824584";
            yonghu[2].Users="2939634393";
            yonghu[3].Users="1812352723";
            yonghu[4].Users="1369286377";
            int count=0;
            for(int i=0;i<5;i++){
                if(yonghu[i].Users.equals(names)&&mima.equals("123")){
                    count++;
                }
            }
            if(count==1){
                JOptionPane.showConfirmDialog(jPanel, "登录成功", "提示", JOptionPane.DEFAULT_OPTION);
                this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(this,"没有注册","登录失败",JOptionPane.WARNING_MESSAGE);
            }
        });
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
            setFont(new Font("微软雅黑", 1, 15));
            setHorizontalAlignment(SwingConstants.CENTER);
        }
    }
}

