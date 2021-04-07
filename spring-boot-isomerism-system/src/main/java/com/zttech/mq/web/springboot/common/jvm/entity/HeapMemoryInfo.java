package com.zttech.mq.web.springboot.common.jvm.entity;

import com.zttech.mq.web.springboot.common.jvm.util.JVMMemoryUtil;

public class HeapMemoryInfo {

	public HeapMemoryInfo() {
		this.heapMemoryMax = JVMMemoryUtil.getHeapMemoryMax();
		this.heapMemoryUsed = JVMMemoryUtil.getHeapMemoryUsed();
	}

	/*
	 * 已使用堆内存
	 */
	private long heapMemoryUsed;
	
	/*
	 * 最大堆内存
	 */
	private long heapMemoryMax;

	public long getHeapMemoryUsed() {
		return heapMemoryUsed;
	}

	public void setHeapMemoryUsed(long heapMemoryUsed) {
		this.heapMemoryUsed = heapMemoryUsed;
	}

	public long getHeapMemoryMax() {
		return heapMemoryMax;
	}

	public void setHeapMemoryMax(long heapMemoryMax) {
		this.heapMemoryMax = heapMemoryMax;
	}
	
}
