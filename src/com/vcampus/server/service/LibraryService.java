package com.vcampus.server.service;

import com.vcampus.dao.LibraryDao;
import com.vcampus.pojo.Book;
import com.vcampus.pojo.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.vcampus.dao.LibraryDao.revise;

/**
 * 图书馆模块的服务层
 */
public class LibraryService implements Service {

    /**
     * 获得所有图书的列表
     *
     * @return 所有书的列表
     */
    public List<Book> viewAllBooks() {
        List<Book> res = null;
        try {
            res = LibraryDao.search();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 指定条件搜索书籍
     *
     * @param book 封装了搜索条件的Book
     * @return 符合条件的书的列表
     */
    public List<Book> searchBooks(Book book) {
        List<Book> res = null, ans = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        try {
            if(book.getBookName() != null) {
                strings.add("bookName");
                strings.add(book.getBookName());
            }
            if(book.getAuthor() != null) {
                strings.add("author");
                strings.add(book.getAuthor());
            }
            if(book.getType() != null) {
                strings.add("type");
                strings.add(book.getType());
            }
            if(strings.size() == 0) res = LibraryDao.search();
            else res = LibraryDao.search(strings.toArray(new String[0]));
            for(Book item: res) {
                if(book.getBookID() == null || item.getBookID().equals(book.getBookID()))
                    ans.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }

    /**
     * 获得这个用户所有借了的书
     *
     * @param user 用户
     * @return 该用户借的书的列表
     */
    public List<Book> searchBorrowedBooks(User user) {
        List<Book> res = null;
        try {
            res = LibraryDao.searchMine(user.getStudentID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 添加一本新书
     *
     * @param book 要被加进去的书
     * @return 操作成功与否
     */
    public boolean addNewBook(Book book) {
        return LibraryDao.addBook(book);
    }

    /**
     * 改变一本书的属性（书名、作者、类型、剩余数量、图片）
     *
     * @param book the book
     * @return 操作成功与否
     */
    public boolean changeBook(Book book) {
        boolean result = true;
        try {
            result = LibraryDao.revise(book.getBookID(), "bookName", book.getBookName());
            result = result & LibraryDao.revise(book.getBookID(), "author", book.getAuthor());
            result = result & LibraryDao.revise(book.getBookID(), "type", book.getType());
            result = result & LibraryDao.revise(book.getBookID(), "leftSize", book.getLeftSize());
            result = result & LibraryDao.revise(book.getBookID(), "image", book.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除一种书
     *
     * @param book 要被删的书
     * @return 操作成功与否
     */
    public boolean deleteBook(Book book) {
        return LibraryDao.deleteBook(book.getBookID());
    }

    /**
     * 借一本书
     *
     * @param bookID    被借的书的ID
     * @param studentID 借书人的ID
     * @return 被借的书
     */
    public Book borrowBook(String bookID, String studentID) {
        boolean res = LibraryDao.borrowBook(bookID, studentID);
        if(!res) return null;
        try {
            return LibraryDao.search("bookID", bookID).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 归还一本书
     *
     * @param bookID    书的ID
     * @param studentID 还书人的ID
     * @return 就是这本书
     */
    public Book returnBook(String bookID, String studentID) {
        try {
            boolean res = LibraryDao.returnBook(bookID, studentID);
            if(!res) return null;
            return LibraryDao.search("bookID", bookID).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
