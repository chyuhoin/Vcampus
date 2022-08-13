package com.vcampus.dao;

import java.util.List;
import java.util.Map;

public class UserDao {
    public String register(String username,String password) throws Exception {
        String sql   = "select * from tb_USR where studentID = '"+username+"'";
        List<Map<String,Object>> list = CRUD.Query(sql);
        if (!list.isEmpty()){
            return "该账户已被注册";
        }else {
            String sql2 = "insert into tb_USR (studentID,password,type) values ('"+username+"','"+password+"',0)";
            //System.out.println(sql2);
            CRUD.update(sql2);
            return "注册成功";
        }
    }
    public boolean loginCheck(String username,String password) throws Exception {
        String sql   = "select * from tb_USR where studentID = '"+username+"' and password = '"+password+"'";
        List<Map<String,Object>> list = CRUD.Query(sql);
        if(!list.isEmpty()){
            return true;
        }else {
            return false;
        }
    }

}
