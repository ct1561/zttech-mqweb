package com.zttech.mq.web.springboot.common.entity;

public class MQListenerItem {

	private String queueName;
	
	private Object item;

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public Object getItem() {
		return item;
	}

	public void setItem(Object item) {
		this.item = item;
	}
	
	
}
