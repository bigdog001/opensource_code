package com.lee.data.trans;

import java.io.Serializable;

public class MessageInfo implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final byte TYPE_USER = 1;
	public static final byte TYPE_GROUP = 2;
	private int sendId;
	private String name;
	private int receiveID;
	private String msg;
	private String time;
	private boolean selfInfo;
	private int msgType = TYPE_USER;

	public MessageInfo(int sendId, String name, String msg, String time) {
		super();
		this.sendId = sendId;
		this.name = name;
		this.msg = msg;
		this.time = time;
	}

	public MessageInfo(int sendId, String name, int receiveID, String msg,
			String time) {
		super();
		this.sendId = sendId;
		this.name = name;
		this.receiveID = receiveID;
		this.msg = msg;
		this.time = time;
	}

	public boolean isSelfInfo() {
		return selfInfo;
	}

	public void setSelfInfo(boolean selfInfo) {
		this.selfInfo = selfInfo;
	}

	public int getSendId() {
		return sendId;
	}

	public void setSendId(int sendId) {
		this.sendId = sendId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getReceiveID() {
		return receiveID;
	}

	public void setReceiveID(int receiveID) {
		this.receiveID = receiveID;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

}
