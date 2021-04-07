package com.zttech.mq.web.springboot.common.jvm.entity;

import com.zttech.mq.web.springboot.common.jvm.util.JVMMemoryUtil;

public class CodeCacheInfo {

	public CodeCacheInfo() {
		this.codeCacheUsed = JVMMemoryUtil.getCodeCacheUsed();
		this.codeCacheMax = JVMMemoryUtil.getCodeCacheMax();
	}

	/*
	 * 已使用
	 */
	private long codeCacheUsed;
	
	/*
	 * 最大值
	 */
	private long codeCacheMax;

	public long getCodeCacheUsed() {
		return codeCacheUsed;
	}

	public void setCodeCacheUsed(long codeCacheUsed) {
		this.codeCacheUsed = codeCacheUsed;
	}

	public long getCodeCacheMax() {
		return codeCacheMax;
	}

	public void setCodeCacheMax(long codeCacheMax) {
		this.codeCacheMax = codeCacheMax;
	}
	
}
