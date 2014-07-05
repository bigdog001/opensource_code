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

	// ���漯�� ����������еķ�����Ϣ
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
		 * �����ж��ٸ����� �ȱ�����.
		 */
		public int getGroupCount() {
			if (groupItems == null) {
				groupItems = CommonNumDao.getGroupInfos();
			}
			return groupItems.size();
		}

		/**
		 * ����ÿ�����������ж��ٸ�����
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
		 * ����ĳ��������ʾ��view����.
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
			tv.setText("    " + groupItems.get(groupPosition)); // �ĳ��˲�ѯ�ڴ�
			return tv;
		}

		/**
		 * ����ĳ�������ĳ�����ӵ�view����.
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
