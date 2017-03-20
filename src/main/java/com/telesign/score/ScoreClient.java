package com.telesign.score;

import java.io.IOException;
import java.util.Map;

import com.telesign.response.TeleSignResponse;
import com.telesign.util.TeleSignRequest;
import com.telesign.util.TeleSignUtils;

/**
 * Score provides risk information about a specified phone number.
 */
public class ScoreClient {
	private final String customer_id;
	private final String secret_key;
	private Map<String, String> params;
	private TeleSignResponse tsResponse;
	private static final String SCORE_RESOURCE = "/v1/score/";
	private static final String API_BASE_URL = "https://rest-api.telesign.com";

	/**
	 * @param customer_id
	 * @param secret_key
	 * @param params
	 */
	public ScoreClient(String customer_id, String secret_key,
			Map<String, String> params) {

		this.customer_id = customer_id;
		this.secret_key = secret_key;
		this.params = params;
	}
	


	/**
	 * Score is an API that delivers reputation scoring based on phone number intelligence, traffic patterns, machine learning, and a global data consortium. See https://developer.telesign.com/docs/rest_api-phoneid-score for detailed API documentation.
	 * @param phone_number
	 * @param account_lifecycle_event
	 * @param params
	 * @return
	 */
	public TeleSignResponse score(String phone_number, String account_lifecycle_event, Map<String, String> scoreParams){
		tsResponse = new TeleSignResponse();
		try {
			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL,
					SCORE_RESOURCE + phone_number, "POST", customer_id, secret_key, params);
						
			scoreParams.put("account_lifecycle_event", account_lifecycle_event);
			StringBuffer body = TeleSignUtils.parsePostParams(scoreParams);

			tr.setPostBody(body.toString());

			tsResponse = tr.executeRequest();
		} catch (IOException e) {

			System.err.println("IOException while executing verify call API: "
					+ e.getMessage());
			throw new RuntimeException(e);
		}
		return tsResponse;
	}			        
			        
}
