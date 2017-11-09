package com.py.common;

import java.io.File;

public class PyConstants {

	public static final String ENCODING = "UTF-8";
	public static final int HTTTP_STATUS_CODE_OK = 200;
	public static final int BAIDU_STATUS_OK = 0;
	public static final int GAODE_STATUS_OK = 1;

	public final static String BAIDU_MAP_URL = "http://api.map.baidu.com/geocoder/v2/";
	public final static String BAIDU_MAP_KEY = "4lMRkAnsVQxCaKIFz8Fq53DSgr9v3Ez4";
	
	public final static String GAODE_MAP_URL = "http://restapi.amap.com/v3/geocode/geo";
	public final static String GAODE_MAP_KEY = "108ad046a3782563c4030b715e887dcc";

	public static final String DELIMITER = ",";
	public static final String SPLIT_PATTERN = ",|，";
	public static final String PATH_SEPARATOR = File.separator;
	public static final int DAILY_REQS_TIMES_LIMIT = 2000000;// 每日请求上限200万
	public static final String FIELD_WRAPPER = "\"";
	public static final String IN_FILE_NAME_EXTENSION = "txt";
	public static final String IN_FILE_PARSED_NAME_SUFFIX = ".parsed";
	public static final String OUT_SUCCESS_FILE_NAME_SUFFIX = "-data.txt";
	public static final String OUT_ALL_RAW_RESPONSE_FILE_NAME_SUFFIX = "-all-raw-data.txt";
	public static final String OUT_ERROR_FILE_NAME_SUFFIX = ".error";
	public static final String OUT_ERROR_SUMMARY_FILE_NAME = "ALL" + OUT_ERROR_FILE_NAME_SUFFIX + ".summary";
	public static final long RECKECK_INTERVAL = 1000 * 60 * 1;// 1分钟后重试

	public static final String REQ_COUNTER_FILE_NAME = "counter.data";
}
