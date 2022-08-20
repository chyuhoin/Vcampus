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
import static com.vcampus.dao.utils.ClassTable.addToTable;
import static com.vcampus.dao.utils.ClassTable.compare;

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
    //判断函数
    public static int judgeLesson(String studentID,String innerID) throws Exception {
        String judgeSql1 = "select * from tb_STUDENTWITHLESSON where studentID = '" + studentID + "' and innerID = '" + innerID + "'";

        List<Map<String, Object>> judge1 = CRUD.Query(judgeSql1,conn);


        if(!judge1.isEmpty())
            return 0;//已选不可选
        else{
            String judgeSql2 = "select * from tb_LESSON where  innerID = '" + innerID + "'";
            List<Map<String, Object>> judge2 = CRUD.Query(judgeSql2,conn);
            int leftSize = (int) judge2.get(0).get("leftSize");
            if(leftSize==0)
                return 1;//已满不可选
            else {
                String sql1 = "select timeTable from tb_LESSONTABLE where studentID = '"+studentID+"'";
                String sql2 = "select time from tb_LESSON where innerID ='"+innerID+"'";
                List<Map<String ,Object>> list1 = CRUD.Query(sql1,conn);
                List<Map<String ,Object>> list2 =CRUD.Query(sql2,conn);
                String time = (String) list1.get(0).get("time");
                String timeTable = (String) list2.get(0).get("timeTable");
                 if(compare(time,timeTable)==false)
                     return 2;//冲突不可选
                else
                    return 3;//可选
            }
        }

    }
    //学生选课

    public static Boolean selectLesson(String studentID, String innerID) throws Exception {
//        String judgeSql = "select * from tb_STUDENTWITHLESSON where studentID = '" + studentID + "' and innerID = '" + innerID + "'";
//        List<Map<String, Object>> judge = CRUD.Query(judgeSql,conn);
//        if (judge.isEmpty()) {
            try {
                String sql1 = "update tb_LESSON set leftSize=leftSize-1 where innerID = '" + innerID + "'";
                String sql2 = "insert into tb_STUDENTWITHLESSON (studentID,innerID) values ('" + studentID + "','" + innerID + "')";
                Connection conn = databaseConn.getConn();
                conn.setAutoCommit(false);
                Statement stm = conn.createStatement();
                stm.executeUpdate(sql1);
                stm.executeUpdate(sql2);
                addToTable(studentID,innerID,conn);
                conn.commit();
                stm.close();
                conn.close();
                return true;
            } catch (Exception e) {
                return false;
            }
//        } else {
//            return false;
//        }
    }
    //学生退课
    public static Boolean returnLesson(String studentID,String innerID){
            try {
                String sql1 = "update tb_LESSON set leftSize=leftSize+1 where innerID = '" + innerID + "'";
                String sql2 = "delete from tb_STUDENTWITHLESSON where studentID ='"+studentID+"' and innerID = '"+innerID+"'";
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

        }

    //管理员添加课程
    public static Boolean addLesson(Lesson lesson) throws Exception {
        try {
            String sql = "insert into tb_LESSON (innerID,lessonID,name,teacherID,maxSize,leftSize,time,school,major,isExam) values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,lesson.getInnerID());
                ps.setString(2,lesson.getLessonID());
                ps.setString(3,lesson.getName());
                ps.setString(4,lesson.getTeacherID());
                ps.setInt(5,lesson.getMaxSize());
                ps.setInt(6,lesson.getLeftSize());
                ps.setString(7,lesson.getTime());
                ps.setString(8,lesson.getSchool());
                ps.setString(9, lesson.getMajor());
                ps.setInt(10,lesson.getIsExam());
            ps.executeUpdate();
            return true;
        }catch (Exception e){
            return false;
        }
    }
    //管理员删除一个课程
    public static Boolean deleteLesson(String innerID){
        return delete("innerID",innerID,"tb_LESSON");
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

