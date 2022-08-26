package com.vcampus.pojo;

public class InnerMessage {
    String studentID;
    String content;
    String sender;

    String innerID;

    public InnerMessage() {
    }

    public InnerMessage(String studentID, String content, String from) {
        this.studentID=studentID;
        this.content = content;
        this.sender = from;
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

    public String getSender() {
        return sender;
    }

    public void setSender(String from) {
        this.sender = from;
    }

    @Override
    public String toString() {
        return "InnerMessage{" +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }
}
