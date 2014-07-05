package com.itheima.mobilesafe.service;

import com.itheima.mobilesafe.R;
import com.itheima.mobilesafe.db.dao.AddressDao;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.itheima.mobilesafe.utils.Logger;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class CallSmsSafeService extends Service {
	public static final String TAG = "CallSmsSafeService";
	private TelephonyManager tm;
	private MyPhoneStateListener listener;
	private InnerReceiver receiver;
	private WindowManager wm;
	private View view;
	private SharedPreferences sp;
	private WindowManager.LayoutParams params; //�Զ���view�����ڴ����еĲ���
	// //"��͸��","������","��ʿ��","������","ƻ����"
	private static int[] bgs = { R.drawable.call_locate_white,
			R.drawable.call_locate_orange, R.drawable.call_locate_blue,
			R.drawable.call_locate_gray, R.drawable.call_locate_green };

	private class InnerReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Logger.i(TAG, "���Ƿ���������ڲ��Ĺ㲥������");
			String number = getResultData();
			String address = AddressDao.getAddress(number);
			// Toast.makeText(context, addres, 1).show();
			showLocation(address);
		}

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * ��ʾ��������Ϣ
	 * 
	 * @param address
	 */
	public void showLocation(String address) {
		view = View.inflate(getApplicationContext(), R.layout.toast_location,
				null);
		// �õ�sp
		int which = sp.getInt("which", 0);

		view.setBackgroundResource(bgs[which]);

		view.setOnTouchListener(new OnTouchListener() {
			int startX ,startY;
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				
				case MotionEvent.ACTION_DOWN:
					Logger.i(TAG,"����");
					startX = (int) event.getRawX();
					startY  = (int) event.getRawY();
					break;

				case MotionEvent.ACTION_MOVE:
					Logger.i(TAG,"�ƶ�");
					int newX = (int) event.getRawX();
					int newY  = (int) event.getRawY();
					int dx = newX - startX;
					int dy = newY - startY;
					params.x+=dx;
					params.y+=dy;
					wm.updateViewLayout(view, params);
					
					//��д��ʼ�� ��ָ��λ��
					startX = (int) event.getRawX();
					startY  = (int) event.getRawY();
					break;
				}

				return true;
			}
		});

		TextView tv = (TextView) view.findViewById(R.id.tv_toast_address);
		tv.setText(address);

		params = new LayoutParams();
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.gravity = Gravity.LEFT | Gravity.TOP;

		params.x = sp.getInt("lastx", 0);
		params.y = sp.getInt("lasty", 0);
		params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		params.format = PixelFormat.TRANSLUCENT;
		params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
		wm.addView(view, params);
	}

	@Override
	public void onCreate() {
		sp = getSharedPreferences("config", MODE_PRIVATE);
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);

		receiver = new InnerReceiver();// ʵ�����㲥�����߶���
		// ���뷽ʽע��㲥������
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
		filter.setPriority(1000);

		registerReceiver(receiver, filter);

		// ע��һ���绰״̬�ļ�����.
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		listener = new MyPhoneStateListener();
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);// ȡ������
		listener = null;

		unregisterReceiver(receiver);// ����ֹͣ��ʱ�� ȡ���㲥�����ߵ�ע��
		receiver = null;

		// �����ڱ�ֹͣ��ʱ�� ����sp���������
		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("showaddress", false);
		editor.commit();
		super.onDestroy();
	}

	private class MyPhoneStateListener extends PhoneStateListener {

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING: // ����״̬.
				String address = AddressDao.getAddress(incomingNumber);
				// Toast.makeText(getApplicationContext(), address, 1).show();
				showLocation(address);
				break;

			case TelephonyManager.CALL_STATE_OFFHOOK: // ͨ��״̬
				break;

			case TelephonyManager.CALL_STATE_IDLE:// ����״̬
				if (view != null) {
					wm.removeView(view);
					view = null;
				}
				break;
			}

		}

	}
}
