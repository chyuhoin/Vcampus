package com.vcampus.pojo;

public class Student {
    String studentID;
    String name;
    String studentNumber;
    String IDcard;
    String school;
    String major;
    Integer sex;
    String classs;
    Long educationalSystem;
    String politics;
    Long grade;
    String phoneNumber;
    byte[] image;
    Integer status;

    public Student(String studentID, String name, String studentNumber, String IDcard, String school, String major, Integer sex, String classs, Long educationalSystem, String politics, Long grade, String phoneNumber, byte[] image, Integer status) {
        this.studentID = studentID;
        this.name = name;
        this.studentNumber = studentNumber;
        this.IDcard = IDcard;
        this.school = school;
        this.major = major;
        this.sex = sex;
        this.classs = classs;
        this.educationalSystem = educationalSystem;
        this.politics = politics;
        this.grade = grade;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.status = status;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getIDcard() {
        return IDcard;
    }

    public void setIDcard(String IDcard) {
        this.IDcard = IDcard;
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public Long getEducationalSystem() {
        return educationalSystem;
    }

    public void setEducationalSystem(Long educationalSystem) {
        this.educationalSystem = educationalSystem;
    }

    public String getPolitics() {
        return politics;
    }

    public void setPolitics(String politics) {
        this.politics = politics;
    }

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentID='" + studentID + '\'' +
                ", name='" + name + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", IDcard='" + IDcard + '\'' +
                ", school='" + school + '\'' +
                ", major='" + major + '\'' +
                ", sex=" + sex +
                ", classs='" + classs + '\'' +
                ", educationalSystem=" + educationalSystem +
                ", politics='" + politics + '\'' +
                ", grade=" + grade +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", status=" + status +
                '}';
    }
}
