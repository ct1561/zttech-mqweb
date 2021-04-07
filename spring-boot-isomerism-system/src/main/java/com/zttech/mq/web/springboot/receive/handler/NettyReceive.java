package com.zttech.mq.web.springboot.receive.handler;

import com.zttech.mq.send.netty.TestEvent;
import com.zttech.mq.web.springboot.receive.controller.NewNettyClient;

public class NettyReceive {

	public void process(TestEvent event, NewNettyClient client) throws InterruptedException {
		client.sendMessage(event);
	}

}
