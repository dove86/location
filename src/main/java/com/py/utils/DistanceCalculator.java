package com.py.utils;

import java.text.NumberFormat;

import com.alibaba.fastjson.asm.MethodWriter;
import com.py.model.LatLng;

public class DistanceCalculator {

	private static final double EARTH_RADIUS = 6378137;// 赤道半径(单位m)

	/**
	 * 转化为弧度(rad)
	 */
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 基于googleMap中的算法得到两经纬度之间的距离,计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下
	 * 
	 * @param lon1
	 *            第一点的精度
	 * @param lat1
	 *            第一点的纬度
	 * @param lon2
	 *            第二点的精度
	 * @param lat3
	 *            第二点的纬度
	 * @return 返回的距离，单位km
	 */
	public static double GetDistance(double lon1, double lat1, double lon2, double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lon1) - rad(lon2);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		// s = Math.round(s * 10000) / 10000;
		return s;
	}

	public static double getLatLngDistance(LatLng start, LatLng end) {
		// 自己实现距离算法：
		/**
		 * 计算两点之间距离
		 * 
		 * @param start
		 * @param end
		 * @return String 多少m , 多少km
		 */

		double lat1 = (Math.PI / 180) * start.getLatitude();
		double lat2 = (Math.PI / 180) * end.getLatitude();

		double lon1 = (Math.PI / 180) * start.getLongitude();
		double lon2 = (Math.PI / 180) * end.getLongitude();

		// double Lat1r = (Math.PI/180)*(gp1.getLatitudeE6()/1E6);
		// double Lat2r = (Math.PI/180)*(gp2.getLatitudeE6()/1E6);
		// double Lon1r = (Math.PI/180)*(gp1.getLongitudeE6()/1E6);
		// double Lon2r = (Math.PI/180)*(gp2.getLongitudeE6()/1E6);

		// 地球半径
		double R = 6371.004;

		// 两点间距离 m，如果想要米的话，结果*1000就可以了
		double dis = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;
		NumberFormat nFormat = NumberFormat.getNumberInstance(); // 数字格式化对象
//		if (dis < 1) { // 当小于1千米的时候用,用米做单位保留一位小数
//
//			nFormat.setMaximumFractionDigits(1); // 已可以设置为0，这样跟百度地图APP中计算的一样
//			dis *= 1000;
//
//			return nFormat.format(dis) + "m";
//		} else {
//			nFormat.setMaximumFractionDigits(2);
//			return nFormat.format(dis) + "km";
//		}

		
		dis *= 1000;

		return dis;
	}
	
	public static double getLatLngDistance(double lon1, double lat1, double lon2, double lat2) {
		// 自己实现距离算法：
		/**
		 * 计算两点之间距离
		 * 
		 * @param start
		 * @param end
		 * @return String 多少m , 多少km
		 */
		LatLng start = new LatLng(lon1,lat1); 
		LatLng end = new LatLng(lon2,lat2); 
		
		return getLatLngDistance(start, end);
		
	}
	public static void main(String[] args) {
		
		double lngVal =113.55591758616305; 	// 经度
		
		double latVal =22.251017257879496; 	//纬度
		
		
//		LatLng start1 = new LatLng(lngVal,latVal); 
//		
//		
//		LatLng end1 = new LatLng(lngVal,latVal+.0001); 
//		
//		System.out.println(getLatLngDistance(start1, end1));
//		
		
//		System.out.println(getLatLngDistance(lngVal, latVal, lngVal, latVal+0.00001));
//		System.out.println(getLatLngDistance(lngVal, latVal, lngVal, latVal+0.0001));
//		System.out.println(getLatLngDistance(lngVal, latVal, lngVal, latVal+0.001));
//		System.out.println(getLatLngDistance(lngVal, latVal, lngVal, latVal+0.01));
//		System.out.println(getLatLngDistance(lngVal, latVal, lngVal, latVal+0.1));
//		System.out.println(getLatLngDistance(lngVal, latVal, lngVal, latVal+.1));
//		
//		System.out.println("------------------------------------------------------");
//		
//		System.out.println(getLatLngDistance(lngVal, latVal, lngVal+0.00001, latVal));
//		System.out.println(getLatLngDistance(lngVal, latVal, lngVal+0.0001, latVal));
//		System.out.println(getLatLngDistance(lngVal, latVal, lngVal+.001, latVal));
//		System.out.println(getLatLngDistance(lngVal, latVal, lngVal+.01, latVal));
//		System.out.println(getLatLngDistance(lngVal, latVal, lngVal+.1, latVal));
//		
//		System.out.println("\n//////////////////////////////////////////////////////////////////////\n");
//		
//		System.out.println(GetDistance(lngVal, latVal, lngVal, latVal+0.00001));
//		System.out.println(GetDistance(lngVal, latVal, lngVal, latVal+0.0001));
//		System.out.println(GetDistance(lngVal, latVal, lngVal, latVal+0.001));
//		System.out.println(GetDistance(lngVal, latVal, lngVal, latVal+0.01));
//		System.out.println(GetDistance(lngVal, latVal, lngVal, latVal+0.1));
//		System.out.println(GetDistance(lngVal, latVal, lngVal, latVal+.1));
//		
//		System.out.println("------------------------------------------------------");
//		
//		System.out.println(GetDistance(lngVal, latVal, lngVal+0.00001, latVal));
//		System.out.println(GetDistance(lngVal, latVal, lngVal+0.0001, latVal));
//		System.out.println(GetDistance(lngVal, latVal, lngVal+.001, latVal));
//		System.out.println(GetDistance(lngVal, latVal, lngVal+.01, latVal));
//		System.out.println(GetDistance(lngVal, latVal, lngVal+.1, latVal));
		
		
		
		System.out.println(GetDistance(114.02522783315226, 22.537269480059784, 113.9535342414395, 22.542214616095755));
		System.out.println(GetDistance(114.02522783315226, 22.537269480059784, 113.94886007998429, 22.531049674544136));
		System.out.println(GetDistance(114.02522783315226, 22.537269480059784, 113.90812013694996, 22.768969532891553));
	}
}
