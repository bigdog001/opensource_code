package com.lee.custom.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lee.data.infos.Group;
import com.lee.tuliao.activity.R;

public class GroupAdapter extends BaseAdapter {

	protected LayoutInflater inflater;
	protected List<Group> list;

	public GroupAdapter(LayoutInflater inflater, List<Group> list) {
		super();
		this.inflater = inflater;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Group getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		if (convertView == null) {
			view = inflater.inflate(R.layout.group, null);
		} else {
			view = convertView;
		}
		TextView name = (TextView) view.findViewById(R.id.groupname);
		TextView notice = (TextView) view.findViewById(R.id.groupnotice);
		name.setText(getItem(position).getName());
		notice.setText(getItem(position).getNotice());
		return view;
	}

}
