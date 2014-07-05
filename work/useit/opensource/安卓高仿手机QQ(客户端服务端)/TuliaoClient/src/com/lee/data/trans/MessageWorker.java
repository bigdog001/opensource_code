package com.lee.data.trans;

import java.io.IOException;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageWorker extends Thread {

	protected NetTransportWorker worker;
	protected Queue<SendPacket> sendQueue;
	protected Timer timer;
	protected TimerTask task;
	// 20S发送一个确认在线的报到
	protected final long delay = 10000;

	protected MessageWorker(final NetTransportWorker worker) {
		this.worker = worker;
		sendQueue = new ConcurrentLinkedQueue<SendPacket>();
		timer = new Timer();
		task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (worker.self.isLogin() && worker.self.isOnline()) {
					SendPacket packet = new SendPacket(
							ProtocolConst.CMD_CHECK_IN);
					packet.writeInt(worker.self.getId());
					addPacket(packet);
					wakeUp();
				}
			}
		};
	}

	protected void start_check_in() {
		timer.schedule(task, delay, delay);
	}

	public void addPacket(SendPacket packet) {
		sendQueue.add(packet);
		wakeUp();
	}

	protected synchronized void wakeUp() {
		notify();
	}

	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				while (worker.self.isOnline() && sendQueue.size() > 0) {
					SendPacket packet = sendQueue.peek();
					if (worker.writeBuf(packet.getData()))
						sendQueue.poll();
				}
				wait();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
