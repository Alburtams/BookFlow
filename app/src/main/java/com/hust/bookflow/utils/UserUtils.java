package com.hust.bookflow.utils;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018/11/2 0002.
 */

public class UserUtils {
    public static String getStuID(SharedPreferences preferences){
        String stuid=preferences.getString("account", "");
        return stuid;
    }
}
