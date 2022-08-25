package com.vcampus.pojo;

import com.vcampus.dao.utils.StringAndImage;

public class Goods {
    String goodsID;
    String goodsName;
    String seller;
    String price;
    String state;
    String dealDate;
    String picture;
    Integer num;
    Integer type;

    public Goods(String goodsID, String goodsName, String seller, String price, String state, String dealDate, String path, Integer num, Integer type) {
        this.goodsID = goodsID;
        this.goodsName = goodsName;
        this.seller = seller;
        this.price = price;
        this.state = state;
        this.dealDate = dealDate;
        try {
            this.picture = StringAndImage.ImageToString(path);
        }catch (Exception e){
            System.out.println("create image error");
        }
        this.num = num;
        this.type = type;
    }

    public Goods() {
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDealDate() {
        return dealDate;
    }

    public void setDealDate(String dealDate) {
        this.dealDate = dealDate;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String path) {
        try {
            this.picture = StringAndImage.ImageToString(path);
        }catch (Exception e){
            System.out.println("create image wrong");
        }
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsID='" + goodsID + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", seller='" + seller + '\'' +
                ", price='" + price + '\'' +
                ", state='" + state + '\'' +
                ", dealDate='" + dealDate + '\'' +
                ", num=" + num +
                ", type=" + type +
                '}';
    }
}
