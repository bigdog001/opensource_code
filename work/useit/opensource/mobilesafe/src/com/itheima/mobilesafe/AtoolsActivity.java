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
	
	//开启号码归属地查询界面.
	public void numberAddressQuery(View view){
		Intent intent = new Intent(this,NumberQueryActivity.class);
		startActivity(intent);
	}
	//开启常用号码查询界面
	public void commonNumQuery(View view){
		
		Intent intent = new Intent(this,CommonNumberActivity.class);
		startActivity(intent);
	}
	//短信的备份
	public void backupSms(View view){
//		new MyAsyncTask() {
//			ProgressDialog pd;
//			@Override
//			public void onPreExecute() {
//				pd = new ProgressDialog(AtoolsActivity.this);
//				pd.setMessage("正在备份短信");
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
		//params 异步任务执行需要的参数
		//Progess 执行的进度
		//result 执行后的结果
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
				pd.setMessage("正在备份短信");
				pd.show();
				super.onPreExecute();
			}

			@Override
			protected void onPostExecute(Boolean result) {
				pd.dismiss();
				if(result){
					Toast.makeText(getApplicationContext(), "备份成功", 0).show();
				}else{
					Toast.makeText(getApplicationContext(), "备份失败", 0).show();
				}
				super.onPostExecute(result);
			}
			
		}.execute("backup.xml");
		
	}
	
	/**
	 * 进入程序锁界面
	 */
	public void enterAppLock(View view){
		Intent intent = new Intent(this,AppLockActivity.class);
		startActivity(intent);
	}
}
