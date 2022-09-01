package com.vcampus.pojo;

/**
 * 课成绩
 *
 * @author 12107
 * @date 2022/09/01
 */
public class LessonGrade {
    String studentID;
    String innerID;
    Integer grade;

    /**
     * 课成绩
     *
     * @param studentID 学生一卡通
     * @param innerID   内部id
     * @param grade     年级
     */
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
