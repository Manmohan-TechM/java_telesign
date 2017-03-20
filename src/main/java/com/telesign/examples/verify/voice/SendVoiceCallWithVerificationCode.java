package com.telesign.examples.verify.voice;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import com.telesign.response.TeleSignResponse;
import com.telesign.verify.Verify;

public class SendVoiceCallWithVerificationCode {

	public static void main(String[] args) {
		String customer_id = "customer_id";
		String secret_key = "secret_key";

		String phone_number = "phone_number";
		// Generating 5 digit random number
		Random rand = new Random();
		long verify_code = rand.nextInt(90000) + 10000;

		// Configuration map for initializing constructor
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readTimeout", "30000");
		params.put("connectTimeout", "30000");
		params.put("httpsProtocol", "TLSv1.1");

		Verify verify = new Verify(customer_id, secret_key, params);

		HashMap<String, String> callParams = new HashMap<String, String>();
		callParams.put("verify_code", String.valueOf(verify_code));
		TeleSignResponse response = verify.voice(phone_number, callParams);

		if (response.getStatusLine().equalsIgnoreCase("ok")) {
			System.out.println("Call sent with message");
			// Input code received with message & compare with random no sent
			Scanner scan = new Scanner(System.in);
			System.out.println("Please enter the verification code you were sent: ");
			String user_entered_verify_code = scan.next();
			scan.close();

			if (user_entered_verify_code.equals(Long.toString(verify_code)))
				System.out.println("Your code is correct.");
			else
				System.out.println("Your code is incorrect.");
		}

	}

}
