package com.vcampus.pojo;

/**
 * 考试
 *
 * @author 12107
 * @date 2022/09/01
 */
public class Exam {
    String innerID;
    String teacherID;
    String time;

    /**
     * 考试
     *
     * @param innerID   内部id
     * @param teacherID 老师id
     * @param time      时间
     */
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
