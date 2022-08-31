package com.vcampus.server.service;

import com.vcampus.dao.UserDao;
import com.vcampus.pojo.User;
import com.vcampus.dao.StudentDao;

import java.util.List;

/**
 * 用户服务
 * * 用于用户管理的后端
 * @author ietot
 * @date 2022/08/31
 */
public class UserService implements Service {

    /**
     * 登录
     *
     * @param user 用户
     * @return boolean
     */
    public boolean login(User user) {
        String res;
        try {
            res = UserDao.loginCheck(user.getStudentID(), user.getPassword(), user.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return "succeeded".equals(res);
    }

    /**
     * 注册
     *
     * @param user 用户
     * @return boolean
     */
    public boolean register(User user) {
        boolean res;
        //String res;
        try {
            if(!StudentDao.search("studentID", user.getStudentID()).isEmpty())//学籍管理里已存在对应ID
                res=false;
            else if(!StudentDao.addStudent(user.getStudentID()))//在学籍管理表里添加ID失败
                res=false;
            else res=UserDao.register(user.getStudentID(), user.getPassword(), user.getType()).equals("succeeded");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }

    /**
     * 得到用户id
     *
     * @param user 用户
     * @return {@link User}
     */
    public User getUserById(User user) {
        User res = null;
        try {
            res = UserDao.getUser(user.getStudentID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 获取所有用户
     *
     * @return {@link List}<{@link User}>
     */
    public List<User> getAllUsers() {
        List<User> users = null;
        try {
            users = UserDao.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * 删除
     *
     * @param user 用户
     * @return boolean
     */
    public boolean delete(User user) {
        return UserDao.deleteUser(user.getStudentID());
    }

    /**
     * 更改密码
     *
     * @param user 用户
     * @return boolean
     */
    public boolean changePassword(User user) {
        return UserDao.revisePassword(user.getStudentID(), user.getPassword());
    }

    /**
     * 改变权限
     *
     * @param user 用户
     * @return boolean
     */
    public boolean changePermission(User user) {
        return UserDao.reviseType(user.getStudentID(), user.getType());
    }
}
