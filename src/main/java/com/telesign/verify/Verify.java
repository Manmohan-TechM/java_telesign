/**
 * @version     1.0
 * @copyright   Copyright © 2013, TeleSign Corporation.
 * @license     http://opensource.org/licenses/mit-license.php The MIT License (MIT).
 * @author      John Weatherford
 * @maintainer  Humberto Morales
 * @repository  https://github.com/TeleSign/java_telesign
 * @support     support@telesign.com
 */
package com.telesign.verify;

import java.io.IOException;
import java.util.Map;

import com.telesign.response.TeleSignResponse;
import com.telesign.util.TeleSignRequest;
import com.telesign.util.TeleSignUtils;

/**
 * The Verify API delivers phone-based verification and two-factor
 * authentication using a time-based, one-time passcode sent via SMS message,
 * Voice call or Push Notification. 
 */
public class Verify {

	private final String customer_id;
	private final String secret_key;

	private Map<String, String> verifyParams;
	private static final String API_BASE_URL = "https://rest.telesign.com";
	private static final String API_MOBILE_URL = "https://rest-mobile.telesign.com";

	private static final String V1_VERIFY = "/v1/verify/";
	private static final String V1_VERIFY_SMS = "/v1/verify/sms";
	private static final String V1_VERIFY_CALL = "/v1/verify/call";
	private static final String V1_VERIFY_SMART = "/v1/verify/smart";

	private static final String V2_VERIFY_PUSH = "/v2/verify/push";
	private static final String V2_VERIFY_TOKEN = "/v2/verify/soft_token";
	private static final String V2_VERIFY_REGISTRATION = "/v2/verify/registration/";

	private TeleSignResponse tsResponse;

	/**
	 * The Verify class constructor. Once you instantiate a Verify object, you
	 * can use it to make instance calls to <em>Verify SMS</em> and
	 * <em>Verify Call</em>.
	 * 
	 * @param customer_id
	 *            [Required] A string containing your TeleSign Customer ID (your
	 *            TeleSign account number).
	 * @param secret_key
	 *            [Required] A string containing your TeleSign Secret Key (a
	 *            bese64-encoded string valu, available from the TeleSign Client
	 *            Portal).
	 */
	public Verify(String customer_id, String secret_key,
			Map<String, String> params) {
		this.customer_id = customer_id;
		this.secret_key = secret_key;
		this.verifyParams = params;
	}

	/**
	 * The SMS Verify API delivers phone-based verification and two-factor
	 * authentication using a time-based, one-time passcode sent over SMS. See
	 * https://developer.telesign.com/docs/rest_api-verify-sms for detailed API
	 * documentation.
	 */
	public TeleSignResponse sms(String phone_number,
			Map<String, String> smsParams) {

		tsResponse = new TeleSignResponse();
		try {

			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL,
					V1_VERIFY_SMS, "POST", customer_id, secret_key,
					verifyParams);
			smsParams.put("phone_number", phone_number);
			StringBuffer body = TeleSignUtils.parsePostParams(smsParams);

			tr.setPostBody(body.toString());

			tsResponse = tr.executeRequest();
		} catch (IOException e) {

			System.err.println("IOException while executing Verify SMS API: "
					+ e.getMessage());
			throw new RuntimeException(e);
		}

		return tsResponse;
	}

	/**
	 * The Voice Verify API delivers patented phone-based verification and
	 * two-factor authentication using a one-time passcode sent over voice
	 * message. See https://developer.telesign.com/docs/rest_api-verify-call for
	 * detailed API documentation.
	 */
	public TeleSignResponse voice(String phone_number,
			Map<String, String> voiceParams) {

		tsResponse = new TeleSignResponse();
		try {
			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL,
					V1_VERIFY_CALL, "POST", customer_id, secret_key,
					verifyParams);
			voiceParams.put("phone_number", phone_number);
			StringBuffer body = TeleSignUtils.parsePostParams(voiceParams);

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
	 * Retrieves the verification result for any verify resource. See
	 * https://developer.telesign.com/docs/rest_api-verify-transaction-callback
	 * for detailed API documentation.
	 */
	public TeleSignResponse status(String resource_id,
			Map<String, String> statusParams) {
		tsResponse = new TeleSignResponse();
		try {

			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL, V1_VERIFY
					+ resource_id, "GET", customer_id, secret_key, verifyParams);
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

	/**
	 * @param phone_number
	 *            [Required] Your end user's phone number, including the country
	 *            code.
	 * @param bundle_id
	 *            [optional] The identifier associated with your whitelabel app
	 *            (your customized/branded version of the AuthID application).
	 * @param originating_ip
	 *            [Optional] Your end users IP Address. This value must be in
	 *            the format defined by IETF in the Internet-Draft document
	 *            titled Textual Representation of IPv4 and IPv6 Addresses. Ex:
	 *            originating_ip=192.168.123.456. Set it to null if not sending
	 *            originating ip.
	 * @param session_id
	 *            [Optional] Your end users session id. Set it to "null" if not
	 *            sending session id.
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object,
	 *         which contains the JSON-formatted response body from the TeleSign
	 *         server.
	 */
	public TeleSignResponse registration(String phone_number,
			Map<String, String> registrationParams) {
		tsResponse = new TeleSignResponse();
		try {
			TeleSignRequest tr = new TeleSignRequest(API_MOBILE_URL,
					V2_VERIFY_REGISTRATION + phone_number, "GET", customer_id,
					secret_key, verifyParams);
			TeleSignUtils.parseGetParams(tr, registrationParams);

			tsResponse = tr.executeRequest();
		} catch (IOException e) {

			System.err
					.println("IOException while executing verify registration API: "
							+ e.getMessage());
			throw new RuntimeException(e);
		}

		return tsResponse;
	}

	/**
	 * The Smart Verify web service simplifies the process of verifying user
	 * identity by integrating several TeleSign web services into a single API
	 * call. This eliminates the need for you to make multiple calls to the
	 * TeleSign Verify resource. See
	 * https://developer.telesign.com/docs/rest_api-smart-verify for detailed
	 * API documentation.
	 */
	public TeleSignResponse smart(String phone_number,
			Map<String, String> smartVerifyParams) {
		tsResponse = new TeleSignResponse();
		try {

			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL,
					V1_VERIFY_SMART, "POST", customer_id, secret_key,
					verifyParams);
			smartVerifyParams.put("phone_number", phone_number);
			StringBuffer body = TeleSignUtils
					.parsePostParams(smartVerifyParams);

			tr.setPostBody(body.toString());

			tsResponse = tr.executeRequest();
		} catch (IOException e) {

			System.err.println("IOException while executing smart verify API: "
					+ e.getMessage());
			throw new RuntimeException(e);
		}
		return tsResponse;
	}

	/**
	 * The Push Verify web service allows you to provide on-device transaction
	 * authorization for your end users. It works by delivering authorization
	 * requests to your end users via push notification, and then by receiving
	 * their permission responses via their mobile device's wireless Internet
	 * connection. See https://developer.telesign.com/docs/rest_api-verify-push
	 * for detailed API documentation.
	 */
	public TeleSignResponse push(String phone_number,
			Map<String, String> pushParams) {
		tsResponse = new TeleSignResponse();
		try {
			TeleSignRequest tr = new TeleSignRequest(API_MOBILE_URL,
					V2_VERIFY_PUSH, "POST", customer_id, secret_key,
					verifyParams);
			pushParams.put("phone_number", phone_number);
			StringBuffer body = TeleSignUtils.parsePostParams(pushParams);

			tr.setPostBody(body.toString());

			tsResponse = tr.executeRequest();
		} catch (IOException e) {

			System.err.println("IOException while executing Verify push API: "
					+ e.getMessage());
			throw new RuntimeException(e);
		}
		return tsResponse;
	}

	/**
	 * @param phone_number
	 *            [Required] The phone number for the Verify Soft Token request,
	 *            including country code. For example, phone_number=13105551212.
	 * @param soft_token_id
	 *            [Optional] The alphanumeric string that uniquely identifies
	 *            your TeleSign soft token subscription.
	 * @param verify_code
	 *            [Required] The verification code received from the end user.
	 * @param bundle_id
	 *            [Optional]
	 * @param originating_ip
	 *            [Optional] Your end users IP Address. This value must be in
	 *            the format defined by IETF in the Internet-Draft document
	 *            titled Textual Representation of IPv4 and IPv6 Addresses. Ex:
	 *            originating_ip=192.168.123.456. Set it to null if not sending
	 *            originating ip.
	 * @param session_id
	 *            [Optional] Your end users session id. Set it to "null" if not
	 *            sending session id.
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object,
	 *         which contains the JSON-formatted response body from the TeleSign
	 *         server.
	 */
	public TeleSignResponse softToken(String phone_number,
			Map<String, String> softTokenParams) {
		tsResponse = new TeleSignResponse();
		try {
			TeleSignRequest tr = new TeleSignRequest(API_MOBILE_URL,
					V2_VERIFY_TOKEN, "POST", customer_id, secret_key,
					verifyParams);
			softTokenParams.put("phone_number", phone_number);
			StringBuffer body = TeleSignUtils.parsePostParams(softTokenParams);
			tr.setPostBody(body.toString());

			tsResponse = tr.executeRequest();
		} catch (IOException e) {
			System.err
					.println("IOException while executing Verify soft token API: "
							+ e.getMessage());
			throw new RuntimeException(e);
		}

		return tsResponse;
	}
}
