package com.vcampus.pojo;

import java.io.Serializable;

public class User implements Serializable {
    String studentID;
    String password;
    Integer type;

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
