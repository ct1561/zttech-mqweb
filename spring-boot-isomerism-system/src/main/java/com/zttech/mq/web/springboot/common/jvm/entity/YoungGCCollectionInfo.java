package com.zttech.mq.web.springboot.common.jvm.entity;

import com.zttech.mq.web.springboot.common.jvm.util.JVMGCUtil;

public class YoungGCCollectionInfo {

	public YoungGCCollectionInfo() {
		this.youngGCCollectionCount = JVMGCUtil.getYoungGCCollectionCount();
		this.youngGCCollectionTime = JVMGCUtil.getYoungGCCollectionTime();
	}

	/*
	 * 新生区总次数
	 */
	private long youngGCCollectionCount;
	
	/*
	 * 新生区总时间
	 */
	private long youngGCCollectionTime;

	public long getYoungGCCollectionCount() {
		return youngGCCollectionCount;
	}

	public void setYoungGCCollectionCount(long youngGCCollectionCount) {
		this.youngGCCollectionCount = youngGCCollectionCount;
	}

	public long getYoungGCCollectionTime() {
		return youngGCCollectionTime;
	}

	public void setYoungGCCollectionTime(long youngGCCollectionTime) {
		this.youngGCCollectionTime = youngGCCollectionTime;
	}
	
}
