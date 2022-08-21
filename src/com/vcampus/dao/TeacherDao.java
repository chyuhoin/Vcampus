package com.vcampus.dao;


import com.vcampus.dao.utils.CRUD;
import com.vcampus.dao.utils.mapToBean;
import com.vcampus.pojo.Teacher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    //删除一个老师
    public static Boolean deleteTeacher(String teacherID){
        return delete("teacherID",teacherID,"tb_USR");
    }
    //
    public static List<Teacher> searchTeacher(String field,String value) throws Exception {
        String sql = "select * from tb_TEACHER where "+field+"'"+value+"'";
        List<Map<String,Object>> resultList = CRUD.Query(sql,conn);
        List<Teacher> result = new ArrayList<>();
        for(Map<String ,Object>map:resultList){
            Teacher teacher = mapToBean.map2Bean(map,Teacher.class);
            result.add(teacher);
        }
        return result;
    }
}
