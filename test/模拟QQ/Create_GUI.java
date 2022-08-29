package 模拟QQ;

import javax.swing.*;

public class Create_GUI {
    public static void Create_GUI(){
        JFrame frame=new MyQQ_Frame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(()->{
            Create_GUI();
        });
    }
}
