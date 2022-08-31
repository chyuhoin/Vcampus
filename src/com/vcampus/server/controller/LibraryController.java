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
 * 图书馆模块的Controller
 *
 * @author ZhongHaoyuan
 */
public class LibraryController implements Controller {

    private final LibraryService service;
    private final Gson gson;
    private final Map<String, Object> map;

    /**
     * 初始化一个LibraryController
     */
    public LibraryController() {
        service = new LibraryService();
        gson = new Gson();
        map = new HashMap<>();
    }

    /**
     * 将json字符串解析成bookID和studentID的一个pair
     *
     * @param msg 消息
     * @return the pair
     */
    private Pair<String, String> parseJSON(Message msg) {
        Map<String,Object> arguments = new Gson().fromJson(msg.getData(),
                new TypeToken<HashMap<String,Object>>(){}.getType());
        String bookId = (String) arguments.get("bookId");
        String studentId = (String) arguments.get("studentId");
        return new Pair<>(bookId, studentId);
    }

    /**
     * 按照传入的Book返回符合对应要求的书籍列表
     *
     * @param msg 消息，包含一个用来描述搜索条件的Book
     * @return 符合要求的书籍列表
     */
    private List<Book> getBook(Message msg) {
        Book book = gson.fromJson(msg.getData(), Book.class);
        return service.searchBooks(book);
    }

    /**
     * 获得该用户所有正在借的书
     *
     * @param msg 消息，包含一个User
     * @return 该用户正在借的书的列表
     */
    private List<Book> getBorrowedBook(Message msg) {
        User user = gson.fromJson(msg.getData(), User.class);
        return service.searchBorrowedBooks(user);
    }

    /**
     * 管理员操作，新增一本书
     *
     * @param msg 消息，包含要被新增进去的那本书
     * @return 表示操作是否正常完成的字符串
     */
    private String adminAddBook(Message msg) {
        Book book = gson.fromJson(msg.getData(), Book.class);
        return service.addNewBook(book) ? "OK" : "Error";
    }

    /**
     * 管理员操作，改变一本书的各项属性（书名、作者、剩余数量等等）
     *
     * @param msg 消息，包含一个Book
     * @return 表示操作是否正常完成的字符串
     */
    private String adminChangeBook(Message msg) {
        Book book = gson.fromJson(msg.getData(), Book.class);
        return service.changeBook(book) ? "OK" : "Error";
    }

    /**
     * 管理员操作，删除一本书
     *
     * @param msg 消息，包含一个Book
     * @return 表示操作是否正常完成的字符串
     */
    private String adminDeleteBook(Message msg) {
        Book book = gson.fromJson(msg.getData(), Book.class);
        return service.deleteBook(book) ? "OK" : "Error";
    }

    /**
     * 管理员操作，为指定同学借一本书（只能让管理员来操作借书，学生自己是不能擅自借书的）
     *
     * @param msg 消息，包含一个学生ID和一个书本ID
     * @return 借来的书的列表
     */
    private List<Book> adminBorrowBook(Message msg) {
        List<Book> res = new ArrayList<>();
        Pair<String, String> pair = parseJSON(msg);
        res.add(service.borrowBook(pair.first, pair.second));
        return res;
    }

    /**
     * 管理员操作，让指定同学归还一本书（同借书，学生是不能擅自还书的）
     *
     * @param msg 消息，包含一个学生ID和一个书本ID
     * @return 归还的书的列表
     */
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
