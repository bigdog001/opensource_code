package com.itheima.mobilesafe;

import com.itheima.mobilesafe.domain.BlackNumberInfo;

import android.app.Application;

/**
 * 代表的是当前应用程序的进程.
 * @author Administrator
 *
 */
public class MobliesafeApplication extends Application {
	public BlackNumberInfo info;
	
	@Override
	public void onCreate() {
		super.onCreate();
		Thread.currentThread().setUncaughtExceptionHandler(new MyExceptionHandler());
		
	}
}
