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
				// 判断sender是否在黑名单数据库 里面 并且 还需要判断拦截模式 全部拦截 短信拦截.
				String body = smsMessage.getMessageBody();
				if (body.contains("fapiao")) {
					Logger.i(TAG, "拦截到了 发票短信...");
					abortBroadcast();
				}

				String mode = dao.findMode(sender);
				if ("1".equals(mode) || "3".equals(mode)) {
					Logger.i(TAG, "拦截到了 黑名单短信...");
					abortBroadcast();
					// 把短信的内容 和 发件人 保存起来.
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

			case TelephonyManager.CALL_STATE_IDLE: // 空闲状态
				long endTime = System.currentTimeMillis();
				long dTime = endTime - startTime;
				if (dTime < 3000) {
					// 发现一个响一声的号码, 通知用户.
					// 判断这个号码是否在黑名单数据库里面.
					String mode = dao.findMode(incomingNumber);
					if ("1".equals(mode) || "2".equals(mode)) {
					} else {
						showNotification(incomingNumber);//不是黑名单号码 弹出来来电响一声界面.
					}
				}

				break;

			case TelephonyManager.CALL_STATE_RINGING:
				// 记录下手机铃声第一次响的时间.
				startTime = System.currentTimeMillis();
				String mode = dao.findMode(incomingNumber);
				if ("1".equals(mode) || "2".equals(mode)) {
					Logger.i(TAG, "挂断电话...");
					endCall(incomingNumber);// 呼叫记录不是立刻生成的 .
					// deleteCallLog(incomingNumber);
					// 注册一个呼叫记录内容变化的内容观察者.
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

		// 当观察到数据发生改变的时候调用的方法.
		@Override
		public void onChange(boolean selfChange) {
			Logger.i(TAG, "呼叫记录变化了.");
			deleteCallLog(incomingNumber);
			getContentResolver().unregisterContentObserver(this);
			super.onChange(selfChange);
		}

	}

	/**
	 * 挂断电话的方法.
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
	 * 显示提示用户来电一声响的 notification
	 * 
	 * @param incomingNumber
	 */
	public void showNotification(String incomingNumber) {

		// 1.获取到notificationmanager
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// 2.实例化notification
		Notification notification = new Notification(R.drawable.notification,
				"发现来电一声响号码:" + incomingNumber, System.currentTimeMillis());
		// 3.设置notification的具体参数 和 延期意图 pendingIntent
		notification.flags = Notification.FLAG_AUTO_CANCEL;// 控制notification是否强占任务栏.
		Intent intent = new Intent(this, CallSmsSafeActivity.class);
		intent.putExtra("number", incomingNumber);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(this, "拦截到响一声号码:", "号码为:"
				+ incomingNumber, contentIntent);
		// 4.显示notification
		nm.notify(0, notification);

	}

	/**
	 * 移除呼叫记录
	 * 
	 * @param incomingNumber
	 */
	public void deleteCallLog(String incomingNumber) {
		Uri uri = Uri.parse("content://call_log/calls");
		getContentResolver().delete(uri, "number=?",
				new String[] { incomingNumber });
	}

}
