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
import com.vcampus.client.window.setjpCourse.mytabelrenderer.EvenOddRenderer;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Lesson;
import com.vcampus.pojo.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelTimeTable_S extends JPanel {

    private boolean existTable=false;
    private String studentID;
    private String[] columnName={"节数","周一","周二","周三","周四","周五","周六","周日"};
    private String[][] tableData=new String[13][8];//一周7天+纵向表头，一天13节课
    private Color[][] tableColor=new Color[13][8];//课表颜色
    private  JTable table=null;
    MessagePasser passer = ClientMessagePasser.getInstance();

    public PanelTimeTable_S(String ID){
        studentID=ID;
        this.setLayout(new CardLayout(20,20));

        JPanel panel=new JPanel();
        this.add(panel);

        //设置数据
        setTimeTableData();

        if(existTable){//有课表，排课
            panel.setLayout(new CardLayout(10,10));

            table=new JTable(tableData,columnName){
                @Override
                public boolean isCellEditable(int row, int column)
                {  // 表格不可编辑---
                    return false;
                }
            };

            table.setRowHeight(44);
            table.setFont(new Font("黑体",Font.PLAIN,20));

            table.setShowGrid(false);

            EvenOddRenderer tablecellRender = new EvenOddRenderer(tableColor);
            table.setDefaultRenderer(Object.class,tablecellRender);

            //table.getTableHeader().setVisible(true);
            table.getTableHeader().setFont(new Font("黑体",Font.PLAIN,20));
            table.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
            table.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列
            JScrollPane jsp=new JScrollPane(table);

            panel.add(jsp);

        } else{
            panel.setLayout(new BorderLayout());
            //标签
            JLabel lbl=new JLabel("暂无课表");
            lbl.setFont(new Font("宋体",Font.BOLD,30));
            lbl.setForeground(Color.GRAY);

            panel.add(lbl,BorderLayout.CENTER);
        }


    }

    private void setTimeTableData(){
        Student student=new Student();
        student.setStudentID(studentID);//设置学生ID
        Gson gson=new Gson();
        String s = gson.toJson(student);
        passer.send(new Message("admin", s, "lesson", "showtablename"));

        Message msg = passer.receive();
        Map<String, String> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, String>>() {}.getType());
        String res = map.get("res");

        if(res!=null && !res.equals("")){
            existTable=true;
            String [] stringArr= res.split(",");//转数组

            //数据处理，更方便处理课表
            for(int i=0;i<tableData.length;i++) {
                tableData[i][0]="第"+(i+1)+"节";
                tableColor[i][0]=new Color(220,220,220);
                for (int j = 1; j < tableData[i].length-2; j++) {
                    String temp=stringArr[i + 13*(j-1)];
                    if ("0".equals(temp)) {
                        tableData[i][j] = " ";
                        tableColor[i][j]=new Color(245,245,245);
                    } else {
                        tableData[i][j] = temp;
                        setTableColor(i,j);
                    }

                }
                tableData[i][tableData[i].length-2]=" ";//周六
                tableColor[i][tableData[i].length-2]=new Color(245,245,245);
                tableData[i][tableData[i].length-1]=" ";//周日
                tableColor[i][tableData[i].length-1]=new Color(245,245,245);
            }

        }else {
            existTable=false;
        }
    }

    private void setTableColor(int i,int j){
        int c=i + 13*(j-1);
        List<Integer> color1= Arrays.asList(0,1,10,11,12,31,32,52,53,62,63,64);
        List<Integer> color2= Arrays.asList(2,3,4,33,34,35,54,55,56);
        List<Integer> color3= Arrays.asList(5,6,26,27,36,37,38,57,58);
        List<Integer> color4= Arrays.asList(7,8,9,28,29,30,59,60,61);
        List<Integer> color5= Arrays.asList(13,14,23,24,25,44,45);
        List<Integer> color6= Arrays.asList(15,16,17,41,42,43,46,47,48);
        List<Integer> color7= Arrays.asList(18,19,49,50,51);
        List<Integer> color8= Arrays.asList(20,21,22,39,40,41);
        if(color1.contains(c))
            tableColor[i][j]=new Color(230,230,250);
        else if(color2.contains(c))
            tableColor[i][j]=new Color(187 ,255 ,255);
        else if(color3.contains(c))
            tableColor[i][j]=new Color(240,255,255);
        else if(color4.contains(c))
            tableColor[i][j]=new Color(255,255,224);
        else if(color5.contains(c))
            tableColor[i][j]=new Color(240,255,240);
        else if(color6.contains(c))
            tableColor[i][j]=new Color(255,239,213);
        else if(color7.contains(c))
            tableColor[i][j]=new Color(245,255,250);
        else if(color8.contains(c))
            tableColor[i][j]=new Color(255,228,225);
        else tableColor[i][j]=new Color(255,240,245);
    }

    public void upDateTable(){
        //更新信息
        setTimeTableData();
        //更新表格内容
        table.setModel(new DefaultTableModel(tableData,columnName));
        //table.setEnabled(true);
    }
}
