package com.telesign.examples.verify.voice;

import java.util.HashMap;

import com.telesign.response.TeleSignResponse;
import com.telesign.verify.Verify;

public class SendCustomVoiceCallInDifferentLanguage {

	public static void main(String[] args) {
		String customer_id = "customer_id";
		String secret_key = "secret_key";

		String phone_number = "phone_number";
		String language = "fr-FR";
		String tts_message = "Votre code de vérification Widgets 'n' More est $$CODE$$.";

		// Configuration map for initializing constructor
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readTimeout", "30000");
		params.put("connectTimeout", "30000");
		params.put("httpsProtocol", "TLSv1.1");

		Verify verify = new Verify(customer_id, secret_key, params);

		HashMap<String, String> callParams = new HashMap<String, String>();
		callParams.put("language", language);
		callParams.put("tts_message", tts_message);

		TeleSignResponse response = verify.voice(phone_number, callParams);

		if (response.getStatusLine().equalsIgnoreCase("ok")) {
			System.out.println("Call sent with message");
		}

	}

}
