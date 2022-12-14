package com.vcampus.pojo;

/**
 * 老师
 *
 * @author 刘骐
 * @date 2022/09/01
 */
public class Teacher {
    String teacherID;//实际上就是教师的一卡通号
    String teacherName;
    String school;
    String abledMajor;
    String time;
    Integer status;

    /**
     * 老师
     *
     * @param teacherID   老师id
     * @param teacherName 老师名字
     * @param school      学院
     * @param abledMajor  专业
     * @param time        时间
     * @param status      状态
     */
    public Teacher(String teacherID, String teacherName, String school, String abledMajor, String time,Integer status) {
        this.teacherID = teacherID;
        this.teacherName = teacherName;
        this.school = school;
        this.abledMajor = abledMajor;
        this.time = time;
        this.status = status;
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

    public String getAbledMajor() {
        return abledMajor;
    }

    public void setAbledMajor(String abledMajor) {
        this.abledMajor = abledMajor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String timeTable) {
        this.time = timeTable;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherID='" + teacherID + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", school='" + school + '\'' +
                ", abledMajor='" + abledMajor + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
