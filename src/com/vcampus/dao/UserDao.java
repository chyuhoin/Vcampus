package com.vcampus.dao;

import com.vcampus.dao.utils.CRUD;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class UserDao {
    public static String register(String username, String password) throws Exception {
        String sql = "select * from tb_USR where studentID = '" + username + "'";
        List<Map<String, Object>> list = CRUD.Query(sql);
        if (!list.isEmpty()) {
            return "���˻��ѱ�ע��";
        } else {
            String sql2 = "insert into tb_USR (studentID,password,type) values ('" + username + "','" + password + "',0)";
            CRUD.update(sql2);
            return "ע��ɹ�";
        }
    }

    public static String loginCheck(String username, String password,Integer type) throws Exception {
        String sql = "select * from tb_USR where studentID = '" + username + "' and password = '" + password + "'";
        List<Map<String, Object>> list = CRUD.Query(sql);
        if (list.isEmpty()) {
            return "�û������������";
        } else if (type !=(Integer)list.get(0).get("type")) {
            return "���ʹ���";
        }else
            return "��½�ɹ�";
    }
}
