package com.py.utils;

import org.apache.log4j.Logger;

public class LoggerUtils {

	private static final Logger logger = Logger.getLogger(LoggerUtils.class);

	public static void info(String str) {
		logger.info(str);
	}

	public static void error(String str) {
		logger.error(str);
	}

	public static void warn(String str) {
		logger.warn(str);
	}

	public static void debug(String str) {
		logger.debug(str);
	}

	public static void trace(String str) {
		logger.trace(str);
	}
}
