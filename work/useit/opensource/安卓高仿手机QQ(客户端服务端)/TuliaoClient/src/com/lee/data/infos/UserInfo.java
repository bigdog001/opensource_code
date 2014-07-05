package com.lee.data.infos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import com.lee.data.trans.MessageInfo;

import android.graphics.Bitmap;
import android.text.format.DateFormat;

public class UserInfo implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int id;
	protected String name;
	protected String pwd;
	protected boolean sex;
	protected String mood;
	protected Bitmap head;
	protected int headID;
	protected String city;
	protected String region;
	protected double latitude;
	protected double longitude;
	/** 如果登录就为true，如果掉线则继续登录 **/
	protected boolean login;
	protected int receiveMsgNo;
	protected boolean online;
	/** 聊天信息 **/
	protected ArrayList<MessageInfo> messageInfos;
	protected GroupInfo groupInfo;

	public UserInfo() {
		super();
		messageInfos = new ArrayList<MessageInfo>();
	}

	public UserInfo(GroupInfo groupInfo, int id, String name, boolean sex,
			String mood) {
		this();
		this.groupInfo = groupInfo;
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.mood = mood;
	}

	public UserInfo(GroupInfo groupInfo, int id, String name, boolean sex,
			String mood, Bitmap head) {
		this();
		this.groupInfo = groupInfo;
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.mood = mood;
		this.head = head;
	}

	public GroupInfo getGroupInfo() {
		return groupInfo;
	}

	public void setGroupInfo(GroupInfo groupInfo) {
		this.groupInfo = groupInfo;
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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	public int getHeadID() {
		return headID;
	}

	public void setHeadID(int headID) {
		this.headID = headID;
	}

	public Bitmap getHead() {
		return head;
	}

	public void setHead(Bitmap head) {
		this.head = head;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public static String timeNow() {
		return DateFormat.format("hh:mm:ss", Calendar.getInstance()).toString();
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public int getReceiveMsgNo() {
		return receiveMsgNo;
	}

	public void setReceiveMsgNo(int receiveMsgNo) {
		this.receiveMsgNo = receiveMsgNo;
		if (receiveMsgNo == 0)
			groupInfo.setNewMsg(false);
	}

	public void receiveMsgNoPP() {
		++receiveMsgNo;
		groupInfo.setNewMsg(true);
	}

}
