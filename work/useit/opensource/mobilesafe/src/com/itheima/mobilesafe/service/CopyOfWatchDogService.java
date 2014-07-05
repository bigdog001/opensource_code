package com.itheima.mobilesafe.service;

import java.util.ArrayList;
import java.util.List;

import com.itheima.mobilesafe.EnterPwdActivity;
import com.itheima.mobilesafe.db.dao.AppLockDao;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import com.itheima.mobilesafe.utils.Logger;

public class CopyOfWatchDogService extends Service {
	protected static final String TAG = "WatchDogService";
	private ActivityManager am;
	private AppLockDao dao;
	private Intent intent;
	private boolean flag;
	private List<String> tempStopProtectPackNames;
	private InnerReceiver receiver;
	private InnerScreenLockReceiver lockReceiver;
	
	private class InnerScreenLockReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			tempStopProtectPackNames.clear();
		}
		
	}
	
	private class InnerReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			Logger.i(TAG,"���ܵ����Զ���Ĺ㲥�¼�");
			String packname = intent.getStringExtra("packname");
			tempStopProtectPackNames.add(packname);
		}
		
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		dao = new AppLockDao(this);
		tempStopProtectPackNames = new ArrayList<String>();
		
		receiver = new InnerReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.itheima.stopprotect");
		registerReceiver(receiver, filter);
		
		
		lockReceiver = new InnerScreenLockReceiver();
		IntentFilter lockFilter = new IntentFilter();
		lockFilter.setPriority(1000);
		lockFilter.addAction(Intent.ACTION_SCREEN_OFF);
		registerReceiver(lockReceiver, lockFilter);
		
		
		intent = new Intent(this, EnterPwdActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		new Thread() {
			public void run() {
				flag = true;
				while (flag) {
					// ����һ�����Ź� ��ͣ�ļ��ӵ�ǰ�û����ڴ򿪵�Ӧ�ó���.
					am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
					List<RunningTaskInfo> infos = am.getRunningTasks(1);// �����ص�
																		// ��������򿪵������ڼ��ϵ���ǰ��.
					RunningTaskInfo taskInfo = infos.get(0);// ����򿪵�����ջ����Ϣ
					String packname = taskInfo.topActivity.getPackageName();// �õ����һ���û��򿪵�Ӧ�ó���İ���.
					Logger.i(TAG, "packname:" + packname);
					// �ж���������Ƿ���һ�������İ���.
					if (dao.find(packname)) {
						// �ж����Ӧ�ó����Ƿ���Ҫ��ʱ��ȡ������.
						if (tempStopProtectPackNames.contains(packname)) {
							//��ʱ��ֹͣ���� ʲô���鶼����.
							
							
						} else {
							// �ĳ��� ,������������Ľ���.
							intent.putExtra("packname", packname);
							startActivity(intent);
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();

		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("applock", false);
		editor.commit();
		flag = false;
		unregisterReceiver(receiver);
		receiver = null;
		unregisterReceiver(lockReceiver);
		lockReceiver = null;
	}

}
