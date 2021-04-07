package com.zttech.mq.web.springboot.receive.thread;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.CacheMode;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;

import com.zttech.mq.send.netty.EventType;
import com.zttech.mq.send.netty.TestEvent;
import com.zttech.mq.web.springboot.common.entity.Client;
import com.zttech.mq.web.springboot.common.entity.CmdConfItem;
import com.zttech.mq.web.springboot.common.entity.FolderReceiveItem;
import com.zttech.mq.web.springboot.common.entity.NettyReceiveItem;
import com.zttech.mq.web.springboot.common.entity.Server;
import com.zttech.mq.web.springboot.common.util.LoggerBuilderUtils;
import com.zttech.mq.web.springboot.receive.controller.NewNettyClient;
import com.zttech.mq.web.springboot.receive.entity.ThreadPoolConf;
import com.zttech.mq.web.springboot.receive.handler.CmdReceive;
import com.zttech.mq.web.springboot.receive.handler.FolderReceive;
import com.zttech.mq.web.springboot.receive.handler.NettyReceive;

import ch.qos.logback.classic.Logger;

public class AllConsumeThread extends Thread {

	private ThreadPoolConf threadPoolConf;
	
	private RabbitProperties rabbitConf;
	
	private Client client;
	
	private Server server;
	
	private int itemNum;
	
	private SimpleMessageListenerContainer container;
	
	private NettyReceive nettyConsume = new NettyReceive();
	
	private CmdReceive cmdConsume = new CmdReceive();
	
	private FolderReceive folderConsume = new FolderReceive();
	
	private Map<String, NettyReceiveItem> nettyItemMap = new HashMap<>();
	private Map<String, CmdConfItem> cmdItemMap = new HashMap<>();
	private Map<String, FolderReceiveItem> folderItemMap = new HashMap<>();
	private Map<String, NewNettyClient> nettyClientMap = new HashMap<>();
	
	public int getItemNum() {
		return itemNum;
	}

	public Map<String, NettyReceiveItem> getNettyItemMap() {
		return nettyItemMap;
	}

	public Map<String, CmdConfItem> getCmdItemMap() {
		return cmdItemMap;
	}

	public Map<String, FolderReceiveItem> getFolderItemMap() {
		return folderItemMap;
	}

	public Map<String, NewNettyClient> getNettyClientMap() {
		return nettyClientMap;
	}

	public AllConsumeThread(RabbitProperties rabbitConf, Client client, Server server, ThreadPoolConf threadPoolConf) {
		this.threadPoolConf = threadPoolConf;
		this.rabbitConf = rabbitConf;
		this.client = client;
		this.server = server;
	}
	
	@Override
	public void run() {
		startServer();
	}
	
	@SuppressWarnings("deprecation")
	private void startServer() {
		CachingConnectionFactory factory = new CachingConnectionFactory(rabbitConf.getHost(), rabbitConf.getPort());
		factory.setUsername(rabbitConf.getUsername());
		factory.setPassword(rabbitConf.getPassword());
		factory.setVirtualHost(rabbitConf.getVirtualHost());
		factory.setCacheMode(CacheMode.CONNECTION);
		factory.setPublisherConfirms(true);
		
		//动态变更监听队列涉及到的东西有，容器监听的队列、队列所属的消费者
		//需要把容器和这几个map暴露出来
		
		container = new SimpleMessageListenerContainer(factory);
		if(client.getReceive().isOpen()) {
			for(FolderReceiveItem item : client.getReceive().getItemList()) {
				if(createFolder(item.getFileSaveRoute())) {
					createFolder(item.getLogFolder());
					container.addQueueNames(item.getQueueName());
					System.out.println(item.getQueueName());
					folderItemMap.put(item.getQueueName(), item);
					itemNum++;
				}
			}
		}
		
		if(server.getReceive().isOpen()) {
			for(NettyReceiveItem item : server.getReceive().getItemList()) {
				createFolder(item.getLogFolder());
				container.addQueueNames(item.getQueueName());
				System.out.println(item.getQueueName());
				nettyItemMap.put(item.getQueueName(), item);
				nettyClientMap.put(item.getQueueName(), new NewNettyClient(item));
				itemNum++;
			}
		}
		
		if(client.getCmd().isOpen()) {
			for(CmdConfItem item : client.getCmd().getItemList()) {
				createFolder(item.getLogFolder());
				container.addQueueNames(item.getQueueName());
				System.out.println(item.getQueueName());
				cmdItemMap.put(item.getQueueName(), item);
				itemNum++;
			}
		}
		
		container.setMessageListener((MessageListener) message -> {
			threadPoolConf.asyncServiceExecutor().execute(new Runnable() {
				
				@Override
				public void run() {
					String consumerQueue = message.getMessageProperties().getConsumerQueue();
					String msg = null;
					try {
						msg = new String(message.getBody(), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					System.out.println(msg);
					if(nettyClientMap.containsKey(consumerQueue)) {
						Logger logger = LoggerBuilderUtils.getLogger(nettyItemMap.get(consumerQueue));
						System.out.println("netty");
						try {
							nettyConsume.process(new TestEvent(EventType.OTHER_EVENT, consumerQueue, null, null, 
									new String(message.getBody(), "UTF-8")), nettyClientMap.get(consumerQueue));
							logger.info("queueName: " + consumerQueue + "; message: " + msg);
						} catch (InterruptedException e) {
							logger.error("queueName: " + consumerQueue + "; message: " + msg + "; error: " + e.getMessage());
							e.printStackTrace();
						} catch (UnsupportedEncodingException e) {
							logger.error("queueName: " + consumerQueue + "; message: " + msg + "; error: " + e.getMessage());
							e.printStackTrace();
						}
						return;
					} else if(cmdItemMap.containsKey(consumerQueue)) {
						System.out.println("cmd");
						Logger logger = LoggerBuilderUtils.getLogger(cmdItemMap.get(consumerQueue));
						try {
							cmdConsume.process(cmdItemMap.get(consumerQueue), msg);
							logger.info("queueName: " + consumerQueue + "; message: " + msg);
						} catch (IOException e) {
							logger.error("queueName: " + consumerQueue + "; message: " + msg + "; error: " + e.getMessage());
							e.printStackTrace();
						}
						return;
					} else if(folderItemMap.containsKey(consumerQueue)) {
						System.out.println("folder");
						Logger logger = LoggerBuilderUtils.getLogger(folderItemMap.get(consumerQueue));
						try {
							folderConsume.process(folderItemMap.get(consumerQueue), msg);
							logger.info("queueName: " + consumerQueue + "; message: " + msg);
						} catch (InterruptedException | IOException e) {
							logger.error("queueName: " + consumerQueue + "; message: " + msg + "; error: " + e.getMessage());
							e.printStackTrace();
						}
						return;
					}
					
					Logger logger = LoggerBuilderUtils.getLogger();
					logger.warn("queueName: " + consumerQueue + "; message: " + msg + "; error: noConsumer");
				}
			});
		});
		
		System.out.println("container  start");
		container.start();
	}
	
	public void addListeneQueue(CmdConfItem item) {
		createFolder(item.getLogFolder());
		cmdItemMap.put(item.getQueueName(), item);
		container.addQueueNames(item.getQueueName());
		itemNum++;
	}
	
	public void addListeneQueue(NettyReceiveItem item) {
		createFolder(item.getLogFolder());
		nettyItemMap.put(item.getQueueName(), item);
		nettyClientMap.put(item.getQueueName(), new NewNettyClient(item));
		container.addQueueNames(item.getQueueName());
		itemNum++;
	}
	
	public void addListeneQueue(FolderReceiveItem item) {
		createFolder(item.getLogFolder());
		folderItemMap.put(item.getQueueName(), item);
		container.addQueueNames(item.getQueueName());
		itemNum++;
	}
	
	public void removeCmdListeneQueue(String queueName) {
		cmdItemMap.remove(queueName);
		container.removeQueueNames(queueName);
		itemNum--;
	}
	
	public void removeNettyListeneQueue(String queueName) {
		nettyItemMap.remove(queueName);
		container.removeQueueNames(queueName);
		itemNum--;
	}
	
	public void removeFolderListeneQueue(String queueName) {
		folderItemMap.remove(queueName);
		container.removeQueueNames(queueName);
		itemNum--;
	}
	
	
	private boolean createFolder(String folderRoute) {
		File folder = new File(folderRoute);
		if(!folder.exists()) {
			folder.mkdirs();
		}
		if(!folder.isDirectory()) {
			System.out.println(folderRoute + "---不是一个文件夹");
			return false;
		}
		return true;
	}
}
