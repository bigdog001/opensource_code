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
	private Display  display; //窗体的显示的分辨率
	
	private long firstClickTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		// Have the system blur any windows behind this one.
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);//窗体管理者
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
				Logger.i(TAG,"被点击了.");
				if(firstClickTime>0){//第二次点击.
					long secondTime = System.currentTimeMillis();
					long dtime = secondTime - firstClickTime;
					if(dtime<500){
						//双击事件.
						Logger.i(TAG,"双击居中");
						int iv_width = iv_drag_view.getRight() - iv_drag_view.getLeft();
						iv_drag_view.layout(display.getWidth()/2-iv_width/2, iv_drag_view.getTop(), display.getWidth()/2+iv_width/2, iv_drag_view.getBottom());
						int lasty = iv_drag_view.getTop();//得到最后在离屏幕上方的距离
						int lastx = iv_drag_view.getLeft();//得到最后离屏幕左边的距离
						Editor editor = sp.edit();
						editor.putInt("lastx", lastx);
						editor.putInt("lasty", lasty);
						editor.commit();
						
					}
					firstClickTime = 0;
					return;
				}
				//单位时间内的两次点击.
				firstClickTime = System.currentTimeMillis();//  记录第一次点击的事件
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
		//true 监听器会把这个事件给消费掉, false 不会消费掉这个事件
		iv_drag_view.setOnTouchListener(new OnTouchListener() {

			int startX , startY;
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:// 手指触摸到屏幕的事件
					Logger.i(TAG,"摸到这个控件了");
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:// 手指在屏幕上移动的事件
					Logger.i(TAG,"移动");
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
					int newt = t+dy;//imageview 在窗体中新的位置
					int newb = b+dy;
					
					if(newl<0||newt < 0 ||newb>display.getHeight()-30||newr>display.getWidth()){
						break;
					}
					
					
					int tv_height = tv_drag_view.getBottom() - tv_drag_view.getTop();
					
					if(newt>display.getHeight()/2){//imageview在窗体的下方
						//textview在窗体的上方
						tv_drag_view.layout(tv_drag_view.getLeft(), 0, tv_drag_view.getRight(), tv_height);
					}else{
						tv_drag_view.layout(tv_drag_view.getLeft(), display.getHeight()-tv_height-30, tv_drag_view.getRight(), display.getHeight()-30);
						//textview在窗体的下方
					}
					
					
					
					
					iv_drag_view.layout(newl,  newt, newr, newb);
					
					//更新手指开始的位置.
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					
					break;
				case MotionEvent.ACTION_UP: // 手指在离开屏幕的一瞬间对应的事件.
					Logger.i(TAG,"放手");
					int lasty = iv_drag_view.getTop();//得到最后在离屏幕上方的距离
					int lastx = iv_drag_view.getLeft();//得到最后离屏幕左边的距离
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
