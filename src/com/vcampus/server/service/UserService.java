package com.vcampus.server.service;

import com.vcampus.dao.UserDao;
import com.vcampus.pojo.User;

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
        String res;
        try {
            res = UserDao.register(user.getStudentID(), user.getPassword(), user.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return "succeeded".equals(res);
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
