package com.telesign.examples.score;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.telesign.response.TeleSignResponse;
import com.telesign.score.ScoreClient;

public class CheckPhoneNoRiskLevel {
	public static void main(String[] args) {
		String customer_id = "customer_id";
		String secret_key = "secret_key";

		String phone_number = "phone_number";
		String account_lifecycle_event = "create";

		// Configuration map for initializing constructor
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readTimeout", "30000");
		params.put("connectTimeout", "30000");
		params.put("httpsProtocol", "TLSv1.1");
		
		ScoreClient scoreClient = new ScoreClient(customer_id, secret_key, params);
		HashMap<String, String> scoreParams = new HashMap<String, String>();
		TeleSignResponse response = scoreClient.score(phone_number, account_lifecycle_event, scoreParams);
		
		if(response.getStatusLine().equalsIgnoreCase("ok")){
			JsonObject jsonObject = response.getBody().getAsJsonObject();
			String riskLevel = (jsonObject.getAsJsonObject("risk")).get("level").getAsString();
			String recommendation = (jsonObject.getAsJsonObject("risk")).get("recommendation").getAsString();
			System.out.format("Phone number %s has a '%s' risk level and the recommendation is to '%s' the transaction.", phone_number, riskLevel, recommendation);
		}
		}
}
