package com.itheima.mobilesafe.receiver;

import java.util.List;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.itheima.mobilesafe.utils.Logger;

public class LockScreenReceiver extends BroadcastReceiver {

	private static final String TAG = "LockScreenReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Logger.i(TAG,"屏幕锁屏了");
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo>  infos = am.getRunningAppProcesses();
		for(RunningAppProcessInfo info: infos){
			//判断 包名是否在白名单
			am.killBackgroundProcesses(info.processName);
		}
		
		
//		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//		alarmManager.setRepeating(type, triggerAtTime, interval, operation)
		
	}

}
