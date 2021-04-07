package com.zttech.mq.web.springboot.common.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "server")
public class Server {

	private NettySend send;
	
	private NettyReceive receive;

	public NettySend getSend() {
		return send;
	}

	public void setSend(NettySend send) {
		this.send = send;
	}

	public NettyReceive getReceive() {
		return receive;
	}

	public void setReceive(NettyReceive receive) {
		this.receive = receive;
	}

}
