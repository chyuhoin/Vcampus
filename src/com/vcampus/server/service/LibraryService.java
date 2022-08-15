package com.vcampus.server.service;

import com.vcampus.dao.LibraryDao;
import com.vcampus.pojo.Book;

import java.util.List;

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
}
