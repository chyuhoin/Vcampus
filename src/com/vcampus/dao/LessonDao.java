package com.vcampus.dao;

import com.vcampus.dao.utils.CRUD;
import com.vcampus.dao.utils.databaseConn;
import com.vcampus.dao.utils.mapToBean;
import com.vcampus.pojo.Book;
import com.vcampus.pojo.Lesson;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LessonDao extends BaseDao{
    //无参时查询所有课程
    public static List<Lesson> search() throws Exception {
        String sql = "select * from tb_LESSON";
        List<Map<String, Object>> resultList = CRUD.Query(sql,conn);
        List<Lesson> result = new ArrayList<>();
        for (Map<String, Object> map : resultList) {
            Lesson lesson = mapToBean.map2Bean(map, Lesson.class);
            result.add(lesson);
        }
        return result;
    }

    //有参按条件查询
    public static List<Lesson> search(String field, String value) throws Exception {
        String sql = "select * from tb_LESSON where " + field + " = '" + value + "'";
        List<Map<String, Object>> resultList = CRUD.Query(sql,conn);
        List<Lesson> result = new ArrayList<>();
        for (Map<String, Object> map : resultList) {
            Lesson lesson = mapToBean.map2Bean(map, Lesson.class);
            result.add(lesson);
        }
        return result;
    }

    //查询自己选的课信息
    public static List<Lesson> searchMine(String studentID) throws Exception {
        String sql = "SELECT tb_STUDENTWITHLESSON.* from tb_LESSON ,tb_STUDENTWITHLESSON " +
                "WHERE tb_LESSON.innerID = tb_STUDENTWITHLESSON.innerID " +
                "and tb_STUDENTWITHLESSON.studentID = '" + studentID + "'";
        List<Map<String, Object>> resultList = CRUD.Query(sql,conn);
        List<Lesson> result = new ArrayList<>();
        System.out.println(resultList);
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

