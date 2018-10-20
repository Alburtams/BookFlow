package com.hust.bookflow.model.bean;

/**
 * Created by 文辉 on 2018/10/19.
 */

public class HomeHttpResult<T> {

    private int count;
    private int total;
    private T books;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public T getBooks() {
        return books;
    }

    public void setBooks(T books) {
        this.books = books;
    }
}
