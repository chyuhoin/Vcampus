package com.vcampus.pojo;

public class Exam {
    String innerID;
    String teacherID;
    String time;

    public Exam(String innerID, String teacherID, String time) {
        this.innerID = innerID;
        this.teacherID = teacherID;
        this.time = time;
    }

    public Exam() {
    }

    public String getinnerID() {
        return innerID;
    }

    public void setinnerID(String innerID) {
        this.innerID = innerID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "innerID='" + innerID + '\'' +
                ", teacherID='" + teacherID + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
