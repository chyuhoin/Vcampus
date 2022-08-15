package com.vcampus.dao;

import com.vcampus.dao.utils.CRUD;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class UserDao {
    public static String register(String username, String password,Integer type) throws Exception {
        String sql = "select * from tb_USR where studentID = '" + username + "'";
        List<Map<String, Object>> list = CRUD.Query(sql);
        if (!list.isEmpty()) {
            return "该账户已被注册";
        } else {
            String sql2 = "insert into tb_USR (studentID,password,type) values ('" + username + "','" + password + "',"+String.valueOf(type)+")";
            CRUD.update(sql2);
            return "注册成功";
        }
    }

    public static String loginCheck(String username, String password,Integer type) throws Exception {
        String sql = "select * from tb_USR where studentID = '" + username + "' and password = '" + password + "'";
        List<Map<String, Object>> list = CRUD.Query(sql);
        if (list.isEmpty()) {
            return "用户名或密码错误";
        } else if (type !=(Integer)list.get(0).get("type")) {
            return "类型错误";
        }else
            return "登陆成功";
    }
//public static String loginCheck(String username, String password) throws Exception {
//    String sql = "select * from tb_USR where studentID = '" + username + "' and password = '" + password + "'";
//    List<Map<String, Object>> list = CRUD.Query(sql);
//    if (list.isEmpty()) {
//        return "用户名或密码错误";
//    } else
//        return "登陆成功";

}

