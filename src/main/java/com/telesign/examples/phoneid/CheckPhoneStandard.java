package com.telesign.examples.phoneid;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.telesign.phoneid.PhoneId;
import com.telesign.phoneid.PhoneIdClient;
import com.telesign.response.TeleSignResponse;

public class CheckPhoneStandard {
	public static void main(String[] args) {
		String customer_id = "customer_id";
		String secret_key = "secret_key";

		String phone_number = "phone_number";
		String phone_type_voip = "5";

		
		// verify configuration map for initializing verify constructor
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readTimeout", "30000");
		params.put("connectTimeout", "30000");
		params.put("httpsProtocol", "TLSv1.1");

		PhoneId phoneId = new PhoneId(customer_id, secret_key, params);
		HashMap<String, String> standardParams = new HashMap<String, String>();
		// Fetch Response from rest.telesign.com 
		TeleSignResponse response = phoneId.standard(phone_number, standardParams);
		if(response.getStatusLine().equalsIgnoreCase("ok")){
			JsonObject jsonObject = response.getBody().getAsJsonObject();
			if((jsonObject.getAsJsonObject("phone_type")).get("code").equals(phone_type_voip)){
				System.out.format("Phone number %s is a VOIP phone.",phone_number);
			} else {
				System.out.format("Phone number %s is not a VOIP phone.",phone_number);
			}
			
		}	
		
	}
}
