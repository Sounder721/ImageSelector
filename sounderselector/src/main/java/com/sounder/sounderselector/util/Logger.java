package com.sounder.sounderselector.util;

import android.text.TextUtils;
import android.util.Log;

/**
 *  Created by sounder on 2017/5/12.
 */

public class Logger {
    private static boolean DEBUG = true;
    private static String TAG = "SOUNDER";

    private static boolean show(String msg){
        return DEBUG && !TextUtils.isEmpty(msg);
    }
    public static void i(int msg){
        i(String.valueOf(msg));
    }
    public static void i(String msg){
        if(show(msg)){
            Log.i(TAG, msg);
        }
    }
    public static void w(String msg){
        if(show(msg)){
            Log.w(TAG, msg);
        }
    }
    public static void d(String msg){
        if(show(msg)){
            Log.d(TAG, msg);
        }
    }
    public static void e(String msg){
        if(show(msg)){
            Log.e(TAG, msg);
        }
    }
}
