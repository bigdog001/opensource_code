package com.itheima.mobilesafe;

import com.itheima.mobilesafe.service.IService;
import com.itheima.mobilesafe.service.WatchDogService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EnterPwdActivity extends Activity {
	private EditText et_password;
	private ImageView iv_enter_pwd;
	private TextView tv_enter_pwd;
	private String packname;
	
	private Intent service;
	private IService iService;
	private MyConn conn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_pwd);
		et_password = (EditText) findViewById(R.id.et_password);
		
		iv_enter_pwd = (ImageView) findViewById(R.id.iv_enter_pwd);
		tv_enter_pwd = (TextView) findViewById(R.id.tv_enter_pwd);
		
		Intent intent = getIntent();
		packname = intent.getStringExtra("packname");
		
		PackageManager pm = getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(packname, 0);
			iv_enter_pwd.setImageDrawable(info.applicationInfo.loadIcon(pm));
			tv_enter_pwd.setText(info.applicationInfo.loadLabel(pm));
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		service = new Intent(this,WatchDogService.class);
		conn = new MyConn();
		bindService(service, conn, BIND_AUTO_CREATE);
		
	}
	
	private class MyConn implements ServiceConnection{

		public void onServiceConnected(ComponentName name, IBinder service) {
			iService = (IService) service;
		}

		public void onServiceDisconnected(ComponentName name) {
		}
		
	}
	
	
	public void enter(View view){
		String pwd = et_password.getText().toString().trim();
		if("123".equals(pwd)){
			//密码正确
			//发送一个自定义的广播 
			/*Intent intent = new Intent();
			intent.setAction("com.itheima.stopprotect");
			intent.putExtra("packname", packname);
			sendBroadcast(intent);*/
			//绑定服务的方式 直接调用服务里面的方法.
			iService.callTempStopProtect(packname);
			finish();
		}else{
			Toast.makeText(this, "密码不正确", 0).show();
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(event.getKeyCode()==KeyEvent.KEYCODE_BACK){
			//回桌面.
			Intent intent  =new Intent();
			intent.setAction("android.intent.action.MAIN");
			intent.addCategory("android.intent.category.HOME");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.addCategory("android.intent.category.MONKEY");
			startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
		unbindService(conn);
		super.onDestroy();
	}
}
