package com.bigdog.android.BTHeadSet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.bigdog.android.BTHeadSet.Util.Util;

/**
 * Created with IntelliJ IDEA.
 * User: jw362j
 * Date: 2/12/14
 * Time: 5:12 PM
 */
public class StateReciver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        Util.log("receive:"+intent.getType()+","+intent.getAction());
    }
}