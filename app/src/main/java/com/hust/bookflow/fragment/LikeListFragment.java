package com.hust.bookflow.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hust.bookflow.R;
import com.hust.bookflow.activity.BookDetailsActivity;
import com.hust.bookflow.adapter.SearchBookAdapter;
import com.hust.bookflow.adapter.base.BaseSearchAdapter;
import com.hust.bookflow.fragment.base.BaseFragment;
import com.hust.bookflow.model.bean.BookListBeans;
import com.hust.bookflow.model.httputils.BookFlowHttpMethods;
import com.hust.bookflow.utils.ToastUtils;
import com.hust.bookflow.utils.UserUtils;

import java.util.List;

import rx.Subscriber;

/**
 * Created by 文辉 on 2018/10/31.
 */

public class LikeListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private SearchBookAdapter likelistAdapter;
    private RecyclerView likelistrv;

    private Subscriber<List<BookListBeans>> borrowedBookSub;
    private List<BookListBeans> mBookBean;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar likelistpb;
    public SwipeRefreshLayout likelistFresh;
    private SharedPreferences userData;
    private String stuId;
    private String tag;
    private Subscriber<List<BookListBeans>> newBorrowedBookSub;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_likelist, container, false);
        this.likelistrv = (RecyclerView) view.findViewById(R.id.book_likelist_rv);
        this.likelistpb = (ProgressBar) view.findViewById(R.id.likelist_pb);
        this.likelistFresh = (SwipeRefreshLayout) view.findViewById(R.id.likelist_fresh);
        //获取当前登录用户的信息
        userData = getActivity().getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        stuId= UserUtils.getStuID(userData);
        tag=userData.getString("tag","");
        if(tag.equals(getString(R.string.nav_menu_like))){
            loadData();
        }else{
            getTags();
        }
        initView();
        initListener();
        return view;
    }

    private void getTags(){
        showProgressbar();
        borrowedBookSub = new Subscriber<List<BookListBeans>>() {
            @Override
            public void onCompleted() {
                closeProgressbar();
            }
            @Override
            public void onError(Throwable e) {
                closeProgressbar();
                ToastUtils.show(getActivity(), "该分类下还没有图书");
            }
            @Override
            public void onNext(List<BookListBeans> subjectsBeen) {
                if (!subjectsBeen.isEmpty()) {
                    mBookBean = subjectsBeen;
                    initRecyclerView();
                } else {
                    ToastUtils.show(getActivity(), "该分类下还没有图书");
                }
            }
        };
        BookFlowHttpMethods.getInstance().getTagBooks(borrowedBookSub, tag,0,20);
    }

    private void loadData() {
        showProgressbar();
        borrowedBookSub = new Subscriber<List<BookListBeans>>() {
            @Override
            public void onCompleted() {
                closeProgressbar();
            }

            @Override
            public void onError(Throwable e) {
                closeProgressbar();
                ToastUtils.show(getActivity(), "查询想看书籍列表错误");
            }
            @Override
            public void onNext(List<BookListBeans> bookListBeanses) {
                if (!bookListBeanses.isEmpty()) {
                    mBookBean = bookListBeanses;
                    initRecyclerView();
                } else {
                    ToastUtils.show(getActivity(), "当前还未有想看图书");
                }
            }
        };

        BookFlowHttpMethods.getInstance().getlikeList(borrowedBookSub, stuId);
    }

    private void initRecyclerView() {
        likelistAdapter = new SearchBookAdapter(getActivity(), mBookBean);
        likelistrv.setAdapter(likelistAdapter);
        likelistAdapter.setOnItemClickListener(new BaseSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String id, String url) {
                BookDetailsActivity.toActivity(getActivity(), id, url);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private void initListener() {
        /**
         * 顶部下拉松开时会调用这个方法
         */
        likelistFresh.setOnRefreshListener(this);
    }

    private void updateData() {
        likelistFresh.setRefreshing(true);
        newBorrowedBookSub = new Subscriber<List<BookListBeans>>() {
            @Override
            public void onCompleted() {
                likelistFresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.show(getActivity(), "更新失败");
                likelistFresh.setRefreshing(false);
            }

            @Override
            public void onNext(List<BookListBeans> bookListBeanses) {
                if (!bookListBeanses.isEmpty()) {
                    ToastUtils.show(getActivity(), "更新成功");
                } else {
                    ToastUtils.show(getActivity(), "当前还未有想看图书");
                }
                mBookBean = bookListBeanses;
                initRecyclerView();
            }
        };

        BookFlowHttpMethods.getInstance().getlikeList(newBorrowedBookSub, stuId);
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        likelistrv.setLayoutManager(mLayoutManager);
    }

    private void showProgressbar() {
        likelistpb.setVisibility(View.VISIBLE);
    }

    private void closeProgressbar() {
        likelistpb.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        if(borrowedBookSub != null) {
            borrowedBookSub.unsubscribe();
        }
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        updateData();
    }
}
