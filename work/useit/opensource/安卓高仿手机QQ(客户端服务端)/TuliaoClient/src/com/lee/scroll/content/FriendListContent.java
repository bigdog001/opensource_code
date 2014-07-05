package com.lee.scroll.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;

import com.lee.custom.adapter.FriendAdapter;
import com.lee.data.infos.GroupInfo;
import com.lee.data.infos.UserInfo;
import com.lee.data.trans.Communication;
import com.lee.scroll.layout.ScrollContent;
import com.lee.tuliao.activity.R;
import com.lee.tuliao.activity.TuliaoChatActivity;

public class FriendListContent extends ScrollContent implements
		OnChildClickListener {

	protected ExpandableListView friends;
	protected FriendAdapter friendAdapter;
	protected ArrayList<GroupInfo> mGroupData;
	protected ArrayList<ArrayList<UserInfo>> mChildData;
	private static FriendListContent instance;
	private Bitmap bitmap;
	private ImageView show_hide;

	public FriendListContent(Activity activity, int resourceID) {
		super(activity, resourceID);
		// TODO Auto-generated constructor stub
		instance = this;
		friends = (ExpandableListView) findViewById(R.id.friendlist);
		friends.setOnChildClickListener(this);
		mGroupData = new ArrayList<GroupInfo>();
		mChildData = new ArrayList<ArrayList<UserInfo>>();
		bitmap = BitmapFactory.decodeResource(mRes, R.drawable.largehead);
		friendAdapter = new FriendAdapter(context, mGroupData, mChildData);
		friends.setAdapter(friendAdapter);
	}

	public void setShow_hide(ImageView show_hide) {
		this.show_hide = show_hide;
	}

	public void setSelfHead(UserInfo info) {
		info.setHead(createHead(info.getHeadID()));
	}

	public void updateFriendList(HashMap<Integer, UserInfo> infos) {
		mGroupData.clear();
		mChildData.clear();
		ArrayList<UserInfo> childs_online = new ArrayList<UserInfo>();
		ArrayList<UserInfo> childs_offline = new ArrayList<UserInfo>();
		GroupInfo online_group = new GroupInfo("在线好友");
		GroupInfo offline_group = new GroupInfo("离线好友");
		Iterator<Integer> keys = infos.keySet().iterator();
		while (keys.hasNext()) {
			UserInfo info = infos.get(keys.next());
			info.setHead(createHead(info.getHeadID()));
			if (info.getId() == Communication.newInstance()
					.getTransportWorker().getSelf().getId())
				continue;
			if (info.isOnline()) {
				childs_online.add(info);
				info.setGroupInfo(online_group);
			} else {
				childs_offline.add(info);
				info.setGroupInfo(offline_group);
			}
		}
		online_group.setTotal(childs_online.size());
		offline_group.setTotal(childs_offline.size());
		mChildData.add(childs_online);
		mChildData.add(childs_offline);

		mGroupData.add(online_group);
		mGroupData.add(offline_group);
		friendAdapter.update(mGroupData, mChildData);
		friendAdapter.notifyDataSetChanged();
	}

	public void notifyDataSetChanged() {
		friendAdapter.notifyDataSetChanged();
	}

	public static FriendListContent getInstance() {
		return instance;
	}

	private Bitmap createHead(int index) {
		int x = 40 * (index % 6);
		int y = 40 * (index / 6);
		Bitmap bit = Bitmap.createBitmap(bitmap, x, y, 40, 40);
		return bit;
	}

	protected UserInfo child;

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		child = friendAdapter.getChild(groupPosition, childPosition);
		child.setReceiveMsgNo(0);
		if (hasMsgInfo() == null) {
			AnimationDrawable drawable = (AnimationDrawable) mRes
					.getDrawable(R.anim.loadding);
			show_hide.setBackgroundDrawable(drawable);
		}
		notifyDataSetChanged();
		Intent intent = new Intent(context, TuliaoChatActivity.class);
		intent.putExtra("from", "friend");
		startActivityForResult(intent, 0);
		return false;
	}

	public UserInfo getChild() {
		return child;
	}

	public UserInfo hasMsgInfo() {
		ArrayList<UserInfo> online = mChildData.get(0);
		for (int i = 0; i < online.size(); i++) {
			UserInfo info = online.get(i);
			if (info.getReceiveMsgNo() > 0)
				return info;
		}
		return null;
	}

}
