package 模拟QQ;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Create_GUI {
    public static void Create_GUI(){

//        JFrame frame=new MyQQ_Frame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame frame=new My_Frame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(()->{
            Create_GUI();
        });
    }
}
