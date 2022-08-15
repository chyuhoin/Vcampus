package com.vcampus.server.controller;

import com.google.gson.Gson;
import com.vcampus.net.Message;
import com.vcampus.pojo.Book;
import com.vcampus.server.service.LibraryService;

import java.util.HashMap;
import java.util.List;

public class LibraryController implements Controller {
    @Override
    public Message check(Message msg) {
        if(msg.getOperation().equals("get")) {
            LibraryService service = new LibraryService();
            Gson gson = new Gson();
            HashMap<String, Object> map = new HashMap<>();
            map.put("res", service.viewAllBooks());
            return new Message("200", gson.toJson(map));
        }
        return new Message("404", "{res: 'Wrong Request!'}");
    }
}
