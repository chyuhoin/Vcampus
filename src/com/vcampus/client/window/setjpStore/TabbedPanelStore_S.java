/** ===================================================
 * Title: TabbedPanelStore_S.java
 * Created: [2022-8-17 17:20:31] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 校园商城管理——学生界面
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
import com.vcampus.pojo.Record;
import com.vcampus.pojo.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabbedPanelStore_S extends JTabbedPane {

    MessagePasser passer = ClientMessagePasser.getInstance();
    Object[] columnNames = new Object[]{"已购商品编号","购买日期","商品状态","收货","退货"};
    public TabbedPanelStore_S(int flag,String ID)
    {
        if(flag==1) {
            this.setTabPlacement(1);
            this.setBounds(0, 0, 1400, 650);//注意！！！！！！！！！！！！！！！！！！！！！！！

            PanelHomePage_ST homePage = new PanelHomePage_ST(ID, flag);
            PanelMyStore_S myStore = new PanelMyStore_S(ID);
            PanelMyPurchaseOrder_ST myPurchase = new PanelMyPurchaseOrder_ST(ID, flag);


            this.addTab("商城首页", null, homePage, "商城首页");
            this.addTab("我的店铺", null, myStore, "我的店铺");
            this.addTab("我的订单", null, myPurchase, "我的订单");
            this.setFont(new Font("宋体", Font.BOLD, 24));

            //选项卡刷新??????????好像不更新

            this.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getClickCount() == 1) {
                        myPurchase.removeAll();
                        myPurchase.setTable(columnNames, getAllOrder(ID));
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
            //JOptionPane.showMessageDialog(this, "未查询到购买记录", "警告", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }


}
