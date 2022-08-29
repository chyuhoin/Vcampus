package com.vcampus.client.window.setjpStore;

import com.vcampus.client.window.setjpLibrary.PanelBookInform;
import com.vcampus.pojo.Book;
import com.vcampus.pojo.Goods;

import javax.swing.*;

public class PanelBuyGoods extends JPanel {
    JButton btnInquire = new JButton("查询");
    JPanel BuyGoods = new PanelGoodsInform(new Goods("123","123","123","123","123","123",null,1,1),true);
    String[] listData = new String[]{"选择搜索条件","书籍号", "书名", "作者", "类型"};
    // 创建一个下拉列表框
    public PanelBuyGoods() {
        this.setLayout(null);
        int x=470,y=50;//起始坐标
        int txtWidth=110, txtHeight=42;
        this.add(btnInquire);
        BuyGoods.setBounds(0,150,1400,350);
        this.add(BuyGoods);
    }
}
