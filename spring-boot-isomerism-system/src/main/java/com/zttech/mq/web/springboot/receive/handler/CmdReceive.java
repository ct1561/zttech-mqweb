package com.zttech.mq.web.springboot.receive.handler;

import java.io.IOException;

import com.zttech.mq.web.springboot.common.entity.CmdConfItem;

public class CmdReceive {

	public void process(CmdConfItem item, String msg) throws IOException {
		if(msg == null || msg.equals("")) {
			
		} else {
			String cmd = "rundll32 " + item.getDllName() + " " + 
					item.getEntryPoint() + " " + msg;
			Process exec = Runtime.getRuntime().exec(cmd);
			exec.isAlive();
		}
	}

}
