package com.telesign.autoverify;

import java.io.IOException;
import java.util.Map;

import com.telesign.response.TeleSignResponse;
import com.telesign.util.TeleSignRequest;
import com.telesign.util.TeleSignUtils;

/**
 * AutoVerify is a secure, lightweight SDK that integrates a frictionless user
 * verification process into existing native mobile applications.
 */
public class AutoVerifyClient {
	private final String customer_id;
	private final String secret_key;
	private Map<String, String> params;
	private TeleSignResponse tsResponse;

	private static final String API_BASE_URL = "https://rest-ww.telesign.com";
	private static final String AUTOVERIFY_STATUS_RESOURCE = "/v1/mobile/verification/status/";

	public AutoVerifyClient(String customer_id, String secret_key,
			Map<String, String> params) {

		this.customer_id = customer_id;
		this.secret_key = secret_key;
		this.params = params;
	}

	/**
	 * Retrieves the verification result for an AutoVerify transaction by
	 * external_id. To ensure a secure verification flow you must check the
	 * status using TeleSign's servers on your backend. Do not rely on the SDK
	 * alone to indicate a successful verification. See <a href=
	 * "https://developer.telesign.com/docs/auto-verify-sdk#section-obtaining-verification-status"
	 * > for detailed API documentation</a>.
	 * 
	 * @param external_id
	 * @param autoVerifyParams
	 * @return TeleSignResponse
	 */
	public TeleSignResponse status(String external_id,
			Map<String, String> statusParams) {
		tsResponse = new TeleSignResponse();
		try {

			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL,
					AUTOVERIFY_STATUS_RESOURCE + external_id, "GET",
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
