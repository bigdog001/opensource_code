package com.lee.tuliao.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TuliaoReceiver extends BroadcastReceiver {

	private static final String ACTION = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if (action.equals(ACTION)) {
			Intent service = new Intent();
			service.setAction("com.service.TuliaoService");
			context.startService(service);
		}
	}

}
