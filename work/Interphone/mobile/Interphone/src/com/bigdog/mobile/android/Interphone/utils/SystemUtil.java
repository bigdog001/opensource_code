package com.bigdog.mobile.android.Interphone.utils;

import android.util.Log;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/22/13
 * Time: 11:40 AM
 */
public class SystemUtil {
    private static boolean log_flag = true;

    public static void log(String logBody) {
        if (log_flag) {
            Log.d("wjn", logBody + ",in " + new Date() + ", " + System.currentTimeMillis());
        }
    }

}
