/** ===================================================
 * Title: PanelBookManage.java
 * Created: [2022-8-22  16:10:03] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 图书管理界面-管理员
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-22,创建此文件
 *2. 2022-8-23，完善设置 修改人：韩宇
 *3.
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpLibrary;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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

import com.vcampus.dao.utils.StringAndImage;
import com.vcampus.pojo.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;

public class PanelBookManage extends JPanel {

    JButton btnAdd = new JButton("添加");
    JButton btnDelete = new JButton("删除");
    JButton btnChange = new JButton("修改");
    JButton btnInquire1 = new JButton("删除书籍");//查询后删除
    JButton btnInquire2 = new JButton("查询");//查询后修改
    JButton btnOk = new JButton("确认");
    JButton btnCancel = new JButton("取消");

    JTextField txtEnquire = new JTextField();
    JLabel lblHint = new JLabel("输入书籍号 ：");
    int btnWidth = 120, btnHeight = 30;
    int txtWidth=110, txtHeight=32;

    Book book = new Book();//承接结果
    String ImgPath="addFig.jpg";

    String operation = "";
    //PanelBookInform panelInform = new PanelBookInform();//传入书本的对象作为参数   最初传个空的
    PanelBookInform panelInform = new PanelBookInform(new Book("","","","",0,""),true);
    MessagePasser passer = ClientMessagePasser.getInstance();
    public PanelBookManage()
    {
        this.setLayout(null);
        int x=60,y=50,differ=160;
        //btnAdd.setFont(new Font("宋体",Font.BOLD, 20));
        setButtonFont(btnAdd);
        btnAdd.setBounds(x,y,btnWidth,btnHeight);
        setButtonFont(btnDelete);
        btnDelete.setBounds(x+differ,y,btnWidth,btnHeight);
        setButtonFont(btnChange);
        btnChange.setBounds(x+differ*2,y,btnWidth,btnHeight);
        setButtonFont(btnInquire1);
        btnInquire1.setBounds(970,y,btnWidth*2-60,btnHeight);
        setButtonFont(btnInquire2);
        btnInquire2.setBounds(970,y,btnWidth,btnHeight);
        setButtonFont(btnOk);
        btnOk.setBounds(420,500,btnWidth,btnHeight);
        setButtonFont(btnCancel);
        btnCancel.setBounds(660,500,btnWidth,btnHeight);

        lblHint.setFont(new Font("宋体", Font.BOLD, 24));
        lblHint.setBounds(560,y-5,200,40);
        txtEnquire.setBounds(730,y,txtWidth*2,txtHeight);
        txtEnquire.setFont(new Font("楷体", Font.BOLD, 20));

        panelInform.setBounds(0,150,1400,350);

        add(btnAdd);
        add(btnDelete);
        add(btnChange);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(panelInform);
                remove(lblHint); remove(txtEnquire);
                remove(btnInquire1); remove(btnInquire2);
                remove(btnOk); remove(btnCancel);
                repaint();
                updateUI();
                repaint();
                operation="post";
                addBook();//空的，可查询

            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(panelInform); remove(btnInquire2);
                remove(btnOk); remove(btnCancel);
                repaint();
                updateUI();
                repaint();
                setEnquire(btnInquire1);//查询


            }
        });

        btnChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(panelInform); remove(btnInquire1);
                remove(btnOk); remove(btnCancel);
                repaint();
                //updateUI();
                //repaint();
                setEnquire(btnInquire2);//查询
                operation="put";
            }
        });
        //删除
        btnInquire1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = txtEnquire.getText();
                System.out.println(str);
                Book b = new Book();
                b.setBookID(str);
                Gson gson = new Gson();
                String s = gson.toJson(b);
                passer.send(new Message("admin", s, "library", "delete"));
                //传消息出去  打包一本书
                //传给详情界面
                deleteBook();
            }
        });
        //修改信息
        btnInquire2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = txtEnquire.getText();
                System.out.println(str);
                //传消息出去  打包一本书//接收结果//传给详情界面

                Book b = new Book();
                b.setBookID(str);
                Gson gson = new Gson();
                String s = gson.toJson(b);
                passer.send(new Message("admin", s, "library", "get"));

                changeBookInform();
            }
        });

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String str1 = panelInform.txtBookID.getText();
                //String str2 = panelInform.txtBookName.getText();
                //String str3 = panelInform.txtAuthor.getText();
                //String str4 = panelInform.txtType.getText();
                //String str5 = panelInform.txtLeftSize.getText();

                //以上直接book。。。。赋值，然后把book传回去
                //传消息，打包book  差一个书的图片路径！！！
                book.setBookID(panelInform.txtBookID.getText());
                book.setBookName(panelInform.txtBookName.getText());
                book.setAuthor(panelInform.txtAuthor.getText());
                book.setType(panelInform.txtType.getText());
                book.setLeftSize(Integer.valueOf(panelInform.txtLeftSize.getText()));
                try {
                    book.setImage(StringAndImage.ImageToString(ImgPath));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                Gson gson = new Gson();
                String s = gson.toJson(book);
                passer.send(new Message("admin", s, "library",operation));
                //System.out.println(str1+'\n'+str2+'\n'+str3+'\n'+str4+'\n'+str5);
                //接收消息
                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                Message msg = passer.receive();
                System.out.println(msg);
                Map<String,Object> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String,Object>>(){}.getType());

                if(map.get("res").equals("OK"))
                {
                    informFrame("操作成功",false);
                    //panelInform=new PanelBookInform(book,true);
                }
                else
                { informFrame("操作失败",true); }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPanel();
            }
        });
    }

    public void setButtonFont(JButton button)
    {
        button.setFont(new Font("宋体",Font.BOLD, 22));
        button.setContentAreaFilled(false);
    }

    public void addBook()//添加书本，界面
    {
        updateUI();
        repaint();
        add(btnOk);  add(btnCancel);
        ////////???/不确定
        //ImageIcon img2 = new ImageIcon("src/fig/addFig.jpg");// 这是背景图片 .png .jpg .gif 等格式的图片都可以
       // book = new Book("","","","",0,"src/com.vcampus/client/window/Pictures/addFig.jpg");
        //remove(panelInform);
        //panelInform = new PanelBookInform(new Book("","","","",0,""),true);
        //add(panelInform);
        //panelInform.setBounds(0,120,1400,350);

        book=new Book("","","","",0,"");
        setPanel();
        ImageIcon img2 = new ImageIcon("addFig.jpg");// 这是背景图片 .png .jpg .gif 等格式的图片都可以
        img2.setImage(img2.getImage().getScaledInstance(180,220,Image.SCALE_DEFAULT));//这里设置图片大小，目前是20*20
        panelInform.lblImg.setIcon(img2);
        //panelInform.add(panelInform.lblImg);


        panelInform.lblImg.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                if (e.getClickCount() == 1) {
                    addPicture(panelInform.lblImg);
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


        //img2.setImage(img2.getImage().getScaledInstance(180,220,Image.SCALE_DEFAULT));//这里设置图片大小，目前是20*20
        //panelInform.lblImg.setIcon(img2);
        //setPanel();//测试
    }

    public void deleteBook()//删除书本，显示成功失败即可
    {
        //remove(panelInform);
        txtEnquire.setText("");
        Message msg = passer.receive();
        System.out.println(msg);
        Map<String,Object> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String,Object>>(){}.getType());

        if(map.get("res").equals("OK"))
        { informFrame("删除成功!",false); }
        else
        { informFrame("删除失败！",true); }
    }

    public void changeBookInform()//修改图书信息
    {
        updateUI();
        repaint();
        add(btnOk); add(btnCancel);
        //remove(panelInform);

        panelInform.lblImg.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                if (e.getClickCount() == 1) {
                    addPicture(panelInform.lblImg);
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

        Message msg = passer.receive();
        Map<String, java.util.List<Book>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Book>>>(){}.getType());
        List<Book> res = map.get("res");
        //先接收消息//根据消息判断，如果大小为1，放详情panel//不为1，放表格//zxz//如果是空的，弹警告窗口

        txtEnquire.setText("");
        if(res.size()!=0)//接收消息，如果为空，警告，否则显示可编辑详情页面
        {
            book=res.get(0);
            //remove(panelInform);
            setPanel();
            panelInform.lblImg.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub
                    if (e.getClickCount() == 1) {
                        addPicture(panelInform.lblImg);
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


        }
        else
        { informFrame("未查询到相关书籍！",true); }
    }

    public void setEnquire(JButton btnInquire)//查询提示 文本框 按钮
    {
        add(txtEnquire);
        add(lblHint);
        add(btnInquire);
    }

    public void setPanel()
    {
        updateUI();
        repaint();
        //先接收消息
        //根据消息判断，如果大小为1，放详情panel
        //不为1，放表格//zxz
        //如果是空的，弹警告窗口
        panelInform = new PanelBookInform(book,true);
        panelInform.setBounds(0,120,1400,350);
        add(panelInform);
    }

    public void informFrame(String title,Boolean flag)
    {
        if(flag)
        { JOptionPane.showMessageDialog(this, title, "警告", JOptionPane.ERROR_MESSAGE); }
        else
        { JOptionPane.showMessageDialog(this, title, "提示", JOptionPane.INFORMATION_MESSAGE); }
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
