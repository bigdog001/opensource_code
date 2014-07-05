package com.lee.data.trans;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Queue;

import com.lee.custom.inter.ReceiveInfoListener;
import com.lee.data.infos.UserInfo;

public class Communication {

	private NetTransportWorker transportWorker;
	private MessageWorker messageWorker;
	private static Communication instance;
	private static MessageDigest md;

	private Communication() {
		transportWorker = new NetTransportWorker();
		transportWorker.start();
		messageWorker = new MessageWorker(transportWorker);
		messageWorker.start();
		try {
			if (md == null)
				md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String MD5(String strSrc) {
		byte[] bt = strSrc.getBytes();
		md.update(bt);
		String strDes = bytes2Hex(md.digest()); // to HexString
		return strDes;
	}

	private static String bytes2Hex(byte[] bts) {
		StringBuffer des = new StringBuffer();
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des.append("0");
			}
			des.append(tmp);
		}
		return des.toString();
	}

	public static Communication newInstance() {
		if (instance == null)
			instance = new Communication();
		return instance;
	}

	public NetTransportWorker getTransportWorker() {
		return transportWorker;
	}

	public MessageWorker getMessageWorker() {
		return messageWorker;
	}

	public void addReceiveInfoListener(ReceiveInfoListener listener) {
		transportWorker.addReceiveInfoListener(listener);
	}

	public void removeReceiveInfoListener(ReceiveInfoListener listener) {
		transportWorker.removeReceiveInfoListener(listener);
	}

	public void reconnect() {
		transportWorker.notify();
	}

	private boolean sendDataToServer(SendPacket packet) {
		try {
			if (transportWorker.writeBuf(packet.getData()))
				return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			packet.close();
		}
		return false;
	}

	/** 如果从不活动界面切换到聊天界面，此方法返回所有队列里面的信息数据包 **/
	public Queue<MessageInfo> getReceivePackets() {
		return transportWorker.receiveQueue;
	}

	public void addPacket(SendPacket packet) {
		messageWorker.addPacket(packet);
	}

	public void wakeUp() {
		messageWorker.wakeUp();
	}

	public void start_check_in() {
		messageWorker.start_check_in();
	}

	// 只负责发送数据包，返回包根据内容再通知是否成功
	/**
	 * 返回false则通信失败true通信成功
	 */
	public boolean register(String name, String pwd) {
		SendPacket packet = new SendPacket(ProtocolConst.CMD_REGISTER);
		packet.writeUTF(name);
		packet.writeUTF(MD5(pwd));
		packet.writeBoolean(true);// sex
		if (sendDataToServer(packet))
			return true;
		return false;
	}

	public boolean login(String name, String pwd) {
		SendPacket packet = new SendPacket(ProtocolConst.CMD_LOGIN);
		packet.writeUTF(name);
		packet.writeUTF(MD5(pwd));
		if (sendDataToServer(packet))
			return true;
		return false;
	}

	public boolean loginAgain() {
		SendPacket packet = new SendPacket(ProtocolConst.CMD_LOGIN);
		UserInfo info = transportWorker.self;
		packet.writeUTF(info.getName());
		packet.writeUTF(MD5(info.getPwd()));
		if (sendDataToServer(packet))
			return true;
		return false;
	}

	public boolean getAllList() {
		SendPacket packet = new SendPacket(ProtocolConst.CMD_GET_ALL_LIST);
		if (sendDataToServer(packet))
			return true;
		return false;
	}

	public String newSessionID() {
		return String.valueOf(System.currentTimeMillis());
	}

}
