package com.zttech.mq.web.springboot.common.entity;

public class CmdConfItem {
	
	public CmdConfItem() {
	}

	public CmdConfItem(String queueName, String dllName, String entryPoint, String logFolder) {
		this.queueName = queueName;
		this.dllName = dllName;
		this.entryPoint = entryPoint;
		this.logFolder = logFolder;
	}

	private String queueName;
	
	private String dllName;
	
	private String entryPoint;
	
	private String logFolder = "E:\\Logger\\Client\\cmd";
	
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

	public String getDllName() {
		return dllName;
	}

	public void setDllName(String dllName) {
		this.dllName = dllName;
	}

	public String getEntryPoint() {
		return entryPoint;
	}

	public void setEntryPoint(String entryPoint) {
		this.entryPoint = entryPoint;
	}

}
