package com.vcampus.dao.utils;

import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 课表相关操作的工具类
 *
 * @author 刘骐
 * @date 2022/09/01
 */
public class ClassTable {
    /**
     * 把时间转为索引
     *
     * @param time 时间
     * @return {@link List}<{@link Integer}>
     */
    public static List<Integer> getTimeIndex(String time){
        System.out.println(time);
        String []str1 = time.split(",");
        List<Integer> timetable=new ArrayList<>();
        for(String str:str1){
            String[]temp=str.split("/");
            int s = (Integer.valueOf(temp[0])-1)*13+Integer.valueOf(temp[1])-1;
            int e = (Integer.valueOf(temp[0])-1)*13+Integer.valueOf(temp[2])-1;
            for(int i=s;i<=e;i++)
                timetable.add(i);
        }
        return timetable;
    }

    /**
     * 比较是否冲突
     *
     * @param lessonTime  课时间
     * @param lessonTable 课表
     * @return {@link Boolean}
     */
    public static Boolean compare(String lessonTime,String lessonTable){
        String []str2 = lessonTable.split(",");
        List<Integer>timetable = getTimeIndex(lessonTime);
        for(Integer temp:timetable){
            if(!Objects.equals(str2[temp], "0"))return false;
        }
        return true;
    }

    /**
     * 添加课到课表
     *
     * @param lessonID  教训id
     * @param studentID 学生一卡通
     * @param conn      连接
     * @throws Exception 异常
     */
    public static  void addToTable (String lessonID,String studentID, Connection conn) throws Exception {
        String sql1 = "select time from tb_LESSON where innerID = '"+lessonID+"'";
        String lessonTime =(String) CRUD.Query(sql1,conn).get(0).get("time");
        String sql2 = "select timeTable from tb_LESSONTABLE where studentID = '"+studentID+"'";
        List<Map<String,Object>> resultList = CRUD.Query(sql2,conn);
        if(resultList.isEmpty()) {
            String sqlextra = "insert into tb_LESSONTABLE (studentID) values('" + studentID + "')";
            CRUD.update(sqlextra,conn);
            resultList = CRUD.Query(sql2,conn);
        }
        String myTable = (String)resultList.get(0).get("timeTable");
        List<Integer>tableIndex = getTimeIndex(lessonTime);
        String[] table=myTable.split(",");
        for(Integer temp:tableIndex){
            table[temp]=lessonID;
        }
        String newTable = String.join(",",table);
        String sql3 = "update tb_LESSONTABLE set timeTable ='"+newTable+"'"+" where studentID = '"+studentID+"'";
        CRUD.update(sql3,conn);
    }
}
