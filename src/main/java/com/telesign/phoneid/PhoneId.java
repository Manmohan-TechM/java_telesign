/**
 * @version     1.0
 * @copyright   Copyright © 2013, TeleSign Corporation.
 * @license     http://opensource.org/licenses/mit-license.php The MIT License (MIT).
 * @author      John Weatherford
 * @maintainer  Humberto Morales
 * @repository  https://github.com/TeleSign/java_telesign
 * @support     support@telesign.com
 */
package com.telesign.phoneid;

import java.io.IOException;
import java.util.Map;

import com.telesign.response.TeleSignResponse;
import com.telesign.util.TeleSignRequest;
import com.telesign.util.TeleSignUtils;

/**
 * PhoneID is a set of REST APIs that deliver deep phone number data attributes
 * that help optimize the end user verification process and evaluate risk.
 * TeleSign PhoneID provides a wide range of risk assessment indicators on the
 * number to help confirm user identity, delivering real-time decision making
 * throughout the number lifecycle and ensuring only legitimate users are
 * creating accounts and accessing your applications.
 */
public class PhoneId {

	private final String customer_id;
	private final String secret_key;

	private Map<String, String> phoneIdParams;

	private static final String API_BASE_URL = "https://rest.telesign.com";

	private static final String V1_PHONEID_STANDARD = "/v1/phoneid/standard/";
	private static final String V1_PHONEID_SCORE = "/v1/phoneid/score/";
	private static final String V1_PHONEID_CONTACT = "/v1/phoneid/contact/";
	private static final String V1_PHONEID_LIVE = "/v1/phoneid/live/";
	private static final String V1_PHONEID_NUMBER_DEACTIVATION = "/v1/phoneid/number_deactivation/";

	private TeleSignResponse tsResponse;

	/**
	 * The PhoneId class constructor.
	 * 
	 * @param customer_id
	 * @param secret_key
	 * @param params
	 */
	public PhoneId(String customer_id, String secret_key,
			Map<String, String> params) {

		this.customer_id = customer_id;
		this.secret_key = secret_key;
		this.phoneIdParams = params;
	}

	/**
	 * The PhoneID Standard API that provides phone type and telecom carrier
	 * information to identify which phone numbers can receive SMS messages
	 * and/or a potential fraud risk. See
	 * https://developer.telesign.com/docs/rest_phoneid-standard for detailed
	 * API documentation.
	 */
	public TeleSignResponse standard(String phone_number,
			Map<String, String> standardParams) {
		tsResponse = new TeleSignResponse();
		try {

			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL,
					V1_PHONEID_STANDARD + phone_number, "GET", customer_id,
					secret_key, phoneIdParams);
			TeleSignUtils.parseGetParams(tr, standardParams);

			tsResponse = tr.executeRequest();
		} catch (IOException e) {

			System.err
					.println("IOException while executing phoneid standard API: "
							+ e.getMessage());
			throw new RuntimeException(e);
		}
		return tsResponse;
	}

	/**
	 * Score is an API that delivers reputation scoring based on phone number
	 * intelligence, traffic patterns, machine learning, and a global data
	 * consortium. See
	 * https://developer.telesign.com/docs/rest_api-phoneid-score for detailed
	 * API documentation.
	 */
	public TeleSignResponse score(String phone_number, String ucid,
			Map<String, String> scoreParams) {
		tsResponse = new TeleSignResponse();
		try {

			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL,
					V1_PHONEID_SCORE + phone_number, "GET", customer_id,
					secret_key, phoneIdParams);
			tr.addParam("ucid", ucid);
			TeleSignUtils.parseGetParams(tr, scoreParams);

			tsResponse = tr.executeRequest();
		} catch (IOException e) {

			System.err
					.println("IOException while executing phoneid score API: "
							+ e.getMessage());
			throw new RuntimeException(e);
		}
		return tsResponse;
	}

	/**
	 * The PhoneID Contact API delivers contact information related to the
	 * subscriber's phone number to provide another set of indicators for
	 * established risk engines. See
	 * https://developer.telesign.com/docs/rest_api-phoneid-contact for detailed
	 * API documentation.
	 */
	public TeleSignResponse contact(String phone_number, String ucid,
			Map<String, String> contactParams) {
		tsResponse = new TeleSignResponse();
		try {

			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL,
					V1_PHONEID_CONTACT + phone_number, "GET", customer_id,
					secret_key, phoneIdParams);

			tr.addParam("ucid", ucid);

			TeleSignUtils.parseGetParams(tr, contactParams);

			tsResponse = tr.executeRequest();
		} catch (IOException e) {

			System.err
					.println("IOException while executing phoneid contact API: "
							+ e.getMessage());
			throw new RuntimeException(e);
		}
		return tsResponse;
	}

	/**
	 * The PhoneID Live API delivers insights such as whether a phone is active
	 * or disconnected, a device is reachable or unreachable and its roaming
	 * status. See https://developer.telesign.com/docs/rest_api-phoneid-live for
	 * detailed API documentation.
	 */
	public TeleSignResponse live(String phone_number, String ucid,
			Map<String, String> liveParams) {
		tsResponse = new TeleSignResponse();
		try {

			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL,
					V1_PHONEID_LIVE + phone_number, "GET", customer_id,
					secret_key, phoneIdParams);

			tr.addParam("ucid", ucid);

			TeleSignUtils.parseGetParams(tr, liveParams);

			tsResponse = tr.executeRequest();
		} catch (IOException e) {

			System.err.println("IOException while executing phoneid live API: "
					+ e.getMessage());
			throw new RuntimeException(e);
		}
		return tsResponse;
	}

	/**
	 * The PhoneID Number Deactivation API determines whether a phone number has
	 * been deactivated and when, based on carriers' phone number data and
	 * TeleSign's proprietary analysis. See
	 * https://developer.telesign.com/docs/rest_api-phoneid-number-deactivation
	 * for detailed API documentation.
	 */
	public TeleSignResponse numberDeactivation(String phoneNo, String ucid,
			Map<String, String> deactivationParams) {
		tsResponse = new TeleSignResponse();
		try {

			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL,
					V1_PHONEID_NUMBER_DEACTIVATION + phoneNo, "GET",
					customer_id, secret_key, phoneIdParams);
			if (null != ucid)
				tr.addParam("ucid", ucid);

			TeleSignUtils.parseGetParams(tr, deactivationParams);

			tsResponse = tr.executeRequest();
		} catch (Exception e) {
			System.err.println("IOException while executing phoneid live API: "
					+ e.getMessage());
			throw new RuntimeException(e);
		}
		return tsResponse;
	}
}
