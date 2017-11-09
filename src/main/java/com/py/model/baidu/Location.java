package com.py.model.baidu;

import lombok.Data;

@Data
public class Location {

	/**
	 * 经度
	 */
	private double lng;
	/**
	 * 纬度
	 */
	private double lat;

	private String id;
	
	private String address;
	
	private String msg;
	
	private String originalFile;
	
	private int lineNumber;
	/**
	 * 百度或高德地图API 返回的原始地理位置信息 JSON 格式
	 */
	private String rawResponse;
}