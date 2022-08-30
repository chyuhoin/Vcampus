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

import com.vcampus.pojo.Goods;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.HashSet;

public class PanelGoodsManage_A extends JPanel {
    //JLabel lblHint = new JLabel("在售商品");
    JPanel panelAll = new JPanel();
    PanelGoodsInform panelInform = new PanelGoodsInform(new Goods("","","","","","","",0,0),true);
    JPanel panelAdd = new JPanel();

    JButton btnAdd = new JButton("上架商品");
    JButton btnOk = new JButton("确定");
    JButton btnCancel = new JButton("取消");
    JButton btnReturn = new JButton("返回");
    JButton btnEdit = new JButton("修改");
    JButton btnDelete = new JButton("删除");

    JButton btnInquire = new JButton("查询");
    String[] listData = new String[]{"输入搜索条件","全部","商品名称", "商品编号", "商品价格"};
    // 创建一个下拉列表框
    JComboBox<String> comboBox = new JComboBox<String>(listData);
    JTextField txtEnquire = new JTextField();


    CardLayout cardLayout=new CardLayout();
    //ButtonEditor btnE1 = null;
    //表格数据
    String ImgPath="addFig.jpg";

    //MessagePasser passer = ClientMessagePasser.getInstance();
    //MyTablePanel_Shop tableP=new MyTablePanel_Shop(tableData,columnNames);
    MyTable_Shop table=null;//老师
    JScrollPane scrollPane = null;
    JPanel tablePanel = new JPanel();
    PanelGoodsInform panelInfo1=null;
    PanelGoodsInform panelInfo2=null;

    //List<goods>承接所以有回传的结果？？？
    //可能需要一个商品对象来回改
    Object[] columnNames={"商品名称", "商品编号","上架时间","商品价格","剩余数量","查看","操作"};
    Object[][] tableData=new Object[][]{{"张三","2233","计算机","计算机","添加"},
            {"张三","2233","计算机","计算机","添加"},
            {"张三","2233","计算机","计算机","添加"},
            {"张三","2233","计算机","计算机","添加"},
            {"张三","2233","计算机","计算机","添加"},
            {"张三","2233","计算机","计算机","添加"},
            {"张三","2233","计算机","计算机","添加"},
            {"张三","2233","计算机","计算机","添加"},
            {"张三","2233","计算机","计算机","添加"},
            {"张三","2233","计算机","计算机","添加"},
            {"张三","2233","计算机","计算机","添加"},
            {"张三","2233","计算机","计算机","添加"},
            {"张三","2233","计算机","计算机","添加"},
            {"张三","2233","计算机","计算机","添加"},
            {"张三","2233","计算机","计算机","添加"}};//保存所有用户信息

    public PanelGoodsManage_A()
    {
        this.setLayout(cardLayout);
        panelAll.setLayout(null);
        panelInform.setLayout(null);
        panelAdd.setLayout(null);

        int x=120+10,y=20;//起始坐标
        int btnWidth = 80, btnHeight = 30;
        int txtWidth=110, txtHeight=30;

        //lblHint.setBounds(x,y,300,40);
        //lblHint.setFont(new Font("宋体", Font.BOLD, 30));

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

        //发消息，获取全部商品放入表格
        setPanel1();//一打开就显示第一页

        btnInquire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = txtEnquire.getText();
                System.out.println(str);//传送，接收结果bool型

                int temp = comboBox.getSelectedIndex();
                switch(temp)//发出消息
                {
                    case 0:
                        informFrame("请选择搜索条件类型！",true);
                        break;
                    case 1:
                        //打包空商品，查所有
                        break;
                    case 2:
                        System.out.println("名称"+str);
                        break;
                    case 3:
                        System.out.println("编号"+str);//book。set
                        break;
                    case 4:
                        System.out.println("价格"+str);//book。set
                        break;
                    default:
                        break;
                }
                //传消息出去
                setPanel1();
                setCard("panelAll");

            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPanel3(0);
                setCard("panelAdd");//传入空的商品
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPanel3(1);
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
                String str = panelInfo1.txtGoodsID.getText();
                //打包传消息，接收消息，删除商品
                //如果成功
                if (true)//操作成功
                {
                    informFrame("商品下架成功！", false);
                    setPanel1();
                    setCard("panelAll");
                } else {
                    informFrame("商品下架失败！", true);
                }

            }
        });

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str1 = panelInform.txtGoodsName.getText();
                String str2 = panelInform.txtGoodsID.getText();
                String str3 = panelInform.txtSeller.getText();//////直接设查询者的一卡通，不允许输入，不可编辑
                String str5 = panelInform.txtDealTime.getText();
                String str6 = panelInform.txtPrice.getText();
                String str7 = panelInform.txtNum.getText();
                //差图片设置

                //获取添加页所有信息，打包整个商品回传
                // 传消息，收消息，成功后返回表格页，表格页更新
                //失败留在这页
                if(true)
                {
                    informFrame("商品已上架！",false);
                    setPanel1();
                    setCard("panelAll");
                }
                else
                {
                    informFrame("商品上架失败！",true);
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
        //用个人一卡通号，getSeller
        //发消息，收消息，不为空
        if(true)
        {
            //tableData=[][7];
            for(int i=0;i<6;i++)
            {
                //设置表格内容
            }
        }
        else
        {
            tableData=null;
        }
        panelAll.removeAll();
       // panelAll.add(lblHint);
        panelAll.add(btnAdd);
        panelAll.add(btnInquire);
        panelAll.add(comboBox);
        panelAll.add(txtEnquire);

        setTable(1150,500);
        panelAll.add(tablePanel);
        panelAll.updateUI();
        panelAll.repaint();
    }

    public void setPanel2()//显示商品详情，要传入一个商品
    {
        panelInform.removeAll();
        panelInform.add(btnReturn);
        panelInform.add(btnEdit);
        panelInform.add(btnDelete);

        //panelInfo1 = new PanelGoodsInform();//传入商品对象为参数
        panelInfo1.setBounds(150,50,800,500);
        panelInform.add(panelInfo1);

        panelInform.updateUI();
        panelInform.repaint();
    }

    public void setPanel3(int flag)//显示添加商品界面||修改界面  传入商品对象，空的就是添加，有东西就是修改
    {
        panelAdd.removeAll();
        panelAdd.add(btnOk);
        panelAdd.add(btnCancel);

        //panelInfo2 = new PanelGoodsInform();//传入对象，文本框可编辑并且一卡通设置成自己且不可编辑！！
        panelInfo2.setBounds(150,50,800,500);
        if(flag==0)
        {
            ImageIcon img = new ImageIcon("Pictures//addFig.jpg");// 这是背景图片 .png .jpg .gif 等格式的图片都可以
            img.setImage(img.getImage().getScaledInstance(180,220,Image.SCALE_DEFAULT));//这里设置图片大小，目前是20*20
            panelInfo2.lblImg.setIcon(img);
            panelInfo2.updateUI();
            panelInfo2.repaint();
        }

        //panelInform.add(panelInform.lblImg);

        panelInfo2.lblImg.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                if (e.getClickCount() == 1) {
                    addPicture(panelInfo2.lblImg);
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
        panelAdd.add(panelInfo2);

        panelAdd.updateUI();
        panelAdd.repaint();
    }

    public void setTable(int width,int height)
    {
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
                setPanel2();//传入”res[rowName],构建详情表格
                System.out.println(rowName);
                setCard("panelInform");


                btnE1.fireEditingStopped_1();
            }
        });

        btnE2.getButton().addActionListener(new ActionListener() {//删除对应商品
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowName = btnE2.getThisRow();
                //获取商品号，打包商品，delete
                //发送消息，接收消息
                if (true)//操作成功
                {
                    informFrame("商品下架成功！", false);
                    model.removeRow(rowName);//表格里删掉这一行
                } else {
                    informFrame("商品下架失败！", true);
                }
            }
        });

        updateUI();
        repaint();
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
