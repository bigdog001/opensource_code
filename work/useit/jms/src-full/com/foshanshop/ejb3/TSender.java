package com.foshanshop.ejb3;

public interface TSender {
	/**
	 * 发送文本类型的消息
	 */
	public void send(String msg);
}
