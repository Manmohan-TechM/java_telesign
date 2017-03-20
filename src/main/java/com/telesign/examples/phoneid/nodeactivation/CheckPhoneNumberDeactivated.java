package com.telesign.examples.phoneid.nodeactivation;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.telesign.phoneid.PhoneId;
import com.telesign.response.TeleSignResponse;

public class CheckPhoneNumberDeactivated {

	public static void main(String[] args) {
		String customer_id = "customer_id";
		String secret_key = "secret_key";

		String phone_number = "phone_number";
		String ucid = "ATCK";

		// verify configuration map for initializing verify constructor
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readTimeout", "30000");
		params.put("connectTimeout", "30000");
		params.put("httpsProtocol", "TLSv1.1");

		PhoneId phoneId = new PhoneId(customer_id, secret_key, params);
		HashMap<String, String> deactivationParams = new HashMap<String, String>();
		// Fetch Response from rest.telesign.com
		TeleSignResponse response = phoneId.numberDeactivation(phone_number, ucid, deactivationParams);

		if (response.getStatusLine().equalsIgnoreCase("ok")) {
			JsonObject jsonObject = response.getBody().getAsJsonObject();
			JsonObject number_deactivation = jsonObject.getAsJsonObject("number_deactivation");
			if (number_deactivation.get("last_deactivated").getAsBoolean()) {
				String number = number_deactivation.get("number").getAsString();
				String last_deactivated = number_deactivation.get("last_deactivated").getAsString();
				System.out.format("Phone number %s was last deactivated %s.", number, last_deactivated);
			} else {
				String number = number_deactivation.get("number").getAsString();
				System.out.format("Phone number %s has not been deactivated.", number);
			}
		}

	}

}
