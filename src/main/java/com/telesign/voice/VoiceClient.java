package com.telesign.voice;

import java.io.IOException;
import java.util.Map;

import com.telesign.response.TeleSignResponse;
import com.telesign.util.TeleSignRequest;
import com.telesign.util.TeleSignUtils;

/**
 * TeleSign's Voice API allows you to easily send voice messages. You can send
 * alerts, reminders, and notifications, or you can send verification messages
 * containing time-based, one-time passcodes (TOTP).
 */
public class VoiceClient {

	private final String customer_id;
	private final String secret_key;
	private Map<String, String> params;
	private TeleSignResponse tsResponse;
	private static final String VOICE_RESOURCE = "/v1/voice";
	private static final String VOICE_STATUS_RESOURCE = "/v1/voice/";
	private static final String API_BASE_URL = "https://rest-ww.telesign.com";

	public VoiceClient(String customer_id, String secret_key,
			Map<String, String> params) {

		this.customer_id = customer_id;
		this.secret_key = secret_key;
		this.params = params;
	}

	/**
	 * Send a voice call to the target phone_number. See <a
	 * href="https://developer.telesign.com/docs/voice-api"> for detailed API
	 * documentation </a>.
	 * 
	 * @param phone_number
	 * @param message
	 * @param message_type
	 * @param params
	 * @return
	 */
	public TeleSignResponse call(String phone_number, String message,
			String message_type, Map<String, String> callParams) {
		tsResponse = new TeleSignResponse();
		try {
			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL,
					VOICE_RESOURCE, "POST", customer_id, secret_key, params);
			callParams.put("phone_number", phone_number);
			callParams.put("message", message);
			callParams.put("message_type", message_type);
			StringBuffer body = TeleSignUtils.parsePostParams(callParams);

			tr.setPostBody(body.toString());

			tsResponse = tr.executeRequest();
		} catch (IOException e) {

			System.err.println("IOException while executing verify call API: "
					+ e.getMessage());
			throw new RuntimeException(e);
		}
		return tsResponse;
	}

	/**
	 * Retrieves the current status of the voice call. See <a
	 * href="https://developer.telesign.com/docs/voice-api"> for detailed API
	 * documentation</a>.
	 * 	 
	 * @param reference_id
	 * @param statusParams
	 * @return
	 */
	public TeleSignResponse status(String reference_id,
			Map<String, String> statusParams) {

		tsResponse = new TeleSignResponse();
		try {

			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL,
					VOICE_STATUS_RESOURCE + reference_id, "GET", customer_id,
					secret_key, params);
			TeleSignUtils.parseGetParams(tr, statusParams);

			tsResponse = tr.executeRequest();
		} catch (IOException e) {

			System.err
					.println("IOException while executing Verify status API: "
							+ e.getMessage());
			throw new RuntimeException(e);
		}
		return tsResponse;
	}
}
