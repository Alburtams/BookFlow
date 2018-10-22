package com.hust.bookflow.bookflowservice;

import com.hust.bookflow.model.bean.BookDetailsBean;
import com.hust.bookflow.model.bean.BookListBeans;
import com.hust.bookflow.model.bean.BooksBean;
import com.hust.bookflow.model.bean.HomeHttpResult;
import com.hust.bookflow.model.bean.BookListHttpResult;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface BookFlowService {

    @FormUrlEncoded
    @POST("login")
    Observable<Boolean> login(@Field("stu_id") String stuId, @Field("passwd") String passwd);

    @FormUrlEncoded
    @POST("register")
    Observable<Boolean> register(@Field("stud_id") String stuId, @Field("stu_name") String stuName, @Field("scho_name") String schoName, @Field("email_addr") String emailAddr);

    // 首页图书列表
    @GET("list")
    Observable<HomeHttpResult<List<BooksBean>>> getHomeList(@Query("count") int count);

    // 图书详情
    @GET("book")
    Observable<BookDetailsBean> getBookDetails(@Query("book_id") String bookId);

    // 按bookid搜索
    @GET("search")
    Observable<BookListHttpResult<List<BookListBeans>>> searchById(@Query("book_id") String bookId);

    // 按name搜索
    @GET("search")
    Observable<BookListHttpResult<List<BookListBeans>>> searchByName(@Query("book_name") String bookName);

    @FormUrlEncoded
    @POST("borrow")
    Observable<Boolean> borrowBook(@Field("book_id") String bookId, @Field("stu_id") String stuId);

    @FormUrlEncoded
    @POST("return")
    Observable<Boolean> returnBook(@Field("book_id") String bookId, @Field("stu_id") String stuId);

    @GET("borrowed")
    Observable<BookListHttpResult<List<BookListBeans>>> getBorrowed(@Query("stu_id") String stuId);

}
