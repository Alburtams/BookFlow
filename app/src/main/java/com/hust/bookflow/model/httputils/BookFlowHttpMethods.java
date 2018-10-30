package com.hust.bookflow.model.httputils;

import com.hust.bookflow.bookflowservice.BookFlowService;
import com.hust.bookflow.doubanservice.DouBanService;
import com.hust.bookflow.model.bean.BookDetailsBean;
import com.hust.bookflow.model.bean.BookHttpResult;
import com.hust.bookflow.model.bean.BooksBean;
import com.hust.bookflow.model.bean.HomeHttpResult;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.hust.bookflow.model.httputils.BookHttpMethods.BASE_URL_BOOK;

/**
 * Created by 文辉 on 2018/10/20.
 */

public class BookFlowHttpMethods {

    public static final String BACKEND_BOOK_URL = "http://202.114.6.141:8080/bookcrossing/";
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

    public void getHomeList(Subscriber<List<BooksBean>> subscriber, int count) {
        bfService.getHomeList(count)
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

}
