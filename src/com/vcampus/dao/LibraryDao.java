package com.vcampus.dao;

import com.vcampus.dao.utils.CRUD;
import com.vcampus.dao.utils.databaseConn;
import com.vcampus.pojo.Book;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.vcampus.dao.utils.mapToBean;
import org.junit.Test;

/**
 * 和图书馆相关操作
 *
 * @author 刘骐
 * @date 2022/09/01
 */
public class LibraryDao extends BaseDao{
    /**
     * 搜索所有图书
     *
     * @return {@link List}<{@link Book}>
     * @throws Exception 异常
     */
    public static List<Book> search() throws Exception {
        return searchAll(Book.class,"tb_BOOK");
    }

    /**
     * 按条件查询图书
     *
     * @param str 查询条件
     * @return {@link List}<{@link Book}>
     * @throws Exception 异常
     *///重载的查询函数，按条件查询,str的顺序为 字段+值，两个一组重复,
    public static List<Book> search(String...str) throws Exception {
        String sql = "select * from tb_BOOK  where {0} = {1} and {2}={3} and {4}={5}" ;
        for(int i=0;i<str.length;i+=2){
           sql =  sql.replace("{"+String.valueOf(i)+"}",str[i]);
           sql =  sql.replace("{"+String.valueOf(i+1)+"}","'"+str[i+1]+"'");
        }
        for(int i=str.length;i<6;i+=2){
            sql =  sql.replace("{"+String.valueOf(i)+"}","''");
            sql =  sql.replace("{"+String.valueOf(i+1)+"}","''");
        }
        List<Map<String, Object>> resultList = CRUD.Query(sql,conn);
        List<Book> result = new ArrayList<>();
        for (Map<String, Object> map : resultList) {
            Book book = mapToBean.map2Bean(map, Book.class);
            result.add(book);
        }
        return result;
    }

    /**
     * 查询借书情况
     *
     * @param studentID 学生证
     * @return {@link List}<{@link Book}>
     * @throws Exception 异常
     */
    public static List<Book> searchMine(String studentID) throws Exception {
        String sql = "SELECT tb_BOOK.* from tb_BOOK ,tb_BOOKWITHSTUDENT "+
        "WHERE tb_BOOK.bookID = tb_BOOKWITHSTUDENT.bookID "+
        "and tb_BOOKWITHSTUDENT.studentID = '"+studentID+"'";
        List<Map<String, Object>> resultList = CRUD.Query(sql,conn);
        List<Book> result = new ArrayList<>();
        System.out.println(resultList);
        for (Map<String, Object> map : resultList) {
            Book book = mapToBean.map2Bean(map, Book.class);
            result.add(book);
        }
        return result;
    }

    /**
     * 管理员添加书
     *
     * @param book 书
     * @return {@link Boolean}
     *///添加一本图书，仅管理员可操作
    public static Boolean addBook(Book book) {
        try {
            addClass(book, "tb_BOOK");
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }

    /**
     * 管理员删除书
     *
     * @param bookID 书id
     * @return {@link Boolean}
     */
    public static Boolean deleteBook(String bookID) {
        try {
            delete("bookID", bookID, "tb_BOOK");
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }

    /**
     * 借书
     *
     * @param bookID    书id
     * @param studentID 学生一卡通号
     * @return {@link Boolean}
     */
    public static Boolean borrowBook(String bookID,String studentID)  {
        try {
            Date date = new Date(System.currentTimeMillis());
            String sql1 = "update tb_BOOK set leftSize = leftSize-1 where bookID = " + "'" + bookID + "'";
            String sql2 = "insert into tb_BOOKWITHSTUDENT (studentID,bookID,date) values " + "('" + studentID + "','" + bookID + "','"+date+"')";
            Connection conn = databaseConn.getConn();//此处使用数据库事务来处理同时执行的语句
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            int i = stm.executeUpdate(sql1);
            int j = stm.executeUpdate(sql2);
            if (i > 0 && j > 0) {
                conn.commit();
                conn.close();
                return true;
            } else {
                conn.rollback();
                conn.close();
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 还书
     *
     * @param bookID    书id
     * @param studentID 学生一卡通号
     * @return {@link Boolean}
     * @throws Exception 异常
     *///还书，用于管理员处理还书的情况
    public static Boolean returnBook(String bookID,String studentID) throws Exception {
        try {
            String sql1 = "update tb_BOOK set leftSize = leftSize+1 where bookID = " + "'" + bookID + "'";
            String sql2 = "delete from tb_BOOKWITHSTUDENT  where studentID = " + "'" + studentID + "' and bookID = '" + bookID + "'";
            Connection conn = databaseConn.getConn();//此处使用数据库事务来处理同时执行的语句
            conn.setAutoCommit(false);
            Statement stm = conn.createStatement();
            int i = stm.executeUpdate(sql2);
            int j = stm.executeUpdate(sql1);
            if (i > 0 && j > 0) {
                conn.commit();
                conn.close();
                return true;
            } else {
                conn.rollback();
                conn.close();
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 修改图书信息
     *
     * @param bookID 书id
     * @param field  字段
     * @param value  值
     * @return {@link Boolean}
     */
   public static Boolean revise(String bookID, String field, Object value) {
        try {
            String sql = "update tb_BOOK set "+field+"  = ? where bookID = '"+bookID+"'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1,value);
            ps.executeUpdate();
            return true;
        }catch (Exception e){
            return false;
        }
   }
}
