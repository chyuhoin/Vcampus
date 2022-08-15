package com.vcampus.server.service;

import com.vcampus.dao.UserDao;
import com.vcampus.pojo.User;

public class UserService {
    public boolean login(User user) {
        String res;
        try {
            res = UserDao.loginCheck(user.getStudentID(), user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return "登陆成功".equals(res);
    }
}
