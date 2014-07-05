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
		//开启服务定期的更新界面.
		Intent intent = new Intent(context,UpdateWidgetService.class);
		context.startService(intent);
		super.onEnabled(context);
	}
	
	@Override
	public void onDisabled(Context context) {
		//关闭掉服务
		Intent intent = new Intent(context,UpdateWidgetService.class);
		context.stopService(intent);
		super.onDisabled(context);
	}
	
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		//最短半个小时执行一次.
		//检查下 服务是否还活着.
		if(!ServiceStatusUtil.isServiceRunning(context, "com.itheima.mobilesafe.service.UpdateWidgetService")){
			Intent intent = new Intent(context,UpdateWidgetService.class);
			context.startService(intent);
		}
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
}
