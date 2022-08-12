package com.vcampus.server.user;

import com.vcampus.dao.CRUD;

import java.util.List;
import java.util.Map;
/*
之所以把登录和注册分开，是因为我觉得登录难度远高于注册，登陆后还要跳转到处理程序，这咋做啊。。。好难
目前写的也仅仅只是判断
 */
public class Login {
    public boolean login(String username,String password) throws Exception {
        String sql   = "select * from tb_USR where studentID = '"+username+"' and password = '"+password+"'";
        List<Map<String,Object>> list = CRUD.Query(sql);
        if(!list.isEmpty()){
            return true;
        }else {
            return false;
        }
    }
}
