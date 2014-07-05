package com.itheima.mobilesafe.receiver;

import java.util.List;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.utils.MyToast;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.itheima.mobilesafe.utils.Logger;
import android.widget.Toast;

public class CustomBroadcastReceiver extends BroadcastReceiver {

	private static final String TAG = "CustomBroadcastReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Logger.i(TAG,"�����Զ���Ĺ㲥������,�ҽ��յ����Զ���Ĺ㲥�¼�");
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> infos = am.getRunningAppProcesses();
		for(RunningAppProcessInfo info :infos){
			am.killBackgroundProcesses(info.processName);
		}
		MyToast.show(context, R.drawable.notification, "�������");
	}

}
