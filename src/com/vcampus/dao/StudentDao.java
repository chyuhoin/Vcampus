package com.vcampus.dao;

import com.vcampus.dao.utils.CRUD;
import com.vcampus.dao.utils.mapToBean;
import com.vcampus.pojo.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDao {
    //search空参时查询所有学生，仅管理员可用
    public static List<Student> search() throws Exception {
        String sql = "select * from tb_STUDENT";
        List<Map<String,Object>> resultList = CRUD.Query(sql);
        List<Student> result = new ArrayList<>();
        for(Map<String,Object> map:resultList){
            Student stu = mapToBean.map2Bean(map,Student.class);
            result.add(stu);
        }
        return result;
    }
    //search有参时查询对应的内容
    public static List<Student> search(String field,Object value) throws Exception {
          String sql = "select * from tb_STUDENT where "+field+" = '"+String.valueOf(value)+"'";
        List<Map<String,Object>> resultList = CRUD.Query(sql);
        List<Student> result = new ArrayList<>();
        for(Map<String,Object> map:resultList){
            Student stu = mapToBean.map2Bean(map,Student.class);
            result.add(stu);
        }
        return result;
    }
    //添加一条学生信息,由管理员操作
    public static Boolean addStudent(Student stu){
        String sql = "insert into tb_STUDENT (studentID,name,studentNumber,IDcard,school,major,sex,classs,educationalSystem,politics,grade,phoneNumber,status) values ("+
                "'"+stu.getStudentID()+"',"+
                "'"+stu.getName()+"',"+
                "'"+stu.getStudentNumber()+"',"+
                "'"+stu.getIDcard()+"',"+
                "'"+stu.getSchool()+"',"+
                "'"+stu.getMajor()+"',"+
                ""+stu.getSex().toString()+","+
                "'"+stu.getClasss()+"',"+
                ""+stu.getEducationalSystem().toString()+","+
                "'"+stu.getPolitics()+"',"+
                ""+stu.getGrade().toString()+","+
                "'"+stu.getPhoneNumber()+"',"+
                ""+stu.getStatus()+")";
        System.out.println(sql);
        return null;
    }
    @Test
    public void test() throws Exception {

    }
}
