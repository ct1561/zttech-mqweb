package com.zttech.mq.web.springboot.common.entity;

import java.util.ArrayList;
import java.util.List;

public class CmdConf {

	private boolean open = false;
	
	List<CmdConfItem> itemList = new ArrayList<>();

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public List<CmdConfItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<CmdConfItem> itemList) {
		this.itemList = itemList;
	}
	
}
