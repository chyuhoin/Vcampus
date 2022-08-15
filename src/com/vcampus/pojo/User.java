package com.vcampus.pojo;

public class User {
    String studentID;
    String password;
    int type;

    public User(String studentID, String password, int type) {
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
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
