package com.bigdog.mobile.android.Interphone.interfaces;

import com.bigdog.mobile.android.Interphone.data.ChatMessage;

/**
 * 接收消息监听的listener接口
 * @author ccf
 *
 */
public interface ReceiveMsgListener {
	public boolean receive(ChatMessage msg);

}
