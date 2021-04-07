package com.zttech.mq.web.springboot.common.jvm.entity;

import com.zttech.mq.web.springboot.common.jvm.util.JVMMemoryUtil;

public class SurvivorInfo {
	
	public SurvivorInfo() {
		this.survivorUsed = JVMMemoryUtil.getSurvivorUsed();
		this.survivorMax = JVMMemoryUtil.getSurvivorMax();
	}

	/*
	 * 已使用
	 */
	private long survivorUsed;
	
	/*
	 * 最大值
	 */
	private long survivorMax;

	public long getSurvivorUsed() {
		return survivorUsed;
	}

	public void setSurvivorUsed(long survivorUsed) {
		this.survivorUsed = survivorUsed;
	}

	public long getSurvivorMax() {
		return survivorMax;
	}

	public void setSurvivorMax(long survivorMax) {
		this.survivorMax = survivorMax;
	}
	
}
