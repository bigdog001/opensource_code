package com.lee.custom.adapter;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lee.data.trans.MessageInfo;
import com.lee.tuliao.activity.R;

public class ChatContentAdapter extends BaseAdapter {

	protected LayoutInflater mInflater;
	protected List<MessageInfo> list;
	protected Resources res;

	public ChatContentAdapter(Context context, List<MessageInfo> list) {
		super();
		this.mInflater = LayoutInflater.from(context);
		this.list = list;
		res = context.getResources();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public MessageInfo getItem(int position) {
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
			view = mInflater.inflate(R.layout.chat_content_item, null);
		} else {
			view = convertView;
		}
		MessageInfo info = getItem(position);
		TextView show_name = (TextView) view.findViewById(R.id.show_name);
		show_name.setText(info.getName());
		if (info.isSelfInfo())
			show_name.setTextColor(res.getColor(R.color.chat_myself));
		else
			show_name.setTextColor(res.getColor(R.color.chat_other));
		TextView show_time = (TextView) view.findViewById(R.id.show_time);
		show_time.setText(info.getTime());
		TextView message = (TextView) view.findViewById(R.id.message);
		message.setText(info.getMsg());
		return view;
	}

}
