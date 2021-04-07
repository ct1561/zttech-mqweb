package com.zttech.mq.web.springboot.common.jvm.entity;

import com.zttech.mq.web.springboot.common.jvm.util.JVMThreadUtil;

public class ThreadInfo {

	public ThreadInfo() {
		this.threadCount = JVMThreadUtil.getThreadCount();
		this.daemonThreadCount = JVMThreadUtil.getDaemonThreadCount();
		this.deadLockedThreadCount = JVMThreadUtil.getDeadLockedThreadCount();
	}

	/*
	 * 总线程数
	 */
	private int threadCount;
	
	/*
	 * 守护线程数
	 */
	private int daemonThreadCount;
	
	/*
	 * 死锁线程数
	 */
	private int deadLockedThreadCount;

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public int getDaemonThreadCount() {
		return daemonThreadCount;
	}

	public void setDaemonThreadCount(int daemonThreadCount) {
		this.daemonThreadCount = daemonThreadCount;
	}

	public int getDeadLockedThreadCount() {
		return deadLockedThreadCount;
	}

	public void setDeadLockedThreadCount(int deadLockedThreadCount) {
		this.deadLockedThreadCount = deadLockedThreadCount;
	}
	
}
