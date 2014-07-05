package com.itheima.mobilesafe.receiver;

import com.itheima.mobilesafe.LostFindActivity;
import com.itheima.mobilesafe.db.dao.AddressDao;
import com.itheima.mobilesafe.service.CallSmsFirewallService;
import com.itheima.mobilesafe.service.CallSmsSafeService;
import com.itheima.mobilesafe.utils.ServiceStatusUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.itheima.mobilesafe.utils.Logger;
import android.widget.Toast;

public class OutCallReceiver extends BroadcastReceiver {

	private static final String TAG = "OutCallReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		String number = getResultData();
		Logger.i(TAG, "�����Ⲧ�绰:" + number);
		if ("20182018".equals(number)) {
			Intent loatFindIntent = new Intent(context, LostFindActivity.class);
			loatFindIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(loatFindIntent);
			setResultData(null);// �����Ⲧ�ĵ绰����Ϊ��.
		}
		SharedPreferences sp = context.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		boolean showaddress = sp.getBoolean("showaddress", false);
		boolean firewall = sp.getBoolean("firewall", false);
		// �������״̬ ,�������û�п��� �Ϳ�����.
		if (showaddress) {
			if (ServiceStatusUtil.isServiceRunning(context,
					"com.itheima.mobilesafe.service.CallSmsSafeService")) {
				Logger.i(TAG, "������������...����Ҫ����");
			} else {
				context.startService(new Intent(context,
						CallSmsSafeService.class));
			}
		}
		if (firewall) {
			if (ServiceStatusUtil.isServiceRunning(context,
					"com.itheima.mobilesafe.service.CallSmsFirewallService")) {
				Logger.i(TAG, "������������...����Ҫ����");
			} else{
			context.startService(new Intent(context,
					CallSmsFirewallService.class));
			}
		}

	}

}
