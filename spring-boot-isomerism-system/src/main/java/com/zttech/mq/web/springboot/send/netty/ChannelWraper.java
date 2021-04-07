package com.zttech.mq.web.springboot.send.netty;

import com.alibaba.fastjson.JSON;

import io.netty.channel.Channel;

public class ChannelWraper {

	public static final String PROTOCOL_TCP = "TCP";
	
	public static final String PROROCOL_WS = "WS";
	
	private Channel channel;
	
	private String protocol;
	
	public ChannelWraper(Channel channel) {
		this.channel = channel;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return JSON.toJSONString(this);
	}
}
