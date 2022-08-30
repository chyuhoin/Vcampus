package com.vcampus.server.controller;

import com.google.gson.Gson;
import com.google.gson.internal.Pair;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.Message;
import com.vcampus.pojo.Goods;
import com.vcampus.pojo.Record;
import com.vcampus.pojo.User;
import com.vcampus.server.service.ShopService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShopController implements Controller{

    private final ShopService service;
    private final Gson gson;
    private final Map<String, Object> map;

    public ShopController() {
        service = new ShopService();
        gson = new Gson();
        map = new HashMap<>();
    }

    private Pair<String, String> parseJSON(Message msg) {
        Map<String,Object> arguments = new Gson().fromJson(msg.getData(),
                new TypeToken<HashMap<String,Object>>(){}.getType());
        String goodsID = (String) arguments.get("goodsID");
        String studentId = (String) arguments.get("studentID");
        return new Pair<>(studentId, goodsID);
    }

    private List<Goods> getGoods(Message msg) {
        Goods goods = gson.fromJson(msg.getData(), Goods.class);
        return service.getGoodsBy(goods);
    }

    private String buyGoods(Message msg) {
        Pair<String, String> pair = parseJSON(msg);
        return service.buyOne(pair.first, pair.second) ? "OK" : "Error";
    }

    private List<Goods> getWhatImSelling(Message msg) {
        User user = gson.fromJson(msg.getData(), User.class);
        Goods goods = new Goods();
        goods.setSeller(user.getStudentID());
        return service.getGoodsBy(goods);
    }

    private List<Record> getWhatIveBought(Message msg) {
        User user = gson.fromJson(msg.getData(), User.class);
        return service.getBoughtGoods(user);
    }

    private String sellGoods(Message msg) {
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

    private String confirmGoods(Message msg) {
        Pair<String, String> pair = parseJSON(msg);
        return service.confirm(pair.first, pair.second) ? "OK" : "Error";
    }

    private String returnGoods(Message msg) {
        Pair<String, String> pair = parseJSON(msg);
        return service.returnGoods(pair.first, pair.second) ? "OK" : "Error";
    }

    @Override
    public Message check(Message msg) {

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
        System.out.println(gson.toJson(map));
        if(map.isEmpty()) return new Message("404", "{res: Wrong Request!}");
        else return new Message("200", gson.toJson(map));
    }
}
