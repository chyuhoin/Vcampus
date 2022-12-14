package com.vcampus.pojo;

import java.io.Serializable;

/**
 * 课程
 *
 * @author 刘骐
 * @date 2022/09/01
 */
public class Lesson implements Serializable {
    String innerID;
    String lessonID;
    String name;
    String teacherID;
    Integer maxSize;
    Integer leftSize;
    String time;
    String school;
    String major;
    Integer isExam;
    String classroom;
    Integer length;
    Integer status;

    /**
     * 课程
     *
     * @param lessonID  教训id
     * @param name      名字
     * @param teacherID 老师id
     * @param maxSize   最大尺寸
     * @param leftSize  离开了大小
     * @param time      时间
     * @param school    学校
     * @param major     专业
     * @param isExam    是否考试
     * @param classroom 教室
     * @param length    长度
     * @param status    状态
     *///lesson的innerID为lessonID+teacherID，不需要手动输入
    public Lesson(String lessonID, String name, String teacherID, Integer maxSize, Integer leftSize, String time, String school, String major, Integer isExam,String classroom,Integer length,Integer status) {
        if(teacherID == null)this.innerID =lessonID;
        else this.innerID = lessonID+teacherID;
        this.lessonID = lessonID;
        this.name = name;
        this.teacherID = teacherID;
        this.maxSize = maxSize;
        this.leftSize = leftSize;
        this.time = time;
        this.school = school;
        this.major = major;
        this.isExam = isExam;
        this.classroom = classroom;
        this.length = length;
        this.status = status;
    }

    public Lesson() {
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInnerID() {
        return innerID;
    }

    public void setInnerID(String innerID) {
        this.innerID = innerID;
    }

    public String getLessonID() {
        return lessonID;
    }

    public void setLessonID(String lessonID) {
        this.lessonID = lessonID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Integer getLeftSize() {
        return leftSize;
    }

    public void setLeftSize(Integer leftSize) {
        this.leftSize = leftSize;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getIsExam() {
        return isExam;
    }

    public void setIsExam(Integer isExam) {
        this.isExam = isExam;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "innerID='" + innerID + '\'' +
                ", lessonID='" + lessonID + '\'' +
                ", name='" + name + '\'' +
                ", teacherID='" + teacherID + '\'' +
                ", maxSize=" + maxSize +
                ", leftSize=" + leftSize +
                ", time='" + time + '\'' +
                ", school='" + school + '\'' +
                ", major='" + major + '\'' +
                ", isExam=" + isExam +
                ", classroom='" + classroom + '\'' +
                ", length=" + length +
                ", status=" + status +
                '}';
    }
}
