package com.vcampus.pojo;

/**
 * 公共信息
 *
 * @author 刘骐
 * @date 2022/09/01
 */
public class PublicMessage {
    String studentID;
    String content;

    /**
     * 公共信息
     *
     * @param studentID 学生一卡通
     * @param content   内容
     */
    public PublicMessage(String studentID, String content) {
        this.studentID = studentID;
        this.content = content;
    }

    public PublicMessage() {
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PublicMessage{" +
                "studentID='" + studentID + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
