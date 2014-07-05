package com.itheima.mobilesafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public abstract class SetupBaseActivity extends Activity {
	protected SharedPreferences sp;
	protected GestureDetector mGestureDetector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp =getSharedPreferences("config", MODE_PRIVATE);
		initView();
		setupView();
		//1.创建一个手势识别器 new 对象
		mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
			//当手指在屏幕上滑动的时候 调用的方法.
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				if(e1.getRawX() - e2.getRawX() > 200){
					showNext();
					return true;
				}
				
				if(e2.getRawX() - e1.getRawX() > 200){
					showPre();
					return true;
				}
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
		
		
	}

	//2.让手势识别器生效
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mGestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	
	/**
	 * 设置布局, 寻找里面的view对象.
	 */
	public abstract void initView();
	/**
	 * 设置view对象的数据 和 点击事件.
	 */
	public abstract void setupView();
	
	/**
	 * 显示下一个界面的方法
	 */
	public abstract void showNext();
	/**
	 * 显示上一个界面的方法
	 */
	public abstract void showPre();
	
	
	public void next(View view){
		showNext();
	}
	public void pre(View view){
		showPre();
	}
}
