package com.itheima.mobilesafe;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.youmi.android.AdManager;
import net.youmi.android.AdView;

import org.w3c.dom.Text;

import com.itheima.mobilesafe.adapter.HomeAdapter;
import com.itheima.mobilesafe.utils.MD5Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class HomeActivity extends Activity implements OnClickListener {
	private GridView gv_home;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_home);

		// SQLiteDatabase.openDatabase(path, factory, flags);

		AdManager.init(this, "8b5fb2a780576fef", "5987356a0341ac8b", 30, true);
		LinearLayout adViewLayout = (LinearLayout) findViewById(R.id.adViewLayout);
		adViewLayout.addView(new AdView(this), new LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		gv_home = (GridView) findViewById(R.id.gv_home);
		gv_home.setAdapter(new HomeAdapter(this));

		gv_home.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch (position) {

				case 0:
					if (isSetupPwd()) {
						// �û����ù����� .���������ĶԻ���
						showNormalEntryDialog();
					} else {
						// �û�û�����ù����� .������������ĶԻ���
						showFirstEntryDialog();
					}
					break;
				case 1:
					intent = new Intent(HomeActivity.this,
							CallSmsSafeActivity.class);
					HomeActivity.this.startActivity(intent);
					break;
				case 2:
					intent = new Intent(HomeActivity.this,
							AppManagerActivity.class);
					HomeActivity.this.startActivity(intent);
					break;
				case 3:
					intent = new Intent(HomeActivity.this,
							TaskManagerActivity.class);
					HomeActivity.this.startActivity(intent);
					break;
				case 4:
					intent = new Intent(HomeActivity.this,
							TrafficManagerActivity.class);
					HomeActivity.this.startActivity(intent);
					break;
				case 5:
					intent = new Intent(HomeActivity.this,
							AntiVirusActivity.class);
					HomeActivity.this.startActivity(intent);
					break;
				case 6:
					intent = new Intent(HomeActivity.this,
							CleanTabActivity.class);
					HomeActivity.this.startActivity(intent);
					break;
				case 7:
					intent = new Intent(HomeActivity.this, AtoolsActivity.class);
					HomeActivity.this.startActivity(intent);
					break;
				case 8:
					intent = new Intent(HomeActivity.this,
							SettingActivity.class);
					HomeActivity.this.startActivity(intent);
					break;

				}

			}
		});
		;

	}

	/**
	 * ��������Ի���
	 */
	private EditText et_normal_entry_pwd;
	private EditText et_normal_entry_pwd_confirm;
	private Button bt_normal_entry_ok;
	private Button bt_normal_entry_cancle;

	protected void showNormalEntryDialog() {
		AlertDialog.Builder builder = new Builder(this);
		View view = View.inflate(this, R.layout.dialog_normal_entry, null);
		et_normal_entry_pwd = (EditText) view
				.findViewById(R.id.et_normal_entry_pwd);
		bt_normal_entry_ok = (Button) view
				.findViewById(R.id.bt_normal_entry_ok);
		bt_normal_entry_cancle = (Button) view
				.findViewById(R.id.bt_normal_entry_cancle);
		bt_normal_entry_ok.setOnClickListener(this);
		bt_normal_entry_cancle.setOnClickListener(this);
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
	}

	/**
	 * ��һ�ν���Ի���
	 */
	private EditText et_first_entry_pwd;
	private EditText et_first_entry_pwd_confirm;
	private Button bt_first_entry_ok;
	private Button bt_first_entry_cancle;
	private AlertDialog dialog;

	protected void showFirstEntryDialog() {
		AlertDialog.Builder builder = new Builder(this);
		View view = View.inflate(this, R.layout.dialog_first_entry, null);
		et_first_entry_pwd = (EditText) view
				.findViewById(R.id.et_first_entry_pwd);
		et_first_entry_pwd_confirm = (EditText) view
				.findViewById(R.id.et_first_entry_pwd_confirm);
		bt_first_entry_ok = (Button) view.findViewById(R.id.bt_first_entry_ok);
		bt_first_entry_cancle = (Button) view
				.findViewById(R.id.bt_first_entry_cancle);
		bt_first_entry_ok.setOnClickListener(this);
		bt_first_entry_cancle.setOnClickListener(this);
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);

		dialog.show();
	}

	/**
	 * �ж��û��Ƿ����ù�����
	 * 
	 * @return
	 */
	private boolean isSetupPwd() {
		String password = sp.getString("password", "");
		if (TextUtils.isEmpty(password)) {
			return false;
		} else {
			return true;
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_first_entry_cancle:
			dialog.dismiss();
			break;

		case R.id.bt_normal_entry_cancle:
			dialog.dismiss();
			break;
		case R.id.bt_normal_entry_ok:
			String enteredPwd = et_normal_entry_pwd.getText().toString().trim();
			if (TextUtils.isEmpty(enteredPwd)) {
				Toast.makeText(getApplicationContext(), "���벻��Ϊ��", 0).show();
				return;
			}
			String savedPwd = sp.getString("password", "");
			if (savedPwd.equals(MD5Utils.encode(enteredPwd))) {
				// ������ȷ. �����ֻ���������
				Intent intent = new Intent(this, LostFindActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), "���벻��ȷ", 0).show();
				return;
			}
			dialog.dismiss();

			break;
		case R.id.bt_first_entry_ok:
			String pwd = et_first_entry_pwd.getText().toString().trim();
			String pwd_confirm = et_first_entry_pwd_confirm.getText()
					.toString().trim();
			if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwd_confirm)) {
				Toast.makeText(getApplicationContext(), "���벻��Ϊ��", 0).show();
				return;
			}
			if (pwd.equals(pwd_confirm)) {
				Editor editor = sp.edit();
				editor.putString("password", MD5Utils.encode(pwd));
				editor.commit();
				dialog.dismiss();
			} else {
				Toast.makeText(getApplicationContext(), "���벻һ��", 0).show();
				return;
			}

			break;
		}

	}

}
