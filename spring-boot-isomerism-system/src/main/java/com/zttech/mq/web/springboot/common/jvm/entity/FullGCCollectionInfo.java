package com.zttech.mq.web.springboot.common.jvm.entity;

import com.zttech.mq.web.springboot.common.jvm.util.JVMGCUtil;

public class FullGCCollectionInfo {

	public FullGCCollectionInfo() {
		this.fullGCCollectionCount = JVMGCUtil.getFullGCCollectionCount();
		this.fullGCCollectionTime = JVMGCUtil.getFullGCCollectionTime();
	}

	/*
	 * 老年代总次数
	 */
	private long fullGCCollectionCount;
	
	/*
	 * 老年代总时间
	 */
	private long fullGCCollectionTime;

	public long getFullGCCollectionCount() {
		return fullGCCollectionCount;
	}

	public void setFullGCCollectionCount(long fullGCCollectionCount) {
		this.fullGCCollectionCount = fullGCCollectionCount;
	}

	public long getFullGCCollectionTime() {
		return fullGCCollectionTime;
	}

	public void setFullGCCollectionTime(long fullGCCollectionTime) {
		this.fullGCCollectionTime = fullGCCollectionTime;
	}
	
}
