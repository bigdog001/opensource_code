package com.itheima.mobilesafe.service;

import java.util.Timer;
import java.util.TimerTask;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.receiver.MyWidget;
import com.itheima.mobilesafe.utils.ProcessStatusUtils;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.Formatter;
import android.widget.RemoteViews;

public class UpdateWidgetService extends Service {
	private Timer timer;
	private TimerTask task;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// �������ڵ��������widget.
		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				AppWidgetManager awm = AppWidgetManager
						.getInstance(getApplicationContext());
				ComponentName component = new ComponentName(
						getApplicationContext(), MyWidget.class);
				RemoteViews views = new RemoteViews(getPackageName(),
						R.layout.process_widget);
				views.setTextViewText(
						R.id.process_count,
						"��������:"
								+ ProcessStatusUtils
										.getProcessCount(getApplicationContext())
								+ "��");
				views.setTextViewText(
						R.id.process_memory,
						"�����ڴ�:"
								+ Formatter
										.formatFileSize(
												getApplicationContext(),
												ProcessStatusUtils
														.getAvailRAM(getApplicationContext())));
				Intent intent = new Intent();
				intent.setAction("com.itheima.killall");
				//����һ���Զ���Ĺ㲥�¼� ����  com.itheima.killall
				PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
				views.setOnClickPendingIntent(R.id.btn_clear, pendingIntent);
				
				
				awm.updateAppWidget(component, views);

			}
		};
		timer.schedule(task, 1000, 2000);

	}
	@Override
	public void onDestroy() {
		timer.cancel();
		task.cancel();
		timer = null;
		task = null;
		super.onDestroy();
	}
}
