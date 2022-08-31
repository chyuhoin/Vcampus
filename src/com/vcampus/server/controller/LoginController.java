package com.vcampus.server.controller;

import com.vcampus.net.Message;
import com.vcampus.pojo.User;
import com.vcampus.server.service.UserService;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 登录模块的Controller
 * get                  登录
 * getone               通过id获取全部信息
 * getAll               获取全部用户
 * register             注册
 *                      注册时会同时创建一个只有ID的学籍信息
 *                      如果注册时ID已存在(学籍管理或用户管理系统)，则注册失败
 * delete               删除用户，输入ID
 * Change Password      修改密码
 * Change Permissions   输入ID，修改其权限 如：“123:1” “ID”+“:”+“1/2/3” 1--学生 2--老师 3--管理员
 *
 * @author ZhongHaoyuan
 */
public class LoginController implements Controller {

    @Override
    public Message check(Message msg) {
        UserService service = new UserService();
        Gson gson = new Gson();
        User user = gson.fromJson(msg.getData(), User.class);
        switch (msg.getOperation()){
            case "get": {//登录
                boolean res = service.login(user);
                String data = res ? "{res: 'OK'}" : "{res: 'Error'}";
                return new Message("200", data);
            }
            case "getone": {//通过id获取全部信息
                User res = service.getUserById(user);
                List<User> users = new ArrayList<>();
                users.add(res);
                HashMap<String, List<User>> map = new HashMap<>();
                map.put("res", users);
                return new Message("200", gson.toJson(map));
            }
            case "getAll": { //获取全部用户
                List<User> res = service.getAllUsers();
                HashMap<String, List<User>> map = new HashMap<>();
                map.put("res", res);
                return new Message("200", gson.toJson(map));
            }
            case "register": {
                //注册
                //注册时会同时创建一个只有ID的学籍信息
                //如果注册时ID已存在(学籍管理或用户管理系统)，则注册失败
                boolean res = service.register(user);
                String data = res ? "{res: 'OK'}" : "{res: 'Error'}";
                return new Message("200", data);
            }
            case "delete": {//删除用户，输入ID
                boolean res = service.delete(user);
                String data = res ? "{res: 'OK'}" : "{res: 'Error'}";
                return new Message("200", data);
            }
            case "Change Password": {//修改密码
                boolean res = service.changePassword(user);
                String data = res ? "{res: 'OK'}" : "{res: 'Error'}";
                return new Message("200", data);
            }
            case "Change Permissions": {//输入ID，修改其权限 如：“123:1” “ID”+“:”+“1/2/3” 1--学生 2--老师 3--管理员
                boolean res = service.changePermission(user);
                String data = res ? "{res: 'OK'}" : "{res: 'Error'}";
                return new Message("200", data);
            }

            default:
                return new Message("404", "{res: 'Wrong Request!'}");
        }
    }
}
