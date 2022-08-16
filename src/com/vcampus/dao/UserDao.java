package com.vcampus.dao;

import com.vcampus.dao.utils.CRUD;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class UserDao extends BaseDao{

    public static String register(String username, String password, Integer type) throws Exception {
        String sql = "select * from tb_USR where studentID = '" + username + "'";
        List<Map<String, Object>> list = CRUD.Query(sql,conn);
        if (!list.isEmpty()) {
            return "registered";
        } else {
            String sql2 = "insert into tb_USR (studentID,password,type) values ('" + username + "','" + password + "',"+String.valueOf(type)+")";
            CRUD.update(sql2,conn);
            return "succeeded";
        }
    }

    public static String loginCheck(String username, String password,Integer type) throws Exception {
        String sql = "select * from tb_USR where studentID = '" + username + "' and password = '" + password + "'";
        List<Map<String, Object>> list = CRUD.Query(sql,conn);
        if (list.isEmpty()) {
            return "wrong password or username";
        } else if (type !=(Integer)list.get(0).get("type")) {
            return "wrong type";
        }else
            return "succeeded";
    }
//public static String loginCheck(String username, String password) throws Exception {
//    String sql = "select * from tb_USR where studentID = '" + username + "' and password = '" + password + "'";
//    List<Map<String, Object>> list = CRUD.Query(sql);
//    if (list.isEmpty()) {
//        return "用户名或密码错误";
//    } else
//        return "登陆成功";

}

