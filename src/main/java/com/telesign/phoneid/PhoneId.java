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
 * The PhoneId class abstracts your interactions with the
 * <em>TeleSign PhoneID web service</em>. A PhoneId object encapsulates your
 * credentials (your TeleSign <em>Customer ID</em> and <em>Secret Key</em>).
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
	 * The PhoneId class constructor. Once you instantiate a PhoneId object, you
	 * can use it to make instance calls to <em>PhoneID Standard</em>,
	 * <em>PhoneID Score</em>, <em>PhoneID Contact</em>, and
	 * <em>PhoneID Live</em>.
	 * 
	 * @param customer_id
	 *            [Required] A string representing your TeleSign Customer ID.
	 *            This represents your TeleSign account number.
	 * @param secret_key
	 *            [Required] A string representing your TeleSign Secret Key
	 *            (available from the TeleSign Client Portal).
	 * @param connectTimeout
	 *            [Required] A integer representing connection timeout value
	 *            while connecting to Telesign api.
	 * @param readTimeout
	 *            [Required] A integer representing read timeout value while
	 *            reading response returned from Telesign api.
	 * @param httpsProtocol
	 *            [Optional] Specify the protocol version to use. ex: TLSv1.1,
	 *            TLSv1.2. default is TLSv1.2
	 */
	public PhoneId(String customer_id, String secret_key,
			Map<String, String> params) {

		this.customer_id = customer_id;
		this.secret_key = secret_key;
		this.phoneIdParams = params;
	}

	/**
	 * Returns information about a specified phone number�s type, numbering
	 * structure, cleansing details, and location details.
	 * 
	 * @param phone_number
	 *            [Required] A string representing the phone number you want
	 *            information about.
	 * @param originating_ip
	 *            [Optional] Your end users IP Address. This value must be in
	 *            the format defined by IETF in the Internet-Draft document
	 *            titled Textual Representation of IPv4 and IPv6 Addresses. Ex:
	 *            originating_ip=192.168.123.456. Set it to null if not sending
	 *            originating ip.
	 * @param session_id
	 *            [Optional] Your end users session id. Set it to "null" if not
	 *            sending session id.
	 * @return A {@link com.telesign.phoneid.response.PhoneIdStandardResponse}
	 *         object, which contains the JSON-formatted response body from the
	 *         TeleSign server.
	 */
	public TeleSignResponse standard(String phone_number,
			Map<String, String> standardparams) {
		tsResponse = new TeleSignResponse();
		try {

			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL,
					V1_PHONEID_STANDARD + phone_number, "GET", customer_id,
					secret_key, phoneIdParams);
			TeleSignUtils.parseGetParams(tr, standardparams);

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
	 * Returns risk information about a specified phone number, including a
	 * real-time risk score, threat level, and recommendation for action.
	 * 
	 * @param phone_number
	 *            [Required] A string representing the phone number you want
	 *            information about.
	 * @param ucid
	 *            [Required] A string specifying one of the Use Case Codes.
	 * @param originating_ip
	 *            [Optional] Your end users IP Address. This value must be in
	 *            the format defined by IETF in the Internet-Draft document
	 *            titled Textual Representation of IPv4 and IPv6 Addresses. Ex:
	 *            originating_ip=192.168.123.456. Set it to null if not sending
	 *            originating ip.
	 * @param session_id
	 *            [Optional] Your end users session id. Set it to "null" if not
	 *            sending session id.
	 * @return A {@link com.telesign.phoneid.response.PhoneIdScoreResponse}
	 *         object, which contains the JSON-formatted response body from the
	 *         TeleSign server.
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
	 * Returns contact details for a specified phone number�s subscriber. This
	 * includes the subscriber's First Name, Last Name, Street Address, City,
	 * State (or Province), Country, and ZIP (Postal) Code.
	 * 
	 * @param phone_number
	 *            [Required] A string representing the phone number you want
	 *            information about.
	 * @param ucid
	 *            [Required] A string specifying one of the Use Case Codes.
	 * @param originating_ip
	 *            [Optional] Your end users IP Address. This value must be in
	 *            the format defined by IETF in the Internet-Draft document
	 *            titled Textual Representation of IPv4 and IPv6 Addresses. Ex:
	 *            originating_ip=192.168.123.456. Set it to null if not sending
	 *            originating ip.
	 * @param session_id
	 *            [Optional] Your end users session id. Set it to "null" if not
	 *            sending session id.
	 * @return A {@link com.telesign.phoneid.response.PhoneIdContactResponse}
	 *         object, which contains the JSON-formatted response body from the
	 *         TeleSign server.
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
	 * Returns information about a specified phone number�s
	 * <em>state of operation</em>. You can use it to find out if:
	 * <ul>
	 * <li>the line is in service,</li>
	 * <li>the number is reachable,</li>
	 * <li>the mobile phone is roaming, and if so, in which country.</li>
	 * </ul>
	 * 
	 * @param phone_number
	 *            [Required] A string representing the phone number you want
	 *            information about.
	 * @param ucid
	 *            [Required] A string specifying one of the Use Case Codes.
	 * @param originating_ip
	 *            [Optional] Your end users IP Address. This value must be in
	 *            the format defined by IETF in the Internet-Draft document
	 *            titled Textual Representation of IPv4 and IPv6 Addresses. Ex:
	 *            originating_ip=192.168.123.456. Set it to null if not sending
	 *            originating ip.
	 * @param session_id
	 *            [Optional] Your end users session id. Set it to "null" if not
	 *            sending session id.
	 * @return A {@link com.telesign.phoneid.response.PhoneIdContactResponse}
	 *         object, which contains the JSON-formatted response body from the
	 *         TeleSign server.
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
	 * The <em>PhoneID Number Deactivation</em> web service provides status
	 * information for phone numbers that have been deactivated due to various
	 * reasons. Reasons include, but are not limited to, contract termination,
	 * SIM card expiration, and so on.
	 * 
	 * @param phoneNo
	 *            [Required] A string representing the phone number you want
	 *            information about.
	 * @param ucid
	 *            [Required]
	 * @return PhoneIdCallForwardResponse
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
