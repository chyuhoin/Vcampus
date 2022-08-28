package com.vcampus.pojo;

public class LessonGrade {
    String studentID;
    String innerID;
    Integer grade;

    public LessonGrade(String studentID, String innerID, Integer grade) {
        this.studentID = studentID;
        this.innerID = innerID;
        this.grade = grade;
    }

    public LessonGrade() {
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getInnerID() {
        return innerID;
    }

    public void setInnerID(String innerID) {
        this.innerID = innerID;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "LessonGrade{" +
                "studentID='" + studentID + '\'' +
                ", innerID='" + innerID + '\'' +
                ", grade=" + grade +
                '}';
    }
}
