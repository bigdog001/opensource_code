package com.itheima.mobilesafe.service;

import com.itheima.mobilesafe.utils.Logger;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.SmsMessage;

public class TestService extends Service {

	private com.itheima.mobilesafe.service.TestService.InnerSmsReceiver receiver;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		receiver = new InnerSmsReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(Integer.MAX_VALUE);
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(receiver, filter);
		System.out.println("���񱻿�����.");
		super.onCreate();
	}
	private class InnerSmsReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Object[] objs = (Object[]) intent.getExtras().get("pdus");
			for (Object obj : objs) {
				SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
				//String sender = smsMessage.getOriginatingAddress();
				// �ж�sender�Ƿ��ں��������ݿ� ���� ���� ����Ҫ�ж�����ģʽ ȫ������ ��������.
				String body = smsMessage.getMessageBody();
				if (body.contains("fapiao")) {
					Logger.i("InnerSmsReceiver", "���ص��� ��Ʊ����------...");
					abortBroadcast();
				}

			
			}
		}

	}
	@Override
	public void onDestroy() {
		unregisterReceiver(receiver);
		receiver = null;
		super.onDestroy();
	}
}
