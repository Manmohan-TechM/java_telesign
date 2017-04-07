package com.telesign.examples.messaging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.telesign.messaging.MessagingClient;
import com.telesign.response.TeleSignResponse;

public class MessagingExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String customer_id = "customer_id";
		String secret_key = "secret_key";

		String phone_number = "phone_number";
		String message = "You're scheduled for a dentist appointment at 2:30PM.";
		String message_type = "ARN";
		
		// verify configuration map for initializing verify constructor
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readTimeout", "30000");
		params.put("connectTimeout", "30000");
		//params.put("httpsProtocol", "TLSv1.1");
		
		params.put("httpProxyIPAddress", "inpout-http-Proxy-IPAddress");
		params.put("httpProxyPort", "input-Proxy-Port");
		params.put("httpProxyUsername", "input-Proxy-Username");
		params.put("httpProxyPassword", "input-Proxy-Password");

		MessagingClient messagingClient = new MessagingClient(customer_id, secret_key, params);
		// Fetch Response from rest.telesign.com 
		TeleSignResponse response = messagingClient.message(phone_number, message, message_type, null);
				
		// 1. Read TeleSignResponse body
		if(response.getStatusLine().equalsIgnoreCase("ok")){
			JsonObject jsonObject = response.getBody().getAsJsonObject();	
			// 1.1 Get the Resource Uri
			System.out.println(jsonObject.get("resource_uri"));
			// 1.2 Get the reference ID/
			System.out.println(jsonObject.get("reference_id"));
		}
		
		// 2. Read Response Headers
		for(Map.Entry<String, List<String>>header:response.getHeaders().entrySet())
			System.out.println(header.getKey() + " : " + header.getValue());
		
		// 3. Look at Telesign Returned response (includes headers and everything)
		System.out.println(response.toString());
		}
}
