package com.itheima.mobilesafe.utils;

import android.os.Handler;

public abstract class MyAsyncTask {
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			onPostExecute();
		};
	};
	/**
	 * 后台任务执行之前 提示用户的界面操作.
	 */
	public abstract void onPreExecute();
	
	/**
	 * 后台任务执行之后 更新界面的操作.
	 */
	public abstract void onPostExecute();
	
	/**
	 * 在后台执行的一个耗时的操作.
	 */
	public abstract void doInBackground();
	public void execute(){
		//1. 耗时任务执行之前通知界面更新
		onPreExecute();
		new Thread(){
			public void run() {
				doInBackground();
				handler.sendEmptyMessage(0);
			};
		}.start();
		
	}
}
