package com.zttech.mq.web.springboot.common.entity;

public class FolderReceiveItem {

	public FolderReceiveItem() {
		super();
	}

	public FolderReceiveItem(String queueName, String fileSaveRoute, String logFolder) {
		this.queueName = queueName;
		this.fileSaveRoute = fileSaveRoute;
		this.logFolder = logFolder;
	}

	private String queueName;
	
	private String fileSaveRoute;

	private String logFolder = "E:\\Logger\\Client\\receive";
	
	public String getLogFolder() {
		return logFolder;
	}

	public void setLogFolder(String logFolder) {
		this.logFolder = logFolder;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getFileSaveRoute() {
		return fileSaveRoute;
	}

	public void setFileSaveRoute(String fileSaveRoute) {
		this.fileSaveRoute = fileSaveRoute;
	}
}
