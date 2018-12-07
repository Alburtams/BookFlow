package com.hust.bookflow.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.hust.bookflow.MyApplication;
import com.hust.bookflow.R;
import com.hust.bookflow.adapter.SearchBookAdapter;
import com.hust.bookflow.adapter.base.BaseSearchAdapter;
import com.hust.bookflow.model.bean.BookListBeans;
import com.hust.bookflow.model.httputils.BookFlowHttpMethods;
import com.hust.bookflow.utils.ToastUtils;
import com.hust.bookflow.utils.UserUtils;

import java.util.List;
import rx.Subscriber;

public class TagActivity extends AppCompatActivity {

    private final int COUNT = 10;
    private int mStart = 0;
    private String tag;

    private SearchBookAdapter mBookAdapter;
    private Subscriber<List<BookListBeans>> booksub;
    private List<BookListBeans> mBookBean;
    private RecyclerView.LayoutManager mLayoutManager;
    private View mFootView;
    private ProgressBar tagpb;
    private FloatingActionButton tagfab;
    private RecyclerView tagrv;
    private TextView tag_name;
    private SharedPreferences userData;
    private String stuId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        this.tagfab = (FloatingActionButton) findViewById(R.id.tag_fab);
        this.tagpb = (ProgressBar) findViewById(R.id.tag_pb);
        this.tagrv = (RecyclerView) findViewById(R.id.tag_rv);
        this.tag_name=(TextView)findViewById(R.id.tag_name);
        Intent intent=getIntent();
        if(intent!=null){
            tag=intent.getStringExtra("Tag");
            tag_name.setText(tag);
            if(tag.equals(getString(R.string.nav_menu_like))){
                userData = getSharedPreferences("userInfo",  Activity.MODE_PRIVATE);
                stuId = UserUtils.getStuID(userData);
                getLikeList();
            }else{
                getTags();
            }
            ToastUtils.show(TagActivity.this,tag);
        }
        initView();
        initListener();
    }

    private void getLikeList(){
        showProgressbar();
        booksub = new Subscriber<List<BookListBeans>>() {
            @Override
            public void onCompleted() {
                closeProgressbar();
            }
            @Override
            public void onError(Throwable e) {
                closeProgressbar();
                ToastUtils.show(TagActivity.this, "没有搜索到结果");
            }
            @Override
            public void onNext(List<BookListBeans> subjectsBeen) {
                if (!subjectsBeen.isEmpty()) {
                    mBookBean = subjectsBeen;
                    initRecyclerView();
                } else {
                    ToastUtils.show(TagActivity.this, "没有搜索到结果");
                }
            }
        };
        BookFlowHttpMethods.getInstance().getlikeList(booksub, stuId);
    }

    private void getTags(){
        showProgressbar();
        booksub = new Subscriber<List<BookListBeans>>() {
            @Override
            public void onCompleted() {
                closeProgressbar();
            }
            @Override
            public void onError(Throwable e) {
                closeProgressbar();
                ToastUtils.show(TagActivity.this, "没有搜索到结果");
            }
            @Override
            public void onNext(List<BookListBeans> subjectsBeen) {
                if (!subjectsBeen.isEmpty()) {
                    mBookBean = subjectsBeen;
                    initRecyclerView();
                } else {
                    ToastUtils.show(TagActivity.this, "没有搜索到结果");
                }
            }
        };
        BookFlowHttpMethods.getInstance().getTagBooks(booksub, tag,0,COUNT);
    }

    private void initListener() {
        tagrv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1) && MyApplication.isNetworkAvailable(TagActivity.this) && mBookBean.size() == 10) {
                    updateBook();
                    mFootView.setVisibility(View.VISIBLE);
                    tagrv.scrollToPosition(mBookAdapter.getItemCount() - 1);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        tagfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tagrv.scrollToPosition(0);
            }
        });
    }

    private void updateBook() {
        if (mBookAdapter.getStart() == mStart) return;
        mStart = mBookAdapter.getStart();
        booksub = new Subscriber<List<BookListBeans>>() {
            @Override
            public void onCompleted() {
                mFootView.setVisibility(View.GONE);
            }
            @Override
            public void onError(Throwable e) {
                mFootView.setVisibility(View.GONE);
            }
            @Override
            public void onNext(List<BookListBeans> list) {
                if (!list.isEmpty()) {
                    mBookAdapter.addData(list);
                }
            }
        };
        BookFlowHttpMethods.getInstance().getTagBooks(booksub, tag, mStart, COUNT);
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        tagrv.setLayoutManager(mLayoutManager);
        mFootView = LayoutInflater.from(this).inflate(R.layout.item_footer, tagrv, false);
    }

    private void initRecyclerView() {
        mBookAdapter = new SearchBookAdapter(this, mBookBean);
        mBookAdapter.setFooterView(mFootView);
        tagrv.setAdapter(mBookAdapter);
        mBookAdapter.setOnItemClickListener(new BaseSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String id, String url) {
                BookDetailsActivity.toActivity(TagActivity.this, id, url);
            }
        });
    }

    private void showProgressbar() {
        tagpb.setVisibility(View.VISIBLE);
    }

    private void closeProgressbar() {
        tagpb.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        if (booksub!=null) booksub.unsubscribe();
        super.onDestroy();
    }
}
