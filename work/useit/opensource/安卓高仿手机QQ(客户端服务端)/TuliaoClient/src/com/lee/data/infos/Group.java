package com.lee.data.infos;

import java.io.Serializable;
import java.util.ArrayList;

import com.lee.data.trans.MessageInfo;

/** »∫°¢◊È¡ƒÃÏ “ **/
public class Group implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String notice;
	protected int receiveMsgNo;
	protected ArrayList<MessageInfo> messageInfos;

	public Group(String name, String notice) {
		super();
		this.name = name;
		this.notice = notice;
		messageInfos = new ArrayList<MessageInfo>();
	}

	public void addMessageInfo(MessageInfo info) {
		messageInfos.add(info);
	}

	public ArrayList<MessageInfo> getMessageInfos() {
		return messageInfos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public void setMessageInfos(ArrayList<MessageInfo> messageInfos) {
		this.messageInfos = messageInfos;
	}

	public void setReceiveMsgNo(int receiveMsgNo) {
		this.receiveMsgNo = receiveMsgNo;
	}

	public void receiveMsgNoPP() {
		++receiveMsgNo;
	}

	public int getReceiveMsgNo() {
		return receiveMsgNo;
	}

}
