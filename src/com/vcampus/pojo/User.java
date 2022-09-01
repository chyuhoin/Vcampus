package com.vcampus.pojo;

import java.io.Serializable;

/**
 * 用户
 *
 * @author 刘骐
 * @date 2022/09/01
 */
public class User implements Serializable {
    String studentID;
    String password;
    Integer type;

    /**
     * 用户
     *
     * @param studentID 学生一卡通
     * @param password  密码
     * @param type      类型
     */
    public User(String studentID, String password, Integer type) {
        this.studentID = studentID;
        this.password = password;
        this.type = type;
    }

    public User() {
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "studentID='" + studentID + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                '}';
    }
}
