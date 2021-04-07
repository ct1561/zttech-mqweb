package com.zttech.mq.web.springboot.common.util;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import ch.qos.logback.core.util.OptionHelper;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zttech.mq.web.springboot.common.entity.CmdConfItem;
import com.zttech.mq.web.springboot.common.entity.FolderReceiveItem;
import com.zttech.mq.web.springboot.common.entity.FolderSend;
import com.zttech.mq.web.springboot.common.entity.NettyReceiveItem;
import com.zttech.mq.web.springboot.common.entity.NettySend;

import java.util.HashMap;
import java.util.Map;
 
@Component
public class LoggerBuilderUtils {
 
	private static final Map<String, Logger> cmdMaps = new HashMap<>();
	
	private static final Map<String, Logger> folderMaps = new HashMap<>();
	
	private static final Map<String, Logger> nettyMaps = new HashMap<>();
	
	private static Logger nettySendLogger = null;
	
	private static Logger noConsumerLogger = null;
	
	private static Logger folderSendLogger = null;
	
    public static Logger getLogger(FolderReceiveItem item) {
    	Logger logger = folderMaps.get(item.getQueueName());
    	if(logger != null) {
    		return logger;
    	}
    	synchronized (LoggerBuilderUtils.class) {
    		logger = folderMaps.get(item.getQueueName());
        	if(logger != null) {
        		return logger;
        	}
        	logger = build(item.getLogFolder());
        	folderMaps.put(item.getQueueName(), logger);
    	}
    	return logger;
    }
    
    public static Logger getLogger(NettyReceiveItem item) {
    	Logger logger = nettyMaps.get(item.getQueueName());
    	if(logger != null) {
    		return logger;
    	}
    	synchronized (LoggerBuilderUtils.class) {
    		logger = nettyMaps.get(item.getQueueName());
        	if(logger != null) {
        		return logger;
        	}
        	logger = build(item.getLogFolder());
        	nettyMaps.put(item.getQueueName(), logger);
    	}
    	return logger;
    }
    
    public static Logger getLogger(CmdConfItem item) {
    	Logger logger = cmdMaps.get(item.getQueueName());
    	if(logger != null) {
    		return logger;
    	}
    	synchronized (LoggerBuilderUtils.class) {
    		logger = cmdMaps.get(item.getQueueName());
        	if(logger != null) {
        		return logger;
        	}
        	logger = build(item.getLogFolder());
        	cmdMaps.put(item.getQueueName(), logger);
    	}
    	return logger;
    }
    
    public static Logger getLogger(FolderSend send) {
    	if(folderSendLogger == null) {
    		folderSendLogger = build(send.getLogFolder());
    	}
    	return folderSendLogger;
    }
    
    public static Logger getLogger(NettySend send) {
    	if(nettySendLogger == null) {
    		nettySendLogger = build(send.getLogFolder());
    	}
    	return nettySendLogger;
    }
 
    public static Logger getLogger() {
    	if(noConsumerLogger == null) {
    		noConsumerLogger = build("E:\\Logger\\NoConsumer");
    	}
    	return noConsumerLogger;
    }
    
    private static Logger build(String name) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = context.getLogger("FILE-" + name);
        logger.setAdditive(false);
        RollingFileAppender<ILoggingEvent> appender = new RollingFileAppender<ILoggingEvent>();
        appender.setContext(context);
        appender.setName("FILE-" + name);
        appender.setFile(OptionHelper.substVars(name + "/logFile" + ".log", context));
        appender.setAppend(true);
        appender.setPrudent(false);
        SizeAndTimeBasedRollingPolicy<Object> policy = new SizeAndTimeBasedRollingPolicy<Object>();
        String fp = OptionHelper.substVars(name + "_big_info.log.%d{yyyy-MM-dd}.%i", context);
 
        policy.setMaxFileSize(FileSize.valueOf("1MB"));
        policy.setFileNamePattern(fp);
        policy.setMaxHistory(30);
        policy.setContext(context);
        policy.setTotalSizeCap(FileSize.valueOf("32GB"));
        policy.setParent(appender);
      
        policy.start();
 
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS}|%X{localIp}|[%t] %-5level %logger{50} %line - %m%n");
        encoder.start();
 
        appender.setRollingPolicy(policy);
        appender.setEncoder(encoder);
        appender.start();
        logger.addAppender(appender);
        return logger;
    }
}
