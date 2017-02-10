package com.telesign.messaging;

import java.io.IOException;
import java.util.Map;

import com.telesign.response.TeleSignResponse;
import com.telesign.util.TeleSignRequest;
import com.telesign.util.TeleSignUtils;

/**
 * TeleSign's Messaging API allows you to easily send SMS messages. You can send
 * alerts, reminders, and notifications, or you can send verification messages
 * containing one-time passcodes (OTP).
 */
public class MessagingClient {
	private final String customer_id;
	private final String secret_key;
	private Map<String, String> params;
	private TeleSignResponse tsResponse;
	private static final String MESSAGING_RESOURCE = "/v1/messaging";
	private static final String MESSAGING_STATUS_RESOURCE = "/v1/messaging/";
	private static final String API_BASE_URL = "https://rest.telesign.com";

	public MessagingClient(String customer_id, String secret_key,
			Map<String, String> params) {

		this.customer_id = customer_id;
		this.secret_key = secret_key;
		this.params = params;
	}

	/**
	 * Send a message to the target phone_number. See <a href
	 * ="https://developer.telesign.com/v2.0/docs/messaging-api">for detailed
	 * API documentation</a>.
	 * 
	 * @param phone_number
	 * @param message
	 * @param message_type
	 * @param messageParams
	 * @return
	 */
	public TeleSignResponse message(String phone_number, String message,
			String message_type, Map<String, String> messageParams) {
		tsResponse = new TeleSignResponse();
		try {
			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL,
					MESSAGING_RESOURCE, "POST", customer_id, secret_key, params);
			messageParams.put("phone_number", phone_number);
			messageParams.put("message", message);
			messageParams.put("message_type", message_type);
			StringBuffer body = TeleSignUtils.parsePostParams(messageParams);

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
	 * Retrieves the current status of the message. See <a
	 * href="https://developer.telesign.com/v2.0/docs/messaging-api"> for
	 * detailed API documentation</a>.
	 * 
	 * @return
	 */
	public TeleSignResponse status(String reference_id,
			Map<String, String> statusParams) {

		tsResponse = new TeleSignResponse();
		try {

			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL,
					MESSAGING_STATUS_RESOURCE + reference_id, "GET",
					customer_id, secret_key, params);
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
