/** ===================================================
 * Title: PanelDealManage_A.java
 * Created: [2022-8-29  17:02:10] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 我的店铺-学生界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-29,创建此文件
 *2. 2022-8-29， 完善设置 修改人：韩宇
 *3.
 *    修改的内容描述，修改的原因
 */
package com.vcampus.client.window.setjpStore;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.dao.utils.StringAndImage;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Goods;
import com.vcampus.pojo.Record;
import com.vcampus.pojo.User;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class PanelMyStore_S  extends JPanel{
    JLabel lblHint = new JLabel("在售商品");
    JPanel panelAll = new JPanel();
    JPanel panelInform = new JPanel();
    PanelGoodsInform Inform = new PanelGoodsInform(new Goods("","","","","","","",0,0),true);
    JPanel panelAdd = new JPanel();
    JButton btnAdd = new JButton("上架商品");
    JButton btnOk = new JButton("确定");
    JButton btnCancel = new JButton("取消");
    JButton btnReturn = new JButton("返回");
    JButton btnEdit = new JButton("修改");

    CardLayout cardLayout=new CardLayout();
    //ButtonEditor btnE1 = null;
    //表格数据
    String ImgPath="addFig.jpg";
    MyTable_Shop table=null;//老师
    JScrollPane scrollPane = null;
    JPanel tablePanel = new JPanel();
    MessagePasser passer = ClientMessagePasser.getInstance();
    String userID;
    String operation;

    Object[] columnNames={"商品编号","商品名称","上架时间","商品价格","剩余数量","查看","操作"};
    Object[][] tableData=null;
    List<Goods> res=null;

    public PanelMyStore_S(String ID)
    {
        this.setLayout(cardLayout);
        panelAll.setLayout(null);
        panelInform.setLayout(null);
        panelAdd.setLayout(null);

        userID = ID;
        int x=120,y=20;//起始坐标
        int btnWidth = 80, btnHeight = 30;

        lblHint.setBounds(x,y,300,40);
        lblHint.setFont(new Font("宋体", Font.BOLD, 30));

        btnAdd.setBounds(x+878,y+20,btnWidth*2,btnHeight);
        setButtonFont(btnAdd);
        btnOk.setBounds(500,500,btnWidth,btnHeight);
        setButtonFont(btnOk);
        btnCancel.setBounds(740,500,btnWidth,btnHeight);
        setButtonFont(btnCancel);
        btnReturn.setBounds(900,500,btnWidth,btnHeight);
        setButtonFont(btnReturn);

        btnEdit.setBounds(720,500,btnWidth,btnHeight);
        setButtonFont(btnEdit);

        tablePanel.setLayout(null);
        tablePanel.setBounds(10,80,1150,500);

        this.add(panelAll,"panelAll");
        this.add(panelInform,"panelInform");
        this.add(panelAdd,"panelAdd");

        setPanel1();

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operation="post";
                setPanel3(new Goods("","","","","","","",0,0),0);
                setCard("panelAdd");//传入空的商品
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //setPanel3(1);
                operation="put";
                setCard("panelAdd");//传入当前正在查看详情的商品
            }
        });

        btnReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCard("panelAll");
            }
        });

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Goods goods = new Goods();
                goods.setGoodsName(Inform.txtGoodsName.getText());
                goods.setGoodsID(Inform.txtGoodsID.getText());
                goods.setSeller(Inform.txtSeller.getText());
                goods.setDealDate(Inform.txtDealTime.getText());
                goods.setPrice(Inform.txtPrice.getText());
                goods.setNum(Integer.valueOf(Inform.txtNum.getText()));

                System.out.println(goods);
                try {
                    goods.setPicture(StringAndImage.ImageToString(ImgPath));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                Gson gson = new Gson();
                String s = gson.toJson(goods);
                passer.send(new Message("student", s, "shop", operation));

                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                Message msg = passer.receive();
                Map<String,Object> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String,Object>>(){}.getType());

                if(map.get("res").equals("OK"))
                {
                    if(operation.equals("post"))
                    {
                        informFrame("商品已上架！",false);
                        setPanel1();
                        setCard("panelAll");
                        panelAll.updateUI();
                        panelAll.repaint();
                    }
                    else
                    {
                        informFrame("修改成功！",false);
                        setPanel2(goods);
                        setCard("panelInform");
                    }

                }
                else
                {
                    if(operation.equals("post"))
                    { informFrame("商品上架失败！",true); }
                    else
                    { informFrame("修改失败！",true); }

                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCard("panelAll");
            }
        });
    }

    public void setCard(String card)
    {
        cardLayout.show(this,card);
    }

    public void setButtonFont(JButton button)
    {
        button.setFont(new Font("宋体",Font.BOLD, 18));
        button.setContentAreaFilled(false);
    }

    public void informFrame(String title,Boolean flag)
    {
        if(flag)
        { JOptionPane.showMessageDialog(this, title, "警告", JOptionPane.ERROR_MESSAGE);}
        else
        { JOptionPane.showMessageDialog(this, title, "提示", JOptionPane.INFORMATION_MESSAGE);}
    }

    public void setPanel1()//显示所有在售商品表格
    {
        panelAll.removeAll();
        User user = new User();
        user.setStudentID(userID);
        Gson gson = new Gson();
        String s = gson.toJson(user);
        passer.send(new Message("student", s, "shop", "getSell"));

        try {
            Thread.sleep(100);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        Message msg = passer.receive();
        Map<String, java.util.List<Goods>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Goods>>>() {}.getType());
        res = map.get("res");

        if(res.size()!=0) {
            tableData = new Object[res.size()][7];
            for (int i = 0; i < res.size(); i++) {
                tableData[i][0] = res.get(i).getGoodsID();
                tableData[i][1] = res.get(i).getGoodsName();
                tableData[i][2] = res.get(i).getDealDate();
                tableData[i][3] = res.get(i).getPrice();
                tableData[i][4] = res.get(i).getNum();
            }
        }
        else
        {
            tableData=null;
        }

        panelAll.add(lblHint);
        panelAll.add(btnAdd);

        setTable(1150,500);
        panelAll.add(tablePanel);
        panelAll.updateUI();
        panelAll.repaint();
    }

    public void setPanel2(Goods g)//显示商品详情，要传入一个商品
    {
        panelInform.removeAll();
        panelInform.add(btnReturn);
        panelInform.add(btnEdit);

        Inform = new PanelGoodsInform(g,false);//传入商品对象为参数
        Inform.setBounds(150,50,800,500);
        panelInform.add(Inform);

        panelInform.updateUI();
        panelInform.repaint();
    }

    public void setPanel3(Goods g,int flag)//显示添加商品界面||修改界面  传入商品对象，空的就是添加，有东西就是修改
    {
        panelAdd.removeAll();
        panelAdd.add(btnOk);
        panelAdd.add(btnCancel);

        Inform = new PanelGoodsInform(g,true);//传入对象，文本框可编辑并且一卡通设置成自己且不可编辑！！
        Inform.txtSeller.setText(userID);
        Inform.txtSeller.setEditable(false);
        Inform.setBounds(150,50,800,500);
        if(flag==0)
        {
            ImageIcon img = new ImageIcon("Pictures//addFig.jpg");// 这是背景图片 .png .jpg .gif 等格式的图片都可以
            img.setImage(img.getImage().getScaledInstance(180,220,Image.SCALE_DEFAULT));//这里设置图片大小，目前是20*20
            Inform.lblImg.setIcon(img);
            Inform.updateUI();
            Inform.repaint();
        }
        if(flag==1)
        {
            Inform.txtGoodsID.setText(g.getGoodsID());
            Inform.txtGoodsID.setEditable(false);
        }

        //panelInform.add(panelInform.lblImg);

        Inform.lblImg.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                if (e.getClickCount() == 1) {
                    addPicture(Inform.lblImg);
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        panelAdd.add(Inform);

        panelAdd.updateUI();
        panelAdd.repaint();
    }

    public void setTable(int width,int height)
    {
        tablePanel.removeAll();
        DefaultTableModel model= new DefaultTableModel(tableData,columnNames);
        table=new MyTable_Shop(model);
        table.setRowSelectionAllowed(true);
        // 行高
        table.setRowHeight(30);// 设置行高
        // 数据
        table.setFont(new Font("黑体",Font.PLAIN,18));//表格字体
        table.getTableHeader().setFont(new Font("黑体",Font.BOLD,20));//表头字体
        //添加渲染器
        ButtonRenderer btnR1=new ButtonRenderer("详情");
        table.getColumn("查看").setCellRenderer(btnR1);
        ButtonEditor btnE1 =new ButtonEditor(new JCheckBox());
        btnE1.getButton().setText("详情");
        table.getColumn("查看").setCellEditor(btnE1);

        ButtonRenderer btnR2=new ButtonRenderer("删除");
        table.getColumn("操作").setCellRenderer(btnR2);
        //添加编辑器
        ButtonEditor btnE2 =new ButtonEditor(new JCheckBox());
        btnE2.getButton().setText("删除");
        table.getColumn("操作").setCellEditor(btnE2);

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0,0,width,height);
        tablePanel.add(scrollPane);

        btnE1.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowName=btnE1.getThisRow();
                //传递对应商品到详情页
                setPanel2(res.get(rowName));
                setPanel3(res.get(rowName),1);//顺便构建修改页
                setCard("panelInform");

                btnE1.fireEditingStopped_1();
            }
        });

         btnE2.getButton().addActionListener(new ActionListener() {//删除对应商品
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowName = btnE2.getThisRow();

                Goods goods = new Goods();
                goods.setGoodsID((String)model.getValueAt(rowName, 0));
                Gson gson = new Gson();
                String s = gson.toJson(goods);
                passer.send(new Message("student", s, "shop", "delete"));

                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                Message msg = passer.receive();
                Map<String,Object> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String,Object>>(){}.getType());

                if(map.get("res").equals("OK"))
                {
                    informFrame("商品下架成功！", false);
                    btnE2.fireEditingStopped_1();
                    model.removeRow(rowName);//表格里删掉这一行
                } else {
                    informFrame("商品下架失败！", true);
                }
            }
        });

        tablePanel.updateUI();
        tablePanel.repaint();
    }

    public void addPicture(JLabel lbl) {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        /** 过滤文件类型 * */
        FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg","png","JPG","PNG","gif","GIF");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(lbl);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] arrFiles = chooser.getSelectedFiles();//得到选择的文件
            if (arrFiles == null || arrFiles.length == 0)//判断是否有文件为jpg或者png
            { return; }
            File  ff = chooser.getSelectedFile();//创建一个fileName得到选择文件的名字
            String fileName =ff.getName();
            String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
            if(!(prefix.equals("jpg") || prefix.equals("png")))//判断选择的文件是否是图片文件
            {
                JOptionPane.showMessageDialog(new JDialog(),":请选择.jpg 或 .png格式的图片");
                return;
            }
            FileInputStream input = null;
            FileOutputStream out = null;
            String path =  "./";//要上传到的路径
            try {
                for (File f : arrFiles) {
                    File dir = new File(path);//目标文件夹
                    File[] fs = dir.listFiles();
                    HashSet<String> set = new HashSet<String>();
                    for (File file : fs)
                    { set.add(file.getName()); }
                    String absolutePath = chooser.getSelectedFile().getAbsolutePath();//通过文件选择器对象拿到选择的文件.拿到该文件的绝对路径
                    ImgPath=absolutePath;
                    ImageIcon imageIcon = new ImageIcon(absolutePath);//创建一个ImageIcon对象 传入图片文件的绝对路径
                    imageIcon.setImage(imageIcon.getImage().getScaledInstance(180,220,Image.SCALE_DEFAULT));//这里设置图片大小，目前是20*20
                    lbl.setIcon(imageIcon);
                    input = new FileInputStream(f);
                    byte[] buffer = new byte[1024];
                    File des = new File(path, f.getName());
                    out = new FileOutputStream(des);
                    int len = 0;
                    while (-1 != (len = input.read(buffer)))
                    { out.write(buffer, 0, len); }
                    out.close();
                    input.close();
                }
                JOptionPane.showMessageDialog(null, "上传成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException e1) {
                JOptionPane.showMessageDialog(null, "上传失败！", "提示", JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "上传失败！", "提示", JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
            }
        }
    }
}

















