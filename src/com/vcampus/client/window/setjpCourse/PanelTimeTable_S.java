/** ===================================================
 * Title: PanelStudentManage_T.java
 * Created: [2022-8-30  00:53:12] by  张星喆
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 教务系统-学生-查看课表
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-30,创建此文件
 *2. 2022-8-30,完善设置
 *3.2022-8-30,前后端连接 修改人：张星喆
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window.setjpCourse;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Lesson;
import com.vcampus.pojo.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelTimeTable_S extends JPanel {

    private boolean existTable=false;
    private String studentID;
    private String[] columnName={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    private String[][] tableData=new String[13][8];//一周7天+纵向表头，一天13节课
    MessagePasser passer = ClientMessagePasser.getInstance();

    public PanelTimeTable_S(String ID){
        studentID=ID;
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
        Student student=new Student();
        student.setStudentID(studentID);//设置学生ID
        Gson gson=new Gson();
        String s = gson.toJson(student);
        passer.send(new Message("admin", s, "lesson", "showtable"));

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
                    String temp=stringArr[i + 13*(j-1)];
                    if ("0".equals(temp)) {
                        tableData[i][j] = " ";
                    } else {
                        tableData[i][j] = temp;
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
