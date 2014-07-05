package com.itheima.mobilesafe;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.mobilesafe.domain.ContactInfo;
import com.itheima.mobilesafe.engine.ContactInfoProvider;

public class SelectContactActivity extends Activity {
	private List<ContactInfo> contactInfos;
	private ListView lv_select_contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_contact);
		// 获取系统的联系人了.
		contactInfos = ContactInfoProvider.getContactInfos(this);
		lv_select_contact = (ListView) findViewById(R.id.lv_select_contact);
		lv_select_contact.setAdapter(new SelectContactAdapter());
		
		lv_select_contact.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ContactInfo info = contactInfos.get(position);
				String phone = info.getPhone(); 
				Intent  data = new Intent();
				data.putExtra("phone", phone);
				setResult(0, data);//设置了结果数据
				finish();//关闭到当前的activity.
			}
		});
	}

	private class SelectContactAdapter extends BaseAdapter {

		public int getCount() {
			return contactInfos.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getApplicationContext(),
					R.layout.list_select_contact_item, null);
			TextView tv_name = (TextView) view
					.findViewById(R.id.tv_contact_name);
			TextView tv_phone = (TextView) view
					.findViewById(R.id.tv_contact_phone);
			ContactInfo info = contactInfos.get(position);
			tv_name.setText(info.getName());
			tv_phone.setText(info.getPhone());
			return view;
		}

	}
}
