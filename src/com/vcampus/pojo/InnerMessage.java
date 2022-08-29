package com.vcampus.pojo;

public class InnerMessage {
    String studentID;
    String content;
    String sender;

    Integer innerID;

    public InnerMessage() {
    }

    public InnerMessage(String studentID, String content, String from, Integer innerID) {
        this.studentID=studentID;
        this.content = content;
        this.sender = from;
        this.innerID = innerID;
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

    public Integer getInnerID() {
        return innerID;
    }

    public void setInnerID(Integer innerID) {
        this.innerID = innerID;
    }

    @Override
    public String toString() {
        return "InnerMessage{" +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }
}
