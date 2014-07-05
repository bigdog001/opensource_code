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
	protected boolean onWork;// �Ƿ���״̬
	public static final String sysError = "����������ӶϿ��ˣ������������ӣ�";
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
		Group group = new Group("��׿������", "����һ����д���������ˡ���");
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
	 * ���UI�ں�̨���߲��ǵ�ǰ״̬�������Ϣ��POLL
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
							// ��Ϊֻ��һ���˿�����ע��͵�¼֮���ȫ���������ﴦ��
							// ��Ϊ���������ܶԿͻ��˷��͹��֮���ϵͳ��Ϣ Ҫ���ֿ�
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
									if (!self.isLogin())// �ظ���¼�Ͳ�����UI��������
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
								// ���������������κβ���
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
								// �����û�ע�������������Ѿ���ȡ��������Ҫ���»�ȡ�����б�
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
								// ���������������Ϣ�ɹ�
								// System.out.println("110");
								break;
							case ProtocolConst.CMD_SEND_INFO_FAILD:
								// ���������������Ϣʧ��
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
									// û�б�����activity���ղ�����
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
									// û�б�����activity���ղ�������������
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
						sendInfoMessageToUI("�����жϣ����ܷ���˹رջ��������쳣��");
						if (sk != null) {
							sk.cancel();
							sk.channel().close();
						}
						// ���ʱ��ر�selector�����������ӶϿ�Ӧ����������
						// �ر�select֮�󣬳���WAKEUP���Ե��������κη��������׳�ClosedSelectorException
						// ��������ѡ��������ѭ����
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
			// �����������С�Ļ���ô��Ϣ����ֳɶ�ν���
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
	 * ���������߳���������
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
			// ��������ʱ
			sc.socket().connect(isa, CONNECT_TIMEOUT);
			// ���ö���ʱ
			sc.socket().setSoTimeout(READ_TIMEOUT);
			sc.configureBlocking(false);
			sc.register(selector, SelectionKey.OP_READ);
			if (sc.isConnected()) {
				// ���ӳɹ���ʼ�����������Ϣ
				// ����һ����֤���ݰ���������������֤
				state = running;
				if (self.isLogin() && !self.isOnline())
					Communication.newInstance().loginAgain();
			} else {
				// �ر�ͨ����60S���¿�ʼ����
				sendInfoMessageToUI("����������ʧ��" + (RECONNECT_TIME / 1000)
						+ "����ٳ�������");
				wait(RECONNECT_TIME);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// ���쳣�ر�ͨ����60S���¿�ʼ����
			e.printStackTrace();
			sendInfoMessageToUI((RECONNECT_TIME / 1000) + "����ٳ�������");
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
