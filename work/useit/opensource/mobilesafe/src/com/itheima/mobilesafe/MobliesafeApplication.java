package com.itheima.mobilesafe;

import com.itheima.mobilesafe.domain.BlackNumberInfo;

import android.app.Application;

/**
 * ������ǵ�ǰӦ�ó���Ľ���.
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
