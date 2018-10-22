package com.hust.bookflow.model.httputils;

import com.hust.bookflow.doubanservice.DouBanService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/10/21 0021.
 */

public class UserHttpMethods {
    public static final String BASE_URL_USER = "http://gank.io/api/data/福利/50/1";
    private DouBanService mUserService;
    private Retrofit retrofit_user;

    private static final int DEFAULT_TIMEOUT = 5;

    private UserHttpMethods(){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit_user=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL_USER)
                .client(httpClientBuilder.build())
                .build();
        mUserService = retrofit_user.create(DouBanService.class);
    }

    private static class Holder {
        private static final UserHttpMethods INSTANCE = new UserHttpMethods();
    }

    public static final UserHttpMethods getInstance() {
        return UserHttpMethods.Holder.INSTANCE;
    }

    /**登录
     * 成功 200
     * 失败 500
     * */
    public void login(Subscriber<Object> subscriber, String stuID,String pwd){
        mUserService.login(stuID,pwd)
                .onErrorReturn(new Func1<Throwable, Object>() {
                    @Override
                    public Object call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**注册
     * 成功 200
     * 失败 500
     * */
    public void register(Subscriber<Object> subscriber, String name,String stuID,String pwd,String email){
        mUserService.register(name,stuID,pwd,email)
                .onErrorReturn(new Func1<Throwable, Object>() {
                    @Override
                    public Object call(Throwable throwable) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
