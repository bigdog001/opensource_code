package com.itheima.mobilesafe.utils;

import android.os.Handler;

public abstract class MyAsyncTask {
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			onPostExecute();
		};
	};
	/**
	 * ��̨����ִ��֮ǰ ��ʾ�û��Ľ������.
	 */
	public abstract void onPreExecute();
	
	/**
	 * ��̨����ִ��֮�� ���½���Ĳ���.
	 */
	public abstract void onPostExecute();
	
	/**
	 * �ں�ִ̨�е�һ����ʱ�Ĳ���.
	 */
	public abstract void doInBackground();
	public void execute(){
		//1. ��ʱ����ִ��֮ǰ֪ͨ�������
		onPreExecute();
		new Thread(){
			public void run() {
				doInBackground();
				handler.sendEmptyMessage(0);
			};
		}.start();
		
	}
}
