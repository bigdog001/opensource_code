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
	private WindowManager.LayoutParams params; //自定义view对象在窗体中的参数
	// //"半透明","活力橙","卫士蓝","金属灰","苹果绿"
	private static int[] bgs = { R.drawable.call_locate_white,
			R.drawable.call_locate_orange, R.drawable.call_locate_blue,
			R.drawable.call_locate_gray, R.drawable.call_locate_green };

	private class InnerReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Logger.i(TAG, "我是服务里面的内部的广播接受者");
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
	 * 显示归属地信息
	 * 
	 * @param address
	 */
	public void showLocation(String address) {
		view = View.inflate(getApplicationContext(), R.layout.toast_location,
				null);
		// 得到sp
		int which = sp.getInt("which", 0);

		view.setBackgroundResource(bgs[which]);

		view.setOnTouchListener(new OnTouchListener() {
			int startX ,startY;
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				
				case MotionEvent.ACTION_DOWN:
					Logger.i(TAG,"摸到");
					startX = (int) event.getRawX();
					startY  = (int) event.getRawY();
					break;

				case MotionEvent.ACTION_MOVE:
					Logger.i(TAG,"移动");
					int newX = (int) event.getRawX();
					int newY  = (int) event.getRawY();
					int dx = newX - startX;
					int dy = newY - startY;
					params.x+=dx;
					params.y+=dy;
					wm.updateViewLayout(view, params);
					
					//重写初始化 手指的位置
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

		receiver = new InnerReceiver();// 实例化广播接受者对象
		// 代码方式注册广播接受者
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.NEW_OUTGOING_CALL");
		filter.setPriority(1000);

		registerReceiver(receiver, filter);

		// 注册一个电话状态的监听器.
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		listener = new MyPhoneStateListener();
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);// 取消监听
		listener = null;

		unregisterReceiver(receiver);// 服务停止的时候 取消广播接受者的注册
		receiver = null;

		// 服务在被停止的时候 重置sp里面的配置
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
			case TelephonyManager.CALL_STATE_RINGING: // 响铃状态.
				String address = AddressDao.getAddress(incomingNumber);
				// Toast.makeText(getApplicationContext(), address, 1).show();
				showLocation(address);
				break;

			case TelephonyManager.CALL_STATE_OFFHOOK: // 通话状态
				break;

			case TelephonyManager.CALL_STATE_IDLE:// 空闲状态
				if (view != null) {
					wm.removeView(view);
					view = null;
				}
				break;
			}

		}

	}
}
