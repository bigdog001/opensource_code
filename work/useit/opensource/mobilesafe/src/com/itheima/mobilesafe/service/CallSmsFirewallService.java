package com.itheima.mobilesafe.service;

import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;
import com.itheima.mobilesafe.CallSmsSafeActivity;
import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.db.dao.BlackNumberDao;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import com.itheima.mobilesafe.utils.Logger;

public class CallSmsFirewallService extends Service {
	public static final String TAG = "CallSmsFirewallService";
	private InnerSmsReceiver receiver;
	private BlackNumberDao dao;
	private TelephonyManager tm;
	private PhoneListener listener;

	private class InnerSmsReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Object[] objs = (Object[]) intent.getExtras().get("pdus");
			for (Object obj : objs) {
				SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
				String sender = smsMessage.getOriginatingAddress();
				// �ж�sender�Ƿ��ں��������ݿ� ���� ���� ����Ҫ�ж�����ģʽ ȫ������ ��������.
				String body = smsMessage.getMessageBody();
				if (body.contains("fapiao")) {
					Logger.i(TAG, "���ص��� ��Ʊ����...");
					abortBroadcast();
				}

				String mode = dao.findMode(sender);
				if ("1".equals(mode) || "3".equals(mode)) {
					Logger.i(TAG, "���ص��� ����������...");
					abortBroadcast();
					// �Ѷ��ŵ����� �� ������ ��������.
				}
			}
		}

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		dao = new BlackNumberDao(this);
		receiver = new InnerSmsReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(Integer.MAX_VALUE);
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(receiver, filter);
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		listener = new PhoneListener();
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("firewall", false);
		editor.commit();
		unregisterReceiver(receiver);
		receiver = null;
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
		super.onDestroy();
	}

	private class PhoneListener extends PhoneStateListener {
		long startTime;

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {

			case TelephonyManager.CALL_STATE_IDLE: // ����״̬
				long endTime = System.currentTimeMillis();
				long dTime = endTime - startTime;
				if (dTime < 3000) {
					// ����һ����һ���ĺ���, ֪ͨ�û�.
					// �ж���������Ƿ��ں��������ݿ�����.
					String mode = dao.findMode(incomingNumber);
					if ("1".equals(mode) || "2".equals(mode)) {
					} else {
						showNotification(incomingNumber);//���Ǻ��������� ������������һ������.
					}
				}

				break;

			case TelephonyManager.CALL_STATE_RINGING:
				// ��¼���ֻ�������һ�����ʱ��.
				startTime = System.currentTimeMillis();
				String mode = dao.findMode(incomingNumber);
				if ("1".equals(mode) || "2".equals(mode)) {
					Logger.i(TAG, "�Ҷϵ绰...");
					endCall(incomingNumber);// ���м�¼�����������ɵ� .
					// deleteCallLog(incomingNumber);
					// ע��һ�����м�¼���ݱ仯�����ݹ۲���.
					Uri uri = Uri.parse("content://call_log/calls");
					getContentResolver().registerContentObserver(uri, true,
							new CallLogObserver(new Handler(), incomingNumber));

				}
				break;
			}

			super.onCallStateChanged(state, incomingNumber);
		}

	}

	private class CallLogObserver extends ContentObserver {
		private String incomingNumber;

		public CallLogObserver(Handler handler, String incomingNumber) {
			super(handler);
			this.incomingNumber = incomingNumber;
		}

		// ���۲쵽���ݷ����ı��ʱ����õķ���.
		@Override
		public void onChange(boolean selfChange) {
			Logger.i(TAG, "���м�¼�仯��.");
			deleteCallLog(incomingNumber);
			getContentResolver().unregisterContentObserver(this);
			super.onChange(selfChange);
		}

	}

	/**
	 * �Ҷϵ绰�ķ���.
	 * 
	 * @param incomingNumber
	 */
	public void endCall(String incomingNumber) {
		try {

			// Object obj = getSystemService(TELEPHONY_SERVICE);
			// ITelephony iTelephony = ITelephony.Stub.asInterface((IBinder)
			// obj);
			Method method = Class.forName("android.os.ServiceManager")
					.getMethod("getService", String.class);
			IBinder binder = (IBinder) method.invoke(null,
					new Object[] { TELEPHONY_SERVICE });
			ITelephony iTelephony = ITelephony.Stub.asInterface(binder);
			iTelephony.endCall();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ʾ��ʾ�û�����һ����� notification
	 * 
	 * @param incomingNumber
	 */
	public void showNotification(String incomingNumber) {

		// 1.��ȡ��notificationmanager
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// 2.ʵ����notification
		Notification notification = new Notification(R.drawable.notification,
				"��������һ�������:" + incomingNumber, System.currentTimeMillis());
		// 3.����notification�ľ������ �� ������ͼ pendingIntent
		notification.flags = Notification.FLAG_AUTO_CANCEL;// ����notification�Ƿ�ǿռ������.
		Intent intent = new Intent(this, CallSmsSafeActivity.class);
		intent.putExtra("number", incomingNumber);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(this, "���ص���һ������:", "����Ϊ:"
				+ incomingNumber, contentIntent);
		// 4.��ʾnotification
		nm.notify(0, notification);

	}

	/**
	 * �Ƴ����м�¼
	 * 
	 * @param incomingNumber
	 */
	public void deleteCallLog(String incomingNumber) {
		Uri uri = Uri.parse("content://call_log/calls");
		getContentResolver().delete(uri, "number=?",
				new String[] { incomingNumber });
	}

}
