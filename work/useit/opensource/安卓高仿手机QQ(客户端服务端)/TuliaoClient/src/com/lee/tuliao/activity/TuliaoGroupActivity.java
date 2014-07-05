package com.lee.tuliao.activity;

import java.util.Iterator;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lee.custom.adapter.ChatContentAdapter;
import com.lee.custom.inter.ReceiveInfoListener;
import com.lee.data.infos.Group;
import com.lee.data.infos.UserInfo;
import com.lee.data.trans.MessageInfo;
import com.lee.data.trans.ProtocolConst;
import com.lee.data.trans.SendPacket;
import com.lee.scroll.content.GroupListContent;

public class TuliaoGroupActivity extends TuliaoBaseActivity implements
		OnClickListener, ReceiveInfoListener {

	protected TextView chat_name;
	protected TextView chat_mood;
	protected ImageView chat_item_head;
	protected Button chat_quit;
	protected Button chat_send;
	protected ListView chat_list;
	protected ChatContentAdapter adapter;
	protected EditText chat_input;

	private Group target;
	protected int receiveID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		chat_name = (TextView) findViewById(R.id.chat_name);
		chat_mood = (TextView) findViewById(R.id.chat_mood);
		chat_item_head = (ImageView) findViewById(R.id.chat_item_head);
		chat_item_head.setBackgroundResource(R.drawable.group_head);
		chat_quit = (Button) findViewById(R.id.chat_quit);
		chat_send = (Button) findViewById(R.id.chat_send);
		chat_list = (ListView) findViewById(R.id.chat_list);
		chat_input = (EditText) findViewById(R.id.chat_input);
		Group group = GroupListContent.getInstance().getGroup();
		if (group != null) {
			receiveID = group.getId();
			chat_name.setText(group.getName());
			chat_mood.setText(group.getNotice());
		}
		target = group;
		Iterator<MessageInfo> it = con.getReceivePackets().iterator();
		while (it.hasNext()) {
			// 获取信息队列里面所有没有显示的信息
			MessageInfo temp = it.next();
			if (temp.getMsgType() == MessageInfo.TYPE_GROUP) {
				target.addMessageInfo(temp);
				con.getReceivePackets().poll();
			}
		}
		adapter = new ChatContentAdapter(this, target.getMessageInfos());
		chat_list.setAdapter(adapter);
		con.addReceiveInfoListener(this);// 注册接收信息监听
	}

	public void addSelfMessage() {
		String msg = chat_input.getText().toString().trim();
		if (!msg.equals("")) {
			UserInfo info = con.getTransportWorker().getSelf();
			MessageInfo messageInfo = new MessageInfo(info.getId(),
					info.getName(), receiveID, msg, getTime());
			SendPacket packet = new SendPacket(
					ProtocolConst.CMD_SEND_INFO_TO_GROUP);
			messageInfo.setSelfInfo(true);
			packet.writeInt(messageInfo.getSendId());
			packet.writeUTF(messageInfo.getMsg());
			packet.writeUTF(messageInfo.getTime());
			con.getMessageWorker().addPacket(packet);
			target.addMessageInfo(messageInfo);
		} else {
			makeTextShort("不能发送空内容");
		}
		chat_input.setText("");
		adapter.notifyDataSetChanged();
	}

	public void addMessageToUI(MessageInfo info) {
		target.addMessageInfo(info);// 存放临时会话列表
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == chat_send) {
			addSelfMessage();
		} else if (v == chat_quit) {
			finish();
		}
	}

	private int cmd;

	@Override
	public void processMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case ProtocolConst.CMD_HAS_USER_ONLINE:
			cmd = ProtocolConst.CMD_HAS_USER_ONLINE;
			break;
		case ProtocolConst.CMD_HAS_USER_OFFLINE:
			cmd = ProtocolConst.CMD_HAS_USER_OFFLINE;
			break;
		case ProtocolConst.CMD_UPDATE_RECEIVE_INFO_FROM_GROUP:
			MessageInfo info = (MessageInfo) msg.obj;
			addMessageToUI(info);
			break;
		}
	}

	@Override
	public boolean receive(MessageInfo info) {
		// TODO Auto-generated method stub
		// 返回true才能从队列中移除
		if (info.getMsgType() == MessageInfo.TYPE_GROUP) {
			Message msg = new Message();
			msg.what = ProtocolConst.CMD_UPDATE_RECEIVE_INFO_FROM_GROUP;
			msg.obj = info;
			sendMessage(msg);
			return true;
		}
		return false;
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		setResult(cmd);
		// 一定要移除，不然不是当前显示所有队列信息全部被poll
		con.removeReceiveInfoListener(this);
		super.finish();
		sendEmptyMessage(ProtocolConst.CMD_RECEIVE_INFO_ON_MAIN);
	}

}
