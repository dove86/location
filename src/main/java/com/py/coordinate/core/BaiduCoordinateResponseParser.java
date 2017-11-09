package com.py.coordinate.core;

import com.google.gson.Gson;
import com.py.model.baidu.BaiduResponse;
import org.apache.log4j.Logger;

/**
 * sample response
 *
 * 有结果
 {"status":0,"result":{"location":{"lng":113.90812013694996,"lat":22.768969532891555},"precise":0,"confidence":25,"level":"道路"}}

 无结果
 {"status":1,"msg":"Internal Service Error:无相关结果","results":[]}
 * 
 * @author panfenghui
 *
 */
public class BaiduCoordinateResponseParser {
	private static Logger logger = Logger.getLogger(BaiduCoordinateResponseParser.class);
	public static BaiduResponse parse(String data) {
		BaiduResponse reponse  =new Gson().fromJson(data, BaiduResponse.class);
		return reponse;
	}

}
