package com.vcampus.client.window.setjpCourse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.client.window.setjpCourse.mytabelrenderer.EvenOddRenderer;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Lesson;
import com.vcampus.pojo.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelAutoScheduling_A extends JPanel{

    Object[] columnNames={"课程名称", "授课教师一卡通号","上课时间","授课教室"};
    Object[][] tableData=new Object[][]{};//保存所有用户信息
    MyTablePanel_Course tableP=new MyTablePanel_Course(tableData,columnNames);
    JButton btn=null;
    SpringLayout layS=new SpringLayout();
    MessagePasser passer = ClientMessagePasser.getInstance();

    public PanelAutoScheduling_A(){
        this.setLayout(layS);

        btn=new JButton("自动排课");
        add(btn);
        btn.setPreferredSize(new Dimension(150,40));
        layS.putConstraint(layS.NORTH,btn,20,layS.NORTH,this);
        layS.putConstraint(layS.EAST,btn,-20,layS.EAST,this);

        //初始进入时，显示全部课程
        Lesson lesson=new Lesson();
        Gson gson = new Gson();
        String s = gson.toJson(lesson);
        Message msg =null;
        synchronized (passer) {
            passer.send(new Message("admin", s, "lesson", "get"));
            msg = passer.receive();
        }
        Map<String, java.util.List<Lesson>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, List<Lesson>>>(){}.getType());
        List<Lesson> res = map.get("res");
        //如果不空，构建表格
        if(res.size()!=0) {
            setData(res);
            setPanel();
        }else {
            System.out.println("教务-课表界面-初始-返回全部课程ERROR");
        }

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = gson.toJson(lesson);
                Message msg =null;
                synchronized (passer) {
                    passer.send(new Message("admin", s, "lesson", "get"));
                    msg = passer.receive();
                }
                Map<String, java.util.List<Lesson>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, List<Lesson>>>(){}.getType());
                List<Lesson> res = map.get("res");

                if(res!=null && res.equals("OK")) {
                    remove(tableP);
                    setData(res);
                    setPanel();
                }else{
                    System.out.println("自动排课失败");
                }
            }
        });
    }

    public void setData(List<Lesson> res){
        tableData=new Object[res.size()][];
        //"课程名称", "授课教师一卡通号","上课时间","授课教室"
        for(int i=0;i<res.size();i++) {
            tableData[i]=new Object[columnNames.length];
            tableData[i][0]=res.get(i).getName();
            tableData[i][1]=res.get(i).getTeacherID();
            tableData[i][2]=res.get(i).getTime();
            tableData[i][3]=res.get(i).getClassroom();
        }
    }
    /**
     * 设置下方表格
     */
    public void setPanel()
    {
        tableP=new MyTablePanel_Course(tableData,columnNames);
        this.add(tableP);
        tableP.getTable().getColumnModel().getColumn(1).setPreferredWidth(120);
        layS.putConstraint(layS.NORTH,tableP,20,layS.SOUTH,btn);
        layS.putConstraint(layS.EAST,tableP,10,layS.EAST,btn);
        layS.putConstraint(layS.SOUTH,tableP,-10,layS.SOUTH,this);
        layS.putConstraint(layS.WEST,tableP,20,layS.WEST,this);


        updateUI();
        repaint();
    }
}
