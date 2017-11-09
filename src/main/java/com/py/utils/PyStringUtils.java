package com.py.utils;

import org.apache.commons.lang3.StringUtils;

public class PyStringUtils {

	public static String wraper(Object object, String wrapper) {
		return wrapper + object + wrapper;
	}
	public static String removeWrapper(String str) {
		return StringUtils.trim(str).replaceAll("\"|\\s", "");
	}
}
