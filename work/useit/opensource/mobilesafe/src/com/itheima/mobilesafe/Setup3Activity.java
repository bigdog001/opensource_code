package com.itheima.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Setup3Activity extends SetupBaseActivity {
	private EditText et_setup3_phone;
	@Override
	public void initView() {
		setContentView(R.layout.activity_setup3);
		et_setup3_phone = (EditText) findViewById(R.id.et_setup3_phone);
	}
	@Override
	public void setupView() {
		et_setup3_phone.setText(sp.getString("safenumber", ""));
	}

	@Override
	public void showNext() {
		String safenumber = et_setup3_phone.getText().toString().trim();
		if(TextUtils.isEmpty(safenumber)){
			Toast.makeText(this, "安全号码不能为空", 0).show();
			return;
		}
		Editor editor = sp.edit();
		editor.putString("safenumber", safenumber);
		editor.commit();
		Intent intent = new Intent(this,Setup4Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);
	}

	@Override
	public void showPre() {
		Intent intent = new Intent(this,Setup2Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}
	/**
	 * 选择联系人的点击事件
	 */
	public void selectContact(View view){
		//激活新的界面 获取系统的所有的联系人.
		Intent intent = new Intent(this,SelectContactActivity.class);
		//startActivity(intent);
		startActivityForResult(intent, 0);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data!=null){
			String phone = data.getStringExtra("phone");
			et_setup3_phone.setText(phone);
		}
		
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
