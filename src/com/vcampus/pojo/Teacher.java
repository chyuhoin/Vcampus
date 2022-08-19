package com.vcampus.pojo;

public class Teacher {
    String teacherID;//实际上就是教师的一卡通号
    String teacherName;
    String major;
    String abledMajor;
    String time;

    public Teacher(String teacherID, String teacherName, String major, String abledMajor, String time) {
        this.teacherID = teacherID;
        this.teacherName = teacherName;
        this.major = major;
        this.abledMajor = abledMajor;
        this.time = time;
    }

    public Teacher() {
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAbledMajor() {
        return abledMajor;
    }

    public void setAbledMajor(String abledMajor) {
        this.abledMajor = abledMajor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherID='" + teacherID + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", major='" + major + '\'' +
                ", abledMajor='" + abledMajor + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
