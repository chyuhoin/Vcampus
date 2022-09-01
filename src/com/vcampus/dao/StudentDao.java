package com.vcampus.dao;

import com.vcampus.dao.utils.CRUD;
import com.vcampus.dao.utils.mapToBean;
import com.vcampus.pojo.Student;
import org.junit.Test;

import java.util.*;

/**
 * 学籍相关操作的Dao层
 *
 * @author 12107
 * @date 2022/09/01
 *//*
涉及学籍的与数据库的操作
 */
public class StudentDao extends BaseDao{
    /**
     * 查询所有学生
     *
     * @return {@link List}<{@link Student}>
     * @throws Exception 异常
     *///search空参时查询所有学生，仅管理员可用
    public static List<Student> search() throws Exception {
        return searchAll(Student.class,"tb_STUDENT");
    }

    /**
     * 按字段查询
     *
     * @param field 字段
     * @param value 值
     * @return {@link List}<{@link Student}>
     * @throws Exception 异常
     *///search有参时查询对应的内容
    public static List<Student> search(String field,Object value) throws Exception {
        return searchBy(Student.class,"tb_STUDENT",field,value);
    }

    /**
     * 增加学生
     *
     * @param stu 学生
     * @return {@link Boolean}
     */
    public static Boolean addStudent(Student stu)  {
        try {
            addClass(stu,"tb_STUDENT");
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }

    /**
     * 增加学生
     *
     * @param studentID 学生一卡通
     * @return {@link Boolean}
     *///只添加一个studentID，以后慢慢的补充
    public static Boolean addStudent(String studentID){
        try {
            String sql = "insert into tb_STUDENT (studentID) values ('" + studentID + "')";
            CRUD.update(sql,conn);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 删除学生
     *
     * @param studentID 学生一卡通
     * @return {@link Boolean}
     */
    public static Boolean deleteStudent(String studentID){
        try {
            delete("studentID",studentID,"tb_STUDENT");
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }

    }

    /**
     * 修改学生
     *
     * @param studentID 学生一卡通
     * @param field     字段
     * @param value     值
     * @return {@link Boolean}
     */
    public static Boolean reviseStudent(String studentID,String field,String value){
        try{
            String sql = "update tb_STUDENT set "+field+" ='"+value+"' where studentID ='"+studentID+"'";
            CRUD.update(sql,conn);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 修改学生
     *
     * @param studentID 学生一卡通
     * @param field     字段
     * @param value     值
     * @return {@link Boolean}
     *///修改学生信息（int类型）
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
