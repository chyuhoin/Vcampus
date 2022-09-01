package com.vcampus.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vcampus.dao.utils.CRUD;
import com.vcampus.dao.utils.mapToBean;
import com.vcampus.pojo.User;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 用户操作相关的Dao层
 *
 * @author 刘骐
 * @date 2022/09/01
 */
public class UserDao extends BaseDao {
    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @param type     类型
     * @return {@link String}
     * @throws Exception 异常
     */
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

    /**
     * 登录检查
     *
     * @param username 用户名
     * @param password 密码
     * @param type     类型
     * @return {@link String}
     * @throws Exception 异常
     */
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

    /**
     * 删除用户
     *
     * @param studentID 学生一卡通
     * @return {@link Boolean}
     *///删除一个用户，管理员可操作
    public static Boolean deleteUser(String studentID) {
        try {
            delete("studentID",studentID,"tb_USR");
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }

    /**
     * 修改类型
     *
     * @param studentID 学生一卡通
     * @param type      类型
     * @return {@link Boolean}
     */
    public static Boolean reviseType(String studentID, Integer type) {
        try {
            String sql = "update tb_USR set type = " + type + " where studentID = '" + studentID + "'";
            CRUD.update(sql, conn);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 修改密码
     *
     * @param studentID 学生证
     * @param password  密码
     * @return {@link Boolean}
     */
    public static Boolean revisePassword(String studentID, String password) {
        try {
            String sql = "update tb_USR set password = '" + password + "' where studentID = '" + studentID + "'";
            CRUD.update(sql, conn);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 查询某个学生
     *
     * @param studentID 学生证
     * @return {@link Boolean}
     */
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

    /**
     * 获取用户的全部信息
     *
     * @param studentID 学生一卡通
     * @return {@link User}
     */
    public static User getUser(String studentID) { //通过id获得学生全部信息
        try {
            String sql = "select * from tb_USR where studentID = '" + studentID + "'";
            List<Map<String, Object>> result = CRUD.Query(sql, conn);
            if (result.isEmpty()) return null;
            else return mapToBean.map2Bean(result.get(0), User.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取全部用户
     *
     * @return {@link List}<{@link User}>
     */
    public static List<User> getAllUsers() {
        try {
            String sql = "select * from tb_USR";
            List<Map<String, Object>> result = CRUD.Query(sql, conn);
            ArrayList<User> users = new ArrayList<>();
            for(Map<String, Object> map: result) {
                User user = mapToBean.map2Bean(map, User.class);
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
