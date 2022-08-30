package com.vcampus.server.service;

import com.vcampus.dao.ShopDao;
import com.vcampus.pojo.Goods;
import com.vcampus.pojo.Record;
import com.vcampus.pojo.User;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShopService implements Service {

    public List<Goods> getAllGoods() {
        try {
            return ShopDao.search();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Goods> getGoodsBy(Goods goods) {
        Class<?> clazz = null;
        List<Goods> res = null;
        try {
            res = ShopDao.search();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(res == null) return new ArrayList<>();
        try {
            clazz = goods.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for(Field field: fields){
                String name = field.getName();
                field.setAccessible(true);
                Object resultValue = field.get(goods);

                if(resultValue == null) continue;
                Iterator<Goods> it = res.iterator();
                while(it.hasNext()) {
                    Goods item = it.next();
                    if(!field.get(item).equals(resultValue))
                        it.remove();
                }

                System.out.println(name + ": " + resultValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean buyOne(String studentID, String goodsID) {
        return Boolean.TRUE.equals(ShopDao.buyGoods(studentID, goodsID));
    }

    public boolean addOneKind(Goods goods) {
        return ShopDao.addGoods(goods);
    }

    public boolean deleteOneKind(Goods goods) {
        return ShopDao.deleteGoods("num", goods.getGoodsID());
    }

    public boolean changeOne(Goods goods) {
        boolean res = ShopDao.revise(goods.getSeller(), goods.getGoodsID(),
                "goodsName", goods.getGoodsName());
        res = res & ShopDao.revise(goods.getSeller(), goods.getGoodsID(),
                "price", goods.getPrice());
        res = res & ShopDao.revise(goods.getSeller(), goods.getGoodsID(),
                "dealDate", goods.getDealDate());
        res = res & ShopDao.revise(goods.getSeller(), goods.getGoodsID(),
                "num", goods.getNum());
        res = res & ShopDao.revise(goods.getSeller(), goods.getGoodsID(),
                "type", goods.getType());
        return res;
    }

    public List<Record> getBoughtGoods(User user) {
        try {
            return ShopDao.searchMine(user.getStudentID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public boolean confirm(String studentID, String goodsID) {
        return ShopDao.confirm(studentID, goodsID);
    }

    public boolean returnGoods(String studentID, String goodsID) {
        return ShopDao.returnGoods(studentID, goodsID);
    }
}
