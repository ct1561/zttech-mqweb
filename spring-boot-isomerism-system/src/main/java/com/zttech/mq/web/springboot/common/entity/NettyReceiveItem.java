package com.zttech.mq.web.springboot.common.entity;

public class NettyReceiveItem {

	public NettyReceiveItem() {
		super();
	}

	public NettyReceiveItem(String host, int port, String queueName, String logFolder) {
		this.host = host;
		this.port = port;
		this.queueName = queueName;
		this.logFolder = logFolder;
	}

	private String host;
	
	private int port;
	
	private String queueName;

	private String logFolder = "E:\\Logger\\Server\\receive";
	
	public String getLogFolder() {
		return logFolder;
	}

	public void setLogFolder(String logFolder) {
		this.logFolder = logFolder;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	
	
}
