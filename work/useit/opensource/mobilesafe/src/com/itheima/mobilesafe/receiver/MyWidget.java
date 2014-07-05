package com.itheima.mobilesafe.receiver;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import com.itheima.mobilesafe.service.UpdateWidgetService;
import com.itheima.mobilesafe.utils.ServiceStatusUtil;

public class MyWidget extends AppWidgetProvider {
	@Override
	public void onEnabled(Context context) {
		//���������ڵĸ��½���.
		Intent intent = new Intent(context,UpdateWidgetService.class);
		context.startService(intent);
		super.onEnabled(context);
	}
	
	@Override
	public void onDisabled(Context context) {
		//�رյ�����
		Intent intent = new Intent(context,UpdateWidgetService.class);
		context.stopService(intent);
		super.onDisabled(context);
	}
	
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		//��̰��Сʱִ��һ��.
		//����� �����Ƿ񻹻���.
		if(!ServiceStatusUtil.isServiceRunning(context, "com.itheima.mobilesafe.service.UpdateWidgetService")){
			Intent intent = new Intent(context,UpdateWidgetService.class);
			context.startService(intent);
		}
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
}
