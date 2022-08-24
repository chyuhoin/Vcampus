package com.vcampus.server.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vcampus.net.Message;
import com.vcampus.pojo.Book;
import com.vcampus.pojo.User;
import com.vcampus.server.service.LibraryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryController implements Controller {
    @Override
    public Message check(Message msg) {
        LibraryService service = new LibraryService();
        Gson gson = new Gson();
        HashMap<String, Object> map = new HashMap<>();

        if(msg.getStatus().equals("admin")) {
            switch (msg.getOperation()) {
                case "get": { //指定条件搜索书籍
                    Book book = gson.fromJson(msg.getData(), Book.class);
                    map.put("res", service.searchBooks(book));
                    break;
                }
                case "getB": { //查看已经借了的书
                    User user = gson.fromJson(msg.getData(), User.class);
                    map.put("res", service.searchBorrowedBooks(user));
                    break;
                }
                case "post": { //管理员新增书籍
                    Book book = gson.fromJson(msg.getData(), Book.class);
                    map.put("res", service.addNewBook(book) ? "OK" : "Error");
                    break;
                }
                case "put": { //管理员修改书籍
                    Book book = gson.fromJson(msg.getData(), Book.class);
                    map.put("res", service.changeBook(book) ? "OK" : "Error");
                    break;
                }
                case "delete": { //管理员删除书籍
                    Book book = gson.fromJson(msg.getData(), Book.class);
                    map.put("res", service.deleteBook(book) ? "OK" : "Error");
                    break;
                }
                case "borrow": { //只有管理员手操才能实现借书
                    Map<String,Object> arguments = new Gson().fromJson(msg.getData(),
                            new TypeToken<HashMap<String,Object>>(){}.getType());
                    String bookId = (String) arguments.get("bookId");
                    String studentId = (String) arguments.get("studentId");
                    List<Book> res = new ArrayList<>();
                    res.add(service.borrowBook(bookId, studentId));
                    map.put("res", res);
                    break;
                }
                case "return": { //只有管理员手操才能实现还书
                    Map<String,Object> arguments = new Gson().fromJson(msg.getData(),
                            new TypeToken<HashMap<String,Object>>(){}.getType());
                    String bookId = (String) arguments.get("bookId");
                    String studentId = (String) arguments.get("studentId");
                    List<Book> res = new ArrayList<>();
                    res.add(service.returnBook(bookId, studentId));
                    map.put("res", res);
                    break;
                }
            }
            return new Message("200", gson.toJson(map));
        } else {
            if(msg.getOperation().equals("get")) { //指定条件搜索书籍
                Book book = gson.fromJson(msg.getData(), Book.class);
                map.put("res", service.searchBooks(book));
                return new Message("200", gson.toJson(map));
            } else if(msg.getOperation().equals("getB")) { //查看已经借了的书
                User user = gson.fromJson(msg.getData(), User.class);
                map.put("res", service.searchBorrowedBooks(user));
                return new Message("200", gson.toJson(map));
            }
        }
        return new Message("404", "{res: 'Wrong Request!'}");
    }
}
