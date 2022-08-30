package com.vcampus.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vcampus.dao.utils.CRUD;
import com.vcampus.dao.utils.StringAndImage;
import com.vcampus.pojo.Goods;
import com.vcampus.pojo.Record;
import org.junit.Test;

import javax.management.Query;
import javax.swing.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ShopDao extends BaseDao{
    //查询全部
    public static List<Goods> search() throws Exception {
        return searchAll(Goods.class,"tb_GOODS");
    }
    //按字段查询
    public static List<Goods> search(String field,Object value) throws Exception {
        return searchBy(Goods.class,"tb_GOODS",field,value);
    }
    //查看我在卖的商品

    //添加商品
    public static Boolean addGoods(Goods goods) {
        try {
            addClass(goods,"tb_GOODS");
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }
    //删除商品
    public static Boolean deleteGoods(String field,Object value){
        try {
            delete(field,value,"tb_GOODS");
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }
    //买入
    public static Boolean buyGoods(String studentID,String goodsID){
        try {
            conn.setAutoCommit(false);
            addClass(new Record(studentID,goodsID),"tb_RECORD");
            deleteGoods("goodsID",goodsID);
            conn.commit();
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }
    //修改
    public static Boolean revise(String studentID,String goodsID,String field, String  value) {
        try {
           // String sql = "update tb_GOODS set " + field +"  = ? where seller = ? and goodsID = ?";
            String sql = "update tb_GOODS set "+field+" = '"+value+"' "+"where seller = '"+studentID+"' and goodsID = '"+goodsID+"'";
           // System.out.println(sql);
            CRUD.update(sql,conn);
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setObject(1,value);
//            ps.setString(2,studentID);
//            ps.setString(3,goodsID);
//            ps.executeUpdate();
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }
    public static Boolean revise(String studentID,String goodsID,String field, Integer  value) {
        try {
            // String sql = "update tb_GOODS set " + field +"  = ? where seller = ? and goodsID = ?";
            String sql = "update tb_GOODS set "+field+" = "+String.valueOf(value)+" "+" where seller = '"+studentID+"' and goodsID = '"+goodsID+"'";
            CRUD.update(sql,conn);
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setObject(1,value);
//            ps.setString(2,studentID);
//            ps.setString(3,goodsID);
//            ps.executeUpdate();
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }
    //确认收货
    public static Boolean confirm(String studentID,String goodsID){
        try {
            String sql = "update tb_RECORD set status = 1 where studentID = '" + studentID + "' and goodsID = '" + goodsID + "'";
            CRUD.update(sql, conn);
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }
    //退货
    public static Boolean returnGoods(String studentID,String goodsID){
        try {
            String sql = "delete from tb_RECORD where studentID = '"+studentID+"' and goodsID = '"+goodsID+"'";
            CRUD.update(sql,conn);
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }
    //查看我买的商品
    public static List<Record> searchMine(String studentID) throws Exception {
        return searchBy(Record.class,"tb_RECORD","studentID",studentID);
    }
    @Test
    public void test() throws Exception {
        String path = "D:\\_CodeFactory\\javaCODE\\Vcampus\\Vcampus\\img\\QQ.png";
        String picture = StringAndImage.ImageToString(path);
      //  addClass(new Goods("01000","08","07","06","05","04",picture,1,0),"tb_GOODS");
        //addGoods(new Goods("009","08","07","06","05","04",picture,1,0));
        revise("07","09","picture","123456");
       // List<Goods>goods=search("goodsID","009");
       // Goods good = goods.get(0);
        //ImageIcon icon = new ImageIcon(StringAndImage.StringToImage(good.getPicture()));
       // System.out.println(good.getPicture().equals(picture));
    }
}
