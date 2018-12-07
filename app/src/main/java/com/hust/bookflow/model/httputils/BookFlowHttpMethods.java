package com.hust.bookflow.model.httputils;

import com.hust.bookflow.bookflowservice.BookFlowService;
import com.hust.bookflow.model.bean.BookDetailsBean;
import com.hust.bookflow.model.bean.BookHttpResult;
import com.hust.bookflow.model.bean.BookListBeans;
import com.hust.bookflow.model.bean.BookListHttpResult;
import com.hust.bookflow.model.bean.BooksBean;
import com.hust.bookflow.model.bean.HomeHttpResult;
import com.hust.bookflow.model.bean.MessageBean;
import com.hust.bookflow.utils.Constants;

import java.util.List;
import java.util.concurrent.TimeUnit;

//import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by 文辉 on 2018/10/20.
 */

public class BookFlowHttpMethods {

//    public static final String BACKEND_BOOK_URL = "http://132.232.199.162:8080/bookcrossing/";
    public static final String BACKEND_BOOK_URL = "http://202.114.6.204:8080/bookcrossing/";
    private BookFlowService bfService;
    private Retrofit bookRetrofit;

    private static final int DEFAULT_TIMEOUT = 5;

    private BookFlowHttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        bookRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BACKEND_BOOK_URL)
                .client(httpClientBuilder.build())
                .build();
        bfService = bookRetrofit.create(BookFlowService.class);
    }

    private static class Holder {
        private static final BookFlowHttpMethods BFINSTANCE = new BookFlowHttpMethods();
    }

    public static final BookFlowHttpMethods getInstance() {
        return Holder.BFINSTANCE;
    }

    public void getHomeList(Subscriber<List<BooksBean>> subscriber, String tag, int start, int count) {
        // TODO
        if (tag.equals(Constants.BOOKTITLE[0])) {
            // 热门
            bfService.getHomeList(start, count)
                    .map(new HttpResultFunc<List<BooksBean>>())
                    .onErrorReturn(new Func1<Throwable, List<BooksBean>>() {
                        @Override
                        public List<BooksBean> call(Throwable throwable) {
                            return null;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        } else if (tag.equals(Constants.BOOKTITLE[1])) {
            // 捐书推荐
            bfService.getNeedList(start, count)
                    .map(new HttpResultFunc<List<BooksBean>>())
                    .onErrorReturn(new Func1<Throwable, List<BooksBean>>() {
                        @Override
                        public List<BooksBean> call(Throwable throwable) {
                            return null;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        } else if (tag.equals(Constants.BOOKTITLE[2])) {
            // 今日上新
            bfService.getHomeList(start, count)
                    .map(new HttpResultFunc<List<BooksBean>>())
                    .onErrorReturn(new Func1<Throwable, List<BooksBean>>() {
                        @Override
                        public List<BooksBean> call(Throwable throwable) {
                            return null;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        } else {
            bfService.getHomeList(start, count)
                    .map(new HttpResultFunc<List<BooksBean>>())
                    .onErrorReturn(new Func1<Throwable, List<BooksBean>>() {
                        @Override
                        public List<BooksBean> call(Throwable throwable) {
                            return null;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }
    public void getlikeList(Subscriber<List<BookListBeans>> subscriber, String stuId){
        bfService.getlikeList(stuId)
                .map(new ListHttpResultFunc<List<BookListBeans>>())
                .onErrorReturn(new Func1<Throwable, List<BookListBeans>>() {
                    @Override
                    public List<BookListBeans> call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void bookLiked(Subscriber<MessageBean> subscriber, String stuID, String bookId){
        bfService.bookLiked(stuID,bookId)
                .onErrorReturn(new Func1<Throwable, MessageBean>() {
                    @Override
                    public MessageBean call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getTagBooks(Subscriber<List<BookListBeans>> subscriber, String tagName,int start,int count){
        bfService.getTagBooks(tagName,start,count)
                .map(new ListHttpResultFunc<List<BookListBeans>>())
                .onErrorReturn(new Func1<Throwable, List<BookListBeans>>() {
                    @Override
                    public List<BookListBeans> call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void likeBook(Subscriber<MessageBean> subscriber, String stuID, String bookId){
        bfService.likeBook(stuID,bookId)
                .onErrorReturn(new Func1<Throwable, MessageBean>() {
                    @Override
                    public MessageBean call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getHomeList(Subscriber<List<BooksBean>> subscriber, int start, int count) {
        bfService.getHomeList(start, count)
                .map(new HttpResultFunc<List<BooksBean>>())
                .onErrorReturn(new Func1<Throwable, List<BooksBean>>() {
                    @Override
                    public List<BooksBean> call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getBookByName(Subscriber<List<BookListBeans>> subscriber, String bookname,int start,int count){
        bfService.searchByName(bookname,start,count)
                .map(new ListHttpResultFunc<List<BookListBeans>>())
                .onErrorReturn(new Func1<Throwable, List<BookListBeans>>() {
                    @Override
                    public List<BookListBeans> call(Throwable throwable) {
                         return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getBookDetails(Subscriber<BookDetailsBean> subscriber, String bookId) {
        bfService.getBookDetails(bookId)
                .onErrorReturn(new Func1<Throwable, BookDetailsBean>() {
                    @Override
                    public BookDetailsBean call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void login(Subscriber<MessageBean> subscriber, String stuID, String pwd){
        bfService.login(stuID,pwd)
                .onErrorReturn(new Func1<Throwable, MessageBean>() {
                    @Override
                    public MessageBean call(Throwable throwable) {
                         return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    
    public void getBorrowed(Subscriber<List<BookListBeans>> subscriber, String stuId) {
        bfService.getBorrowed(stuId)
                .map(new ListHttpResultFunc<List<BookListBeans>>())
                .onErrorReturn(new Func1<Throwable, List<BookListBeans>>() {
                    @Override
                    public List<BookListBeans> call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void register(Subscriber<MessageBean> subscriber, String stuId,String stuName,String passwd){
        bfService.register(stuId,stuName,passwd)
                .onErrorReturn(new Func1<Throwable, MessageBean>() {
                    @Override
                    public MessageBean call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void borrowBook(Subscriber<Boolean> subscriber, String bookId, String stuId) {
        bfService.borrowBook(bookId, stuId)
                .onErrorReturn(new Func1<Throwable, Boolean>() {
                    @Override
                    public Boolean call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void returnBook(Subscriber<Boolean> subscriber, String bookId, String stuId) {
        bfService.returnBook(bookId, stuId)
                .onErrorReturn(new Func1<Throwable, Boolean>() {
                    @Override
                    public Boolean call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void isBookExist(Subscriber<Boolean> subscriber, String bookId) {
        bfService.isBookExist(bookId)
                .onErrorReturn(new Func1<Throwable, Boolean>() {
                    @Override
                    public Boolean call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void canBorrow(Subscriber<Boolean> subscriber, String stuId) {
        bfService.canBorrow(stuId)
                .onErrorReturn(new Func1<Throwable, Boolean>() {
                    @Override
                    public Boolean call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private class HttpResultFunc<T> implements Func1<HomeHttpResult<T>, T> {
        @Override
        public T call(HomeHttpResult<T> tHomeHttpResult) {
            if (tHomeHttpResult != null) {
                return tHomeHttpResult.getBooks();
            } else {
                throw new RuntimeException("出错了");
            }
        }
    }

    private class ListHttpResultFunc<T> implements Func1<BookListHttpResult<T>, T> {
        @Override
        public T call(BookListHttpResult<T> tHomeHttpResult) {
            if (tHomeHttpResult != null) {
                return tHomeHttpResult.getBooks();
            } else {
                throw new RuntimeException("出错了");
            }
        }
    }
}
