package com.zttech.mq.web.springboot.common.jvm.entity;

import com.zttech.mq.web.springboot.common.jvm.util.JVMMemoryUtil;

public class EdenGenInfo {

	public EdenGenInfo() {
		this.edenGenUsed = JVMMemoryUtil.getEdenGenUsed();
		this.edenGenMax = JVMMemoryUtil.getEdenGenMax();
	}

	/*
	 * eden已使用
	 */
	private long edenGenUsed;
	
	/*
	 * eden最大值
	 */
	private long edenGenMax;

	public long getEdenGenUsed() {
		return edenGenUsed;
	}

	public void setEdenGenUsed(long edenGenUsed) {
		this.edenGenUsed = edenGenUsed;
	}

	public long getEdenGenMax() {
		return edenGenMax;
	}

	public void setEdenGenMax(long edenGenMax) {
		this.edenGenMax = edenGenMax;
	}
	
}
