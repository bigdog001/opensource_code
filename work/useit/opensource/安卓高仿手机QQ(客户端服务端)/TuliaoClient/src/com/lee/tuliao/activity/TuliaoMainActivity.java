package com.lee.tuliao.activity;

import java.util.HashMap;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lee.custom.inter.LayoutChangeListener;
import com.lee.data.infos.Group;
import com.lee.data.infos.UserInfo;
import com.lee.data.trans.ProtocolConst;
import com.lee.scroll.content.FriendListContent;
import com.lee.scroll.content.GroupListContent;
import com.lee.scroll.content.NearListContent;
import com.lee.scroll.layout.ScrollLayout;

public class TuliaoMainActivity extends TuliaoBaseActivity implements
		LayoutChangeListener, OnClickListener {

	protected ScrollLayout layout;
	protected FriendListContent friendListContent;
	protected GroupListContent groupListContent;
	protected NearListContent nearListContent;
	protected ImageView imageView;
	protected ImageView tab1;
	protected ImageView tab2;
	protected ImageView tab3;
	protected ImageView show_hide;
	protected ImageView main_self_head;
	protected TextView myname;
	protected TextView mymood;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		layout = (ScrollLayout) findViewById(R.id.scrolllayout);
		layout.addChangeListener(this);
		tab1 = (ImageView) findViewById(R.id.tab1);
		tab2 = (ImageView) findViewById(R.id.tab2);
		tab3 = (ImageView) findViewById(R.id.tab3);
		show_hide = (ImageView) findViewById(R.id.show_hide);
		imageView = (ImageView) findViewById(R.id.top_bar_select);
		main_self_head = (ImageView) findViewById(R.id.main_self_head);
		myname = (TextView) findViewById(R.id.myname);
		mymood = (TextView) findViewById(R.id.mymood);
		friendListContent = new FriendListContent(this, R.layout.friend_content);
		groupListContent = new GroupListContent(this, R.layout.group_content);
		nearListContent = new NearListContent(this, R.layout.near_content);
		friendListContent.setShow_hide(show_hide);
		groupListContent.setShow_hide(show_hide);
		groupListContent.addGroup(con.getTransportWorker().getGroup(0));
		layout.addView(nearListContent.getView());
		layout.addView(friendListContent.getView());
		layout.addView(groupListContent.getView());
		layout.setToScreen(1);
		// 请求好友列表
		con.getAllList();
		UserInfo self = con.getTransportWorker().getSelf();
		friendListContent.setSelfHead(self);
		main_self_head.setImageBitmap(self.getHead());
		myname.setText(self.getName());
		mymood.setText(self.getMood());
	}

	@Override
	public void doChange(int lastIndex, int currentIndex) {
		// TODO Auto-generated method stub
		if (lastIndex != currentIndex) {
			TranslateAnimation animation = null;
			LinearLayout layout = null;
			switch (currentIndex) {
			case 0:
				if (lastIndex == 1) {
					layout = (LinearLayout) tab1.getParent();
					animation = new TranslateAnimation(0, -layout.getWidth(),
							0, 0);
				} else if (lastIndex == 2) {
					layout = (LinearLayout) tab2.getParent();
					animation = new TranslateAnimation(layout.getLeft(),
							-((LinearLayout) tab1.getParent()).getWidth(), 0, 0);
				}
				break;
			case 1:
				if (lastIndex < 1) {
					// 左到中
					layout = (LinearLayout) tab1.getParent();
					animation = new TranslateAnimation(-layout.getWidth(), 0,
							0, 0);
				} else if (lastIndex > 1) {
					// 右到中
					layout = (LinearLayout) tab2.getParent();
					animation = new TranslateAnimation(layout.getLeft(), 0, 0,
							0);
				}
				break;
			case 2:
				if (lastIndex == 1) {
					layout = (LinearLayout) tab2.getParent();
					animation = new TranslateAnimation(0, layout.getLeft(), 0,
							0);
				} else if (lastIndex == 0) {
					layout = (LinearLayout) tab2.getParent();
					animation = new TranslateAnimation(
							-((LinearLayout) tab1.getParent()).getWidth(),
							layout.getLeft(), 0, 0);
				}
				break;
			}
			animation.setDuration(300);
			animation.setFillAfter(true);
			imageView.startAnimation(animation);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == tab1) {
			layout.snapToScreen(0);
		} else if (v == tab2) {
			layout.snapToScreen(1);
		} else if (v == tab3) {
			layout.snapToScreen(2);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case ProtocolConst.CMD_GET_ALL_LIST_SUCESS: {
			HashMap<Integer, UserInfo> infos = (HashMap<Integer, UserInfo>) msg.obj;
			friendListContent.updateFriendList(infos);
		}
			break;
		case ProtocolConst.CMD_GET_ALL_LIST_FAILD:
			makeTextShort("好友列表获取失败");
			break;
		case ProtocolConst.CMD_HAS_USER_ONLINE: {
			HashMap<Integer, UserInfo> infos = (HashMap<Integer, UserInfo>) msg.obj;
			friendListContent.updateFriendList(infos);
		}
			break;
		case ProtocolConst.CMD_HAS_USER_OFFLINE: {
			HashMap<Integer, UserInfo> infos = (HashMap<Integer, UserInfo>) msg.obj;
			friendListContent.updateFriendList(infos);
		}
			break;
		case ProtocolConst.CMD_RECEIVE_INFO_ON_MAIN: {
			friendListContent.notifyDataSetChanged();
			if (friendListContent.hasMsgInfo() != null) {
				UserInfo info = friendListContent.hasMsgInfo();
				AnimationDrawable drawable = (AnimationDrawable) getResources()
						.getDrawable(R.anim.loadding);
				show_hide.setBackgroundDrawable(drawable);
				Drawable show = new BitmapDrawable(info.getHead());
				drawable.addFrame(show, 500);
				drawable.start();
			} else {
				Group group = groupListContent.hasMessage();
				if (group != null) {
					AnimationDrawable drawable = (AnimationDrawable) getResources()
							.getDrawable(R.anim.loadding);
					show_hide.setBackgroundDrawable(drawable);
					Drawable show = getResources().getDrawable(
							R.drawable.group_head);
					drawable.addFrame(show, 500);
					drawable.start();
				}
			}
		}
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK)
			exit();
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == ProtocolConst.CMD_HAS_USER_ONLINE) {
			friendListContent.updateFriendList(con.getTransportWorker()
					.getAll_user());
		} else if (resultCode == ProtocolConst.CMD_HAS_USER_OFFLINE) {
			friendListContent.updateFriendList(con.getTransportWorker()
					.getAll_user());
		}
	}

}
