package com.zttech.mq.web.springboot.common.entity;


import com.zttech.mq.send.service.IMessageSend;
import com.zttech.mq.web.springboot.send.netty.ChannelWraper;

public class ChannelWraperAndSenderMessage {

	public ChannelWraperAndSenderMessage() {
	}
	
	public ChannelWraperAndSenderMessage(ChannelWraper channelWraper, String clientName, IMessageSend messageSend) {
		this.channelWraper = channelWraper;
		this.messageSend = messageSend;
	}

	private ChannelWraper channelWraper;
	
	private String clientName;
	
	private IMessageSend messageSend;

	public ChannelWraper getChannelWraper() {
		return channelWraper;
	}

	public void setChannelWraper(ChannelWraper channelWraper) {
		this.channelWraper = channelWraper;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public IMessageSend getMessageSender() {
		return messageSend;
	}

	public void setMessageSender(IMessageSend messageSend) {
		this.messageSend = messageSend;
	}
}
