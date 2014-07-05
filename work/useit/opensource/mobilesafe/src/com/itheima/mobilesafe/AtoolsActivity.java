package com.itheima.mobilesafe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itheima.mobilesafe.engine.SmsUtils;
import com.itheima.mobilesafe.engine.SmsUtils.BackUpStatusListener;
import com.itheima.mobilesafe.utils.MyAsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class AtoolsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atools);
	}
	
	//������������ز�ѯ����.
	public void numberAddressQuery(View view){
		Intent intent = new Intent(this,NumberQueryActivity.class);
		startActivity(intent);
	}
	//�������ú����ѯ����
	public void commonNumQuery(View view){
		
		Intent intent = new Intent(this,CommonNumberActivity.class);
		startActivity(intent);
	}
	//���ŵı���
	public void backupSms(View view){
//		new MyAsyncTask() {
//			ProgressDialog pd;
//			@Override
//			public void onPreExecute() {
//				pd = new ProgressDialog(AtoolsActivity.this);
//				pd.setMessage("���ڱ��ݶ���");
//				pd.show();
//			}
//			
//			@Override
//			public void onPostExecute() {
//				pd.dismiss();
//			}
//			
//			@Override
//			public void doInBackground() {
//				try {
//					File file = new File(Environment.getExternalStorageDirectory(),"backup.xml");
//					FileOutputStream fos = new FileOutputStream(file);
//					SmsUtils.backUp(getApplicationContext(), fos);
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}.execute();
		//params �첽����ִ����Ҫ�Ĳ���
		//Progess ִ�еĽ���
		//result ִ�к�Ľ��
		new AsyncTask<String, Void, Boolean>() {
			ProgressDialog pd;
			@Override
			protected Boolean doInBackground(String... params) {
				try {
					String filename = params[0];
					File file = new File(Environment.getExternalStorageDirectory(),filename);
					FileOutputStream fos = new FileOutputStream(file);
					SmsUtils.backUp(getApplicationContext(), fos, new BackUpStatusListener() {
						public void onBackUpProcess(int process) {
							pd.setProgress(process);
						}
						
						public void beforeBackup(int max) {
							pd.setMax(max);
						}
					});
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}	
			}

			@Override
			protected void onPreExecute() {
				pd = new ProgressDialog(AtoolsActivity.this);
				pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				pd.setMessage("���ڱ��ݶ���");
				pd.show();
				super.onPreExecute();
			}

			@Override
			protected void onPostExecute(Boolean result) {
				pd.dismiss();
				if(result){
					Toast.makeText(getApplicationContext(), "���ݳɹ�", 0).show();
				}else{
					Toast.makeText(getApplicationContext(), "����ʧ��", 0).show();
				}
				super.onPostExecute(result);
			}
			
		}.execute("backup.xml");
		
	}
	
	/**
	 * �������������
	 */
	public void enterAppLock(View view){
		Intent intent = new Intent(this,AppLockActivity.class);
		startActivity(intent);
	}
}
