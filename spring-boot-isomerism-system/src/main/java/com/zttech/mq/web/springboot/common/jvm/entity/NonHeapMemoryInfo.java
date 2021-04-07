package com.zttech.mq.web.springboot.common.jvm.entity;

import com.zttech.mq.web.springboot.common.jvm.util.JVMMemoryUtil;

public class NonHeapMemoryInfo {

	
	
	public NonHeapMemoryInfo() {
		this.nonHeapMemoryUsed = JVMMemoryUtil.getNonHeapMemoryUsed();
		this.nonHeapMemoryMax = JVMMemoryUtil.getNonHeapMemoryMax();
	}

	/*
	 * 已使用堆外内存
	 */
	private long nonHeapMemoryUsed;
	
	/*
	 * 最大可使用堆外内存
	 */
	private long nonHeapMemoryMax;

	public long getNonHeapMemoryUsed() {
		return nonHeapMemoryUsed;
	}

	public void setNonHeapMemoryUsed(long nonHeapMemoryUsed) {
		this.nonHeapMemoryUsed = nonHeapMemoryUsed;
	}

	public long getNonHeapMemoryMax() {
		return nonHeapMemoryMax;
	}

	public void setNonHeapMemoryMax(long nonHeapMemoryMax) {
		this.nonHeapMemoryMax = nonHeapMemoryMax;
	}
	
}
