package com.py.coordinate.core.requester;

import com.py.model.AddressInfo;
import com.py.model.baidu.Location;
import com.py.utils.HttpUtils;

import java.util.Map;

/**
 * @author panfenghui
 *
 * 百度
 * {"status":0,"result":{"location":{"lng":113.90812013694996,"lat":22.768969532891555},"precise":0,"confidence":25,"level":"道路"}}
 *
 */
public abstract class AbstractCoordinateRequester implements  CoordinateRequester {

	public Location getLocation(AddressInfo addressInfo) {
		String address = addressInfo.getAddress();
		Map<String, String> paramsMap = getParamsMap(address);

		Location location = new Location();
		location.setAddress(address);
		location.setId(addressInfo.getId());
		String responseStr = null;
		try {
			
			responseStr = HttpUtils.get(getRequestUrl(), paramsMap);
			//百度
			responseStr = "{\"status\":0,\"result\":{\"location\":{\"lng\":113.90812013694996,\"lat\":22.768969532891555},\"precise\":0,\"confidence\":25,\"level\":\"道路\"}}";
			transformToLocation(responseStr,location);
			
		} catch (Exception e) {
			e.printStackTrace();
			location.setMsg("请求异常");
			return location;
		}
		return location;

	}

	protected abstract void transformToLocation(String responseStr,Location location);
	protected abstract Map<String, String> getParamsMap(String address);
	protected abstract String getRequestUrl();
}
