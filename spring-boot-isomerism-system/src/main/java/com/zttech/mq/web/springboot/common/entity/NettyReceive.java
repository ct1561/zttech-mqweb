package com.zttech.mq.web.springboot.common.entity;

import java.util.ArrayList;
import java.util.List;

public class NettyReceive {

	private boolean open = false;

	private List<NettyReceiveItem> itemList = new ArrayList<>();
	
	public List<NettyReceiveItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<NettyReceiveItem> itemList) {
		this.itemList = itemList;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
	
}
