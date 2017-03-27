package com.telesign.examples.voice;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

import com.telesign.response.TeleSignResponse;
import com.telesign.voice.VoiceClient;

public class SendCallWithVerificationCode {

	public static void main(String[] args) {
		String customer_id = "customer_id";
		String secret_key = "secret_key";

		String phone_number = "phone_number";
		// Generating 5 digit random number				
		Random rand = new Random();
		long verify_code_long = rand.nextInt(90000) + 10000;
		
		String verify_code = generateVerifyCodeWithComma(verify_code_long);
		
		String message = "Hello, your code is " + verify_code + ". Once again, your code is " + verify_code + ". Goodbye.";
		String message_type = "OTP";
		

		// Configuration map for initializing constructor
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readTimeout", "30000");
		params.put("connectTimeout", "30000");
		params.put("httpsProtocol", "TLSv1.1");
		
		// uncomment below for making connection using proxy values 
		/*params.put("httpProxyIPAddress", "Ip Address of Proxy");
		params.put("httpProxyPort", "port");
		params.put("httpProxyUsername", "username");
		params.put("httpProxyPassword", "password");*/
		
		VoiceClient voiceClient = new VoiceClient(customer_id, secret_key, params);
		HashMap<String, String> voiceParams = new HashMap<String, String>();
		TeleSignResponse response = voiceClient.call(phone_number, message, message_type, voiceParams);

		if (response.getStatusLine().equalsIgnoreCase("ok")) {
			System.out.println("Call sent with message");
		}
		
		// Input code received with message & compare with random no sent
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the verification code you were sent: ");
		String user_entered_verify_code = scan.next();
		scan.close();
		
		if(user_entered_verify_code.equals(Long.toString(verify_code_long)))
			System.out.println("Your code is correct.");
		else
			System.out.println("Your code is incorrect.");

	}

	/**
	 * Verify code formatted with commas.
	 * Notice the Locale used in Decimal Format in method body.
	 * @param verify_code_long
	 * @return verify_code having commas in between.
	 */
	private static String generateVerifyCodeWithComma(long verify_code_long) {
		NumberFormat numFormat = DecimalFormat.getInstance(Locale.US);
		DecimalFormat dFormat = (DecimalFormat)numFormat;
		dFormat.applyPattern("#.#");
		dFormat.setGroupingUsed(true);
		dFormat.setGroupingSize(1);
		String verify_code = dFormat.format(verify_code_long);
		return verify_code;
	}

}
