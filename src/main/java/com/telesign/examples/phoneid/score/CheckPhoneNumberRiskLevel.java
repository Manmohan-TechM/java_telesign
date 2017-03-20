package com.telesign.examples.phoneid.score;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.telesign.phoneid.PhoneId;
import com.telesign.response.TeleSignResponse;

public class CheckPhoneNumberRiskLevel {

	public static void main(String[] args) {
		String customer_id = "customer_id";
		String secret_key = "secret_key";

		String phone_number = "phone_number";
		String ucid = "BACF";

		// Configuration map for initializing constructor
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readTimeout", "30000");
		params.put("connectTimeout", "30000");
		params.put("httpsProtocol", "TLSv1.1");

		PhoneId phoneId = new PhoneId(customer_id, secret_key, params);
		HashMap<String, String> scoreParams = new HashMap<String, String>();
		// Fetch Response from rest.telesign.com
		TeleSignResponse response = phoneId.score(phone_number, ucid, scoreParams);

		if (response.getStatusLine().equalsIgnoreCase("ok")) {
			JsonObject jsonObject = response.getBody().getAsJsonObject();
			JsonObject riskJsonObject = jsonObject.getAsJsonObject("risk");

			String level = riskJsonObject.get("level").getAsString();
			String recommendation = riskJsonObject.get("recommendation").getAsString();
			System.out.format(
					"Phone number %s has a '%s' risk level and the recommendation is to '%s' the transaction.",
					phone_number, level, recommendation);

		}

	}

}
