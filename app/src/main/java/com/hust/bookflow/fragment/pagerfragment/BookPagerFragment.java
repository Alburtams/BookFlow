package com.hust.bookflow.fragment.pagerfragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.hust.bookflow.R;
import com.hust.bookflow.activity.BookDetailsActivity;
import com.hust.bookflow.adapter.PageBookAdapter;
import com.hust.bookflow.fragment.base.BasePagerFragment;
import com.hust.bookflow.model.bean.BooksBean;
import com.hust.bookflow.utils.CacheUtils;
import com.hust.bookflow.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ChinaLHR on 2016/12/24.
 * Email:13435500980@163.com
 */

public class BookPagerFragment extends BasePagerFragment {
    private List<BooksBean> mdate;
    private Subscriber<List<BooksBean>> mListSubscriber;

    public BookPagerFragment(){}

    @SuppressLint("ValidFragment")
    public BookPagerFragment(Observable<Integer> observable) {
        super(observable);
    }

    @Override
    public void initRecyclerView() {
        List<BooksBean> mBookbean = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        pagerbaserv.setLayoutManager(mLayoutManager);
        mAdapter = new PageBookAdapter(getContext(), mBookbean);
        footer = LayoutInflater.from(this.getActivity()).inflate(R.layout.item_footer, pagerbaserv, false);

        Observable.just(mdate = CacheUtils.readbean(getActivity(), CacheUtils.DataCache_book, Constants.BOOKTITLE[position]))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<BooksBean>>() {
                    @Override
                    public void call(List<BooksBean> booksBeen) {
                        if (mdate!=null) {
                            mAdapter.upDates(mdate);
                        }else {
                            loadMovieData();
                        }
                    }
                });
        mAdapter.setFooterView(footer);
        pagerbaserv.setAdapter(mAdapter);
    }

    @Override
    public void updateMovieData() {
        if (mAdapter.getStart() == mStart) return;
        mStart = mAdapter.getStart();

        mListSubscriber = new Subscriber<List<BooksBean>>() {
            @Override
            public void onCompleted() {
                pagerbasefresh.setRefreshing(false);
                footer.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                footer.setVisibility(View.GONE);
            }

            @Override
            public void onNext(List<BooksBean> subjectsBeen) {
                if (subjectsBeen != null) {
                    mAdapter.addDatas(subjectsBeen);
                }
            }
        };


    }

    @Override
    public void loadMovieData() {
        pagerbasefresh.setRefreshing(true);
        mListSubscriber = new Subscriber<List<BooksBean>>() {
            @Override
            public void onCompleted() {
                pagerbasefresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                pagerbasefresh.setRefreshing(false);
            }

            @Override
            public void onNext(List<BooksBean> booksBeen) {
                if (booksBeen != null) {
                    mAdapter.upDates(booksBeen);

                    Observable.just(booksBeen)
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .observeOn(Schedulers.io())
                            .subscribe(new Action1<List<BooksBean>>() {
                                @Override
                                public void call(List<BooksBean> booksBeen) {
                                    CacheUtils.savebean(getActivity(), booksBeen, CacheUtils.DataCache_book, Constants.BOOKTITLE[position]);
                                }
                            });

                }
            }


        };



    }


    @Override
    public void onRecyclerViewListener() {
        mAdapter.setOnClickListener(new PageBookAdapter.OnItemClickListener() {


            @Override
            public void ItemClickListener(View view, String id, String img) {
                BookDetailsActivity.toActivity(getActivity(), id, img);
            }

            @Override
            public void ItemLongClickListener(View view, int postion) {

            }
        });
    }

    @Override
    public void onDestroy() {
        if (mListSubscriber!=null){
            mListSubscriber.unsubscribe();

        }

        super.onDestroy();
    }
}
