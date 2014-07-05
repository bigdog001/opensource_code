package com.bigdog.android.BTHeadSet.Util;

import android.util.Log;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jw362j
 * Date: 2/12/14
 * Time: 4:43 PM
 */
public class Util {
    private static boolean log_flag = true;

    public static void log(String logBody) {
        if (log_flag) {
            Log.i("att-android", logBody + ",in " + new Date() + ", " + System.currentTimeMillis());
        }
    }
}
