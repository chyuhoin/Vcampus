package com.vcampus.pojo;

import com.vcampus.dao.utils.StringAndImage;

import java.io.IOException;
import java.io.Serializable;

/**
 * 书
 *
 * @author 12107
 * @date 2022/09/01
 */
public class Book implements Serializable {
    private String bookID;
    private String bookName;
    private String author;
    private String type;
    private Integer leftSize;
    private String image;

    /**
     * 书
     *
     * @param bookID   书id
     * @param bookName 书名字
     * @param author   作者
     * @param type     类型
     * @param leftSize 剩余数量
     * @param path     图片路径
     */
    public Book(String bookID, String bookName, String author, String type, Integer leftSize, String path){
        //最后一个path是本地图片的路径
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.type = type;
        this.leftSize = leftSize;
        try {
            this.image = StringAndImage.ImageToString(path);
        }catch (Exception e){

        }
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

    public Integer getLeftSize() {
        return leftSize;
    }

    public void setLeftSize(Integer leftSize) {
        this.leftSize = leftSize;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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
