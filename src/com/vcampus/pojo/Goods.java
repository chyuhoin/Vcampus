package com.vcampus.pojo;

import com.vcampus.dao.utils.StringAndImage;

/**
 * 货物
 *
 * @author 刘骐
 * @date 2022/09/01
 */
public class Goods {
    String goodsID;
    String goodsName;
    String seller;
    String price;
    String state;
    String dealDate;//上架时间
    String picture;
    Integer num;
    Integer type;

    /**
     * 货物
     *
     * @param goodsID   商品id
     * @param goodsName 商品名称
     * @param seller    卖方
     * @param price     价格
     * @param state     状态
     * @param dealDate  交易日期
     * @param path      图片
     * @param num       数量
     * @param type      类型
     */
    public Goods(String goodsID, String goodsName, String seller, String price, String state, String dealDate, String path, Integer num, Integer type) {
        this.goodsID = goodsID;
        this.goodsName = goodsName;
        this.seller = seller;
        this.price = price;
        this.state = state;
        this.dealDate = dealDate;
        this.picture = path;
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

    public String getDealDate() { return dealDate; }


    public void setDealDate(String dealDate) {
        this.dealDate = dealDate;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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
