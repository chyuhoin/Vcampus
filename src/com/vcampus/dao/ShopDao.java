package com.vcampus.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vcampus.dao.utils.CRUD;
import com.vcampus.pojo.Goods;
import com.vcampus.pojo.Record;
import org.junit.Test;

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
    //添加商品
    public static Boolean addGoods(Goods goods){
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
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }
    //修改
    public static Boolean revise(String studentID,String goodsID,String field,Object value) {
        try {
            String sql = "update tb_GOODS set " + field+"  = ? where seller = ? and  = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1,value);
            ps.setString(2,studentID);
            ps.setString(3,goodsID);
            ps.executeUpdate();
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
}
