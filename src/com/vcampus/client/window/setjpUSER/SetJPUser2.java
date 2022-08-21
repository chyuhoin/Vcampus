/** ===================================================
 * Title: SetJPUser2.java
 * Created: [2022-8-21 15:14:42] by  张星喆
 *=====================================================
 * Copyright:  Copyright (c)　东南大学计算机学院, 2021-2022
 * =====================================================
 * Description: 用户管理-【管理员】用户信息管理界面
 *=====================================================
 *Revised Hisytory:
 *1. 2022-8-21,创建此文件
 *2. 对这个文件的修改历史进行详细描述，一般包括时间，修改者，
 *    修改的内容描述，修改的原因
 */

package com.vcampus.client.window.setjpUSER;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetJPUser2 {
    public String[][] STRList=new String[20][5];
    public String[] strList=new String[5];
    public JPanel jp1=new JPanel();//信息展示页面
    public JPanel jp2=new JPanel();//个人信息
    public JPanel jp3=new JPanel();//个人信息编辑
    public JPanel jp4=new JPanel();//注册
    public String ID;//身份
    public SetJPUser2(String id,JPanel jp, CardLayout layout_Card){
        SpringLayout layout_Spring = new SpringLayout();
        jp.add("jp1",jp1);
        jp.add("jp2",jp2);
        jp.add("jp3",jp3);
        jp.add("jp4",jp4);
        jp1.setLayout(layout_Spring);
        jp2.setLayout(layout_Spring);
        jp3.setLayout(layout_Spring);
        jp4.setLayout(layout_Spring);
        layout_Card.show(jp, "jp1");//先显示jp1

        ID=id;//身份
        setSTRList();
        setjp1(jp,layout_Spring,layout_Card);//加载jp1
    }
    /**
     * 设置文本框的内容
     */
    public void setSTRList(){
        for(int i=0;i<STRList.length;i++)
            for(int j=0;j<STRList[i].length;j++)
                STRList[i][j]="12345";
    }
    public void setstrList(){
        for(int i=0;i<strList.length;i++)
            strList[i]="11111";
    }
    public void setjp1(JPanel jp,SpringLayout layout_Spring,CardLayout layout_Card){
        //jp1内容
        //标题
        JLabel lbl = new JLabel("用户信息管理");
        jp1.add(lbl);
        lbl.setFont(new Font("黑体", Font.BOLD, 50));
        layout_Spring.putConstraint(layout_Spring.NORTH, lbl, 20, layout_Spring.NORTH, jp1);  //标签1北侧——>容器北侧
        layout_Spring.putConstraint(layout_Spring.WEST, lbl, 20, layout_Spring.WEST, jp1);    //标签1西侧——>容器西侧
        // 创建注册按钮
        JButton btn1=new JButton("添加用户");
        jp1.add(btn1);
        btn1.setFont(new Font("黑体", Font.BOLD, 20));
        layout_Spring.putConstraint(layout_Spring.NORTH, btn1, 20, layout_Spring.NORTH, jp1);
        layout_Spring.putConstraint(layout_Spring.EAST, btn1, -20, layout_Spring.EAST, jp1);
        //创建搜索按钮
        JButton btn2=new JButton("搜索");
        jp1.add(btn2);
        btn2.setFont(new Font("黑体", Font.BOLD, 20));
        layout_Spring.putConstraint(layout_Spring.NORTH, btn2, 20, layout_Spring.NORTH, jp1);
        layout_Spring.putConstraint(layout_Spring.EAST, btn2, -10, layout_Spring.WEST, btn1);
        //创建文本框
        JTextField text=new JTextField();
        jp1.add(text);
        text.setEditable(true);//可以编辑
        text.setFont(new Font("黑体", Font.PLAIN, 20));//文本框字体
        text.setPreferredSize(new Dimension (300,36));
        layout_Spring.putConstraint(layout_Spring.NORTH, text, 20, layout_Spring.NORTH, jp1);
        layout_Spring.putConstraint(layout_Spring.EAST, text, -10, layout_Spring.WEST, btn2);

        //创建信息表格
        String[] columnNames = {"一卡通号", "姓名","性别","联系电话","操作"}; // 定义表格列名数组
        // 定义表格数据数组
        String[][] tableValues = new String[STRList.length][5];
        for(int i=0;i<STRList.length;i++){
            for(int j=0;j<STRList[i].length-1;j++)
                tableValues[i][j] = STRList[i][j];
            tableValues[i][STRList[i].length-1]=i+"";
        }
        // 创建指定列名和数据的表格
        JTable table = new JTable(tableValues, columnNames);
        //表格样式设置
        table.setRowHeight(30);// 设置行高
        table.setFont(new Font("黑体",Font.PLAIN,18));//表格字体
        table.getTableHeader().setFont(new Font("黑体",Font.BOLD,20));//表头字体
        table.getTableHeader().setReorderingAllowed(false);//不允许拖动列头，以重新排序各列
        table.getTableHeader().setResizingAllowed(false);//不允许手动拖动来调整各列的大小

        table.getColumnModel().getColumn(4).setCellRenderer(new MyButtonRender(jp,table));//添加按钮
        // 创建显示表格的滚动面板
        JScrollPane scrollPane = new JScrollPane(table);
        jp1.add(scrollPane);
        layout_Spring.putConstraint(layout_Spring.NORTH, scrollPane, 20, layout_Spring.SOUTH, lbl);
        layout_Spring.putConstraint(layout_Spring.WEST, scrollPane, 20, layout_Spring.WEST, jp1);
        layout_Spring.putConstraint(layout_Spring.SOUTH, scrollPane, -40, layout_Spring.SOUTH, jp1);
        layout_Spring.putConstraint(layout_Spring.EAST, scrollPane, -20, layout_Spring.EAST, jp1);

        //监听事件
        //搜索按钮
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout_Card.next(jp);
                String info=text.getText();
                //信息查找
                setstrList();//设置srtList
                setjp2(jp,layout_Spring,layout_Card);//重新加载jp2
                System.out.println("用户管理系统-用户管理-个人信息搜索");
            }
        });
        //注册按钮
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout_Card.last(jp);//转入jp4
                setjp4(jp,layout_Spring,layout_Card);//加载setjp4
                System.out.println("用户管理系统-用户管理-注册");
            }
        });

    }
    public void setjp2(JPanel jp,SpringLayout layout_Spring,CardLayout layout_Card){
        //jp2内容
        //标题
        JLabel lbl2 = new JLabel("信息查询");
        jp2.add(lbl2);
        lbl2.setFont(new Font("黑体", Font.BOLD, 50));
        layout_Spring.putConstraint(layout_Spring.NORTH, lbl2, 20, layout_Spring.NORTH, jp2);  //标签1北侧——>容器北侧
        layout_Spring.putConstraint(layout_Spring.WEST, lbl2, 20, layout_Spring.WEST, jp2);    //标签1西侧——>容器西侧
        //按钮
        JButton btn1=new JButton("编辑");
        JButton btn2=new JButton("返回");
        jp2.add(btn1);jp2.add(btn2);
        btn1.setFont(new Font("黑体", Font.BOLD, 20));
        btn2.setFont(new Font("黑体", Font.BOLD, 20));
        layout_Spring.putConstraint(layout_Spring.NORTH, btn2, 20, layout_Spring.NORTH, jp2);//返回按钮
        layout_Spring.putConstraint(layout_Spring.EAST, btn2, -20, layout_Spring.EAST, jp2);
        layout_Spring.putConstraint(layout_Spring.NORTH, btn1, 20, layout_Spring.NORTH, jp2);//编辑按钮
        layout_Spring.putConstraint(layout_Spring.EAST, btn1, -30, layout_Spring.WEST, btn2);
        //信息列表：
        //标签
        JLabel[] lblList={
                new JLabel("姓名"),
                new JLabel("一卡通号"),
                new JLabel("性别"),
                new JLabel("年龄"),
                new JLabel("联系电话")
        };
        for(int i=0;i<lblList.length;i++){
            JLabel lbli=lblList[i];
            jp2.add(lbli);
            lbli.setFont(new Font("黑体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, lbli, 60+50*i, layout_Spring.SOUTH, lbl2);  //标签1北侧——>容器北侧
            layout_Spring.putConstraint(layout_Spring.EAST, lbli, 50, layout_Spring.EAST, lbl2);    //标签1西侧——>容器西侧
        }
        //文本框
        //JTextField[] textList=new JTextField[5];
        JTextField[] textList={
                new JTextField(),new JTextField(),
                new JTextField(),new JTextField(), new JTextField()};
        for(int i=0;i< textList.length;i++){
            textList[i].setText(strList[i]);
        }

        for(int i=0;i<textList.length;i++){
            JTextField texti=textList[i];
            jp2.add(texti);
            texti.setEditable(false);
            texti.setFont(new Font("黑体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, texti, 0, layout_Spring.NORTH, lblList[i]);
            layout_Spring.putConstraint(layout_Spring.WEST, texti, 20, layout_Spring.EAST, lblList[i]);
            texti.setColumns(60);
        }
        //textList[4].setEditable(true);

        //编辑按钮监听
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout_Card.next(jp);
                setjp3(jp,layout_Spring,layout_Card);
                System.out.println("用户管理系统-个人信息-修改编辑确认");
            }
        });
        //返回按钮监听
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout_Card.first(jp);//转到jp1
                setjp1(jp,layout_Spring,layout_Card);//重新加载jp1
                System.out.println("用户管理系统-个人信息-修改编辑取消");
            }
        });
    }
    public void setjp3(JPanel jp,SpringLayout layout_Spring,CardLayout layout_Card){
        //jp3内容 信息编辑，注销
        //标题
        JLabel lbl3 = new JLabel("信息查询");
        jp3.add(lbl3);
        lbl3.setFont(new Font("黑体", Font.BOLD, 50));
        layout_Spring.putConstraint(layout_Spring.NORTH, lbl3, 20, layout_Spring.NORTH, jp3);  //标签1北侧——>容器北侧
        layout_Spring.putConstraint(layout_Spring.WEST, lbl3, 20, layout_Spring.WEST, jp3);    //标签1西侧——>容器西侧
        //按钮
        JButton btn3=new JButton("确认");
        JButton btn4=new JButton("取消");
        JButton btn5=new JButton("注销该用户");
        jp3.add(btn3);jp3.add(btn4);jp3.add(btn5);
        btn3.setFont(new Font("黑体", Font.BOLD, 20));
        btn4.setFont(new Font("黑体", Font.BOLD, 20));
        btn5.setFont(new Font("黑体", Font.BOLD, 20));
        layout_Spring.putConstraint(layout_Spring.NORTH, btn4, 20, layout_Spring.NORTH, jp3);//取消按钮
        layout_Spring.putConstraint(layout_Spring.EAST, btn4, -20, layout_Spring.EAST, jp3);
        layout_Spring.putConstraint(layout_Spring.NORTH, btn3, 20, layout_Spring.NORTH, jp3);//确认按钮
        layout_Spring.putConstraint(layout_Spring.EAST, btn3, -30, layout_Spring.WEST, btn4);
        layout_Spring.putConstraint(layout_Spring.SOUTH, btn5, -40, layout_Spring.SOUTH, jp3);//注销
        layout_Spring.putConstraint(layout_Spring.EAST, btn5, -20, layout_Spring.WEST, jp3);

        //信息列表：
        //标签
        JLabel[] lblList={
                new JLabel("姓名"),
                new JLabel("一卡通号"),
                new JLabel("性别"),
                new JLabel("年龄"),
                new JLabel("联系电话")
        };
        for(int i=0;i<lblList.length;i++){
            JLabel lbli=lblList[i];
            jp3.add(lbli);
            lbli.setFont(new Font("黑体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, lbli, 60+50*i, layout_Spring.SOUTH, lbl3);  //标签1北侧——>容器北侧
            layout_Spring.putConstraint(layout_Spring.EAST, lbli, 50, layout_Spring.EAST, lbl3);    //标签1西侧——>容器西侧
        }
        //文本框
        JTextField[] textList={
                new JTextField(),new JTextField(),
                new JTextField(),new JTextField(), new JTextField()};
        for(int i=0;i< textList.length;i++){
            textList[i].setText(strList[i]);
        }

        for(int i=0;i<textList.length;i++){
            JTextField texti=textList[i];
            jp3.add(texti);
            texti.setEditable(true);
            texti.setFont(new Font("黑体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, texti, 0, layout_Spring.NORTH, lblList[i]);
            layout_Spring.putConstraint(layout_Spring.WEST, texti, 20, layout_Spring.EAST, lblList[i]);
            texti.setColumns(60);
            strList[i]=texti.getText();//获取文本框内信息
        }
        textList[1].setEditable(false);

        //确认按钮监听
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout_Card.previous(jp);
                //更新数据库信息-信息更新
                setstrList();//设置strList
                setjp2(jp,layout_Spring,layout_Card);//重新加载jp2
                System.out.println("用户管理系统-用户管理-修改编辑确认");
            }
        });
        //取消按钮监听
        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout_Card.previous(jp);
                System.out.println("用户管理系统-用户管理-修改编辑取消");
            }
        });
        //注销按钮监听
        btn5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        jp3,
                        "确认注销该用户？",
                        "提示",
                        JOptionPane.YES_NO_CANCEL_OPTION
                );
                System.out.println("选择结果: " + result);
                if(result==JOptionPane.YES_OPTION){
                    layout_Card.first(jp);
                    //更新数据库-信息注销
                    //判断是否注销成功，并弹出对话框。。。。。。。
                    System.out.println("用户管理系统-用户管理-注销用户");
                }
            }
        });
    }
    public void setjp4(JPanel jp,SpringLayout layout_Spring,CardLayout layout_Card){
        //jp4内容
        String[] newUser=new String[5];//新用户内容
        //标题
        JLabel lbl4 = new JLabel("注册新用户");
        jp4.add(lbl4);
        lbl4.setFont(new Font("黑体", Font.BOLD, 50));
        layout_Spring.putConstraint(layout_Spring.NORTH, lbl4, 20, layout_Spring.NORTH, jp4);  //标签1北侧——>容器北侧
        layout_Spring.putConstraint(layout_Spring.WEST, lbl4, 20, layout_Spring.WEST, jp4);    //标签1西侧——>容器西侧
        //语句
        JLabel lbltext = new JLabel("·请输入新用户的信息：");
        jp4.add(lbltext);
        lbltext.setFont(new Font("黑体", Font.BOLD, 30));
        layout_Spring.putConstraint(layout_Spring.NORTH, lbltext, 20, layout_Spring.SOUTH, lbl4);  //标签1北侧——>容器北侧
        layout_Spring.putConstraint(layout_Spring.WEST, lbltext, 60, layout_Spring.WEST, lbl4);
        //按钮
        JButton btn1=new JButton("确认");
        JButton btn2=new JButton("取消");
        jp4.add(btn1);jp4.add(btn2);
        btn1.setFont(new Font("黑体", Font.BOLD, 20));
        btn2.setFont(new Font("黑体", Font.BOLD, 20));
        layout_Spring.putConstraint(layout_Spring.NORTH, btn2, 20, layout_Spring.NORTH, jp4);//取消按钮
        layout_Spring.putConstraint(layout_Spring.EAST, btn2, -20, layout_Spring.EAST, jp4);
        layout_Spring.putConstraint(layout_Spring.NORTH, btn1, 20, layout_Spring.NORTH, jp4);//确认按钮
        layout_Spring.putConstraint(layout_Spring.EAST, btn1, -30, layout_Spring.WEST, btn2);
        //信息列表：
        //标签
        JLabel[] lblList={
                new JLabel("姓名"),
                new JLabel("一卡通号"),
                new JLabel("性别"),
                new JLabel("年龄"),
                new JLabel("联系电话")
        };
        for(int i=0;i<lblList.length;i++){
            JLabel lbli=lblList[i];
            jp4.add(lbli);
            lbli.setFont(new Font("黑体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, lbli, 60+50*i, layout_Spring.SOUTH, lbltext);  //标签1北侧——>容器北侧
            layout_Spring.putConstraint(layout_Spring.EAST, lbli, 50, layout_Spring.EAST, lbl4);
        }
        //文本框
        //JTextField[] textList=new JTextField[5];
        JTextField[] textList={
                new JTextField(),new JTextField(),
                new JTextField(),new JTextField(),
                new JTextField()
        };
        for(int i=0;i<textList.length;i++){
            JTextField texti=textList[i];
            jp4.add(texti);
            texti.setEditable(true);
            texti.setFont(new Font("黑体", Font.BOLD, 20));
            layout_Spring.putConstraint(layout_Spring.NORTH, texti, 0, layout_Spring.NORTH, lblList[i]);
            layout_Spring.putConstraint(layout_Spring.WEST, texti, 20, layout_Spring.EAST, lblList[i]);
            texti.setColumns(60);
            newUser[i]=texti.getText();//获取文本框内容
        }

        //确认按钮监听
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        jp4,
                        "确认注册该用户？",
                        "提示",
                        JOptionPane.YES_NO_OPTION
                );
                System.out.println("选择结果: " + result);
                if(result==JOptionPane.YES_OPTION){
                    layout_Card.first(jp);
                    //更新数据库-信息添加 newUser[5]
                    //判断是否注册成功，并弹出对话框。。。。。。。
                    System.out.println("用户管理系统-用户管理-注册确认");
                }
            }
        });
        //取消按钮监听
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout_Card.first(jp);//返回jp1
                setjp1(jp,layout_Spring,layout_Card);//重新加载jp1
                System.out.println("用户管理系统-用户管理-注册取消");
            }
        });
    }

    public class MyButtonRender implements TableCellRenderer {
        public JPanel jp;
        public JTable table;
        private JPanel jPanel;
        private JButton btn1,btn2;

        public MyButtonRender(JPanel tempjp,JTable tempTbl) {
            jp=tempjp;
            table=tempTbl;
            initJPanel();
            initButton();
            jPanel.add(btn1);
            jPanel.add(btn2);
        }

        private void initButton() {
            SpringLayout layout_Spring = new SpringLayout();
            CardLayout layout_Card=new CardLayout();
            btn1 = new JButton();
            btn2 = new JButton();
            btn1.setBounds(10, 1, 100, 30-4);
            btn2.setBounds(20+100, 1, 100, 30-4);
            btn1.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            layout_Card.show(jp,"jp2");
                            //获取选中的行
                            int index1 = table.getSelectedRow();//获取选中的行
                            String value= (String) table.getModel().getValueAt(index1, 0);//一卡通号
                            //数据库数据获取
                            //重新加载jp2
                            setjp2(jp,layout_Spring,layout_Card);
                            System.out.println("" + e.getActionCommand());
                            System.out.println(btn1.getText());
                        }
                    });
            btn2.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            layout_Card.show(jp,"jp3");
                            //获取选中的行
                            int index1 = table.getSelectedRow();//获取选中的行
                            String value= (String) table.getModel().getValueAt(index1, 0);//一卡通号
                            //数据库数据获取
                            //重新加载jp3
                            setjp3(jp,layout_Spring,layout_Card);
                            System.out.println("" + e.getActionCommand());
                            System.out.println(btn2.getText());
                        }
                    });
        }

        private void initJPanel() {
            jPanel = new JPanel();
            jPanel.setLayout(null);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            btn1.setText("编辑");
            btn2.setText("查看");
            return jPanel;
        }
    }
}
