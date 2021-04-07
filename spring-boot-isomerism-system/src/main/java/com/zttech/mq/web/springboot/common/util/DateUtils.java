package com.zttech.mq.web.springboot.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String getDateStringFormat(String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		String dateString = simpleDateFormat.format(new Date());
		return dateString;
	}
	
	public static String getNowTime() {
		long currentTimeMillis = System.currentTimeMillis();
		String valueOf = String.valueOf(currentTimeMillis);
		return valueOf;
	}
}
