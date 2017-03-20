package com.telesign.phoneid;

import java.io.IOException;
import java.util.Map;

import com.telesign.response.TeleSignResponse;
import com.telesign.util.TeleSignRequest;
import com.telesign.util.TeleSignUtils;

/**
 * The TeleSign Data APIs that deliver deep phone number data attributes that
 * help optimize the end user verification process and evaluate risk.
 */
public class PhoneIdClient {
	private final String customer_id;
	private final String secret_key;
	private Map<String, String> params;
	private TeleSignResponse tsResponse;
	private static final String API_BASE_URL = "https://rest-api.telesign.com";
	private static final String PHONEID_RESOURCE = "/v1/phoneid/";

	public PhoneIdClient(String customer_id, String secret_key,
			Map<String, String> params) {

		this.customer_id = customer_id;
		this.secret_key = secret_key;
		this.params = params;
	}

	/**
	 * The PhoneID API provides a cleansed phone number, phone type, and telecom
	 * carrier information to determine the best communication method - SMS or
	 * voice. See https://developer.telesign.com/docs/phoneid-api for detailed API
	 * documentation.
	 * 
	 * @param phone_number
	 * @param phoneidParams
	 * @return
	 */
	public TeleSignResponse phoneid(String phone_number,
			Map<String, String> phoneidParams) {
		tsResponse = new TeleSignResponse();
		try {
			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL,
					PHONEID_RESOURCE + phone_number, "POST", customer_id,
					secret_key, params);
			if(null != phoneidParams){
				StringBuffer body = TeleSignUtils.parsePostParams(phoneidParams);

				tr.setPostBody(body.toString());	
			}
			
			tsResponse = tr.executeRequest();
		} catch (IOException e) {

			System.err.println("IOException while executing phoneid API: "
					+ e.getMessage());
			throw new RuntimeException(e);
		}
		return tsResponse;
	}

}
