package com.jj.corner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener {
	private MyListView listView_1, listView_2, listView_3;
	private ArrayList<Map<String, String>> listData, listData2, listData3;
	private SimpleAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		listView_1 = (MyListView) findViewById(R.id.mylistview_1);
		listView_2 = (MyListView) findViewById(R.id.mylistview_2);
		listView_3 = (MyListView) findViewById(R.id.mylistview_3);

		listView_1.setAdapter(getSimpleAdapter_1());
		listView_2.setAdapter(getSimpleAdapter_2());
		listView_3.setAdapter(getSimpleAdapter_3());

		listView_1.setOnItemClickListener(this);
		listView_2.setOnItemClickListener(this);
		listView_3.setOnItemClickListener(this);

		setListViewHeightBasedOnChildren(listView_1);
		setListViewHeightBasedOnChildren(listView_2);
		setListViewHeightBasedOnChildren(listView_3);
	}

	/**
	 * 设置第一列数据
	 */
	private SimpleAdapter getSimpleAdapter_1() {
		listData = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("text", "天气动画");
		listData.add(map);

		map = new HashMap<String, String>();
		map.put("text", "通知栏天气");
		listData.add(map);

		return new SimpleAdapter(MainActivity.this, listData,
				R.layout.list_item, new String[] { "text" },
				new int[] { R.id.tv_list_item });

	}

	/**
	 * 设置第二列数据
	 */
	private SimpleAdapter getSimpleAdapter_2() {
		listData2 = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("text", "桌面插件");
		listData2.add(map);

		map = new HashMap<String, String>();
		map.put("text", "绑定微博");
		listData2.add(map);

		map = new HashMap<String, String>();
		map.put("text", "天气分享");
		listData2.add(map);

		map = new HashMap<String, String>();
		map.put("text", "通知与提示");
		listData2.add(map);

		map = new HashMap<String, String>();
		map.put("text", "定时播报");
		listData2.add(map);

		return new SimpleAdapter(MainActivity.this, listData2,
				R.layout.list_item, new String[] { "text" },
				new int[] { R.id.tv_list_item });

	}

	/**
	 * 设置第三列数据
	 */
	private SimpleAdapter getSimpleAdapter_3() {
		listData3 = new ArrayList<Map<String, String>>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("text", "检查新版本");
		listData3.add(map);

		map = new HashMap<String, String>();
		map.put("text", "发送建议");
		listData3.add(map);

		map = new HashMap<String, String>();
		map.put("text", "帮助");
		listData3.add(map);

		map = new HashMap<String, String>();
		map.put("text", "关于");
		listData3.add(map);

		return new SimpleAdapter(MainActivity.this, listData3,
				R.layout.list_item, new String[] { "text" },
				new int[] { R.id.tv_list_item });

	}

	/***
	 * 动态设置listview的高度
	 * 
	 * @param listView
	 */
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// params.height += 5;// if without this statement,the listview will be
		// a
		// little short
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		if (parent == listView_1) {
			Map<String, String> map = listData.get(position);
			Toast.makeText(MainActivity.this, map.get("text"), 1).show();
		} else if (parent == listView_2) {
			Map<String, String> map = listData2.get(position);
			Toast.makeText(MainActivity.this, map.get("text"), 1).show();
		} else if (parent == listView_3) {
			Map<String, String> map = listData3.get(position);
			Toast.makeText(MainActivity.this, map.get("text"), 1).show();
		}

	}

}
