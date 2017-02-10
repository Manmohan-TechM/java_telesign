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
	 * @param postParams
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static StringBuffer parsePostParams(Map<String, String> postParams)
			throws UnsupportedEncodingException {
		StringBuffer body = new StringBuffer();
		
		for (Map.Entry<String, String> param : postParams.entrySet()) {
			if(body.length() == 0)
				body.append(param.getKey()).append("=").append(URLEncoder.encode(param.getValue(), "UTF-8"));
			else
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
