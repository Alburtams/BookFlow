package com.hust.bookflow.fragment;

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
import com.hust.bookflow.activity.LoginActivity;
import com.hust.bookflow.activity.SearchActivity;
import com.hust.bookflow.adapter.BookBackAdapter;
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

public class BookBackFragment extends BaseFragment {

    private BookBackAdapter backAdapter;
    private RecyclerView backrv;

    private Subscriber<List<BookListBeans>> borrowedBookSub;
    private Subscriber<Boolean> backBookSub;
    private List<BookListBeans> mBookBean;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar backpb;
    public SwipeRefreshLayout backFresh;

    private SharedPreferences userData;
    private String stuId;

    private boolean isBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_back, container, false);
        this.backrv = (RecyclerView) view.findViewById(R.id.book_back_rv);
        this.backpb = (ProgressBar) view.findViewById(R.id.back_pb);
        //获取当前登录用户的信息
        getUser();
        initView();
//        initListener();
        loadData();
        return view;
    }

    private void getUser() {
        userData = getActivity().getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        stuId= UserUtils.getStuID(userData);
        if (stuId.equals("")) {
            ToastUtils.show(getActivity(), "请先登录");
            LoginActivity.toActivity(getActivity());
            getActivity().finish();
        }
    }

    private void loadData() {
//        backFresh.setRefreshing(true);
        showProgressbar();
        borrowedBookSub = new Subscriber<List<BookListBeans>>() {
            @Override
            public void onCompleted() {
                closeProgressbar();
//                backFresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                closeProgressbar();
                ToastUtils.show(getActivity(), "查询已借阅书籍错误");
//                backFresh.setRefreshing(false);
            }

            @Override
            public void onNext(List<BookListBeans> bookListBeanses) {
                if (!bookListBeanses.isEmpty()) {
                    mBookBean = bookListBeanses;
                    initRecyclerView();
                } else {
                    ToastUtils.show(getActivity(), "当前还未借阅图书");
                }
            }
        };

        BookFlowHttpMethods.getInstance().getBorrowed(borrowedBookSub, stuId);
    }

    private void initRecyclerView() {
        backAdapter = new BookBackAdapter(getActivity(), mBookBean);
        backrv.setAdapter(backAdapter);
        backAdapter.setOnItemClickListener(new BookBackAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(String id, String url) {
                BookDetailsActivity.toActivity(getActivity(), id, url);
            }

            @Override
            public void onBtnClick(String id) {
                // TODO
                //获取当前登录学生学号
                backBook(id, stuId); //还书操作
                if(isBack) {
                    ToastUtils.show(getActivity(), "还书成功");
                } else {
                    ToastUtils.show(getActivity(), "还书失败");
                }
            }
        });
    }

    private void backBook(String bookId, String stuId) {
        // ToDo 还书函数
        backBookSub = new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                isBack = false;
                ToastUtils.show(getActivity(), "还书失败");
            }

            @Override
            public void onNext(Boolean aBoolean) {
                isBack = aBoolean;
                if(!aBoolean) {
                    ToastUtils.show(getActivity(), "还书成功");
                }
            }
        };

        BookFlowHttpMethods.getInstance().returnBook(backBookSub, bookId, stuId);
    }

    private void initListener() {
        /**
         * 顶部下拉松开时会调用这个方法
         */
        backFresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        backrv.setLayoutManager(mLayoutManager);
    }

    private void showProgressbar() {
        backpb.setVisibility(View.VISIBLE);
    }

    private void closeProgressbar() {
        backpb.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        if(borrowedBookSub != null) {
            borrowedBookSub.unsubscribe();
        }
        super.onDestroy();
    }
}
