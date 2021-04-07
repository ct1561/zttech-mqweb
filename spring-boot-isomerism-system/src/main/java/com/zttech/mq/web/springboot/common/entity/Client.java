package com.zttech.mq.web.springboot.common.entity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "client")
public class Client {

	Map<String, MQParm> keyMap = new HashMap<>();

	private CmdConf cmd;
	
	private FolderReceive receive;
	
	private FolderSend send;
	
	public Map<String, MQParm> getKeyMap() {
		return keyMap;
	}

	public void setKeyMap(Map<String, MQParm> keyMap) {
		this.keyMap = keyMap;
	}

	public CmdConf getCmd() {
		return cmd;
	}

	public void setCmd(CmdConf cmd) {
		this.cmd = cmd;
	}
	
	public FolderReceive getReceive() {
		return receive;
	}

	public void setReceive(FolderReceive receive) {
		this.receive = receive;
	}

	public FolderSend getSend() {
		return send;
	}

	public void setSend(FolderSend send) {
		this.send = send;
	}
	
}
