package com.vcampus.server.service;

import com.vcampus.net.Message;
import com.vcampus.dao.UserDao;
import com.vcampus.pojo.User;

public class UserService {
//    public boolean login(User user) {
//        String res;
//        try {
//            res = UserDao.loginCheck(user.getStudentID(), user.getPassword(), user.getType());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return "succeeded".equals(res);
//    }
    public Message login(User user) {
        String res;
        try {
            res = UserDao.loginCheck(user.getStudentID(), user.getPassword(), user.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return new Message("200", "{res: 'NO'}");
        }
        switch (res) {
            case "succeeded":
                return new Message("200", "{res: 'succeeded'}");//登录成功
            case "wrong password or username":
                return new Message("200", "{res: 'wrong password or username'}");//用户名或密码错误
            case "wrong type":
                return new Message("200", "{res: 'wrong type'}");//类型错误
            default:
                return new Message("200", "{res: 'NO'}");//登录失败
        }
    }
//    public boolean register(User user) {
//        String res;
//        try {
//            res = UserDao.register(user.getStudentID(), user.getPassword(), user.getType());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return "succeeded".equals(res);
//    }
    public Message register(User user) {
        String res;
        try {
            res = UserDao.register(user.getStudentID(), user.getPassword(), user.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return new Message("200", "{res: 'NO'}");
        }
        switch (res) {
            case "succeeded":
                return new Message("200", "{res: 'succeeded'}");//注册成功
            case "registered":
                return new Message("200", "{res: 'registered'}");//已经过注册了
            default:
                return new Message("200", "{res: 'NO'}");//注册失败
        }
    }
}
