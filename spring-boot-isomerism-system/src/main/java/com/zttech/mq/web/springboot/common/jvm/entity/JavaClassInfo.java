package com.zttech.mq.web.springboot.common.jvm.entity;

import com.zttech.mq.web.springboot.common.jvm.util.JVMInfoUtil;

public class JavaClassInfo {

	public JavaClassInfo() {
		this.jvmLoadedClassCount = JVMInfoUtil.getJVMLoadedClassCount();
		this.jvmUnLoadedClassCount = JVMInfoUtil.getJVMUnLoadedClassCount();
	}

	/*
	 * 加载类总量
	 */
	private long jvmLoadedClassCount;
	
	/*
	 * 卸载类总量
	 */
	private long jvmUnLoadedClassCount;

	public long getJvmLoadedClassCount() {
		return jvmLoadedClassCount;
	}

	public void setJvmLoadedClassCount(long jvmLoadedClassCount) {
		this.jvmLoadedClassCount = jvmLoadedClassCount;
	}

	public long getJvmUnLoadedClassCount() {
		return jvmUnLoadedClassCount;
	}

	public void setJvmUnLoadedClassCount(long jvmUnLoadedClassCount) {
		this.jvmUnLoadedClassCount = jvmUnLoadedClassCount;
	}
	
}
