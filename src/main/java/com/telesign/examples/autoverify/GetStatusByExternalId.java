package com.telesign.examples.autoverify;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.telesign.autoverify.AutoVerifyClient;
import com.telesign.response.TeleSignResponse;

public class GetStatusByExternalId {
	public static void main(String[] args) {
		String customer_id = "customer_id";
		String secret_key = "secret_key";		

		String external_id = "external_id";

		// verify configuration map for initializing verify constructor
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readTimeout", "30000");
		params.put("connectTimeout", "30000");
		params.put("httpsProtocol", "TLSv1.1");
		
		AutoVerifyClient autoVerifyClient = new AutoVerifyClient(customer_id, secret_key, params);
		
		HashMap<String, String> statusParams = new HashMap<String, String>();
		TeleSignResponse response = autoVerifyClient.status(external_id, statusParams);
		
		if(response.getStatusLine().equalsIgnoreCase("ok")){
			JsonObject jsonObject = response.getBody().getAsJsonObject();
			JsonObject statusObject = jsonObject.getAsJsonObject("status");
			System.out.format("AutoVerify transaction with external_id %s has status code %s and status description %s.", external_id, statusObject.getAsJsonObject("code").getAsString(), statusObject.getAsJsonObject("description").getAsString());
		}
		
	}
}
