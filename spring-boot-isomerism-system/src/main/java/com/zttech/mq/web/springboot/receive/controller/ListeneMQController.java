package com.zttech.mq.web.springboot.receive.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zttech.mq.send.netty.EventType;
import com.zttech.mq.send.netty.TestEvent;
import com.zttech.mq.web.springboot.common.entity.CmdConfItem;
import com.zttech.mq.web.springboot.common.entity.FolderReceiveItem;
import com.zttech.mq.web.springboot.common.entity.NettyReceiveItem;
import com.zttech.mq.web.springboot.common.entity.Server;
import com.zttech.mq.web.springboot.common.util.LoggerBuilderUtils;
import com.zttech.mq.web.springboot.receive.entity.ThreadSavePlace;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping(value = "/mq")
public class ListeneMQController {

	@Autowired
	private Server server;
	
	@RequestMapping(value = "/send")
	@ResponseBody
	public boolean sendMessage(HttpServletRequest req, String exchangeName, String routingKey, String content) throws InterruptedException {
		if(routingKey == null) {
			routingKey = "";
		}
		SingtonNettyClient.getClient("127.0.0.1", server.getSend().getPort())
		.sendMessage(new TestEvent(EventType.RABBIT_EVENT, "", exchangeName, routingKey, content));
		Logger logger = LoggerBuilderUtils.getLogger(server.getSend());
		String requestIP = getRequestIP(req);
		logger.info("fromIp: " + requestIP + "; exchangeName: " + exchangeName + "; routingKey: " + "; content: " + content);
		return true;
	}
	
	@RequestMapping(value = "/goNetty")
	public String goNetty(ModelMap model) {
		Map<String, NettyReceiveItem> nettyItemMap = ThreadSavePlace.getThreadSavePlace().getThread().getNettyItemMap();
		model.addAttribute("maps", nettyItemMap);
		return "/mqListene/netty";
	}
	
	@RequestMapping(value = "/goString")
	@ResponseBody
	public NettyReceiveItem goString() {
		NettyReceiveItem nettyReceiveItem = new NettyReceiveItem();
		nettyReceiveItem.setHost("host");
		nettyReceiveItem.setPort(123);
		nettyReceiveItem.setLogFolder("folder");
		nettyReceiveItem.setQueueName("queueName");
		return nettyReceiveItem;
	}
	
	@RequestMapping(value = "/goCmd")
	public String goCmd(ModelMap model) {
		Map<String, CmdConfItem> cmdItemMap = ThreadSavePlace.getThreadSavePlace().getThread().getCmdItemMap();
		model.addAttribute("maps", cmdItemMap);
		return "/mqListene/cmd";
	}
	
	@RequestMapping(value = "/goFolder")
	public String goFolder(ModelMap model) {
		Map<String, FolderReceiveItem> folderItemMap = ThreadSavePlace.getThreadSavePlace().getThread().getFolderItemMap();
		model.addAttribute("maps", folderItemMap);
		return "/mqListene/folder";
	}
	
	@RequestMapping(value = "/goAddNetty")
	public String goAddNetty() {
		return "/mqListene/addNetty";
	}
	
	@RequestMapping(value = "/goAddCmd")
	public String goAddCmd() {
		return "/mqListene/addCmd";
	}
	
	@RequestMapping(value = "/goAddFolder")
	public String goAddFolder() {
		return "/mqListene/addFolder";
	}
	
	@RequestMapping(value = "/addCmd", method = RequestMethod.POST)
	@ResponseBody
	public boolean addCmd(CmdConfItem item) {
		try {
			ThreadSavePlace.getThreadSavePlace().getThread().addListeneQueue(item);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	@RequestMapping(value = "/addFolder", method = RequestMethod.POST)
	@ResponseBody
	public boolean addFolder(FolderReceiveItem item) {
		try {
			ThreadSavePlace.getThreadSavePlace().getThread().addListeneQueue(item);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@RequestMapping(value = "/addNetty", method = RequestMethod.POST)
	@ResponseBody
	public boolean addNetty(NettyReceiveItem item) {
		try {
			ThreadSavePlace.getThreadSavePlace().getThread().addListeneQueue(item);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	@RequestMapping(value = "/removeCmd", method = RequestMethod.POST)
	@ResponseBody
	public boolean removeCmd(CmdConfItem item) {
		try {
			ThreadSavePlace.getThreadSavePlace().getThread().removeCmdListeneQueue(item.getQueueName());
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	@RequestMapping(value = "/removeFolder", method = RequestMethod.POST)
	@ResponseBody
	public boolean removeFolder(FolderReceiveItem item) {
		try {
			ThreadSavePlace.getThreadSavePlace().getThread().removeFolderListeneQueue(item.getQueueName());
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	@RequestMapping(value = "/removeNetty", method = RequestMethod.POST)
	@ResponseBody
	public boolean removeNetty(NettyReceiveItem item) {
		try {
			ThreadSavePlace.getThreadSavePlace().getThread().removeNettyListeneQueue(item.getQueueName());
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	private String getRequestIP(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
//	private List<MQListenerItem> initList(Page page){
//		List<MQListenerItem> list = new ArrayList<>();
//		AllConsumeThread thread = ThreadSavePlace.getThreadSavePlace().getThread();
//		int count = thread.getItemNum();
//		Map<String, CmdConfItem> cmdItemMap = thread.getCmdItemMap();
//		Map<String, NettyReceiveItem> nettyItemMap = thread.getNettyItemMap();
//		Map<String, FolderReceiveItem> folderItemMap = thread.getFolderItemMap();
//		
//		if(page.getPageNum() < nettyItemMap.size()) {
//			if(page.getPageNum() + page.getPageSize() <= nettyItemMap.size()) {
//				
//			} 
//		}
//		
//		return list;
//	}
}
