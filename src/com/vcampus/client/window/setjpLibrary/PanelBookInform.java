/** ===================================================
 * Title: PanelBookInform.java
 * Created: [2022-8-22  09:01:13] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 图书信息展示界面-通用
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-22,创建此文件
 *2. 2022-8-22， 完善设置 修改人：韩宇
 *3. 2022-8-23, 增加参数，前后端链接 修改人：韩宇
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window.setjpLibrary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.dao.utils.StringAndImage;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Book;

public class PanelBookInform extends JPanel {
    JLabel lblImg = new JLabel();//图书照片
    JLabel lblBookID = new JLabel("书籍号   : ");//书籍号
    JTextField txtBookID = new JTextField();
    JLabel lblBookName = new JLabel("书名     : ");//书名
    JTextField txtBookName = new JTextField();
    JLabel lblAuthor = new JLabel("作者     : ");
    JTextField txtAuthor = new JTextField();
    JLabel lblType = new JLabel("类型     : ");
    JTextField txtType = new JTextField();
    JLabel lblLeftSize = new JLabel ("剩余册数 : ");
    JTextField txtLeftSize = new JTextField();

    JLabel labels[] = {lblBookID,lblBookName,lblAuthor,lblType,lblLeftSize};
    JTextField texts[] = {txtBookID,txtBookName,txtAuthor,txtType,txtLeftSize};

    public PanelBookInform(Book book,Boolean flag)
    {
        this.setLayout(null);
        //setPanel(book,false);
        int x=380,y=30;//起始坐标
        int lblWidth=200,lblHeight=40,txtWidth=300, txtHeight=40;
        int heightDiffer=60;//上下两行高度差
        int ltDiffer1=150;//1-左起两列标签文本框间隔 2-第三列标签文本框间隔
        removeAll();
        //设置照片
        //照片
        ImageIcon img = null;// 这是背景图片 .png .jpg .gif 等格式的图片都可以
        System.out.println("book.getImage()="+book.getImage());
        if(book.getImage()==null)
        {
            System.out.println("没有图片");
            img = new ImageIcon("noFig.png");
            //img.setImage(img.getImage().getScaledInstance(180,220,Image.SCALE_DEFAULT));
        }
        else
        {
            try {
                Image Img = Toolkit.getDefaultToolkit().createImage(StringAndImage.StringToImage(book.getImage()));
                img = new ImageIcon(Img);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //System.out.println("img==null:"+(img==null)+img);

        img.setImage(img.getImage().getScaledInstance(180,220,Image.SCALE_DEFAULT));//这里设置图片大小，目前是20*20
        lblImg.setIcon(img);
        lblImg.setBounds(150,60,180,220);
        lblImg.setBorder(BorderFactory.createTitledBorder("分组框")); //设置面板边框，实现分组框的效果，此句代码为关键代码
        lblImg.setBorder(BorderFactory.createLineBorder(Color.black));//设置面板边框颜色

        txtBookID.setText(book.getBookID());//书籍号
        txtBookName.setText(book.getBookName());//书名
        txtAuthor.setText(book.getAuthor());//作者
        txtType.setText(book.getType());//类型
        txtLeftSize.setText(book.getLeftSize().toString());//剩余册数

        //设置其余坐标和字体
        for(int i=0;i<5;i++)
        {
            labels[i].setBounds(x,y+heightDiffer*i,lblWidth,lblHeight);
            texts[i].setBounds(x+ltDiffer1,y+heightDiffer*i,txtWidth,txtHeight);
            setLabelFont(labels[i],texts[i],flag);
            add(labels[i]); add(texts[i]);
        }
        add(lblImg);

        updateUI();
        repaint();
    }

    ////需要增加上传图片的操作
   /* public void setPanel(Book b, Boolean flag)//布置界面  参数：书本 bool型0-显示传入书本信息，1-空白文本框
    {
        int x=380,y=30;//起始坐标
        int lblWidth=300,lblHeight=40,txtWidth=500, txtHeight=40;
        int heightDiffer=60;//上下两行高度差
        int ltDiffer1=150;//1-左起两列标签文本框间隔 2-第三列标签文本框间隔
        //int llDiffer=270;//两个标签之间的差距
        removeAll();
        //设置照片
        ImageIcon img = new ImageIcon("E:\\358-441.jpg");// 这是背景图片 .png .jpg .gif 等格式的图片都可以
        img.setImage(img.getImage().getScaledInstance(180,220,Image.SCALE_DEFAULT));//这里设置图片大小，目前是20*20
        lblImg.setIcon(img);
        lblImg.setBounds(150,60,180,220);

        txtBookID.setText("00001");//书籍号
        txtBookName.setText("计算机组成原理");//书名
        txtAuthor.setText("任国林");//作者
        txtType.setText("教科书");//类型
        txtLeftSize.setText("20");//剩余册数

        //设置其余坐标和字体
        for(int i=0;i<5;i++)
        {
            labels[i].setBounds(x,y+heightDiffer*i,lblWidth,lblHeight);
            texts[i].setBounds(x+ltDiffer1,y+heightDiffer*i,txtWidth,txtHeight);
            setLabelFont(labels[i],texts[i],false);
            add(labels[i]); add(texts[i]);
        }
        add(lblImg);

        updateUI();
        repaint();
    }

    */

    public void removeInform()
    {
        for(int i=0;i<5;i++)
        {
            remove(labels[i]);
            remove(texts[i]);
        }
    }

    public void setLabelFont(JLabel label,JTextField text,Boolean f)
    {
        label.setFont(new Font("楷体", Font.BOLD, 24));
        text.setFont(new Font("楷体", Font.BOLD, 20));
        text.setEditable(f);//true 可编辑，false 不可编辑
    }
}
