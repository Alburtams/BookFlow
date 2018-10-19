package com.hust.bookflow.fragment.factory;

import com.hust.bookflow.fragment.BookFragment;
import com.hust.bookflow.fragment.ChangeLabelFragment;
import com.hust.bookflow.fragment.CollectionFragment;
import com.hust.bookflow.fragment.LeaderboardFragment;
import com.hust.bookflow.fragment.MovieFragment;
import com.hust.bookflow.fragment.base.BaseFragment;
import com.hust.bookflow.utils.Constants;

/**
 * Created by ChinaLHR on 2016/12/13.
 * Email:13435500980@163.com
 */

public class FragmentFactory {
    public static BaseFragment getFragment(String title) {
        BaseFragment fragment = null;
        switch (title) {
            case Constants.HOME:
                fragment = new MovieFragment();
                break;
            case Constants.BOOKBACK:
                fragment = new BookFragment();
                break;
            case Constants.HistoryList:
                fragment = new LeaderboardFragment();
                break;
            case Constants.COLLECTION:
                fragment = new CollectionFragment();
                break;
            case Constants.CHANGESECTION:
                fragment = new ChangeLabelFragment();
                break;
        }
        return fragment;
    }
}
