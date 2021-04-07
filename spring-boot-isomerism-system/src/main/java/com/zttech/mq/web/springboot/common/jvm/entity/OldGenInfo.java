package com.zttech.mq.web.springboot.common.jvm.entity;

import com.zttech.mq.web.springboot.common.jvm.util.JVMMemoryUtil;

public class OldGenInfo {

	public OldGenInfo() {
		this.oldGenUsed = JVMMemoryUtil.getOldGenUsed();
		this.oldGenMax = JVMMemoryUtil.getOldGenMax();
	}

	/*
	 * 老年代已使用
	 */
	private long oldGenUsed;
	
	/*
	 * 老年代最大值
	 */
	private long oldGenMax;

	public long getOldGenUsed() {
		return oldGenUsed;
	}

	public void setOldGenUsed(long oldGenUsed) {
		this.oldGenUsed = oldGenUsed;
	}

	public long getOldGenMax() {
		return oldGenMax;
	}

	public void setOldGenMax(long oldGenMax) {
		this.oldGenMax = oldGenMax;
	}
	
}
