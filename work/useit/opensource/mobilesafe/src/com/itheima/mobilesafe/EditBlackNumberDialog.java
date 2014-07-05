package com.itheima.mobilesafe;

import com.itheima.mobilesafe.db.dao.BlackNumberDao;
import com.itheima.mobilesafe.domain.BlackNumberInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class EditBlackNumberDialog extends Activity {
	private TextView tv_title;
	private EditText et_add_blacknumber;
	private RadioGroup radioGroup1;
	private RadioButton rb_all, rb_phone, rb_sms;
	private BlackNumberDao dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_add_blacknumber);
		dao = new BlackNumberDao(this);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("请修改黑名单拦截的模式");
		et_add_blacknumber = (EditText) findViewById(R.id.et_add_blacknumber);

		Intent intent = getIntent();// 获取激活当前activity的意图
		BlackNumberInfo info = (BlackNumberInfo) intent.getSerializableExtra("obj");
		System.out.println(info.toString());
		final String number = intent.getStringExtra("number");
		et_add_blacknumber.setText(number);
		et_add_blacknumber.setEnabled(false);// 禁止用户更改里面的内容.
		
		
		MobliesafeApplication myapp = (MobliesafeApplication) getApplication();
		final BlackNumberInfo myblackInfo = myapp.info;
		System.out.println("edit number:"+myblackInfo.hashCode());
		radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
		rb_all = (RadioButton) findViewById(R.id.rb_all);
		rb_phone = (RadioButton) findViewById(R.id.rb_phone);
		rb_sms = (RadioButton) findViewById(R.id.rb_sms);

		String mode = intent.getStringExtra("mode");

		if ("1".equals(mode)) {
			rb_all.setChecked(true);
		} else if ("2".equals(mode)) {
			rb_phone.setChecked(true);
		} else if ("3".equals(mode)) {
			rb_sms.setChecked(true);
		}

		findViewById(R.id.bt_add_blacknumber_ok).setOnClickListener(
				new OnClickListener() {

					public void onClick(View v) {
						int id = radioGroup1.getCheckedRadioButtonId();
						String mode = "1";
						switch (id) {
						case R.id.rb_all:
							mode = "1";
							break;

						case R.id.rb_phone:
							mode = "2";
							break;
						case R.id.rb_sms:
							mode = "3";
							break;
						}
						dao.update(number, mode);
						setResult(300);//数据更新了.
						myblackInfo.setMode(mode);
						finish();
					}
				});
		findViewById(R.id.bt_add_blacknumber_cancle).setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				setResult(404);//数据没有更新
				finish();
				
			}
		});
	}
}
