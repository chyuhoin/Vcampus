package com.vcampus.dao;

import com.vcampus.dao.utils.CRUD;
import com.vcampus.dao.utils.databaseConn;
import com.vcampus.dao.utils.mapToBean;
import com.vcampus.pojo.Book;
import com.vcampus.pojo.Student;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseDao {
    static Connection conn;

    static {
        try {
            conn = databaseConn.getConn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //查询全部
    public static <T> List<T> searchAll(Class<T>clz,String table) throws Exception {
        String sql = "select * from "+table;
        List<Map<String,Object>> resultList = CRUD.Query(sql,conn);
        List<T>result =new ArrayList<>();
        for(Map<String ,Object>map:resultList){
            T temp = mapToBean.map2Bean(map,clz);
            result.add(temp);
        }
        return result;
    }
    //按字段查询
    public static <T> List<T> searchBy(Class<T>clz,String table,String field,Object value) throws Exception {
        String sql = "select * from "+table+" where "+field+" = ";
        if(value.getClass()==String.class)
            sql +="'"+value+"'";
        else if(value.getClass()==Integer.class)
            sql +=String.valueOf(value);
        List<Map<String,Object>> resultList = CRUD.Query(sql,conn);
        List<T>result =new ArrayList<>();
        for(Map<String ,Object>map:resultList){
            T temp = mapToBean.map2Bean(map,clz);
            result.add(temp);
        }
        return result;
    }
    @Test
    public void test() throws Exception {
        String sql = "select * from tb_STUDENT where sex = '1'";
        List<Map<String,Object>> resultList =CRUD.Query(sql,conn);
        List<Student>result =new ArrayList<>();
        for(Map<String ,Object>map:resultList){
            Student temp = mapToBean.map2Bean(map,Student.class);
            result.add(temp);
        }
        System.out.println(resultList);
    }
}
