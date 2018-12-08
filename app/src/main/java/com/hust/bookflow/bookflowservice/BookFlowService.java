package com.hust.bookflow.bookflowservice;

import com.hust.bookflow.model.bean.BookDetailsBean;
import com.hust.bookflow.model.bean.BookListBeans;
import com.hust.bookflow.model.bean.BooksBean;
import com.hust.bookflow.model.bean.HomeHttpResult;
import com.hust.bookflow.model.bean.BookListHttpResult;
import com.hust.bookflow.model.bean.MessageBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface BookFlowService {

    @GET("book/likeList")
    Observable<BookListHttpResult<List<BookListBeans>>> getlikeList(@Query("stu_id") String stuId);

    @FormUrlEncoded
    @POST("book/liked")
    Observable<MessageBean> bookLiked(@Field("stu_id") String stuId, @Field("book_id") String bookId);

    @GET("tagBooks")
    Observable<BookListHttpResult<List<BookListBeans>>> getTagBooks(@Query("tag_name") String tagName,@Query("start") int start, @Query("count") int count);

    @FormUrlEncoded
    @POST("book/like")
    Observable<MessageBean> likeBook(@Field("stu_id") String stuId, @Field("book_id") String bookId);

    @FormUrlEncoded
    @POST("user/login")
    Observable<MessageBean> login(@Field("stu_id") String stuId, @Field("passwd") String passwd);

    @FormUrlEncoded
    @POST("user/register")
    Observable<MessageBean> register(@Field("stu_id") String stuId, @Field("stu_name") String stuName,@Field("passwd") String passwd);

    // 首页图书列表
    @GET("list")
    Observable<HomeHttpResult<List<BooksBean>>> getHomeList(@Query("start") int start, @Query("count") int count);

    // 首页图书列表
    @GET("hotlist")
    Observable<HomeHttpResult<List<BooksBean>>> getHotList(@Query("start") int start, @Query("count") int count);

    // 首页图书列表
    @GET("needlist")
    Observable<HomeHttpResult<List<BooksBean>>> getNeedList(@Query("start") int start, @Query("count") int count);

    // 首页图书列表
    @GET("todaylist")
    Observable<HomeHttpResult<List<BooksBean>>> getTodayList(@Query("start") int start, @Query("count") int count);

    // 图书详情
    @GET("book")
    Observable<BookDetailsBean> getBookDetails(@Query("book_id") String bookId);

    // 按name搜索
    @GET("search")
    Observable<BookListHttpResult<List<BookListBeans>>> searchByName(@Query("book_name") String bookName,@Query("start") int start, @Query("count") int count);

    @FormUrlEncoded
    @POST("borrow")
    Observable<Boolean> borrowBook(@Field("book_id") String bookId, @Field("stu_id") String stuId);

    @FormUrlEncoded
    @POST("return")
    Observable<Boolean> returnBook(@Field("book_id") String bookId, @Field("stu_id") String stuId);

    @GET("borrowed")
    Observable<BookListHttpResult<List<BookListBeans>>> getBorrowed(@Query("stu_id") String stuId);

    @GET("book_exist")
    Observable<Boolean> isBookExist(@Query("book_id") String bookId);

    @GET("can_borrow")
    Observable<Boolean> canBorrow(@Query("stu_id") String stuId);
}
