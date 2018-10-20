package com.hust.bookflow.model.bean;

/**
 * Created by 文辉 on 2018/10/19.
 */

public class BookListHttpResult<T> {

    private int count;
    private T books;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T getBooks() {
        return books;
    }

    public void setBooks(T books) {
        this.books = books;
    }
}
