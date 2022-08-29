package 模拟QQ;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

public class MyQQ_Frame extends JFrame {
    public MyQQ_Frame() {
        //设置窗体大小
        setSize(425,330);
        //去除标题栏
        setUndecorated(true);
        //设置可见性
        setVisible(true);
        //设置透明度
        com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.8f);

        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // 鼠标保持按下状态移动即为拖动
//                System.out.println("鼠标拖动");
                int left=getLocation().x;
                int top=getLocation().y;
//                setLocation(left+e.getX()-x, top+e.getY()-y);
            }
            @Override
            public void mouseMoved(MouseEvent e) {
//                System.out.println("鼠标移动");
            }
        });
        //由于没有标题栏所以界面不能拖动改变位置
        //采取以下方法可以解决
        //静态鼠标触发器
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
//				System.out.println("鼠标按下");
                //当鼠标点击时获取距离屏幕的坐标
//                x=e.getX();
//                y=e.getY();
            }
        });
        //动态鼠标触发器
//        addMouseMotionListener(new MouseMotionAdapter() {
//            public void mouseDragged(MouseEvent e){
////				System.out.println("鼠标拖动");
//                //获取当前位置的横坐标和纵坐标 left and top
//
//                //横向移动距离=横向动态坐标-鼠标点击时的横向静态坐标
//                //纵向移动距离=纵向动态坐标-鼠标点击时的纵向静态坐标
//                //设置可变化的位置 加上原来的位置即可
//                left=getLocation().x;
//                top=getLocation().y;
//                setLocation(left+e.getX()-x, top+e.getY()-y);
//            }
//        });

    }
}

