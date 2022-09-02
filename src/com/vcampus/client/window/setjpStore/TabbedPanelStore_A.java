/** ===================================================
 * Title: TabbedPanelStore_A.java
 * Created: [2022-8-17 17:19:27] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 校园商城管理——管理员界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-17,创建此文件
 *2.
 *3.
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 管理员身份校园商店管理界面
 * @author 韩宇 张星喆
 * @date 2022/08/17
 */
public class TabbedPanelStore_A extends JTabbedPane {
    public TabbedPanelStore_A(int flag)
    {
        this.setBackground(new Color(0x0000001, true));
        this.setOpaque(false);
        if(flag==3) {
            this.setTabPlacement(1);
            this.setBounds(0, 0, 1400, 700);//注意！！！！！！！！！！！！！！！！！！！！！！！


            PanelGoodsManage_A goodsManage = new PanelGoodsManage_A();
            JPanel dealManage = new PanelDealManage_A();


            this.addTab("商品管理", new ImageIcon("Vcampus//resource//tab_store.png"), goodsManage, "商品管理");
            this.addTab("交易记录", new ImageIcon("Vcampus//resource//tab_orders.png"), dealManage, "交易信息");
            this.setFont(new Font("宋体", Font.BOLD, 24));
        }

    }



}
