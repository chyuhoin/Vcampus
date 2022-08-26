package com.vcampus.server.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.Message;
import com.vcampus.pojo.Goods;
import com.vcampus.pojo.User;
import com.vcampus.server.service.ShopService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Shop controller.
 */
public class ShopController implements Controller{

    private final ShopService service;
    private final Gson gson;
    private final Map<String, Object> map;

    /**
     * Instantiates a new Shop controller.
     */
    public ShopController() {
        service = new ShopService();
        gson = new Gson();
        map = new HashMap<>();
    }

    private List<Goods> getGoods(Message msg) {
        Goods goods = gson.fromJson(msg.getData(), Goods.class);
        return service.getGoodsBy(goods);
    }

    private String buyGoods(Message msg) {
        String data = msg.getData();
        Map<?,String> input = gson.fromJson(data, new TypeToken<Map<String, String>>(){}.getType());
        return service.buyOne(input.get("studentID"), input.get("goodsID")) ? "OK" : "Error";
    }

    private List<Goods> getWhatImSelling(Message msg) {
        User user = gson.fromJson(msg.getData(), User.class);
        Goods goods = new Goods();
        goods.setSeller(user.getStudentID());
        return service.getGoodsBy(goods);
    }

    private String adminAddGoods(Message msg) {
        Goods goods = gson.fromJson(msg.getData(), Goods.class);
        return service.addOneKind(goods) ? "OK" : "Error";
    }

    private String adminDeleteGoods(Message msg) {
        Goods goods = gson.fromJson(msg.getData(), Goods.class);
        return service.deleteOneKind(goods) ? "OK" : "Error";
    }

    private String changeSellingGoods(Message msg) {
        Goods goods = gson.fromJson(msg.getData(), Goods.class);
        return service.changeOne(goods) ? "OK" : "Error";
    }

    @Override
    public Message check(Message msg) {

        switch (msg.getOperation()) {
            case "get":
                map.put("res", getGoods(msg));
                break;
            case "buy":
                map.put("res", buyGoods(msg));
                break;
            case "getSell":
                map.put("res", getWhatImSelling(msg));
                break;
            case "put":
                map.put("res", changeSellingGoods(msg));
        }
        if(msg.getStatus().equals("admin")) {
            switch (msg.getOperation()) {
                case "post": {
                    map.put("res", adminAddGoods(msg));
                    break;
                }
                case "delete": {
                    map.put("res", adminDeleteGoods(msg));
                    break;
                }
                case "put": {
                    map.put("res", changeSellingGoods(msg));
                    break;
                }
            }
        }

        if(map.isEmpty()) return new Message("404", "{res: Wrong Request!}");
        else return new Message("200", gson.toJson(map));
    }
}
