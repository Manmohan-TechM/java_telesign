package com.telesign;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.telesign.messaging.MessagingClient;
import com.telesign.response.TeleSignResponse;
import com.telesign.util.TestUtil;

public class MessagingClientTest {
	@BeforeClass
	public static void setUp() throws IOException {
		TestUtil.initProperties();
	}

	private MessagingClient getInstance() {
		if (TestUtil.CUSTOMER_ID.isEmpty() || TestUtil.SECRET_KEY.isEmpty()) {
			fail("CUSTOMER_ID, SECRET_KEY must be set to pass this test");
		}

		return new MessagingClient(TestUtil.CUSTOMER_ID, TestUtil.SECRET_KEY,
				TestUtil.params);
	}

	@Test
	public void messageWithCodeTest() {
		MessagingClient messagingClient = getInstance();
		// Generating 5 digit random number
		Random rand = new Random();
		long randNo = rand.nextInt(90000) + 10000;
		String message = "Your code is ";
		String message_type = "OTP";

		HashMap<String, String> messageParams = new HashMap<String, String>();

		TeleSignResponse ret = messagingClient.message(TestUtil.PHONE_NUMBER,
				message + randNo, message_type, messageParams);
		assertNotNull(ret);
		JsonObject jsonObject = ret.getBody().getAsJsonObject();
		assertTrue((jsonObject.getAsJsonObject("status")).get("code").getAsInt() == 290);
	}

	@Test
	public void statusTest() {
		MessagingClient messagingClient = getInstance();
		// Generating 5 digit random number
		Random rand = new Random();
		long randNo = rand.nextInt(90000) + 10000;
		String message = "Your code is ";
		String message_type = "OTP";

		HashMap<String, String> messageParams = new HashMap<String, String>();

		TeleSignResponse ret = messagingClient.message(TestUtil.PHONE_NUMBER,
				message + randNo, message_type, messageParams);
		
		JsonObject jsonObject = ret.getBody().getAsJsonObject();
		
		HashMap<String, String> statusParams = new HashMap<String, String>();
		// Get Status
		TeleSignResponse statusResponse = messagingClient.status(jsonObject
				.get(("reference_id")).getAsString(), statusParams);

		assertNotNull(statusResponse);
		JsonObject statusJsonObject = statusResponse.getBody()
				.getAsJsonObject();
		assertTrue((statusJsonObject.getAsJsonObject("status")).get("code").getAsInt() == 290);
	}

}
