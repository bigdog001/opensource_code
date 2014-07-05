package com.itheima.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Setup2Activity extends SetupBaseActivity {
	private RelativeLayout rl_setup2_bind;
	private ImageView iv_setup2_bindstatus;
	private TelephonyManager tm;

	@Override
	public void initView() {
		setContentView(R.layout.activity_setup2);
		rl_setup2_bind = (RelativeLayout) findViewById(R.id.rl_setup2_bind);
		iv_setup2_bindstatus = (ImageView) findViewById(R.id.iv_setup2_bindstatus);
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		String savedSim = sp.getString("sim", "");
		if (TextUtils.isEmpty(savedSim)) {
			iv_setup2_bindstatus.setImageResource(R.drawable.unlock);
		}else{
			iv_setup2_bindstatus.setImageResource(R.drawable.lock);
		}
		
	}

	@Override
	public void setupView() {
		rl_setup2_bind.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String savedSim = sp.getString("sim", "");
				if (TextUtils.isEmpty(savedSim)) {
					String sim = tm.getSimSerialNumber();// 获取sim卡的串号
					Editor editor = sp.edit();
					editor.putString("sim", sim);
					editor.commit();
					iv_setup2_bindstatus.setImageResource(R.drawable.lock);
				}else{
					Editor editor = sp.edit();
					editor.putString("sim", "");
					editor.commit();
					iv_setup2_bindstatus.setImageResource(R.drawable.unlock);
				}
			}
		});
	}

	@Override
	public void showNext() {
		String savedSim = sp.getString("sim", "");
		if (TextUtils.isEmpty(savedSim)) {
			Toast.makeText(this, "请先绑定sim卡", 0).show();
			return ;
		}
		
		
		
		Intent intent = new Intent(this, Setup3Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);
	}

	@Override
	public void showPre() {
		Intent intent = new Intent(this, Setup1Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}

}
