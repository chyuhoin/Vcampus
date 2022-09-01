package com.vcampus.dao;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
import com.vcampus.dao.utils.CRUD;
import com.vcampus.pojo.InnerMessage;
import com.vcampus.pojo.PublicMessage;
import org.junit.Test;

import java.util.List;

/**
 * 站内消息有关的Dao层
 *
 * @author 刘骐
 * @date 2022/09/01
 */
public class MessageDao extends BaseDao{
    /**
     * 发私信
     *
     * @param studentID 学生一卡通号
     * @param des       目标
     * @param content   内容
     * @return {@link Boolean}
     * @throws Exception 异常
     *///发送
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

    /**
     * 读私信
     *
     * @param studentID 学生一卡通号
     * @return {@link List}<{@link InnerMessage}>
     * @throws Exception 异常
     *///获取消息
    public static List<InnerMessage> readMessage(String studentID) throws Exception {
        return searchBy(InnerMessage.class,"tb_MESSAGE","studentID",studentID);
    }

    /**
     * 删除消息
     *
     * @param id 消息id
     * @return {@link Boolean}
     *///删除一条消息
    public static Boolean deleteMessage(Integer id){
        try {
            delete("innerID",id,"tb_MESSAGE");
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }

    /**
     * 发送到公共频道
     *
     * @param studentID 学生一卡通号
     * @param message   消息
     * @return {@link Boolean}
     */
    public static Boolean sendToPublic(String studentID,String message){
        try {
            String sql = "insert into tb_PUBLICMESSAGE (studentID,content) values ('"+studentID+"','"+message+"')";
            CRUD.update(sql,conn);
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }

    /**
     * 查看公共频道消息
     *
     * @return {@link List}<{@link PublicMessage}>
     * @throws Exception 异常
     */
    public static List<PublicMessage> getPublicMessage() throws Exception {
        return searchAll(PublicMessage.class,"tb_PUBLICMESSAGE");
    }
}
