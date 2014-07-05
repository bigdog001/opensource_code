package com.itheima.mobilesafe;

import com.itheima.mobilesafe.db.dao.AddressDao;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NumberQueryActivity extends Activity {
	private EditText et_numberquery_number;
	private TextView tv_numberquery_address;
	private Vibrator vibrator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		
		setContentView(R.layout.activity_number_query);
		et_numberquery_number = (EditText) findViewById(R.id.et_numberquery_number);
		tv_numberquery_address = (TextView) findViewById(R.id.tv_numberquery_address);
		et_numberquery_number.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String address  = AddressDao.getAddress(s.toString());
				tv_numberquery_address.setText(address);
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			public void afterTextChanged(Editable s) {
				
			}
		});
	
	}
	
	public void query(View view){
		String number = et_numberquery_number.getText().toString().trim();
		if(TextUtils.isEmpty(number)){
			Toast.makeText(this, "ºÅÂë²»ÄÜÎª¿Õ", 0).show();
	        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
	        et_numberquery_number.startAnimation(shake);
			vibrator.vibrate(300);
			vibrator.vibrate(new long[]{200,300,200,100}, 1);
			return ;
		}
		String address  = AddressDao.getAddress(number);
		tv_numberquery_address.setText(address);
		
	}
}
