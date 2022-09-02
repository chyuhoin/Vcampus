/** ===================================================
 * Title: TabbedPanelStore_T.java
 * Created: [2022-8-17 17:21:13] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 校园商城管理——教师界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-17,创建此文件
 *2.
 *3.
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpStore;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Goods;
import com.vcampus.pojo.Record;
import com.vcampus.pojo.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 教师身份校园商店管理界面
 * @author 韩宇 张星喆
 * @date 2022/08/17
 */
public class TabbedPanelStore_T extends JTabbedPane {

    MessagePasser passer = ClientMessagePasser.getInstance();
    Object[] columnNames = new Object[]{"已购商品编号","购买日期","商品状态","收货","退货"};
    public TabbedPanelStore_T(int flag, String ID)
    {
        this.setBackground(new Color(0x0000001, true));
        this.setOpaque(false);
        if(flag==2) {
            this.setTabPlacement(1);
            this.setBounds(0, 0, 1400, 700);//注意！！！！！！！！！！！！！！！！！！！！！！！

            PanelHomePage_ST homePage = new PanelHomePage_ST(ID, flag);
            PanelMyPurchaseOrder_ST myPurchase = new PanelMyPurchaseOrder_ST(ID, flag);

            this.addTab("商城首页", new ImageIcon("Vcampus//resource//tab_store.png"), homePage, "商城首页");
            this.addTab("我的订单", new ImageIcon("Vcampus//resource//tab_orders.png"), myPurchase, "我的订单");
            this.setFont(new Font("宋体", Font.BOLD, 24));

            this.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getClickCount() == 1) {
                        myPurchase.removeAll();
                        myPurchase.setTable(columnNames, getAllOrder(ID));
                        homePage.setPanel1(new Goods());
                        // purchaseOrder.updateUI();
                        // purchaseOrder.repaint();
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
        }

    }

    public Object[][] getAllOrder(String ID){
        User user = new User();
        user.setStudentID(ID);
        Gson gson = new Gson();
        String s = gson.toJson(user);
        passer.send(new Message("student", s, "shop", "getBuy"));

        try {
            Thread.sleep(100);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        Message msg = passer.receive();
        Map<String, java.util.List<Record>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Record>>>() {}.getType());
        List<Record> res = map.get("res");

        if(res.size()!=0)
        {
            Object[][] rowData = new Object[res.size()][5];
            for (int i = 0; i < res.size(); i++) {
                rowData[i][0] = res.get(i).getGoodsID();
                rowData[i][1] = res.get(i).getDate();
                rowData[i][2] = res.get(i).getStatus();
            }
            return rowData;
        }
        else
        {
            return null;
        }
    }

}
