package com.itheima.mobilesafe.receiver;

import com.itheima.mobilesafe.service.CallSmsFirewallService;
import com.itheima.mobilesafe.service.CallSmsSafeService;
import com.itheima.mobilesafe.service.TestService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import com.itheima.mobilesafe.utils.Logger;

public class BootCompleteReceiver extends BroadcastReceiver {

	private static final String TAG = "BootCompleteReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Logger.i(TAG,"�ֻ�������....");
		Intent testIntent = new Intent(context,TestService.class);
		context.startService(testIntent);
		
		
		
		//����ֻ� �����Ƿ���.
		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		boolean showaddress = sp.getBoolean("showaddress", false);
		boolean firewall = sp.getBoolean("firewall", false);
		if(showaddress){
			context.startService(new Intent(context,CallSmsSafeService.class));
		}
		if(firewall){
			context.startService(new Intent(context,CallSmsFirewallService.class));
		}
		boolean protect = sp.getBoolean("protect", false);
		if(protect){//�ֻ�����������.
			//1.��ȡ��ǰ�ֻ������sim������.
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String realSim = tm.getSimSerialNumber();
			String savedSim = sp.getString("sim", "");
			if(savedSim.equals(realSim)){//simû�б仯...
				
			}else{
				String address = sp.getString("safenumber", "");
				//���ͱ�������
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(address, null, "sim changed", null, null);
			}
		}
	}

}
