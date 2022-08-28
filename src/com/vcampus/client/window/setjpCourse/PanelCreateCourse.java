/** ===================================================
 * Title: TabbedCreatCourse.java
 * Created: [2022-8-26 17:06:11] by  韩宇
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 课程创建-管理员界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-26,创建此文件
 *2. 2022-8-27,完善设置
 *3.
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpCourse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Book;
import com.vcampus.pojo.Lesson;
import com.vcampus.pojo.Student;
import com.vcampus.pojo.Teacher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelCreateCourse extends JPanel{

    int x=300,y=120;//起始坐标
    int lblWidth=300,lblHeight=40,txtWidth=500, txtHeight=40;
    int heightDiffer=60;//上下两行高度差
    int llDiffer1=330,ltDiffer1=180;;//1-左起两列标签文本框间隔 2-第三列标签文本框间隔
    int btnWidth = 80, btnHeight = 30;

    JLabel lblHint1 = new JLabel("填写课程信息");
    JLabel lblHint2 = new JLabel("选择任课老师");
    JLabel lblHint3 = new JLabel("选择上课教室");
    JLabel lblName = new JLabel("课程名称");
    JTextField txtName = new JTextField();
    JLabel lblIdNum = new JLabel("课程编号");
    JTextField txtIdNum = new JTextField();
    JLabel lblDep = new JLabel("开课学院");
    JTextField txtDep = new JTextField();
    JLabel lblMajor = new JLabel("所属专业");
    JTextField txtMajor = new JTextField();
    JLabel lblTime = new JLabel("授课时间");
    JTextField txtTime = new JTextField();
    JLabel lblExam = new JLabel("是否安排考试");
    JRadioButton radioBtn01 = new JRadioButton("是");
    JRadioButton radioBtn02 = new JRadioButton("否");

    //课程ID、课程名称、学院、专业、是否有考试

    JPanel panelCourse = new JPanel();
    JPanel panelTeacher = new JPanel();
    JPanel panelRoom = new JPanel();

    JButton btnNext1 = new JButton("下一步");
    JButton btnNext2 = new JButton("下一步");
    JButton btnReturn1 = new JButton("返回");
    JButton btnReturn2 = new JButton("返回");
    JButton btnFinish = new JButton("完成创建");


    ButtonGroup btnGroup = new ButtonGroup();

    JLabel labels[] = {lblName,lblIdNum,lblDep,lblMajor,lblTime,lblExam};
    JTextField texts[] = {txtName,txtIdNum,txtDep,txtMajor,txtTime};

    CardLayout cardLayout=new CardLayout();
    ButtonEditor btnE = null;

    MyTable table1=null;//老师
    MyTable table2=null;//教室
    //DefaultTableModel tableModel1 =null;//表格模型
    //DefaultTableModel tableModel2 =null;//表格模型
    JScrollPane scrollPane1 = null;
    JScrollPane scrollPane2 = null;
    JPanel tablePanel1 = new JPanel();
    JPanel tablePanel2 = new JPanel();

    Object[] columnNames1={"教师姓名", "一卡通号","学院","专业","操作"};
    Object[] columnNames2={"教室","操作"};
    Object[][] tableData1=new Object[][]{};//保存所有用户信息

    Object[][] tableData2=new Object[][]{{"张三","添加"},{"张三","添加"},{"张三","添加"},{"张三","添加"},{"张三","添加"},
            {"张三","添加"},{"张三","添加"},{"张三","添加"},{"张三","添加"},
            {"张三","添加"},{"张三","添加"},{"张三","添加"},{"张三","添加"},
            {"张三","添加"},{"张三","添加"},{"张三","添加"},{"张三","添加"}};

    //Lesson lesson = new Lesson();
    MessagePasser passer = ClientMessagePasser.getInstance();

    public PanelCreateCourse()
    {
        this.setLayout(cardLayout);
        panelCourse.setLayout(null);
        panelTeacher.setLayout(null);
        panelRoom.setLayout(null);

        lblHint1.setBounds(220,40,lblWidth,lblHeight);
        lblHint1.setFont(new Font("宋体",Font.BOLD, 28));
        lblHint2.setBounds(220+llDiffer1,40,lblWidth,lblHeight);
        lblHint2.setFont(new Font("宋体",Font.BOLD, 28));
        lblHint3.setBounds(220+llDiffer1*2,40,lblWidth,lblHeight);
        lblHint3.setFont(new Font("宋体",Font.BOLD, 28));
        setButtonFont(btnNext1);
        btnNext1.setBounds(760,520,btnWidth*2,btnHeight);
        setButtonFont(btnNext2);
        btnNext2.setBounds(760,520,btnWidth*2,btnHeight);
        setButtonFont(btnFinish);
        btnFinish.setBounds(760,520,btnWidth*2,btnHeight);
        setButtonFont(btnReturn1);
        btnReturn1.setBounds(1000,520,btnWidth,btnHeight);
        setButtonFont(btnReturn2);
        btnReturn2.setBounds(1000,520,btnWidth,btnHeight);

        //位置
        radioBtn01.setBounds(500,430,80,25);
        radioBtn02.setBounds(600,430,80,25);
        radioBtn01.setFont(new Font("宋体",Font.BOLD, 18));
        radioBtn02.setFont(new Font("宋体",Font.BOLD, 18));

        btnGroup.add(radioBtn01);
        btnGroup.add(radioBtn02);

        tablePanel1.setLayout(null);
        tablePanel1.setBounds(10,100,1200,400);
        tablePanel2.setLayout(null);
        tablePanel2.setBounds(330,100,600,400);

        //设置其余坐标和字体
        for(int i=0;i<5;i++)
        {
            labels[i].setBounds(x,y+heightDiffer*i,lblWidth,lblHeight);
            texts[i].setBounds(x+ltDiffer1,y+heightDiffer*i,txtWidth,txtHeight);
            setLabelFont(labels[i],texts[i]);
            texts[i].setEditable(true);
            //p.add(labels[i]); p.add(texts[i]);
        }
        labels[5].setFont(new Font("楷体", Font.BOLD, 24));
        labels[5].setBounds(x,y+heightDiffer*5,lblWidth,lblHeight);

        setPanel1();

        btnNext1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str1=txtName.getText();//lesson.name=
                String str2=txtIdNum.getText();
                String str3=txtDep.getText();
                String str4=txtMajor.getText();
                String str5=txtTime.getText();

                Lesson lesson=new Lesson();
                lesson.setMajor(txtMajor.getText());
                lesson.setTime(txtTime.getText());
                System.out.println(lesson);

                Gson gson = new Gson();
                String s = gson.toJson(lesson);
                passer.send(new Message("admin", s, "lesson", "showteachertime"));

                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                Message msg = passer.receive();
                Map<String, java.util.List<Teacher>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<Teacher>>>(){}.getType());
                List<Teacher> res = map.get("res");
                System.out.println(res);
                //发消息，发送课程号和时间，返回老师的list
                //如果有，跳转第二页并构建表格
                //如果没有，提示
                if(res.size()!=0)
                {
                    tableData1 = new Object[res.size()][5]; //设置表格内容
                    for (int i = 0; i < res.size(); i++)
                    {
                        tableData1[i][0] = res.get(i).getTeacherName();
                        tableData1[i][1] = res.get(i).getTeacherID();
                        tableData1[i][2] = res.get(i).getSchool();
                        tableData1[i][3] = res.get(i).getAbledMajor();
                    }
                    System.out.println("找到老师");
                }
                else
                {
                    informFrame("未查询到授课教师！",true);
                }

                setPanel2();
                setCard("panelTeacher");
            }
        });

        btnNext2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //发消息，传上课时间
                //接收消息，所有空教室
                Lesson lesson=new Lesson();
                lesson.setTime(txtTime.getText());

                Gson gson = new Gson();
                String s = gson.toJson(lesson);
                passer.send(new Message("admin", s, "lesson", "showroom"));

                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                /////////??????传回来的是什么？
                Message msg = passer.receive();
                Map<String, java.util.List<String>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, java.util.List<String>>>(){}.getType());
                List<String> res = map.get("res");

                if(res.size()!=0)//设置表格内容
                {
                    tableData2 = new Object[res.size()][4];
                    for (int i = 0; i < res.size(); i++)
                    {
                        tableData2[i][0] = res.get(i);

                    }

                    System.out.println("空余教室");
                }
                else
                {
                    informFrame("未查询到空余教室！",true);
                }
                setPanel3();
                setCard("panelRoom");
            }
        });

        btnReturn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPanel1();
                setCard("panelCourse");
            }
        });

        btnReturn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPanel2();
                setCard("panelTeacher");
            }
        });

        btnFinish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str1=txtName.getText();
                String str2=txtIdNum.getText();
                String str3=txtDep.getText();
                String str4=txtMajor.getText();
                String str5=txtTime.getText();

                //打包回传所有信息，接收消息，如果成功
                if(true)
                {
                    informFrame("创建成功",false);
                    setPanel1();
                    setCard("panelCourse");
                }
                else
                {
                    informFrame("创建失败",true);
                }
            }
        });

        radioBtn01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(radioBtn01.isSelected())//如果单选按钮选中，登录按钮才可使用，否则登录按钮无效
                {
                    //有考试，设置lesson
                    System.out.println("有");
                }
            }
        });

        radioBtn02.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(radioBtn01.isSelected())//如果单选按钮选中，登录按钮才可使用，否则登录按钮无效
                {
                    //有考试，设置lesson
                    System.out.println("没有");
                }
            }
        });

       /* lbl.setVerticalTextPosition(JLabel.CENTER);
        lbl.setHorizontalTextPosition(JLabel.CENTER);
         */
        /*ImageIcon img1 = new ImageIcon("进度1.png");// 这是背景图片 .png .jpg .gif 等格式的图片都可以
        img1.setImage(img1.getImage().getScaledInstance(200,80,Image.SCALE_DEFAULT));//这里设置图片大小，目前是20*20
       // lblImg.setBorder(BorderFactory.createTitledBorder("分组框")); //设置面板边框，实现分组框的效果，此句代码为关键代码
        //lblImg.setBorder(BorderFactory.createLineBorder(Color.black));//设置面板边框颜色
        lblHint1.setIcon(img1);
        lblHint1.setBounds(150,60,200,80);
         */

        this.add(panelCourse,"panelCourse");
        this.add(panelTeacher,"panelTeacher");
        this.add(panelRoom,"panelRoom");

    }
    public void informFrame(String title,Boolean flag)
    {
        if(flag)
        { JOptionPane.showMessageDialog(this, title, "警告", JOptionPane.ERROR_MESSAGE);}
        else
        { JOptionPane.showMessageDialog(this, title, "提示", JOptionPane.INFORMATION_MESSAGE);}
    }

    public void setCard(String card)
    {
        cardLayout.show(this,card);
    }

    public void setPanel1()//传入课程对象
    {
        panelCourse.removeAll();
        panelCourse.add(lblHint1);
        panelCourse.add(radioBtn01);
        panelCourse.add(radioBtn02);

        for(int i=0;i<5;i++)
        {
            panelCourse.add(labels[i]); panelCourse.add(texts[i]);
        }
        panelCourse.add(lblExam);
        panelCourse.add(btnNext1);

       // txtName.setText("xxx");//姓名
        //txtIdNum.setText("213201445");//一卡通
        //txtDep.setText("计算机科学与工程学院");//学院
        //txtMajor.setText("计算机科学与技术专业");//专业
        //txtTime.setText("周一6-7");//时间（可能要转格式）

        panelCourse.updateUI();
        panelCourse.repaint();

    }

    public void setPanel2()
    {
        panelTeacher.removeAll();
        panelTeacher.add(lblHint1);panelTeacher.add(lblHint2);
        panelTeacher.add(btnNext2);
        panelTeacher.add(btnReturn1);

        setTable(columnNames1,tableData1,tablePanel1,table1,scrollPane1,1200,400,0);

        panelTeacher.add(tablePanel1);

        panelTeacher.updateUI();
        panelTeacher.repaint();

    }

    public void setPanel3()
    {
        panelRoom.removeAll();
        panelRoom.add(lblHint1);panelRoom.add(lblHint2);panelRoom.add(lblHint3);
        panelRoom.add(btnReturn2);
        panelRoom.add(btnFinish);

        setTable(columnNames2,tableData2,tablePanel2,table2,scrollPane2,600,400,1);
        panelRoom.add(tablePanel2);
        //tablePanel2.setBounds(0,0,1200,400);

        panelRoom.updateUI();
        panelRoom.repaint();

/*        table2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {

                int rowName=((JTable)e.getSource()).rowAtPoint(e.getPoint());
                int columnName=((JTable)e.getSource()).columnAtPoint(e.getPoint());
                int temp=tableModel2.getRowCount();
                if(columnName==tableModel2.getColumnCount()-1)
                {
                    String teacherID=(String)tableModel2.getValueAt(rowName, 1);
                    String courseID=txtIdNum.getText();
                    System.out.println("当前列"+rowName+"00"+temp);

                    //收发请求，删掉对应课程老师
                    //如果成功
                    if(true) {
                        informFrame("添加成功",false);
                        // tableModel.removeRow(rowName);//不能删最后一行
                        //setTable();//重新构建表格  拿回所有数据？

                    }else {
                        informFrame("添加失败",true);
                    }
                }
                btnE.fireEditingStopped_1();

            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}

        });
 */
    }

    public void setButtonFont(JButton button)
    {
        button.setFont(new Font("宋体",Font.BOLD, 18));
        button.setContentAreaFilled(false);
    }

    public void setLabelFont(JLabel label,JTextField text)
    {
        label.setFont(new Font("楷体", Font.BOLD, 24));
        text.setFont(new Font("楷体", Font.BOLD, 20));
    }

    public void setTable(Object[] title,Object[][] data,JPanel p,MyTable table, JScrollPane scrollPane,int width,int height,int flag)
    {

        System.out.println("构件表格");
        DefaultTableModel model= new DefaultTableModel(data,title);
        table=new MyTable(model);
        table.setRowSelectionAllowed(true);
        // 行高
        table.setRowHeight(30);// 设置行高
        // 数据
        table.setFont(new Font("黑体",Font.PLAIN,18));//表格字体
        table.getTableHeader().setFont(new Font("黑体",Font.BOLD,20));//表头字体

        //添加渲染器
        //table.getColumn("操作").setCellRenderer(new ButtonRenderer());
        //添加编辑器
        //table.getColumn("操作").setCellEditor( new ButtonEditor(new JCheckBox()));

        //添加渲染器
        ButtonRenderer btnR=new ButtonRenderer();
        table.getColumn("操作").setCellRenderer(btnR);
        //添加编辑器
        btnE =new ButtonEditor(new JCheckBox());
        table.getColumn("操作").setCellEditor(btnE);
        btnE.getButton().setText("添加");

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0,0,width,height);
        p.add(scrollPane);

/*
        //设置鼠标双击事件
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    int rowName=((JTable)e.getSource()).rowAtPoint(e.getPoint());
                    String teacherID=model.getValueAt(rowName,1).toString();
                    //连数据库

                    //发消息
                    //收消息

                    if(true) {
                        //table.setValueAt("已排课",rowName,data[0].length);
                        System.out.println("添加老师<" + teacherID + ">");
                    }else {
                        JOptionPane.showMessageDialog(null, "数据查询出错", "提示",
                                JOptionPane.ERROR_MESSAGE);
                        System.out.println("ERROR:添加老师<" + teacherID + ">：数据查询出错");
                    }
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
        */
        if(flag==0)
        {
            respond1(table,model);
        }
        else
        {
            respond2(table,model);

        }
        updateUI();
        repaint();
    }

    public void respond1(MyTable table,DefaultTableModel model)//添加老师，响应函数
    {
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {

                int rowName=((JTable)e.getSource()).rowAtPoint(e.getPoint());
                int columnName=((JTable)e.getSource()).columnAtPoint(e.getPoint());
                int temp=model.getRowCount();
                if(columnName==model.getColumnCount()-1)
                {
                    String teacherID=(String)model.getValueAt(rowName, 1);
                    String courseID=txtIdNum.getText();
                    System.out.println("当前列"+rowName+"00"+teacherID);

                    //收发请求，删掉对应课程老师
                    //如果成功
                    if(true) {
                        informFrame("添加成功",false);
                        // tableModel.removeRow(rowName);//不能删最后一行
                        //setTable();//重新构建表格  拿回所有数据？

                    }else {
                        informFrame("添加失败",true);
                    }
                }
                btnE.fireEditingStopped_1();

            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}

        });
    }

    public void respond2(MyTable table,DefaultTableModel model)//添加教室，响应函数
    {
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {

                int rowName=((JTable)e.getSource()).rowAtPoint(e.getPoint());
                int columnName=((JTable)e.getSource()).columnAtPoint(e.getPoint());

                if(columnName==model.getColumnCount()-1)
                {
                    String roomID=(String)model.getValueAt(rowName, 1);
                    //String courseID=txtIdNum.getText();
                    System.out.println("当前列"+rowName+"00"+roomID);

                    //收发请求，添加教室，创建完整课程
                    //如果成功
                    if(true) {
                        informFrame("添加成功",false);
                        // tableModel.removeRow(rowName);//不能删最后一行
                        //setTable();//重新构建表格  拿回所有数据？

                    }else {
                        informFrame("添加失败",true);
                    }
                }
                btnE.fireEditingStopped_1();

            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}

        });
    }

    /*
    public String[][] STRList;//保存所有用户信息
    //创建信息表格
    String[] columnNames = {"一卡通号", "密码","权限","操作"}; // 定义表格列名数组
    // 定义表格数据数组
    String[][] tableValues = new String[STRList.length][4];
        for(int i=0;i<STRList.length;i++){
        System.arraycopy(STRList[i], 0, tableValues[i], 0, 3);
        tableValues[i][3]=i+"";
    }
    // 创建指定列名和数据的表格
    JTable table = new MyTable(tableValues, columnNames);
    //表格样式设置
        table.setRowHeight(30);// 设置行高
        table.setFont(new Font("黑体",Font.PLAIN,18));//表格字体
        table.getTableHeader().setFont(new Font("黑体",Font.BOLD,20));//表头字体
        table.getColumnModel().getColumn(3).setCellRenderer(new MyButtonRender(jp,table));//添加按钮

    // 创建显示表格的滚动面板
    JScrollPane scrollPane = new JScrollPane(table);
        jp1.add(scrollPane);
        layout_Spring.putConstraint(layout_Spring.NORTH, scrollPane, 20, layout_Spring.SOUTH, lbl);
        layout_Spring.putConstraint(layout_Spring.WEST, scrollPane, 20, layout_Spring.WEST, jp1);
        layout_Spring.putConstraint(layout_Spring.SOUTH, scrollPane, -40, layout_Spring.SOUTH, jp1);
        layout_Spring.putConstraint(layout_Spring.EAST, scrollPane, -20, layout_Spring.EAST, jp1);

     */

}
