package com.vcampus.pojo;

/**
 * 站内消息
 *
 * @author 刘骐
 * @date 2022/09/01
 */
public class InnerMessage {
    String studentID;
    String content;
    String sender;

    Integer innerID;

    public InnerMessage() {
    }

    /**
     * 站内消息
     *
     * @param studentID 学生一卡通
     * @param content   内容
     * @param from      来自
     * @param innerID   内部id
     */
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
                "studentID='" + studentID + '\'' +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", innerID=" + innerID +
                '}';
    }
}
