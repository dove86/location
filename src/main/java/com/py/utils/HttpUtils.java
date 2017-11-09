package com.py.utils;

import com.py.common.PyConstants;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.Asserts;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {
	// private static Logger logger = Logger.getLogger(HttpUtils.class);

	private static CloseableHttpClient httpclient = HttpClients.createDefault();

	public static String get(String reqUrl, Map<String, String> paramsMap) throws Exception {
		String reponseStr = null;

		if (paramsMap != null && paramsMap.size() > 0) {
			String paramsStr = "?";
			for (String key : paramsMap.keySet()) {
				paramsStr += key + "=" + URLEncoder.encode(paramsMap.get(key), PyConstants.ENCODING) + "&";
			}
			paramsStr = StringUtils.substring(paramsStr, 0, paramsStr.length() - 1);
			reqUrl = reqUrl + paramsStr;
		}

//		LoggerUtils.info("reqUrl:"+reqUrl);
		HttpGet httpGet = new HttpGet(reqUrl);
		httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
		CloseableHttpResponse response = httpclient.execute(httpGet);
		try {
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				throw new RuntimeException("http 请求失败:[statusCode:" + statusCode + " ]");
			}
			HttpEntity entity = response.getEntity();
			reponseStr = EntityUtils.toString(entity, "UTF-8");
			EntityUtils.consume(entity);
//			LoggerUtils.info("reponseStr:"+reponseStr);
		} finally {
			if (response != null) {

				response.close();
			}

		}

		return reponseStr;

	}

	public static String post(String reqUrl, Map<String, String> paramsMap) throws Exception {
		String reponseStr = null;

		HttpPost httpPost = new HttpPost(reqUrl);

		List<NameValuePair> postParameters = mapToPairList(paramsMap);
		httpPost.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		CloseableHttpResponse response = httpclient.execute(httpPost);
		try {
			int statusCode = response.getStatusLine().getStatusCode();
			// logger.info("getStatusCode:" + statusCode);
			if (statusCode != 200) {
				throw new RuntimeException("http 请求失败:[statusCode:" + statusCode + " ]");
			}
			HttpEntity entity = response.getEntity();
			reponseStr = EntityUtils.toString(entity, "UTF-8");
			// logger.info("reponseStr:" + reponseStr);
			EntityUtils.consume(entity);
		} finally {
			if (response != null) {
				response.close();
			}

		}

		return reponseStr;

	}

	public static List<NameValuePair> mapToPairList(Map<String, String> paramsMap) {
		Asserts.notNull(paramsMap, "请求参数");
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		for (String key : paramsMap.keySet()) {
			postParameters.add(new BasicNameValuePair(key, paramsMap.get(key)));
		}
		return postParameters;
	}

	@SneakyThrows
	public static void closeClient() {
		if (httpclient != null) {
			httpclient.close();
		}
	}

}