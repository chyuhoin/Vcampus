package com.vcampus.server.service;

import com.vcampus.dao.UserDao;
import com.vcampus.pojo.User;
import com.vcampus.dao.StudentDao;

public class UserService {

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

    public boolean delete(User user) {
        return UserDao.deleteUser(user.getStudentID());
    }

    public boolean changePassword(User user) {
        return UserDao.revisePassword(user.getStudentID(), user.getPassword());
    }

    public boolean changePermission(User user) {
        return UserDao.reviseType(user.getStudentID(), user.getType());
    }
}
