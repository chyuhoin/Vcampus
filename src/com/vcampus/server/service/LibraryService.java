package com.vcampus.server.service;

import com.vcampus.dao.LibraryDao;
import com.vcampus.pojo.Book;
import com.vcampus.pojo.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.vcampus.dao.LibraryDao.revise;

public class LibraryService implements Service {

    public List<Book> viewAllBooks() {
        List<Book> res = null;
        try {
            res = LibraryDao.search();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

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

    public List<Book> searchBorrowedBooks(User user) {
        List<Book> res = null;
        try {
            res = LibraryDao.searchMine(user.getStudentID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean addNewBook(Book book) {
        return LibraryDao.addBook(book);
    }

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

    public boolean deleteBook(Book book) {
        return LibraryDao.deleteBook(book.getBookID());
    }

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
