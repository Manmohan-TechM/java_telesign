package com.telesign.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author Manmohan
 * The class with common utility methods. 
 */
public class TeleSignUtils {
	
	/**
	 * @param phone_number
	 * @param postParams
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static StringBuffer parsePostParams(String phone_number, Map<String, String> postParams)
			throws UnsupportedEncodingException {
		StringBuffer body = new StringBuffer();
		body.append("phone_number=").append(
				URLEncoder.encode(phone_number, "UTF-8"));
		for (Map.Entry<String, String> param : postParams.entrySet()) {
			body.append("&").append(param.getKey()).append("=")
					.append(URLEncoder.encode(param.getValue(), "UTF-8"));
		}
		return body;
	}
	
	/**
	 * @param tr
	 * @param getParams
	 */
	public static void parseGetParams(TeleSignRequest tr, Map<String, String> getParams) {	
		for(Map.Entry<String, String> param : getParams.entrySet()){
			tr.addParam(param.getKey(), param.getValue());			
		}
	}
}
