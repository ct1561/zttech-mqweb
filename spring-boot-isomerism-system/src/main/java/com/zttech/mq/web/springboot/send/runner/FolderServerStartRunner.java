package com.zttech.mq.web.springboot.send.runner;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.zttech.mq.web.springboot.common.entity.Client;
import com.zttech.mq.web.springboot.common.entity.FolderSend;
import com.zttech.mq.web.springboot.send.folder.FolderThread;

@Component
@Order(value = 3)
public class FolderServerStartRunner implements ApplicationRunner {
	
	@Autowired
	private RabbitProperties rabbitProperties;
	
	@Autowired
	private Client client;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		FolderSend send = client.getSend();
		if(send.isOpen()) {
			while(true) {
				if(createFolder(send.getFolderRoute())) {
					createFolder(send.getLogFolder());
					new FolderThread(send, client.getKeyMap(), rabbitProperties).start();
					break;
				}
			}
		} else {
			System.out.println("生产者文件夹读取尚未开启");
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
