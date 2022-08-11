package dao;

import org.junit.Test;
import vo.lesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class forLessons {

    public static List<lesson> query(String sql) throws Exception {//²éÑ¯
        try {
            List<lesson>listOfLesson = new ArrayList<lesson>();
            Connection conn = databaseConn.getConn();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()){
                String lessonID = rs.getString("lessonID");
                String name     = rs.getString("name");
                String teacher  = rs.getString("teacher");
                int maxSize     = rs.getInt("maxSize");
                int leftSize    = rs.getInt("leftSize");
                String time     = rs.getString("time");
                listOfLesson.add(new lesson(lessonID,name,teacher,maxSize,leftSize,time));
            }
            return listOfLesson;
        }catch (Exception e)
        {
            System.out.println("²é¿´Ê§°Ü");
            return null;
        }
    }
}
