package com.py.coordinate.core.requester;

import com.py.common.PyConstants;
import com.py.coordinate.core.BaiduCoordinateResponseParser;
import com.py.model.baidu.BaiduLocation;
import com.py.model.baidu.BaiduResponse;
import com.py.model.baidu.Location;
import com.py.utils.LoggerUtils;

import java.util.HashMap;
import java.util.Map;

import static com.py.common.PyConstants.BAIDU_MAP_KEY;
import static com.py.common.PyConstants.BAIDU_MAP_URL;

/**
 * @author panfenghui
 *http://api.map.baidu.com/geocoder/v2/?ak=4lMRkAnsVQxCaKIFz8Fq53DSgr9v3Ez4&output=json&ret_coordtype=gcj02ll&address=广东省深圳市宝安区新安街道办翻身大道西侧石鸿花园D栋2104
 */
public class BaiduCoordinateRequester extends AbstractCoordinateRequester{

	
	@Override
	protected Map<String, String> getParamsMap(String address) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("ak", BAIDU_MAP_KEY);
		paramsMap.put("output", "json");
		paramsMap.put("ret_coordtype", "gcj02ll");
		paramsMap.put("address", address);
		return paramsMap;
	}
	@Override
	protected String getRequestUrl(){
		return BAIDU_MAP_URL;
	}
	@Override
	protected void transformToLocation(String responseStr,Location location) {
		location.setRawResponse(responseStr);
		BaiduResponse locationResponse = BaiduCoordinateResponseParser.parse(responseStr);
		if (locationResponse.getStatus() == PyConstants.BAIDU_STATUS_OK) {
			BaiduLocation baiduLocation = locationResponse.getResult().getLocation();
			location.setLat(baiduLocation.getLat());
			location.setLng(baiduLocation.getLng());
		}
		else {
			String errorMsg = "invalid reponse status:" + locationResponse.getStatus();
			LoggerUtils.error(errorMsg);
			location.setMsg(errorMsg);
			
		}
	}
}
