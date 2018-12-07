package com.hust.bookflow.fragment.factory;

import android.support.v4.app.Fragment;

import com.hust.bookflow.fragment.BookBackFragment;
import com.hust.bookflow.fragment.BookFragment;
import com.hust.bookflow.fragment.CollectionFragment;
import com.hust.bookflow.fragment.HomeFragment;
import com.hust.bookflow.fragment.TagFragment;
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
                fragment = new BookFragment();
                break;
            case Constants.BOOKBACK:
                fragment = new BookBackFragment();
                break;
            case Constants.TAG:
                fragment = new TagFragment();
                break;
            case Constants.COLLECTION:
                fragment = new CollectionFragment();
                break;
            case Constants.CHANGESECTION:

                break;
        }
        return fragment;
    }
}
