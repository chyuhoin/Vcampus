package com.vcampus.client.window.setjpCourse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Student;
import com.vcampus.pojo.Teacher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PanelTimeTable_T extends JPanel {
    private boolean existTable=false;
    private String teacherID;
    private String[] columnName={"节数","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    private String[][] tableData=new String[13][8];//一周7天+纵向表头，一天13节课

    MessagePasser passer = ClientMessagePasser.getInstance();

    public PanelTimeTable_T(String ID){
        teacherID=ID;
        this.setLayout(new CardLayout(20,20));

        JPanel panel=new JPanel();
        this.add(panel);

        setTimeTable();
        if(existTable){//有课表，排课
            panel.setLayout(new CardLayout(10,10));

            Integer[] temp={2,4};
            JTable table=new MyTable(new DefaultTableModel(tableData,columnName),temp);
            table.setRowHeight(30);
            table.setFont(new Font("黑体",Font.PLAIN,20));

            table.getTableHeader().setVisible(true);
            table.getTableHeader().setFont(new Font("黑体",Font.PLAIN,20));
            //table.getColumn(0).setMaxWidth(60);

            panel.add(table);

        } else{
            panel.setLayout(new BorderLayout());
            //标签
            JLabel lbl=new JLabel("暂无课表");
            lbl.setFont(new Font("宋体",Font.BOLD,30));
            lbl.setForeground(Color.GRAY);

            panel.add(lbl,BorderLayout.CENTER);
        }


    }

    private void setTimeTable(){
        Teacher teacher=new Teacher();
        teacher.setTeacherID(teacherID);
        Gson gson=new Gson();
        String s = gson.toJson(teacher);
        passer.send(new Message("admin", s, "lesson", "showtime"));

        Message msg = passer.receive();
        Map<String, String> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, String>>() {}.getType());
        String res = map.get("res");

        if(res!=null && !res.equals("")){
            existTable=true;
            String [] stringArr= res.split(",");//转数组

            //数据处理，更方便处理课表
            for(int i=0;i<tableData.length;i++) {
                tableData[i][0]="第"+(i+1)+"节";
                for (int j = 1; j < tableData[i].length-2; j++) {
                    String temp = stringArr[i + 13*(j-1)];
                    switch (temp){
                        case "0":
                            tableData[i][j] = " ";break;
                        case "1":
                            tableData[i][j] = "非偏好时间";break;
                        default:
                            tableData[i][j] = temp;break;
                    }
                }
                tableData[i][tableData[i].length-2]=" ";//周六
                tableData[i][tableData[i].length-1]=" ";//周日
            }

        }else {
            existTable=false;
        }
    }

}
