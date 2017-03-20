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
import com.telesign.telebureau.TelebureauClient;
import com.telesign.util.TestUtil;

public class TelebureauClientTest {
	@BeforeClass
	public static void setUp() throws IOException {
		TestUtil.initProperties();
	}

	private TelebureauClient getInstance() {
		if (TestUtil.CUSTOMER_ID.isEmpty() || TestUtil.SECRET_KEY.isEmpty()) {
			fail("CUSTOMER_ID, SECRET_KEY must be set to pass this test");
		}

		return new TelebureauClient(TestUtil.CUSTOMER_ID, TestUtil.SECRET_KEY,
				TestUtil.params);
	}
	
	@Test
	public void createTest(){
		TelebureauClient telebureauClient = getInstance();
		HashMap<String, String> createEventParams = new HashMap<String, String>();	
		
		TeleSignResponse response = telebureauClient.create(TestUtil.PHONE_NUMBER, TestUtil.FRAUD_TYPE, TestUtil.OCCURRED_AT, createEventParams);
		assertNotNull(response);
		JsonObject jsonObject = response.getBody().getAsJsonObject();
		System.out.println(response.getBody());
		assertTrue((jsonObject.getAsJsonObject("status")).get("code").getAsInt() == 300);		
	}
	
	@Test
	public void retrieveTest(){
		TelebureauClient telebureauClient = getInstance();
		HashMap<String, String> retrieveParams = new HashMap<String, String>();
		
		TeleSignResponse createResponse = telebureauClient.create(TestUtil.PHONE_NUMBER, TestUtil.FRAUD_TYPE, TestUtil.OCCURRED_AT, retrieveParams);
		JsonObject jsonObject = createResponse.getBody().getAsJsonObject();
		
		TeleSignResponse retrieveResponse = telebureauClient.retrieve(jsonObject.get("reference_id").getAsString(), retrieveParams);
		assertNotNull(retrieveResponse);
		//JsonObject retrieveJsonObject = retrieveResponse.getBody().getAsJsonObject();
		System.out.println(retrieveResponse.getBody());
		assertTrue(retrieveResponse.getStatusCode() == 200);		
	}
	
	@Test
	public void deleteTest(){
		TelebureauClient telebureauClient = getInstance();
		HashMap<String, String> retrieveParams = new HashMap<String, String>();
		
		TeleSignResponse createResponse = telebureauClient.create(TestUtil.PHONE_NUMBER, TestUtil.FRAUD_TYPE, TestUtil.OCCURRED_AT, retrieveParams);
		JsonObject jsonObject = createResponse.getBody().getAsJsonObject();
		
		HashMap<String, String> deleteParams = new HashMap<String, String>();
		TeleSignResponse retrieveResponse = telebureauClient.delete(jsonObject.get("reference_id").getAsString(), deleteParams);
		assertNotNull(retrieveResponse);

		System.out.println(retrieveResponse.getBody());
		assertTrue(retrieveResponse.getStatusCode() == 200);
	}
}
