/** ===================================================
 * Title: PanelGoodsManage_A.java
 * Created: [2022-8-30  15:42:57] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 商品管理-管理员
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-30,创建此文件
 *2. 2022-8-30， 完善设置 修改人：韩宇
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

public class PanelGoodsManage_A extends JPanel {
    JPanel panelAll = new JPanel();
    JPanel panelInform = new JPanel();
    PanelGoodsInform Inform = new PanelGoodsInform(new Goods("","","","","","","",0,0),true);
    JPanel panelAdd = new JPanel();

    JButton btnAdd = new JButton("上架商品");
    JButton btnOk = new JButton("确定");
    JButton btnCancel = new JButton("取消");
    JButton btnReturn = new JButton("返回");
    JButton btnEdit = new JButton("修改");
    JButton btnDelete = new JButton("删除");

    JButton btnInquire = new JButton("查询");
    String[] listData = new String[]{"输入搜索条件","全部","商品名称", "商品编号", "商品价格"};
    JComboBox<String> comboBox = new JComboBox<String>(listData);
    JTextField txtEnquire = new JTextField();

    CardLayout cardLayout=new CardLayout();
    String ImgPath="Vcampus/img/addFig.jpg";

    MyTable_Shop table=null;//老师
    JScrollPane scrollPane = null;
    JPanel tablePanel = new JPanel();

    Goods goods = new Goods();
    String operation;
    MessagePasser passer = ClientMessagePasser.getInstance();

    Object[] columnNames={"商品编号","商品名称","上架时间","商品价格","剩余数量","查看","操作"};
    Object[][] tableData=new Object[][]{};//保存所有用户信息
    List<Goods>res=null;

    public PanelGoodsManage_A()
    {
        this.setLayout(cardLayout);
        panelAll.setLayout(null);
        panelInform.setLayout(null);
        panelAdd.setLayout(null);

        int x=120+10,y=20;//起始坐标
        int btnWidth = 80, btnHeight = 30;
        int txtWidth=110, txtHeight=30;

        btnAdd.setBounds(x+878,y+15,btnWidth*2,btnHeight+5);
        setButtonFont(btnAdd);
        btnOk.setBounds(500,500,btnWidth,btnHeight);
        setButtonFont(btnOk);
        btnCancel.setBounds(740,500,btnWidth,btnHeight);
        setButtonFont(btnCancel);
        btnReturn.setBounds(760,500,btnWidth,btnHeight);
        setButtonFont(btnReturn);
        btnEdit.setBounds(580,500,btnWidth,btnHeight);
        setButtonFont(btnEdit);
        btnDelete.setBounds(400,500,btnWidth,btnHeight);
        setButtonFont(btnDelete);

        comboBox.setBounds(x-110+290,y+15,220,35);
        comboBox.setFont(new Font("楷体", Font.BOLD, 20));
        comboBox.setOpaque(true);

        txtEnquire.setBounds(x+130+290,y+15,txtWidth*3,txtHeight+5);
        txtEnquire.setFont(new Font("楷体", Font.BOLD, 20));

        btnInquire.setBounds(x+480+290,y+15,btnWidth,btnHeight+5);
        setButtonFont(btnInquire);

        tablePanel.setLayout(null);
        tablePanel.setBounds(20,80,1150,500);

        this.add(panelAll,"panelAll");
        this.add(panelInform,"panelInform");
        this.add(panelAdd,"panelAdd");


        setPanel1(goods);//一打开就显示第一页

        btnInquire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int temp = comboBox.getSelectedIndex();
                switch(temp)//发出消息
                {
                    case 0:
                        informFrame("请选择搜索条件类型！",true);
                        break;
                    case 1:
                        goods = new Goods();
                        setPanel1(goods);
                        break;
                    case 2:
                        goods = new Goods();
                        goods.setGoodsName(txtEnquire.getText());
                        setPanel1(goods);
                        break;
                    case 3:
                        goods = new Goods();
                        goods.setGoodsID(txtEnquire.getText());
                        setPanel1(goods);
                        break;
                    case 4:
                        goods = new Goods();
                        goods.setPrice(txtEnquire.getText());
                        setPanel1(goods);
                        break;
                    default:
                        break;
                }
                //setPanel1();
                setCard("panelAll");
                repaint();
                updateUI();
            }
        });

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

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Goods goods = new Goods();
                goods.setGoodsID(Inform.txtGoodsID.getText());

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
                    //setPanel1();
                    setCard("panelAll");
                } else {
                    informFrame("商品下架失败！", true);
                }

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
                        Goods t = new Goods();
                        setPanel1(t);
                        setCard("panelAll");
                        //panelAll.updateUI();
                        //panelAll.repaint();
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

    public void setPanel1(Goods g)//显示所有在售商品表格
    {
        Gson gson = new Gson();
        String s = gson.toJson(g);
        passer.send(new Message("admin", s, "shop", "get"));

        panelAll.removeAll();
        Message msg = passer.receive();
        Map<String, List<Goods>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, List<Goods>>>() {}.getType());
        res = map.get("res");
        if(res.size()!=0)
        {
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
            informFrame("暂无商品上架",true);
            tableData=null;
        }

        panelAll.add(btnAdd);
        panelAll.add(btnInquire);
        panelAll.add(comboBox);
        panelAll.add(txtEnquire);

        setTable(1150,500);
        panelAll.add(tablePanel);
        panelAll.updateUI();
        panelAll.repaint();
    }

    public void setPanel2(Goods g)//显示商品详情，要传入一个商品
    {
        System.out.println(g);
        panelInform.removeAll();
        panelInform.add(btnReturn);
        panelInform.add(btnEdit);
        panelInform.add(btnDelete);

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
        Inform.setBounds(150,50,800,500);

        if(flag==0)
        {
            ImageIcon img = new ImageIcon("Vcampus/img/addFig.jpg");// 这是背景图片 .png .jpg .gif 等格式的图片都可以
            img.setImage(img.getImage().getScaledInstance(180,220,Image.SCALE_DEFAULT));//这里设置图片大小，目前是20*20
            Inform.lblImg.setIcon(img);
            Inform.updateUI();
            Inform.repaint();
        }
        if(flag==1)
        {
            Inform.txtGoodsID.setText(g.getGoodsID());
            Inform.txtGoodsID.setEditable(false);
            Inform.txtSeller.setText(g.getSeller());
            Inform.txtSeller.setEditable(false);
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

        System.out.println("构件表格");
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
