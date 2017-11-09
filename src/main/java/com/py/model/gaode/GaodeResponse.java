package com.py.model.gaode;

import java.util.List;

import lombok.Data;

/*
 * sample response json
 * {
	  "status": "1",
	  "info": "OK",
	  "infocode": "10000",
	  "count": "1",
	  "geocodes": [
	    {
	      "formatted_address": "北京市朝阳区方恒国际中心|A座",
	      "province": "北京市",
	      "citycode": "010",
	      "city": "北京市",
	      "district": "朝阳区",
	      "township": [],
	      "neighborhood": {
	        "name": [],
	        "type": []
	      },
	      "building": {
	        "name": [],
	        "type": []
	      },
	      "adcode": "110105",
	      "street": [],
	      "number": [],
	      "location": "116.480724,39.989584",
	      "level": "门牌号"
	    }
	  ]
	}*/
@Data
public class GaodeResponse {

	private String status;
	private String info;
	private String infocode;
	private String count;
	private List<GeoCode> geocodes;
}
