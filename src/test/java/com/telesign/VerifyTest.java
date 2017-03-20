package com.telesign;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.telesign.response.TeleSignResponse;
import com.telesign.util.TestUtil;
import com.telesign.verify.Verify;

/**
 *	Copyright (c) TeleSign Corporation 2012.
 *	License: MIT
 *	Support email address "support@telesign.com"
 *	Author: jweatherford
 */
public class VerifyTest {
	
	@BeforeClass
    public static void setUp() throws IOException {
		TestUtil.initProperties();		
	}
	
	
	private Verify getInstance() {
		if(TestUtil.CUSTOMER_ID.isEmpty() || TestUtil.SECRET_KEY.isEmpty() || TestUtil.PHONE_NUMBER.isEmpty()) {
			fail("CUSTOMER_ID, SECRET_KEY and PHONE_NUMBER must be set to pass this test");
		}		
		Verify verify = new Verify(TestUtil.CUSTOMER_ID, TestUtil.SECRET_KEY, TestUtil.params);		
		return verify;
	}
	
	@Test
	public void verifyRequestSMS() {
		Verify ver = getInstance();
		HashMap<String, String> smsParams = new HashMap<String, String>();
		TeleSignResponse ret = ver.sms(TestUtil.PHONE_NUMBER, smsParams);
		assertNotNull(ret);
		JsonObject jsonObject = ret.getBody().getAsJsonObject();
		assertTrue(jsonObject.getAsJsonArray("errors").size() == 0);
	}
	
	@Test
	public void verifyRequestVoice() {
		Verify ver = getInstance();
		HashMap<String, String> voiceParams = new HashMap<String, String>();
		TeleSignResponse ret = ver.voice(TestUtil.PHONE_NUMBER, voiceParams);
		assertNotNull(ret);
		JsonObject jsonObject = ret.getBody().getAsJsonObject();
		assertTrue(jsonObject.getAsJsonArray("errors").size() == 0);
	}
	
	@Test
	public void verifyRequestVoiceWithLanguage() {
		Verify ver = getInstance();
		HashMap<String, String> voiceParams = new HashMap<String, String>();
		voiceParams.put("language", "fr-FR");
		TeleSignResponse ret = ver.voice(TestUtil.PHONE_NUMBER, voiceParams);
		assertNotNull(ret);
		JsonObject jsonObject = ret.getBody().getAsJsonObject();
		assertTrue(jsonObject.getAsJsonArray("errors").size() == 0);
	}
	
	@Test
	public void verifyRequestRegistration(){
		Verify ver = getInstance();
		HashMap<String, String> registrationParams = new HashMap<String, String>();
		TeleSignResponse ret = ver.registration(TestUtil.PHONE_NUMBER, registrationParams);
		assertNotNull(ret);
		JsonObject jsonObject = ret.getBody().getAsJsonObject();
		assertTrue(jsonObject.getAsJsonArray("errors").size() == 0);
	}
	
	@Test
	public void smartVerify(){
		Verify ver = getInstance();
		HashMap<String, String> smartVerifyParams = new HashMap<String, String>();
		TeleSignResponse ret = ver.smart(TestUtil.PHONE_NUMBER, smartVerifyParams);
		assertNotNull(ret);
		JsonObject jsonObject = ret.getBody().getAsJsonObject();
		assertTrue(jsonObject.getAsJsonArray("errors").size() == 0);
	}
	
	@Test
	public void verifyRequestPush(){
		Verify ver = getInstance();
		HashMap<String, String> pushParams = new HashMap<String, String>();
		TeleSignResponse ret = ver.push(TestUtil.PHONE_NUMBER, pushParams);		
		assertNotNull(ret);
		JsonObject jsonObject = ret.getBody().getAsJsonObject();
		assertTrue(jsonObject.getAsJsonArray("errors").size() == 0);
	}
	
	@Test
	public void verifyRequestSoftToken(){
		Verify ver = getInstance();
		HashMap<String, String> softTokenParams = new HashMap<String, String>();
		TeleSignResponse ret = ver.softToken(TestUtil.PHONE_NUMBER, softTokenParams);
		assertNotNull(ret);
		JsonObject jsonObject = ret.getBody().getAsJsonObject();
		assertTrue(jsonObject.getAsJsonArray("errors").size() == 0);
	}
	
	@Test
	public void verifySMSwithCodeGetStatus() {
		Verify ver = getInstance();
		HashMap<String, String> smsParams = new HashMap<String, String>();
		smsParams.put("verify_code", "12345");
		
		
		TeleSignResponse ret = ver.sms(TestUtil.PHONE_NUMBER, smsParams);
		assertNotNull(ret);
		JsonObject jsonObject = ret.getBody().getAsJsonObject();
		assertTrue(jsonObject.getAsJsonArray("errors").size() == 0);
		
		String reference_id = jsonObject.get("reference_id").getAsString();
		HashMap<String, String> statusParams = new HashMap<String, String>();
		statusParams.put("verify_code", "12345");
		TeleSignResponse ret2 = ver.status(reference_id, statusParams);
		assertNotNull(ret2);
		JsonObject statusJsonObject = ret2.getBody().getAsJsonObject();
		assertTrue(statusJsonObject.getAsJsonArray("errors").size() == 0);		
		assertTrue((statusJsonObject.getAsJsonObject("verify")).get("code_state").getAsString().equalsIgnoreCase("VALID"));
	}
	
}
