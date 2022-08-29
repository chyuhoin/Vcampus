package com.vcampus.client.window.setjpCourse.mypaneltable.MyExtensionPanel;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {

   // private static final long serialVersionUID = 1L;
    private Object[][] Info=new Object[0][];
    private String lessonID=null;//课程ID
    private String studentID=null;//学生ID

    public ContentPanel(String lID,String sID,Object[][] info) {
        super();
        lessonID=lID;
        studentID=sID;
        Info=info;
        createContent();
    }

    public void createContent() {
        int w = this.getPreferredSize().width;
        int h = this.getPreferredSize().height;

        int numTeacher=Info.length;
        int numPanel=numTeacher;

        JPanel jp=new JPanel();//网格布局
        GridLayout layout = new GridLayout(3, 3);
        jp.setLayout(layout);
        // 设置 水平 和 竖直 间隙
        layout.setHgap(10);
        layout.setVgap(10);
        //设置网格
        if(numTeacher<=3) {
            layout.setRows(1);layout.setColumns(3);
            numPanel=3;
        }
        else if(numTeacher<=6) {
            layout.setRows(2);layout.setColumns(3);
            numPanel=6;
        }
        else {
            layout.setRows(3);layout.setColumns(3);
            numPanel=9;
        }


        //小panel
        SubContentPanel[] subjp = new SubContentPanel[numPanel];
        for(int i=0;i<numPanel;i++){
            if(i<numTeacher)
                subjp[i]=new SubContentPanel(lessonID,studentID,Info[i]);
            else subjp[i]=new SubContentPanel();//空panel

            jp.add(subjp[i]);
        }


        this.setLayout(new CardLayout(10,10));
        this.add(jp);

    }

    public static void main(String[] args) {
        JFrame jf = new JFrame("测试窗口");
        jf.setSize(600, 400);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);

        Object[][] str={
                {"张三","1-01-02",50,"ID1"},
                {"张三","1-01-02",50,"ID2"},
                {"张三","1-01-02",50,"ID3"},
                {"张三","1-01-02",50,"ID4"}
        };
        ContentPanel panel=new ContentPanel("0001","00000001",str);


        jf.setContentPane(panel);
        jf.setVisible(true);
    }

}