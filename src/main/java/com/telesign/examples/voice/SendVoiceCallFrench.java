package com.telesign.examples.voice;

import java.util.HashMap;

import com.telesign.response.TeleSignResponse;
import com.telesign.voice.VoiceClient;

public class SendVoiceCallFrench {

	public static void main(String[] args) {
		String customer_id = "customer_id";
		String secret_key = "secret_key";

		String phone_number = "phone_number";
		String message = "N'oubliez pas d'appeler votre mère pour son anniversaire demain.";
		String message_type = "ARN";

		// Configuration map for initializing constructor
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readTimeout", "30000");
		params.put("connectTimeout", "30000");
		params.put("httpsProtocol", "TLSv1.1");

		VoiceClient voiceClient = new VoiceClient(customer_id, secret_key, params);
		HashMap<String, String> voiceParams = new HashMap<String, String>();
		voiceParams.put("voice", "f-FR-fr");
		TeleSignResponse response = voiceClient.call(phone_number, message, message_type, voiceParams);

		if (response.getStatusLine().equalsIgnoreCase("ok")) {
			System.out.println("Call sent with message");
		}

	}

}
