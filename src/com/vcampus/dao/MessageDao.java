package com.vcampus.dao;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
import com.vcampus.dao.utils.CRUD;
import com.vcampus.pojo.InnerMessage;
import com.vcampus.pojo.PublicMessage;
import org.junit.Test;

import java.util.List;

public class MessageDao extends BaseDao{
    //发送
    public static Boolean sendMessage(String studentID,String des,String content) throws Exception {
   //     try {
            String sql = "insert into tb_MESSAGE (studentID,content,sender,innerID) values ('" + des + "','" + content + "','" + studentID + "',0)";
            CRUD.update(sql,conn);
            return true;
//        }catch (Exception e){
//            System.out.println("wrong");
//            return false;
//        }
    }
    //获取消息
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
    //发送到公共聊天频道
    public static Boolean sendToPublic(String studentID,String message){
        try {
            String sql = "insert into tb_PUBLICMASSAGE (studentID,content) values ('"+studentID+"','"+message+"')";
            CRUD.update(sql,conn);
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }
    //查询所有公共消息
    public static List<PublicMessage> getPublicMessage() throws Exception {
        return searchAll(PublicMessage.class,"tb_PUBLICMESSAGE");
    }
}
