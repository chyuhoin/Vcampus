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
                " where tb_EXAM.innerID = tb_STUDENTWITHLESSON.innerID "+
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
    //管理员添加考试信息
    public static Boolean addExam(Exam exam){
        try{
            String sql = "insert into tb_EXAM (innerID,teacherID,time) values('"+
                    exam.getinnerID()+"','"+
                    exam.getTeacherID()+"','"+
                    exam.getTime()+"')";
            CRUD.update(sql,conn);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    //管理员删除考试信息
    public static Boolean deleteExam(String innerID) {
//        try {
//            String sql = "delete from tb_EXAM where innerID = '" + innerID + "'";
//            CRUD.update(sql, conn);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
        try{
         delete("innerID",innerID,"tb_EXAM");
         return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }
    //用于管理员修改某个字段的信息
    public static Boolean revise(String innerID,String field,String value){
        try {
            String sql = "update tb_EXAM set "+field+" ='"+value+"' where innerID ='"+innerID+"'";
            CRUD.update(sql,conn);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    //根据id查找exam
    public static List<Exam> searchExam(String innerID) throws Exception {
        String sql = "select * from tb_EXAM where innerID ='"+innerID+"'";
        List<Map<String,Object>>resultList = CRUD.Query(sql,conn);
        List<Exam>result = new ArrayList<>();
        for(Map<String,Object>map:resultList){
            Exam e = mapToBean.map2Bean(map,Exam.class);
            result.add(e);
        }
        return result;
    }
}
