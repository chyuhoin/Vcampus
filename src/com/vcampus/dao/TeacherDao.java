package com.vcampus.dao;


import com.vcampus.dao.utils.CRUD;
import com.vcampus.pojo.Teacher;
import org.junit.Test;

public class TeacherDao extends UserDao {
    public static Boolean addTeacher(Teacher teacher){
        try{
            String sql = "insert into tb_TEACHER (teacherID,teacherName,major,abledMajor,time) values ("+
                    "'"+teacher.getTeacherID()+"',"+
                    "'"+teacher.getTeacherName()+"',"+
                    "'"+teacher.getMajor()+"',"+
                    "'"+teacher.getAbledMajor()+"',"+
                    "'"+teacher.getTime()+"')";
            CRUD.update(sql,conn);
            return true;
        }catch (Exception e){
            return  false;
        }
    }
    public static Boolean deleteTeacher(String teacherID){
        return delete("teacherID",teacherID,"tb_USR");
    }
}
