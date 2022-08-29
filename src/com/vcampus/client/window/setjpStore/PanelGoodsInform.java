package com.vcampus.client.window.setjpStore;

import com.sun.xml.internal.ws.server.ServerRtException;
import com.vcampus.dao.utils.StringAndImage;
import com.vcampus.pojo.Goods;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PanelGoodsInform extends JPanel {
    JLabel lblgoodsID = new JLabel("商品号");
     JTextField txtgoodsID = new JTextField();
    JLabel lblgoodsName = new JLabel("商品名");
    JTextField txtgoodsName = new JTextField();
    //JLabel lblseller = new JLabel("出售者");
    //JTextField txtseller = new JTextField();
    JLabel lblprice = new JLabel("价格");
    JTextField txtprice = new JTextField();
    JLabel lblImg = new JLabel("图片");
    JTextField txtImg = new JTextField();
    JLabel lblnum = new JLabel("数量");
    JTextField txtnum = new JTextField();
    JLabel lbltype = new JLabel("类型");
    JTextField txtType = new JTextField();
    JLabel [] labels = {lblgoodsName,lblprice,lblnum,lbltype};
    JTextField [] texts = {txtgoodsName,txtprice,txtnum,txtType};

    public PanelGoodsInform(Goods goods,Boolean flag){
     this.setLayout(null);
     //setPanel(book,false);
     int x=380,y=30;//起始坐标
     int lblWidth=300,lblHeight=40,txtWidth=500, txtHeight=40;
     int heightDiffer=60;//上下两行高度差
     int ltDiffer1=150;//1-左起两列标签文本框间隔 2-第三列标签文本框间隔
     removeAll();
     //设置照片
     //照片
     ImageIcon img = null;// 这是背景图片 .png .jpg .gif 等格式的图片都可以
     //System.out.println("book.getImage()="+goods.getPicture());
     if(goods.getPicture()==null)
     {
      System.out.println("没有图片");
      img = new ImageIcon("noFig.png");
      //img.setImage(img.getImage().getScaledInstance(180,220,Image.SCALE_DEFAULT));
     }
     else
     {
      try {
       Image Img = Toolkit.getDefaultToolkit().createImage(StringAndImage.StringToImage(goods.getPicture()));
       img = new ImageIcon(Img);
      } catch (IOException e) {
       e.printStackTrace();
      }
     }
     img.setImage(img.getImage().getScaledInstance(180,220,Image.SCALE_DEFAULT));//这里设置图片大小，目前是20*20
     lblImg.setIcon(img);
     lblImg.setBounds(150,60,180,220);
     lblImg.setBorder(BorderFactory.createTitledBorder("分组框")); //设置面板边框，实现分组框的效果，此句代码为关键代码
     lblImg.setBorder(BorderFactory.createLineBorder(Color.black));//设置面板边框颜色

    // txtBookID.setText(book.getBookID());//书籍号
     txtgoodsName.setText(goods.getGoodsName());
     txtprice.setText(goods.getPrice());
     txtnum.setText(String.valueOf(goods.getNum()));
     txtType.setText(String.valueOf(goods.getType()));//类型
     for(int i=0;i<4;i++)
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
 public void removeInform()
 {
  for(int i=0;i<4;i++)
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
