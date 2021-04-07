package com.zttech.mq.web.springboot.common.jvm.entity;

import com.zttech.mq.web.springboot.common.jvm.util.JVMGCUtil;
import com.zttech.mq.web.springboot.common.jvm.util.JVMInfoUtil;
import com.zttech.mq.web.springboot.common.jvm.util.JVMThreadUtil;

public class JvmBaseParameter {
	
	
	
	/*
	 * JVM
	 */
	
	public JvmBaseParameter() {
		this.jdkVersion = JVMInfoUtil.getJVMVersion();
		this.jvmStartTimeMs = JVMInfoUtil.getJVMStartTimeMs();
		this.jvmUpTimeMs = JVMInfoUtil.getJVMUpTimeMs();
		this.jvmLoadClassCount = JVMInfoUtil.getJVMLoadedClassCount();
		this.jvmUnLoadClassCount = JVMInfoUtil.getJVMUnLoadedClassCount();
		this.daemonThreadCount = JVMThreadUtil.getDaemonThreadCount();
		this.threadCount = JVMThreadUtil.getThreadCount();
		this.deadLockedThreadCount = JVMThreadUtil.getDeadLockedThreadCount();
		this.youngGCName = JVMGCUtil.getYoungGCName();
		this.youngGCCollectionCount = JVMGCUtil.getYoungGCCollectionCount();
		this.youngGCCollectionTime = JVMGCUtil.getYoungGCCollectionTime();
		this.fullGCName = JVMGCUtil.getFullGCName();
		this.fullGCCollectionCount = JVMGCUtil.getFullGCCollectionCount();
		this.fullGCCollectionTime = JVMGCUtil.getFullGCCollectionTime();
	}

	/*
	 * JDK版本
	 */
	private String jdkVersion;
	
	/*
	 * JVM启动时间
	 */
	private long jvmStartTimeMs;
	
	/*
	 * JVM运行时间
	 */
	private long jvmUpTimeMs;
	
	/*
	 * Class
	 */
	
	/*
	 * 当前加载类总量
	 */
	private long jvmLoadClassCount;
	
	/*
	 * 当前已卸载的
	 */
	private long jvmUnLoadClassCount;
	
	
	/*
	 * thread
	 */
	
	/*
	 * 守护线程数
	 */
	private int daemonThreadCount;
	
	/*
	 * 当前线程总量
	 */
	private int threadCount;
	
	/*
	 * 死锁线程总量
	 */
	private int deadLockedThreadCount;
	
	/*
	 * GC
	 */
	
	/*
	 * 新生区GC名字
	 */
	private String youngGCName;
	
	/*
	 * 新生区GC次数
	 */
	private long youngGCCollectionCount;
	
	/*
	 * 新生区GC时间
	 */
	private long youngGCCollectionTime;
	
	/*
	 * 老年区GC名字
	 */
	private String fullGCName;
	
	/*
	 * 老年区GC次数
	 */
	private long fullGCCollectionCount;
	
	/*
	 * 老年去GC时间
	 */
	private long fullGCCollectionTime;
	
	/*
	 * 内存
	 */
	
	private HeapMemoryInfo heapMemoryInfo = new HeapMemoryInfo();
	
	private NonHeapMemoryInfo nonHeapMemoryInfo = new NonHeapMemoryInfo();

	private EdenGenInfo edenGenInfo = new EdenGenInfo();
	
	private SurvivorInfo survivorInfo = new SurvivorInfo();
	
	private OldGenInfo oldGenInfo = new OldGenInfo();
	
	private PermGenInfo permGenInfo = new PermGenInfo();
	
	private CodeCacheInfo codeCacheInfo = new CodeCacheInfo();
	
	public HeapMemoryInfo getHeapMemoryInfo() {
		return heapMemoryInfo;
	}

	public void setHeapMemoryInfo(HeapMemoryInfo heapMemoryInfo) {
		this.heapMemoryInfo = heapMemoryInfo;
	}

	public NonHeapMemoryInfo getNonHeapMemoryInfo() {
		return nonHeapMemoryInfo;
	}

	public void setNonHeapMemoryInfo(NonHeapMemoryInfo nonHeapMemoryInfo) {
		this.nonHeapMemoryInfo = nonHeapMemoryInfo;
	}

	public EdenGenInfo getEdenGenInfo() {
		return edenGenInfo;
	}

	public void setEdenGenInfo(EdenGenInfo edenGenInfo) {
		this.edenGenInfo = edenGenInfo;
	}

	public SurvivorInfo getSurvivorInfo() {
		return survivorInfo;
	}

	public void setSurvivorInfo(SurvivorInfo survivorInfo) {
		this.survivorInfo = survivorInfo;
	}

	public OldGenInfo getOldGenInfo() {
		return oldGenInfo;
	}

	public void setOldGenInfo(OldGenInfo oldGenInfo) {
		this.oldGenInfo = oldGenInfo;
	}

	public PermGenInfo getPermGenInfo() {
		return permGenInfo;
	}

	public void setPermGenInfo(PermGenInfo permGenInfo) {
		this.permGenInfo = permGenInfo;
	}

	public CodeCacheInfo getCodeCacheInfo() {
		return codeCacheInfo;
	}

	public void setCodeCacheInfo(CodeCacheInfo codeCacheInfo) {
		this.codeCacheInfo = codeCacheInfo;
	}

	public String getJdkVersion() {
		return jdkVersion;
	}

	public void setJdkVersion(String jdkVersion) {
		this.jdkVersion = jdkVersion;
	}

	public long getJvmStartTimeMs() {
		return jvmStartTimeMs;
	}

	public void setJvmStartTimeMs(long jvmStartTimeMs) {
		this.jvmStartTimeMs = jvmStartTimeMs;
	}

	public long getJvmUpTimeMs() {
		return jvmUpTimeMs;
	}

	public void setJvmUpTimeMs(long jvmUpTimeMs) {
		this.jvmUpTimeMs = jvmUpTimeMs;
	}

	public long getJvmLoadClassCount() {
		return jvmLoadClassCount;
	}

	public void setJvmLoadClassCount(long jvmLoadClassCount) {
		this.jvmLoadClassCount = jvmLoadClassCount;
	}

	public long getJvmUnLoadClassCount() {
		return jvmUnLoadClassCount;
	}

	public void setJvmUnLoadClassCount(long jvmUnLoadClassCount) {
		this.jvmUnLoadClassCount = jvmUnLoadClassCount;
	}

	public int getDaemonThreadCount() {
		return daemonThreadCount;
	}

	public void setDaemonThreadCount(int daemonThreadCount) {
		this.daemonThreadCount = daemonThreadCount;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public int getDeadLockedThreadCount() {
		return deadLockedThreadCount;
	}

	public void setDeadLockedThreadCount(int deadLockedThreadCount) {
		this.deadLockedThreadCount = deadLockedThreadCount;
	}

	public String getYoungGCName() {
		return youngGCName;
	}

	public void setYoungGCName(String youngGCName) {
		this.youngGCName = youngGCName;
	}

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

	public String getFullGCName() {
		return fullGCName;
	}

	public void setFullGCName(String fullGCName) {
		this.fullGCName = fullGCName;
	}

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
