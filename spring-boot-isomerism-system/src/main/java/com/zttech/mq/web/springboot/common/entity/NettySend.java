package com.zttech.mq.web.springboot.common.entity;

public class NettySend {

	private boolean open = false;
	
	private int port = 8888;

	private String logFolder = "E:\\Logger\\Server\\send";
	
	public String getLogFolder() {
		return logFolder;
	}

	public void setLogFolder(String logFolder) {
		this.logFolder = logFolder;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}
