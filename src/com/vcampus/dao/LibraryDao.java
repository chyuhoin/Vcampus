package com.vcampus.dao;

import com.vcampus.dao.utils.CRUD;
import com.vcampus.dao.utils.databaseConn;
import com.vcampus.pojo.Book;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.vcampus.dao.utils.mapToBean;
import org.junit.Test;

public class LibraryDao {
    //查询函数，无参的情况下查看所有值
    public static List<Book> search() throws Exception {
        String sql = "select * from tb_BOOK";
        List<Map<String, Object>> resultList = CRUD.Query(sql);
        List<Book> result = new ArrayList<>();
        for (Map<String, Object> map : resultList) {
            Book book = mapToBean.map2Bean(map, Book.class);
            result.add(book);
        }
        return result;
    }
    //重载的查询函数，按条件查询
    public static List<Book> search(String field, String values) throws Exception {
        String sql = "select * from tb_BOOK" + " where " + field + " = " + "'" + values + "'";
        List<Map<String, Object>> resultList = CRUD.Query(sql);
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
        List<Map<String, Object>> resultList = CRUD.Query(sql);
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
            String sql = "insert into tb_BOOK (bookID,bookName,author,type,leftSize) values" + "('" +
                    book.getBookID() + "','" +
                    book.getBookName() + "','" +
                    book.getAuthor() + "','" +
                    book.getType() + "','" +
                    book.getLeftSize() + "')";
            CRUD.update(sql);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    //删除一本图书，仅管理员可操作
    public static Boolean deleteBook(String bookID) {
        try {
            String sql = "delete from tb_BOOK where bookID = " + "'" + bookID + "'";
            CRUD.update(sql);
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
}
