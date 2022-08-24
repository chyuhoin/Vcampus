package com.vcampus.client.window.setjpLibrary;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.client.window.setjpLibrary.mytablepanel.MyTablePanel;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Book;
import com.vcampus.pojo.User;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelMyBook extends JPanel {
    MessagePasser passer = ClientMessagePasser.getInstance();

    public PanelMyBook(String myID) {
        SpringLayout layS = new SpringLayout();
        this.setLayout(layS);

        //查询数据库
        User user = new User();
        user.setStudentID(myID);

        Gson gson = new Gson();
        String s = gson.toJson(user);
        passer.send(new Message("admin", s, "library", "getB"));

        Message msg = passer.receive();
        Map<String, List<Book>> map = new Gson().fromJson(msg.getData(), new TypeToken<HashMap<String, List<Book>>>() {
        }.getType());
        List<Book> res = map.get("res");

        Object[] columnNames = new Object[]{"书籍号", "书名", "作者", "类型", "剩余册数"};
        Object[][] rowData1 = new Object[res.size()][5];
        for (int i = 0; i < res.size(); i++) {
            rowData1[i][0] = res.get(i).getBookID();
            rowData1[i][1] = res.get(i).getBookName();
            rowData1[i][2] = res.get(i).getAuthor();
            rowData1[i][3] = res.get(i).getType();
            rowData1[i][4] = res.get(i).getLeftSize();

            System.out.println(rowData1[i][1]);
        }
        /*
        if (res.size() == 0)
            JOptionPane.showMessageDialog(
                    this,
                    "您还未有借阅记录哦 📖",
                    " ",
                    JOptionPane.INFORMATION_MESSAGE
            );

         */

        JPanel myPanel = new MyTablePanel(rowData1, columnNames);
        this.add(myPanel);
        layS.putConstraint(layS.NORTH,myPanel,10,layS.NORTH,this);
        layS.putConstraint(layS.WEST,myPanel,10,layS.WEST,this);
        layS.putConstraint(layS.EAST,myPanel,-10,layS.EAST,this);
        layS.putConstraint(layS.SOUTH,myPanel,-20,layS.SOUTH,this);
    }

}
