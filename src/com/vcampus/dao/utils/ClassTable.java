package com.vcampus.dao.utils;

import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassTable {
    public static List<Integer> getTimeIndex(String time){
        String []str1 = time.split(",");
        List<Integer> timetable=new ArrayList<>();
        for(String str:str1){
            String[]temp=str.split("/");
            int s = Integer.valueOf(temp[0])*13+Integer.valueOf(temp[1])-1;
            int e = Integer.valueOf(temp[0])*13+Integer.valueOf(temp[2])-1;
            for(int i=s;i<=e;i++)
                timetable.add(i);
        }
        return timetable;
    }
    public static Boolean compare(String lessonTime,String lessonTable){
        String []str2 = lessonTable.split(",");
        List<Integer>timetable = getTimeIndex(lessonTime);
        for(Integer temp:timetable){
            if(!Objects.equals(str2[temp], "0"))return false;
        }
        return true;
    }
    public static  void addToTable (String lessonID,String studentID, Connection conn) throws Exception {
        String sql1 = "select time from tb_LESSON where innerID = '"+lessonID+"'";
        String lessonTime =(String) CRUD.Query(sql1,conn).get(0).get("time");
        String sql2 = "select timeTable from tb_LESSONTABLE where studentID = '"+studentID+"'";
        String myTable = (String) CRUD.Query(sql2,conn).get(0).get("timeTable");
        List<Integer>tableIndex = getTimeIndex(lessonTime);
        String[] table=myTable.split(",");
        for(Integer temp:tableIndex){
            table[temp]=lessonID;
        }
        String newTable = String.join(",",table);
        String sql3 = "update tb_LESSONTABLE set timeTable ='"+newTable+"'";
        CRUD.update(sql3,conn);
    }
}
