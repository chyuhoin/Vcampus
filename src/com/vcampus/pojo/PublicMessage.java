package com.vcampus.pojo;

public class PublicMessage {
    String studentID;
    String content;

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
}
