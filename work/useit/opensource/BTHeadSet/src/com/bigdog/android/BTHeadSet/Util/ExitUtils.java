package com.bigdog.android.BTHeadSet.Util;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jw362j
 * Date: 2/12/14
 * Time: 4:20 PM
 */
public class ExitUtils {
    private List<Activity> activityList=new LinkedList<Activity>();
    private static ExitUtils exitUtils;
    private  ExitUtils(){

    }

    public static ExitUtils getExitUtilsInstance() {
        if (exitUtils == null) {
            exitUtils = new ExitUtils();
        }
        return exitUtils;
    }

    public void addActivity(Activity activity){
        activityList.add(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}
