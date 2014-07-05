package com.itheima.mobilesafe;

import android.content.Intent;


public class Setup1Activity extends SetupBaseActivity {

	@Override
	public void initView() {
		setContentView(R.layout.activity_setup1);
	}
	@Override
	public void setupView() {
		
	}

	@Override
	public void showNext() {
		Intent intent = new Intent(this,Setup2Activity.class);
		startActivity(intent);
		finish();
		
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);
	}

	@Override
	public void showPre() {
		
	}


}
