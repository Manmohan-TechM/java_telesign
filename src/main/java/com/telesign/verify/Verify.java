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

import com.google.gson.Gson;
import com.telesign.response.TeleSignResponse;
import com.telesign.util.IpValidator;
import com.telesign.util.TeleSignRequest;
import com.telesign.verify.response.VerifyResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  The Verify class abstracts your interactions with the <em>TeleSign Verify web service</em>.
 *  A Verify object encapsulates your credentials (your TeleSign <em>Customer ID</em> and <em>Secret Key</em>).
 */
public class Verify {

	private final String customer_id;
	private final String secret_key;
	private int connectTimeout = 30000;
	private int readTimeout = 30000;
	private String httpsProtocol = "TLSv1.2";

	private static final String API_BASE_URL   = "https://rest.telesign.com";
	private static final String API_MOBILE_URL = "https://rest-mobile.telesign.com";
	
	private static final String V1_VERIFY       = "/v1/verify/";
	private static final String V1_VERIFY_SMS   = "/v1/verify/sms";
	private static final String V1_VERIFY_CALL  = "/v1/verify/call"; 
	private static final String V1_VERIFY_SMART = "/v1/verify/smart";
	
	private static final String V2_VERIFY_PUSH         = "/v2/verify/push";
	private static final String V2_VERIFY_TOKEN        = "/v2/verify/soft_token";
	private static final String V2_VERIFY_REGISTRATION = "/v2/verify/registration/";
	
	private final Gson gson = new Gson();
	private TeleSignResponse tsResponse;
	/**
	 * The Verify class constructor.
	 * Once you instantiate a Verify object, you can use it to make instance calls to <em>Verify SMS</em> and <em>Verify Call</em>.
	 * @param customer_id	[Required]	A string containing your TeleSign Customer ID (your TeleSign account number).
	 * @param secret_key	[Required]	A string containing your TeleSign Secret Key (a bese64-encoded string valu, available from the TeleSign Client Portal).
	 */
	public Verify(String customer_id, String secret_key) {

		this.customer_id = customer_id;
		this.secret_key = secret_key;
	}
	
	/**
	 * The Verify class constructor.
	 * Once you instantiate a Verify object, you can use it to make instance calls to <em>Verify SMS</em> and <em>Verify Call</em>.
	 * @param customer_id	[Required]	A string containing your TeleSign Customer ID (your TeleSign account number).
	 * @param secret_key	[Required]	A string containing your TeleSign Secret Key (a bese64-encoded string valu, available from the TeleSign Client Portal).
	 * @param httpsProtocol [Optional]	Specify the protocol version to use. ex: TLSv1.1, TLSv1.2. default is TLSv1.2
	 */
	public Verify(String customer_id, String secret_key, String httpsProtocol) {

		this.customer_id = customer_id;
		this.secret_key = secret_key;
		this.httpsProtocol = httpsProtocol;
	}
	
	/**
	 * The Verify class constructor.
	 * Once you instantiate a Verify object, you can use it to make instance calls to <em>Verify SMS</em> and <em>Verify Call</em>.
	 * @param customer_id	[Required]	A string containing your TeleSign Customer ID (your TeleSign account number).
	 * @param secret_key	[Required]	A string containing your TeleSign Secret Key (a bese64-encoded string valu, available from the TeleSign Client Portal).
	 * @param connectTimeout 
	 * 			[Required] A integer representing connection timeout value while connecting to Telesign api.
	 * @param readTimeout
	 * 			[Required] A integer representing read timeout value while reading response returned from Telesign api.
	 */
	public Verify(String customer_id, String secret_key, int connectTimeout, int readTimeout) {

		this.customer_id = customer_id;
		this.secret_key = secret_key;
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
	}
	
	/**
	 * The Verify class constructor.
	 * Once you instantiate a Verify object, you can use it to make instance calls to <em>Verify SMS</em> and <em>Verify Call</em>.
	 * @param customer_id	[Required]	A string containing your TeleSign Customer ID (your TeleSign account number).
	 * @param secret_key	[Required]	A string containing your TeleSign Secret Key (a bese64-encoded string valu, available from the TeleSign Client Portal).
	 * @param connectTimeout 
	 * 			[Required] A integer representing connection timeout value while connecting to Telesign api.
	 * @param readTimeout
	 * 			[Required] A integer representing read timeout value while reading response returned from Telesign api.
	 * @param httpsProtocol [Optional]	Specify the protocol version to use. ex: TLSv1.1, TLSv1.2. default is TLSv1.2
	 */
	public Verify(String customer_id, String secret_key, int connectTimeout, int readTimeout, String httpsProtocol) {

		this.customer_id = customer_id;
		this.secret_key = secret_key;
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
		this.httpsProtocol = httpsProtocol;
	}

	/**
	 * Delivers a verification code to the end user by sending it in a text message.
	 * This is the simplest of the three overloads of this method. This overload takes the only required paramter�the end user's phone number. 
	 * @param phone_number	[Required] A string containing the user's phone number.
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	public TeleSignResponse sms(String phone_number) {

		return sms(phone_number, null);
	}

	/**
	 * Delivers a verification code to the end user by sending it in a text message.
	 * Use this overload when the user's native written language is not the default language (English). You specify the user's language in the <em>language</em> parameter.
	 * @param phone_number	[Required]	A string containing the user�s phone number.
	 * @param language		[Optional]	A string containing the IETF language tag. For example, "fr-CA". Set this value to "null" to use English (the default). This value is used in applying predefined text message templates.
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	public TeleSignResponse sms(String phone_number, String language) {

		return sms(phone_number, language, null, null);
	}
	
	/**
	 * Delivers a verification code to the end user by sending it in a text message.
	 * Use this overload when the user's native written language is not the default language (English). You specify the user's language in the <em>language</em> parameter.
	 * @param phone_number	[Required]	A string containing the user�s phone number.
	 * @param language		[Optional]	A string containing the IETF language tag. For example, "fr-CA". Set this value to "null" to use English (the default). This value is used in applying predefined text message templates.
	 * @param verify_code	[Optional]	A string containing the verification code that you want to send to the end user. When you set this value to "null", TeleSign automatically generates the verification code (the default behavior).
	 * @param template		[Optional]	A string containing a text message to override the predefined text message template. Your text message must incorporate a $$CODE$$ placeholder to integrate the verify_code field. Set this value to null (the default) to use the predefined template.
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	public TeleSignResponse sms(String phone_number, String language, String verify_code, String template) {
		
		return sms(phone_number, language, verify_code, template, null, null);
	}

	/**
	 * Delivers a verification code to the end user by sending it in a text message.
	 * Use this overload when:
	 * <ul>
	 * 	<li>the end user's native written language is not the default language (English), or</li>
	 * 	<li>when you want to send the user a verification code that you create, or</li>
	 * 	<li>when you want to apply a custom text message template.</li>
	 * </ul>
	 * 
	 * @param phone_number	[Required]	A string containing the user�s phone number.
	 * @param language		[Optional]	A string containing the IETF language tag. For example, "fr-CA". Set this value to "null" to use English (the default). This value is used in applying predefined text message templates.
	 * @param verify_code	[Optional]	A string containing the verification code that you want to send to the end user. When you set this value to "null", TeleSign automatically generates the verification code (the default behavior).
	 * @param template		[Optional]	A string containing a text message to override the predefined text message template. Your text message must incorporate a $$CODE$$ placeholder to integrate the verify_code field. Set this value to null (the default) to use the predefined template.
	 * @param originating_ip [Optional] Your end users IP Address. This value must be in the format defined by IETF in the 
	 * 								   Internet-Draft document titled Textual Representation of IPv4 and IPv6 Addresses. Ex: originating_ip=192.168.123.456.
	 * 								   Set it to null if not sending originating ip.
	 * @param session_id	[Optional] Your end users session id. Set it to "null" if not sending session id.
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	public TeleSignResponse sms(String phone_number, String language, String verify_code, String template, String originating_ip, String session_id) {

		String result = null;
		tsResponse = new TeleSignResponse();
		try {

			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL, V1_VERIFY_SMS, "POST", customer_id, secret_key, connectTimeout, readTimeout, httpsProtocol);
			String body = "phone_number=" + URLEncoder.encode(phone_number, "UTF-8");
			
			if(language != null) {

				body += "&language=" + URLEncoder.encode(language, "UTF-8");
			}
			
			if(verify_code != null) {

				body += "&verify_code=" + URLEncoder.encode(verify_code, "UTF-8");
			}
			
			if(template != null) {

				body += "&template=" + URLEncoder.encode(template, "UTF-8");
			}
			
			if(originating_ip != null && !originating_ip.isEmpty() && IpValidator.isValidIpAddress(originating_ip)) {

				body += "&originating_ip=" + URLEncoder.encode(originating_ip, "UTF-8");
			}
			
			if(session_id != null && !session_id.isEmpty()) {

				body += "&session_id=" + URLEncoder.encode(session_id, "UTF-8");
			}
			
			
			tr.setPostBody(body);
			
			//result = tr.executeRequest();
			tsResponse = tr.executeRequest();
		}
		catch (IOException e) {

			System.err.println("IOException while executing Verify SMS API: " + e.getMessage());
			throw new RuntimeException(e);
		}
		
		//VerifyResponse response = gson.fromJson(result, VerifyResponse.class);
		
		return tsResponse;
	}
	
	/**
	 * Delivers a verification code to the end user with a phone call. When the user answers their phone, the TeleSign server plays an automated voice message that contains the code.
	 * This is the simplest of the three overloads of this method. This overload takes the only required parameter�the end user's phone number.
	 * @param phone_number	[Required]	A string containing the user�s phone number.
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	/*public VerifyResponse call(String phone_number) {

		return call(phone_number, null);
	}*/
	
	/**
	 * Delivers a verification code to the end user with a phone call. When the user answers their phone, the TeleSign server plays an automated voice message that contains the code.
	 * Use this overload when the user's native spoken language is not the default language (English). You specify the user's language in the <em>language</em> parameter.
	 * @param phone_number	[Required] A string containing the user's phone number.
	 * @param language		[Optional]	A string containing the IETF language tag. For example, "fr-CA". Set this value to "null" to use English (the default).
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	/*public VerifyResponse call(String phone_number, String language) {

		return call(phone_number, language, null, null, 0, null, true, null, null, null, null);
	}*/
	/** 
	 * @param phone_number	[Required] A string containing the user's phone number.
	 * @param language		[Optional] A string containing the IETF language tag. For example, "fr-CA". Set this value to "null" to use English (the default).
	 * @param ttsMessage 	[Optional] Allows you to pass a text-to-speech (TTS) message with language. Ex String: Hello, your secret code is $$CODE$$. Thank you.
	 * @return	A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	/*public VerifyResponse call(String phone_number, String language, String ttsMessage) {

		return call(phone_number, language, null, null, 0, null, true, null, null, null, ttsMessage);
	}*/
		
	/**
	 * Delivers a verification code to the end user with a phone call. When the user answers their phone, the TeleSign server plays an automated voice message that contains the code.
	 * Use this overload when the user's native spoken language is not the default language (English). You specify the user's language in the <em>language</em> parameter.
	 * @param phone_number	[Required] A string containing the user�s phone number.
	 * @param language		[Optional]	A string containing the IETF language tag. For example, "fr-CA". Set this value to "null" to use English (the default).
	 * @param originating_ip [Optional] Your end users IP Address. This value must be in the format defined by IETF in the 
	 * 								   Internet-Draft document titled Textual Representation of IPv4 and IPv6 Addresses. Ex: originating_ip=192.168.123.456.
	 * 								   Set it to null if not sending originating ip.
	 * @param session_id	[Optional] Your end users session id. Set it to "null" if not sending session id.
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	/*public VerifyResponse call(String phone_number, String language, String originating_ip, String session_id) {

		return call(phone_number, language, originating_ip, session_id, null);
	}*/
	
	/**
	 * Delivers a verification code to the end user with a phone call. When the user answers their phone, the TeleSign server plays an automated voice message that contains the code.
	 * Use this overload when the user's native spoken language is not the default language (English). You specify the user's language in the <em>language</em> parameter.
	 * @param phone_number	[Required] A string containing the user�s phone number.
	 * @param language		[Optional]	A string containing the IETF language tag. For example, "fr-CA". Set this value to "null" to use English (the default).
	 * @param originating_ip [Optional] Your end users IP Address. This value must be in the format defined by IETF in the 
	 * 								   Internet-Draft document titled Textual Representation of IPv4 and IPv6 Addresses. Ex: originating_ip=192.168.123.456.
	 * 								   Set it to null if not sending originating ip.
	 * @param session_id	[Optional] Your end users session id. Set it to "null" if not sending session id.
	 * @param call_forward_action 	[Optional]	A string containing call forward action
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	/*public VerifyResponse call(String phone_number, String language, String originating_ip, String session_id, String call_forward_action) {

		return call(phone_number, language, null, null, 0, null, true, originating_ip, session_id, call_forward_action, null);
	}*/
	
	/**
	 * Delivers a verification code to the end user with a phone call. When the user answers their phone, the TeleSign server plays an automated voice message that contains the code.
	 * Use this overload when the user's native spoken language is not the default language (English). You specify the user's language in the <em>language</em> parameter.
	 * @param phone_number	[Required] A string containing the user�s phone number.
	 * @param language		[Optional]	A string containing the IETF language tag. For example, "fr-CA". Set this value to "null" to use English (the default).
	 * @param originating_ip [Optional] Your end users IP Address. This value must be in the format defined by IETF in the 
	 * 								   Internet-Draft document titled Textual Representation of IPv4 and IPv6 Addresses. Ex: originating_ip=192.168.123.456.
	 * 								   Set it to null if not sending originating ip.
	 * @param session_id	[Optional] Your end users session id. Set it to "null" if not sending session id.
	 * @param call_forward_action 	[Optional]	A string containing call forward action
	 * @param ttsMessage 	[Optional] Allows you to pass a text-to-speech (TTS) message with language. Ex String: Hello, your secret code is $$CODE$$. Thank you.
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	/*public VerifyResponse call(String phone_number, String language, String originating_ip, String session_id, String call_forward_action, String ttsMessage) {

		return call(phone_number, language, null, null, 0, null, true, originating_ip, session_id, call_forward_action, ttsMessage);
	}*/
	/**
	 * Delivers a verification code to the end user - with a phone call. When the user answers their phone, the TeleSign server plays an automated voice message that contains the code.
	 * Use this overload when:
	 * <ul>
	 * 	<li>the end user's spoken language is not the default language (English), or</li>
	 * 	<li>when you want to send them a verification code that you create, or </li>
	 * 	<li>when you need to specify a method for handling automated interactions with a PBX.</li>
	 * </ul>
	 * 
	 * @param phone_number			[Required]	A string containing the user's phone number.
	 * @param language				[Optional]	A string containing the IETF language tag. For example, "fr-CA". Set this value to "null" to use English (the default).
	 * @param verify_code			[Optional]	A string containing the verification code that you want to send to the end user. When you set this value to "null", TeleSign automatically generates the verification code (the default behavior).
	 * @param verify_method			[Optional]	A string containing the input method you want the end user to use when returning the verification code. Use a value of "keypress" when you want the user to use their phone to dial the code. Set this value to null when you want the user to enter the code into your web aplication (the default). 
	 * @param extension_type		[Optional]	An Integer value representing the type of response to use when dialing into a Private Branch Exchange (PBX). Use a value of 1 to have TeleSign use Dual-Tone Multi-Frequency (DTMF) tones to dail the user's extension. Use a value of 2 to have TeleSign use voice automation to request the user's extension. Use a value of 0 (the default) when the user isn't behind a PBX. 
	 * @param extension_template	[Optional]	A numerical string specifying the user's PBX extension number. Since this value is used in the call string, you can include one second pauses by adding commas before the extension number.  Set this value to null (the default) if not used. 
	 * @param redial				[Optional]	A boolean value that enables/disables redialing. Set this value to "true" (the default) when you want TeleSign to re-attempt the call after a failed attempt. Set this value to "false" when you don't.	
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	/*public VerifyResponse call(String phone_number, String language, String verify_code, String verify_method, int extension_type, String extension_template, boolean redial) {
		
		return call(phone_number , language, verify_code, verify_method, extension_type, extension_template, redial, null, null);
	}*/
	/**
	 * Delivers a verification code to the end user - with a phone call. When the user answers their phone, the TeleSign server plays an automated voice message that contains the code.
	 * Use this overload when:
	 * <ul>
	 * 	<li>the end user's spoken language is not the default language (English), or</li>
	 * 	<li>when you want to send them a verification code that you create, or </li>
	 * 	<li>when you need to specify a method for handling automated interactions with a PBX.</li>
	 * </ul>
	 * 
	 * @param phone_number			[Required]	A string containing the user�s phone number.
	 * @param language				[Optional]	A string containing the IETF language tag. For example, "fr-CA". Set this value to "null" to use English (the default).
	 * @param verify_code			[Optional]	A string containing the verification code that you want to send to the end user. When you set this value to "null", TeleSign automatically generates the verification code (the default behavior).
	 * @param verify_method			[Optional]	A string containing the input method you want the end user to use when returning the verification code. Use a value of "keypress" when you want the user to use their phone to dial the code. Set this value to null when you want the user to enter the code into your web aplication (the default). 
	 * @param extension_type		[Optional]	An Integer value representing the type of response to use when dialing into a Private Branch Exchange (PBX). Use a value of 1 to have TeleSign use Dual-Tone Multi-Frequency (DTMF) tones to dail the user's extension. Use a value of 2 to have TeleSign use voice automation to request the user's extension. Use a value of 0 (the default) when the user isn't behind a PBX. 
	 * @param extension_template	[Optional]	A numerical string specifying the user's PBX extension number. Since this value is used in the call string, you can include one second pauses by adding commas before the extension number.  Set this value to null (the default) if not used. 
	 * @param redial				[Optional]	A boolean value that enables/disables redialing. Set this value to "true" (the default) when you want TeleSign to re-attempt the call after a failed attempt. Set this value to "false" when you don't.
	 * @param originating_ip 		[Optional]  Your end users IP Address. This value must be in the format defined by IETF in the 
	 * 								   		    Internet-Draft document titled Textual Representation of IPv4 and IPv6 Addresses. Ex: originating_ip=192.168.123.456.
	 * 								 		    Set it to null if not sending originating ip.
	 * @param session_id			[Optional]  Your end users session id. Set it to "null" if not sending session id.	 
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	/*public VerifyResponse call(String phone_number , String language, String verify_code, String verify_method, int extension_type, String extension_template, boolean redial, String originating_ip, String session_id) {
		return call(phone_number , language, verify_code, verify_method, extension_type, extension_template, redial, originating_ip, session_id, null, null);
	}*/
	
	/**
	 * Delivers a verification code to the end user - with a phone call. When the user answers their phone, the TeleSign server plays an automated voice message that contains the code.
	 * Use this overload when:
	 * <ul>
	 * 	<li>the end user's spoken language is not the default language (English), or</li>
	 * 	<li>when you want to send them a verification code that you create, or </li>
	 * 	<li>when you need to specify a method for handling automated interactions with a PBX.</li>
	 * </ul>
	 * 
	 * @param phone_number			[Required]	A string containing the user�s phone number.
	 * @param language				[Optional]	A string containing the IETF language tag. For example, "fr-CA". Set this value to "null" to use English (the default).
	 * @param verify_code			[Optional]	A string containing the verification code that you want to send to the end user. When you set this value to "null", TeleSign automatically generates the verification code (the default behavior).
	 * @param verify_method			[Optional]	A string containing the input method you want the end user to use when returning the verification code. Use a value of "keypress" when you want the user to use their phone to dial the code. Set this value to null when you want the user to enter the code into your web aplication (the default). 
	 * @param extension_type		[Optional]	An Integer value representing the type of response to use when dialing into a Private Branch Exchange (PBX). Use a value of 1 to have TeleSign use Dual-Tone Multi-Frequency (DTMF) tones to dail the user's extension. Use a value of 2 to have TeleSign use voice automation to request the user's extension. Use a value of 0 (the default) when the user isn't behind a PBX. 
	 * @param extension_template	[Optional]	A numerical string specifying the user's PBX extension number. Since this value is used in the call string, you can include one second pauses by adding commas before the extension number.  Set this value to null (the default) if not used. 
	 * @param redial				[Optional]	A boolean value that enables/disables redialing. Set this value to "true" (the default) when you want TeleSign to re-attempt the call after a failed attempt. Set this value to "false" when you don't.
	 * @param originating_ip 		[Optional]  Your end users IP Address. This value must be in the format defined by IETF in the 
	 * 								   		    Internet-Draft document titled Textual Representation of IPv4 and IPv6 Addresses. Ex: originating_ip=192.168.123.456.
	 * 								 		    Set it to null if not sending originating ip.
	 * @param session_id			[Optional]  Your end users session id. Set it to "null" if not sending session id.
	 * @param call_forward_action 	[Optional]	A string containing call forward action
	 * @param ttsMessage			[Optional]	text to speech feature of Telesign gets used, <a href="https://developer.telesign.com/v2.0/docs/rest_api-verify-call#text-to-speech-tts-hints">TTS hints</a>
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	public TeleSignResponse call(String phone_number , String language, String verify_code, String verify_method, int extension_type, String extension_template, boolean redial, 
			String originating_ip, String session_id, String call_forward_action, String ttsMessage) {

		String result = null;
		tsResponse = new TeleSignResponse();
		try {

			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL, V1_VERIFY_CALL, "POST", customer_id, secret_key, connectTimeout, readTimeout, httpsProtocol);
			String body = "phone_number=" + URLEncoder.encode(phone_number, "UTF-8");
			
			if(language != null) {

				body += "&language=" + URLEncoder.encode(language, "UTF-8");
			}
			
			if(verify_code != null) {

				body += "&verify_code=" + URLEncoder.encode(verify_code, "UTF-8");
			}
			
			if(verify_method != null && verify_method.equalsIgnoreCase("keypress")) {

				body += "&verify_method=" + URLEncoder.encode(verify_method, "UTF-8");
			}

			if(extension_type > 0 && extension_type < 3) {

				body += "&extension_type=" + URLEncoder.encode(Integer.toString(extension_type), "UTF-8");
			}
			if(extension_template != null) {

				body += "&extension_template=" + URLEncoder.encode(extension_template, "UTF-8");
			}
			if(originating_ip != null && !originating_ip.isEmpty() && IpValidator.isValidIpAddress(originating_ip)) {

				body += "&originating_ip=" + URLEncoder.encode(originating_ip, "UTF-8");
			}
			if(session_id != null && !session_id.isEmpty()) {

				body += "&session_id=" + URLEncoder.encode(session_id, "UTF-8");
			}
			if(!redial) {

				body += "&redial=" + URLEncoder.encode(Boolean.toString(redial), "UTF-8");
			}
			
			if (null != call_forward_action) {				
				if ("block".equalsIgnoreCase(call_forward_action)) {

					body += "&call_forward_action=" + URLEncoder.encode("Block", "UTF-8");

				} else if ("flag".equalsIgnoreCase(call_forward_action)) {

					body += "&call_forward_action=" + URLEncoder.encode("Flag", "UTF-8");

				}
			}
			
			if(null != ttsMessage && !ttsMessage.isEmpty())
				body += "&tts_message=" + URLEncoder.encode(ttsMessage, "UTF-8");
			
			tr.setPostBody(body);
			//result = tr.executeRequest();
			tsResponse = tr.executeRequest();
		}
		catch (IOException e) {

			System.err.println("IOException while executing verify call API: " + e.getMessage());
			throw new RuntimeException(e);
		}
		
		//VerifyResponse response = gson.fromJson(result, VerifyResponse.class);
		
		return tsResponse;
	}
	
	/**
	 * Requests the verification result from TeleSign.
	 * After sending an end user a verification code, wait a minute or two to allow them to receive it and then respond, and then call this method to find out if the end user passed the code challenge.
	 * This method takes only one parameter�the ID of this particular web service transaction.
	 * @param resource_id	[Required]	The string returned in the Response Message that TeleSign sends upon receipt of your HTTP 1.1 Request Message - for either {@link com.telesign.verify#sms()} or {@link com.telesign.verify#call()}.
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	public TeleSignResponse status(String resource_id) {

		return status(resource_id, null);
	}
	
	/**
	 * Requests the verification result from TeleSign.
	 * After sending an end user a verification code, wait a minute or two to allow them to receive it and then respond, and then call this method to find out if the end user passed the code challenge.
	 * This method takes only one parameter�the ID of this particular web service transaction.
	 * @param resource_id	[Required]	The string returned in the Response Message that TeleSign sends upon receipt of your HTTP 1.1 Request Message - for either {@link com.telesign.verify#sms()} or {@link com.telesign.verify#call()}.
	 * @param verify_code	[Required]	The verification code received from the user.
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	public TeleSignResponse status(String resource_id, String verify_code) {
		return status(resource_id, verify_code, null, null);
	}

	/**
	 * Requests the verification result from TeleSign.
	 * After sending an end user a verification code, wait a minute or two to allow them to receive it and then respond, and then call this method to find out if the end user passed the code challenge.
	 * This method takes only one parameter�the ID of this particular web service transaction.
	 * @param resource_id	[Required]	The string returned in the Response Message that TeleSign sends upon receipt of your HTTP 1.1 Request Message - for either {@link com.telesign.verify#sms()} or {@link com.telesign.verify#call()}.
	 * @param verify_code	[Required]	The verification code received from the user.
	 * @param originating_ip [Optional] Your end users IP Address. This value must be in the format defined by IETF in the 
	 * 								   Internet-Draft document titled Textual Representation of IPv4 and IPv6 Addresses. Ex: originating_ip=192.168.123.456.
	 * 								   Set it to null if not sending originating ip.
	 * @param session_id	[Optional] Your end users session id. Set it to "null" if not sending session id.
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	public TeleSignResponse status(String resource_id, String verify_code, String originating_ip, String session_id) {

		String result = null;
		tsResponse = new TeleSignResponse();
		try {

			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL, V1_VERIFY + resource_id, "GET", customer_id, secret_key, connectTimeout, readTimeout, httpsProtocol);

			if (verify_code != null)
				tr.addParam("verify_code", verify_code);
			
			if(originating_ip != null && !originating_ip.isEmpty() && IpValidator.isValidIpAddress(originating_ip)) {

				tr.addParam("originating_ip", originating_ip);
			}
			
			if(session_id != null && !session_id.isEmpty()) {

				tr.addParam("session_id", session_id);
			}

			//result = tr.executeRequest();
			tsResponse = tr.executeRequest();
		}
		catch (IOException e) {

			System.err.println("IOException while executing Verify status API: " + e.getMessage());
			throw new RuntimeException(e);
		}
		
		//VerifyResponse response = gson.fromJson(result, VerifyResponse.class);		
		
		return tsResponse;
	}
	
	/**
	 * Requests the verification result from TeleSign.
	 * After sending an end user a verification code, wait a minute or two to allow them to receive it and then respond, and then call this method to find out if the end user passed the code challenge.
	 * This method takes only one parameter�the ID of this particular web service transaction.
	 * @param resource_id	[Required]	The string returned in the Response Message that TeleSign sends upon receipt of your HTTP 1.1 Request Message - for either {@link com.telesign.verify#sms()} or {@link com.telesign.verify#call()}.
	 * @param originating_ip [Optional] Your end users IP Address. This value must be in the format defined by IETF in the 
	 * 								   Internet-Draft document titled Textual Representation of IPv4 and IPv6 Addresses. Ex: originating_ip=192.168.123.456.
	 * 								   Set it to null if not sending originating ip.
	 * @param session_id	[Optional] Your end users session id. Set it to "null" if not sending session id.
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	/*public VerifyResponse status(String resource_id, String originating_ip, String session_id) {

		return status(resource_id, null, originating_ip, session_id);
	}*/

	/**
	 * @param phone_number [Required] Your end user's phone number, including the country code.
	 * @param bundle_id    [Required]
	 * @return  A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	/*public VerifyResponse registration(String phone_number, String bundle_id){
		return registration(phone_number, bundle_id, null, null);
	}*/

	/**
	 * @param phone_number 		   [Required] Your end user's phone number, including the country code.
	 * @param bundle_id 		   [optional] The identifier associated with your whitelabel app (your customized/branded version of the AuthID application).
	 * @param originating_ip       [Optional] Your end users IP Address. This value must be in the format defined by IETF in the 
	 * 								          Internet-Draft document titled Textual Representation of IPv4 and IPv6 Addresses. Ex: originating_ip=192.168.123.456.
	 * 								          Set it to null if not sending originating ip.
	 * @param session_id	       [Optional] Your end users session id. Set it to "null" if not sending session id.
	 * @return  A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	public TeleSignResponse registration(String phone_number, String bundle_id, String originating_ip, String session_id){
		String result = null;
		tsResponse = new TeleSignResponse();
		try {
			TeleSignRequest tr = new TeleSignRequest(API_MOBILE_URL, V2_VERIFY_REGISTRATION + phone_number, "GET", customer_id, secret_key, connectTimeout, readTimeout, httpsProtocol);
			
			if(null != bundle_id && !bundle_id.isEmpty()) {

				tr.addParam("bundle_id", bundle_id);
			}

			if(originating_ip != null && !originating_ip.isEmpty() && IpValidator.isValidIpAddress(originating_ip)) {

				tr.addParam("originating_ip", originating_ip);
			}
			
			if(session_id != null && !session_id.isEmpty()) {

				tr.addParam("session_id", session_id);
			}
			
			//result = tr.executeRequest();
			tsResponse = tr.executeRequest();
		}
		catch (IOException e) {

			System.err.println("IOException while executing verify registration API: " + e.getMessage());
			throw new RuntimeException(e);
		}
			
		//VerifyResponse response = gson.fromJson(result, VerifyResponse.class);
		
		return tsResponse;
	}
	
	/**
	 * @param phone_number [Required] Your end user's phone number, including the country code.
	 * @param ucid		   [Required] A string the specifies one of the <a href="http://docs.telesign.com/rest/content/xt/xt-use-case-codes.html#xref-use-case-codes">Use Case Codes</a>.
	 * @param language	   [Optional] Determines the message for Verify SMS and Verify Push. IETF language tag is used in mapping languages codes to predefined templates.
	 * @param verify_code  [Optional] The verification code used for the code challenge. By default, TeleSign automatically generates a six-digit value for you.
	 * @param preference   [Optional] Allows customers to override the Smart Verify method selection. Customers can specify either "call", "sms" or "push" to be the recommended method to attempt.
	 * @param ignore_risk  [Optional] If set to "true", allows customers to bypass blocking the request if the score is above the threshold value configured in the customer account.
	 * @param ttsMessage		 [Optional] Allows you to pass a text-to-speech (TTS) message with language. Language parameter must be used with this.
	 * @param pushMessage		 [Optional] Allows you to pass message that you want to Push. Ex String: Hello, your secret code is $$CODE$$. Thank you.
	 * @param smsMessage		 [Optional] Allows you to pass a message sent via sms . Ex String: Hello, your secret code is $$CODE$$. Thank you.
	 * @return	A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	/*public VerifyResponse smartVerify(String phone_number, String ucid, String caller_id, String language, String verify_code, String preference, String ignore_risk, String ttsMessage, String pushMessage, String smsMessage){
		return smartVerify(phone_number, ucid, caller_id, language, verify_code, preference, ignore_risk, null, null, ttsMessage, pushMessage, smsMessage);
	}*/
	
	/**
	 * @param phone_number [Required] Your end user's phone number, including the country code.
	 * @param ucid		   [Required] A string the specifies one of the <a href="http://docs.telesign.com/rest/content/xt/xt-use-case-codes.html#xref-use-case-codes">Use Case Codes</a>.
	 * @param language	   [Optional] Determines the message for Verify SMS and Verify Push. IETF language tag is used in mapping languages codes to predefined templates.
	 * @param verify_code  [Optional] The verification code used for the code challenge. By default, TeleSign automatically generates a six-digit value for you.
	 * @param preference   [Optional] Allows customers to override the Smart Verify method selection. Customers can specify either "call", "sms" or "push" to be the recommended method to attempt.
	 * @param ignore_risk  [Optional] If set to "true", allows customers to bypass blocking the request if the score is above the threshold value configured in the customer account.
	 * @return	A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	/*public VerifyResponse smartVerify(String phone_number, String ucid, String caller_id, String language, String verify_code, String preference, String ignore_risk){
		return smartVerify(phone_number, ucid, caller_id, language, verify_code, preference, ignore_risk, null, null, null, null, null);
	}*/
		
	/**
	 * @param phone_number [Required] Your end user's phone number, including the country code.
	 * @param ucid		   [Required] A string the specifies one of the <a href="http://docs.telesign.com/rest/content/xt/xt-use-case-codes.html#xref-use-case-codes">Use Case Codes</a>.
	 * @param caller_id	   [Optional] End user's caller ID if available. Used for Verify SMS and Verify Call transactions, but is ignored for Verify Push transactions.
	 * @param language	   [Optional] Determines the message for Verify SMS and Verify Push. IETF language tag is used in mapping languages codes to predefined templates. 
	 * 								  For Verify Voice, the language determines the set of audio files to play for the call. </br>For a complete list of language tags, see <a href="http://docs.telesign.com/rest/content/xt/xt-language-tags.html#xref-language-tags">Supported Languages</a>.
	 * @param verify_code  [Optional] The verification code used for the code challenge. By default, TeleSign automatically generates a six-digit value for you.
	 * 								  </br>If you prefer to use your own verification code, you can override the default behavior by including this parameter and giving it an all-digit string value (0-9 in Latin-1), with the length as specified by your selected TeleSign settings.
	 *     							  </br>Leading zeros are recognized, and therefore should be used accordingly.
	 * @param preference   [Optional] Allows customers to override the Smart Verify method selection. Customers can specify either "call", "sms" or "push" to be the recommended method to attempt. 
	 * 								  </br>Since not all methods are supported on all devices, TeleSign may ignore the selected override method, in order to provide the method that is most appropriate, in which case Telesign selects the method in the order of "push", "sms", and "call".
	 * @param ignore_risk  [Optional] If set to "true", allows customers to bypass blocking the request if the score is above the threshold value configured in the customer account.
	 * @param originating_ip [Optional] Your end users IP Address. This value must be in the format defined by IETF in the Internet-Draft document titled Textual Representation of IPv4 and IPv6 Addresses. 
	 * 									</br>Ex: originating_ip=192.168.123.456. </br>Set it to null if not sending originating ip.
	 * @param session_id	[Optional] Your end users session id. Set it to "null" if not sending session id.
	 * @param ttsMessage	[Optional] Allows you to pass a text-to-speech (TTS) message with language. Language parameter must be used with this.
	 * @param pushMessage	[Optional] Allows you to pass message that you want to Push. Ex String: Hello, your secret code is $$CODE$$. Thank you.
	 * @param smsMessage	[Optional] Allows you to pass a message sent via sms . Ex String: Hello, your secret code is $$CODE$$. Thank you.
	 * @return	A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	public TeleSignResponse smartVerify(String phone_number, String ucid, String caller_id, String language, String verify_code, String preference, String ignore_risk, String originating_ip, String session_id, String ttsMessage, String pushMessage, String smsMessage){
		String result = null;
		tsResponse = new TeleSignResponse();
		try {

			TeleSignRequest tr = new TeleSignRequest(API_BASE_URL, V1_VERIFY_SMART, "POST", customer_id, secret_key, connectTimeout, readTimeout, httpsProtocol);
			String body = "phone_number=" + URLEncoder.encode(phone_number, "UTF-8");

			if(null != ucid) {

				body += "&ucid=" + URLEncoder.encode(ucid, "UTF-8");
			}
			
			if(null != caller_id) {

				body += "&caller_id=" + URLEncoder.encode(caller_id, "UTF-8");
			}
			
			if(null != language) {

				body += "&language=" + URLEncoder.encode(language, "UTF-8");
			}
			
			if(null != verify_code) {

				body += "&verify_code=" + URLEncoder.encode(verify_code, "UTF-8");
			}
			
			if(null != preference) {

				body += "&preference=" + URLEncoder.encode(preference, "UTF-8");
			}
			
			if(null != ignore_risk) {

				body += "&ignore_risk=" + URLEncoder.encode(ignore_risk, "UTF-8");
			}
			
			if(null != ttsMessage && !ttsMessage.isEmpty())
				body += "&tts_message=" + URLEncoder.encode(ttsMessage, "UTF-8");
			
			if(null != pushMessage && !pushMessage.isEmpty())
				body += "&push_message=" + URLEncoder.encode(pushMessage, "UTF-8");
			
			if(null != smsMessage && !smsMessage.isEmpty())
				body += "&sms_message=" + URLEncoder.encode(smsMessage, "UTF-8");
			
			if(null != originating_ip && !originating_ip.isEmpty() && IpValidator.isValidIpAddress(originating_ip)) {

				body += "&originating_ip=" + URLEncoder.encode(originating_ip, "UTF-8");
			}
			if(null != session_id && !session_id.isEmpty()) {

				body += "&session_id=" + URLEncoder.encode(session_id, "UTF-8");
			}

			tr.setPostBody(body);			
			//result = tr.executeRequest();
			tsResponse = tr.executeRequest();
		}
		catch (IOException e) {

			System.err.println("IOException while executing smart verify API: " + e.getMessage());
			throw new RuntimeException(e);
		}	
		
		//VerifyResponse response = gson.fromJson(result, VerifyResponse.class);
		
		return tsResponse;
	}
	
	/**
	 * @param phone_number [Required] The phone number of the mobile device that you want to send push notifications to. 
	 * 								  The phone number must include its associated country code (1 for North America). 
	 * 								  For example, phone_number=13105551212.
	 * @param bundle_id 	   [Required] Specifies a custom banner and icon for the TeleSign AuthID application to use for this notification. 
	 * 							 	  This allows you to brand your notifications with your corporate logo and/or your service-specific branding.
	 * 							  	  [Examples] template=mobile_2fa, or template=Outlook-2FA
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	/*public VerifyResponse push(String phone_number, String bundle_id){
		return push(phone_number, null, null, bundle_id, null, null, null);
	}*/
	
	/**
	 * @param phone_number 		 [Required] The phone number of the mobile device that you want to send push notifications to. 
	 * 								  		The phone number must include its associated country code (1 for North America). 
	 * 								  		For example, phone_number=13105551212.
	 * @param notification_type	 [Optional] Indicates the security measure to use for transaction authorization. Valid values are <i>SIMPLE</i> and <i>CODE</i>.
	 * 									   	The default value is <i>SIMPLE</i>.
	 * @param notification_value [Optional] Applies when notification_type=CODE.You normally leave this parameter empty, 
	 * 										and accept the default behavior in which TeleSign automatically generates a six-digit value for you, 
	 * 										and sends it to you in our response message. If you'd prefer to use your own verification code, 
	 * 										you can override the default behavior by setting a numeric value for this parameter. 
	 * 										Values must be between six and eight digits long. [Default] is <i>null</i>.
	 * @param bundle_id 		 [Required] Specifies a custom banner and icon for the TeleSign AuthID application to use for this notification. 
	 * 							  			This allows you to brand your notifications with your corporate logo and/or your service-specific branding.
	 * 							  			[Examples] template=mobile_2fa, or template=Outlook-2FA
	 * @param message 			 [Optional] The message to display to the end user, in the body of the notification. 
	 * 							 			If you don't include this parameter, then TeleSign automatically supplies the default message.
	 * 							 			[Example] message=Enter the code displayed on our web site.[Default] is <i>null</i>.
	 * @param originating_ip 	 [Optional] Your end users IP Address. This value must be in the format defined by IETF in the 
	 * 								   		Internet-Draft document titled Textual Representation of IPv4 and IPv6 Addresses. Ex: originating_ip=192.168.123.456.
	 * 								   		Set it to null if not sending originating ip.
	 * @param session_id		 [Optional] Your end users session id. Set it to "null" if not sending session id.
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	public TeleSignResponse push(String phone_number, String notification_type, String notification_value, String bundle_id, String message, 
			String originating_ip, String session_id){
		String result = null;
		tsResponse = new TeleSignResponse();
		try {			
			TeleSignRequest tr = new TeleSignRequest(API_MOBILE_URL, V2_VERIFY_PUSH, "POST", customer_id, secret_key, connectTimeout, readTimeout, httpsProtocol);
			String body = "phone_number=" + URLEncoder.encode(phone_number, "UTF-8");			
			
			if(null == notification_type || notification_type.isEmpty()){
				
				notification_type = "SIMPLE";
				body += "&notification_type=" + URLEncoder.encode(notification_type, "UTF-8");
				
			} else if("CODE".equalsIgnoreCase(notification_type)) {
				
				body += "&notification_type=" + URLEncoder.encode(notification_type.toUpperCase(), "UTF-8");
				body += "&notification_value=" + URLEncoder.encode(isValidNotificationValue(notification_value)?notification_value:null, "UTF-8");
			}						

			if(null != bundle_id) {

				body += "&bundle_id=" + URLEncoder.encode(bundle_id, "UTF-8");
			}
			
			if(null != message) {

				body += "&message=" + URLEncoder.encode(message, "UTF-8");
			}
			
			if(null != originating_ip && !originating_ip.isEmpty() && IpValidator.isValidIpAddress(originating_ip)) {

				body += "&originating_ip=" + URLEncoder.encode(originating_ip, "UTF-8");
			}
			if(null != session_id && !session_id.isEmpty()) {

				body += "&session_id=" + URLEncoder.encode(session_id, "UTF-8");
			}
			
			tr.setPostBody(body);
			//result = tr.executeRequest();
			tsResponse = tr.executeRequest();
		}
		catch (IOException e) {

			System.err.println("IOException while executing Verify push API: " + e.getMessage());
			throw new RuntimeException(e);
		}		
		
		//VerifyResponse response = gson.fromJson(result, VerifyResponse.class);
		
		return tsResponse;
	}
	
	/**
	 * @param phone_number [Required] The phone number for the Verify Soft Token request, including country code. For example, phone_number=13105551212.	 
	 * @param verify_code  [Required] The verification code received from the end user.
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	/*public VerifyResponse softToken(String phone_number, String soft_token_id, String verify_code, String bundle_id){
		return softToken( phone_number, soft_token_id, verify_code, bundle_id, null, null);
	}*/
	
	/**
	 * @param phone_number   [Required] The phone number for the Verify Soft Token request, including country code. For example, phone_number=13105551212.
	 * @param soft_token_id  [Optional] The alphanumeric string that uniquely identifies your TeleSign soft token subscription.
	 * @param verify_code    [Required] The verification code received from the end user.
	 * @param bundle_id		 [Optional]
	 * @param originating_ip [Optional] Your end users IP Address. This value must be in the format defined by IETF in the 
	 * 								    Internet-Draft document titled Textual Representation of IPv4 and IPv6 Addresses. Ex: originating_ip=192.168.123.456.
	 * 								    Set it to null if not sending originating ip.
	 * @param session_id	 [Optional] Your end users session id. Set it to "null" if not sending session id.
	 * @return A {@link com.telesign.verify.response.VerifyResponse} object, which contains the JSON-formatted response body from the TeleSign server.
	 */
	public TeleSignResponse softToken(String phone_number, String soft_token_id, String verify_code, String bundle_id, String originating_ip, String session_id){
		String result = null;
		tsResponse = new TeleSignResponse();
		try {			
			TeleSignRequest tr = new TeleSignRequest(API_MOBILE_URL, V2_VERIFY_TOKEN, "POST", customer_id, secret_key, connectTimeout, readTimeout, httpsProtocol);
			String body = "phone_number=" + URLEncoder.encode(phone_number, "UTF-8");		

			if(null != soft_token_id) {

				body += "&soft_token_id=" + URLEncoder.encode(soft_token_id, "UTF-8");
			}
			
			if(null != verify_code) {

				body += "&verify_code=" + URLEncoder.encode(verify_code, "UTF-8");
			}
			
			if(null != bundle_id) {

				body += "&bundle_id=" + URLEncoder.encode(bundle_id, "UTF-8");
			}
			
			if(null != originating_ip && !originating_ip.isEmpty() && IpValidator.isValidIpAddress(originating_ip)) {

				body += "&originating_ip=" + URLEncoder.encode(originating_ip, "UTF-8");
			}
			
			if(null != session_id && !session_id.isEmpty()) {

				body += "&session_id=" + URLEncoder.encode(session_id, "UTF-8");
			}
			
			tr.setPostBody(body);
			//result = tr.executeRequest();
			tsResponse = tr.executeRequest();
		}
		catch (IOException e) {
			System.err.println("IOException while executing Verify soft token API: " + e.getMessage());
			throw new RuntimeException(e);
			}
		
		//VerifyResponse response = gson.fromJson(result, VerifyResponse.class);
		
		return tsResponse;
		}
	/**
	 * Matches the notification_value for a string having 6-8 digits 
	 * @param notification_value
	 * @return boolean value checking validity for notification_value  
	 */
	private boolean isValidNotificationValue(String notification_value){
		if(null != notification_value){
			final Matcher m = Pattern.compile("\\d{6,8}").matcher(notification_value);		
			return m.matches();
		} else 
			return false;
	}
}
