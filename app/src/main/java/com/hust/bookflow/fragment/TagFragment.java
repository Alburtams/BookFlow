package com.hust.bookflow.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hust.bookflow.R;
import com.hust.bookflow.activity.MainActivity;
import com.hust.bookflow.fragment.base.BaseFragment;

/**
 * Created by Administrator on 2018/11/28 0028.
 */

public class TagFragment extends BaseFragment {
    private TextView tag_jy;
    private TextView tag_kj;
    private TextView tag_sh;
    private TextView tag_jg;
    private TextView tag_rwsk;
    private TextView tag_ts;
    private TextView tag_qcwx;
    private TextView tag_wy;
    private TextView tag_xs;

    private FragmentManager manager;
    private FragmentTransaction ft;
    private Toolbar main_toolbar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = getFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag, container, false);
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchTag(((TextView)v).getText().toString());
            }
        };
        tag_jy=(TextView) view.findViewById(R.id.tag_jy);
        tag_jy.setOnClickListener(onClickListener);
        tag_kj=(TextView) view.findViewById(R.id.tag_kj);
        tag_kj.setOnClickListener(onClickListener);
        tag_sh=(TextView) view.findViewById(R.id.tag_sh);
        tag_sh.setOnClickListener(onClickListener);
        tag_jg=(TextView) view.findViewById(R.id.tag_jg);
        tag_jg.setOnClickListener(onClickListener);
        tag_rwsk=(TextView) view.findViewById(R.id.tag_rwsk);
        tag_rwsk.setOnClickListener(onClickListener);
        tag_ts=(TextView) view.findViewById(R.id.tag_ts);
        tag_ts.setOnClickListener(onClickListener);
        tag_qcwx=(TextView) view.findViewById(R.id.tag_qcwx);
        tag_qcwx.setOnClickListener(onClickListener);
        tag_wy=(TextView) view.findViewById(R.id.tag_wy);
        tag_wy.setOnClickListener(onClickListener);
        tag_xs=(TextView) view.findViewById(R.id.tag_xs);
        tag_xs.setOnClickListener(onClickListener);

        return view;
    }

    private void switchTag(String title){
        MainActivity mainActivity=(MainActivity) getActivity();
        mainActivity.tagFragmentToList(title);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
