package com.telesign.examples.phoneid;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.telesign.phoneid.PhoneIdClient;
import com.telesign.response.TeleSignResponse;

public class Cleansing {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String customer_id = "customer_id";
		String secret_key = "secret_key";

		String phone_number = "phone_number";
		String extra_digit = "0";
		String incorrect_phone_number = phone_number + extra_digit;
		
		// verify configuration map for initializing verify constructor
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readTimeout", "30000");
		params.put("connectTimeout", "30000");
		params.put("httpsProtocol", "TLSv1.1");

		PhoneIdClient phoneIdClient = new PhoneIdClient(customer_id, secret_key, params);
		HashMap<String, String> phoneidParams = new HashMap<String, String>();
		phoneidParams.put("account_lifecycle_event", "create");
		// Fetch Response from rest.telesign.com 
		TeleSignResponse response = phoneIdClient.phoneid(incorrect_phone_number, phoneidParams);
		JsonObject jsonObject = response.getBody().getAsJsonObject();		
		
		JsonObject numbering = jsonObject.getAsJsonObject("numbering");
		JsonObject cleansing = numbering.getAsJsonObject("cleansing");
		JsonObject call = cleansing.getAsJsonObject("call");
		
		String country_code = call.get("country_code").getAsString();
		String cleansedNo = call.get("phone_number").getAsString();

		System.out.format("Cleansed phone number has country code %s and phone number is %s.", country_code, cleansedNo);		
		System.out.format("Original phone number was %s",((jsonObject.getAsJsonObject("numbering")).getAsJsonObject("original")).get("complete_phone_number").getAsString());


	}

}
