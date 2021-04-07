package com.zttech.mq.web.springboot.common.jvm.util;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * 线程
 * @author 061308
 *
 */
public class JVMThreadUtil {

	static private ThreadMXBean threadMXBean;

    static {
        threadMXBean = ManagementFactory.getThreadMXBean();
    }

    //Daemon线程总量
    static public int getDaemonThreadCount() {
        return threadMXBean.getDaemonThreadCount();
    }

    //当前线程总量
    static public int getThreadCount() {
        return threadMXBean.getThreadCount();
    }

    //死锁线程总量
    static public int getDeadLockedThreadCount() {
        try {
            long[] deadLockedThreadIds = threadMXBean.findDeadlockedThreads();
            if (deadLockedThreadIds == null) {
                return 0;
            }
            return deadLockedThreadIds.length;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
