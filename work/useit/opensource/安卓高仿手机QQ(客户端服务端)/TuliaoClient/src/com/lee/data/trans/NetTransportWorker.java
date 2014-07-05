package com.lee.data.trans;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.os.Message;

import com.lee.custom.inter.ReceiveInfoListener;
import com.lee.data.infos.Group;
import com.lee.data.infos.UserInfo;
import com.lee.tuliao.activity.TuliaoBaseActivity;

public class NetTransportWorker extends Thread {

	protected Selector selector = null;
	protected SocketChannel sc = null;
	protected static final int CONNECT_TIMEOUT = 10000;
	protected static final int READ_TIMEOUT = 10000;
	protected static final int RECONNECT_TIME = 60000;

	protected ByteArrayOutputStream readByte;
	protected DataOutputStream readData;
	static {
		java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");

	};
	protected final byte connect = 1;
	protected final byte running = 2;
	protected byte state = connect;
	protected boolean onWork;// 是否工作状态
	public static final String sysError = "与服务器连接断开了，马上重新连接！";
	protected Vector<ReceiveInfoListener> listeners;
	protected Queue<MessageInfo> receiveQueue;
	protected UserInfo self;
	protected HashMap<Integer, UserInfo> all_user;
	protected HashMap<Integer, Group> all_group;

	protected NetTransportWorker() {
		onWork = true;
		readByte = new ByteArrayOutputStream();
		readData = new DataOutputStream(readByte);
		listeners = new Vector<ReceiveInfoListener>();
		receiveQueue = new ConcurrentLinkedQueue<MessageInfo>();
		self = new UserInfo();
		all_user = new HashMap<Integer, UserInfo>();
		all_group = new HashMap<Integer, Group>();
		Group group = new Group("安卓爱好者", "这个家伙随便写点就算完成了……");
		all_group.put(0, group);
	}

	public UserInfo getSelf() {
		return self;
	}

	public UserInfo getUser(int id) {
		return all_user.get(id);
	}

	public Group getGroup(int id) {
		return all_group.get(id);
	}

	public HashMap<Integer, UserInfo> getAll_user() {
		return all_user;
	}

	protected void addReceiveInfoListener(ReceiveInfoListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	protected void removeReceiveInfoListener(ReceiveInfoListener listener) {
		if (listeners.contains(listener))
			listeners.remove(listener);
	}

	/**
	 * 如果UI在后台或者不是当前状态则队列信息不POLL
	 */
	private boolean receive(MessageInfo info) {
		for (int i = 0; i < listeners.size(); i++) {
			ReceiveInfoListener listener = listeners.get(i);
			if (listener.receive(info))
				return true;
		}
		return false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (onWork) {
			switch (state) {
			case connect:
				connect();
				break;
			case running:
				running();
				break;
			}
		}
	}

	private synchronized void running() {
		try {
			while (selector.select() > 0) {
				for (SelectionKey sk : selector.selectedKeys()) {
					selector.selectedKeys().remove(sk);
					try {
						ReceivePacket packet = readBuf(sk);
						if (packet != null) {
							// 因为只有一个端口所以注册和登录之类的全部放在这里处理
							// 因为服务器可能对客户端发送广告之类的系统信息 要区分开
							switch (packet.cmd) {
							case ProtocolConst.CMD_REGISTER: {
								if (packet.readBoolean())
									TuliaoBaseActivity
											.sendEmptyMessage(ProtocolConst.CMD_REGISTER_SUCESS);
								else
									TuliaoBaseActivity
											.sendEmptyMessage(ProtocolConst.CMD_REGISTER_FAILD);

							}
								break;
							case ProtocolConst.CMD_LOGIN: {
								if (packet.readBoolean()) {
									if (!self.isLogin())// 重复登录就不发送UI更新命令
										TuliaoBaseActivity
												.sendEmptyMessage(ProtocolConst.CMD_LOGIN_SUCESS);
									else
										Communication.newInstance()
												.getAllList();
									int id = packet.readInt();
									String name = packet.readUTF();
									boolean sex = packet.readBoolean();
									String mood = packet.readUTF();
									int headID = packet.readInt();
									self.setId(id);
									self.setName(name);
									self.setSex(sex);
									self.setMood(mood);
									self.setHeadID(headID);
									self.setOnline(true);
									self.setLogin(true);
								} else
									TuliaoBaseActivity
											.sendEmptyMessage(ProtocolConst.CMD_LOGIN_FAILD);
							}
								break;
							case ProtocolConst.CMD_CHECK_IN: {
								// 服务器报到不作任何操作
							}
								break;
							case ProtocolConst.CMD_GET_ALL_LIST: {
								all_user.clear();
								if (packet.readBoolean()) {
									int total = packet.readInt();
									for (int i = 0; i < total; i++) {
										UserInfo info = new UserInfo();
										info.setId(packet.readInt());
										info.setName(packet.readUTF());
										info.setSex(packet.readBoolean());
										info.setMood(packet.readUTF());
										info.setHeadID(packet.readInt());
										info.setOnline(packet.readBoolean());
										all_user.put(info.getId(), info);
									}
									Message msg = new Message();
									msg.what = ProtocolConst.CMD_GET_ALL_LIST_SUCESS;
									msg.obj = all_user;
									TuliaoBaseActivity.sendMessage(msg);
								} else {
									TuliaoBaseActivity
											.sendEmptyMessage(ProtocolConst.CMD_GET_ALL_LIST_FAILD);
								}
							}
								break;
							case ProtocolConst.CMD_HAS_USER_ONLINE: {
								int id = packet.readInt();
								// 有新用户注册上线这里面已经获取不到，需要重新获取好友列表
								if (all_user.get(id) != null)
									all_user.get(id).setOnline(true);
								Message msg = new Message();
								msg.what = ProtocolConst.CMD_HAS_USER_ONLINE;
								msg.obj = all_user;
								TuliaoBaseActivity.sendMessage(msg);
							}
								break;
							case ProtocolConst.CMD_HAS_USER_OFFLINE: {
								int id = packet.readInt();
								if (all_user.get(id) != null)
									all_user.get(id).setOnline(false);
								Message msg = new Message();
								msg.what = ProtocolConst.CMD_HAS_USER_OFFLINE;
								msg.obj = all_user;
								TuliaoBaseActivity.sendMessage(msg);
							}
								break;
							case ProtocolConst.CMD_SEND_INFO_SUCESS:
								// 处理服务器发送信息成功
								// System.out.println("110");
								break;
							case ProtocolConst.CMD_SEND_INFO_FAILD:
								// 处理服务器发送信息失败
								break;
							case ProtocolConst.CMD_RECEIVE_INFO: {
								int sendId = packet.readInt();
								int receiveId = packet.readInt();
								String name = packet.readUTF();
								String msg = packet.readUTF();
								String time = packet.readUTF();
								MessageInfo info = new MessageInfo(sendId,
										name, receiveId, msg, time);
								if (!receive(info)) {
									// 没有被聊天activity接收并处理
									receiveQueue.add(info);
									if (getUser(sendId) != null)
										getUser(sendId).receiveMsgNoPP();
									TuliaoBaseActivity
											.sendEmptyMessage(ProtocolConst.CMD_RECEIVE_INFO_ON_MAIN);
								}
								TuliaoBaseActivity
										.sendEmptyMessage(ProtocolConst.CMD_PLAY_MSG);
							}
								break;
							case ProtocolConst.CMD_RECEIVE_INFO_FROM_GROUP: {
								int sendId = packet.readInt();
								String name = packet.readUTF();
								String msg = packet.readUTF();
								String time = packet.readUTF();
								MessageInfo info = new MessageInfo(sendId,
										name, self.getId(), msg, time);
								info.setMsgType(MessageInfo.TYPE_GROUP);
								if (!receive(info)) {
									// 没有被聊天activity接收并处理则放入队列
									receiveQueue.add(info);
									if (all_group.get(0) != null)
										all_group.get(0).receiveMsgNoPP();
									TuliaoBaseActivity
											.sendEmptyMessage(ProtocolConst.CMD_RECEIVE_INFO_ON_MAIN);
								}
								TuliaoBaseActivity
										.sendEmptyMessage(ProtocolConst.CMD_PLAY_MSG);
							}
								break;
							}
						}
						packet.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						sendInfoMessageToUI("连接中断，可能服务端关闭或者网络异常！");
						if (sk != null) {
							sk.cancel();
							sk.channel().close();
						}
						// 这个时候关闭selector。服务器连接断开应该马上连接
						// 关闭select之后，除了WAKEUP可以调用其它任何方法都会抛出ClosedSelectorException
						// 所以这里选择跳出该循环体
						close();
						return;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sendInfoMessageToUI(sysError);
			close();
		}
	}

	public final ReceivePacket readBuf(SelectionKey selectionKey)
			throws IOException {
		readByte.reset();
		if (selectionKey.isReadable()) {
			SocketChannel sc = (SocketChannel) selectionKey.channel();
			// 如果缓冲区过小的话那么信息流会分成多次接收
			ByteBuffer buff = ByteBuffer.allocate(1024);
			int ch;
			while ((ch = sc.read(buff)) > 0) {
				buff.flip();
				byte[] data = new byte[ch];
				buff.get(data);
				readData.write(data);
			}
			if (ch < 0) {
				throw new EOFException("Read EOF");
			} else {
				selectionKey.interestOps(SelectionKey.OP_READ);
				readByte.flush();
				return new ReceivePacket(readByte.toByteArray());
			}
		}
		return null;
	}

	protected final boolean writeBuf(byte[] data) throws IOException {
		if (sc != null && sc.isConnected() && state == running) {
			ByteBuffer buffer = ByteBuffer.wrap(data);
			int size = buffer.remaining();
			int actually = sc.write(buffer);
			if (actually == size)
				return true;
		} else if (state != running) {
			// reconnect();
		}
		return false;
	}

	private void sendInfoMessageToUI(String text) {
		TuliaoBaseActivity.sendMessage(ProtocolConst.CMD_SYSTEM_ERROR, text);
		self.setOnline(false);
		Iterator<Integer> keys = all_user.keySet().iterator();
		while (keys.hasNext()) {
			UserInfo info = all_user.get(keys.next());
			info.setOnline(false);
		}
		Message msg = new Message();
		msg.what = ProtocolConst.CMD_HAS_USER_OFFLINE;
		msg.obj = all_user;
		TuliaoBaseActivity.sendMessage(msg);
	}

	/**
	 * 唤起连接线程重新连接
	 */
	protected synchronized void reconnect() {
		close();
		notify();
	}

	private synchronized void connect() {
		try {
			selector = Selector.open();
			InetSocketAddress isa = new InetSocketAddress(ProtocolConst.ip,
					ProtocolConst.port);
			sc = SocketChannel.open();
			// 设置连超时
			sc.socket().connect(isa, CONNECT_TIMEOUT);
			// 设置读超时
			sc.socket().setSoTimeout(READ_TIMEOUT);
			sc.configureBlocking(false);
			sc.register(selector, SelectionKey.OP_READ);
			if (sc.isConnected()) {
				// 连接成功开始监听服务端消息
				// 发送一个验证数据包到服务器进行验证
				state = running;
				if (self.isLogin() && !self.isOnline())
					Communication.newInstance().loginAgain();
			} else {
				// 关闭通道过60S重新开始连接
				sendInfoMessageToUI("服务器连接失败" + (RECONNECT_TIME / 1000)
						+ "秒后再尝试连接");
				wait(RECONNECT_TIME);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// 有异常关闭通道过60S重新开始连接
			e.printStackTrace();
			sendInfoMessageToUI((RECONNECT_TIME / 1000) + "秒后再尝试连接");
			try {
				wait(RECONNECT_TIME);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private void close() {
		state = connect;
		try {
			if (sc != null) {
				sc.close();
				sc.socket().close();
				sc = null;
			}
			if (selector != null) {
				selector.close();
				selector = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
