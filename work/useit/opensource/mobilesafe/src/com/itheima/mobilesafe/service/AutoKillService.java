package com.itheima.mobilesafe.service;

import com.itheima.mobilesafe.receiver.LockScreenReceiver;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class AutoKillService extends Service {
	private LockScreenReceiver recevier;
	
	
	
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		recevier = new LockScreenReceiver();
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		registerReceiver(recevier, filter);
		
		super.onCreate();
	}
	
	
	public void onDestroy() {
		unregisterReceiver(recevier);
		recevier = null;
	};
}
