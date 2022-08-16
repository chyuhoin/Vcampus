package com.vcampus.pojo;

public class Lesson {
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

    public Lesson(String innerID, String lessonID, String name, String teacherID, Integer maxSize, Integer leftSize, String time, String school, String major, Integer isExam) {
        this.innerID = innerID;
        this.lessonID = lessonID;
        this.name = name;
        this.teacherID = teacherID;
        this.maxSize = maxSize;
        this.leftSize = leftSize;
        this.time = time;
        this.school = school;
        this.major = major;
        this.isExam = isExam;
    }

    public Lesson() {
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
                '}';
    }
}
