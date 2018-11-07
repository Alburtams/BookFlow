package com.hust.bookflow.fragment.factory;

import android.support.v4.app.Fragment;

import com.hust.bookflow.fragment.BookBackFragment;
import com.hust.bookflow.fragment.BookFragment;
import com.hust.bookflow.fragment.ChangeLabelFragment;
import com.hust.bookflow.fragment.CollectionFragment;
import com.hust.bookflow.fragment.HomeFragment;
import com.hust.bookflow.fragment.LeaderboardFragment;
import com.hust.bookflow.fragment.MovieFragment;
import com.hust.bookflow.fragment.base.BaseFragment;
import com.hust.bookflow.fragment.pagerfragment.BookPagerFragment;
import com.hust.bookflow.fragment.pagerfragment.MoviePagerFragment;
import com.hust.bookflow.utils.Constants;

/**
 * Created by ChinaLHR on 2016/12/13.
 * Email:13435500980@163.com
 */

public class FragmentFactory {
    public static Fragment getFragment(String title) {
        Fragment fragment = null;
        switch (title) {
            case Constants.HOME:
                fragment = new MoviePagerFragment();
                break;
            case Constants.BOOKBACK:
                fragment = new BookBackFragment();
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
