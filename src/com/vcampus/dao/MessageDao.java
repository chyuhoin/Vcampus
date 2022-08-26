package com.vcampus.dao;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
import com.vcampus.dao.utils.CRUD;
import com.vcampus.pojo.InnerMessage;
import org.junit.Test;

import java.util.List;

public class MessageDao extends BaseDao{
    //发送
    public static Boolean sendMessage(String studentID,String des,String content) throws Exception {
        try {
            String sql = "insert into tb_MESSAGE (studentID,content,sender,innerID) values ('" + des + "','" + content + "','" + studentID + "',0)";
            CRUD.update(sql,conn);
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }
    //读消息
    public static List<InnerMessage> readMessage(String studentID) throws Exception {
        return searchBy(InnerMessage.class,"tb_MESSAGE","studentID",studentID);
    }
    //删除一条消息
    public static Boolean deleteMessage(Integer id){
        try {
            delete("innerID",id,"tb_MESSAGE");
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }
    @Test
    public void test() throws Exception {
        sendMessage("213202132","123456","test");
    }
}
