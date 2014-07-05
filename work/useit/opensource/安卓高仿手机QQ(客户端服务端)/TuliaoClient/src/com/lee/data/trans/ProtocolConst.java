package com.lee.data.trans;

public class ProtocolConst {

	// protected static final String ip = "192.168.1.102";
	protected static final String ip = "oraysain.vicp.net";
	// protected static final String ip = "192.168.0.117";
	protected static final int port = 8888;
	public static final int CMD_REGISTER = 1;
	public static final int CMD_LOGIN = 2;
	public static final int CMD_CHECK_IN = 3;
	public static final int CMD_GET_ALL_LIST = 4;
	public static final int CMD_SEND_INFO_TO_USER = 5;
	public static final int CMD_SEND_INFO_TO_GROUP = 6;

	public static final int CMD_LOGIN_SUCESS = 100;
	public static final int CMD_LOGIN_FAILD = 101;
	public static final int CMD_REGISTER_SUCESS = 102;
	public static final int CMD_REGISTER_FAILD = 103;
	public static final int CMD_CHECK_IN_SUCESS = 104;
	public static final int CMD_CHECK_IN_FAILD = 105;
	public static final int CMD_GET_ALL_LIST_SUCESS = 106;
	public static final int CMD_GET_ALL_LIST_FAILD = 107;
	public static final int CMD_HAS_USER_ONLINE = 108;
	public static final int CMD_HAS_USER_OFFLINE = 109;
	public static final int CMD_SEND_INFO_SUCESS = 110;
	public static final int CMD_SEND_INFO_FAILD = 111;
	public static final int CMD_RECEIVE_INFO = 112;
	public static final int CMD_RECEIVE_INFO_ON_MAIN = 113;
	public static final int CMD_RECEIVE_INFO_FROM_GROUP = 114;

	public static final int CMD_PASSWORD_NOT_SAME = 200;
	public static final int CMD_UPDATE_RECEIVE_INFO = 201;
	public static final int CMD_UPDATE_RECEIVE_INFO_FROM_GROUP = 202;

	public static final int CMD_PLAY_MSG = 500;
	public static final int CMD_SYSTEM_INFO = 900;
	public static final int CMD_SYSTEM_ERROR = 901;

}
