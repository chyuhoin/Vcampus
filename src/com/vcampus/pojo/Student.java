package com.vcampus.pojo;

import com.vcampus.dao.utils.StringAndImage;

import java.io.Serializable;

/**
 * 学生
 *
 * @author 刘骐
 * @date 2022/09/01
 */
public class Student implements Serializable {
    String studentID;
    String name;
    String studentNumber;
    String IDcard;
    String school;
    String major;
    Integer sex;
    String classs;//班级
    Integer educationalSystem;
    String politics;
    Integer grade;
    String phoneNumber;
    //byte[] image;
    Integer status;
    String image;
    String nation;
    String graduateTime;//毕业时间

    Integer status2;

    /**
     * 学生
     *
     * @param studentID         学生一卡通
     * @param name              名字
     * @param studentNumber     学号
     * @param IDcard            身份证
     * @param school            学院
     * @param major             专业
     * @param sex               性别
     * @param classs            班级
     * @param educationalSystem 学制
     * @param politics          政治面貌
     * @param grade             年级
     * @param phoneNumber       电话号码
     * @param status            状态
     * @param path              图片路径
     * @param nation            民族
     * @param graduateTime      毕业时间
     * @param status2           status2
     *///path为图片所在路径
    public Student(String studentID, String name, String studentNumber, String IDcard, String school, String major, Integer sex, String classs, Integer educationalSystem, String politics, Integer grade, String phoneNumber, Integer status, String path,String nation,String graduateTime,Integer status2) {
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
        this.status = status;
        try{
            this.image = StringAndImage.ImageToString(path);
        }catch (Exception e){

        }
        this.graduateTime = graduateTime;
        this.status2 = status2;
    }

    public Integer getStatus2() {
        return status2;
    }

    public void setStatus2(Integer status2) {
        this.status2 = status2;
    }

    public Student() {
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

    public Integer getEducationalSystem() {
        return educationalSystem;
    }

    public void setEducationalSystem(Integer educationalSystem) {
        this.educationalSystem = educationalSystem;
    }

    public String getPolitics() {
        return politics;
    }

    public void setPolitics(String politics) {
        this.politics = politics;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

//    public byte[] getImage() {
//        return image;
//    }

//    public void setImage(byte[] image) {
//        this.image = image;
//    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getGraduateTime() {
        return graduateTime;
    }

    public void setGraduateTime(String graduateTime) {
        this.graduateTime = graduateTime;
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
                ", nation='" + nation + '\'' +
                '}';
    }
}
