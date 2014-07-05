package com.itheima.mobilesafe.utils;

import android.os.Handler;

public abstract class DelayExecuter {
	
	public Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			doInUiThread();
		};
	};
	
	/**
	 * ��ʱ֮�������߳�ִ�еķ���.
	 */
	public  abstract void doInUiThread();
	
	/**
	 * ��ʱ���ִ������
	 * @param time ��ʱ�ĺ���ֵ
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
