package com.vcampus.server.user;

import com.vcampus.dao.CRUD;

import java.util.List;
import java.util.Map;

public class Register {
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
    //登录我有个问题，就是登陆成功后它需要转到处理程序，但这点我不是很明白...按理来说应该转到用户对应的线程

}
