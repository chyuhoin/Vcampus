package com.vcampus.server.controller;

import com.google.gson.Gson;
import com.google.gson.internal.Pair;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.Message;
import com.vcampus.pojo.Book;
import com.vcampus.pojo.User;
import com.vcampus.server.service.LibraryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Library controller.
 */
public class LibraryController implements Controller {

    private final LibraryService service;
    private final Gson gson;
    private final Map<String, Object> map;

    /**
     * Instantiates a new Library controller.
     */
    public LibraryController() {
        service = new LibraryService();
        gson = new Gson();
        map = new HashMap<>();
    }

    private Pair<String, String> parseJSON(Message msg) {
        Map<String,Object> arguments = new Gson().fromJson(msg.getData(),
                new TypeToken<HashMap<String,Object>>(){}.getType());
        String bookId = (String) arguments.get("bookId");
        String studentId = (String) arguments.get("studentId");
        return new Pair<>(bookId, studentId);
    }

    private List<Book> getBook(Message msg) {
        Book book = gson.fromJson(msg.getData(), Book.class);
        return service.searchBooks(book);
    }

    private List<Book> getBorrowedBook(Message msg) {
        User user = gson.fromJson(msg.getData(), User.class);
        return service.searchBorrowedBooks(user);
    }

    private String adminAddBook(Message msg) {
        Book book = gson.fromJson(msg.getData(), Book.class);
        return service.addNewBook(book) ? "OK" : "Error";
    }

    private String adminChangeBook(Message msg) {
        Book book = gson.fromJson(msg.getData(), Book.class);
        return service.changeBook(book) ? "OK" : "Error";
    }

    private String adminDeleteBook(Message msg) {
        Book book = gson.fromJson(msg.getData(), Book.class);
        return service.deleteBook(book) ? "OK" : "Error";
    }

    private List<Book> adminBorrowBook(Message msg) {
        List<Book> res = new ArrayList<>();
        Pair<String, String> pair = parseJSON(msg);
        res.add(service.borrowBook(pair.first, pair.second));
        return res;
    }

    private List<Book> adminReturnBook(Message msg) {
        List<Book> res = new ArrayList<>();
        Pair<String, String> pair = parseJSON(msg);
        res.add(service.returnBook(pair.first, pair.second));
        return res;
    }

    @Override
    public Message check(Message msg) {


        if(msg.getStatus().equals("admin")) {
            switch (msg.getOperation()) {
                case "get": { //指定条件搜索书籍
                    map.put("res", getBook(msg));
                    break;
                }
                case "getB": { //查看已经借了的书
                    map.put("res", getBorrowedBook(msg));
                    break;
                }
                case "post": { //管理员新增书籍
                    map.put("res", adminAddBook(msg));
                    break;
                }
                case "put": { //管理员修改书籍
                    map.put("res", adminChangeBook(msg));
                    break;
                }
                case "delete": { //管理员删除书籍
                    map.put("res", adminDeleteBook(msg));
                    break;
                }
                case "borrow": { //只有管理员手操才能实现借书
                    map.put("res", adminBorrowBook(msg));
                    break;
                }
                case "return": { //只有管理员手操才能实现还书
                    map.put("res", adminReturnBook(msg));
                    break;
                }
            }
            return new Message("200", gson.toJson(map));
        } else {
            if(msg.getOperation().equals("get")) { //指定条件搜索书籍
                map.put("res", getBook(msg));
                return new Message("200", gson.toJson(map));
            } else if(msg.getOperation().equals("getB")) { //查看已经借了的书
                map.put("res", getBorrowedBook(msg));
                return new Message("200", gson.toJson(map));
            }
        }
        return new Message("404", "{res: 'Wrong Request!'}");
    }
}
