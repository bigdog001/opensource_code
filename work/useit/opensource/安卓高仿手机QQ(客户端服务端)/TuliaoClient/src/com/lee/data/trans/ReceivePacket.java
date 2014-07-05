package com.lee.data.trans;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.Serializable;

public class ReceivePacket implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int cmd;
	protected ByteArrayInputStream bis;
	protected DataInputStream dis;

	public ReceivePacket(byte[] vdata) {
		byte[] data = new byte[vdata.length];
		System.arraycopy(vdata, 0, data, 0, data.length);
		bis = new ByteArrayInputStream(data);
		dis = new DataInputStream(bis);
		try {
			cmd = dis.readInt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getCmd() {
		return cmd;
	}

	public void setCmd(int cmd) {
		this.cmd = cmd;
	}

	public int available() {
		try {
			return dis.available();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public byte readByte() {
		try {
			return dis.readByte();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public int read(byte[] data) {
		try {
			return dis.read(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public byte[] readData(int length) throws IOException {
		byte[] data = new byte[length];
		int ch;
		int pos = 0;
		while ((ch = dis.read()) != -1) {
			data[pos] = (byte) ch;
			pos++;
		}
		return data;
	}

	public int readInt() {
		try {
			return dis.readInt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public String readUTF() {
		try {
			return dis.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public long readLong() {
		try {
			return dis.readLong();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public double readDouble() {
		try {
			return dis.readDouble();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public boolean readBoolean() {
		try {
			return dis.readBoolean();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public short readShort() {
		try {
			return dis.readShort();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public void close() {
		try {
			bis.close();
			dis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
