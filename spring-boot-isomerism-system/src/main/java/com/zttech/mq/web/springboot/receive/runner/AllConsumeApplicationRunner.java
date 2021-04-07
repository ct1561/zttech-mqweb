package com.zttech.mq.web.springboot.receive.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.zttech.mq.web.springboot.common.entity.Client;
import com.zttech.mq.web.springboot.common.entity.Server;
import com.zttech.mq.web.springboot.receive.entity.ThreadPoolConf;
import com.zttech.mq.web.springboot.receive.entity.ThreadSavePlace;
import com.zttech.mq.web.springboot.receive.thread.AllConsumeThread;

@Component
@Order(value = 4)
public class AllConsumeApplicationRunner implements ApplicationRunner {
	
	@Autowired
	private ThreadPoolConf threadPoolConf;
	
	@Autowired
	private RabbitProperties rabbitConf;
	
	@Autowired
	private Client client;
	
	@Autowired
	private Server server;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		AllConsumeThread allConsumeThread = new AllConsumeThread(rabbitConf, client, server, threadPoolConf);
		ThreadSavePlace.getThreadSavePlace(allConsumeThread);
		allConsumeThread.start();
	}

}
