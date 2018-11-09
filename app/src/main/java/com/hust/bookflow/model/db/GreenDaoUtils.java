package com.hust.bookflow.model.db;

import android.content.Context;

import com.hust.bookflow.model.dbinterface.DbObservrt;
import com.hust.bookflow.model.dbinterface.DbSubject;

import java.util.Collections;
import java.util.List;

/**
 * Created by ChinaLHR on 2017/1/8.
 * Email:13435500980@163.com
 */

public class GreenDaoUtils extends DbSubject {

    private GreenDaoHelper mHelper;

    public GreenDaoUtils(Context context) {
        mHelper = GreenDaoHelper.getInstance(context);
    }

    public boolean insertBook(Book_db book_db) {
        long insert = mHelper.getDaoSession().getBook_dbDao().insert(book_db);
        if (insert != 0) {
            notifyUpdateBook();
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteBook(String id) {
        List<Book_db> list = mHelper.getDaoSession().getBook_dbDao().queryBuilder()
                .where(Book_dbDao.Properties.Book_id.eq(id))
                .build()
                .list();
        for (Book_db book : list) {
            mHelper.getDaoSession().getBook_dbDao().delete(book);
        }
        if (!list.isEmpty()) {
            notifyUpdateBook();
        }
        return !list.isEmpty();
    }

    public List<Book_db> queryAllBook() {
        List<Book_db> book_dbs = mHelper.getDaoSession().getBook_dbDao().loadAll();
        Collections.reverse(book_dbs);
        return book_dbs;
    }

    public boolean queryBook(String id) {
        List<Book_db> list = mHelper.getDaoSession().getBook_dbDao().queryBuilder()
                .where(Book_dbDao.Properties.Book_id.eq(id))
                .build()
                .list();
        return !list.isEmpty();
    }


    @Override
    public void notifyUpdateMovie() {
        for (Object o : observers) {
            ((DbObservrt) o).updateMovie();
        }
    }

    @Override
    public void notifyUpdateBook() {
        for (Object o : observers) {
            ((DbObservrt) o).updateBook();
        }
    }

    @Override
    public void notifyUpdateActor() {
        for (Object o : observers) {
            ((DbObservrt) o).updateActor();
        }
    }
}
