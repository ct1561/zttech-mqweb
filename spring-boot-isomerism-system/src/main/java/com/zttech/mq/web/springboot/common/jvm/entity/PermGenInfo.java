package com.zttech.mq.web.springboot.common.jvm.entity;

import com.zttech.mq.web.springboot.common.jvm.util.JVMMemoryUtil;

public class PermGenInfo {

	public PermGenInfo() {
		this.permGenUsed = JVMMemoryUtil.getPermGenUsed();
		this.permGenMax = JVMMemoryUtil.getPermGenMax();
	}

	/*
	 * 持久带已使用
	 */
	private long permGenUsed;
	
	/*
	 * 持久带最大值
	 */
	private long permGenMax;

	public long getPermGenUsed() {
		return permGenUsed;
	}

	public void setPermGenUsed(long permGenUsed) {
		this.permGenUsed = permGenUsed;
	}

	public long getPermGenMax() {
		return permGenMax;
	}

	public void setPermGenMax(long permGenMax) {
		this.permGenMax = permGenMax;
	}
	
}
