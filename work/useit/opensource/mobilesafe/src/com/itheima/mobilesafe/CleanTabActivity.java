package com.itheima.mobilesafe;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class CleanTabActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clean);
		TabHost tabHost = getTabHost();

		TabSpec tab1 = tabHost.newTabSpec("缓存清理");
		TabSpec tab2 = tabHost.newTabSpec("SD卡清理");

		tab1.setIndicator(getTabView(R.drawable.tab1, "缓存清理"));
		tab2.setIndicator(getTabView(R.drawable.tab2, "SD卡清理"));
		
		tab1.setContent(new Intent(this,CleanCacheActivity.class));
		tab2.setContent(new Intent(this,CleanSDActivity.class));
		
		
		tabHost.addTab(tab1);
		tabHost.addTab(tab2);

	}

	private View getTabView(int icon, String text) {
		View view = View.inflate(getApplicationContext(), R.layout.tab_clean,
				null);
		TextView tv = (TextView) view.findViewById(R.id.tv_tab);
		tv.setText(text);
		ImageView iv = (ImageView) view.findViewById(R.id.iv_tab);
		iv.setImageResource(icon);
		return view;
	}

}
