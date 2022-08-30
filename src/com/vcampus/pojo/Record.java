package com.vcampus.pojo;

import java.util.Date;

//商品交易记录类
public class Record {
    String studentID;
    String goodsID;
    String date;
    Integer status;//0表示尚未确认收货，1表示确认收货
    public Record(String studentID, String goodsID) {
        this.studentID = studentID;
        this.goodsID = goodsID;
        this.date = String.format("%1$tY-%1$tm-%1$td", new Date(System.currentTimeMillis()));
        this.status = 0;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Record() {
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public String getDate() {
        return date;
    }

    //public Integer getStatus(){return status;};

    public void setDate(Date date) {
        this.date = String.format("%1$tY-%1$tm-%1$td", date);
    }

    @Override
    public String toString() {
        return "Record{" +
                "studentID='" + studentID + '\'' +
                ", goodsID='" + goodsID + '\'' +
                ", date='" + date + '\'' +
                ", status=" + status +
                '}';
    }
}
