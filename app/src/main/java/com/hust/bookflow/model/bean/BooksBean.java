package com.hust.bookflow.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ChinaLHR on 2016/12/24.
 * Email:13435500980@163.com
 */

public class BooksBean implements Serializable {

    private String book_id;
    private String book_name;
    private String picture;

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
}
