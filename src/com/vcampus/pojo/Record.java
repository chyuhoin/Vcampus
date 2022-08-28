package com.vcampus.pojo;

import java.util.Date;

//商品交易记录类
public class Record {
    String studentID;
    String goodsID;
    Date date;
    Integer status;//0表示尚未确认收货，1表示确认收货
    public Record(String studentID, String goodsID) {
        this.studentID = studentID;
        this.goodsID = goodsID;
        this.date = new Date(System.currentTimeMillis());
        this.status = 0;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
