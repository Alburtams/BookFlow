package com.hust.bookflow.model.bean;

import java.io.Serializable;

/**
 * Created by 文辉 on 2018/10/19.
 */

// TODO
<<<<<<< HEAD
public class BookListBeans implements Serializable {
=======
public class BookListBeans {

    private String book_id;
    private String book_name;
    private String picture;
    private String author;
    private String press;

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }
>>>>>>> master
}
