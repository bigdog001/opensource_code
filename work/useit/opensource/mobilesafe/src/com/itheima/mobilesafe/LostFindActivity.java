package com.itheima.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LostFindActivity extends Activity {
	private SharedPreferences sp;
	private TextView tv_lostfind_number;
	private ImageView iv_lostfind_status;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		//�ж��û��Ƿ���й�������.
		if(isSetup()){
			setContentView(R.layout.activity_lost_find);
			tv_lostfind_number = (TextView) findViewById(R.id.tv_lostfind_number);
			iv_lostfind_status = (ImageView) findViewById(R.id.iv_lostfind_status);
			String safenumber = sp.getString("safenumber", "");
			tv_lostfind_number.setText(safenumber);
			
			boolean protect = sp.getBoolean("protect", false);
			if(protect){
				iv_lostfind_status.setImageResource(R.drawable.lock);
			}else{
				iv_lostfind_status.setImageResource(R.drawable.unlock);
			}
			
		}else{
			finish();
			Intent intent = new Intent(this,Setup1Activity.class);
			startActivity(intent);
		}

	}

	// ����ѡ��˵�
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.lost_find_menu, menu);
		return true;
	}

	//�˵��ĵ���¼�
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.item_change_name:
	        AlertDialog.Builder builder = new Builder(this);
	        builder.setTitle("�����ֻ���������");
	        
	    	final EditText et = new EditText(this);
	    	et.setHint("�������µ�����");
	    	builder.setView(et);
	    	builder.setPositiveButton("ȷ��", new OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					String newname = et.getText().toString().trim();
					Editor editor = sp.edit();
					editor.putString("newname", newname);
					editor.commit();
				}
			});
	    	builder.show();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	/**
	 * �ж��û��Ƿ���й�������.
	 * @return
	 */
	private boolean  isSetup(){
		return sp.getBoolean("issetup", false);
	}
	
	/**
	 * ���½���������
	 */
	public void reEntrySetup(View view){
		finish();
		Intent intent = new Intent(this,Setup1Activity.class);
		startActivity(intent);
	}
}
