package com.py.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PyDateUtils {

//	public final static String DEFAULT_DATE_FORMAT="yyyy-MM-dd";
	public final static String DEFAULT_DATE_FORMAT="yyyy-MM-dd";
	public final static String DEFAULT_DATE_TIME_FORMAT="yyyy-MM-dd:HH:mm:ss";
//	private final static String DEFAULT_FORMAT= PyConstants.DAILY_REQS_TIMES_LIMIT > 1000000?"yyyy-MM-dd":"yyyy-MM-dd HH:mm";//测试用的
	public static String getDateFormat(long recheck_interval){
//		int msInMinute = 1000*60;
		String str  = DEFAULT_DATE_FORMAT;
//		if (recheck_interval/msInMinute<30) {// 小于30分钟重试    recheck  
//			str = "yyyy-MM-dd:HH:mm";
//		}
//		logger.info("data format:"+str);
		return str;
	}
	public static String getCurrentDateTimeStr(){
		return format(new Date(), DEFAULT_DATE_TIME_FORMAT);
	}
	public static String getCurrentDateStr(){
		return format(new Date(), DEFAULT_DATE_FORMAT);
	}
	public static String format(Date date){
		return format(date, DEFAULT_DATE_FORMAT);
	}
	public static String format(Date date,String format){
		return new SimpleDateFormat(format).format(date);
	}
	
	public static Date parse(String dateStr){
		return parse(dateStr, DEFAULT_DATE_FORMAT);
	}
	public static Date parse(String dateStr,String format){
		try {
			return new SimpleDateFormat(format).parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
