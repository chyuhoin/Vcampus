package com.vcampus.pojo;

import java.io.Serializable;
import java.util.Arrays;

public class Book implements Serializable {
    private String bookID;
    private String bookName;
    private String author;
    private String type;
    private Long leftSize;
    private byte[] image;

    public Book(String bookID, String bookName, String author, String type, Long leftSize, byte[] image) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.type = type;
        this.leftSize = leftSize;
        this.image = image;
    }

    public Book() {
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

    public Long getLeftSize() {
        return leftSize;
    }

    public void setLeftSize(Long leftSize) {
        this.leftSize = leftSize;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookID='" + bookID + '\'' +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", type='" + type + '\'' +
                ", leftSize=" + leftSize +
                '}';
    }
}
