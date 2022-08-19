package com.vcampus.dao;

import com.sun.org.apache.bcel.internal.generic.Type;
import com.vcampus.dao.utils.CRUD;
import com.vcampus.dao.utils.databaseConn;
import com.vcampus.dao.utils.mapToBean;
import com.vcampus.pojo.Book;
import com.vcampus.pojo.Exam;
import com.vcampus.pojo.Lesson;
import com.vcampus.pojo.Student;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.sun.org.apache.bcel.internal.generic.Type.STRING;

public class LessonDao extends BaseDao{
    //无参时查询所有课程
    public static List<Lesson> search() throws Exception {
//        String sql = "select * from tb_LESSON";
//        List<Map<String, Object>> resultList = CRUD.Query(sql,conn);
//        List<Lesson> result = new ArrayList<>();
//        for (Map<String, Object> map : resultList) {
//            Lesson lesson = mapToBean.map2Bean(map, Lesson.class);
//            result.add(lesson);
//        }
        return searchAll(Lesson.class,"tb_LESSON");
    }

    //有参按条件查询
    public static List<Lesson> search(String field, Object value) throws Exception {
//        String sql = "select * from tb_LESSON where " + field + " = '" + value + "'";
//        List<Map<String, Object>> resultList = CRUD.Query(sql,conn);
//        List<Lesson> result = new ArrayList<>();
//        for (Map<String, Object> map : resultList) {
//            Lesson lesson = mapToBean.map2Bean(map, Lesson.class);
//            result.add(lesson);
//        }
        return searchBy(Lesson.class,"tb_LESSON",field,value);
    }

    //查询自己选的课信息
    public static List<Lesson> searchMine(String studentID) throws Exception {
        String sql = "SELECT tb_STUDENTWITHLESSON.* from tb_LESSON ,tb_STUDENTWITHLESSON " +
                "WHERE tb_LESSON.innerID = tb_STUDENTWITHLESSON.innerID " +
                "and tb_STUDENTWITHLESSON.studentID = '" + studentID + "'";
        List<Map<String, Object>> resultList = CRUD.Query(sql,conn);
        List<Lesson> result = new ArrayList<>();
        for (Map<String, Object> map : resultList) {
            Lesson lesson = mapToBean.map2Bean(map, Lesson.class);
            result.add(lesson);
        }
        return result;
    }

    //学生选课
    public static Boolean selectLesson(String studentID, String innerID) throws Exception {
        String judgeSql = "select * from tb_STUDENTWITHLESSON where studentID = '" + studentID + "' and innerID = '" + innerID + "'";
        List<Map<String, Object>> judge = CRUD.Query(judgeSql,conn);
        if (judge.isEmpty()) {
            try {
                String sql1 = "update tb_LESSON set leftSize=leftSize-1 where innerID = '" + innerID + "'";
                String sql2 = "insert into tb_STUDENTWITHLESSON (studentID,innerID) values ('" + studentID + "','" + innerID + "')";
                Connection conn = databaseConn.getConn();
                conn.setAutoCommit(false);
                Statement stm = conn.createStatement();
                stm.executeUpdate(sql1);
                stm.executeUpdate(sql2);
                conn.commit();
                stm.close();
                conn.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }
    //添加课程
    public static Boolean addLesson(Lesson lesson) throws Exception {
       // try {
            String sql = "insert into tb_LESSON (innerID,lessonID,name,teacherID,maxSize,leftSize,time,school,major,isExam) values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
           // if(lesson.getInnerID()!=null)
                ps.setString(1,lesson.getInnerID());
//            else
//                ps.setNull(1, Types.VARCHAR);
//            if(lesson.getLessonID()!=null)
                ps.setString(2,lesson.getLessonID());
//            else
//                ps.setNull(2, Types.VARCHAR);
//            if(lesson.getName()!=null)
                ps.setString(3,lesson.getName());
//            else
//                ps.setNull(3,Types.VARCHAR);
//            if(lesson.getTeacherID()!=null)
                ps.setString(4,lesson.getTeacherID());
//            else
//                ps.setNull(4,Types.VARCHAR);
//            if(lesson.getMaxSize()!=null)
                ps.setInt(5,lesson.getMaxSize());
//            else
//                ps.setNull(5,Types.INTEGER);
//            if(lesson.getLeftSize()!=null)
                ps.setInt(6,lesson.getLeftSize());
//            else
//                ps.setNull(6,Types.INTEGER);
//            if(lesson.getTime()!=null)
                ps.setString(7,lesson.getTime());
//            else
//                ps.setNull(7,Types.VARCHAR);
//            if(lesson.getSchool()!=null)
                ps.setString(8,lesson.getSchool());
//            else
//                ps.setNull(8,Types.VARCHAR);
//            if(lesson.getMajor()!=null)
                ps.setString(9, lesson.getMajor());
//            else
//                ps.setNull(9, Types.VARCHAR);
            //if(lesson.getIsExam()!=null)
                ps.setInt(10,lesson.getIsExam());
//            else
//                ps.setNull(10,Types.INTEGER);
            ps.executeUpdate();
            return true;
//        }catch (Exception e){
//            return false;
//        }
    }
    //管理员给学生添加成绩
    public static Boolean addGrade(String studentID, String innerID, Integer grade) throws Exception {
        String sql = "update tb_STUDENTWITHLESSON set grade = " + String.valueOf(grade) + " where studentID = '" + studentID + "' and innerID = '" + innerID + "'";
        try {
            CRUD.update(sql,conn);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

