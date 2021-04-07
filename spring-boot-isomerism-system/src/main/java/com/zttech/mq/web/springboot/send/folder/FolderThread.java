package com.zttech.mq.web.springboot.send.folder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.boot.autoconfigure.amqp.RabbitProperties;

import com.zttech.mq.send.builder.RabbitProducerBuilder;
import com.zttech.mq.web.springboot.common.entity.FolderSend;
import com.zttech.mq.web.springboot.common.entity.MQParm;
import com.zttech.mq.web.springboot.common.util.LoggerBuilderUtils;
import com.zttech.mq.web.springboot.send.entity.RabbitConf;

import ch.qos.logback.classic.Logger;

/**
 * 启动文件夹读取线程
 * @author 061308
 *
 */
public class FolderThread extends Thread {
	
	private FolderSend folderSend;
	
	private RabbitProducerBuilder builder;
	
	private Map<String, MQParm> keyMap = new HashMap<>();
	
	public FolderThread(FolderSend folderSend, Map<String, MQParm> keyMap,RabbitProperties rabbitProperties) {
		this.keyMap = keyMap;
		this.folderSend = folderSend;
		this.builder = createMessageSender(rabbitProperties);
	}
	
	@Override
	public void run() {

		try {
			startFolderServer();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 启动
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private void startFolderServer() throws InterruptedException, IOException {
		Logger logger = LoggerBuilderUtils.getLogger(folderSend);
		while(true) {
			Thread.sleep(100);
			//1.读文件夹文件
			//2.逐一处理,后并删除
			//3.备份
			
			
			File folder = new File(this.folderSend.getFolderRoute());
			
			File[] listFiles = folder.listFiles();
			
			for(File file : listFiles) {
				//限制一下名字是时间戳+标识
				String fileName = file.getName();
				fileName = fileName.substring(0, fileName.length() - 4);
				String[] split = fileName.split("-");
				
				String msg = readFile(file);
				
				MQParm mqParm = this.keyMap.get(split[1]);
				
				if(mqParm != null) {
					try {
						this.builder.sendMessage(msg, mqParm.getExchangeName(), mqParm.getRoutingKey());
						logger.info("exchangeName: " + mqParm.getExchangeName() + "; routingKey: " + mqParm.getRoutingKey() + 
								"; key: " + split[1] + "; message: " + msg);
					} catch (Exception e) {
						logger.error("exchangeName: " + mqParm.getExchangeName() + "; routingKey: " + mqParm.getRoutingKey() + 
								"; key: " + split[1] + "; message: " + msg + "; error: " + e.getMessage());
					}
					
					
				}
				file.delete();
//				
				
			}
			
		}
	}
	
	
	/**
	 * 读出文件中的内容
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private String readFile(File file) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(file);
		String string = null;
		byte[] bytes = new byte[1024];
		int len = 0;
		len = fileInputStream.read(bytes);
		if(len != -1) {
			string = new String(bytes, 0, len, "UTF-8");
		}
		
		fileInputStream.close();
		fileInputStream = null;
		return string;
	}
	
	
	/**
	 * 创建rabbit生产者
	 * @param clientConf
	 * @return
	 */
	private RabbitProducerBuilder createMessageSender(RabbitProperties rabbitProperties) {
		return new RabbitProducerBuilder(createProperties(new RabbitConf(rabbitProperties.getHost(), 
				rabbitProperties.getPort(), rabbitProperties.getUsername(), rabbitProperties.getPassword(), 
				rabbitProperties.getVirtualHost())));
	}
	
	/**
	 * 准备创建properties
	 * @return
	 */
	private Properties createProperties(RabbitConf rabbitConf){
		Properties properties = new Properties();
		properties.setProperty("host", rabbitConf.getHost());
		properties.setProperty("port", String.valueOf(rabbitConf.getPort()) );
		properties.setProperty("username", rabbitConf.getUsername());
		properties.setProperty("password", rabbitConf.getPassword());
		properties.setProperty("virtualHost", rabbitConf.getVirtualHost());
		properties.setProperty("initConnectionPoolNum", "3");
		properties.setProperty("initChannelPoolNum", "3");
		return properties; 
	}
}
