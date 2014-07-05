package com.itheima.mobilesafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import com.itheima.mobilesafe.utils.Logger;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class DragViewActivity extends Activity {
	protected static final String TAG = "DragViewActivity";
	private ImageView iv_drag_view;
	private TextView tv_drag_view;
	private SharedPreferences sp;
	
	private WindowManager wm;
	private Display  display; //�������ʾ�ķֱ���
	
	private long firstClickTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		// Have the system blur any windows behind this one.
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);//���������
		display = wm.getDefaultDisplay();
		
		setContentView(R.layout.activity_drag_view);
		tv_drag_view = (TextView) findViewById(R.id.tv_drag_view);
		iv_drag_view = (ImageView) findViewById(R.id.iv_drag_view);
		
		int lastx = sp.getInt("lastx", 0);
		int lasty = sp.getInt("lasty", 0);
//		int iv_height = iv_drag_view.getBottom() - iv_drag_view.getTop();
//		int iv_width = iv_drag_view.getRight() - iv_drag_view.getLeft();
//		iv_drag_view.layout(lastx, lasty, lastx + iv_width, lasty+iv_height);
		
		RelativeLayout.LayoutParams params = (LayoutParams) iv_drag_view.getLayoutParams();
		params.leftMargin = lastx;
		params.topMargin = lasty;
		iv_drag_view.setLayoutParams(params);

		iv_drag_view.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Logger.i(TAG,"�������.");
				if(firstClickTime>0){//�ڶ��ε��.
					long secondTime = System.currentTimeMillis();
					long dtime = secondTime - firstClickTime;
					if(dtime<500){
						//˫���¼�.
						Logger.i(TAG,"˫������");
						int iv_width = iv_drag_view.getRight() - iv_drag_view.getLeft();
						iv_drag_view.layout(display.getWidth()/2-iv_width/2, iv_drag_view.getTop(), display.getWidth()/2+iv_width/2, iv_drag_view.getBottom());
						int lasty = iv_drag_view.getTop();//�õ����������Ļ�Ϸ��ľ���
						int lastx = iv_drag_view.getLeft();//�õ��������Ļ��ߵľ���
						Editor editor = sp.edit();
						editor.putInt("lastx", lastx);
						editor.putInt("lasty", lasty);
						editor.commit();
						
					}
					firstClickTime = 0;
					return;
				}
				//��λʱ���ڵ����ε��.
				firstClickTime = System.currentTimeMillis();//  ��¼��һ�ε�����¼�
				new Thread(){
					public void run() {
						try {
							Thread.sleep(500);
							firstClickTime = 0;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					};
				}.start();
				
				
			}
		});
		
		
		//True if the listener has consumed the event, false otherwise
		//true �������������¼������ѵ�, false �������ѵ�����¼�
		iv_drag_view.setOnTouchListener(new OnTouchListener() {

			int startX , startY;
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:// ��ָ��������Ļ���¼�
					Logger.i(TAG,"��������ؼ���");
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:// ��ָ����Ļ���ƶ����¼�
					Logger.i(TAG,"�ƶ�");
					int newX = (int) event.getRawX();
					int newY = (int) event.getRawY();
					int dx = newX - startX;
					int dy = newY - startY;
					int l = iv_drag_view.getLeft();
					int r = iv_drag_view.getRight();
					int b = iv_drag_view.getBottom();
					int t = iv_drag_view.getTop();
					
					int newl = l+dx;
					int newr = r+dx;
					int newt = t+dy;//imageview �ڴ������µ�λ��
					int newb = b+dy;
					
					if(newl<0||newt < 0 ||newb>display.getHeight()-30||newr>display.getWidth()){
						break;
					}
					
					
					int tv_height = tv_drag_view.getBottom() - tv_drag_view.getTop();
					
					if(newt>display.getHeight()/2){//imageview�ڴ�����·�
						//textview�ڴ�����Ϸ�
						tv_drag_view.layout(tv_drag_view.getLeft(), 0, tv_drag_view.getRight(), tv_height);
					}else{
						tv_drag_view.layout(tv_drag_view.getLeft(), display.getHeight()-tv_height-30, tv_drag_view.getRight(), display.getHeight()-30);
						//textview�ڴ�����·�
					}
					
					
					
					
					iv_drag_view.layout(newl,  newt, newr, newb);
					
					//������ָ��ʼ��λ��.
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					
					break;
				case MotionEvent.ACTION_UP: // ��ָ���뿪��Ļ��һ˲���Ӧ���¼�.
					Logger.i(TAG,"����");
					int lasty = iv_drag_view.getTop();//�õ����������Ļ�Ϸ��ľ���
					int lastx = iv_drag_view.getLeft();//�õ��������Ļ��ߵľ���
					Editor editor = sp.edit();
					editor.putInt("lastx", lastx);
					editor.putInt("lasty", lasty);
					editor.commit();
					break;
				}
				return false;
			}
		});

	}
}
