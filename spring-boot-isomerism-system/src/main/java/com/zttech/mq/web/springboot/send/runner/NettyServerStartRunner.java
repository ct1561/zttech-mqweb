package com.zttech.mq.web.springboot.send.runner;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.zttech.mq.web.springboot.common.entity.Server;
import com.zttech.mq.web.springboot.send.netty.NettyThread;

@Component
@Order(value = 2)
public class NettyServerStartRunner implements ApplicationRunner {

	@Autowired
	private Server server;
	
	@Autowired
	private RabbitProperties rabbitProperties;
	
//	@Resource(name = "DEFAULT_DISPATCHER_SERVLET_BEAN_NAME")
//	private DispatcherServlet servlet;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		if(server.getSend().isOpen()) {
			createFolder(server.getSend().getLogFolder());
			new NettyThread(server.getSend(), rabbitProperties).start();
			System.out.println("netty服务器已启动，端口号为：" + server.getSend().getPort());
		} else {
			System.out.println("netty服务器未开启");
		}
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
