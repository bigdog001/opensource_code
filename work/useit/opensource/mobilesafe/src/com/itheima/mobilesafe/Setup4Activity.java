package com.itheima.mobilesafe;

import com.itheima.mobilesafe.receiver.MyAdmin;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Setup4Activity extends SetupBaseActivity {
	private CheckBox cb_setup4_status;

	@Override
	public void initView() {
		setContentView(R.layout.activity_setup4);
		cb_setup4_status = (CheckBox) findViewById(R.id.cb_setup4_status);
		boolean protect = sp.getBoolean("protect", false);
		if (protect) {
			cb_setup4_status.setText("防盗保护已经开启");
		} else {
			cb_setup4_status.setText("防盗保护没有开启");
		}
		cb_setup4_status.setChecked(protect);
	}

	@Override
	public void setupView() {
		cb_setup4_status
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						Editor editor = sp.edit();
						if (isChecked) {
							cb_setup4_status.setText("防盗保护已经开启");
							editor.putBoolean("protect", true);
						} else {
							cb_setup4_status.setText("防盗保护没有开启");
							editor.putBoolean("protect", false);
						}
						editor.commit();
					}
				});
	}

	@Override
	public void showNext() {
		Editor editor = sp.edit();
		editor.putBoolean("issetup", true);
		editor.commit();
		Intent intent = new Intent(this, LostFindActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);

	}

	@Override
	public void showPre() {
		Intent intent = new Intent(this, Setup3Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}

	// 激活设备超级管理员
	public void activeAdmin(View view) {
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		// 初始化要激活的组件
		ComponentName mDeviceAdminSample = new ComponentName(this,
				MyAdmin.class);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
				mDeviceAdminSample);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
				"激活后可以远程锁屏,清理数据");
		startActivity(intent);
	}
}
