package com.telesign.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeleSignUtils {
	public static enum ConfigParam {
		READTIMEOUT, CONNECTIONTIMEOUT, HTTPSPROTOCOL;
	}

	private static enum VerifyParam {
		LANGUAGE, SESSIONID, ORIGINATINGIP, TEMPLATE, VERIFYCODE, VERIFYMETHOD, EXTENSIONTYPE, EXTENSIONTEMPLATE, REDIAL, CALLFORWARDACTION, TTSMESSAGE, UCID, CALLERID, PREFERENCE, IGNORERISK, PUSHMESSAGE, SMSMESSAGE, BUNDLEID, NOTIFICATIONTYPE,NOTIFICATIONVALUE, SOFTTOKENID;
	}
	private static enum VerifyGetParam {
		
	} 
	public static enum PhoneidServiceParam {

	}
	
	/**
	 * @param phone_number
	 * @param smsParams
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String parseVerifyPostParams(String phone_number,
			Map<String, String> verifyParams) throws UnsupportedEncodingException {
		String body = "phone_number=" + URLEncoder.encode(phone_number, "UTF-8");
		for(Map.Entry<String, String> param : verifyParams.entrySet())
		{
			VerifyParam verifyServiceParam = VerifyParam.valueOf(param.getKey().toUpperCase());
			switch(verifyServiceParam){
			case LANGUAGE:
				body += "&language=" + URLEncoder.encode(param.getValue(), "UTF-8");
				break;
			case ORIGINATINGIP:
				if(IpValidator.isValidIpAddress(param.getValue()))
					body += "&originating_ip=" + URLEncoder.encode(param.getValue(), "UTF-8");
				break;
			case SESSIONID:
				body += "&session_id=" + URLEncoder.encode(param.getValue(), "UTF-8");
				break;
			case TEMPLATE:
				body += "&template=" + URLEncoder.encode(param.getValue(), "UTF-8");
				break;
			case VERIFYCODE:
				body += "&verify_code=" + URLEncoder.encode(param.getValue(), "UTF-8");
			case VERIFYMETHOD:	
				body += "&verify_method=" + URLEncoder.encode(param.getValue(), "UTF-8");
			case EXTENSIONTYPE:
				body += "&extension_type=" + URLEncoder.encode(param.getValue(), "UTF-8");
			case EXTENSIONTEMPLATE:
				body += "&extension_template=" + URLEncoder.encode(param.getValue(), "UTF-8");
			case REDIAL:
				body += "&redial=" + URLEncoder.encode(param.getValue(), "UTF-8");
			case CALLFORWARDACTION:
				String call_forward_action = param.getValue();
				if ("block".equalsIgnoreCase(call_forward_action)) {

					body += "&call_forward_action=" + URLEncoder.encode("Block", "UTF-8");

				} else if ("flag".equalsIgnoreCase(call_forward_action)) {

					body += "&call_forward_action=" + URLEncoder.encode("Flag", "UTF-8");

				}
				break;
			case TTSMESSAGE:
				body += "&tts_message=" + URLEncoder.encode(param.getValue(), "UTF-8");
				break;
			case UCID:
				body += "&ucid=" + URLEncoder.encode(param.getValue(), "UTF-8");
				break;
			case CALLERID:
				body += "&caller_id=" + URLEncoder.encode(param.getValue(), "UTF-8");
				break;
			case PREFERENCE:	
				body += "&preference=" + URLEncoder.encode(param.getValue(), "UTF-8");
				break;
			case IGNORERISK:
				body += "&ignore_risk=" + URLEncoder.encode(param.getValue(), "UTF-8");
				break;
			case PUSHMESSAGE:
				body += "&push_message=" + URLEncoder.encode(param.getValue(), "UTF-8");
				break;
			case SMSMESSAGE:
				body += "&sms_message=" + URLEncoder.encode(param.getValue(), "UTF-8");
				break;
			case NOTIFICATIONTYPE:
				body += "&notification_type=" + URLEncoder.encode(param.getValue().toUpperCase(), "UTF-8");
			case NOTIFICATIONVALUE:
				String notification_value = param.getValue();
				body += "&notification_value=" + URLEncoder.encode(isValidNotificationValue(notification_value)?notification_value:null, "UTF-8");
			case BUNDLEID:
				body += "&bundle_id=" + URLEncoder.encode(param.getValue(), "UTF-8");
			case SOFTTOKENID:
				body += "&soft_token_id=" + URLEncoder.encode(param.getValue(), "UTF-8");
			default:
				break;
			}
		}		
		return body;
	}
	
	/**
	 * @param registrationParams
	 * @param tr
	 */
	public static void parseVerifyGetParams(Map<String, String> verifyParams,
			TeleSignRequest tr) {
		for(Map.Entry<String, String> param : verifyParams.entrySet()){
			VerifyParam verifyServiceParam = VerifyParam.valueOf(param.getKey().toUpperCase());
			switch(verifyServiceParam){
			case VERIFYCODE:
				tr.addParam("verify_code", param.getValue());
				break;
			case BUNDLEID:
				tr.addParam("bundle_id", param.getValue());
				break;
			case ORIGINATINGIP:
				tr.addParam("originating_ip", param.getValue());
				break;
			case SESSIONID:
				tr.addParam("session_id", param.getValue());
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * Matches the notification_value for a string having 6-8 digits 
	 * @param notification_value
	 * @return boolean value checking validity for notification_value  
	 */
	private static boolean isValidNotificationValue(String notification_value){
		if(null != notification_value){
			final Matcher m = Pattern.compile("\\d{6,8}").matcher(notification_value);		
			return m.matches();
		} else 
			return false;
	}
}
