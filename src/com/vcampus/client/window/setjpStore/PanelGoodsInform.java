/** ===================================================
 * Title: PanelGoodsInform.java
 * Created: [2022-8-29  15:16:13] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 商品详情界面-通用
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-29,创建此文件
 *2. 2022-8-29， 完善设置 修改人：韩宇
 *3.
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window.setjpStore;

import javax.swing.*;
import java.awt.*;
import java.awt.print.Book;

public class PanelGoodsInform extends JPanel {
    JLabel lblImg = new JLabel();//商品照片
    JLabel lblGoodsID = new JLabel("商品编号  : ");//商品编号
    JTextField txtGoodsID = new JTextField();
    JLabel lblGoodsName = new JLabel("商品名称  : ");//商品名称
    JTextField txtGoodsName = new JTextField();
    JLabel lblType = new JLabel ("商品类型  : ");
    JTextField txtType = new JTextField();
    JLabel lblSeller = new JLabel("商品卖家  : ");//卖家一卡通号
    JTextField txtSeller = new JTextField();
    JLabel lblPrice = new JLabel("商品价格  : ");
    JTextField txtPrice = new JTextField();
    JLabel lblDealTime = new JLabel ("上架时间  : ");
    JTextField txtDealTime = new JTextField();
    JLabel lblNum = new JLabel ("剩余数量  : ");
    JTextField txtNum = new JTextField();

    JLabel labels[] = {lblGoodsID, lblGoodsName, lblType, lblSeller, lblPrice, lblDealTime, lblNum};
    JTextField texts[] = {txtGoodsID, txtGoodsName, txtType, txtSeller, txtPrice, txtDealTime, txtNum};

    //Goods goods = new Goods();

    public PanelGoodsInform()//传入商品的对象，flag 是否可编辑
    {
        this.setLayout(null);
        int x=240,y=30;//起始坐标
        int lblWidth=300,lblHeight=40,txtWidth=350, txtHeight=40;
        int heightDiffer=60;//上下两行高度差
        int ltDiffer1=150;//1-左起两列标签文本框间隔 2-第三列标签文本框间隔
        //int llDiffer=270;//两个标签之间的差距
        removeAll();
        //设置照片
        ImageIcon img = new ImageIcon("E:\\358-441.jpg");// 这是背景图片 .png .jpg .gif 等格式的图片都可以
        img.setImage(img.getImage().getScaledInstance(180,220,Image.SCALE_DEFAULT));//这里设置图片大小，目前是20*20
        lblImg.setBorder(BorderFactory.createTitledBorder("分组框")); //设置面板边框，实现分组框的效果，此句代码为关键代码
        lblImg.setBorder(BorderFactory.createLineBorder(Color.black));//设置面板边框颜色
        lblImg.setIcon(img);
        lblImg.setBounds(0,110,180,220);

        txtGoodsID.setText("00001");//商品编号
        txtGoodsName.setText("计算机组成原理");//商品名称
        txtType.setText("服装");//商品类型
        txtSeller.setText("213201455");//卖家
        txtPrice.setText("教科书");//商品价格
        txtDealTime.setText("2022/8/30");//上架时间
        txtNum.setText("20");//剩余数量

        //设置其余坐标和字体
        for(int i=0;i<7;i++)
        {
            labels[i].setBounds(x,y+heightDiffer*i,lblWidth,lblHeight);
            texts[i].setBounds(x+ltDiffer1,y+heightDiffer*i,txtWidth,txtHeight);
            //texts[i].setBorder(null);
            setLabelFont(labels[i],texts[i],false);//是否可编辑
            add(labels[i]); add(texts[i]);
        }
        add(lblImg);

        updateUI();
        repaint();
    }

    public void setLabelFont(JLabel label,JTextField text,Boolean flag)
    {
        label.setFont(new Font("楷体", Font.BOLD, 24));
        text.setFont(new Font("楷体", Font.BOLD, 20));
        text.setEditable(flag);
    }

}










