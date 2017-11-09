package com.py.coordinate.core.requester;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.py.common.PyConstants;
import com.py.model.baidu.Location;
import com.py.model.gaode.GaodeResponse;
import com.py.model.gaode.GeoCode;
import com.py.utils.LoggerUtils;

/**
 * @author panfenghui
 *
 */
public class GaodeCoordinateRequester extends AbstractCoordinateRequester {

	private static final String OUTPUT_JSON = "JSON";

	@Override
	protected Map<String, String> getParamsMap(String address) {
		Map<String, String> paramsMap = new HashMap<String, String>();

		paramsMap.put("address", address);
		paramsMap.put("output", OUTPUT_JSON);
		paramsMap.put("key", PyConstants.GAODE_MAP_KEY);
		return paramsMap;
	}

	@Override
	protected String getRequestUrl() {
		return PyConstants.GAODE_MAP_URL;
	}

	@Override
	protected void transformToLocation(String responseStr, Location location) {
		location.setRawResponse(responseStr);
		GaodeResponse gaodeResponse = new Gson().fromJson(responseStr, GaodeResponse.class);
		int status = Integer.valueOf(gaodeResponse.getStatus());
		if (status == PyConstants.GAODE_STATUS_OK) {
			List<GeoCode> geoCodes = gaodeResponse.getGeocodes();
			GeoCode geoCode = geoCodes.get(0);
			String locationStr = geoCode.getLocation();
			String[] lngLatArr = locationStr.split(",");
			// 经度
			Double longitude = new Double(lngLatArr[0]);
			location.setLng(longitude);
			LoggerUtils.debug("经度" + longitude);
			// 纬度
			Double latitude = new Double(lngLatArr[1]);
			LoggerUtils.debug("纬度" + latitude);
			location.setLat(latitude);
		} else {
			String errorMsg = "invalid reponse status:" + status;
			location.setMsg(errorMsg);

		}
	}

}
