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
		//1.����һ������ʶ���� new ����
		mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
			//����ָ����Ļ�ϻ�����ʱ�� ���õķ���.
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

	//2.������ʶ������Ч
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mGestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	
	/**
	 * ���ò���, Ѱ�������view����.
	 */
	public abstract void initView();
	/**
	 * ����view��������� �� ����¼�.
	 */
	public abstract void setupView();
	
	/**
	 * ��ʾ��һ������ķ���
	 */
	public abstract void showNext();
	/**
	 * ��ʾ��һ������ķ���
	 */
	public abstract void showPre();
	
	
	public void next(View view){
		showNext();
	}
	public void pre(View view){
		showPre();
	}
}
