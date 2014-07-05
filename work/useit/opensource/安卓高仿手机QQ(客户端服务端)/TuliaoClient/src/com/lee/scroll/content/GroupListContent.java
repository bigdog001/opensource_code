package com.lee.scroll.content;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.lee.custom.adapter.GroupAdapter;
import com.lee.data.infos.Group;
import com.lee.scroll.layout.ScrollContent;
import com.lee.tuliao.activity.R;
import com.lee.tuliao.activity.TuliaoGroupActivity;

public class GroupListContent extends ScrollContent implements
		OnItemClickListener {

	protected ListView group_list;
	protected GroupAdapter adapter;
	private static GroupListContent instance;
	private ImageView show_hide;
	private ArrayList<Group> list;

	public GroupListContent(Activity activity, int resourceID) {
		super(activity, resourceID);
		// TODO Auto-generated constructor stub
		instance = this;
		group_list = (ListView) findViewById(R.id.group_list);
		list = new ArrayList<Group>();
		adapter = new GroupAdapter(activity.getLayoutInflater(), list);
		group_list.setAdapter(adapter);
		group_list.setOnItemClickListener(this);
	}

	public void addGroup(Group group) {
		list.add(group);
	}

	public static GroupListContent getInstance() {
		return instance;
	}

	public void setShow_hide(ImageView show_hide) {
		this.show_hide = show_hide;
	}

	public Group hasMessage() {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getReceiveMsgNo() > 0)
				return list.get(i);
		}
		return null;
	}

	protected Group group;
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		group = adapter.getItem(position);
		group.setReceiveMsgNo(0);
		if (hasMessage() == null) {
			AnimationDrawable drawable = (AnimationDrawable) mRes
					.getDrawable(R.anim.loadding);
			show_hide.setBackgroundDrawable(drawable);
		}
		Intent intent = new Intent(context, TuliaoGroupActivity.class);
		intent.putExtra("from", "group");
		startActivityForResult(intent, 0);
	}

	public Group getGroup() {
		return group;
	}

}
