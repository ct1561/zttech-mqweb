package com.zttech.mq.web.springboot.common.entity;

import java.util.ArrayList;
import java.util.List;

public class FolderReceive {

	private boolean open = false;
	
	private List<FolderReceiveItem> itemList = new ArrayList<>();

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public List<FolderReceiveItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<FolderReceiveItem> itemList) {
		this.itemList = itemList;
	}
	
	
}
