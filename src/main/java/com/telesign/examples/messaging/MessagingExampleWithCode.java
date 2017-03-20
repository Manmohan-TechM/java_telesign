package com.telesign.examples.messaging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.telesign.messaging.MessagingClient;
import com.telesign.response.TeleSignResponse;

public class MessagingExampleWithCode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String customer_id = "customer_id";
		String secret_key = "secret_key";

		String phone_number = "phone_number";
		String message = "Your code is ";
		String message_type = "OTP";

		// configuration map for initializing constructor
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readTimeout", "30000");
		params.put("connectTimeout", "30000");
		params.put("httpsProtocol", "TLSv1.1");

		MessagingClient messagingClient = new MessagingClient(customer_id,
				secret_key, params);

		// Generating 5 digit random number
		Random rand = new Random();
		long randNo = rand.nextInt(90000) + 10000;

		// Fetch Response from rest.telesign.com
		TeleSignResponse response = messagingClient.message(phone_number,
				message + randNo, message_type, null);

		// Input code received with message & compare with random no sent
		Scanner scan = new Scanner(System.in);
		System.out
				.println("Please enter the verification code you were sent: ");
		String user_entered_verify_code = scan.next();
		scan.close();

		if (user_entered_verify_code.equals(Long.toString(randNo)))
			System.out.println("Your code is correct.");
		else
			System.out.println("Your code is incorrect.");

	}

}
