package com.vcampus.dao;

import com.vcampus.dao.utils.CRUD;
import com.vcampus.dao.utils.mapToBean;
import com.vcampus.pojo.Student;
import org.junit.Test;

import java.util.*;

/*
涉及学籍的与数据库的操作
 */
public class StudentDao extends BaseDao{
    //search空参时查询所有学生，仅管理员可用
    public static List<Student> search() throws Exception {
        return searchAll(Student.class,"tb_STUDENT");
    }
    //search有参时查询对应的内容
    public static List<Student> search(String field,Object value) throws Exception {
        return searchBy(Student.class,"tb_STUDENT",field,value);
    }
    //添加一条学生信息,由管理员操作
    public static Boolean addStudent(Student stu)  {
        return addClass(stu,"tb_STUDENT");
    }
    //只添加一个studentID，以后慢慢的补充
    public static Boolean addStudent(String studentID){
        try {
            String sql = "insert into tb_STUDENT (studentID) values ('" + studentID + "')";
            CRUD.update(sql,conn);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    //删除一个学生的信息（仅管理员
    public static Boolean deleteStudent(String studentID){
        return delete("studentID",studentID,"tb_STUDENT");
    }
    //修改学生的信息（String类型）
    public static Boolean reviseStudent(String studentID,String field,String value){
        try{
            String sql = "update tb_STUDENT set "+field+" ='"+value+"' where studentID ='"+studentID+"'";
            CRUD.update(sql,conn);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    //修改学生信息（int类型）
    public static Boolean reviseStudent(String studentID,String field,int value){
        try{
            String sql = "update tb_STUDENT set "+field+" ="+value+" where studentID ='"+studentID+"'";
            CRUD.update(sql,conn);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
