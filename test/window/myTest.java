package window;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.client.window.myLoginFrame;
import com.vcampus.client.window.myMainFrame;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.User;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class myTest extends JFrame {
    JTextField txtUserName = new JTextField(20);
    JPasswordField txtPassWord = new JPasswordField(20);
    JButton btnLogin = new JButton("登录");
    JLabel lblTitleLabel=new JLabel("测试");

    JLabel lblUserName = new JLabel("用户名:");

    JLabel lblPassWord = new JLabel("密码:");

    //加载图片
    ImageIcon icon=new ImageIcon("test/window/seu.png");
    //将图片放入label中
    JLabel label=new JLabel(icon);

    int flag=0;
    String userName = "";

    public myTest(String title)
    {
        //调用父类构造函数，设置窗口名称
        //添加面板
        JPanel panel = new JPanel();
        this.add(panel);
        //设置布局
        panel.setLayout(null);

        // 当关闭窗口时，退出整个程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置窗口的其他参数，如窗口大小
        this.setSize(858, 613);
        this.setResizable(false);//窗口大小不可改
        int width=this.getWidth();//获取窗口宽度
        int height=this.getHeight();//获取窗口高度 你也可以设置高度居中


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - this.getWidth()) / 2, (screenSize.height - this.getHeight()) / 2);

        // 显示窗口
        this.setVisible(true);

        //显示背景
        label.setBounds(0,0,this.getWidth(),this.getHeight());
        this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
        panel.setOpaque(false);
        JPanel j=(JPanel)this.getContentPane();
        j.setOpaque(false);



        //设置标题
        lblTitleLabel.setBounds((int) (width*0.02), (int) (height*0.3), (int) (width*0.09), (int) (height*0.1));
        lblTitleLabel.setFont(new Font("宋体",Font.BOLD, (int) (width*height*0.00004)));
        panel.add(lblTitleLabel);

        // 创建用户名和密码输入提示 JLabel
        lblUserName.setBounds((int) (width*0.02), (int) (height*0.3), (int) (width*0.09), (int) (height*0.1));
        lblUserName.setFont(new Font("宋体",Font.BOLD, (int) (width*height*0.00004)));
        panel.add(lblUserName);
        lblPassWord.setBounds((int) (width*0.02), (int) (height*0.45), (int) (width*0.09), (int) (height*0.1));
        lblPassWord.setFont(new Font("宋体",Font.BOLD, (int) (width*height*0.00004)));
        panel.add(lblPassWord);

        //创建文本框用于用户名和密码输入
        txtUserName.setBounds((int) (width*0.1), (int) (height*0.3), (int) (width*0.4), (int) (height*0.1));
        txtUserName.setFont(new Font("宋体",Font.BOLD, (int) (width*height*0.00004)));
        MatteBorder border = new MatteBorder(0, 0, 2, 0, new Color(0, 250, 154));// 创建边框
        txtUserName.setBorder(border);// 色绘制边框
        panel.add(txtUserName);
        txtPassWord.setBounds((int) (width*0.1), (int) (height*0.45), (int) (width*0.4), (int) (height*0.1));
        txtPassWord.setFont(new Font("宋体",Font.BOLD, (int) (width*height*0.00004)));

        panel.add(txtPassWord);

        //创建单选按钮并设置其位置
        JRadioButton radioBtn01 = new JRadioButton("学生");
        JRadioButton radioBtn02 = new JRadioButton("教师");
        JRadioButton radioBtn03 = new JRadioButton("管理员");
        radioBtn01.setBounds(40,135,80,25);
        radioBtn02.setBounds(120,135,80,25);
        radioBtn03.setBounds(200,135,80,25);
        panel.add(radioBtn01);
        panel.add(radioBtn02);
        panel.add(radioBtn03);

        // 创建按钮组，把两个单选按钮添加到该组
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(radioBtn01);
        btnGroup.add(radioBtn02);
        btnGroup.add(radioBtn03);

        // 创建登录按钮
        btnLogin.setBounds((int) (width*0.05), (int) (height*0.7), (int) (width*0.5), (int) (height*0.15));
        panel.add(btnLogin);
        btnLogin.setEnabled(false);

        //监听三个单选按钮选中事件，选中其中一个登录按钮才可使用
        responseSelected(radioBtn01,1);
        responseSelected(radioBtn02,2);
        responseSelected(radioBtn03,3);

        //设置登录按钮的监听事件
        btnLogin.addActionListener(new ActionListener() {
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
                    new myMainFrame("VCampus虚拟校园系统",flag,userName);

                    dispose();
                }
                else//未输入用户名或密码，无法登陆，弹出窗口提示
                {
                    JOptionPane.showMessageDialog(panel, "用户名或密码错误！", "警告", JOptionPane.ERROR_MESSAGE);
                    clearText();
                }
            }
        });
        this.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
//                System.out.println("windowStateChanged: " + e.getNewState());
            }
        });
        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
//                System.out.println("windowGainedFocus: 窗口得到焦点");
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
//                System.out.println("windowLostFocus: 窗口失去焦点");
            }
        });
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
//                System.out.println("windowOpened: 窗口首次变为可见时调用");
            }

            @Override
            public void windowClosing(WindowEvent e) {
//                System.out.println("windowClosing: 用户试图从窗口的系统菜单中关闭窗口时调用");
            }

            @Override
            public void windowClosed(WindowEvent e) {
//                System.out.println("windowClosed: 窗口调用 dispose 而将其关闭时调用");
            }

            @Override
            public void windowIconified(WindowEvent e) {
//                System.out.println("windowIconified: 窗口从正常状态变为最小化状态时调用");
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
//                System.out.println("windowDeiconified: 窗口从最小化状态变为正常状态时调用");
            }

            @Override
            public void windowActivated(WindowEvent e) {
//                System.out.println("windowActivated: 窗口变为活动状态时调用");
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
//                System.out.println("windowDeactivated: 窗口变为不再是活动状态时调用");
            }
        });
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // 鼠标保持按下状态移动即为拖动
//                System.out.println("鼠标拖动");
            }
            @Override
            public void mouseMoved(MouseEvent e) {
//                System.out.println("鼠标移动");
            }
        });
        this.addComponentListener(new ComponentAdapter() {//拖动窗口监听

            public void componentResized(ComponentEvent e) {
                System.out.println("窗口拖动");
                Resized();
            }
        });
}

    public Boolean readTextContent()
    {
        //读取输入的用户名和密码
        userName = txtUserName.getText().trim();
        String passWord = new String(txtPassWord.getPassword());

        MessagePasser passer = ClientMessagePasser.getInstance();
        User user = new User(userName, passWord, flag);
        Gson gson = new Gson();
        passer.send(new Message("no", gson.toJson(user), "login", "get"));

        Message msg = passer.receive();
        Map<String,Object> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String,Object>>(){}.getType());
        return map.get("res").equals("OK");
    }

    //选中学生/教师/管理员后的响应
    public void responseSelected(JRadioButton button, int num)
    {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(button.isSelected())//如果单选按钮选中，登录按钮才可使用，否则登录按钮无效
                {
                    btnLogin.setEnabled(true);//
                    flag=num;//学生——1，教师——2，管理员——3，用以区分权限
                }
            }
        });
    }

    public void clearText()
    {
        txtUserName.setText("");
        txtPassWord.setText("");
    }
    public void Resized()
    {
        System.out.println(this.getWidth());
        int width=this.getWidth();//获取窗口宽度
        int height=this.getHeight();//获取窗口高度 你也可以设置高度居中

        label.setBounds(0,0,this.getWidth(),this.getHeight());
        //图片
        lblTitleLabel.setBounds((int) (width*0.15), (int) (height*0.05), (int) (width*0.3), (int) (height*0.2));
        lblTitleLabel.setFont(new Font("宋体",Font.BOLD, (int) (width*height*0.00015)));
        //标题

        lblUserName.setBounds((int) (width*0.02), (int) (height*0.3), (int) (width*0.09), (int) (height*0.1));
        lblUserName.setFont(new Font("宋体",Font.BOLD, (int) (width*height*0.00004)));

        lblPassWord.setBounds((int) (width*0.02), (int) (height*0.45), (int) (width*0.09), (int) (height*0.1));
        lblPassWord.setFont(new Font("宋体",Font.BOLD, (int) (width*height*0.00004)));

        txtUserName.setBounds((int) (width*0.1), (int) (height*0.3), (int) (width*0.4), (int) (height*0.1));
        txtUserName.setFont(new Font("宋体",Font.BOLD, (int) (width*height*0.00004)));
        //(起始点x，起始点y，宽地w，高h) 标签设置宽高不明显//将lable放在 窗口左边的1/2处
//
        txtPassWord.setBounds((int) (width*0.1), (int) (height*0.45), (int) (width*0.4), (int) (height*0.1));
        txtPassWord.setFont(new Font("宋体",Font.BOLD, (int) (width*height*0.00004)));
        //(起始点x，起始点y，宽地w，高h)//宽度始终是窗口的1/2
//
        btnLogin.setBounds((int) (width*0.05), (int) (height*0.7), (int) (width*0.5), (int) (height*0.15));
        //(起始点x，起始点y，宽地w，高h)
    }
    public static void main(String[] args){
        myTest frame = new myTest("登录");
    }

}
