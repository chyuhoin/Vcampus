package com.vcampus.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vcampus.dao.utils.CRUD;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class UserDao {
    public static String register(String username, String password,Integer type) throws Exception {
        String sql = "select * from tb_USR where studentID = '" + username + "'";
        List<Map<String, Object>> list = CRUD.Query(sql);
        if (!list.isEmpty()) {
            return "���˻��ѱ�ע��";
        } else {
            String sql2 = "insert into tb_USR (studentID,password,type) values ('" + username + "','" + password + "',"+String.valueOf(type)+")";
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
    //删除一个用户，管理员可操作
    public static Boolean deleteUser(String studentID){
        try{
            String sql = "delete from tb_USR where studentID = '"+studentID+"'";
            CRUD.update(sql,conn);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public static Boolean reviseType(String studentID,Integer type){
        try{
            String sql = "update tb_USR set type = "+type+" where studentID = '"+studentID+"'";
            CRUD.update(sql,conn);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    //修改密码
    public static Boolean revisePassword(String studentID,String password){
        try{
            String sql = "update tb_USR set password = '"+password+"' where studentID = '"+studentID+"'";
            CRUD.update(sql,conn);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
