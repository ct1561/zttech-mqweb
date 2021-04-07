package com.zttech.mq.web.springboot.receive.entity;

import com.zttech.mq.web.springboot.receive.controller.NewNettyClient;
import com.zttech.mq.web.springboot.receive.thread.AllConsumeThread;

public class ThreadSavePlace {
	
	private static ThreadSavePlace place = null;
	
	public ThreadSavePlace() {
	}
	
	public ThreadSavePlace(AllConsumeThread thread) {
		this.thread = thread;
	}
	
	public static ThreadSavePlace getThreadSavePlace(AllConsumeThread thread) {
		if(place == null) {
			place = new ThreadSavePlace(thread);
		}
		return place;
	}
	
	public static ThreadSavePlace getThreadSavePlace() {
		return place;
	}
	
	private AllConsumeThread thread;

	private NewNettyClient client;
	
	public NewNettyClient getClient() {
		return client;
	}

	public void setClient(NewNettyClient client) {
		this.client = client;
	}

	public AllConsumeThread getThread() {
		return thread;
	}

	public void setThread(AllConsumeThread thread) {
		this.thread = thread;
	}
}
