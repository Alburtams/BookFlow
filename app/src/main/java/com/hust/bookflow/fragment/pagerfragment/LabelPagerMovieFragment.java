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

/**
 * Created by ChinaLHR on 2016/12/23.
 * Email:13435500980@163.com
 */

public class LabelPagerMovieFragment extends Fragment {
    private RecyclerView pager_f_labelmovie;
    private List<String> mLabel;
    private List<String> oLabel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pagerfragment_labelm, container, false);
        pager_f_labelmovie = (RecyclerView) view.findViewById(R.id.pager_f_labelmovie);
        initdata();
        init();
        return view;
    }

    private void init() {

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        pager_f_labelmovie.setLayoutManager(manager);
        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(pager_f_labelmovie);



    }

    private void initdata() {
        mLabel = new ArrayList<>();
        oLabel = new ArrayList<>();
        for (int i = 0; i < Constants.MOVIETITLE.length; i++) {
            mLabel.add(Constants.MOVIETITLE[i]);
        }
        boolean flag = true;
        for(String s:Constants.ALLMOVIE){
            for(int i=0;i<Constants.MOVIETITLE.length;i++)
            {
                if(s.equals(Constants.MOVIETITLE[i])) {
                    flag = false;
                    break;
                }
                flag = true;
            }
            if (flag){oLabel.add(s);}
        }

    }

}
