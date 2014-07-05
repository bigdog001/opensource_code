package com.itheima.mobilesafe.utils;

import android.os.Handler;

public abstract class DelayExecuter {
	
	public Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			doInUiThread();
		};
	};
	
	/**
	 * 延时之后在主线程执行的方法.
	 */
	public  abstract void doInUiThread();
	
	/**
	 * 延时多久执行任务
	 * @param time 延时的毫秒值
	 */
	public void delayExectue(final long time){
		new Thread(){
			public void run() {
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendEmptyMessage(0);
			};
		}.start();
	}
}
