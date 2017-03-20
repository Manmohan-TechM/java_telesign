package com.telesign;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.telesign.phoneid.PhoneId;
import com.telesign.response.TeleSignResponse;
import com.telesign.util.TestUtil;

/**
 *	Copyright (c) TeleSign Corporation 2012.
 *	License: MIT
 *	Support email address "support@telesign.com"
 *	Author: jweatherford
 */
public class PhoneIdTest {
	
	
	@BeforeClass
    public static void setUp() throws IOException {
		TestUtil.initProperties();
	}	
	
	private PhoneId getInstance() {
		if(TestUtil.CUSTOMER_ID.isEmpty() || TestUtil.SECRET_KEY.isEmpty()) {
			fail("CUSTOMER_ID and SECRET_KEY must be set to pass this test");
		}				
		PhoneId pid = new PhoneId(TestUtil.CUSTOMER_ID, TestUtil.SECRET_KEY, TestUtil.params);
		
		return pid;
	}
	
	@Test
	public void phoneIdStandard() {
		PhoneId pid = getInstance();
		HashMap<String, String> standardParams = new HashMap<String, String>();
		
		TeleSignResponse response = pid.standard(TestUtil.PHONE_NUMBER, standardParams);
		
		assertNotNull(response);
		assertTrue(response.getStatusCode() == 200);
		JsonObject jsonObject = response.getBody().getAsJsonObject();
		assertTrue((jsonObject.getAsJsonObject("status")).get("code").getAsInt() == 300);		
	}
	
	@Test
	public void phoneIdScore() {
		PhoneId pid = getInstance();
		HashMap<String, String> scoreParams = new HashMap<String, String>();
		
		TeleSignResponse response = pid.score(TestUtil.PHONE_NUMBER, "BACS", scoreParams);
		assertNotNull(response);
		assertTrue(response.getStatusCode() == 200);
		JsonObject jsonObject = response.getBody().getAsJsonObject();
		

		assertTrue(jsonObject.getAsJsonArray("errors").size() == 0);
		assertTrue((jsonObject.getAsJsonObject("status")).get("code").getAsInt() == 300);
		assertTrue((jsonObject.getAsJsonObject("risk")).get("score").getAsInt() > 0);
		
	}
	
	@Test
	public void phoneIdContact() {
		PhoneId pid = getInstance();
		HashMap<String, String> contactParams = new HashMap<String, String>();
		
		TeleSignResponse response = pid.contact(TestUtil.PHONE_NUMBER, "BACS", contactParams);
		assertNotNull(response);
		
		assertTrue(response.getStatusCode() == 200); 
	}
	
	@Test
	public void phoneIdLiveDummy() {
		PhoneId pid = getInstance();
		HashMap<String, String> liveParams = new HashMap<String, String>();
		
		TeleSignResponse response = pid.live("13105551234", "BACS", liveParams);
		assertNotNull(response);
		JsonObject jsonObject = response.getBody().getAsJsonObject();
		
		assertTrue((jsonObject.getAsJsonObject("status")).get("code").getAsInt() == 301);  //for this fake number we expect a partially completed request
	}
	
	@Test
	public void phoneIdLiveReal() {		
		if(TestUtil.PHONE_NUMBER.isEmpty()) {
			fail("For this test we require a valid phone number to test against");
		}
		PhoneId pid = getInstance();
		HashMap<String, String> liveParams = new HashMap<String, String>();
		
		TeleSignResponse response = pid.live(TestUtil.PHONE_NUMBER , "BACS", liveParams);
		JsonObject jsonObject = response.getBody().getAsJsonObject();
		assertTrue((jsonObject.getAsJsonObject("status")).get("code").getAsInt() == 300); 
		assertNotNull(jsonObject.getAsJsonObject("live"));		
		assertTrue(!(jsonObject.getAsJsonObject("live")).get("subscriber_status").getAsString().isEmpty());
	}
		
	@Test
	public void phoneIdNoDeactivation() {		
		if(TestUtil.PHONE_NUMBER.isEmpty()) {
			fail("For this test we require a valid phone number to test against");
		}
		PhoneId pid = getInstance();
		HashMap<String, String> deactivationParams = new HashMap<String, String>();
		
		TeleSignResponse response = pid.numberDeactivation(TestUtil.PHONE_NUMBER, "BACS", deactivationParams);
		JsonObject jsonObject = response.getBody().getAsJsonObject();
		assertTrue((jsonObject.getAsJsonObject("status")).get("code").getAsInt() == 2300);		
	}
	
}
