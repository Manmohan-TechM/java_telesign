package com.telesign.examples.verify.sms;

import java.util.HashMap;

import com.telesign.response.TeleSignResponse;
import com.telesign.verify.Verify;

public class SendCustomSmsWithCustomSenderId {

	public static void main(String[] args) {
		String customer_id = "customer_id";
		String secret_key = "secret_key";

		String phone_number = "phone_number";
		String my_sender_id = "my_sender_id"; // Client Services must white list any custom sender_id for it to take effect

		// verify configuration map for initializing verify constructor
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readTimeout", "30000");
		params.put("connectTimeout", "30000");
		params.put("httpsProtocol", "TLSv1.1");

		Verify ver = new Verify(customer_id, secret_key, params);
		
		HashMap<String, String> smsParams = new HashMap<String, String>();
		smsParams.put("my_sender_id", my_sender_id);

		TeleSignResponse response = ver.sms(phone_number, smsParams);
		if (response.getStatusLine().equalsIgnoreCase("ok")) {
			System.out.println("Custom Sms with sender id sent");
		}

	}

}
