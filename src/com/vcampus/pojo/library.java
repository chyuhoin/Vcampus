package com.vcampus.pojo;

public class library {
    private String bookID;
    private String bookName;
    private String author;
    private String type;
    private int leftSize;

    public library(String bookID, String bookName, String author, String type, int leftSize) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.type = type;
        this.leftSize = leftSize;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLeftSize() {
        return leftSize;
    }

    public void setLeftSize(int leftSize) {
        this.leftSize = leftSize;
    }
}
