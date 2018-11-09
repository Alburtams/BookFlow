package com.hust.bookflow.fragment.pagerfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hust.bookflow.R;
import com.hust.bookflow.adapter.helper.ItemDragHelperCallback;
import com.hust.bookflow.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.hust.bookflow.R.id.pager_f_labelmovie;

/**
 * Created by ChinaLHR on 2016/12/23.
 * Email:13435500980@163.com
 */

public class LabelPagerBookFragment extends Fragment {
    private RecyclerView pager_f_labelbook;
    private List<String> mLabel;
    private List<String> oLabel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pagerfragment_labelm, container, false);
        pager_f_labelbook = (RecyclerView) view.findViewById(pager_f_labelmovie);
        initdata();
        init();
        return view;
    }

    private void init() {

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        pager_f_labelbook.setLayoutManager(manager);
        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(pager_f_labelbook);


        //设置头布局占四个span，其他占一个

    }


    private void initdata() {
        mLabel = new ArrayList<>();
        oLabel = new ArrayList<>();
        for (int i = 0; i < Constants.BOOKTITLE.length; i++) {
            mLabel.add(Constants.BOOKTITLE[i]);
        }
        boolean flag = true;
        for(String s:Constants.ALLBOOK){
            for(int i=0;i<Constants.BOOKTITLE.length;i++)
            {
                if(s.equals(Constants.BOOKTITLE[i])) {
                    flag = false;
                    break;
                }
                flag = true;
            }
            if (flag){oLabel.add(s);}
        }

    }
}
