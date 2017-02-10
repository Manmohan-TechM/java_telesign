package com.telesign.telebureau;

import java.io.IOException;
import java.util.Map;

import com.telesign.response.TeleSignResponse;
import com.telesign.util.TeleSignRequest;
import com.telesign.util.TeleSignUtils;

/**
 * TeleBureau is a service is based on TeleSign's watchlist, which is a
 * proprietary database containing verified phone numbers of users known to have
 * committed online fraud. TeleSign crowd-sources this information from its
 * customers. Participation is voluntary, but you have to contribute in order to
 * benefit.
 */
public class TeleBureauClient {
	private String customerId;
	private String secretKey;	
	private Map<String, String> telebureauParams;
	private static final String API_BASE_URL = "https://rest.telesign.com";
	private static final String TELEBUREAU_EVENT = "/v1/telebureau/event/";

	private TeleSignResponse tsResponse;

	public TeleBureauClient(String customerId, String secretKey,
			Map<String, String> params) {
		this.customerId = customerId;
		this.secretKey = secretKey;
		this.telebureauParams = params;
	}

	/**
	 * Creates a telebureau event corresponding to supplied data. See
	 * https://developer.telesign.com/docs/rest_api-telebureau for detailed API
	 * documentation.
	 *	
	 * @param phone_number
	 * @param fraud_type
	 * @param occurred_at
	 * @param createEventParams
	 * @return
	 */
	public TeleSignResponse create(String phone_number, String fraud_type,
			String occurred_at, Map<String, String> createEventParams) {
		tsResponse = new TeleSignResponse();
		try {

			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL,
					TELEBUREAU_EVENT, "POST", customerId, secretKey,
					telebureauParams);
			createEventParams.put("fraud_type", fraud_type);
			createEventParams.put("occurred_at", occurred_at);
			StringBuffer body = TeleSignUtils.parsePostParams(phone_number,
					createEventParams);

			tr.setPostBody(body.toString());

			tsResponse = tr.executeRequest();
		} catch (IOException e) {

			System.err
					.println("IOException while executing Telebureau create event API: "
							+ e.getMessage());
			throw new RuntimeException(e);
		}
		return tsResponse;
	}

	/**
	 * Retrieves the fraud event status. You make this call in your web
	 * application after completion of create transaction for a telebureau
	 * event. See https://developer.telesign.com/docs/rest_api-telebureau for
	 * detailed API documentation.
	 *	
	 * @param reference_id
	 * @param retrieveParams
	 * @return
	 */
	public TeleSignResponse retrieve(String reference_id,
			Map<String, String> retrieveParams) {
		tsResponse = new TeleSignResponse();
		try {
			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL,
					TELEBUREAU_EVENT + reference_id, "GET", customerId,
					secretKey, telebureauParams);
			TeleSignUtils.parseGetParams(tr, retrieveParams);

			tsResponse = tr.executeRequest();
		} catch (IOException e) {
			System.err
					.println("IOException while executing Telebureau retrieve API: "
							+ e.getMessage());
			throw new RuntimeException(e);
		}
		return tsResponse;
	}

	/**
	 * Deletes a previously submitted fraud event. You make this call in your
	 * web application after completion of the create transaction for a
	 * telebureau event. See
	 * <a href="https://developer.telesign.com/docs/rest_api-telebureau"> for detailed API</a>
	 * documentation.
	 * 
	 * @param reference_id
	 * @param deleteParams
	 * @return
	 */
	public TeleSignResponse delete(String reference_id,
			Map<String, String> deleteParams) {

		tsResponse = new TeleSignResponse();
		try {
			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL,
					TELEBUREAU_EVENT + reference_id, "DELETE", customerId,
					secretKey, telebureauParams);
			TeleSignUtils.parseGetParams(tr, deleteParams);

			tsResponse = tr.executeRequest();
		} catch (IOException e) {
			System.err
					.println("IOException while executing Telebureau delete API: "
							+ e.getMessage());
			throw new RuntimeException(e);
		}
		return tsResponse;

	}
}
