package com.itheima.mobilesafe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import com.itheima.mobilesafe.db.dao.CommonNumDao;

public class CommonNumberActivity extends Activity {
	private ExpandableListView elv;

	// 缓存集合 用来存放所有的分组信息
	private List<String> groupItems;

	private Map<Integer, List<String>> childrenCaches;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_num);
		elv = (ExpandableListView) findViewById(R.id.elv);
		childrenCaches = new HashMap<Integer, List<String>>();
		elv.setAdapter(new MyAdapter());

		elv.setOnChildClickListener(new OnChildClickListener() {

			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				TextView tv = (TextView) v;
				String str = tv.getText().toString();
				String number = str.split("\n")[1];
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:" + number));
				startActivity(intent);
				return false;
			}
		});

	}

	private class MyAdapter extends BaseExpandableListAdapter {

		/**
		 * 返回有多少个分组 先被调用.
		 */
		public int getGroupCount() {
			if (groupItems == null) {
				groupItems = CommonNumDao.getGroupInfos();
			}
			return groupItems.size();
		}

		/**
		 * 返回每个分组里面有多少个孩子
		 */
		public int getChildrenCount(int groupPosition) {

			List<String> childInfos;
			if (childrenCaches.containsKey(groupPosition)) {
				childInfos = childrenCaches.get(groupPosition);
			} else {
				childInfos = CommonNumDao
						.getChildrenInfosByGroupPostion(groupPosition);
				childrenCaches.put(groupPosition, childInfos);
			}
			return childInfos.size();
		}

		public Object getGroup(int groupPosition) {
			return null;
		}

		public Object getChild(int groupPosition, int childPosition) {
			return null;
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public boolean hasStableIds() {
			return false;
		}

		/**
		 * 返回某个分组显示的view对象.
		 */
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			TextView tv ;
			if(convertView==null){
			tv = new TextView(getApplicationContext());
			}else{
				tv = (TextView) convertView;
			}
			tv.setTextSize(28);
			tv.setTextColor(Color.RED);
			// tv.setText("    " + CommonNumDao.getGroupName(groupPosition));
			tv.setText("    " + groupItems.get(groupPosition)); // 改成了查询内存
			return tv;
		}

		/**
		 * 返回某个分组的某个孩子的view对象.
		 */
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			TextView tv ;
			if(convertView==null){
			tv = new TextView(getApplicationContext());
			}else{
				tv = (TextView) convertView;
			}
			tv.setTextSize(20);
			tv.setTextColor(Color.BLACK);
//			tv.setText(CommonNumDao.getChildInfoByPosition(groupPosition,
//					childPosition));
			tv.setText(childrenCaches.get(groupPosition).get(childPosition));
			return tv;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	}
}
