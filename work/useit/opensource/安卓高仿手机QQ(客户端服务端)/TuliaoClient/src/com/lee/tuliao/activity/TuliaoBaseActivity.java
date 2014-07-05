package com.lee.tuliao.activity;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.lee.data.trans.Communication;
import com.lee.data.trans.ProtocolConst;

public abstract class TuliaoBaseActivity extends Activity {

	protected static LinkedList<TuliaoBaseActivity> queue = new LinkedList<TuliaoBaseActivity>();
	public static final String communication = "请稍后，正在通信……";
	public static final String communication_faild = "对不起，通信失败！";
	protected static Communication con;
	private static MediaPlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (con == null)
			con = Communication.newInstance();
		if (!queue.contains(this))
			queue.add(this);
		if (player == null) {
			player = MediaPlayer.create(this, R.raw.msg);
			try {
				player.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static TuliaoBaseActivity getActivity(int index) {
		if (index < 0 || index >= queue.size())
			throw new IllegalArgumentException("out of queue");
		return queue.get(index);
	}

	public static TuliaoBaseActivity getCurrentActivity() {
		return queue.getLast();
	}

	public void makeTextShort(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	public void makeTextLong(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	public abstract void processMessage(Message msg);

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		queue.removeLast();
	}

	public static void sendMessage(int cmd, String text) {
		Message msg = new Message();
		msg.obj = text;
		msg.what = cmd;
		sendMessage(msg);
	}

	public static void sendMessage(Message msg) {
		handler.sendMessage(msg);
	}

	public static void sendEmptyMessage(int what) {
		handler.sendEmptyMessage(what);
	}

	private static Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case ProtocolConst.CMD_SYSTEM_INFO: {
				queue.getLast().makeTextShort(msg.obj.toString());
			}
				break;
			case ProtocolConst.CMD_SYSTEM_ERROR: {
				queue.getLast().makeTextShort(msg.obj.toString());
			}
				break;
			case ProtocolConst.CMD_PLAY_MSG: {
				playMsg();
			}
				break;
			default:
				queue.getLast().processMessage(msg);
				break;
			}
		}

	};

	private static void playMsg() {
		try {
			player.start();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getTime() {
		return DateFormat.format("hh:mm:ss", Calendar.getInstance()).toString();
	}

	public void exit() {
		while (queue.size() > 0)
			queue.getLast().finish();
	}

}
