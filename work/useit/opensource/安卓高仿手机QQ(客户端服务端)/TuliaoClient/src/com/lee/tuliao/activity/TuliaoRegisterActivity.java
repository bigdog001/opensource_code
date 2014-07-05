package com.lee.tuliao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.lee.data.trans.ProtocolConst;

public class TuliaoRegisterActivity extends TuliaoBaseActivity implements
		OnClickListener {

	protected Button register;
	protected Button cancel;
	protected EditText name;
	protected EditText pwd;
	protected EditText confirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		name = (EditText) findViewById(R.id.name);
		pwd = (EditText) findViewById(R.id.pwd);
		confirm = (EditText) findViewById(R.id.confirm);
		register = (Button) findViewById(R.id.register);
		cancel = (Button) findViewById(R.id.cancel);
	}

	@Override
	public void processMessage(Message msg) {
		// TODO Auto-generated method stub
		if (msg.what == ProtocolConst.CMD_PASSWORD_NOT_SAME)
			makeTextShort("对不起，你两次输入的密码不一样！");
		else if (msg.what == ProtocolConst.CMD_REGISTER_SUCESS) {
			Intent data = new Intent();
			data.putExtra("name", name.getText().toString());
			data.putExtra("pwd", pwd.getText().toString());
			setResult(ProtocolConst.CMD_REGISTER_SUCESS, data);
			finish();
			makeTextShort("恭喜你，注册成功！");
		} else if (msg.what == ProtocolConst.CMD_REGISTER_FAILD)
			makeTextShort("对不起，注册失败！");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == cancel) {
			finish();
		} else if (v == register) {
			String NAME = name.getText().toString();
			String PWD = pwd.getText().toString();
			String CONFIRM = confirm.getText().toString();
			if (!PWD.equals(CONFIRM)) {
				sendEmptyMessage(ProtocolConst.CMD_PASSWORD_NOT_SAME);
			} else {
				if (con.register(NAME, PWD))
					makeTextShort(communication);
				else
					makeTextShort(communication_faild);
			}
		}
	}

}
