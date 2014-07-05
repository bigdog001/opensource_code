package com.itheima.mobilesafe.receiver;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.engine.GPSInfoProvider;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import com.itheima.mobilesafe.utils.Logger;

public class SmsReceiver extends BroadcastReceiver {

	private static final String TAG = "SmsReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {

		Object[] objs = (Object[]) intent.getExtras().get("pdus");
		for (Object obj : objs) {
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
			String body = smsMessage.getMessageBody();
			String sender = smsMessage.getOriginatingAddress();
			if ("#*loaction*#".equals(body)) {
				abortBroadcast();// ��ֹ���ŵĹ㲥�¼�.
				Logger.i(TAG, "�����ֻ���λ��.");
				String lastloaction = GPSInfoProvider.getInstance(context)
						.getPhoneLocation();
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(sender, null, "loaction:"
						+ lastloaction, null, null);
			} else if ("#*alarm*#".equals(body)) {
				abortBroadcast();// ��ֹ���ŵĹ㲥�¼�.
				MediaPlayer player = MediaPlayer.create(context, R.raw.ylzs);
				player.setVolume(1.0f, 1.0f);
				player.start();

				Logger.i(TAG, "���ű�������.");
			} else if ("#*wipedate*#".equals(body)) {
				abortBroadcast();// ��ֹ���ŵĹ㲥�¼�.
				Logger.i(TAG, "�������.");
				// �ж� ��ǰӦ�ó����Ƿ��Ѿ��õ��˳�������Ա��Ȩ��

				DevicePolicyManager dpm = (DevicePolicyManager) context
						.getSystemService(Context.DEVICE_POLICY_SERVICE);
				if (dpm.isAdminActive(new ComponentName(context, MyAdmin.class)))
					dpm.wipeData(0);

			} else if ("#*lockscreen*#".equals(body)) {
				abortBroadcast();// ��ֹ���ŵĹ㲥�¼�.
				Logger.i(TAG, "Զ������.");
				DevicePolicyManager dpm = (DevicePolicyManager) context
						.getSystemService(Context.DEVICE_POLICY_SERVICE);
				if (dpm.isAdminActive(new ComponentName(context, MyAdmin.class))) {
					dpm.resetPassword("321", 0);
					dpm.lockNow();
				}else{
					//todo:
				}
			}
		}

	}

}
