package com.vcampus.server.service;

import com.vcampus.dao.LibraryDao;
import com.vcampus.pojo.Book;
import com.vcampus.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class LibraryService implements Service {

    private boolean checkBook(Book std, Book book) {
        if(std.getBookID() != null) {
            if(!book.getBookID().equals(std.getBookID())) return false;
        }
        if(std.getType() != null) {
            if(!book.getType().equals(std.getType())) return false;
        }
        if(std.getAuthor() != null) {
            return book.getAuthor().equals(std.getAuthor());
        }
        return true;
    }

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
        List<Book> res = new ArrayList<>();
        try {
            List<Book> tmp = LibraryDao.search();
            for(Book item: tmp) {
                if(checkBook(book, item)) res.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
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

    public boolean deleteBook(Book book) {
        return LibraryDao.deleteBook(book.getBookID());
    }

    public boolean borrowBook(String bookID, String studentID) {
        return LibraryDao.borrowBook(bookID, studentID);
    }

    public boolean returnBook(String bookID, String studentID) {
        try {
            return LibraryDao.returnBook(bookID, studentID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
