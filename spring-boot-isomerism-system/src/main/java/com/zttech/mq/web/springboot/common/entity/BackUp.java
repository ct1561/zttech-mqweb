package com.zttech.mq.web.springboot.common.entity;

public class BackUp {

	public BackUp() {
		
	}

	public BackUp(String fileRoute, int fileSize) {
		this.fileRoute = fileRoute;
		this.fileSize = fileSize;
	}

	private String fileRoute;
	
	private int fileSize;

	public String getFileRoute() {
		return fileRoute;
	}

	public void setFileRoute(String fileRoute) {
		this.fileRoute = fileRoute;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
}
