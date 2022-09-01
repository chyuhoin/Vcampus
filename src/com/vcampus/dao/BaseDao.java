package com.vcampus.dao;

import com.vcampus.dao.utils.CRUD;
import com.vcampus.dao.utils.databaseConn;
import com.vcampus.dao.utils.mapToBean;
import com.vcampus.pojo.Book;
import com.vcampus.pojo.Student;
import com.vcampus.pojo.User;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *所有Dao的父类，提供增删改查泛型方法
 * @author 刘骐
 * @date 2022/09/01
 */
public class BaseDao {
    static Connection conn;

    static {
        try {
            conn = databaseConn.getConn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 搜索所有内容
     *
     * @param clz   用于接受的JavaBean对象的class
     * @param table 表格
     * @return 返回结果列表
     * @throws Exception 查询失败的异常
     *///查询全部的公共方法
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

    /**
     * 按字段查询
     *
     * @param clz   用于接受的JavaBean对象的class
     * @param table 表格
     * @param field 字段
     * @param value 值
     * @return 返回结果列表
     * @throws Exception 查询失败的异常
     *///按字段查询的公共方法
    public static <T> List<T> searchBy(Class<T>clz,String table,String field,Object value) throws Exception {
        String sql = "select * from "+table +" where FIND_IN_SET(";
        if(value.getClass()==String.class)
            sql +="'"+value+"'";
        else if(value.getClass()==Integer.class)
            sql +=String.valueOf(value);
        sql+=","+field+")";
        List<Map<String,Object>> resultList = CRUD.Query(sql,conn);
        List<T>result =new ArrayList<>();
        for(Map<String ,Object>map:resultList){
            T temp = mapToBean.map2Bean(map,clz);
            result.add(temp);
        }
        return result;
    }

    /**
     * 删除某一值
     *
     * @param field 字段
     * @param value 值
     * @param table 表格
     * @throws Exception 异常
     *///删除的公共方法
    public static void delete(String field,Object value,String table) throws Exception {
            String sql = "delete from "+table+" "+"where "+field+" = ";
            if(value.getClass()==String.class)
                sql +="'"+value+"'";
            else if(value.getClass()==Integer.class)
                sql +=String.valueOf(value);
            CRUD.update(sql,conn);
    }

    /**
     * 添加值
     *
     * @param temp  传入的javabean对象
     * @param table 表格
     * @throws SQLException           sqlexception异常
     * @throws IllegalAccessException 非法访问异常
     *///添加的公共方法
    public static<T>  void addClass(T temp,String table) throws SQLException, IllegalAccessException {
        Class clazz = temp.getClass();
        Field[] fields = clazz.getDeclaredFields();
        String sql = "insert into " + table + " (";
        String sql2 = "values(";
        for (int i = 0; i < fields.length; i++) {
            sql += fields[i].getName();
            sql2 += "?";
            if (i != fields.length - 1) {
                sql += ",";
                sql2 += ",";
            } else {
                sql += ")";
                sql2 += ")";
            }
        }
        sql += sql2;
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            ps.setObject(i + 1, fields[i].get(temp));
        }
        ps.executeUpdate();

    }

}
