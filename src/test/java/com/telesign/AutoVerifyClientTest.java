package com.telesign;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.telesign.autoverify.AutoVerifyClient;
import com.telesign.response.TeleSignResponse;
import com.telesign.util.TestUtil;

public class AutoVerifyClientTest {
	@BeforeClass
    public static void setUp() throws IOException {
		TestUtil.initProperties();		
	}
	
	
	private AutoVerifyClient getInstance() {
		if(TestUtil.CUSTOMER_ID.isEmpty() || TestUtil.SECRET_KEY.isEmpty()) {
			fail("CUSTOMER_ID, SECRET_KEY must be set to pass this test");
		}		
		AutoVerifyClient autoVerifyClient = new AutoVerifyClient(TestUtil.CUSTOMER_ID, TestUtil.SECRET_KEY, TestUtil.params);		
		return autoVerifyClient;
	}
	
	@Test
	public void verifyRequestSMS() {
		AutoVerifyClient ver = getInstance();
		HashMap<String, String> statusParams = new HashMap<String, String>();
		String external_id = "external_id";
		TeleSignResponse ret = ver.status(external_id, statusParams);
		assertNotNull(ret);
		JsonObject jsonObject = ret.getBody().getAsJsonObject();
		assertTrue(jsonObject.getAsJsonArray("errors").size() == 0);
	}
}
