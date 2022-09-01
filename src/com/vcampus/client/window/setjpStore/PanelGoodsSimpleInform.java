/** ===================================================
 * Title: PanelDealManage_A.java
 * Created: [2022-8-29  16:30:45] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 商城首页商品简介界面-通用
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-29,创建此文件
 *2. 2022-8-29， 完善设置 修改人：韩宇
 *3.
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window.setjpStore;
import com.vcampus.dao.utils.StringAndImage;
import com.vcampus.pojo.Goods;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.io.IOException;



public class PanelGoodsSimpleInform extends JPanel {
    JLabel lblImg = new JLabel();//商品照片
    JLabel lblGoodsName = new JLabel("名称: ");//书名
    JTextField txtGoodsName = new JTextField();
    //JLabel lblType = new JLabel ("类型: ");
    //JTextField txtType = new JTextField();
    JLabel lblPrice = new JLabel("价格: ");
    JTextField txtPrice = new JTextField();
    JLabel lblNum = new JLabel ("数量: ");
    JTextField txtNum = new JTextField();

    JLabel labels[] = {lblGoodsName, lblPrice, lblNum};
    JTextField texts[] = {txtGoodsName, txtPrice, txtNum};

    JButton btnMore = new JButton("详情");

    //Goods goods = new Goods();

    public PanelGoodsSimpleInform(PanelHomePage_ST homePage,Goods goods)//传入商品的对象
    {
        this.setLayout(null);
        int x=120,y=35;//起始坐标
        int lblWidth=100,lblHeight=20,txtWidth=120, txtHeight=20;
        int heightDiffer=25;//上下两行高度差
        int ltDiffer1=60;//1-左起两列标签文本框间隔 2-第三列标签文本框间隔
        //int llDiffer=270;//两个标签之间的差距
        removeAll();

        ImageIcon img = null;// 这是背景图片 .png .jpg .gif 等格式的图片都可以
        if(goods.getPicture()==null)
        {
//            System.out.println("没有图片"+goods.getPicture());
            img = new ImageIcon("Pictures//noFig.png");
        }
        else
        {
//            System.out.println("有图片"+goods.getPicture());
            try {
                Image Img = Toolkit.getDefaultToolkit().createImage(StringAndImage.StringToImage(goods.getPicture()));
                img = new ImageIcon(Img);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        img.setImage(img.getImage().getScaledInstance(100,130,Image.SCALE_DEFAULT));//这里设置图片大小，目前是20*20
        lblImg.setBorder(BorderFactory.createTitledBorder("分组框")); //设置面板边框，实现分组框的效果，此句代码为关键代码
        lblImg.setBorder(BorderFactory.createLineBorder(Color.black));//设置面板边框颜色
        lblImg.setIcon(img);
        lblImg.setBounds(10,20,100,130);

        btnMore.setBounds(220,130,80,20);
        btnMore.setFont(new Font("楷体", Font.BOLD, 18));
        btnMore.setContentAreaFilled(false);

        txtGoodsName.setText(goods.getGoodsName());//商品名称
        txtPrice.setText(goods.getPrice());//商品价格
        txtNum.setText(goods.getNum().toString());//剩余数量

        //设置其余坐标和字体
        for(int i=0;i<3;i++)
        {
            labels[i].setBounds(x,y+heightDiffer*i,lblWidth,lblHeight);
            texts[i].setBounds(x+ltDiffer1,y+heightDiffer*i+3,txtWidth,txtHeight);
            texts[i].setBorder(null);
            setLabelFont(labels[i],texts[i]);//是否可编辑
            add(labels[i]); add(texts[i]);
        }
        add(lblImg);
        add(btnMore);

        updateUI();
        repaint();

        btnMore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ///能不能写到外面，调详情页出来，传入商品
                homePage.setPanel2(goods);
                homePage.setCard("panelBuy");
            }
        });
    }

    public void setLabelFont(JLabel label,JTextField text)
    {
        label.setFont(new Font("楷体", Font.BOLD, 18));
        text.setFont(new Font("楷体", Font.BOLD, 16));
        text.setEditable(false);
    }

}



