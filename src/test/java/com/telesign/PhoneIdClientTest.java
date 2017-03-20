package com.telesign;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.telesign.phoneid.PhoneIdClient;
import com.telesign.response.TeleSignResponse;
import com.telesign.util.TestUtil;

public class PhoneIdClientTest {
	@BeforeClass
	public static void setUp() throws IOException {
		TestUtil.initProperties();
	}

	private PhoneIdClient getInstance() {
		if (TestUtil.CUSTOMER_ID.isEmpty() || TestUtil.SECRET_KEY.isEmpty()) {
			fail("CUSTOMER_ID, SECRET_KEY must be set to pass this test");
		}

		return new PhoneIdClient(TestUtil.CUSTOMER_ID, TestUtil.SECRET_KEY,
				TestUtil.params);
	}
	
	@Test
	public void phoneidTest(){
		PhoneIdClient phoneIdClient = getInstance();
		HashMap<String, String> phoneidParams = new HashMap<String, String>();
		phoneidParams.put("account_lifecycle_event", "create");
		TeleSignResponse response = phoneIdClient.phoneid(TestUtil.PHONE_NUMBER, phoneidParams);
		assertNotNull(response);
		JsonObject jsonObject = response.getBody().getAsJsonObject();
		System.out.println(response.getBody());
		assertTrue((jsonObject.getAsJsonObject("status")).get("code").getAsInt() == 300);
		
	}
}
