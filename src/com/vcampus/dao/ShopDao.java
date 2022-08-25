package com.vcampus.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vcampus.pojo.Goods;

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
        return addClass(goods,"tb_GOODS");
    }
    //删除商品
    public static Boolean deleteGoods(String field,Object value){
        return delete(field,value,"tb_GODDS");
    }
    //买入
    public static Boolean buyGoods(String studentID,String goodsID){
        return null;
    }
}
