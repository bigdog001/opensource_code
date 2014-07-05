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
		Logger.i(TAG,"手机重启了....");
		Intent testIntent = new Intent(context,TestService.class);
		context.startService(testIntent);
		
		
		
		//检查手机 防盗是否开启.
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
		if(protect){//手机防盗开启了.
			//1.获取当前手机里面的sim卡串号.
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String realSim = tm.getSimSerialNumber();
			String savedSim = sp.getString("sim", "");
			if(savedSim.equals(realSim)){//sim没有变化...
				
			}else{
				String address = sp.getString("safenumber", "");
				//发送报警短信
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(address, null, "sim changed", null, null);
			}
		}
	}

}
