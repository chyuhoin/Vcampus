package com.vcampus.client.window.setjpStore;

import com.google.gson.Gson;
import com.vcampus.net.ClientMessagePasser;
import com.vcampus.net.Message;
import com.vcampus.net.MessagePasser;
import com.vcampus.pojo.Record;

public class PanelBuyRecordInform {

    String [] columNames = {"商品名称","售卖者","购买日期","价格","交易状态"};
    MessagePasser passer = ClientMessagePasser.getInstance();
    public PanelBuyRecordInform(String studentID){
        Record record = new Record();
        record.setStudentID(studentID);
        Gson gson = new Gson();
        String s = gson.toJson(record);
        passer.send(new Message("0", s, "library", "get"));
    }
}
