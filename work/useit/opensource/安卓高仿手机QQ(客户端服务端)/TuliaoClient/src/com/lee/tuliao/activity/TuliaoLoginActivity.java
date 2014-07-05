package com.lee.tuliao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.lee.data.trans.ProtocolConst;
import com.lee.tuliao.tools.DataHelper;

public class TuliaoLoginActivity extends TuliaoBaseActivity implements
		OnClickListener {

	protected Button login;
	protected Button register;
	protected EditText account_input;
	protected EditText password_input;
	protected DataHelper helper;

	protected CheckBox remember;
	protected CheckBox autologin;
	protected CheckBox hidelogin;
	protected CheckBox autorun;

	protected final String db_name = "name";
	protected final String db_password = "password";
	protected final String db_remember = "remember";
	protected final String db_autologin = "autologin";
	protected final String db_hidelogin = "hidelogin";
	protected final String db_autorun = "autorun";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		helper = DataHelper.getInstance(this);
		login = (Button) findViewById(R.id.login);
		register = (Button) findViewById(R.id.register);
		account_input = (EditText) findViewById(R.id.account_input);
		password_input = (EditText) findViewById(R.id.password_input);
		login.setOnClickListener(this);
		register.setOnClickListener(this);

		remember = (CheckBox) findViewById(R.id.remember);
		autologin = (CheckBox) findViewById(R.id.autologin);
		hidelogin = (CheckBox) findViewById(R.id.hidelogin);
		autorun = (CheckBox) findViewById(R.id.autorun);

		remember.setChecked(helper.getBoolean(db_remember, true));
		autologin.setChecked(helper.getBoolean(db_autologin, true));
		hidelogin.setChecked(helper.getBoolean(db_hidelogin, false));
		autorun.setChecked(helper.getBoolean(db_autorun, false));
		String NAME = helper.getString(db_name, null);
		String PWD = helper.getString(db_password, null);
		if (NAME != null && PWD != null) {
			account_input.setText(NAME);
			password_input.setText(PWD);
		}
	}

	private void saveData() {
		helper.putBoolean(db_remember, remember.isChecked());
		helper.putBoolean(db_autologin, autologin.isChecked());
		helper.putBoolean(db_hidelogin, hidelogin.isChecked());
		helper.putBoolean(db_autorun, autorun.isChecked());
		if (remember.isChecked()) {
			helper.putString(db_name, account_input.getText().toString());
			helper.putString(db_password, password_input.getText().toString());
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String name = account_input.getText().toString();
		String pwd = password_input.getText().toString();
		if (v == login) {
			if (con.login(name, pwd))
				makeTextShort(communication);
			else
				makeTextShort(communication_faild);
		} else if (v == register) {
			Intent intent = new Intent(this, TuliaoRegisterActivity.class);
			startActivityForResult(intent, 0);
		}
	}

	@Override
	public void processMessage(Message msg) {
		// TODO Auto-generated method stub
		if (msg.what == ProtocolConst.CMD_LOGIN_SUCESS) {
			saveData();
			Intent intent = new Intent(this, TuliaoMainActivity.class);
			startActivity(intent);
			con.start_check_in();// 登录成功必须要定时发送数据包务端
			//用于掉线重新登录
			String pwd = password_input.getText().toString();
			con.getTransportWorker().getSelf().setPwd(pwd);
		} else if (msg.what == ProtocolConst.CMD_LOGIN_SUCESS) {
			makeTextShort("对不起，登录失败！");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == ProtocolConst.CMD_REGISTER_SUCESS) {
			String NAME = data.getStringExtra("name");
			String PWD = data.getStringExtra("pwd");
			account_input.setText(NAME);
			password_input.setText(PWD);
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		System.exit(0);
	}
}