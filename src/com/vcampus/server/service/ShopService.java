package com.vcampus.server.service;

import com.vcampus.dao.ShopDao;
import com.vcampus.pojo.Goods;
import com.vcampus.pojo.Record;
import com.vcampus.pojo.User;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 商店模块的服务层
 *
 * @author ZhongHaoyuan
 */
public class ShopService implements Service {

    /**
     * 获得全部的商品
     *
     * @return the all goods
     */
    public List<Goods> getAllGoods() {
        try {
            return ShopDao.search();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 通过Goods里面封装的条件来获得符合要求的商品列表
     * 使用反射技术来筛选每个属性
     *
     * @param goods 封装搜索条件的Goods
     * @return 符合条件的所有商品
     */
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

    /**
     * 购买一个商品
     *
     * @param studentID 购买者的ID
     * @param goodsID   要被买的商品的ID
     * @return 操作成功与否
     */
    public boolean buyOne(String studentID, String goodsID) {
        return Boolean.TRUE.equals(ShopDao.buyGoods(studentID, goodsID));
    }

    /**
     * 新增一种商品
     *
     * @param goods 要被新增的这种商品
     * @return 操作已经成功了吗
     */
    public boolean addOneKind(Goods goods)  {
        return ShopDao.addGoods(goods);
    }

    /**
     * 删除某一种商品
     *
     * @param goods 要被删除的这种商品
     * @return 移除已经成功了吗
     */
    public boolean deleteOneKind(Goods goods) {
        return ShopDao.deleteGoods("goodsID", goods.getGoodsID());
    }

    /**
     * 改变商品的属性，以ID和seller为参照物
     * 这边依然是只能暴力一个属性一个属性地改
     *
     * @param goods 要把商品修改成的目标
     * @return 操作成功与否
     */
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
        res = res & ShopDao.revise(goods.getSeller(), goods.getGoodsID(),
                "picture", goods.getPicture());
        return res;
    }

    /**
     * 获得用户全部买过的商品的购买记录
     *
     * @param user 用户
     * @return 这个用户买过的商品记录
     */
    public List<Record> getBoughtGoods(User user) {
        try {
            return ShopDao.searchMine(user.getStudentID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 确认收货
     *
     * @param studentID 购买者的ID
     * @param goodsID   被买的商品的ID
     * @return 操作成功与否
     */
    public boolean confirm(String studentID, String goodsID) {
        return ShopDao.confirm(studentID, goodsID);
    }

    /**
     * 退货
     *
     * @param studentID 退货者的ID
     * @param goodsID   商品ID
     * @return 操作成功与否
     */
    public boolean returnGoods(String studentID, String goodsID) {
        return ShopDao.returnGoods(studentID, goodsID);
    }
}
