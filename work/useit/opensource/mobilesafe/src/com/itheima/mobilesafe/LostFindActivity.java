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
		//判断用户是否进行过设置向导.
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

	// 创建选项菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.lost_find_menu, menu);
		return true;
	}

	//菜单的点击事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.item_change_name:
	        AlertDialog.Builder builder = new Builder(this);
	        builder.setTitle("更改手机防盗名称");
	        
	    	final EditText et = new EditText(this);
	    	et.setHint("请输入新的名称");
	    	builder.setView(et);
	    	builder.setPositiveButton("确定", new OnClickListener() {
				
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
	 * 判断用户是否进行过设置向导.
	 * @return
	 */
	private boolean  isSetup(){
		return sp.getBoolean("issetup", false);
	}
	
	/**
	 * 重新进入设置向导
	 */
	public void reEntrySetup(View view){
		finish();
		Intent intent = new Intent(this,Setup1Activity.class);
		startActivity(intent);
	}
}
