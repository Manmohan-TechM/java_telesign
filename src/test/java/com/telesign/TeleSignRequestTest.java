package com.telesign;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.telesign.response.TeleSignResponse;
import com.telesign.util.AuthMethod;
import com.telesign.util.TeleSignRequest;
import com.telesign.util.TestUtil;

/**
 * Copyright (c) TeleSign Corporation 2012. License: MIT Support email address
 * "support@telesign.com" Author: jweatherford
 */
public class TeleSignRequestTest {
	public static String CUSTOMER_ID;
	public static String SECRET_KEY;
	public static String PHONE_NUMBER;
	public static String CONNECT_TIMEOUT;
	public static String READ_TIMEOUT;
	public static int readTimeout;
	public static int connectTimeout;
	public static boolean timeouts = false;
	public static String HTTPS_PROTOCOL;
	public static boolean isHttpsProtocolSet = false;

	@BeforeClass
	public static void setUp() throws IOException {
		TestUtil.initProperties();		
	}

	private TeleSignRequest getInstance() {
		if (TestUtil.CUSTOMER_ID.isEmpty() || TestUtil.SECRET_KEY.isEmpty()
				|| TestUtil.PHONE_NUMBER.isEmpty()) {
			fail("CUSTOMER_ID, SECRET_KEY and PHONE_NUMBER must be set to pass this test");
		}
		return new TeleSignRequest("https://rest.telesign.com",
				"/v1/phoneid/standard/" + TestUtil.PHONE_NUMBER, "GET",
				TestUtil.CUSTOMER_ID, TestUtil.SECRET_KEY, TestUtil.params);
	}

	@Test
	public void requestCreation() {
		TeleSignRequest tr = getInstance();
		assertNotNull(tr);

	}

	@Test(expected = IOException.class)
	public void malformedUrl() throws IOException {
		TeleSignRequest tr = new TeleSignRequest("junk://rest.telesign.com",
				"/v1/phoneid/standard/" + TestUtil.PHONE_NUMBER, "GET",
				TestUtil.CUSTOMER_ID, TestUtil.SECRET_KEY, TestUtil.params);

		assertNotNull(tr);
		tr.executeRequest();
	}

	@Test
	public void addParameterTest() {
		TeleSignRequest tr = getInstance();

		assertNull(tr.getAllParams().get("code"));
		tr.addParam("code", "001");
		assertTrue(tr.getAllParams().get("code").equals("001"));
	}

	@Test
	public void addHeaderTest() {
		TeleSignRequest tr = getInstance();

		assertNull(tr.getAllHeaders().get("Authorization"));
		tr.addHeader("Authorization", "fake");
		assertTrue(tr.getAllHeaders().get("Authorization").equals("fake"));

		assertNull(tr.getTsHeaders().get("X-TS-Date"));
		assertNull(tr.getAllHeaders().get("X-TS-Date"));

		tr.addHeader("X-TS-Date", "2012-03-13");

		assertTrue(tr.getTsHeaders().get("X-TS-Date").equals("2012-03-13"));
		assertTrue(tr.getAllHeaders().get("X-TS-Date").equals("2012-03-13"));

		try {
			TeleSignResponse response = tr.executeRequest();
			assertNotNull(response);
		} catch (IOException e) {
			fail("IOException through " + e.getMessage());
		}
	}

	@Test
	public void shaMethodTest() throws IOException {

		TeleSignRequest tr = getInstance();

		tr.setSigningMethod(AuthMethod.SHA256);

		assertNotNull(tr);

		TeleSignResponse result = tr.executeRequest();

		assertNotNull(result);

		JsonObject jsonObject = result.getBody().getAsJsonObject();
		assertTrue(jsonObject.getAsJsonArray("errors").size() == 0);
	}

	@Test
	public void nonceTest() throws IOException {
		String nonce = "myUniqueNonce" + System.currentTimeMillis();

		TeleSignRequest tr = getInstance();

		tr.setNonce(nonce);

		assertNotNull(tr);

		TeleSignResponse result = tr.executeRequest();

		assertNotNull(result);
		

		JsonObject jsonObject = result.getBody().getAsJsonObject();
		assertTrue(jsonObject.getAsJsonArray("errors").size() == 0);

	}

}
