package com.lee.custom.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lee.data.infos.GroupInfo;
import com.lee.data.infos.UserInfo;
import com.lee.tuliao.activity.R;

public class FriendAdapter extends BaseExpandableListAdapter {

	protected Context context;
	private ArrayList<GroupInfo> mGroupData;
	private ArrayList<ArrayList<UserInfo>> mChildData;
	private LayoutInflater mInflater;

	public FriendAdapter(Context context, ArrayList<GroupInfo> mGroupData,
			ArrayList<ArrayList<UserInfo>> mChildData) {
		super();
		this.context = context;
		this.mGroupData = mGroupData;
		this.mChildData = mChildData;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void update(ArrayList<GroupInfo> mGroupData,
			ArrayList<ArrayList<UserInfo>> mChildData) {
		this.mGroupData = mGroupData;
		this.mChildData = mChildData;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return mGroupData.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return mChildData.get(groupPosition).size();
	}

	@Override
	public GroupInfo getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return mGroupData.get(groupPosition);
	}

	@Override
	public UserInfo getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return mChildData.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		if (convertView == null) {
			view = mInflater.inflate(R.layout.group_item, null);
		} else {
			view = convertView;
		}
		GroupInfo info = getGroup(groupPosition);
		TextView groupName = (TextView) view.findViewById(R.id.groupname);
		TextView onlineno = (TextView) view.findViewById(R.id.onlineno);
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		buffer.append(info.getTotal());
		buffer.append("]");
		groupName.setText(info.getName());
		onlineno.setText(buffer.toString());
		ImageView chat_paopao = (ImageView) view.findViewById(R.id.chat_paopao);
		if (info.hasNewMsg())
			chat_paopao.setVisibility(View.VISIBLE);
		else
			chat_paopao.setVisibility(View.INVISIBLE);
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		if (convertView == null) {
			view = mInflater.inflate(R.layout.child_item, null);
		} else {
			view = convertView;
		}
		UserInfo info = getChild(groupPosition, childPosition);
		ImageView child_item_head = (ImageView) view
				.findViewById(R.id.child_item_head);
		child_item_head.setImageBitmap(info.getHead());
		TextView userName = (TextView) view.findViewById(R.id.username);
		userName.setText(info.getName());
		TextView mood = (TextView) view.findViewById(R.id.mood);
		mood.setText(info.getMood());
		TextView msgnum = (TextView) view.findViewById(R.id.msgnum);
		if (info.getReceiveMsgNo() > 0) {
			msgnum.setText(String.valueOf(info.getReceiveMsgNo()));
			msgnum.setVisibility(View.VISIBLE);
		} else {
			msgnum.setVisibility(View.INVISIBLE);
		}
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
