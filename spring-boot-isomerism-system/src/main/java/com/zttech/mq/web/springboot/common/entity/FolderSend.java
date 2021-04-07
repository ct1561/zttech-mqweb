package com.zttech.mq.web.springboot.common.entity;

public class FolderSend {

	private boolean open = false;
	
	private String folderRoute;
	
	private boolean clientDeleteIt = true;

	private String logFolder = "E:\\Logger\\Client\\send";
	
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

	public String getFolderRoute() {
		return folderRoute;
	}

	public void setFolderRoute(String folderRoute) {
		this.folderRoute = folderRoute;
	}

	public boolean isClientDeleteIt() {
		return clientDeleteIt;
	}

	public void setClientDeleteIt(boolean clientDeleteIt) {
		this.clientDeleteIt = clientDeleteIt;
	}
	
	
}
