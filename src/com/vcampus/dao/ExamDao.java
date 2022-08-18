package com.vcampus.dao;

import com.vcampus.dao.utils.CRUD;
import com.vcampus.dao.utils.mapToBean;
import com.vcampus.pojo.Exam;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExamDao extends BaseDao{
    //学生查看自己安排的考试
    public static List<Exam> searchMyExamForStudent(String studentID) throws Exception {
        String sql = "select tb_EXAM.* from tb_EXAM,tb_STUDENTWITHLESSON"+
                "where tb_EXAM.innerID = tb_STUDENTWITHLESSON.innerID "+
                "and tb_STUDENTWITHLESSON.studentID = '"+studentID+"'";
        List<Map<String, Object>> resultList = CRUD.Query(sql,conn);
        List<Exam> result = new ArrayList<>();
        for (Map<String, Object> map : resultList) {
            Exam exam = mapToBean.map2Bean(map, Exam.class);
            result.add(exam);
        }
        return result;
    }
    //老师查看自己监考的考试
    public static List<Exam> searchMyExamForTeacher(String teacherID) throws Exception {
        String sql = "select * from tb_EXAM where find_in_set('"+teacherID+"',teacherID)";
        List<Map<String, Object>> resultList = CRUD.Query(sql,conn);
        List<Exam> result = new ArrayList<>();
        for (Map<String, Object> map : resultList) {
            Exam exam = mapToBean.map2Bean(map, Exam.class);
            result.add(exam);
        }
        return result;

    }

}
