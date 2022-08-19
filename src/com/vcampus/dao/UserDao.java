package com.vcampus.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vcampus.dao.utils.CRUD;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class UserDao extends BaseDao {
    public static String register(String username, String password, Integer type) throws Exception {
        String sql = "select * from tb_USR where studentID = '" + username + "'";
        List<Map<String, Object>> list = CRUD.Query(sql, conn);
        if (!list.isEmpty()) {
            return "existed user";
        } else {
            String sql2 = "insert into tb_USR (studentID,password,type) values ('" + username + "','" + password + "'," + String.valueOf(type) + ")";
            CRUD.update(sql2, conn);
            return "succeeded";
        }
    }

    public static String loginCheck(String username, String password, Integer type) throws Exception {
        String sql = "select * from tb_USR where studentID = '" + username + "' and password = '" + password + "'";
        List<Map<String, Object>> list = CRUD.Query(sql, conn);
        if (list.isEmpty()) {
            return "no user";
        } else if (type != (Integer) list.get(0).get("type")) {
            return "wrong type";
        } else
            return "succeeded";
    }

    //删除一个用户，管理员可操作
    public static Boolean deleteUser(String studentID) {
        return delete("studentID",studentID,"tb_USR");
    }

    public static Boolean reviseType(String studentID, Integer type) {
        try {
            String sql = "update tb_USR set type = " + type + " where studentID = '" + studentID + "'";
            CRUD.update(sql, conn);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //修改密码
    public static Boolean revisePassword(String studentID, String password) {
        try {
            String sql = "update tb_USR set password = '" + password + "' where studentID = '" + studentID + "'";
            CRUD.update(sql, conn);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //查询某个学生
    public static Boolean search(String studentID) {
        try {
            String sql = "select * from tb_USR where studentID = '" + studentID + "'";
            List<Map<String, Object>> result = CRUD.Query(sql, conn);
            if (result.isEmpty()) return false;
            else return true;
        } catch (Exception e) {
            return false;
        }
    }
}
