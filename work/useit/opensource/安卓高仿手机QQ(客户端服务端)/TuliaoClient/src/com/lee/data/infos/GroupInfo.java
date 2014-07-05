package com.lee.data.infos;

import java.io.Serializable;

public class GroupInfo implements Cloneable, Serializable {

	/**
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int total;
	private boolean hasNewMsg;

	public GroupInfo(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public boolean hasNewMsg() {
		return hasNewMsg;
	}

	public void setNewMsg(boolean hasNewMsg) {
		this.hasNewMsg = hasNewMsg;
	}

}
