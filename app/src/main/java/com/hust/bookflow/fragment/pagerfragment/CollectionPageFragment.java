package com.hust.bookflow.fragment.pagerfragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hust.bookflow.MyApplication;
import com.hust.bookflow.R;
import com.hust.bookflow.activity.BookDetailsActivity;
import com.hust.bookflow.adapter.CollectionBookAdapter;
import com.hust.bookflow.adapter.base.BaseCollectionAdapter;
import com.hust.bookflow.model.db.Book_db;
import com.hust.bookflow.model.db.GreenDaoUtils;
import com.hust.bookflow.model.dbinterface.DbObservrt;
import com.hust.bookflow.utils.Constants;
import com.hust.bookflow.utils.ToastUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ChinaLHR on 2017/1/7.
 * Email:13435500980@163.com
 */

public class CollectionPageFragment extends Fragment implements DbObservrt {
    private Subscriber mSubscriber;
    private String title;
    private GreenDaoUtils utils;
    private RecyclerView page_collection_rv;


    private CollectionBookAdapter mBookAdapter;




    private List<Book_db> bookdata;



    private Subscriber<List<Book_db>> mBookSubscriber;


    private RecyclerView.LayoutManager mLayoutManager;


    public CollectionPageFragment(){}

    @SuppressLint("ValidFragment")
    public CollectionPageFragment(Observable<String> observable) {
        mSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                title = s;
            }

        };
        observable.subscribe(mSubscriber);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utils = MyApplication.getDbUtils();
        utils.attach(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pagefragment_collection, container, false);
        page_collection_rv = (RecyclerView) view.findViewById(R.id.page_collection_rv);
        initData();
        return view;


    }

    private void initData() {


        if (title.equals(Constants.COLLECTION_TYPE[0])) {

        } else if (title.equals(Constants.COLLECTION_TYPE[1])) {
            getBookByDatabase();
        } else if (title.equals(Constants.COLLECTION_TYPE[2])) {

        }


    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (title.equals(Constants.COLLECTION_TYPE[0])) {

        } else if (title.equals(Constants.COLLECTION_TYPE[1])) {
            mBookAdapter = new CollectionBookAdapter(getActivity(),bookdata);
            page_collection_rv.setLayoutManager(mLayoutManager);
            page_collection_rv.setAdapter(mBookAdapter);
            mBookAdapter.setOnItemClickListener(new BaseCollectionAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(String id, String url) {
                    BookDetailsActivity.toActivity(getActivity(),id,url);
                }
            });
        }else if (title.equals(Constants.COLLECTION_TYPE[2])){

        }


    }

    /**
     * 异步读取book
     */
    public void getBookByDatabase() {
        mBookSubscriber = new Subscriber<List<Book_db>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.show(getActivity(), "读取数据失败");
            }

            @Override
            public void onNext(List<Book_db> book_dbs) {
                if (!book_dbs.isEmpty()) {
                    bookdata = book_dbs;
                    initView();
                }
            }
        };
        Observable.just(utils.queryAllBook())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mBookSubscriber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscriber != null) {
            mSubscriber.unsubscribe();
        }
        if (mBookSubscriber != null) {
            mBookSubscriber.unsubscribe();
        }
        utils.detach(this);
    }


    @Override
    public void updateMovie() {
        if (title.equals(Constants.COLLECTION_TYPE[0])) {

        }
    }

    @Override
    public void updateBook() {
        if (title.equals(Constants.COLLECTION_TYPE[1])) {
            getBookByDatabase();
        }
    }

    @Override
    public void updateActor() {
        if (title.equals(Constants.COLLECTION_TYPE[2])) {

        }
    }
}
