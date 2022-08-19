package com.vcampus.dao;

import com.vcampus.dao.utils.CRUD;
import com.vcampus.dao.utils.databaseConn;
import com.vcampus.pojo.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.vcampus.dao.utils.mapToBean;
import org.junit.Test;

public class LibraryDao extends BaseDao{
    //查询函数，无参的情况下查看所有值
    public static List<Book> search() throws Exception {
        String sql = "select * from tb_BOOK";
        List<Map<String, Object>> resultList = CRUD.Query(sql,conn);
        List<Book> result = new ArrayList<>();
        for (Map<String, Object> map : resultList) {
            Book book = mapToBean.map2Bean(map, Book.class);
            result.add(book);
        }
        return result;
    }
    //重载的查询函数，按条件查询,str的顺序为 字段+值，两个一组重复
    public static List<Book> search(String...str) throws Exception {
        String sql = "select * from tb_BOOK  where {0} = {1} and {2}={3} and {4}={5}" ;
        for(int i=0;i<str.length;i+=2){
           sql =  sql.replace("{"+String.valueOf(i)+"}","'"+str[i]+"'");
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
    //查看自己借了哪些书
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
    //添加一本图书，仅管理员可操作
    public static Boolean addBook(Book book) {
        try {
            String sql = "insert into tb_BOOK (bookID,bookName,author,type,leftSize,image) values" + "('" +
                    book.getBookID() + "','" +
                    book.getBookName() + "','" +
                    book.getAuthor() + "','" +
                    book.getType() + "'," +
                    String.valueOf(book.getLeftSize()) +",'"+
                    book.getImage()+"')";
            CRUD.update(sql,conn);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //删除一本图书，仅管理员可操作
    public static Boolean deleteBook(String bookID) {
        try {
            String sql = "delete from tb_BOOK where bookID = " + "'" + bookID + "'";
            CRUD.update(sql,conn);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    //借一本书，用于管理员处理借书的情况
    public static Boolean borrowBook(String bookID,String studentID)  {
        try {
            String sql1 = "update tb_BOOK set leftSize = leftSize-1 where bookID = " + "'" + bookID + "'";
            String sql2 = "insert into tb_BOOKWITHSTUDENT (studentID,bookID) values " + "('" + studentID + "','" + bookID + "')";
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
    //还书，用于管理员处理还书的情况
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
    //修改图书的信息，由管理员操作
   public static Boolean revise(String bookID, String field, Object value) throws SQLException {
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
