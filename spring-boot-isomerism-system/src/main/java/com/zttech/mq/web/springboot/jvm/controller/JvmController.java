package com.zttech.mq.web.springboot.jvm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zttech.mq.web.springboot.common.jvm.entity.CodeCacheInfo;
import com.zttech.mq.web.springboot.common.jvm.entity.EdenGenInfo;
import com.zttech.mq.web.springboot.common.jvm.entity.FullGCCollectionInfo;
import com.zttech.mq.web.springboot.common.jvm.entity.HeapMemoryInfo;
import com.zttech.mq.web.springboot.common.jvm.entity.JavaClassInfo;
import com.zttech.mq.web.springboot.common.jvm.entity.JvmBaseParameter;
import com.zttech.mq.web.springboot.common.jvm.entity.NonHeapMemoryInfo;
import com.zttech.mq.web.springboot.common.jvm.entity.OldGenInfo;
import com.zttech.mq.web.springboot.common.jvm.entity.PermGenInfo;
import com.zttech.mq.web.springboot.common.jvm.entity.SurvivorInfo;
import com.zttech.mq.web.springboot.common.jvm.entity.ThreadInfo;
import com.zttech.mq.web.springboot.common.jvm.entity.YoungGCCollectionInfo;

@Controller
public class JvmController {

	@RequestMapping(value = "/goJvm")
	public String goJvm(Model model) {
		model.addAttribute("parameter", new JvmBaseParameter());
		return "/jvm/home";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getJvm")
	public JvmBaseParameter getNowJvmParameter() {
		return new JvmBaseParameter();
	}
	
	@RequestMapping(value = "/goHeapMemory")
	public String goHeapMemory() {
		return "/jvm/heapMemory";
	}
	
	/**
	 * 堆内存
	 * @return
	 * @throws InterruptedException
	 */
	@ResponseBody
	@RequestMapping(value = "/getHeapMemoryInfo")
	public HeapMemoryInfo getHeapMemoryInfo() {
		return new HeapMemoryInfo();
	}
	
	@RequestMapping(value = "/goNonHeapMemory")
	public String goNonHeapMemory() {
		return "/jvm/nonHeapMemory";
	}
	
	/**
	 * 堆外内存
	 * @return
	 * @throws InterruptedException
	 */
	@ResponseBody
	@RequestMapping(value = "/getNonHeapMemoryInfo")
	public NonHeapMemoryInfo getNonHeapMemoryInfo() {
		return new NonHeapMemoryInfo();
	}
	
	@RequestMapping(value = "/goEdenGen")
	public String goEdenGen() {
		return "/jvm/edenGen";
	}
	
	/**
	 * eden区内存
	 * @return
	 * @throws InterruptedException
	 */
	@ResponseBody
	@RequestMapping(value = "/getEdenGenInfo")
	public EdenGenInfo getEdenGenInfo() {
		return new EdenGenInfo();
	}
	
	@RequestMapping(value = "/goSurvivor")
	public String goSurvivor() {
		return "/jvm/survivor";
	}
	
	/**
	 * Survivor区内存
	 * @return
	 * @throws InterruptedException
	 */
	@ResponseBody
	@RequestMapping(value = "/getSurvivorInfo")
	public SurvivorInfo getSurvivorInfo() {
		return new SurvivorInfo();
	}
	
	@RequestMapping(value = "/goOldGen")
	public String goOldGen() {
		return "/jvm/oldGen";
	}
	
	/**
	 * 老年区内存
	 * @return
	 * @throws InterruptedException 
	 */
	@ResponseBody
	@RequestMapping(value = "/getOldGenInfo")
	public OldGenInfo getOldGenInfo() {
		return new OldGenInfo();
	}
	
	@RequestMapping(value = "/goPermGen")
	public String goPermGen() {
		return "/jvm/permGen";
	}
	
	/**
	 * 持久代内存
	 * @return
	 * @throws InterruptedException
	 */
	@ResponseBody
	@RequestMapping(value = "/getPermGenInfo")
	public PermGenInfo getPermGenInfo() {
		return new PermGenInfo();
	}
	
	@RequestMapping(value = "/goCodeCache")
	public String goCodeCache() {
		return "/jvm/codeCache";
	}
	
	/**
	 * codeCache区内存
	 * @return
	 * @throws InterruptedException
	 */
	@ResponseBody
	@RequestMapping(value = "/getCodeCacheInfo")
	public CodeCacheInfo getCodeCacheInfo() {
		return new CodeCacheInfo();
	}
	
	@RequestMapping(value = "/goThread")
	public String goThread() {
		return "/jvm/thread";
	}
	
	/**
	 * 线程数量统计
	 * @return
	 * @throws InterruptedException
	 */
	@ResponseBody
	@RequestMapping(value = "/getThreadInfo")
	public ThreadInfo getThreadInfo() {
		return new ThreadInfo();
	}
	
	@RequestMapping(value = "/goJavaClass")
	public String goJavaClass() {
		return "/jvm/javaClass";
	}
	
	/**
	 * java类使用
	 * @return
	 * @throws InterruptedException
	 */
	@ResponseBody
	@RequestMapping(value = "/getJavaClassInfo")
	public JavaClassInfo getJavaClassInfo() {
		return new JavaClassInfo();
	}
	
	@RequestMapping(value = "/goYoungGCCollection")
	public String goYoungGCCollection() {
		return "/jvm/youngGCCollection";
	}
	
	/**
	 * 新生区GC情况
	 * @return
	 * @throws InterruptedException
	 */
	@ResponseBody
	@RequestMapping(value = "/getYoungGCCollectionInfo")
	public YoungGCCollectionInfo getYoungGCCollectionInfo() {
		return new YoungGCCollectionInfo();
	}
	
	@RequestMapping(value = "/goFullGCCollection")
	public String goFullGCCollection() {
		return "/jvm/fullGCCollection";
	}
	
	/**
	 * 老年代GC情况
	 * @return
	 * @throws InterruptedException
	 */
	@ResponseBody
	@RequestMapping(value = "/getFullGCCollectionInfo")
	public FullGCCollectionInfo getFullGCCollectionInfo() {
		return new FullGCCollectionInfo();
	}
}
