package com.vcampus.server.controller;

import com.google.gson.Gson;
import com.google.gson.internal.Pair;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.Message;
import com.vcampus.pojo.Goods;
import com.vcampus.pojo.Record;
import com.vcampus.pojo.User;
import com.vcampus.server.service.ShopService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商店模块的Controller
 *
 * @author ZhongHaoyuan
 */
public class ShopController implements Controller{

    private final ShopService service;
    private final Gson gson;
    private final Map<String, Object> map;

    /**
     * 初始化一个ShopController
     */
    public ShopController() {
        service = new ShopService();
        gson = new Gson();
        map = new HashMap<>();
    }

    /**
     * 将json字符串解析成goodsID和studentID的一个pair
     *
     * @param msg 消息
     * @return the pair
     */
    private Pair<String, String> parseJSON(Message msg) {
        Map<String,Object> arguments = new Gson().fromJson(msg.getData(),
                new TypeToken<HashMap<String,Object>>(){}.getType());
        String goodsID = (String) arguments.get("goodsID");
        String studentId = (String) arguments.get("studentID");
        return new Pair<>(studentId, goodsID);
    }

    /**
     * 按照传入的Goods返回符合对应要求的商品
     *
     * @param msg 消息，包含一个用来描述搜索条件的Goods
     * @return the goods
     */
    private List<Goods> getGoods(Message msg) {
        Goods goods = gson.fromJson(msg.getData(), Goods.class);
        return service.getGoodsBy(goods);
    }

    /**
     * 购买一个商品，返回成功还是失败
     *
     * @param msg 消息，包含购买者的id和要买的商品的id
     * @return 结果字符串
     */
    private String buyGoods(Message msg) {
        Pair<String, String> pair = parseJSON(msg);
        return service.buyOne(pair.first, pair.second) ? "OK" : "Error";
    }

    /**
     * 获得这个用户正在售卖的商品列表
     *
     * @param msg 消息，包含一个User
     * @return 正在售卖的商品列表
     */
    private List<Goods> getWhatImSelling(Message msg) {
        User user = gson.fromJson(msg.getData(), User.class);
        Goods goods = new Goods();
        goods.setSeller(user.getStudentID());
        return service.getGoodsBy(goods);
    }

    /**
     * 获得这个用户已经买了的商品记录
     *
     * @param msg 消息，包含一个User
     * @return 该用户的全部购买记录
     */
    private List<Record> getWhatIveBought(Message msg) {
        User user = gson.fromJson(msg.getData(), User.class);
        return service.getBoughtGoods(user);
    }

    /**
     * 卖出一个商品
     *
     * @param msg 消息，包含一个被卖的商品Goods
     * @return 表示是否正常的字符串
     */
    private String sellGoods(Message msg) {
        Goods goods = gson.fromJson(msg.getData(), Goods.class);
        return service.addOneKind(goods) ? "OK" : "Error";
    }

    /**
     * 删除一个商品，管理员的话可以随便乱删
     *
     * @param msg 消息，包含一个要被删的Goods
     * @return 表示是否正常的字符串
     */
    private String adminDeleteGoods(Message msg) {
        Goods goods = gson.fromJson(msg.getData(), Goods.class);
        return service.deleteOneKind(goods) ? "OK" : "Error";
    }

    /**
     * 改变正在卖的商品的信息
     *
     * @param msg 消息，包含一个Goods
     * @return 表示是否正常的字符串
     */
    private String changeSellingGoods(Message msg) {
        Goods goods = gson.fromJson(msg.getData(), Goods.class);
        return service.changeOne(goods) ? "OK" : "Error";
    }

    /**
     * 确认收货
     *
     * @param msg 消息
     * @return 表示是否正常的字符串
     */
    private String confirmGoods(Message msg) {
        Pair<String, String> pair = parseJSON(msg);
        return service.confirm(pair.first, pair.second) ? "OK" : "Error";
    }

    /**
     * 退货
     *
     * @param msg 消息
     * @return 表示是否正常的字符串
     */
    private String returnGoods(Message msg) {
        Pair<String, String> pair = parseJSON(msg);
        return service.returnGoods(pair.first, pair.second) ? "OK" : "Error";
    }

    @Override
    public Message check(Message msg)  {

        switch (msg.getOperation()) {
            case "get": //传入Goods
                map.put("res", getGoods(msg));
                break;
            case "post": //传入Goods
                map.put("res", sellGoods(msg));
                break;
            case "delete": //传入Goods
                map.put("res", adminDeleteGoods(msg));
                break;
            case "put": //传入Goods
                map.put("res", changeSellingGoods(msg));
                break;
            case "buy": //传入Pair
                map.put("res", buyGoods(msg));
                break;
            case "getSell": //传入User
                map.put("res", getWhatImSelling(msg));
                break;
            case "getBuy": //传入User
                map.put("res", getWhatIveBought(msg));
                break;
            case "confirm": //传入Pair
                map.put("res", confirmGoods(msg));
                break;
            case "return": //传入Pair
                map.put("res", returnGoods(msg));
                break;
        }

        if(map.isEmpty()) return new Message("404", "{res: Wrong Request!}");
        else return new Message("200", gson.toJson(map));
    }
}
