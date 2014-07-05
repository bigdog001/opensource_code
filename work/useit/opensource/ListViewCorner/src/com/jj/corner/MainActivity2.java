package com.jj.corner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends Activity {
	private LinearLayout ll_main;
	private TableLayout tableLayout;

	private LinearLayout.LayoutParams layoutParams;

	private static final String MSG_0[] = { "jjhappyforever" };

	private static final String MSG_1[] = { "天气动画", "通知栏天气" };

	private static final String MSG_2[] = { "桌面插件", "绑定微博", "天气分享", "通知与提示",
			"定时播报" };

	private static final String MSG_3[] = { "检查新版本", "发送建议", "帮助", "关于" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main2);
		ll_main = (LinearLayout) findViewById(R.id.ll_main);
		showTable();
	}

	/***
	 * 显示table
	 */
	public void showTable() {

		layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		layoutParams.bottomMargin = 30;
		layoutParams.topMargin = 10;
		ll_main.addView(getTable(MSG_0), layoutParams);

		ll_main.addView(getTable(MSG_1), layoutParams);

		ll_main.addView(getTable(MSG_2), layoutParams);

		ll_main.addView(getTable(MSG_3), layoutParams);
	}

	/***
	 * 获取Table
	 * 
	 * @param array
	 * @return
	 */
	int i;

	public TableLayout getTable(final String[] array) {

		tableLayout = new TableLayout(this);
		tableLayout.setLayoutParams(layoutParams);
		tableLayout.setStretchAllColumns(true);

		for (i = 0; i < array.length; i++) {
			TableRow tableRow = new TableRow(this);
			View view = getView(array[i], i, array.length);
			tableRow.addView(view);

			tableLayout.addView(tableRow);

		}
		return tableLayout;

	}

	/****
	 * 
	 * @param msg
	 *            显示信息
	 * @param current_Id
	 *            当前个数
	 * @param totle_Num
	 *            总个数
	 * @return
	 */
	public View getView(final String msg, int current_Id, int totle_Num) {

		LinearLayout linearLayout = new LinearLayout(this);

		LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParams1.height = 1;
		linearLayout.setOrientation(1);
		// 创建分割线
		View line = new View(this);
		line.setLayoutParams(layoutParams1);
		line.setBackgroundColor(getResources().getColor(R.color.black));

		View view = LayoutInflater.from(MainActivity2.this).inflate(
				R.layout.item, null);
		view.setBackgroundDrawable(new BitmapDrawable());

		view.setFocusable(true);
		view.setClickable(true);
		TextView textView = (TextView) view.findViewById(R.id.tv_list_item);
		textView.setText(msg);
		textView.setTextSize(20);

		// 事件执行（如果Table布局 事件要写在Row里面的view）
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (msg.equals(MSG_0[0])) {
					startActivity(new Intent(MainActivity2.this,
							MainActivity.class));
				} else {
					Toast.makeText(MainActivity2.this, msg, 1).show();
				}

			}
		});

		// 只有一项
		if (totle_Num == 1) {
			view.setBackgroundResource(R.drawable.default_selector);
			return view;
		}
		// 第一项
		else if (current_Id == 0) {
			view.setBackgroundResource(R.drawable.list_top_selector);
		}
		// 最后一项
		else if (current_Id == totle_Num - 1) {
			view.setBackgroundResource(R.drawable.list_bottom_selector);
			line.setVisibility(View.GONE);
		} else
			view.setBackgroundResource(R.drawable.list_center_selector);

		linearLayout.addView(view);
		linearLayout.addView(line);

		return linearLayout;
	}

}
