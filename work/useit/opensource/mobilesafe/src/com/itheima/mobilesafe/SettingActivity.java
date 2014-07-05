package com.itheima.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.mobilesafe.service.CallSmsFirewallService;
import com.itheima.mobilesafe.service.CallSmsSafeService;
import com.itheima.mobilesafe.service.WatchDogService;
import com.itheima.mobilesafe.ui.SettingView;

public class SettingActivity extends Activity implements OnClickListener {
	
	private SettingView sv_setting_update;
	
	private SharedPreferences sp;
	private String s;
	//归属地查询的服务设置
	private SettingView sv_setting_showaddress;
	private Intent showAddressIntent;
	
	
	//来电短信黑名单服务设置
	private SettingView sv_setting_firewall;
	private Intent callSmsFirewallIntent;
	
	
	// 程序锁设置
	private SettingView sv_setting_applock;
	private Intent appLockIntent;
	
	
	//归属地显示的背景
	private RelativeLayout rl_setting_changebg;
	private TextView tv_setting_addressbg;
	private static	String[] items = {"半透明","活力橙","卫士蓝","金属灰","苹果绿"};
	
	//归属地提示框位置
	private RelativeLayout rl_setting_change_location;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		rl_setting_changebg = (RelativeLayout) findViewById(R.id.rl_setting_changebg);
		rl_setting_changebg.setOnClickListener(this);
		tv_setting_addressbg = (TextView) findViewById(R.id.tv_setting_addressbg);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		
		
		//归属地提示框位置
		rl_setting_change_location = (RelativeLayout) findViewById(R.id.rl_setting_change_location);
		rl_setting_change_location.setOnClickListener(this);
		
		int which = sp.getInt("which", 0);
		tv_setting_addressbg.setText(items[which]);
		
		sv_setting_update = (SettingView) findViewById(R.id.sv_setting_update);
		boolean  update = sp.getBoolean("update", true);//默认情况下自动更新被打开.
		sv_setting_update.setChecked(update);//初始化 选中状态.
		sv_setting_update.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				sv_setting_update.setChecked(!sv_setting_update.isChecked());
				Editor editor = sp.edit();
				editor.putBoolean("update", sv_setting_update.isChecked());
				editor.commit();
			}
		});
		
		//归属地查询的初始化
		sv_setting_showaddress = (SettingView)findViewById(R.id.sv_setting_showaddress);
	
		showAddressIntent = new Intent(this,CallSmsSafeService.class);
		sv_setting_showaddress.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(sv_setting_showaddress.isChecked()){
					sv_setting_showaddress.setChecked(false);//取消勾选.
					stopService(showAddressIntent);
					
				}else{
					sv_setting_showaddress.setChecked(true);//设置勾选.
					//记录选中状态.
					Editor editor = sp.edit();
					editor.putBoolean("showaddress", true);
					editor.commit();
					startService(showAddressIntent);
				}
				
			}
		});
		//防火墙的初始化
		sv_setting_firewall = (SettingView) findViewById(R.id.sv_setting_firewall);
		callSmsFirewallIntent = new Intent(this,CallSmsFirewallService.class);
		sv_setting_firewall.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(sv_setting_firewall.isChecked()){
					sv_setting_firewall.setChecked(false);
					stopService(callSmsFirewallIntent);
				}else{
					sv_setting_firewall.setChecked(true);
					startService(callSmsFirewallIntent);
					//记录选中状态.
					Editor editor = sp.edit();
					editor.putBoolean("firewall", true);
					editor.commit();
				}
				
			}
		});
		
		//程序锁的初始化
		sv_setting_applock = (SettingView)findViewById(R.id.sv_setting_applock);
		appLockIntent = new Intent(this,WatchDogService.class);
		sv_setting_applock.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(sv_setting_applock.isChecked()){
					sv_setting_applock.setChecked(false);
					stopService(appLockIntent);
				}else{
					sv_setting_applock.setChecked(true);
					startService(appLockIntent);
					//记录选中状态.
					Editor editor = sp.edit();
					editor.putBoolean("applock", true);
					editor.commit();
				}
				
			}
		});
	
		
	}
	
	@Override
	protected void onStart() {
		boolean showaddress = sp.getBoolean("showaddress", false);
		sv_setting_showaddress.setChecked(showaddress);
		
		boolean firewall = sp.getBoolean("firewall", false);
		sv_setting_firewall.setChecked(firewall);
		
		boolean applock = sp.getBoolean("applock", false);
		sv_setting_applock.setChecked(applock);
		
		
		super.onStart();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_setting_changebg:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setIcon(R.drawable.notification);
			builder.setTitle("归属地提示框风格");

			int which = sp.getInt("which", 0);
			builder.setSingleChoiceItems(items, which, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Editor editor = sp.edit();
					editor.putInt("which", which);
					editor.commit();
					dialog.dismiss();
					tv_setting_addressbg.setText(items[which]);
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					s.equals("haha");
					//"haha".equals(s);
				}
			});
			builder.show();
			break;
		case R.id.rl_setting_change_location:
			Intent intent = new Intent(this,DragViewActivity.class);
			startActivity(intent);
			
			break;
		}
		
	}
}
