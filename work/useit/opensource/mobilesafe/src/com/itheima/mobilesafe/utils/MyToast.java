package com.itheima.mobilesafe.utils;

import com.itheima.mobilesafe.R;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyToast {

	/**
	 *  显示一个自定义的土司
	 * @param icon
	 * @param text
	 */
	public static void show(Context context, int icon,String text){
		Toast toast = new Toast(context);
		View view = View.inflate(context, R.layout.mytoast, null);
		ImageView iv = (ImageView) view.findViewById(R.id.iv_toast);
		iv.setImageResource(icon);
		TextView tv = (TextView) view.findViewById(R.id.tv_toast);
		tv.setText(text);
		toast.setView(view);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setGravity(Gravity.TOP, 0, 20);
		toast.show();
	}
}
