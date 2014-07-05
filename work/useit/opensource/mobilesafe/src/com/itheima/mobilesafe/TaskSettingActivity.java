package com.itheima.mobilesafe;

import com.itheima.mobilesafe.service.AutoKillService;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class TaskSettingActivity extends Activity {
	private CheckBox cb_tasksetting;
	private SharedPreferences sp;
	
	private CheckBox cb_tasksetting_autokill;
	private Intent autoKillIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_task_setting);
		cb_tasksetting = (CheckBox) findViewById(R.id.cb_tasksetting);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		boolean showsystem  = sp.getBoolean("showsystem", false);
		cb_tasksetting.setChecked(showsystem);
		cb_tasksetting.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Editor editor = sp.edit();
				editor.putBoolean("showsystem", isChecked);
				editor.commit();
			}
		});
		
		cb_tasksetting_autokill = (CheckBox) findViewById(R.id.cb_tasksetting_autokill);
		autoKillIntent = new Intent(this,AutoKillService.class);
		cb_tasksetting_autokill.setOnCheckedChangeListener(new  OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//采用代码的方式注册一个屏幕锁屏的广播接受者.
				if(isChecked){
					startService(autoKillIntent);
				}else{
					stopService(autoKillIntent);
				}
			}
		});
		super.onCreate(savedInstanceState);
	}
}
